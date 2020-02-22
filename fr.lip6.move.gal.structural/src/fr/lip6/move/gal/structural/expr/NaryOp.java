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
			return children.stream().mapToInt(e -> e.eval(state)).allMatch(x -> x==1)?1:0;
		case OR :
			return children.stream().mapToInt(e -> e.eval(state)).anyMatch(x -> x==1)?1:0;
		case ADD :
			return children.stream().mapToInt(e -> e.eval(state)).sum();
		case MULT :
			return children.stream().mapToInt(e -> e.eval(state)).reduce(1, (a, b) -> a * b);
		default :
			throw new UnsupportedOperationException();			
		}
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {
		throw new UnsupportedOperationException();
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
	
}
