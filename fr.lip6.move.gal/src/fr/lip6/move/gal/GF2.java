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
		if (l instanceof False) {
			return l;
		}
		if (r instanceof False) {
			return r;
		}
		if (EcoreUtil.equals(l, r)) {
			return l;
		}
		And and = GalFactory.eINSTANCE.createAnd();
		and.setLeft(l);
		and.setRight(r);
		return and;
	}
	
	public static BooleanExpression or(BooleanExpression l, BooleanExpression r) {
		if (l instanceof False) {
			return r;
		}
		if (r instanceof False) {
			return l;
		}
		if (l instanceof True) {
			return l;
		}
		if (r instanceof True) {
			return r;
		}
		if (EcoreUtil.equals(l, r)) {
			return l;
		}

		Or or = GalFactory.eINSTANCE.createOr();
		or.setLeft(l);
		or.setRight(r);
		return or;
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


	public static BooleanExpression createComparison (IntExpression left, ComparisonOperators op, IntExpression right) {
		Comparison comp = GalFactory.eINSTANCE.createComparison();
		comp.setOperator(op);
		comp.setLeft(left);
		comp.setRight(right);
		return comp;
	}
	
	public static Statement createAssignment(VariableReference lhs, IntExpression rhs) {
		return createTypedAssignment(lhs, AssignType.ASSIGN, rhs);
	}
	
	public static Statement createTypedAssignment(VariableReference lhs, AssignType type, IntExpression rhs) {
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft(lhs);
		if (type != AssignType.ASSIGN) {
			if (rhs instanceof UnaryMinus) {
				UnaryMinus umin = (UnaryMinus) rhs;
				rhs = umin.getValue();
				if (type == AssignType.INCR)
					type = AssignType.DECR;
				else 
					type = AssignType.INCR;
			}			
		}
		ass.setType(type);
		ass.setRight(rhs);
		return ass;
	}
	
	public static Label createLabel (String name) {
		Label lab = GalFactory.eINSTANCE.createLabel();
		lab.setName(name);
		return lab;
	}

	public static ParamRef createParamRef(AbstractParameter param) {
		ParamRef pref = GalFactory.eINSTANCE.createParamRef();
		pref.setRefParam(param);
		return pref;
	}

	public static VariableReference createArrayVarAccess(ArrayPrefix array, IntExpression index) {
		VariableReference ava = GalFactory.eINSTANCE.createVariableReference();
		ava.setRef(array);
		ava.setIndex(index);
		return ava ;
	}

	public static VariableReference createVariableRef(NamedDeclaration var) {
		VariableReference vref = GalFactory.eINSTANCE.createVariableReference();
		vref.setRef(var);
		return vref ;
	}



	public static BooleanExpression not(BooleanExpression b) {
		if (b instanceof True) {
			return GalFactory.eINSTANCE.createFalse();
		}
		if (b instanceof False) {
			return GalFactory.eINSTANCE.createTrue();
		}
		if (b instanceof Not) {
			return EcoreUtil.copy(((Not)b).getValue());
		}
		Not not = GalFactory.eINSTANCE.createNot();
		not.setValue(b);
		return not;
	}

	public static Statement createIncrement(Variable v, int n) {
		return createIncrement(createVariableRef(v), n);
	}

	public static Statement createIncrement(VariableReference va, int n) {
		if (n >= 0) {
			return createTypedAssignment(EcoreUtil.copy(va), AssignType.INCR, constant(n));
		} else {
			return createTypedAssignment(EcoreUtil.copy(va), AssignType.DECR, constant(-n));
		}
	}

	public static InstanceDeclaration createInstance(TypeDeclaration typeDeclaration, String string) {
		InstanceDeclaration decl = GalFactory.eINSTANCE.createInstanceDeclaration();
		decl.setType(typeDeclaration);
		decl.setName(string);
		return decl;
	}

	public static InstanceCall createInstanceCall(VariableReference inst, Label label) {
		InstanceCall call = GalFactory.eINSTANCE.createInstanceCall();
		call.setInstance(inst);
		call.setLabel(label);
		return call ;
	}

	public static Statement createAssignVarConst(Variable variable, int constant) {
		return createAssignment(createVariableRef(variable), constant(constant));
	}

	public static TypedefDeclaration createTypeDef(String name, int min, int max) {
		TypedefDeclaration td = GalFactory.eINSTANCE.createTypedefDeclaration();
		td.setName(name);
		td.setMin(constant(min));
		td.setMax(constant(max));
		return td ;
	}

	public static Parameter createParameter(String name, TypedefDeclaration type) {
		Parameter param = GalFactory.eINSTANCE.createParameter();
		param.setName(name);
		param.setType(type);
		return param ;
	}

	public static Transition createTransition(String name) {
		Transition tr = GalFactory.eINSTANCE.createTransition();
		tr.setName(name);
		return tr ;
	}

	public static Statement createSelfCall(Label lab) {
		SelfCall call = GalFactory.eINSTANCE.createSelfCall();
		call.setLabel(lab);
		return call ;
	}

	
}
