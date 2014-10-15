package fr.lip6.move.divine.togal.transform;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.divine.divine.AndOr;
import fr.lip6.move.divine.divine.ArithmeticSigned;
import fr.lip6.move.divine.divine.Array;
import fr.lip6.move.divine.divine.ArrayReference;
import fr.lip6.move.divine.divine.ArrayVarAccess;
import fr.lip6.move.divine.divine.BooleanLiteral;
import fr.lip6.move.divine.divine.BooleanNegation;
import fr.lip6.move.divine.divine.Comparison;
import fr.lip6.move.divine.divine.Constant;
import fr.lip6.move.divine.divine.Expression;
import fr.lip6.move.divine.divine.Minus;
import fr.lip6.move.divine.divine.MultiDivMod;
import fr.lip6.move.divine.divine.NumberLiteral;
import fr.lip6.move.divine.divine.OrAndXor;
import fr.lip6.move.divine.divine.Plus;
import fr.lip6.move.divine.divine.Process;
import fr.lip6.move.divine.divine.ProcessReference;
import fr.lip6.move.divine.divine.ProcessStateAccess;
import fr.lip6.move.divine.divine.Shift;
import fr.lip6.move.divine.divine.Variable;
import fr.lip6.move.divine.divine.VariableOrConstantReference;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.gal.WrapBoolExpr;


public class Converter {
	
	//maps global variables to their (ref)image in gal (could be type parameters or variables)
	private Map<Variable,fr.lip6.move.gal.IntExpression> gvarmap = new HashMap<Variable,fr.lip6.move.gal.IntExpression>();
	
	// maps local variables 
	private Map<Variable, fr.lip6.move.gal.IntExpression> lvarmap = new HashMap<Variable,fr.lip6.move.gal.IntExpression>();

	// maps global array to their image in gal
	private Map<Array, fr.lip6.move.gal.ArrayVarAccess> garraymap = new HashMap<Array, fr.lip6.move.gal.ArrayVarAccess>();
	
	// maps local array to their image in gal
	private Map<Array, fr.lip6.move.gal.ArrayVarAccess> larraymap = new HashMap<Array, fr.lip6.move.gal.ArrayVarAccess>();
		
	// maps constants to thier image in gal
	private Map<Constant,fr.lip6.move.gal.IntExpression> constmap = new HashMap<Constant,fr.lip6.move.gal.IntExpression>();
	
	// maps process to thier image in gal
		private Map<Process, fr.lip6.move.gal.Variable> procStateMap = new HashMap<Process, fr.lip6.move.gal.Variable>();
		
	public void declareStateVar(Process proc, fr.lip6.move.gal.Variable pstate) {
		procStateMap.put(proc,pstate);
	}
	
	public void addGlobal(Variable var, fr.lip6.move.gal.IntExpression value) {
		gvarmap.put(var, value);
	}
	
	public void addGlobal(Constant c, fr.lip6.move.gal.IntExpression gava) {
		constmap.put(c, gava);
	}
	
	public void addGlobal(Array array, fr.lip6.move.gal.ArrayVarAccess gava) {
		garraymap.put(array, gava);
	}
	
	public void addLocal(Variable var, fr.lip6.move.gal.IntExpression value) {
		lvarmap.put(var, value);
	}
	
	public void addLocal(Array array, fr.lip6.move.gal.ArrayVarAccess gava) {
		larraymap.put(array, gava);
	}
	
	public fr.lip6.move.gal.IntExpression getImage(Variable var) {
		if (gvarmap.get(var) != null)
			return gvarmap.get(var);
		return lvarmap.get(var);
	}
	
	public fr.lip6.move.gal.IntExpression getImage(Constant c) {
		return constmap.get(c);
	}
	
	public fr.lip6.move.gal.ArrayVarAccess getImage(Array array) {
		if (garraymap.get(array) != null)
			return garraymap.get(array);
		return larraymap.get(array);
	}
	
	public fr.lip6.move.gal.Variable getImage(Process p) {
		return procStateMap.get(p);
	}

	public boolean isIntExpression(Expression e){
		if (e instanceof NumberLiteral)
			return true;
		return false;
	}
	
