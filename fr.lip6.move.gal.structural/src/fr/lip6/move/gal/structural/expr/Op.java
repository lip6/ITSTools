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
	APREF, // a reference to an atomic prop in a formula
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
		case F: return G;
		case G: return F;
		case X: return X;
		case U: return U;
		case AF: return EG;
		case EG:return EF;
		case EF:return AG;
		case AG:return EF;
		case EX:return AX;
		case AX:return EX;
		case AND:return OR;
		case OR:return AND;
		default :
			throw new IllegalArgumentException();
		}
	}

	public static boolean isComparison(Op op) {
		switch (op) {
		case EQ:
		case NEQ:
		case GEQ:
		case GT:
		case LEQ:
		case LT:
			return true;
		default:
			return false;
		}
	}
	
}
