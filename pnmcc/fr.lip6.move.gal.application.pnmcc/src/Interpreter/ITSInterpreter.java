package Interpreter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.application.MccTranslator;
import fr.lip6.move.gal.itscl.modele.Listener;

public class ITSInterpreter extends Listener implements Callable<Boolean> {

	// private Map<String, List<Property>> boundProps;
	private String examination;
	private boolean withStructure;
	private MccTranslator reader;
	private Set<String> seen;
	private Set<String> todoProps;

	public ITSInterpreter(String examination, boolean withStructure, MccTranslator reader, Set<String> doneProps,
			Set<String> todoProps, int pipeSize) {
		super(pipeSize);
		this.examination = examination;
		this.withStructure = withStructure;
		this.reader = reader;
		this.seen = doneProps;
		this.todoProps = todoProps;
	}

	public Boolean call() throws Exception {
		try {
			for (String line = ""; line != null; line = in.readLine()) {
				System.out.println(line);
				// stdOutput.toString().split("\\r?\\n")) ;
				if (line.matches("Max variable value.*")) {
					if (examination.equals("StateSpace")) {
						System.out.println("STATE_SPACE MAX_TOKEN_IN_PLACE " + line.split(":")[1]
								+ " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure ? "USE_NUPN" : ""));
					}
				}
				if (line.matches("Maximum sum along a path.*")) {
					if (examination.equals("StateSpace")) {
						int nbtok = Integer.parseInt(line.split(":")[1].replaceAll("\\s", ""));
						nbtok += reader.countMissingTokens();
						System.out.println("STATE_SPACE MAX_TOKEN_PER_MARKING " + nbtok
								+ " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure ? "USE_NUPN" : ""));
					}
				}
				if (line.matches("Exact state count.*")) {
					if (examination.equals("StateSpace")) {
						System.out.println("STATE_SPACE STATES " + line.split(":")[1]
								+ " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure ? "USE_NUPN" : ""));
					}
				}
				if (line.matches("Total edges in reachability graph.*")) {
					if (examination.equals("StateSpace")) {
						System.out.println("STATE_SPACE UNIQUE_TRANSITIONS " + line.split(":")[1]
								+ " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure ? "USE_NUPN" : ""));
					}
				}
				if (line.matches("System contains.*deadlocks.*")) {
					if (examination.equals("ReachabilityDeadlock")) {

						Property dead = reader.getSpec().getProperties().get(0);
						String pname = dead.getName();
						double nbdead = Double.parseDouble(line.split("\\s+")[2]);
						String res;
						if (nbdead == 0)
							res = "FALSE";
						else
							res = "TRUE";
						System.out.println("FORMULA " + pname + " " + res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL "
								+ (withStructure ? "USE_NUPN" : ""));
						seen.add(pname);
					}
				}
				if (line.matches("Bounds property.*")) {
					if (examination.contains("Bounds")) {
						String[] words = line.split(" ");
						String pname = words[2];
						String[] tab = line.split("<=");

						String sbound = tab[2].replaceAll("\\s", "");

						int bound = Integer.parseInt(sbound);
						Property target = null;
						for (Property prop : reader.getSpec().getProperties()) {
							if (prop.getName().equals(pname)) {
								target = prop;
								break;
							}
						}
						int toadd = 0;
						for (TreeIterator<EObject> it = target.eAllContents(); it.hasNext();) {
							EObject obj = it.next();
							if (obj instanceof Constant) {
								Constant cte = (Constant) obj;
								toadd += cte.getValue();
							} else if (obj instanceof Reference) {
								it.prune();
							}
						}
						seen.add(pname);
						System.out.println("FORMULA " + pname + " " + (bound + toadd)
								+ " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure ? "USE_NUPN" : ""));
					}
				}
				if (examination.startsWith("CTL")) {
					if (line.matches(".*formula \\d+,\\d+,.*")) {
						String[] tab = line.split(",");
						int formindex = Integer.parseInt(tab[0].split(" ")[1]);
						int verdict = Integer.parseInt(tab[1]);
						String res;
						if (verdict == 0)
							res = "FALSE";
						else
							res = "TRUE";
						String pname = reader.getSpec().getProperties().get(formindex).getName();
						System.out.println("FORMULA " + pname + " " + res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL "
								+ (withStructure ? "USE_NUPN" : ""));
						seen.add(pname);
					}
				}
				if (examination.startsWith("LTL")) {
					if (line.matches("Formula \\d+ is .*")) {
						String[] tab = line.split(" ");
						int formindex = Integer.parseInt(tab[1]);
						String res = tab[3];
						String pname = reader.getSpec().getProperties().get(formindex).getName();
						System.out.println("FORMULA " + pname + " " + res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL "
								+ (withStructure ? "USE_NUPN" : ""));
						seen.add(pname);
					}
				}

				if (line.matches(".*-" + examination + "-\\d+.*")) {
					// System.out.println(line);
					String res;
					if (line.matches(".*property.*") && !line.contains("Bounds")) {
						String pname = line.split(" ")[2];
						if (line.contains("does not hold")) {
							res = "FALSE";
						} else if (line.contains("No reachable states")) {
							res = "FALSE";
							pname = line.split(":")[1];
						} else {
							res = "TRUE";
						}
						pname = pname.replaceAll("\\s", "");
						if (!seen.contains(pname)) {
							System.out.println("FORMULA " + pname + " " + res
									+ " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL COLLATERAL_PROCESSING "
									+ (withStructure ? "USE_NUPN" : ""));
							seen.add(pname);
						}
					}
				}
			}
			in.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		closePinPout();

		if (seen.containsAll(todoProps)) {
			return true;
		}
		return false;
	}

}
