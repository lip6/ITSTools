package fr.lip6.move.gal.application.runner.spot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBAEdge;
import fr.lip6.ltl.tgba.io.TGBAparserHOAF;
import fr.lip6.move.gal.application.logicng.ExpressionToLogicNG;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.CExpressionPrinter;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.PrefixParser;
import fr.lip6.move.gal.structural.expr.Simplifier;

public class SpotRunner {


	public static enum GivenStrategy {
		/**restrict edge labels to their useful subset*/
		CONSTRAIN,
		/**relax labels using impossible assignments if that reduce their support */
		RELAX,
		/** do both [the default] */
		ALL
	}
	
	private static final int DEBUG = 0;
	private String pathToltlfilt;
	private String pathToltl2tgba;
	private String pathToautfilt;
	private String workFolder;
	private long timeout;
	private String pathToautstates;
	private String pathToSenseCLSL;

	public SpotRunner(String pathToExe, String workFolder, long timeout) {
		super();
		this.pathToltlfilt = pathToExe;
		this.pathToltl2tgba = pathToExe.replace("ltlfilt", "ltl2tgba");
		this.pathToautfilt = pathToExe.replace("ltlfilt", "autfilt");
		this.pathToautstates = pathToExe.replace("ltlfilt", "autstates.py");
		this.pathToSenseCLSL = pathToExe.replace("ltlfilt", "senseclsl.py");
		this.workFolder = workFolder;
		this.timeout = timeout;
	}

