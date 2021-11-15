package fr.lip6.move.gal.application.solver.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import android.util.SparseIntArray;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Simplifier;
import fr.lip6.move.gal.structural.smt.DeadlockTester;

public class AtomicReducerSR {
	private static final int DEBUG = 0;

	public int strongReductions(String solverPath, MccTranslator reader, DoneProperties doneProps) {
		if (reader.getExamination().contains("LTL") || reader.getExamination().contains("CTL")) {
			return checkAtomicPropositionsLogic(reader.getSPN(), doneProps, solverPath);
		} else {
			int solved = checkAtomicPropositions(reader.getSPN(), doneProps, solverPath, true);
			solved += checkAtomicPropositions(reader.getSPN(), doneProps, solverPath, false);
			reader.getSPN().simplifyLogic();
			return solved;
		}
	}

	/**
	 * Extract Atomic Propositions, unify them, for each of them test initial state, then try to assert it will not vary => simplifiable.
	 * @param spn
	 * @param doneProps
	 * @param isSafe
	 * @param solverPath 
	 * @param comparisonAtoms if true look only at comparisons only as atoms (single predicate), otherwise sub boolean formulas are considered atoms (CTL, LTL) 
	 */
	private int checkAtomicPropositionsLogic (SparsePetriNet spn, DoneProperties doneProps, String solverPath) {

		if (solverPath == null) {
			return 0;
		}

		// order is not really important, but reproducibility of iteration order we depend upon
		AtomicPropManager apm = new AtomicPropManager();
		Map<String, Expression> pmap = apm.loadAtomicProps(spn.getProperties());

		// build a list of invariants to test with SMT/random
		// for each of them test value in initial state
		List<Expression> tocheck = new ArrayList<>();

		SparseIntArray istate = new SparseIntArray(spn.getMarks());
		for (AtomicProp ent: apm.getAtoms()) {
			Expression cmp = ent.getExpression();
			int val = cmp.eval(istate);
			if (val == 1) {
				// UNSAT => it never becomes false
				tocheck.add(Expression.not(cmp));
			} else {
				// UNSAT => it never becomes true
				tocheck.add(cmp);
			}			
		}

		List<Integer> tocheckIndexes = new ArrayList<>();
		for (int j=0; j < tocheck.size(); j++) { tocheckIndexes.add(j);}

		RandomExplorer re = new RandomExplorer(spn);
		int steps = 100000; // 100k steps
		int timeout = 30; // 30 secs
		int[] verdicts = re.runRandomReachabilityDetection(steps,tocheck,timeout,-1);
		for (int v = verdicts.length-1 ; v >= 0 ; v--) {
			if (verdicts[v] != 0) {
				// well, this is not an invariant, too bad
				tocheck.remove(v);
				tocheckIndexes.remove(v);
			}
		}

		if (tocheck.isEmpty()) {
			return 0;
		}		


		int nsolved = 0;
		int nsimpl = 0;
		List<Integer> repr = new ArrayList<>();
		List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, spn, solverPath, repr,20,false);

		Iterator<AtomicProp> it = apm.getAtoms().iterator();
		int vi=0;
		int cur=0;
		while (it.hasNext()) {
			AtomicProp ent = it.next();
			int vind = tocheckIndexes.get(cur);
			if (vi == vind) {
				SparseIntArray parikh = paths.get(cur);
				if (parikh == null) {
					// cool we've proven an invariant, we can substitute
					Expression c = ent.getExpression();
					boolean val = c.eval(istate) == 1;
					if (DEBUG >= 1) System.out.println("SMT proved "+tocheck.get(cur) + " is unreachable; concluding " + c + " is always " + val);						

					if (val)
						ent.setExpression(Expression.constant(true));
					else
						ent.setExpression(Expression.constant(false));

					nsolved ++;
				}
				cur++;
				if (cur >= tocheckIndexes.size()) {
					break;
				}
			}				
			vi++;
		}

