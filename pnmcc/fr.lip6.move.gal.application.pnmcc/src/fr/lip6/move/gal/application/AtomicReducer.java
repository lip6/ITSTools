package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import android.util.SparseIntArray;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.gal2smt.DeadlockTester;
import fr.lip6.move.gal.instantiate.PropertySimplifier;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.serialization.SerializationUtil;

public class AtomicReducer {
	private static final int DEBUG = 0;

	public void strongReductions(String solverPath, MccTranslator reader, boolean isSafe, Set<String> doneProps) {
		int solved = checkAtomicPropositions(reader.getSpec(), doneProps, isSafe,solverPath, true);
		solved += checkAtomicPropositions(reader.getSpec(), doneProps, isSafe,solverPath, false);
		// simplify complex redundant formulas
		GalToLogicNG gtl = new GalToLogicNG();
		List<BooleanExpression> atoms = new ArrayList<>();				
		// collect all atomic predicates in the property
		for (Property prop : reader.getSpec().getProperties()) {
			for (TreeIterator<EObject> it = prop.eAllContents() ; it.hasNext() ;) {
				EObject obj = it.next();
				if (isPureBool(obj)) {
					atoms.add((BooleanExpression) obj);
					it.prune();
				}
			}
		}
		gtl.simplify(atoms);
	}

	private boolean isPureBool(EObject obj) {
		if (obj instanceof And || obj instanceof Or || obj instanceof Not ) {
			for (EObject child:  obj.eContents()) {
				if (! isPureBool(child)) {
					return false;
				}
			}
			return true;
		} else if (obj instanceof Comparison || obj instanceof True || obj instanceof False) {
			return true;
		}				
		return false;
	}
	/**
	 * Extract Atomic Propositions, unify them, for each of them test initial state, then try to assert it will not vary => simplifiable.
	 * @param spec
	 * @param doneProps
	 * @param isSafe
	 * @param solverPath 
	 * @param comparisonAtoms if true look only at comparisons only as atoms (single predicate), otherwise sub boolean formulas are considered atoms (CTL, LTL) 
	 */
	private int checkAtomicPropositions(Specification spec, Set<String> doneProps, boolean isSafe, String solverPath, boolean comparisonAtoms) {
		
		if (solverPath == null) {
			return 0;
		}
		IDeterministicNextBuilder dnb = IDeterministicNextBuilder.build(INextBuilder.build(spec));
		
		// order is not really important, but reproducibility of iteration order we depend upon
		Map<String,List<BooleanExpression>> atoms = new LinkedHashMap<>();				
		// collect all atomic predicates in the property
		int pid = 0;
		for (Property prop : spec.getProperties()) {
			if (DEBUG >= 1) System.out.println("p" + pid++ + ":" + SerializationUtil.getText(prop, true));
			extractAtoms(prop, atoms, comparisonAtoms);			
		}
		
		StructuralReduction sr ;
		if (comparisonAtoms) {
			sr = testAndRewriteBoundedComparison(atoms, dnb, solverPath, isSafe);
		} else {
			sr = new StructuralReduction(dnb);
		}

		// build a list of invariants to test with SMT/random
		// for each of them test value in initial state
		List<Expression> tocheck = new ArrayList<>();
		List<Boolean> initialValues = new ArrayList<>();

		for (Entry<String, List<BooleanExpression>> ent:atoms.entrySet()) {
			BooleanExpression cmp = ent.getValue().get(0); // never empty by cstr
			boolean val = PropertySimplifier.evalInInitialState(cmp);
			initialValues.add(val);
			if (val) {
				// UNSAT => it never becomes false
				tocheck.add(Expression.not(Expression.buildExpression(cmp, dnb)));
			} else {
				// UNSAT => it never becomes true
				tocheck.add(Expression.buildExpression(cmp, dnb));
			}			
		}
		
		List<Integer> tocheckIndexes = new ArrayList<>();
		for (int j=0; j < tocheck.size(); j++) { tocheckIndexes.add(j);}

		RandomExplorer re = new RandomExplorer(sr);
		int steps = 100000; // 100k steps
		int timeout = 30; // 30 secs
		int[] verdicts = re.run(steps,tocheck,timeout);
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
			List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, isSafe, repr);

