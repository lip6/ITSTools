package fr.lip6.move.gal.application.mcc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.PropertySimplifier;

public class SumRewriter {

	private static class BadSumException extends Exception {
	}

	private static class VarInt {
		int index;
		VarDecl var;

		public VarInt(VarDecl var, int index) {
			this.var = var;
			this.index = index;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if ((obj == null) || (getClass() != obj.getClass())) {
				return false;
			}
			VarInt other = (VarInt) obj;
			if (index != other.index) {
				return false;
			}
			if (var == null) {
				if (other.var != null) {
					return false;
				}
			} else if (!var.equals(other.var)) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			result = prime * result + ((var == null) ? 0 : var.hashCode());
			return result;
		}

	}

	private static int collectChildren(IntExpression expr, Set<VarInt> vars) throws BadSumException {
		int a = 0;
		if (expr instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) expr;
			if (bin.getOp().equals("+")) {
				a = collectChildren(bin.getLeft(), vars);
				a += collectChildren(bin.getRight(), vars);
			} else {
				throw new BadSumException();
			}
		} else if (expr instanceof VariableReference) {
			VariableReference vr = (VariableReference) expr;
			int ind = -1;
			if (vr.getIndex() != null) {
				if (vr.getIndex() instanceof Constant) {
					Constant cte = (Constant) vr.getIndex();
					ind = cte.getValue();
				} else {
					throw new BadSumException();
				}
			}
			if (vars != null) {
				vars.add(new VarInt((VarDecl) vr.getRef(), ind));
			}
		} else if (expr instanceof Constant) {
			a = ((Constant) expr).getValue();
		} else {
			throw new BadSumException();
		}
		return a;
	}

	public static Map<Set<VarInt>, List<IntExpression>> collectSums(Specification spec) {
		Map<Set<VarInt>, List<IntExpression>> sumMap = new HashMap<>();
		for (Property p : spec.getProperties()) {
			for (TreeIterator<EObject> it = p.eAllContents(); it.hasNext();) {
				EObject obj = it.next();
				if (obj instanceof BinaryIntExpression) {
					BinaryIntExpression bin = (BinaryIntExpression) obj;
					if (bin.getOp().equals("+")) {
						Set<VarInt> vars = new HashSet<>();
						try {
							int added = collectChildren(bin, vars);
							sumMap.computeIfAbsent(vars, v -> new ArrayList<>()).add(bin);
						} catch (BadSumException e) {
							e.printStackTrace();
						}
						it.prune();
					}
				}
			}
		}
		return sumMap;
	}

	public static int computeEffect(Entry<Set<VarInt>, List<IntExpression>> entry, Transition t) {
		int res = 0;
		for (Statement a : t.getActions()) {
			if (a instanceof Ite) {
				Ite ite = (Ite) a;
				if (ite.getIfFalse().size() == 1 && ite.getIfFalse().get(0) instanceof Abort
						&& ite.getIfTrue().size() == 1) {
					a = ite.getIfTrue().get(0);
				}
			}
			if (a instanceof Assignment) {
				Assignment ass = (Assignment) a;
				VarDecl v = (VarDecl) ass.getLeft().getRef();
				int ind = -1;
				if (ass.getLeft().getIndex() != null) {
					ind = ((Constant) ass.getLeft().getIndex()).getValue();
				}
				if (entry.getKey().contains(new VarInt(v, ind))) {
					if (ass.getType() == AssignType.INCR) {
						res += ((Constant) ass.getRight()).getValue();
					} else if (ass.getType() == AssignType.DECR) {
						res -= ((Constant) ass.getRight()).getValue();
					}
				}
			} else {
				return 1;
			}
		}
		return res;
	}

	public static Variable createSumOfVariable(Entry<Set<VarInt>, List<IntExpression>> entry) {
		StringBuilder sb = new StringBuilder();
		for (VarInt v : entry.getKey()) {
			sb.append(v.var.getName()).append("_");
			if (v.index != -1) {
				sb.append(v.index).append("_");
			}
			if (sb.length() >= 64) {
				break;
			}
		}
		Variable sum = GalFactory.eINSTANCE.createVariable();
		sum.setName(sb.toString());

		int summed = entry.getKey().stream()
				.mapToInt(v -> v.index < 0 ? ((Constant) ((Variable) v.var).getValue()).getValue()
						: ((Constant) ((ArrayPrefix) v.var).getValues().get(v.index)).getValue())
				.sum();

		sum.setValue(GF2.constant(summed));
		return sum;
	}

	public static boolean rewriteConstantSums(Specification spec) {
		boolean toret = false;
		try {
			Map<Set<VarInt>, List<IntExpression>> sumMap = collectSums(spec);
			for (Entry<Set<VarInt>, List<IntExpression>> entry : sumMap.entrySet()) {
				GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
				boolean isConst = true;
				for (Transition t : gal.getTransitions()) {
					int res = computeEffect(entry, t);
					if (res != 0) {
						isConst = false;
						break;
					}
				}
				if (isConst) {
					Variable sum = createSumOfVariable(entry);
					for (IntExpression head : entry.getValue()) {
						EcoreUtil.replace(head, EcoreUtil.copy(sum.getValue()));
					}
					System.out.println("Successfully replaced " + entry.getValue().size()
							+ " occurrences of a constant sum of " + entry.getKey().size() + " variables");
					toret = true;
				}
			}
		} catch (Exception e) {
			System.out.println("Problem detected in Rewrite constant sums.");
			e.printStackTrace();
		}
		return toret;
	}

	public static boolean rewriteSums(Specification spec) {
		boolean toret = false;
		try {
			Map<Set<VarInt>, List<IntExpression>> sumMap = collectSums(spec);
			for (Entry<Set<VarInt>, List<IntExpression>> entry : sumMap.entrySet()) {
				Variable sum = createSumOfVariable(entry);
				GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
				for (Transition t : gal.getTransitions()) {
					int res = computeEffect(entry, t);
					if (res != 0) {
						t.getActions().add(GF2.createIncrement(sum, res));
						if (res < 0) {
							t.setGuard(GF2.and(GF2.createComparison(GF2.createVariableRef(sum), ComparisonOperators.GE,
									GF2.constant(-res)), t.getGuard()));
						}
					}
				}
				gal.getVariables().add(sum);
				for (IntExpression head : entry.getValue()) {
					int v = collectChildren(head, null);
					if (v == 0) {
						EcoreUtil.replace(head, GF2.createVariableRef(sum));
					} else {
						EcoreUtil.replace(head,
								GF2.createBinaryIntExpression(GF2.createVariableRef(sum), "+", GF2.constant(v)));
					}
				}
				System.out.println("Successfully replaced " + entry.getValue().size() + " occurrences of a sum of "
						+ entry.getKey().size() + " variables");
				toret = true;
			}
		} catch (BadSumException ve) {
			ve.printStackTrace();
		}
		return toret;
	}

	/**
	 * Assumes spec is one bounded and rewrites variable comparisons x <= y to
	 * comparisons to constants (0 or 1).
	 * 
	 * @param spec
	 */
	public static void rewriteVariableComparisons(Specification spec) {
		Map<BooleanExpression, BooleanExpression> todo = new HashMap<>();
		for (Property prop : spec.getProperties()) {
			for (TreeIterator<EObject> it = prop.getBody().eAllContents(); it.hasNext();) {
				EObject obj = it.next();
				if (obj instanceof IntExpression) {
					it.prune();
				} else if (obj instanceof Comparison) {
					Comparison cmp = (Comparison) obj;
					if (cmp.getLeft() instanceof Reference && cmp.getRight() instanceof Reference) {
						BooleanExpression res = PropertySimplifier.assumeOneBounded(cmp);
						todo.put(cmp, res);
					}
					it.prune();
				}
			}
		}
		for (Entry<BooleanExpression, BooleanExpression> ent : todo.entrySet()) {
			EcoreUtil.replace(ent.getKey(), ent.getValue());
		}
	}

}