	/**
	 * 
	 * @param prop an LTL property
	 * @return a TGBA or null if spot failure
	 */
	public TGBA transformToTGBA (Property prop) {
		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(Collections.singletonList(prop));
		try {
			return computeTGBA(prop, atoms, pmap);
		} catch (IOException|TimeoutException|InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String,TGBA> loadTGBA (PetriNet net) throws TimeoutException {
		Map<String,TGBA> automata = new HashMap<>();
		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(net.getProperties());
		try {
			for (Property prop : net.getProperties()) {
				TGBA tgba = computeTGBA(prop, atoms, pmap);
				if (tgba != null)
					automata.put(prop.getName(), tgba);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return automata;
	}


	private TGBA computeTGBA(Property prop, AtomicPropManager atoms, Map<String, Expression> pmap)
			throws IOException, TimeoutException, InterruptedException {
		TGBA tgba = null;
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToltl2tgba);
		cl.addArg("--hoaf=tv"); // prefix notation for output
		if (prop.getType() == PropertyType.LTL) {
			cl.addArg("-f"); // formula in next argument
			cl.addArg("!(" +printLTLProperty(pmap.get(prop.getName()))+ ")");
		} else {
			return null;
		}
		System.out.println("Running Spot : " + cl);
		File stdOutput = Files.createTempFile("spotaut", ".hoa").toFile();
		if (DEBUG == 0) stdOutput.deleteOnExit();
		int status = Runner.runTool(timeout, cl, stdOutput, true);
		if (status == 0) {
			if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());
			tgba = TGBAparserHOAF.parseFrom(stdOutput.getCanonicalPath(), atoms);
			if (DEBUG >= 2) System.out.println("Resulting TGBA : "+ tgba.toString());
		} else {
			System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
				stream.forEach(System.out::println);
			}
		}
		return tgba;
	}

	public void runLTLSimplifications (PetriNet net) throws TimeoutException {

		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(net.getProperties());
		try {
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.setWorkingDir(new File(workFolder));
			cl.addArg(pathToltlfilt);
			cl.addArg("--lbt"); // prefix notation for output
			cl.addArg("-r"); // reduce the formulas
			cl.addArg("--unabbreviate=eiMRW^"); // reduce the formulas			
			int seen = 0;
			for (Property prop : net.getProperties()) {
				if (prop.getType() == PropertyType.LTL) {
					cl.addArg("-f"); // formula in next argument
					cl.addArg(printLTLProperty(atoms.getAPformula(prop.getName())));
					seen++;
				}
			}
			if (seen == 0) return;
			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File outputff = Files.createTempFile("spotrun", ".txt").toFile();
			if (DEBUG == 0) outputff.deleteOnExit();
			int status = Runner.runTool(timeout, cl, outputff, true);
			if (status == 0) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + outputff.getCanonicalPath());
				BufferedReader reader = new BufferedReader(new FileReader(outputff));
				String line;						
				for (Property prop : net.getProperties()) {
					if (prop.getType() == PropertyType.LTL) {
						line = reader.readLine();
						if (line == null)
							break;
						Expression res = PrefixParser.parsePrefix(line, new ArrayList<>(atoms.getAtoms()));
						prop.setBody(res);
					}
				}						
				reader.close();
				
				if (DEBUG >= 1) {
					StringBuilder sb = new StringBuilder();
					for (Property prop : net.getProperties()) {
						sb.append(printLTLProperty(prop.getBody())).append(",");
					}
					System.out.println("Resulting properties : "+ sb.toString());
				}
			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(outputff.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static String printLTLProperty(Expression prop) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(baos);
		SpotPropertyPrinter pp = new SpotPropertyPrinter(pw, "src");
		prop.accept(pp);
		pw.close();
		return baos.toString();
	}

	public static void exportLTLProperties (PetriNet net, String prefix, String workFolder) throws IOException {
		String stdOutput = workFolder + "/"+prefix+".ltl";
		PrintWriter pw = new PrintWriter(new File(stdOutput));

		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(net.getProperties());
		SpotPropertyPrinter pp = new SpotPropertyPrinter(pw, "src");
		for (Property p : net.getProperties()) {
			if (p.getType() == PropertyType.LTL) {
				pmap.get(p.getName()).accept(pp);
				pw.println();
			}
		}
		pw.close();		
	}

	private static class SpotPropertyPrinter extends CExpressionPrinter {


		public SpotPropertyPrinter(PrintWriter pw, String prefix) {
			super(pw, prefix);
		}
		
		@Override
		public Void visit(AtomicPropRef apRef) {
			pw.print(apRef.getAp().getName());
			return null;
		}

		@Override
		public Void visit(BinOp binOp) {
			switch (binOp.getOp()) {
			case F:
				pw.print("F(");
				binOp.left.accept(this);
				pw.print(")");
				break;
			case G:
				pw.print("G(");
				binOp.left.accept(this);
				pw.print(")");
				break;
			case X:
				pw.print("X(");
				binOp.left.accept(this);
				pw.print(")");
				break;
			case U:
				infix(binOp, " U ");
				break;
			default:
				super.visit(binOp);
			}
			return null;
		}

		

	}

	public Map<String, TGBA> computeStutterings(PetriNet pn) throws TimeoutException {
		Map<String, TGBA> tgbas = loadTGBA(pn);
		if (DEBUG >= 1) {
			for (Entry<String, TGBA> entry:tgbas.entrySet()) {
				System.out.println("Found automata for " + entry.getKey() + " : " + entry.getValue());
			}
		}

		for (Entry<String, TGBA> entry:tgbas.entrySet()) {
			computeInfStutter(entry.getValue());
		}		
		return tgbas;
	}

	public TGBA computeProduct (TGBA tgba, String ltlprop) {
		
		try {
			File f1 = Files.createTempFile("autA", ".hoa").toFile();
			if (DEBUG == 0) f1.deleteOnExit();
			PrintWriter pw = new PrintWriter(f1);
			tgba.exportAsHOA(pw);
			pw.close();
			
			File f2 = Files.createTempFile("autB", ".hoa").toFile();
			if (DEBUG == 0) f2.deleteOnExit();
			buildAutomaton(ltlprop, f2);			
			
			return makeProduct(f1, f2, tgba.getApm());
			
		} catch (IOException|TimeoutException|InterruptedException e) {
			System.out.println("Spot failed to build product :");
			e.printStackTrace();
			return tgba;
		}		
	}
	
	public TGBA computeForwardClosedSI(TGBA tgba) throws IOException {
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToautstates);
		File curAut = Files.createTempFile("curaut", ".hoa").toFile();
		if (DEBUG == 0) curAut.deleteOnExit();
		PrintWriter pw = new PrintWriter(curAut);
		tgba.exportAsHOA(pw);
		pw.close();
		cl.addArg(curAut.getCanonicalPath());

		if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
		File autPath = Files.createTempFile("fwclsi", ".hoa").toFile();
		if (DEBUG == 0) autPath.deleteOnExit();
		int status = 1;
		try {
			status = Runner.runTool(timeout, cl, autPath, true);
		} catch (IOException | TimeoutException | InterruptedException e) {
			throw new IOException(e);
		}
		if (status == 0) {
			if (DEBUG >= 1) System.out.println("Successful run of Spot took " + (System.currentTimeMillis() - time) + " ms captured in "
					+ autPath.getCanonicalPath());
			BufferedReader br = new BufferedReader(new FileReader(autPath));
			String line = br.readLine();
			br.close();
			
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(autPath));
			fis.readNBytes(line.length()+1);
			TGBA tgbaout = TGBAparserHOAF.parseFrom(fis, tgba.getApm());
			fis.close();			
			
			boolean [] stutter = new boolean [tgbaout.nbStates()];
			
			for (int i=0, cur=0; i < line.length() ; i++) {
				char c = line.charAt(i);
				if (c == '1') {
					stutter[cur++] = true;
				} else if (c == '0') {
					stutter[cur++] = false;
				}
			}
			tgbaout.setStutterMarkers(stutter);
			if (DEBUG >= 2)
				System.out.println("Resulting TGBA : " + tgbaout.toString());
			return tgbaout;
		} else {
			System.out.println("Spot run failed in " + (System.currentTimeMillis() - time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(autPath.getCanonicalPath()))) {
				stream.forEach(System.out::println);
			}
			throw new IOException();
		}			
	}
	