	public boolean isBooleanExpression(Expression e){
		if (e instanceof AndOr)
			return true;
		else if (e instanceof Comparison)
			return true;
		else if (e instanceof BooleanNegation)
			return true;
		else if (e instanceof BooleanLiteral)
			return true;
		else if (e instanceof ProcessReference)
			return true;
		
		return false;
	}
	
	public boolean isBinaryIntExpression(Expression e) {

		if (e instanceof Plus)
			return true;
		else if (e instanceof Minus)
			return true;
		else if (e instanceof MultiDivMod)
			return true;
		else if (e instanceof Shift)
			return true;
		else if(e instanceof OrAndXor)
			return true;
		
		return false;
	}
	
	public boolean isReference(Expression e) {
		if (e instanceof VariableOrConstantReference)
			return true;
		else if (e instanceof ArrayReference)
			return true;
		
		return false;
	}
	
	public BooleanExpression convertToGalBool(Expression e) {
		if (e instanceof BooleanLiteral) {
			BooleanLiteral bool = (BooleanLiteral) e;
			if (bool.getValue().equals("true"))
				return GalFactory.eINSTANCE.createTrue();
			else 
				return GalFactory.eINSTANCE.createFalse();
		}
		else if (e instanceof AndOr) {
			AndOr b = (AndOr) e;
			if (b.getOp().equals("&&") || b.getOp().equals("and")){
				fr.lip6.move.gal.And target = GalFactory.eINSTANCE.createAnd();
				target.setLeft(convertToGalBool(b.getLeft()));
				target.setRight(convertToGalBool(b.getRight()));
				return target;
			}
			else if (b.getOp().equals("||") || b.getOp().equals("or")){
				fr.lip6.move.gal.Or target = GalFactory.eINSTANCE.createOr();
				target.setLeft(convertToGalBool(b.getLeft()));
				target.setRight(convertToGalBool(b.getRight()));
				return target;
			}	
		}
		else if (e instanceof Comparison) {
			Comparison b = (Comparison) e;
			fr.lip6.move.gal.Comparison target = GalFactory.eINSTANCE.createComparison();
			target.setOperator(convertToGalOp(b));
			target.setLeft(convertToGalInt(b.getLeft()));
			target.setRight(convertToGalInt(b.getRight()));
			return target;
		}
		else if (e instanceof BooleanNegation) {
			BooleanNegation b = (BooleanNegation) e;
			fr.lip6.move.gal.Not target = GalFactory.eINSTANCE.createNot();
			target.setValue(convertToGalBool(b.getExpression()));
			return target;
		}
		// e is a reference of state in a process
		else if (e instanceof ProcessReference) {
			ProcessReference pref = (ProcessReference) e;
			if (pref.getRef() instanceof ProcessStateAccess) {
				ProcessStateAccess psa = (ProcessStateAccess) pref.getRef();
				fr.lip6.move.gal.Variable pstate = procStateMap.get(psa.getProcess());
				
				// grab var describing proc state
				VariableRef vref= GalFactory.eINSTANCE.createVariableRef();
				vref.setReferencedVar(pstate);	
				
				// grab index of the state
				int index = psa.getProcess().getStateDeclaration().getStates().indexOf(psa.getState());
				fr.lip6.move.gal.Constant valstate = GalFactory.eINSTANCE.createConstant();
				valstate.setValue(index);
				
				// build comparison : pstate == index
				BooleanExpression guard = createComparison(vref, ComparisonOperators.EQ, valstate);
				return guard;
			}
			else 
				throw new UnsupportedOperationException("Unmapped process "+ pref);
		}
		// pgm_protocol.8.dve ... guard close ... close is a variable
		else if (isReference(e)) {
			if (e instanceof VariableOrConstantReference) {
				VariableOrConstantReference ref = (VariableOrConstantReference) e;
				if (ref.getRef() instanceof Variable) {
					Variable var = (Variable) ref.getRef();
					VariableRef vref = (VariableRef) getImage(var);
					if (vref != null) {
						// build comparison vref != 0
						fr.lip6.move.gal.Constant zero = GalFactory.eINSTANCE.createConstant();
						zero.setValue(0);
						BooleanExpression guard = createComparison(EcoreUtil.copy(vref), ComparisonOperators.NE, zero);
						return guard;
					}
					throw new UnsupportedOperationException("Unmapped variable "+ var);
				}
				else if (ref.getRef() instanceof Constant) {
					Constant c = (Constant) ref.getRef();
					ParamRef pref = (ParamRef) getImage(c);
					if (pref != null) {
						// build comparison pref != 0
						fr.lip6.move.gal.Constant zero = GalFactory.eINSTANCE.createConstant();
						zero.setValue(0);
						BooleanExpression guard = createComparison(EcoreUtil.copy(pref), ComparisonOperators.NE, zero);
						return guard;
					}
					throw new UnsupportedOperationException("Unmapped constant "+ c);
				}
				else
					throw new UnsupportedOperationException("Unmapped variable or constant "+ ref);
			}
			else if (e instanceof ArrayReference) {
				ArrayReference ref = (ArrayReference) e;
				if (ref.getRef() instanceof ArrayVarAccess) {
					ArrayVarAccess ava = (ArrayVarAccess) ref.getRef();
					fr.lip6.move.gal.ArrayVarAccess gava; 
					gava = getImage(ava.getPrefix());
					if (gava != null) {
						gava.setIndex(convertToGalInt(ava.getIndex()));
						// build comparison array[index] != 0
						fr.lip6.move.gal.Constant zero = GalFactory.eINSTANCE.createConstant();
						zero.setValue(0);
						BooleanExpression guard = createComparison(EcoreUtil.copy(gava), ComparisonOperators.NE, zero);
						return guard;
					}
					throw new UnsupportedOperationException("Unmapped array "+ ava);
				}
			}
		}
		throw new UnsupportedOperationException("Unexpected boolean expression type in conversion "+e);
	}
	
