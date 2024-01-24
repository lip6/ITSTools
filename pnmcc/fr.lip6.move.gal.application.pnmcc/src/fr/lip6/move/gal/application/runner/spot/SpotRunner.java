package fr.lip6.move.gal.application.runner.spot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.lip6.ltl.spot.binaries.BinaryToolsPlugin.Tool;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBA.ExportMode;
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
		/** restrict labels */
		RESTRICT,
		/** relax labels */
		RELAX,
		/**restrict/relax edge labels to their useful subset*/
		MINATO,
		/** A variant of relax designed to make the language SI */
		STUTTER_RELAX,
		/** A variant of restrict designed to make the language SI */
		STUTTER_RESTRICT,
		/** do both [the default] */
		ALL;
		
		public String toString() {
			switch (this) {
			case RESTRICT: return "restrict";
			case RELAX: return "relax";
			case MINATO: return "minato";
			case STUTTER_RELAX: return "stutter-relax";
			case STUTTER_RESTRICT: return "stutter-restrict";
			case ALL: return "all";
			default : return null;
			}
		}
	}
	
	private static final int DEBUG = 0;
	private String pathToltlfilt;
	private String pathToltl2tgba;
	private String pathToautfilt;
	private long timeout;
	private String pathToautstates;
	private String pathToSenseCLSL;

	public SpotRunner(long timeout) {
		super();
		try {
			this.pathToltlfilt = fr.lip6.ltl.spot.binaries.BinaryToolsPlugin.getProgramURI(Tool.ltlfilt).getPath();
			this.pathToltl2tgba = fr.lip6.ltl.spot.binaries.BinaryToolsPlugin.getProgramURI(Tool.ltl2tgba).getPath();
			this.pathToautfilt = fr.lip6.ltl.spot.binaries.BinaryToolsPlugin.getProgramURI(Tool.autfilt).getPath();
			this.pathToautstates = fr.lip6.ltl.spot.binaries.BinaryToolsPlugin.getProgramURI(Tool.autstates).getPath();
			this.pathToSenseCLSL = fr.lip6.ltl.spot.binaries.BinaryToolsPlugin.getProgramURI(Tool.senseclsl).getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public void printLTLPropertyToStream (Property prop, PrintWriter pw) {
		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(Collections.singletonList(prop));
		pw.println(printLTLProperty(pmap.get(prop.getName())));
	}

	public TGBA computeTGBA(Property prop, AtomicPropManager atoms, Map<String, Expression> pmap)
			throws IOException, TimeoutException, InterruptedException {
		List<File> todel = new ArrayList<>();
		try {
			TGBA tgba = null;
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.addArg(pathToltl2tgba);

			cl.addArg("--check=stutter");
			cl.addArg("--hoaf=tv"); // prefix notation for output
			if (prop.getType() == PropertyType.LTL) {
				cl.addArg("-f"); // formula in next argument
				cl.addArg("!(" +printLTLProperty(pmap.get(prop.getName()))+ ")");
			} else {
				return null;
			}
			System.out.println("Running Spot : " + cl);
			File stdOutput = Files.createTempFile("spotaut", ".hoa").toFile();
			todel.add(stdOutput);
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
		} finally {
			if (DEBUG==0)
				for (File f:todel) {
					f.delete();
				}
		}
	}


	public TGBA buildTGBA(String formula) throws IOException, TimeoutException, InterruptedException {
		List<File> todel = new ArrayList<>();
		try {
			TGBA tgba = null;
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.addArg(pathToltl2tgba);
			cl.addArg("--check=stutter");
			cl.addArg("--hoaf=tv");
			
			File formOutput = Files.createTempFile("form", ".ltl").toFile();
			todel.add(formOutput);
			try (FileOutputStream fos = new FileOutputStream(formOutput)) {
				fos.write(formula.getBytes());
			}
			cl.addArg("-F");
			cl.addArg(formOutput.getCanonicalPath()); // Directly use the formula string

			if (DEBUG > 0) System.out.println("Running Spot : " + cl);
			File stdOutput = Files.createTempFile("spotaut", ".hoa").toFile();
			todel.add(stdOutput);

			int status = Runner.runTool(timeout, cl, stdOutput, true);
			if (status == 0) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took " + (System.currentTimeMillis() - time) + " ms captured in " + stdOutput.getCanonicalPath());

				tgba = parseAbstractTGBA(stdOutput);
				if (DEBUG >= 2) System.out.println("Resulting TGBA : " + tgba.toString());
			} else {
				System.out.println("Spot run failed in " + (System.currentTimeMillis() - time) + " ms. Status : " + status);
				try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}
			return tgba;
		} finally {
			if (DEBUG==0)
				for (File f:todel) {
					f.delete();
				}
		}
	}

	public TGBA buildTGBAwithAlphabet(String formula, Set<String> rawSupport) throws IOException, TimeoutException, InterruptedException {
		List<File> todel = new ArrayList<>();
		try {
			long time = System.currentTimeMillis();
			File tgbaTempFile = Files.createTempFile("ltl2tgba", ".hoa").toFile();
			todel.add(tgbaTempFile);

			boolean buildSuccess = buildAutomaton(formula, tgbaTempFile);
			if (!buildSuccess) {
				return null;
			}

			CommandLine cl = new CommandLine();
			cl.addArg(pathToautfilt);
			cl.addArg("--hoaf=tv");
			cl.addArg("--small");
			cl.addArg("-F");
			cl.addArg(tgbaTempFile.getCanonicalPath());
			if (rawSupport != null && ! rawSupport.isEmpty()) {
				cl.addArg("--remove-ap=" + String.join(",", rawSupport));
			}

			File autfiltOutput = Files.createTempFile("autfilt", ".hoa").toFile();
			todel.add(autfiltOutput);

			int status = Runner.runTool(timeout, cl, autfiltOutput, true);
			if (status == 0) {
				if (DEBUG >= 1) {
					System.out.println("Running :" +cl);
					System.out.println("Successful run of autfilt took "+ (System.currentTimeMillis() - time) + " ms, captured in " + autfiltOutput.getCanonicalPath());
				}
				TGBA tgba = parseAbstractTGBA(autfiltOutput);
				return tgba;
			} else {
				System.out.println("autfilt run failed in " + (System.currentTimeMillis() - time) + " ms. Status: " + status);
				try (Stream<String> stream = Files.lines(Paths.get(autfiltOutput.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
				return null;
			}
		} finally {
			if (DEBUG==0)
				for (File f:todel) {
					f.delete();
				}
		}
	}

	
	public TGBA parseAbstractTGBA(File hoaFile) throws IOException {
		TGBA tgba;
		// Create a trivial or adapter version of AtomicPropManager
		AtomicPropManager trivialAPM = new AtomicPropManager() {
			@Override
			public AtomicProp findAP(String name) {
				return new AtomicProp(name, Expression.constant(true));
			}
		};

		tgba = TGBAparserHOAF.parseFrom(hoaFile.getCanonicalPath(), trivialAPM);
		return tgba;
	}


	public void runLTLSimplifications (PetriNet net) throws TimeoutException {
		List<File> todel = new ArrayList<>();
		try {

			AtomicPropManager atoms = new AtomicPropManager();
			Map<String, Expression> pmap = atoms.loadAtomicProps(net.getProperties());
			try {
				long time = System.currentTimeMillis();
				CommandLine cl = new CommandLine();
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
				todel.add(outputff);
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

							BitSet oldsupp = new BitSet();
							PetriNet.addSupport(prop.getBody(), oldsupp);

							BitSet support = new BitSet();
							PetriNet.addSupport(res, support);

							if (support.cardinality() < oldsupp.cardinality()) {
								prop.setBody(res);
							} else {
								Property test = new Property(prop.getBody(), PropertyType.LTL, "test");
								// count AP in resulting formula
								int nbAPold = countAP(test);
								test.setBody(res);
								int nbAPnew = countAP(test);
								if (nbAPnew <= nbAPold) {
									prop.setBody(res);
								}
							}
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
		} finally {
			if (DEBUG==0)
				for (File f:todel) {
					f.delete();
				}
		}
	}


	private static int countAP(Property prop) {
		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(Collections.singletonList(prop));
		return atoms.getAtoms().size();
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
		List<File> todel = new ArrayList<>();
		try {
			File f1 = Files.createTempFile("autA", ".hoa").toFile();
			todel.add(f1);
			PrintWriter pw = new PrintWriter(f1);
			tgba.exportAsHOA(pw, ExportMode.SPOTAP);
			pw.close();
			
			File f2 = Files.createTempFile("autB", ".hoa").toFile();
			todel.add(f2);
			buildAutomaton(ltlprop, f2);			
			
			return makeProduct(f1, f2, tgba.getApm());
			
		} catch (IOException|TimeoutException|InterruptedException e) {
			System.out.println("Spot failed to build product :");
			e.printStackTrace();
			return tgba;
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}
	
	public TGBA computeForwardClosedSI(TGBA tgba) throws IOException {
		List<File> todel = new ArrayList<>();
		try {
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.addArg(pathToautstates);
			File curAut = Files.createTempFile("curaut", ".hoa").toFile();
			todel.add(curAut);
			PrintWriter pw = new PrintWriter(curAut);
			tgba.exportAsHOA(pw, ExportMode.SPOTAP);
			pw.close();
			cl.addArg(curAut.getCanonicalPath());

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File autPath = Files.createTempFile("fwclsi", ".hoa").toFile();
			todel.add(autPath);
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
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}
	
	public void computeInfStutter(TGBA tgba)  {
		List<File> todel = new ArrayList<>();
		try {

			int oldinit = tgba.getInitial();
			long time = System.currentTimeMillis();
			List<Expression> infStutter = new ArrayList<>();
			for (int state = 0; state < tgba.nbStates() ; state++) {
				// set initial state
				tgba.setInitial(state);

				try {
					// export resulting automaton, load result to grab (reduced) alphabet
					File autPath = Files.createTempFile("aut"+state, ".hoa").toFile();
					todel.add(autPath);
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
						todel.add(stutterAut);
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
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}


	private TGBA makeProduct(File autPath, File stutterAut, AtomicPropManager apm) throws TimeoutException, IOException, InterruptedException {
		List<File> todel = new ArrayList<>();
		try {
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.addArg(pathToautfilt);
			cl.addArg("--hoaf=tv"); // prefix notation for output
			// bigger is better, and spot likes it big !
			cl.addArg("--small");
			cl.addArg("-F");
			cl.addArg(autPath.getCanonicalPath());
			cl.addArg("--product-and="+ stutterAut.getCanonicalPath());

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File stdOutput = Files.createTempFile("prod", ".hoa").toFile();
			todel.add(stdOutput);
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
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}


	private boolean buildAutomaton(String ltl, File stdOutput) throws TimeoutException, IOException, InterruptedException {
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
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
		List<File> todel = new ArrayList<>();
		try {

			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.addArg(pathToautfilt);
			cl.addArg("--hoaf=tv"); // prefix notation for output
			cl.addArg("--small");
			File curAut = Files.createTempFile("curaut", ".hoa").toFile();
			todel.add(curAut);
			PrintWriter pw = new PrintWriter(curAut);
			tgba.exportAsHOA(pw, ExportMode.SPOTAP);
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
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}

	public boolean buildComplement (TGBA tgba, File compPath)  {
		List<File> todel = new ArrayList<>();
		try {
			CommandLine cl = new CommandLine();
			try {
				long time = System.currentTimeMillis();

				cl.addArg(pathToautfilt);

				cl.addArg("--hoaf=tv"); // force TGBA

				// pass TGBA in HOAF
				File curAut = Files.createTempFile("curaut", ".hoa").toFile();
				todel.add(curAut);
				PrintWriter pw = new PrintWriter(curAut);
				tgba.exportAsHOA(pw, ExportMode.SPOTAP);
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
				System.err.println("Error while executing :"+ cl);
				e.printStackTrace();
			}
			return false;
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}
	
	
	
	
	public boolean complementProductAndTestEmpty(TGBA tgba, Expression factoid) {
		List<File> todel = new ArrayList<>();
		try {
			CommandLine cl = new CommandLine();
			try {
				File comp = Files.createTempFile("comp", ".hoa").toFile();
				todel.add(comp);
				buildComplement(tgba, comp);

				String ltl = printLTLProperty(factoid);
				File fact = Files.createTempFile("fact", ".hoa").toFile();
				todel.add(fact);
				buildAutomaton(ltl, fact);

				long time = System.currentTimeMillis();

				cl.addArg(pathToautfilt);

				cl.addArg("--hoaf=tv"); // force TGBA

				// pass comp in HOAF
				cl.addArg("-F");
				cl.addArg(comp.getCanonicalPath());

				// please test inclusion
				cl.addArg("--included-in="+fact.getCanonicalPath());

				if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
				File resPath = Files.createTempFile("res", ".hoa").toFile();
				todel.add(resPath);
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
				System.err.println("Error while executing :"+ cl);
				e.printStackTrace();
			}
			return false;
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}

	public boolean isProductEmpty(File a1path, String ltl) {
		List<File> todel = new ArrayList<>();
		try {
			CommandLine cl = new CommandLine();
			try {
				long time = System.currentTimeMillis();		

				cl.addArg(pathToautfilt);
				cl.addArg("--hoaf=tv");

				cl.addArg("-F");
				cl.addArg(a1path.getCanonicalPath());

				File a2path = Files.createTempFile("a2", ".hoa").toFile();
				todel.add(a2path);
				cl.addArg("--product-and="+ a2path.getCanonicalPath());

				if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
				File stdOutput = Files.createTempFile("prod", ".hoa").toFile();
				todel.add(stdOutput);
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
				System.err.println("Error while executing :"+ cl);
				e.printStackTrace();
			}
			return false;
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}

	public void analyzeCLSL(TGBA tgba) throws IOException {
		List<File> todel = new ArrayList<>();
		try {

			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.addArg(pathToSenseCLSL);
			File curAut = Files.createTempFile("curaut", ".hoa").toFile();
			todel.add(curAut);
			PrintWriter pw = new PrintWriter(curAut);
			tgba.exportAsHOA(pw, ExportMode.SPOTAP);
			pw.close();
			cl.addArg(curAut.getCanonicalPath());

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File outPath = Files.createTempFile("outclsl", ".txt").toFile();
			todel.add(outPath);
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
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}

	}

	public TGBA buildTGBA(String ltl, AtomicPropManager apm) throws IOException, TimeoutException, InterruptedException {
		List<File> todel = new ArrayList<>();
		try {

			File f2 = Files.createTempFile("autLTL", ".hoa").toFile();
			todel.add(f2);
			buildAutomaton(ltl, f2);		

			return TGBAparserHOAF.parseFrom(f2.getCanonicalPath(), apm);
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}

	public TGBA givenThat(TGBA tgba, Expression factoid, GivenStrategy constrain) {
		return givenThat(tgba, Collections.singletonList(factoid), constrain);
	}
	public TGBA givenThat(TGBA tgba, List<Expression> factoids, GivenStrategy constrain) {
		ArrayList<String> strFactoids = new ArrayList<>();
		for (Expression factoid : factoids)
			strFactoids.add(printLTLProperty(factoid));
		return givenThat(tgba, strFactoids, constrain);
	}
	public TGBA givenThat(TGBA tgba, ArrayList<String> factoids, GivenStrategy constrain) {
		List<File> todel = new ArrayList<>();
		try {

			TGBA tgbaout = null;
			CommandLine cl = new CommandLine();
			try {
				long time = System.currentTimeMillis();

				cl.addArg(pathToautfilt);
				cl.addArg("--check=stutter");
				cl.addArg("--hoaf=tv"); // prefix notation for output
				cl.addArg("--tgba");
				cl.addArg("--small");
				for (String factoid : factoids)
					cl.addArg("--given-formula="+factoid);

				File curAut = Files.createTempFile("b4k", ".hoa").toFile();
				todel.add(curAut);
				PrintWriter pw = new PrintWriter(curAut);
				tgba.exportAsHOA(pw, ExportMode.SPOTAP);
				pw.close();
				cl.addArg("-F");
				cl.addArg(curAut.getCanonicalPath());	
				cl.addArg("--given-strategy="+constrain.toString());


				if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
				File stdOutput = Files.createTempFile("prod", ".hoa").toFile();
				todel.add(stdOutput);
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
				System.err.println("Error "+ e.getMessage());
				// e.printStackTrace();
			}
			return tgbaout;
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}

	}
	
	public String toBuchi(TGBA tgba, ExportMode forLTSMin) {
		List<File> todel = new ArrayList<>();
		try {
			CommandLine cl = new CommandLine();
			try {
				long time = System.currentTimeMillis();

				cl.addArg(pathToautfilt);

				// build automaton for tgba
				// pass TGBA in HOAF
				File curAut = Files.createTempFile("curaut", ".hoa").toFile();
				todel.add(curAut);
				PrintWriter pw = new PrintWriter(curAut);
				tgba.exportAsHOA(pw, forLTSMin);
				pw.close();

				// this is the flags that LTSmin likes
				cl.addArg("--buchi");
				cl.addArg("--state-based-acceptance");
				cl.addArg("--deterministic");

				cl.addArg(curAut.getCanonicalPath());

				if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
				File stdOutput = Files.createTempFile("stateBased", ".hoa").toFile();
				todel.add(stdOutput);
				int status = Runner.runTool(timeout, cl, stdOutput, true);
				if (status == 0 || status == 1) {
					if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());

					if (stdOutput.length() > 0) {
						return stdOutput.getCanonicalPath();
					}

				} else {
					System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
					try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
						stream.forEach(System.out::println);
					}
				}
			} catch (IOException | TimeoutException | InterruptedException e) {
				System.err.println("Error while executing :"+ cl);
				e.printStackTrace();
			}				
			return null;
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
	}

	
	public boolean isIncludedIn(Expression falseFact, TGBA tgba) {
		List<File> todel = new ArrayList<>();
		CommandLine cl = new CommandLine();
		try {
			long time = System.currentTimeMillis();
			
			cl.addArg(pathToautfilt);
			
			// build automaton for tgba
			// pass TGBA in HOAF
			File curAut = Files.createTempFile("curaut", ".hoa").toFile();
			todel.add(curAut);
			PrintWriter pw = new PrintWriter(curAut);
			tgba.exportAsHOA(pw, ExportMode.SPOTAP);
			pw.close();
			cl.addArg("--included-in="+curAut.getCanonicalPath());
			
			// build and pass aut for false factoid
			File falseAut = Files.createTempFile("ffact", ".hoa").toFile();
			todel.add(falseAut);
			String ltlFalseFact = printLTLProperty(falseFact);
			if (! buildAutomaton(ltlFalseFact,falseAut)) {
				return false;
			}
			
			cl.addArg(falseAut.getCanonicalPath());

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File stdOutput = Files.createTempFile("isImply", ".out").toFile();
			todel.add(stdOutput);
			int status = Runner.runTool(timeout, cl, stdOutput, true);
			if (status == 0 || status == 1) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());
				
				if (stdOutput.length() > 0) {
					return true;
				} else {
					return false;
				}
				
			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}
		} catch (IOException | TimeoutException | InterruptedException e) {
			System.err.println("Error while executing :"+ cl);
			e.printStackTrace();
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}				
		return false;
	}


	public boolean isImpliedBy(Expression main, Expression implicant) {
		List<File> todel = new ArrayList<>();
		CommandLine cl = new CommandLine();
		try {
			long time = System.currentTimeMillis();
			
			cl.addArg(pathToltlfilt);
			
			cl.addArg("--implied-by="+printLTLProperty(implicant));
			cl.addArg("-f");
			cl.addArg(printLTLProperty(main));

			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File stdOutput = Files.createTempFile("isImply", ".out").toFile();
			todel.add(stdOutput);
			int status = Runner.runTool(timeout, cl, stdOutput, true);
			if (status == 0 || status == 1) {
				if (DEBUG >= 1) System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput.getCanonicalPath());
				
				if (stdOutput.length() > 0) {
					return true;
				} else {
					return false;
				}
				
			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(stdOutput.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
			}
		} catch (IOException | TimeoutException | InterruptedException e) {
			System.err.println("Error while executing :"+ cl);
			e.printStackTrace();
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
		return false;
	}

	/**
	 * Returns some stats on the argument TGBA, in this order :
	 * %c, number of SCC
	 * %[a]c, accepting SCC
	 * %[r]c, rejecting SCC
	 * %[v]c, trivial SCC
	 * %t, number of transition (with True = 2^AP)
	 * %n, number of non deterministic states
	 * @param tgba
	 * @return
	 */
	public List<Integer> computeStats(TGBA tgba) {
		List<File> todel = new ArrayList<>();
		try {

			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.addArg(pathToautfilt);
			File curAut = Files.createTempFile("curaut", ".hoa").toFile();
			todel.add(curAut);
			PrintWriter pw = new PrintWriter(curAut);
			tgba.exportAsHOA(pw, ExportMode.SPOTAP);
			pw.close();
			cl.addArg(curAut.getCanonicalPath());
			
			cl.addArg("--stats");
			cl.addArg("%c,%[a]c,%[r]c,%[v]c,%t,%n");
			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			File outPath = Files.createTempFile("outstats", ".txt").toFile();
			todel.add(outPath);
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
				String line = br.readLine();
				br.close();
				
				return  Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());

			} else {
				System.out.println("Spot run failed in " + (System.currentTimeMillis() - time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(outPath.getCanonicalPath()))) {
					stream.forEach(System.out::println);
				}
				throw new IOException();
			}
		} catch (IOException e) {
			// set -1
		} finally {
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
		return Collections.emptyList();
	}

	public boolean testNegativeKnowledge(ArrayList<String> falseKnowledge, String knowledgeAndPhi) {
		List<File> todel = new ArrayList<>();
		long time = System.currentTimeMillis();
		boolean result = false;
		try {
			File curAut = Files.createTempFile("aut", ".hoa").toFile();
			todel.add(curAut);
			
			if (! buildAutomaton(knowledgeAndPhi, curAut)) {
				return false;
			}
			
			File falseFactFile = Files.createTempFile("falseFact", ".hoa").toFile();
			todel.add(falseFactFile);
			
			CommandLine cl = new CommandLine();
			cl.addArg(pathToautfilt);
			cl.addArg("-F");
			cl.addArg(curAut.getCanonicalPath());

			cl.addArg("--product-and="+falseFactFile.getCanonicalPath());
			
			cl.addArg("--is-empty");
			
			File outPath = Files.createTempFile("out", ".hoa").toFile();
			todel.add(outPath);
			
			if (DEBUG >= 1) System.out.println("Running Spot : " + cl);
			
			for (String falseFact : falseKnowledge) {
				long loopTime = System.currentTimeMillis();
				if (! buildAutomaton(falseFact, falseFactFile)) {
					return false;
				}
								
				int status = 1;
				try {
					status = Runner.runTool(timeout, cl, outPath, true);
				} catch (IOException | TimeoutException | InterruptedException e) {
					throw new IOException(e);
				}
				/*From autfilt --help :
				Exit status:
					  0  if some automata were output
					  1  if no automata were output (no match)
					  2  if any error has been reported
				*/
				if (status == 0 || status == 1) {
					if (DEBUG >= 1) System.out.println("Successful run of Spot took " + (System.currentTimeMillis() - loopTime) + " ms captured in "
							+ outPath.getCanonicalPath());
					if (status == 0) {
						
						if (DEBUG >= 1) {
							System.out.println("Concluded with negative knowledge : "+falseFact);
						}
						result=true;
						return true;
					}					
				} else {
					System.out.println("Spot run failed in " + (System.currentTimeMillis() - time) + " ms. Status :" + status);
					try (Stream<String> stream = Files.lines(Paths.get(outPath.getCanonicalPath()))) {
						stream.forEach(System.out::println);
					}
					throw new IOException();
				}
				
			}
			
		} catch (IOException|InterruptedException|TimeoutException e) {
			// set -1
			
		} finally {
			if (DEBUG >= 1) {
				System.out.println("Complete false knowledge loop with " + falseKnowledge.size() + " negative factoids took "+ (System.currentTimeMillis() - time) + " ms and concluded "+result);
			}
			
			if (DEBUG == 0)
				for (File f : todel) {
					f.delete();
				}
		}
		return false;
	}

}