	public void computeInfStutter(TGBA tgba)  {
		int oldinit = tgba.getInitial();
		long time = System.currentTimeMillis();
		List<Expression> infStutter = new ArrayList<>();
		for (int state = 0; state < tgba.nbStates() ; state++) {
			// set initial state
			tgba.setInitial(state);

			try {
				// export resulting automaton, load result to grab (reduced) alphabet
				File autPath = Files.createTempFile("aut"+state, ".hoa").toFile();
				if (DEBUG == 0) autPath.deleteOnExit();
				TGBA tgbaSimp = simplify(tgba,autPath);

				if (!tgbaSimp.getAPs().isEmpty()) {
					// now build infinite stuttering assertion over the alphabet
					StringBuilder sb = new StringBuilder();
					boolean first = true;
					for (AtomicProp ap : tgbaSimp.getAPs()) {
						if (first) first=false;
						else sb.append("&");
						sb.append("(G ").append(ap.getName()).append(" | G!").append(ap.getName()).append(")");
					}
					String ltl = sb.toString();
					File stutterAut = Files.createTempFile("stutter", ".hoa").toFile();
					if (DEBUG == 0) stutterAut.deleteOnExit();
					if (! buildAutomaton(ltl,stutterAut)) {
						break;
					}

					// finally make a product of these two
					TGBA prod = makeProduct(autPath,stutterAut,tgbaSimp.getApm());

					if (prod == null) {
						// suppose no edge
						infStutter.add(Expression.constant(false));
						continue;
					}

					// explore the product
					List<Expression> st = new ArrayList<>();
					for (TGBAEdge arc : prod.getEdges().get(prod.getInitial())) {
						st.add(arc.getCondition());
					}
					Expression dnf = Expression.nop(Op.OR,st);
					Expression red = new ExpressionToLogicNG().simplify(dnf);
					Expression fst = Simplifier.simplifyBoolean(red);						

					infStutter.add(fst);

				} else {
					if (tgbaSimp.getEdges().get(0).isEmpty()) {
						// no edge
						infStutter.add(Expression.constant(false));
					} else {
						// just true as AP
						infStutter.add(Expression.constant(true));
					}
				}
			} catch (TimeoutException|InterruptedException|IOException te) {
				System.out.println("Spot timed out "+te.getMessage());
				infStutter.add(Expression.constant(false));
			}
		}
		System.out.println("Stuttering acceptance computed with spot in "+(System.currentTimeMillis() - time)+" ms :" + infStutter);
		tgba.setInfStutterConditions(infStutter);
		tgba.setInitial(oldinit);
	}


	private TGBA makeProduct(File autPath, File stutterAut, AtomicPropManager apm) throws TimeoutException, IOException, InterruptedException {
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToautfilt);
		cl.addArg("--hoaf=tv"); // prefix notation for output
		// bigger is better, and spot likes it big !
		cl.addArg("--small");
		cl.addArg("-F");
		cl.addArg(autPath.getCanonicalPath());
		cl.addArg("--product-and="+ stutterAut.getCanonicalPath());

		if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
		File stdOutput = Files.createTempFile("prod", ".hoa").toFile();
		if (DEBUG == 0) stdOutput.deleteOnExit();
		int status = Runner.runTool(timeout, cl, stdOutput, true);
		if (status == 0) {
			if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());