	private ComparisonOperators convertToGalOp(Comparison c) {
		if (c.getOp().equals(">")) return ComparisonOperators.GT;
		else if (c.getOp().equals("<")) return ComparisonOperators.LT;
		else if (c.getOp().equals(">=")) return ComparisonOperators.GE;
		else if (c.getOp().equals("<=")) return ComparisonOperators.LE;
		else if (c.getOp().equals("==")) return ComparisonOperators.EQ;
		else if (c.getOp().equals("!=")) return ComparisonOperators.NE;
		else
			throw new UnsupportedOperationException("Unexpected operator received for conversion to ComparisonOperators : " + c.getClass().getSimpleName());
	}
	
	public IntExpression convertToGalInt(Expression e) {
		
		if (e instanceof NumberLiteral) {
			NumberLiteral cte = (NumberLiteral) e;
			fr.lip6.move.gal.Constant target = GalFactory.eINSTANCE.createConstant();
			target.setValue(cte.getValue());
			return target;
		} 
		else if (isBinaryIntExpression(e)) {
			return convertToGalBinaryInt(e);	
		}
		else if (e instanceof ArithmeticSigned) {
			ArithmeticSigned as = (ArithmeticSigned) e;
			fr.lip6.move.gal.UnaryMinus target = GalFactory.eINSTANCE.createUnaryMinus();
			target.setValue(convertToGalInt(as.getExpression()));
			return target;
		}
		else if (isReference(e))
			return convertToGalRefInt(e);
		else if (e instanceof Comparison) { // cas ((var==255)*255) ... fischer.2.dve
			Comparison comp = (Comparison) e;
			WrapBoolExpr target = GalFactory.eINSTANCE.createWrapBoolExpr();
			target.setValue(convertToGalBool(comp));
			return target;
		}
		else {
			throw new UnsupportedOperationException("Unexpected type received for conversion to Int expr : " + e.getClass().getSimpleName());
		}		
	}

