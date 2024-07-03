package fr.lip6.mist.io.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import fr.lip6.mist.io.properties.PropertiesParser.*;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class PropertiesImporter {

	public List<Property> parseFile (String path, Map<String, Integer> vars) throws IOException {
		long time = System.currentTimeMillis();
		CharStream codePointCharStream = CharStreams.fromFileName(path);
		PropertiesLexer lexer = new PropertiesLexer(codePointCharStream);
		PropertiesParser parser = new PropertiesParser(new CommonTokenStream(lexer));
		
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });
        
        final List<Property> list = new ArrayList<>();
        
        // Start parsing
     	PropertiesVisitor ev = new PropertiesVisitor(list, vars);
     	parser.properties().accept(ev);
     	
		System.out.println("Parsed propertie file at "+path+ " to " + list.size() + " properties in "+ (System.currentTimeMillis()-time) + " ms.");        	
		
		return list;	
	}
	
	
	class PropertiesVisitor extends PropertiesBaseVisitor<Expression> {

		private List<Property> list;
		private Map<String,Expression> atoms = new HashMap<>();
		private Map<String, Integer> variables;

		public PropertiesVisitor(List<Property> list, Map<String, Integer> vars) {
			this.list = list;
			this.variables = vars;
		}
		
		
		@Override
		public Expression visitProperties(PropertiesContext ctx) {
			for (PropertyContext prop : ctx.property()) {
				PropertyType type = PropertyType.UNKNOWN;
				Expression expr = null;
				
				String name = prop.name.getText();
				if (name.startsWith("\"")) {
					name = name.substring(1, name.length()-1);
				}
				// cases for property type
				LogicPropContext logic = prop.logicProp();
				if (logic.boundsProp() != null) {
					type = PropertyType.BOUNDS;
					expr = logic.boundsProp().target.accept(this);
                } else {
                	BoolPropContext boolProp = logic.boolProp();

                	if (boolProp.safetyProp() != null) {
                		type = PropertyType.INVARIANT;
                		if (boolProp.safetyProp().reachableProp() != null) {
                			expr = Expression.nop(Op.EF, boolProp.safetyProp().reachableProp().predicate.accept(this));
                		} else {
                			expr = Expression.nop(Op.AG, boolProp.safetyProp().invariantProp().predicate.accept(this));
                		}
                	} else if (boolProp.ctlProp() != null) {
                		type = PropertyType.CTL;
                		expr = boolProp.ctlProp().predicate.accept(this);
                	} else if (boolProp.ltlProp() != null) {
                		type = PropertyType.LTL;
                		expr = boolProp.ltlProp().predicate.accept(this);
                	} else if (boolProp.atomicProp() != null) {
                		// special case                			
                		expr = boolProp.atomicProp().predicate.accept(this);
                		atoms.put(name, expr);
                		// next property
                		continue;
                	} else {
                		throw new IllegalArgumentException("Unknown property type " + boolProp.getText());
                	}
                }
				
				Property newprop = new Property(expr, type, name);				
				list.add(newprop);
			}
			return super.visitProperties(ctx);
		}
		
		@Override
		public Expression visitCtlPrimaryBool(CtlPrimaryBoolContext ctx) {
			if (ctx.nested != null) {
				return ctx.nested.accept(this);
			}
			return super.visitCtlPrimaryBool(ctx);
		}
		
		@Override
		public Expression visitLtlPrimaryBool(LtlPrimaryBoolContext ctx) {
			if (ctx.nested != null) {
				return ctx.nested.accept(this);
			}
			return super.visitLtlPrimaryBool(ctx);
		}
		
		@Override
		public Expression visitBoundsPredicate(BoundsPredicateContext ctx) {
			List<Expression> bounds = new ArrayList<>();
			for (BpPrimaryContext primary : ctx.bpPrimary()) {
				bounds.add(primary.accept(this));
			}
			return Expression.nop(Op.ADD, bounds);
		}
	
		@Override
		public Expression visitConstant(ConstantContext ctx) {
			return Expression.constant(Integer.parseInt(ctx.getText()));
		}
		
		@Override
		public Expression visitPAddition(PAdditionContext ctx) {
			List<Expression> children = new ArrayList<>();
			for (ParserRuleContext primary : ctx.pPrimary()) {
				children.add(primary.accept(this));
			}
			return Expression.nop(Op.ADD, children);
		}
		
		@Override
		public Expression visitReference(ReferenceContext ctx) {
			String name = ctx.name.getText();
			if (name.startsWith("\"")) {
				name = name.substring(1, name.length()-1);
			}
			Integer index = variables.get(name);
			if (index == null) {
				index = variables.size();
				variables.put(name, index);
			}
			return Expression.var(index);
		}

		@Override
		public Expression visitAtomReference(AtomReferenceContext ctx) {
			String name = ctx.name.getText();
			Expression expr = atoms.get(name);
			if (expr == null) {
				throw new IllegalArgumentException("Unknown atomic property " + name);
			} else {
				return expr;
			}
		}

		
		@Override
		public Expression visitTTrue(TTrueContext ctx) {
			return Expression.constant(true);
		}
		
		@Override
		public Expression visitFFalse(FFalseContext ctx) {
			return Expression.constant(false);
		}
		
		@Override
		public Expression visitPOr(POrContext ctx) {
			List<Expression> children = new ArrayList<>();
			for (ParserRuleContext primary : ctx.pAnd()) {
				children.add(primary.accept(this));
			}
			return Expression.nop(Op.OR, children);
		}
		@Override
		public Expression visitCtlOr(CtlOrContext ctx) {
			List<Expression> children = new ArrayList<>();
			for (ParserRuleContext primary : ctx.ctlAnd()) {
				children.add(primary.accept(this));
			}
			return Expression.nop(Op.OR, children);
		}
		@Override
		public Expression visitLtlOr(LtlOrContext ctx) {
			List<Expression> children = new ArrayList<>();
			for (ParserRuleContext primary : ctx.ltlAnd()) {
				children.add(primary.accept(this));
			}
			return Expression.nop(Op.OR, children);
		}
		
		
		@Override
		public Expression visitPAnd(PAndContext ctx) {
			List<Expression> children = new ArrayList<>();
			for (ParserRuleContext primary : ctx.pNot()) {
				children.add(primary.accept(this));
			}
			return Expression.nop(Op.AND, children);
		}
		@Override
		public Expression visitCtlAnd(CtlAndContext ctx) {
			List<Expression> children = new ArrayList<>();
			for (ParserRuleContext primary : ctx.ctlTemporal()) {
				children.add(primary.accept(this));
			}
			return Expression.nop(Op.AND, children);
		}
		@Override
		public Expression visitLtlAnd(LtlAndContext ctx) {
			List<Expression> children = new ArrayList<>();
			for (ParserRuleContext primary : ctx.ltlUntil()) {
				children.add(primary.accept(this));
			}
			return Expression.nop(Op.AND, children);
		}
		
		@Override
		public Expression visitPNot(PNotContext ctx) {
			if (ctx.isNeg != null) {
				return Expression.nop(Op.NOT, ctx.pPrimaryBool().accept(this));
			} else {
				return ctx.pPrimaryBool().accept(this);
			}
		}
		@Override
		public Expression visitCtlNot(CtlNotContext ctx) {
			if (ctx.isNeg != null) {
				return Expression.nop(Op.NOT, ctx.left.accept(this));
			} else {
				return ctx.left.accept(this);
			}
		}
		@Override
		public Expression visitLtlNot(LtlNotContext ctx) {
			if (ctx.isNeg != null) {
				return Expression.nop(Op.NOT, ctx.ltlPrimaryBool().accept(this));
			} else {
				return ctx.ltlPrimaryBool().accept(this);
			}
		}
		
		private Op readOp(String op) {
			switch (op) {
			case "&&" : return Op.AND;
			case "||" : return Op.OR;
			case "!" : return Op.NOT;
			case "F" : return Op.F;
			case "G" : return Op.G;
			case "X" : return Op.X;
			case "U" : return Op.U;
			case "AG" : return Op.AG;
			case "AF" : return Op.AF;
			case "AX" : return Op.AX;
			case "EG" : return Op.EG;
			case "EF" : return Op.EF;
			case "EX" : return Op.EX;
			case "<" : return Op.LT;
			case "<=" : return Op.LEQ;
			case "==": return Op.EQ;
			case "!=": return Op.NEQ;
			case ">": return Op.GT;
			case ">=": return Op.GEQ;
			
			default : 
				throw new IllegalArgumentException("Unknown operator " + op);
			}
		}
		
		@Override
		public Expression visitPPrimaryBool(PPrimaryBoolContext ctx) {
			if (ctx.nested != null) {
				return ctx.nested.accept(this);
			}
			return super.visitPPrimaryBool(ctx);
		}
		
		@Override
		public Expression visitPComparison(PComparisonContext ctx) {
			Op op = readOp(ctx.operator.getText());
			Expression left = ctx.left.accept(this);
			Expression right = ctx.right.accept(this);
			return Expression.nop(op, left, right);
		}
		
		@Override
		public Expression visitCtlUntil(CtlUntilContext ctx) {
			if (ctx.q == null) {
				return ctx.left.accept(this);
			} else {
				if (ctx.q.getText().equals("A")) {
					return Expression.nop(Op.AU, ctx.left.accept(this), ctx.right.accept(this));
				} else {
					return Expression.nop(Op.EU, ctx.left.accept(this), ctx.right.accept(this));
				}
			}
		}
		
		@Override
		public Expression visitCtlTemporal(CtlTemporalContext ctx) {
			if (ctx.op == null) {
				return ctx.left.accept(this);
			} else {
				Op op = readOp(ctx.op.getText());
				Expression child = ctx.left.accept(this);
				return Expression.nop(op, child);
			}
		}
		
		@Override
		public Expression visitLtlImply(LtlImplyContext ctx) {
			if (ctx.right == null) {
				return ctx.left.accept(this);
			} else {
				Expression left = ctx.left.accept(this);
				Expression right = ctx.right.accept(this);
				return Expression.nop(Op.OR, Expression.nop(Op.NOT, left), right);
			}
		}

		@Override
		public Expression visitCtlImply(CtlImplyContext ctx) {
			if (ctx.right == null) {
				return ctx.left.accept(this);
			} else {
				Expression left = ctx.left.accept(this);
				Expression right = ctx.right.accept(this);
				return Expression.nop(Op.OR, Expression.nop(Op.NOT, left), right);
			}
		}

		@Override
		public Expression visitLtlNext(LtlNextContext ctx) {
			if (ctx.op == null) {
				return ctx.left.accept(this);
			} else {
				return Expression.nop(Op.X, ctx.left.accept(this));
			}
		}
		
		@Override
		public Expression visitLtlFutGen(LtlFutGenContext ctx) {
			if (ctx.op == null) {
				return super.visitLtlFutGen(ctx);
			} else {
				Op op = readOp(ctx.op.getText());
				return Expression.nop(op, ctx.left.accept(this));
			}
		}
		
		@Override
		public Expression visitLtlUntil(LtlUntilContext ctx) {
			if (ctx.right == null) {
				return ctx.left.accept(this);
			} else {
				return Expression.nop(Op.U, ctx.left.accept(this), ctx.right.accept(this));
			}
		}
	}	
}
