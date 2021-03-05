package fr.lip6.move.gal.structural.expr;

public enum Op {
	// unary
	NOT,
	// binary logic
	AND, OR,
	// binary arithmetic
	ADD, MULT, MINUS, DIV, MOD,
	// binary comparisons
	EQ, NEQ, GEQ, GT, LEQ, LT,
	// n-ary property atoms
	ENABLED, CARD, BOUND,
	// 0 arity constants
	CONST, // an integer
	BOOLCONST, // true or false
	DEAD, // a deadlock proposition
	// references to objects of the net
	PARAMREF, // a formal parameter of a transition
	PLACEREF, // a reference to a place
	HLPLACEREF, // a reference to a HL place a.k.a an array
	TRANSREF, // a reference to a transition of the net 
	// CTL unary
	EF, EG, AF, AG, EX, AX,
	// CTL Binary
	EU, AU,
	// LTL unary
	F, G, X,
	// LTL binary
	U;

	public static Op negate(Op op) {
		switch (op) {
		case EQ : return NEQ;
		case NEQ : return EQ;
		case GT : return LEQ;
		case GEQ : return LT;
		case LEQ : return GT;
		case LT : return GEQ;
		default :
			throw new IllegalArgumentException();
		}
	}  
}