	private IntExpression convertToGalBinaryInt(Expression e) {
		fr.lip6.move.gal.BinaryIntExpression tbin = 
				GalFactory.eINSTANCE.createBinaryIntExpression();
		
		if (e instanceof Plus){
			Plus bin = (Plus) e;
			tbin.setOp(bin.getOp());
			tbin.setLeft(convertToGalInt(bin.getLeft()));
			tbin.setRight(convertToGalInt(bin.getRight()));
		}
		else if (e instanceof Minus) {
			Minus bin = (Minus) e;
			tbin.setOp(bin.getOp());
			tbin.setLeft(convertToGalInt(bin.getLeft()));
			tbin.setRight(convertToGalInt(bin.getRight()));
		}	
		else if (e instanceof MultiDivMod) {
			MultiDivMod bin = (MultiDivMod) e;
			tbin.setOp(bin.getOp());
			tbin.setLeft(convertToGalInt(bin.getLeft()));
			tbin.setRight(convertToGalInt(bin.getRight()));
		}
		else if (e instanceof Shift) {
			Shift bin = (Shift) e;
			tbin.setOp(bin.getOp());
			tbin.setLeft(convertToGalInt(bin.getLeft()));
			tbin.setRight(convertToGalInt(bin.getRight()));
		}
		else if (e instanceof OrAndXor) {
			OrAndXor bin = (OrAndXor) e;
			tbin.setOp(bin.getOp());
			tbin.setLeft(convertToGalInt(bin.getLeft()));
			tbin.setRight(convertToGalInt(bin.getRight()));
		}
		else {
			throw new UnsupportedOperationException(
					"Unexpected type received for conversion to Int expr : " + e);
		}
		return tbin;
	}
	
	private IntExpression convertToGalRefInt(Expression e) {
		fr.lip6.move.gal.IntExpression target = null;
		// e is a reference of variabe or constant
		if (e instanceof VariableOrConstantReference) {
			VariableOrConstantReference ref = (VariableOrConstantReference) e;
			if (ref.getRef() instanceof Variable) {
				Variable var = (Variable) ref.getRef();
				// check if global var
				target = gvarmap.get(var);
				if (target != null)
					return EcoreUtil.copy(target);
				else {
					// local var
					target = lvarmap.get(var);
					if (target == null)
						throw new UnsupportedOperationException("Unmapped variable "+ var);
					return EcoreUtil.copy(target);	
				}
			}
			else if (ref.getRef() instanceof Constant) {
				Constant c = (Constant) ref.getRef();
				target = constmap.get(c);
				if (target != null)
					return EcoreUtil.copy(target);
				throw new UnsupportedOperationException("Unmapped constant "+ c);
			}
			else
				throw new UnsupportedOperationException("Unmapped variable or constant "+ ref);
		}
		// e is a reference of array
		else if (e instanceof ArrayReference) {
			ArrayReference ref = (ArrayReference) e;
			if (ref.getRef() instanceof ArrayVarAccess) {
				ArrayVarAccess ava = (ArrayVarAccess) ref.getRef();
				fr.lip6.move.gal.ArrayVarAccess gava; 
				// check if global array
				gava = garraymap.get(ava.getPrefix());
				if (gava != null) {
					gava.setIndex(convertToGalInt(ava.getIndex()));
					return EcoreUtil.copy(gava);
				}
				else {
					// local array
					gava = larraymap.get(ava.getPrefix());
					if (gava != null) {
						gava.setIndex(convertToGalInt(ava.getIndex()));
						return EcoreUtil.copy(gava);
					}
					else
						throw new UnsupportedOperationException("Unmapped array "+ ava);
				}
			}
			else 
				throw new UnsupportedOperationException("Unmapped array "+ ref);
		}
		
		return target;
	}
	
	private BooleanExpression createComparison(VariableRef vref,
			ComparisonOperators op, fr.lip6.move.gal.Constant value) {
		fr.lip6.move.gal.Comparison guard = GalFactory.eINSTANCE.createComparison();
		guard.setLeft(vref);
		guard.setOperator(op);
		guard.setRight(value);
		return guard;
	}
	private BooleanExpression createComparison(ParamRef pref,
			ComparisonOperators op, fr.lip6.move.gal.Constant value) {
		fr.lip6.move.gal.Comparison guard = GalFactory.eINSTANCE.createComparison();
		guard.setLeft(pref);
		guard.setOperator(op);
		guard.setRight(value);
		return guard;
	}
	private BooleanExpression createComparison(fr.lip6.move.gal.ArrayVarAccess ava,
			ComparisonOperators op, fr.lip6.move.gal.Constant value) {
		fr.lip6.move.gal.Comparison guard = GalFactory.eINSTANCE.createComparison();
		guard.setLeft(ava);
		guard.setOperator(op);
		guard.setRight(value);
		return guard;
	}


//	public void clearVars() {
//		varmap = new HashMap<DeclId, fr.lip6.move.gal.ArrayVarAccess>();
//	}
//
//
//	public void clearGlobals() {
//		gvarmap = new HashMap<DeclId, fr.lip6.move.gal.IntExpression> ();		
//	}

}