			TGBA tgbaout = TGBAparserHOAF.parseFrom(stdOutput.getCanonicalPath(), apm);

			if (DEBUG >= 2) System.out.println("Resulting TGBA : "+ tgbaout.toString());
			return tgbaout;
		} else {
			System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
				stream.forEach(System.out::println);
			}
		}
		return null;
	}


	private boolean buildAutomaton(String ltl, File stdOutput) throws TimeoutException, IOException, InterruptedException {
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToltl2tgba);
		cl.addArg("--hoaf=tv"); // prefix notation for output
		cl.addArg("-f"); // formula in next argument
		cl.addArg(ltl);

		if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
		int status = Runner.runTool(timeout, cl, stdOutput, true);
		if (status == 0) {
			if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());
			return true;
		} else {
			System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
				stream.forEach(System.out::println);
			}
			return false;
		}

	}

	public TGBA simplify(TGBA tgba, File autPath) throws IOException, TimeoutException, InterruptedException {

		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToautfilt);
		cl.addArg("--hoaf=tv"); // prefix notation for output
		cl.addArg("--small");
		File curAut = Files.createTempFile("curaut", ".hoa").toFile();
		if (DEBUG == 0) curAut.deleteOnExit();
		PrintWriter pw = new PrintWriter(curAut);
		tgba.exportAsHOA(pw);
		pw.close();
		cl.addArg("-F");
		cl.addArg(curAut.getCanonicalPath());

		if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
		int status = Runner.runTool(timeout, cl, autPath, true);
		if (status == 0) {
			if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + autPath.getCanonicalPath());

			TGBA tgbaout = TGBAparserHOAF.parseFrom(autPath.getCanonicalPath(), tgba.getApm());

			if (DEBUG >= 2) System.out.println("Resulting TGBA : "+ tgbaout.toString());
			return tgbaout;
		} else {
			System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(autPath.getCanonicalPath()))) {
				stream.forEach(System.out::println);
			}
		}
		return tgba;
	}

	public boolean buildComplement (TGBA tgba, File compPath)  {
		try {
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.setWorkingDir(new File(workFolder));
			cl.addArg(pathToautfilt);

			cl.addArg("--hoaf=tv"); // force TGBA

			// pass TGBA in HOAF
			File curAut = Files.createTempFile("curaut", ".hoa").toFile();
			if (DEBUG == 0) curAut.deleteOnExit();
			PrintWriter pw = new PrintWriter(curAut);
			tgba.exportAsHOA(pw);
			pw.close();
			cl.addArg("-F");
			cl.addArg(curAut.getCanonicalPath());

			// please complement
			cl.addArg("--complement");
			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			int status = Runner.runTool(timeout, cl, compPath, true);
			if (status == 0) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + compPath.getCanonicalPath());
				return true;
			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(compPath.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}
		} catch (IOException|TimeoutException|InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	public boolean complementProductAndTestEmpty(TGBA tgba, Expression factoid) {
		
		try {
			File comp = Files.createTempFile("comp", ".hoa").toFile();
			if (DEBUG == 0) comp.deleteOnExit();
			buildComplement(tgba, comp);

			String ltl = printLTLProperty(factoid);
			File fact = Files.createTempFile("fact", ".hoa").toFile();
			if (DEBUG == 0) fact.deleteOnExit();
			buildAutomaton(ltl, fact);

			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.setWorkingDir(new File(workFolder));
			cl.addArg(pathToautfilt);

			cl.addArg("--hoaf=tv"); // force TGBA

			// pass comp in HOAF
			cl.addArg("-F");
			cl.addArg(comp.getCanonicalPath());

			// please test inclusion
			cl.addArg("--included-in="+fact.getCanonicalPath());

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File resPath = Files.createTempFile("res", ".hoa").toFile();
			if (DEBUG == 0) resPath.deleteOnExit();
			int status = Runner.runTool(timeout, cl, resPath, true);
			if (status == 0 || status == 1) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + resPath.getCanonicalPath());

				if (resPath.length() == 0) {
					return true;
				}
			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(resPath.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}

		} catch (IOException|TimeoutException|InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isProductEmpty(File a1path, String ltl) {
		try {
			long time = System.currentTimeMillis();		
			CommandLine cl = new CommandLine();
			cl.setWorkingDir(new File(workFolder));
			cl.addArg(pathToautfilt);
			cl.addArg("--hoaf=tv");
			
			cl.addArg("-F");
			cl.addArg(a1path.getCanonicalPath());

			File a2path = Files.createTempFile("a2", ".hoa").toFile();
			if (DEBUG == 0) a2path.deleteOnExit();
			buildAutomaton(ltl, a2path);
			cl.addArg("--product-and="+ a2path.getCanonicalPath());

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File stdOutput = Files.createTempFile("prod", ".hoa").toFile();
			if (DEBUG == 0) stdOutput.deleteOnExit();
			int status = Runner.runTool(timeout, cl, stdOutput, true);
			if (status == 0 || status == 1) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());

				if (stdOutput.length() == 0) {
					return true;
				}
			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}
		} catch (IOException|TimeoutException|InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void analyzeCLSL(TGBA tgba) throws IOException {
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToSenseCLSL);
		File curAut = Files.createTempFile("curaut", ".hoa").toFile();
		if (DEBUG == 0) curAut.deleteOnExit();
		PrintWriter pw = new PrintWriter(curAut);
		tgba.exportAsHOA(pw);
		pw.close();
		cl.addArg(curAut.getCanonicalPath());

		if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
		File outPath = Files.createTempFile("outclsl", ".txt").toFile();
		if (DEBUG == 0) outPath.deleteOnExit();
		int status = 1;
		try {
			status = Runner.runTool(timeout, cl, outPath, true);
		} catch (IOException | TimeoutException | InterruptedException e) {
			throw new IOException(e);
		}
		if (status == 0) {
			if (DEBUG >= 1) System.out.println("Successful run of Spot took " + (System.currentTimeMillis() - time) + " ms captured in "
					+ outPath.getCanonicalPath());
			BufferedReader br = new BufferedReader(new FileReader(outPath));
			// skip comment line
			// #is_stutter,is_sl_ins,is_cl_ins
			String line = br.readLine();
			line = br.readLine();			
			br.close();
			
			boolean [] stutter = new boolean [3];
			
			for (int i=0, cur=0; i < line.length() ; i++) {
				char c = line.charAt(i);
				if (c == '1') {
					stutter[cur++] = true;
				} else if (c == '0') {
					stutter[cur++] = false;
				}
			}
			
			if (stutter[0] && ! tgba.getProperties().contains("stutter-invariant")) {
				tgba.getProperties().add("stutter-invariant");
			}
			if (stutter[1]) {
				tgba.getProperties().add("sl-invariant");
			}
			if (stutter[2]) {
				tgba.getProperties().add("cl-invariant");
			}

		} else {
			System.out.println("Spot run failed in " + (System.currentTimeMillis() - time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(outPath.getCanonicalPath()))) {
				stream.forEach(System.out::println);
			}
			throw new IOException();
		}	
		
	}

	public TGBA buildTGBA(String ltl, AtomicPropManager apm) throws IOException, TimeoutException, InterruptedException {
		File f2 = Files.createTempFile("autLTL", ".hoa").toFile();
		if (DEBUG == 0) f2.deleteOnExit();
		buildAutomaton(ltl, f2);		
		
		return TGBAparserHOAF.parseFrom(f2.getCanonicalPath(), apm);
	}

	public TGBA givenThat(TGBA tgba, Expression factoid, GivenStrategy constrain) {
		TGBA tgbaout = tgba;

		try {
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.setWorkingDir(new File(workFolder));
			cl.addArg(pathToautfilt);
			cl.addArg("--hoaf=tv"); // prefix notation for output
			cl.addArg("--small");
			cl.addArg("--given-formula="+printLTLProperty(factoid));

			File curAut = Files.createTempFile("b4k", ".hoa").toFile();
			if (DEBUG == 0) curAut.deleteOnExit();
			PrintWriter pw = new PrintWriter(curAut);
			tgba.exportAsHOA(pw);
			pw.close();
			cl.addArg("-F");
			cl.addArg(curAut.getCanonicalPath());		

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File stdOutput = Files.createTempFile("prod", ".hoa").toFile();
			if (DEBUG == 0) stdOutput.deleteOnExit();
			int status = Runner.runTool(timeout, cl, stdOutput, true);
			if (status == 0 || status == 1) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());
				tgbaout = TGBAparserHOAF.parseFrom(stdOutput.getCanonicalPath(), tgba.getApm());


			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}
		} catch (IOException | TimeoutException | InterruptedException e) {
			e.printStackTrace();
		}
		return tgbaout;
	}
}
