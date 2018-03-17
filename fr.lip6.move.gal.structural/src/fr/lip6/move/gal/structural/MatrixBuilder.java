package fr.lip6.move.gal.structural;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.semantics.Assign;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.LeafNextVisitor;
import fr.lip6.move.gal.semantics.Predicate;

public class MatrixBuilder {

	private FlowMatrix matrix;
	private boolean isPresburger=true;
	
	public MatrixBuilder(IDeterministicNextBuilder inb) {
		matrix = new FlowMatrix(inb.getInitial().size(),inb.getDeterministicNext().size());
		int tindex = 0;
		for (List<INext> trans : inb.getDeterministicNext()) {
			visitTransition(trans, tindex++);
		}
		if (! isPresburger) {
			matrix = null;
		}
	}
	
	public boolean isPresburger() {
		return isPresburger;
	}
	
	public FlowMatrix getMatrix() {
		return matrix;
	}
	
	class PresburgerChecker implements LeafNextVisitor<Boolean> {
		private final int tindex;

		public PresburgerChecker(int tindex) {
			this.tindex = tindex;
		}

		@Override
		public Boolean visit(Assign assn) {
			Assignment ass = assn.getAssignment();

			// index of the target variable
			int vindex = assn.getIndexer().getIndex(ass.getLeft().getRef().getName());
			// make sure lhs is a constant index access
			if ( ass.getLeft().getIndex() != null) {
				try {
					int ind = Instantiator.evalConst(ass.getLeft().getIndex());
					vindex += ind;
				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}
			}

			// value added to target variable
			int val =0;			
			if (ass.getType() == AssignType.INCR) {
				try {
					int v = Instantiator.evalConst(ass.getRight());
					val = v;
				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}				
			} else if (ass.getType() == AssignType.DECR) {
				try {
					int v = Instantiator.evalConst(ass.getRight());
					val = -v;
				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}				
			} else if (ass.getType() == AssignType.ASSIGN) {
				if (ass.getRight() instanceof BinaryIntExpression) {
					BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
					if (EcoreUtil.equals(ass.getLeft(), bin.getLeft())) {
						// x = x + k ?
						try {
							int  k = Instantiator.evalConst(bin.getRight());
							if (bin.getOp().equals("+")) {
								val = k;
							} else if (bin.getOp().equals("-")) {
								val = -k;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							return false;
						}
					} else if (EcoreUtil.equals(ass.getLeft(), bin.getRight())) {
						// x = k + x ?
						try {
							int  k = Instantiator.evalConst(bin.getLeft());
							if (bin.getOp().equals("+")) {
								val = k;
							} else if (bin.getOp().equals("-")) {
								return false;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							return false;
						}
					} else {
						// rhs is not k+x or x+k
						return false;
					}
				} else {
					return false;
				}
			}

			matrix.addWriteEffect(tindex, vindex, val);
			return true;
		}

		private boolean visitBool(Predicate pred, BooleanExpression be) {
			if (be instanceof Comparison) {
				Comparison cmp = (Comparison) be;
				if (cmp.getOperator() == ComparisonOperators.GE) {
					if (cmp.getLeft() instanceof VariableReference && cmp.getRight() instanceof Constant) {
						VariableReference vref = (VariableReference) cmp.getLeft();
						Constant cte = (Constant) cmp.getRight(); 
						// index of the target variable
						int vindex = pred.getIndexer().getIndex(vref.getRef().getName());
						// make sure lhs is a constant index access
						if ( vref.getIndex() != null) {
							try {
								int ind = Instantiator.evalConst(vref.getIndex());
								vindex += ind;
							} catch (ArrayIndexOutOfBoundsException e) {
								return false;
							}
						}
						matrix.addReadEffect(tindex, vindex, cte.getValue());						
						return true;
					}
				}				
			} else if (be instanceof And) {
				And and = (And) be;
				return visitBool(pred, and.getLeft()) && visitBool(pred, and.getRight()); 				
			} 
			return false;
		}

		@Override
		public Boolean visit(Predicate pred) {
			return visitBool(pred, pred.getGuard());
		}
	}

	
	private void visitTransition(List<INext> seq, int tindex) {		
		if (! isPresburger) {
			return;
		}
		// Use this opportunity to compute flow matrix
		PresburgerChecker pc = new PresburgerChecker(tindex);
		for (INext n : seq) {
			Boolean b = n.accept(pc);
			if (! b) {
				isPresburger = false;
				return ;
			}
		}
	}

	
}
