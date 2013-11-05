package fr.lip6.move.xta.togal.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.timedAutomata.And;
import fr.lip6.move.timedAutomata.BinaryIntExpression;
import fr.lip6.move.timedAutomata.Comparison;
import fr.lip6.move.timedAutomata.Constant;
import fr.lip6.move.timedAutomata.DeclId;
import fr.lip6.move.timedAutomata.False;
import fr.lip6.move.timedAutomata.FormalDeclaration;
import fr.lip6.move.timedAutomata.IntExpression;
import fr.lip6.move.timedAutomata.Not;
import fr.lip6.move.timedAutomata.Or;
import fr.lip6.move.timedAutomata.Parameter;
import fr.lip6.move.timedAutomata.True;
import fr.lip6.move.timedAutomata.VarAccess;

public class Converter {
	
	// maps global variables to their (ref)image in gal (could be type parameters or variables)
	private Map<DeclId,fr.lip6.move.gal.IntExpression> gvarmap;
	
	// maps variables to their array in gal
	private Map<DeclId,fr.lip6.move.gal.ArrayVarAccess> varmap;
	// maps parameters to their array in gal
	private Map<Parameter,fr.lip6.move.gal.ArrayVarAccess> parammap ;


	
	public fr.lip6.move.gal.BooleanExpression convertToGAL(
			fr.lip6.move.timedAutomata.BooleanExpression b) {
		if (b instanceof True) {
			return GalFactory.eINSTANCE.createTrue();
		} else if (b instanceof False) {
			return GalFactory.eINSTANCE.createFalse();
		} else if (b instanceof And) {
			And and = (And) b;
			fr.lip6.move.gal.And rand = GalFactory.eINSTANCE.createAnd();
			rand.setLeft(convertToGAL(and.getLeft()));
			rand.setRight(convertToGAL(and.getRight()));
			return rand;
		} else if (b instanceof Or) {
			Or and = (Or) b;
			fr.lip6.move.gal.Or rand = GalFactory.eINSTANCE.createOr();
			rand.setLeft(convertToGAL(and.getLeft()));
			rand.setRight(convertToGAL(and.getRight()));
			return rand;
		} else if (b instanceof Not) {
			Not and = (Not) b;
			fr.lip6.move.gal.Not rand = GalFactory.eINSTANCE.createNot();
			rand.setValue(convertToGAL(and.getValue()));
			return rand;
		} else if (b instanceof Comparison) {
			Comparison comp = (Comparison) b;
			fr.lip6.move.gal.Comparison rcomp = GalFactory.eINSTANCE.createComparison();
			rcomp.setOperator(fr.lip6.move.gal.ComparisonOperators.get(comp.getOperator().getValue()));
			rcomp.setLeft(convertToGAL(comp.getLeft()));
			rcomp.setRight(convertToGAL(comp.getRight()));
			return rcomp;
		} 
		throw new ArrayIndexOutOfBoundsException("Unexpected boolean expression type in conversion "+b);
	}


	public fr.lip6.move.gal.IntExpression convertToGAL(IntExpression value) {
		if (value instanceof VarAccess) {
			FormalDeclaration fd = ((VarAccess) value).getRef();
			if (fd instanceof DeclId) {
				DeclId did = (DeclId) fd;
				// Global var 
				fr.lip6.move.gal.IntExpression vd = gvarmap.get(did);				
				if (vd != null) {					
					return EcoreUtil.copy(vd);
				} else {
					// local var
					ArrayVarAccess va = varmap.get(did);
					if (va==null) {
						throw new ArrayIndexOutOfBoundsException("Unmapped variable "+did);
					}
					return EcoreUtil.copy(va);
				}
			} else {
				Parameter param = (Parameter) fd;
				ArrayVarAccess va = parammap.get(param);
				if (va==null) {
					throw new ArrayIndexOutOfBoundsException("Unmapped variable "+param);
				}
				return EcoreUtil.copy(va);
			}						
		} else if (value instanceof Constant) {
			Constant cte = (Constant) value;
			fr.lip6.move.gal.Constant tcte = GalFactory.eINSTANCE.createConstant();
			tcte.setValue(cte.getValue());
			return tcte;

		} else if (value instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) value;
			fr.lip6.move.gal.BinaryIntExpression rbin = GalFactory.eINSTANCE.createBinaryIntExpression();
			rbin.setOp(bin.getOp());
			rbin.setLeft(convertToGAL(bin.getLeft()));
			rbin.setRight(convertToGAL(bin.getRight()));
			return rbin;
		}		
		throw new ArrayIndexOutOfBoundsException("Unexpected integer expression type in conversion "+value);
	}


	public void clearParams() {
		parammap = new HashMap<Parameter, fr.lip6.move.gal.ArrayVarAccess>();
	}


	public void addParameter(Parameter param, ArrayVarAccess ava) {
		parammap.put(param, ava);
	}


	public void clearVars() {
		varmap = new HashMap<DeclId, fr.lip6.move.gal.ArrayVarAccess>();
	}


	public void addVariable(DeclId did, ArrayVarAccess ava) {
		varmap.put(did, ava);
	}


	public void updateParam(ParamRef pref) {
		// update varmap with current context
		for (Entry<DeclId, ArrayVarAccess> e : varmap.entrySet()) {
			e.getValue().setIndex(EcoreUtil.copy(pref));
		}
		for (Entry<Parameter, ArrayVarAccess> e : parammap.entrySet()) {
			e.getValue().setIndex(EcoreUtil.copy(pref));
		}
	}


	public void clearGlobals() {
		gvarmap = new HashMap<DeclId, fr.lip6.move.gal.IntExpression> ();		
	}


	public void addGlobal(DeclId did, fr.lip6.move.gal.IntExpression vr) {
		gvarmap.put(did, vr);
	}


	public fr.lip6.move.gal.IntExpression getImage(DeclId var) {
		return varmap.get(var);
	}

}
