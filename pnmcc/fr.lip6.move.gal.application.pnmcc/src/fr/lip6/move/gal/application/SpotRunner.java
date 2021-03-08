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
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.io.TGBAparserHOAF;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.CExpressionPrinter;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.PrefixParser;

public class SpotRunner {


	private String pathToExe;
	private String workFolder;
	private long timeout;

	public SpotRunner(String pathToExe, String workFolder, long timeout) {
		super();
		this.pathToExe = pathToExe;
		this.workFolder = workFolder;
		this.timeout = timeout;
	}


	public Map<String,TGBA> loadTGBA (PetriNet net) throws TimeoutException {
		Map<String,TGBA> automata = new HashMap<>();
		AtomicPropManager atoms = new AtomicPropManager();
		atoms.loadAtomicProps(net.getProperties());
		try {
			long time = System.currentTimeMillis();
			
			for (Property prop : net.getProperties()) {
				CommandLine cl = new CommandLine();
				cl.setWorkingDir(new File(workFolder));
				cl.addArg(pathToExe);
				cl.addArg("--hoaf=tv"); // prefix notation for output
				if (prop.getType() == PropertyType.LTL) {
					cl.addArg("-f"); // formula in next argument
					cl.addArg(printLTLProperty(prop.getBody(), atoms));
				} else {
					continue;
				}
				System.out.println("Running Spot : " + cl);
				String stdOutput = workFolder + "/spotaut.txt";
				int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
				if (status == 0) {
					System.out.println("Successful run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);
					TGBA tgba = TGBAparserHOAF.parseFrom(stdOutput, atoms.getAtoms());
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
		atoms.loadAtomicProps(net.getProperties());
		try {
			long time = System.currentTimeMillis();
			CommandLine cl = new CommandLine();
			cl.setWorkingDir(new File(workFolder));
			cl.addArg(pathToExe);
			cl.addArg("--lbt"); // prefix notation for output
			cl.addArg("-r"); // reduce the formulas
			cl.addArg("--unabbreviate=eiMRW^"); // reduce the formulas			
			int seen = 0;
			for (Property prop : net.getProperties()) {
				if (prop.getType() == PropertyType.LTL) {
					cl.addArg("-f"); // formula in next argument
					cl.addArg(printLTLProperty(prop.getBody(), atoms));
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
						Expression res = PrefixParser.parsePrefix(line, atoms.getAtoms());
						prop.setBody(res);
					}
				}						
				reader.close();
				StringBuilder sb = new StringBuilder();
				for (Property prop : net.getProperties()) {
					sb.append(printLTLProperty(prop.getBody(), atoms)).append(",");
				}
				System.out.println("Resulting properties : "+ sb.toString());
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
		SpotPropertyPrinter pp = new SpotPropertyPrinter(pw, "src", atoms);
		prop.accept(pp);
		pw.close();
		return baos.toString();
	}
	
	public static void exportLTLProperties (PetriNet net, String prefix, String workFolder) throws IOException {
		String stdOutput = workFolder + "/"+prefix+".ltl";
		PrintWriter pw = new PrintWriter(new File(stdOutput));
		
		AtomicPropManager atoms = new AtomicPropManager();
		atoms.loadAtomicProps(net.getProperties());
		SpotPropertyPrinter pp = new SpotPropertyPrinter(pw, "src", atoms);
		for (Property p : net.getProperties()) {
			if (p.getType() == PropertyType.LTL) {
				p.getBody().accept(pp);
				pw.println();
			}
		}
		pw.close();		
	}
	
	private static class SpotPropertyPrinter extends CExpressionPrinter {

		private AtomicPropManager atomMap;

		public SpotPropertyPrinter(PrintWriter pw, String prefix, AtomicPropManager atomMap) {
			super(pw, prefix);
			this.atomMap = atomMap;
		}

		private boolean testAtom(Expression e) {
			AtomicProp atom = atomMap.getAtomMap().get(e);
			if (atom != null) {
				pw.print( atom.getName() );
				return true;
			}
			return false;
		}

		@Override
		public Void visit(BinOp binOp) {
			if (!testAtom(binOp)) {
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
			}
			return null;
		}

		@Override
		public Void visit(NaryOp naryOp) {
			if (!testAtom(naryOp)) {
				if ( (naryOp.getOp() == Op.OR || naryOp.getOp() == Op.AND) && naryOp.nbChildren() > 2) {
					List<Expression> subAtoms = new ArrayList<>();
					List<Expression> others = new ArrayList<>();
					for (int cid=0, cide=naryOp.nbChildren() ; cid < cide ; cid++) {
						Expression child = naryOp.childAt(cid);
						if (atomMap.getAtomMap().get(child) != null) {
							subAtoms.add(child);
						} else {
							others.add(child);
						}
					}
					if (subAtoms.size() > 2) {
						// build a new atom for these children, we might make too many AP for spot otherwise
						Expression newAtom = Expression.nop(naryOp.getOp(), subAtoms);
						AtomicProp ap = atomMap.registerExpression(newAtom);
						
						String symbol = null;
						if (naryOp.getOp() == Op.AND) {
							symbol = "&&";
						} else {
							symbol = "||";
						}
						
						pw.append("(");
						for (Expression child : others) {
							child.accept(this);
							pw.append(symbol);
						}
						pw.append(ap.getName());
						pw.append(")");
						return null;
					}
				}				
				super.visit(naryOp);
			}
			return null;
		}

	}
}
