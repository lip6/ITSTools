package fr.lip6.move.gal;

import org.eclipse.emf.ecore.util.EcoreUtil;


public class GF2 {

	public static BooleanExpression and(BooleanExpression l, BooleanExpression r) {
		if (l instanceof True) {
			return r;
		}
		if (r instanceof True) {
			return l;
		}
		And and = GalFactory.eINSTANCE.createAnd();
		and.setLeft(l);
		and.setRight(r);
		return and;
	}

	public static IntExpression constant(int val) {
		Constant tmp = GalFactory.eINSTANCE.createConstant();
		if (val < 0) {
			tmp.setValue(-val);
			UnaryMinus um = GalFactory.eINSTANCE.createUnaryMinus();
			um.setValue(tmp);
			return um;
		} else {
			tmp.setValue(val);
			return tmp;
		}
	}
	
	public static IntExpression createBinaryIntExpression (IntExpression left, String op, IntExpression right) {
		BinaryIntExpression binop = GalFactory.eINSTANCE.createBinaryIntExpression();
		binop.setLeft(left);
		binop.setOp(op);
		binop.setRight(right);
		return binop;
	}

	public static Assignment increment(VarAccess var, Integer value) {
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft(EcoreUtil.copy(var));
		
		BinaryIntExpression op = GalFactory.eINSTANCE.createBinaryIntExpression();		
		op.setLeft(EcoreUtil.copy(var));
		
		if (value >= 0) {
			op.setOp("+");
			op.setRight(constant(value));
		} else {
			op.setOp("-");
			op.setRight(constant(- value));
		}
		
		ass.setRight(op);
		return ass;
	}

	public static BooleanExpression createComparison (IntExpression left, ComparisonOperators op, IntExpression right) {
		Comparison comp = GalFactory.eINSTANCE.createComparison();
		comp.setOperator(op);
		comp.setLeft(left);
		comp.setRight(right);
		return comp;
	}
	
	public static Actions createAssignment(VarAccess lhs, IntExpression rhs) {
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft(lhs);
		ass.setRight(rhs);
		return ass;
	}
	
	public static Label createLabel (String name) {
		Label lab = GalFactory.eINSTANCE.createLabel();
		lab.setName(name);
		return lab;
	}

	public static ParamRef createParamRef(Parameter param) {
		ParamRef pref = GalFactory.eINSTANCE.createParamRef();
		pref.setRefParam(param);
		return pref;
	}

	public static ArrayVarAccess createArrayVarAccess(ArrayPrefix array, IntExpression index) {
		ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
		ava.setPrefix(array);
		ava.setIndex(index);
		return ava ;
	}

	public static VariableRef createVariableRef(Variable var) {
		VariableRef vref = GalFactory.eINSTANCE.createVariableRef();
		vref.setReferencedVar(var);
		return vref ;
	}


	public static Assignment increment (VarAccess var, int value) {
		
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft(EcoreUtil.copy(var));
		
		BinaryIntExpression op = GalFactory.eINSTANCE.createBinaryIntExpression();		
		op.setLeft(EcoreUtil.copy(var));
		
		if (value >= 0) {
			op.setOp("+");
			op.setRight(constant(value));
		} else {
			op.setOp("-");
			op.setRight(constant(- value));
		}
		
		ass.setRight(op);
		return ass;
	}

	
}
