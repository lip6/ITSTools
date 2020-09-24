package fr.lip6.move.gal.structural.expr;

import java.util.ArrayDeque;
import java.util.List;

public class PrefixParser {

	
	public static Expression parsePrefix (String expr, List<AtomicProp> list) {
		ArrayDeque<Expression> operands = new ArrayDeque<>();
		String [] tokens = expr.split(" ");
		
		for (int index = tokens.length-1 ; index >= 0 ; index--) {
			String token = tokens[index];
			// binary operators 
			if ("&".equals(token)) {
				Expression c1 = operands.pop();
				Expression c2 = operands.pop();				
				operands.push(Expression.op(Op.AND, c1, c2));	
			} else if ("|".equals(token)) {
				Expression c1 = operands.pop();
				Expression c2 = operands.pop();				
				operands.push(Expression.op(Op.OR, c1, c2));					
			} else if ("U".equals(token)) {
				Expression c1 = operands.pop();
				Expression c2 = operands.pop();				
				operands.push(Expression.op(Op.U, c1, c2));				
			} else if ("F".equals(token)) {
				Expression child = operands.pop();
				operands.push(Expression.op(Op.F, child, null));
			} else if ("G".equals(token)) {
				Expression child = operands.pop();
				operands.push(Expression.op(Op.G, child, null));
			} else if ("X".equals(token)) {
				Expression child = operands.pop();
				operands.push(Expression.op(Op.X, child, null));
			} else if ("!".equals(token)) {
				Expression child = operands.pop();
				operands.push(Expression.not(child));
			} else if (token.startsWith("p")) {
				int val = Integer.parseInt(token.substring(1));
				operands.push(list.get(val).getExpression());				
			}
		}
		return operands.pop();
	}
}