		if (nsolved > 0) {
			for (Property prop : spn.getProperties()) {
				Expression nbody = AtomicPropManager.rewriteWithoutAP(pmap.get(prop.getName()));
				if (prop.getBody() != nbody) {
					nsimpl++;
					prop.setBody(nbody);
				}				
			}
			spn.simplifyLogic();
			System.out.println("Successfully simplified "+nsolved+" atomic propositions for a total of " + nsimpl +" simplifications.");
			if (DEBUG >= 1)  {
				int pid = 0;
				for (Property prop : spn.getProperties()) {
					System.out.println("p" + pid++ + ":" + prop);
				}
			}
		}
		return nsolved;
	}


	/**
	 * Extract Atomic Propositions, unify them, for each of them test initial state, then try to assert it will not vary => simplifiable.
	 * @param spn
	 * @param doneProps
	 * @param isSafe
	 * @param solverPath 
	 * @param comparisonAtoms if true look only at comparisons only as atoms (single predicate), otherwise sub boolean formulas are considered atoms (CTL, LTL) 
	 */
	private int checkAtomicPropositions(SparsePetriNet spn, DoneProperties doneProps, String solverPath, boolean comparisonAtoms) {

		if (solverPath == null) {
			return 0;
		}

		// order is not really important, but reproducibility of iteration order we depend upon
		Map<String,List<Expression>> atoms = new LinkedHashMap<>();				
		// collect all atomic predicates in the property
		int pid = 0;
		for (Property prop : spn.getProperties()) {
			if (DEBUG >= 1) System.out.println("p" + pid++ + ":" + prop);
			extractAtoms(prop.getBody(), atoms, comparisonAtoms);	
		}

		if (comparisonAtoms) {
			return 0 ;
		}
		// build a list of invariants to test with SMT/random
		// for each of them test value in initial state
		List<Expression> tocheck = new ArrayList<>();
		List<Boolean> initialValues = new ArrayList<>();

		SparseIntArray istate = new SparseIntArray(spn.getMarks());
		for (Entry<String, List<Expression>> ent:atoms.entrySet()) {
			Expression cmp = ent.getValue().get(0); // never empty by cstr
			int val = cmp.eval(istate);
			initialValues.add(val == 1);
			if (val == 1) {
				// UNSAT => it never becomes false
				tocheck.add(Expression.not(cmp));
			} else {
				// UNSAT => it never becomes true
				tocheck.add(cmp);
			}			
		}

		List<Integer> tocheckIndexes = new ArrayList<>();
		for (int j=0; j < tocheck.size(); j++) { tocheckIndexes.add(j);}

		RandomExplorer re = new RandomExplorer(spn);
		int steps = 100000; // 100k steps
		int timeout = 30; // 30 secs
		int[] verdicts = re.runRandomReachabilityDetection(steps,tocheck,timeout,-1);
		for (int v = verdicts.length-1 ; v >= 0 ; v--) {
			if (verdicts[v] != 0) {
				// well, this is not an invariant, too bad
				tocheck.remove(v);
				tocheckIndexes.remove(v);
			}
		}

		if (tocheck.isEmpty()) {
			return 0;
		}		


		try {
			int nsolved = 0;
			int nsimpl = 0;
			List<Integer> repr = new ArrayList<>();
			List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, spn, solverPath, repr,20,false);

			IdentityHashMap<Expression,Expression> mapTo = new IdentityHashMap<>();
			Iterator<Entry<String, List<Expression>>> it = atoms.entrySet().iterator();
			int vi=0;
			int cur=0;
			while (it.hasNext()) {
				Entry<String, List<Expression>> ent = it.next();
				int vind = tocheckIndexes.get(cur);
				if (vi == vind) {
					SparseIntArray parikh = paths.get(cur);
					if (parikh == null) {
						// cool we've proven an invariant, we can substitute
						Expression c = ent.getValue().get(0); // never empty by cstr
						boolean val = c.eval(istate) == 1;
						if (DEBUG >= 1) System.out.println("SMT proved "+tocheck.get(cur) + " is unreachable; concluding " + c + " is always " + val);						
						for (Expression cmp : ent.getValue()) {
							nsimpl++;
							if (val)
								mapTo.put(cmp, Expression.constant(true));
							else
								mapTo.put(cmp, Expression.constant(false));
						}
						nsolved ++;
					}
					cur++;
					if (cur >= tocheckIndexes.size()) {
						break;
					}
				}				
				vi++;
			}

			if (nsolved > 0) {
				for (Property prop : spn.getProperties()) {
					prop.setBody(Expression.replaceSubExpressions(prop.getBody(), mapTo));
				}
				spn.simplifyLogic();
				System.out.println("Successfully simplified "+nsolved+" atomic propositions for a total of " + nsimpl +" simplifications.");
				if (DEBUG >= 1)  {
					pid = 0;
					for (Property prop : spn.getProperties()) {
						System.out.println("p" + pid++ + ":" + prop);
					}
				}
			}
			return nsolved;
		} catch (Exception e) {
			// TODO : this is no longer true with new client isolation API the exception should have been caught before this.
			// in particular java.lang.ArithmeticException
			// at uniol.apt.analysis.invariants.InvariantCalculator.test1b2(InvariantCalculator.java:448)
			// can occur here.
			System.out.println("Failed to apply SMT based 'atomic proposition is an invariant' test, skipping this step." +e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}



	public ISparsePetriNet testAndRewriteBoundedComparison(Map<String, List<Expression>> atoms, 
			SparsePetriNet spec, String solverPath, boolean isSafe) {
		// try for each comparison to assert one of the terms at least is one bounded
		List<Expression> tocheckBounds = new ArrayList<>();
		List<Entry<String, List<Expression>>> totreat = new ArrayList<>(); 
		for (Entry<String, List<Expression>> ent:atoms.entrySet()) {
			BinOp cmp = (BinOp) ent.getValue().get(0); // never empty by cstr
			if (! (cmp.left.getOp() == Op.CONST || cmp.right.getOp() == Op.CONST)) {
				Expression be = Expression.op(Op.GT, cmp.left, Expression.constant(1));
				tocheckBounds.add(be);
				be = Expression.op(Op.GT, cmp.right, Expression.constant(1));
				tocheckBounds.add(be);
				totreat.add(ent);
			}
		}

		// to build a random explorer and fast SMT checker
		// we do not apply reductions here to be context free.
		StructuralReduction sr = new StructuralReduction(spec);

		if (! tocheckBounds.isEmpty()) {
			try {
				int nsolved = 0;
				int nsimpl = 0;
				List<Integer> repr = new ArrayList<>();
				Set<String> treated = new HashSet<>();
				List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheckBounds, sr, solverPath, repr,20,false);
				if (DEBUG  >=1) {
					for (int i=0; i < paths.size(); i++) {
						if (paths.get(i)==null) {
							System.out.println("Proved "+tocheckBounds.get(i)+ " unreachable.");
						}
					}
				}
				IdentityHashMap<Expression, Expression> mapTo = new IdentityHashMap<>();
				for (int v=0; v < paths.size()-1; v+=2) {
					int vind = v / 2;
					Entry<String, List<Expression>> ent = totreat.get(vind);
					boolean isLeftBounded = paths.get(v) == null;
					boolean isRightBounded = paths.get(v+1) == null;
					if (isLeftBounded || isRightBounded) {
						// drop these guys out
						treated.add(ent.getKey());
						nsolved++;

						// cool we've proven an invariant  lhs <= 1 or rhs <= 1, we can substitute equality test with one safe rule						
						//  a==b   <=> a==0 & b==0  | a==1 & b==1
						// a < b <=>  b>1 |   a==0 & b==1 

						// this will hold the new expression
						Expression eqtest = createBoundedComparison(isLeftBounded, isRightBounded, (BinOp) ent.getValue().get(0));

						if (DEBUG  >=1) 
							System.out.println("isLB/isRB:" + isLeftBounded + "/" + isRightBounded +" After replacing "+ ent.getValue().get(0)+" by "+ eqtest);

						extractAtoms(eqtest, atoms,true);

						for (ListIterator<Expression> it = ent.getValue().listIterator() ; it.hasNext(); ) {
							Expression cmp = it.next();
							Expression img = eqtest;
							mapTo.put(cmp, img);
							nsimpl++;
						}						
					}
				}


				if (! treated.isEmpty()) {
					for (Property prop : spec.getProperties()) {
						prop.setBody(Expression.replaceSubExpressions(prop.getBody(), mapTo));
					}

					System.out.println("Successfully simplified "+nsolved+" atomic comparison propositions using bounds test for a total of " + nsimpl +" simplifications.");
					//					if (DEBUG >= 1) {
					//						pid = 0;
					//						for (Property prop : spec.getProperties()) {
					//							System.out.println("p" + pid++ + ":" + SerializationUtil.getText(prop, true));
					//						}
					//					}
				}
			} catch (Exception e) {
				// in particular java.lang.ArithmeticException
				// at uniol.apt.analysis.invariants.InvariantCalculator.test1b2(InvariantCalculator.java:448)
				// can occur here.
				System.out.println("Failed to apply SMT based 'comparison of one bounded terms' test, skipping this step." );
				e.printStackTrace();
			}
		}
		return sr;
	}

	public Void extractAtoms(Expression expr, Map<String, List<Expression>> atoms, boolean comparisonAtoms) {
		if (expr == null) {
			return null;
		} else {
			switch (expr.getOp()) {
			case BOOLCONST :
				return null;
			case GT : case GEQ : case EQ : case NEQ : case LT : case LEQ :
			{
				String stringProp = expr.toString();
				atoms.computeIfAbsent(stringProp, v -> new ArrayList<>()).add(expr);
				return null;
			}
			default :
			{
				if (! comparisonAtoms && AtomicPropManager.isPureBool(expr)) {
					String stringProp = expr.toString();
					atoms.computeIfAbsent(stringProp, v -> new ArrayList<>()).add(expr);
					return null;					
				}
			}
			}

			// else recurse
			expr.forEachChild(c -> extractAtoms(c, atoms, comparisonAtoms));
			return null;
		}
	}

	public Expression createBoundedComparison(boolean isLeftBounded, boolean isRightBounded, BinOp binOp) {
		// treat the simplest cases first
		if (isLeftBounded && isRightBounded
				|| isLeftBounded && binOp.getOp()==Op.GT			
				|| isRightBounded && binOp.getOp()==Op.LT
				) {
			return Simplifier.assumeOnebounded(binOp);
		} else if (isLeftBounded && binOp.getOp()==Op.GEQ) {
			return Expression.op(Op.AND, Simplifier.assumeOnebounded(binOp), Expression.op(Op.LEQ, binOp.right, Expression.constant(1)));
		} else if (isRightBounded && binOp.getOp()==Op.LEQ) {
			return Expression.op(Op.AND, Simplifier.assumeOnebounded(binOp), Expression.op(Op.LEQ, binOp.left, Expression.constant(1)));
		} else {
			Expression other ;
			if (!isLeftBounded) {
				other = Expression.op(Op.GT, binOp.left, Expression.constant(1));
			} else {
				other = Expression.op(Op.GT, binOp.right, Expression.constant(1));
			}			
			return Expression.op(Op.OR, Simplifier.assumeOnebounded(binOp),other);
		}
	}

}