			Iterator<Entry<String, List<BooleanExpression>>> it = atoms.entrySet().iterator();
			int vi=0;
			int cur=0;
			while (it.hasNext()) {
				Entry<String, List<BooleanExpression>> ent = it.next();
				int vind = tocheckIndexes.get(cur);
				if (vi == vind) {
					SparseIntArray parikh = paths.get(cur);
					if (parikh == null) {
						// cool we've proven an invariant, we can substitute
						BooleanExpression c = ent.getValue().get(0); // never empty by cstr
						boolean val = PropertySimplifier.evalInInitialState(c);
						if (DEBUG >= 1) System.out.println("SMT proved "+tocheck.get(cur) + " is unreachable; concluding " + SerializationUtil.getText(c, true)+ " is always " + val);
						for (BooleanExpression cmp : ent.getValue()) {
							nsimpl++;
							if (val)
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
							else
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
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
				System.out.println("Successfully simplified "+nsolved+" atomic propositions for a total of " + nsimpl +" simplifications.");
				if (DEBUG >= 1)  {
					pid = 0;
					for (Property prop : spec.getProperties()) {
						System.out.println("p" + pid++ + ":" + SerializationUtil.getText(prop, true));
					}
				}
			}
			return nsolved;
		} catch (Exception e) {
			// in particular java.lang.ArithmeticException
			// at uniol.apt.analysis.invariants.InvariantCalculator.test1b2(InvariantCalculator.java:448)
			// can occur here.
			System.out.println("Failed to apply SMT based 'atomic proposition is an invariant' test, skipping this step." +e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public StructuralReduction testAndRewriteBoundedComparison(Map<String, List<BooleanExpression>> atoms, 
			IDeterministicNextBuilder dnb, String solverPath, boolean isSafe) {
		// try for each comparison to assert one of the terms at least is one bounded
		List<Expression> tocheckBounds = new ArrayList<>();
		List<Entry<String, List<BooleanExpression>>> totreat = new ArrayList<>(); 
		for (Entry<String, List<BooleanExpression>> ent:atoms.entrySet()) {
			Comparison cmp = (Comparison) ent.getValue().get(0); // never empty by cstr
			if (! (cmp.getLeft() instanceof Constant || cmp.getRight() instanceof Constant)) {
				BooleanExpression be = GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.GT, GF2.constant(1));
				tocheckBounds.add(Expression.buildExpression(be, dnb));
				be = GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.GT, GF2.constant(1));
				tocheckBounds.add(Expression.buildExpression(be, dnb));
				totreat.add(ent);
			}
		}

		// to build a random explorer and fast SMT checker
		// we do not apply reductions here to be context free.
		StructuralReduction sr = new StructuralReduction(dnb);

		if (! tocheckBounds.isEmpty()) {
			try {
				int nsolved = 0;
				int nsimpl = 0;
				List<Integer> repr = new ArrayList<>();
				Set<String> treated = new HashSet<>();
				List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheckBounds, sr, solverPath, isSafe, repr);
				if (DEBUG  >=1) {
					for (int i=0; i < paths.size(); i++) {
						if (paths.get(i)==null) {
							System.out.println("Proved "+tocheckBounds.get(i)+ " unreachable.");
						}
					}
				}
				
				for (int v=0; v < paths.size()-1; v+=2) {
					int vind = v / 2;
					Entry<String, List<BooleanExpression>> ent = totreat.get(vind);
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
						BooleanExpression eqtest = createBoundedComparison(isLeftBounded, isRightBounded, (Comparison) ent.getValue().get(0));
						
						if (DEBUG  >=1) 
							System.out.println("isLB/isRB:" + isLeftBounded + "/" + isRightBounded +" After replacing "+SerializationUtil.getText(ent.getValue().get(0),true) +" by "+ SerializationUtil.getText(eqtest,true));
						
						for (ListIterator<BooleanExpression> it = ent.getValue().listIterator() ; it.hasNext(); ) {
							BooleanExpression cmp = it.next();
							BooleanExpression copy = EcoreUtil.copy(eqtest);
							EcoreUtil.replace(cmp, copy);
							extractAtoms(copy, atoms,true);
							nsimpl++;
						}
					}
				}
				if (! treated.isEmpty()) {
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

	public void extractAtoms(EObject prop, Map<String, List<BooleanExpression>> atoms, boolean comparisonAtoms) {
		for (TreeIterator<EObject> it=prop.eAllContents() ; it.hasNext() ;) {
			EObject obj = it.next();
			if (obj instanceof True || obj instanceof False) {
				continue;
			}
			if (obj instanceof Comparison || (!comparisonAtoms && isPureBool(obj))) {
				String stringProp = SerializationUtil.getText(obj,true);
				atoms.compute(stringProp, (k,v)-> {
					if (v==null) v=new ArrayList<>();
					v.add((BooleanExpression) obj);
					return v;
				});
				it.prune();
			}
		}
	}

	public BooleanExpression createBoundedComparison(boolean isLeftBounded, boolean isRightBounded, Comparison cmp) {
		// treat the simplest cases first
		if (isLeftBounded && isRightBounded
				|| isLeftBounded && cmp.getOperator()==ComparisonOperators.GT			
				|| isRightBounded && cmp.getOperator()==ComparisonOperators.LT
				) {
			return PropertySimplifier.assumeOneBounded(cmp);
		} else if (isLeftBounded && cmp.getOperator()==ComparisonOperators.GE) {
			return GF2.and(PropertySimplifier.assumeOneBounded(cmp), GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.LE, GF2.constant(1)) ) ;
		} else if (isRightBounded && cmp.getOperator()==ComparisonOperators.LE) {
			return GF2.and(PropertySimplifier.assumeOneBounded(cmp), GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.LE, GF2.constant(1)) ) ;			
		} else {
			BooleanExpression other ;
			if (!isLeftBounded) {
				other = GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.GT, GF2.constant(1));
			} else {
				other = GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.GT, GF2.constant(1));
			}			
			return GF2.or(PropertySimplifier.assumeOneBounded(cmp),other);
		}
	}

}
