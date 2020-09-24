package fr.lip6.move.gal.application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.TimeoutException;

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
			System.out.println("Run of Spot took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);
			if (status == 0) {
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
		SpotPropertyPrinter pp = new SpotPropertyPrinter(pw, "src", atoms.getAtomMap());
		prop.accept(pp);
		pw.close();
		return baos.toString();
	}
	
	private static class SpotPropertyPrinter extends CExpressionPrinter {

		private Map<Expression, AtomicProp> atomMap;

		public SpotPropertyPrinter(PrintWriter pw, String prefix, Map<Expression, AtomicProp> atomMap) {
			super(pw, prefix);
			this.atomMap = atomMap;
		}

		private boolean testAtom(Expression e) {
			AtomicProp atom = atomMap.get(e);
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
				super.visit(naryOp);
			}
			return null;
		}

	}
}
