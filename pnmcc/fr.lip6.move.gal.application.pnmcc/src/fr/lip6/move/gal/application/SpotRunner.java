package fr.lip6.move.gal.application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBAEdge;
import fr.lip6.ltl.tgba.io.TGBAparserHOAF;
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


	private static final int DEBUG = 0;
	private String pathToltlfilt;
	private String pathToltl2tgba;
	private String pathToautfilt;
	private String workFolder;
	private long timeout;

	public SpotRunner(String pathToExe, String workFolder, long timeout) {
		super();
		this.pathToltlfilt = pathToExe;
		this.pathToltl2tgba = pathToExe.replace("ltlfilt", "ltl2tgba");
		this.pathToautfilt = pathToExe.replace("ltlfilt", "autfilt");
		this.workFolder = workFolder;
		this.timeout = timeout;
	}


	public Map<String,TGBA> loadTGBA (PetriNet net) throws TimeoutException {
		Map<String,TGBA> automata = new HashMap<>();
		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(net.getProperties());
		try {
			long time = System.currentTimeMillis();

			for (Property prop : net.getProperties()) {
				CommandLine cl = new CommandLine();
				cl.setWorkingDir(new File(workFolder));
				cl.addArg(pathToltl2tgba);
				cl.addArg("--hoaf=tv"); // prefix notation for output
				if (prop.getType() == PropertyType.LTL) {
					cl.addArg("-f"); // formula in next argument
					cl.addArg("!(" +printLTLProperty(pmap.get(prop.getName()), atoms)+ ")");
				} else {
					continue;
				}
				System.out.println("Running Spot : " + cl);
				String stdOutput = workFolder + "/spotaut.txt";
				int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
				if (status == 0) {
					System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);
					TGBA tgba = TGBAparserHOAF.parseFrom(stdOutput, atoms);
					automata.put(prop.getName(), tgba);
					System.out.println("Resulting TGBA : "+ tgba.toString());
				} else {
					System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
					try (Stream<String> stream = Files.lines(Paths.get(stdOutput))) {
						stream.forEach(System.out::println);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return automata;
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
					cl.addArg(printLTLProperty(atoms.getAPformula(prop.getName()), atoms));
					seen++;
				}
			}
			if (seen == 0) return;
			System.out.println("Running Spot : " + cl);
			String stdOutput = workFolder + "/spotrun.txt";
			int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
			if (status == 0) {
				System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);
				BufferedReader reader = new BufferedReader(new FileReader(stdOutput));
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
						sb.append(printLTLProperty(prop.getBody(), atoms)).append(",");
					}
					System.out.println("Resulting properties : "+ sb.toString());
				}
			} else {
				System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
				try (Stream<String> stream = Files.lines(Paths.get(stdOutput))) {
					stream.forEach(System.out::println);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static String printLTLProperty(Expression prop, AtomicPropManager atoms) {
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
		for (Entry<String, TGBA> entry:tgbas.entrySet()) {
			System.out.println("Found automata for " + entry.getKey() + " : " + entry.getValue());
		}
		
		try {
			for (Entry<String, TGBA> entry:tgbas.entrySet()) {
				TGBA tgba = entry.getValue();
				int oldinit = tgba.getInitial();

				List<Expression> infStutter = new ArrayList<>();
				for (int state = 0; state < tgba.getEdges().size() ; state++) {
					// set initial state
					tgba.setInitial(state);

					// export resulting automaton, load result to grab (reduced) alphabet
					String autPath = "aut"+state+".hoa";
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
						String stutterAut ="stutter.hoa";
						if (! buildAutomaton(ltl,stutterAut)) {
							break;
						}

						// finally make a product of these two
						TGBA prod = makeProduct(autPath,stutterAut,tgbaSimp);

						// explore the product
						List<Expression> st = new ArrayList<>();
						for (TGBAEdge arc : prod.getEdges().get(prod.getInitial())) {
							st.add(arc.getCondition());
						}
						Expression fst = Simplifier.simplifyBoolean(Expression.nop(Op.OR,st));
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
				}
				System.out.println("Stuttering aceptance :" + infStutter);
				tgba.setInfStutterConditions(infStutter);
				tgba.setInitial(oldinit);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return tgbas;
	}


	private TGBA makeProduct(String a1path, String a2path, TGBA tgba) throws TimeoutException, IOException, InterruptedException {
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToautfilt);
		cl.addArg("--hoaf=tv"); // prefix notation for output
		cl.addArg("--small");
		cl.addArg("-F");
		cl.addArg(a1path);
		cl.addArg("--product-and="+ a2path);

		System.out.println("Running Spot : " + cl);
		String stdOutput = workFolder + "/" + "prod.hoa";
		int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
		if (status == 0) {
			System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);

			TGBA tgbaout = TGBAparserHOAF.parseFrom(stdOutput, tgba.getApm());

			if (DEBUG >= 1) System.out.println("Resulting TGBA : "+ tgbaout.toString());
			return tgbaout;
		} else {
			System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(stdOutput))) {
				stream.forEach(System.out::println);
			}
		}
		return null;
	}


	private boolean buildAutomaton(String ltl, String path) throws TimeoutException, IOException, InterruptedException {
		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToltl2tgba);
		cl.addArg("--hoaf=tv"); // prefix notation for output
		cl.addArg("-f"); // formula in next argument
		cl.addArg(ltl);

		System.out.println("Running Spot : " + cl);
		String stdOutput = workFolder + "/" + path;
		int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
		if (status == 0) {
			System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);
			return true;
		} else {
			System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(stdOutput))) {
				stream.forEach(System.out::println);
			}
			return false;
		}

	}

	private TGBA simplify(TGBA tgba, String autPath) throws IOException, TimeoutException, InterruptedException {

		long time = System.currentTimeMillis();
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToautfilt);
		cl.addArg("--hoaf=tv"); // prefix notation for output
		cl.addArg("--small");
		String curAut = workFolder + "/curaut.hoa";
		PrintWriter pw = new PrintWriter(new File(curAut));
		tgba.exportAsHOA(pw);
		pw.close();
		cl.addArg("-F");
		cl.addArg(curAut);

		System.out.println("Running Spot : " + cl);
		String stdOutput = workFolder + "/" + autPath;
		int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
		if (status == 0) {
			System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);

			TGBA tgbaout = TGBAparserHOAF.parseFrom(stdOutput, tgba.getApm());

			System.out.println("Resulting TGBA : "+ tgbaout.toString());
			return tgbaout;
		} else {
			System.out.println("Spot run failed in "+ (System.currentTimeMillis() -time) + " ms. Status :" + status);
			try (Stream<String> stream = Files.lines(Paths.get(stdOutput))) {
				stream.forEach(System.out::println);
			}
		}
		return tgba;
	}
}
