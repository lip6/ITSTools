package fr.lip6.move.gal.structural.expr;

import java.util.List;
import java.util.function.Function;

import android.util.SparseIntArray;

public class NaryOp implements Expression {
	private Op op;
	private List<Expression> children;	
		
	public NaryOp(Op op, List<Expression> children) {
		this.op = op;
		this.children = children;
	}
	
	public Op getOp() {
		return op;
	}
	
	@Override
	public int eval(SparseIntArray state) {
		switch (op) {
		case AND :
		{
			boolean res = true;
			for (Expression e : children) {
				if (e.eval(state) != 1) {
					res = false;
					break;
				}
			}
			return res?1:0;
		}
		case OR :
		{
			boolean res = false;
			for (Expression e : children) {
				if (e.eval(state) == 1) {
					res = true;
					break;
				}
			}
			return res?1:0;
		}
		case ADD :
		{
			int res = 0;
			for (Expression e : children) {
				res += e.eval(state);
			}
			return res;
		}
		case MULT :
		{
			int res = 1;
			for (Expression e : children) {
				res *= e.eval(state);
			}
			return res;
		}
		default :
			throw new UnsupportedOperationException();			
		}
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {
		if (! isNeg) {
			// Boolean cases
			switch (op) {
			case AND:
			{
				int sum = 0;
				for (Expression child : getChildren()) {
					sum += child.evalDistance(state, isNeg);
				}
				return sum;
			}
			case OR:
			{
				int min = Integer.MAX_VALUE;
				for (Expression child : getChildren()) {
					min = Math.min(min, child.evalDistance(state, isNeg));
				}
				return min;				
			}
			case ADD:
			{
				//BOUNDS only
				return eval(state);
			}
			default:
			}
		} else {
			// Boolean cases
			switch (op) {
			case AND:
			{
				int min = Integer.MAX_VALUE;
				for (Expression child : getChildren()) {
					min = Math.min(min, child.evalDistance(state, isNeg));
				}
				return min;				
			}
			case OR:
			{
				int sum = 0;
				for (Expression child : getChildren()) {
					sum += child.evalDistance(state, isNeg);
				}
				return sum;
			}
			default:
			}			
		}
		throw new RuntimeException("Unexpected operator type in expression " + op);
	}
	
	public void addChild(Expression child) {
		children.add(child);
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}
	
	public List<Expression> getChildren() {
		return children;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(op).append(" ");
		boolean first = true;
		for (Expression c : children) {
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(c);
		}
		sb.append(")");
		return sb.toString();
	}
	
	@Override
	public <T> void forEachChild(Function<Expression, T> foo) {
		for (Expression c : children) {
			foo.apply(c);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 9157;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NaryOp other = (NaryOp) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (op != other.op)
			return false;
		return true;
	}

	@Override
	public int nbChildren() {
		return children.size();
	}

	@Override
	public Expression childAt(int index) {
		return children.get(index);
	}
	
}
