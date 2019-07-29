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
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import android.util.SparseIntArray;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.DeadlockTester;
import fr.lip6.move.gal.instantiate.PropertySimplifier;
import fr.lip6.move.gal.semantics.ExpressionPrinter;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.expr.Expression;

public class AtomicReducer {
	public void strongReductions(String solverPath, MccTranslator reader, boolean isSafe, Set<String> doneProps) {
		checkAtomicPropositions(reader.getSpec(), doneProps, isSafe,solverPath);			
		// simplify complex redundant formulas
		GalToLogicNG gtl = new GalToLogicNG();
		List<BooleanExpression> tosimp = reader.getSpec().getProperties().stream().map(p -> (BooleanExpression) p.getBody().eAllContents().next()).collect(Collectors.toList());
		gtl.simplify(tosimp);
	}

	/**
	 * Extract Atomic Propositions, unify them, for each of them test initial state, then try to assert it will not vary => simplifiable.
	 * @param spec
	 * @param doneProps
	 * @param isSafe
	 * @param solverPath 
	 */
	private void checkAtomicPropositions(Specification spec, Set<String> doneProps, boolean isSafe, String solverPath) {
		
		if (solverPath == null) {
			return;
		}
		IDeterministicNextBuilder dnb = IDeterministicNextBuilder.build(INextBuilder.build(spec));
		// order is not really important, but reproducibility of iteration order we depend upon
		Map<String,List<Comparison>> atoms = new LinkedHashMap<>();		
		
		// collect all atomic predicates in the property
		for (Property prop : spec.getProperties()) {
			extractAtoms(prop, atoms, dnb);
		}
		// try for each comparison to assert one of the terms at least is one bounded
		List<Expression> tocheckBounds = new ArrayList<>();
		List<Entry<String, List<Comparison>>> totreat = new ArrayList<>(); 
		for (Entry<String, List<Comparison>> ent:atoms.entrySet()) {
			Comparison cmp = ent.getValue().get(0); // never empty by cstr
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
				for (int v=0; v < paths.size()-1; v+=2) {
					int vind = v / 2;
					Entry<String, List<Comparison>> ent = totreat.get(vind);
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
						BooleanExpression eqtest = createBoundedComparison(isLeftBounded, isRightBounded, ent.getValue().get(0));

						for (ListIterator<Comparison> it = ent.getValue().listIterator() ; it.hasNext(); ) {
							Comparison cmp = it.next();
							BooleanExpression copy = EcoreUtil.copy(eqtest);
							EcoreUtil.replace(cmp, copy);
							extractAtoms(copy, atoms, dnb);
							nsimpl++;
						}
					}
				}
				if (! treated.isEmpty()) {
					System.out.println("Successfully simplified "+nsolved+" atomic comparison propositions using bounds test for a total of " + nsimpl +" simplifications.");
				}
			} catch (Exception e) {
				// in particular java.lang.ArithmeticException
				// at uniol.apt.analysis.invariants.InvariantCalculator.test1b2(InvariantCalculator.java:448)
				// can occur here.
				System.out.println("Failed to apply SMT based 'comparison of one bounded terms' test, skipping this step." );
				e.printStackTrace();
			}
		}

		// build a list of invariants to test with SMT/random
		// for each of them test value in initial state
		List<Expression> tocheck = new ArrayList<>();
		List<Boolean> initialValues = new ArrayList<>();

		for (Entry<String, List<Comparison>> ent:atoms.entrySet()) {
			Comparison cmp = ent.getValue().get(0); // never empty by cstr
			boolean val = PropertySimplifier.evalInInitialState(cmp);
			initialValues.add(val);
			System.out.println("Atom : "+ent.getKey()+" initial="+val);
			if (val) {
				// UNSAT => it never becomes false
				tocheck.add(Expression.not(Expression.buildExpression(cmp, dnb)));
			} else {
				// UNSAT => it never becomes true
				tocheck.add(Expression.buildExpression(cmp, dnb));
			}
			if (! (cmp.getLeft() instanceof Constant || cmp.getRight() instanceof Constant)) {
				BooleanExpression be = GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.GT, GF2.constant(1));
				tocheckBounds.add(Expression.buildExpression(be, dnb));
				be = GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.GT, GF2.constant(1));
				tocheckBounds.add(Expression.buildExpression(be, dnb));
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
			return ;
		}		


		try {
			int nsolved = 0;
			int nsimpl = 0;
			List<Integer> repr = new ArrayList<>();
			List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, isSafe, repr);

			Iterator<Entry<String, List<Comparison>>> it = atoms.entrySet().iterator();
			int vi=0;				
			for (int v=0; v < paths.size()-1; v++) {
				int vind = tocheckIndexes.get(vi);
				Entry<String, List<Comparison>> ent = it.next();
				if (vind == v) {
					SparseIntArray parikh = paths.get(v);
					if (parikh == null) {
						// cool we've proven an invariant, we can substitute
						Comparison c = ent.getValue().get(0); // never empty by cstr
						boolean val = PropertySimplifier.evalInInitialState(c);
						for (Comparison cmp : ent.getValue()) {
							nsimpl++;
							if (val)
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
							else
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
						}
						nsolved ++;
					}						
					vi++;
				}
			}
			if (nsolved > 0)
				System.out.println("Successfully simplified "+nsolved+" atomic propositions for a total of " + nsimpl +" simplifications.");
		} catch (Exception e) {
			// in particular java.lang.ArithmeticException
			// at uniol.apt.analysis.invariants.InvariantCalculator.test1b2(InvariantCalculator.java:448)
			// can occur here.
			System.out.println("Failed to apply SMT based 'atomic proposition is an invariant' test, skipping this step." );
			e.printStackTrace();
		}

		
	}

	public void extractAtoms(EObject prop, Map<String, List<Comparison>> atoms,
			IDeterministicNextBuilder dnb) {
		for (TreeIterator<EObject> it=prop.eAllContents() ; it.hasNext() ;) {
			EObject obj = it.next();
			if (obj instanceof Comparison) {
				String stringProp = ExpressionPrinter.printQualifiedExpression(obj, "s", dnb);
				atoms.compute(stringProp, (k,v)-> {
					if (v==null) v=new ArrayList<>();
					v.add((Comparison) obj);
					return v;
				});
				it.prune();
			}				
		}
	}

	public BooleanExpression createBoundedComparison(boolean isLeftBounded, boolean isRightBounded, Comparison cmp) {
		BooleanExpression eqtest = GalFactory.eINSTANCE.createFalse();
		// treat the simplest cases first
		if (isLeftBounded && isRightBounded
				|| isLeftBounded && cmp.getOperator()==ComparisonOperators.GE 
				|| isLeftBounded && cmp.getOperator()==ComparisonOperators.GT				
				|| isRightBounded && cmp.getOperator()==ComparisonOperators.LE 
				|| isRightBounded && cmp.getOperator()==ComparisonOperators.LT
				) {
			return PropertySimplifier.assumeOneBounded(cmp);
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
		
//		// if equality is supported, create a predicate covering the cases
//		if (cmp.getOperator() == ComparisonOperators.EQ || cmp.getOperator() == ComparisonOperators.LE || cmp.getOperator() == ComparisonOperators.GE) {
//			eqtest = GF2.or(
//					GF2.and(
//							// lhs == 0
//							GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.EQ, GF2.constant(0)), 										
//							// rhs == 0
//							GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.EQ, GF2.constant(0))
//							),
//					GF2.and(
//							// lhs == 1
//							GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.EQ, GF2.constant(1)), 										
//							// rhs == 1
//							GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.EQ, GF2.constant(1))
//							)
//
//					);
//		}
//		// a lesser b
//		if (cmp.getOperator() == ComparisonOperators.LE || cmp.getOperator() == ComparisonOperators.LT) {
//			// one the two at least is one bounded
//			// a < b  <=> a==0 & b == 1 
//			eqtest = GF2.or(eqtest, 
//					GF2.and(
//							// lhs == 0
//							GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.EQ, GF2.constant(0)), 										
//							// rhs == 1
//							GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.EQ, GF2.constant(1))
//							)										
//					);
//			if (! isRightBounded) {
//				// additional possible case here : b > 1
//				eqtest = GF2.or(eqtest, 																				
//						// rhs > 1
//						GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.GT, GF2.constant(1))
//						);
//			} 									
//		}
//		
//		// a greater b
//		if (cmp.getOperator() == ComparisonOperators.GE || cmp.getOperator() == ComparisonOperators.GT) {
//			// one the two at least is one bounded
//			// a > b  <=> a==1 & b == 0 
//			eqtest = GF2.or(eqtest, 
//					GF2.and(
//							// lhs == 1
//							GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.EQ, GF2.constant(1)), 										
//							// rhs == 0
//							GF2.createComparison(EcoreUtil.copy(cmp.getRight()), ComparisonOperators.EQ, GF2.constant(0))
//							)										
//					);
//			if (! isLeftBounded) {
//				// additional possible case here : a > 1
//				eqtest = GF2.or(eqtest, 																				
//						// lhs > 1
//						GF2.createComparison(EcoreUtil.copy(cmp.getLeft()), ComparisonOperators.GT, GF2.constant(1))
//						);
//			} 									
//		}
//		return eqtest;
//	}

}
