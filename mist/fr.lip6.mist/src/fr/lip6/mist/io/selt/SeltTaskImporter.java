package fr.lip6.mist.io.selt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import fr.lip6.mist.io.selt.SeltParser.BoolPredContext;
import fr.lip6.mist.io.selt.SeltParser.ComparisonContext;
import fr.lip6.mist.io.selt.SeltParser.ConstantContext;
import fr.lip6.mist.io.selt.SeltParser.CtlContext;
import fr.lip6.mist.io.selt.SeltParser.ExprContext;
import fr.lip6.mist.io.selt.SeltParser.PlacerefContext;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class SeltTaskImporter {

	Map<String,Integer> pmap = new HashMap<>();
	
	public static String normalizeName(String text) {
		String res = text.replace(' ', '_');
		res = res.replace('-', '_');
		res = res.replace('/', '_');
		res = res.replace('*', 'x');
		res = res.replace('=', '_');
		
		return res;
	}
	
	public boolean loadSeltTask (String path, SparsePetriNet pn) throws IOException {
		long time = System.currentTimeMillis();
		
		CharStream codePointCharStream = CharStreams.fromFileName(path);
		SeltLexer lexer = new SeltLexer(codePointCharStream);
		SeltParser parser = new SeltParser(new CommonTokenStream(lexer));
		
		Property prop = new Property(null, PropertyType.INVARIANT, path.replaceAll(".*/", ""));
		pn.getProperties().add(prop);
        
		parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

		// build an index
		
		for (int pid=0,pide=pn.getPlaceCount() ; pid <pide ; pid++) {
			pmap.put(pn.getPnames().get(pid), pid);
		}

		// Start parsing
		ExprVisitor ev = new ExprVisitor();
		prop.setBody(parser.ctl().accept(ev));
		
		System.out.println("Parsed Selt task file at "+path+ " to a property in "+ (System.currentTimeMillis()-time) + " ms.");        	
		
		return true;				
	}
	
	private class ExprVisitor extends SeltBaseVisitor<Expression> {
				
		@Override
		public Expression visitPlaceref(PlacerefContext ctx) {
			String pname = ctx.name.getText();
			if (pname.startsWith("{") && pname.endsWith("}")) {
				pname = pname.substring(1,pname.length()-1);
				pname = normalizeName(pname);
			}
			
			Integer ppid = pmap.get(pname);
			if (ppid == null) {
				System.err.println("Could not find a place named :'"+pname+"' in net.");
				System.err.println("Known place names :" + pmap.keySet());
			}
			int pid = ppid;
			return Expression.var(pid);
		}
		
		@Override
		public Expression visitBoolPred(BoolPredContext ctx) {
			if (ctx.sub != null) {
				return ctx.sub.accept(this);				
			} else if (ctx.nested != null) {
				return ctx.nested.accept(this);
			} else if (ctx.tt != null) {
				if ("T".equals(ctx.tt.getText())) {
					return Expression.constant(true);
				} else {
					return Expression.constant(false);
				}
			}
						
			Op op;
			String sop = ctx.op.getText();
			if ("/\\".equals(sop)) {
				op = Op.AND;
			} else if ("\\/".equals(sop)) {
				op = Op.OR;
			} else if ("-".equals(sop)) {
				op = Op.NOT;
			} else {
				throw new IllegalArgumentException("The operator whould be boolean, was :"+sop);
			}
			
			if (op == Op.NOT) {
				Expression res = ctx.left.accept(this);
				return Expression.not(res);
			} else {
				return Expression.op(op,ctx.left.accept(this),ctx.right.accept(this));
			}
		}
		
		@Override
		public Expression visitExpr(ExprContext ctx) {
			if (ctx.nested2 != null) {
				return ctx.nested2.accept(this);
			}
			
			Op op;
			if (ctx.op != null) {
				String sop = ctx.op.getText();
				if ("+".equals(sop)) {
					op = Op.ADD;
				} else if ("-".equals(sop)) {
					op = Op.MINUS;
				} else if ("*".equals(sop)) {
					op = Op.MULT;
					Expression l = ctx.l.accept(this);
					Expression r = ctx.r.accept(this);
					int mult = 0;
					Expression rep = null;
					if (l.getOp() == Op.CONST) {
						mult = l.getValue();
						rep = r;
					} else if (r.getOp() == Op.CONST) {
						mult = r.getValue();
						rep = l;
					} else {
						throw new IllegalArgumentException("Only in fine linear constraints supported, one operand of multiplication should be a constant.");						
					} 
						
					List<Expression> toadd = new ArrayList<>();
					for (int i=0; i < mult ; i++) {
						toadd.add(rep);
					}
					return Expression.nop(Op.ADD, toadd);
					
				} else {
					throw new IllegalArgumentException("The operator should be arithmetic, was :"+sop);
				}
				Expression l = ctx.l.accept(this);
				Expression r = ctx.r.accept(this);
				return Expression.op(op, l, r);
			} else {
				return ctx.children.get(0).accept(this);
			}
		}
		@Override
		public Expression visitConstant(ConstantContext ctx) {
			int val = Integer.parseInt(ctx.val.getText());
			return Expression.constant(val);
		}
		
		@Override
		public Expression visitComparison(ComparisonContext ctx) {
			String ops = ctx.op.getText();
			Op op;
			if ("<".equals(ops)) {
				op = Op.LT;
			} else if ("<=".equals(ops)) {
				op = Op.LEQ;
			} else if ("=".equals(ops)) {
				op = Op.EQ;
			} else if ("!=".equals(ops)) {
				op = Op.NEQ;
			} else if (">=".equals(ops)) {
				op = Op.GEQ;
			} else if (">".equals(ops)) {
				op = Op.GT;
			} else {
				throw new IllegalArgumentException("Bad operator for comparison : " + ops);
			}
			Expression l = ctx.lhs.accept(this);
			Expression r = ctx.rhs.accept(this);
			if (l.getOp() == Op.MINUS) {
				return Expression.op(op, l.childAt(0), Expression.op(Op.ADD, l.childAt(1), r));
			} else {
				return Expression.op(op, l, r);
			}
		}
		
		@Override
		public Expression visitCtl(CtlContext ctx) {
			String ops = ctx.op.getText();
			if ("[]".equals(ops)) {
				return Expression.op(Op.AG, ctx.pred.accept(this), null);
			} else if ("<>".equals(ops)){
				return Expression.op(Op.EF, ctx.pred.accept(this), null);				
			} else {
				throw new IllegalArgumentException("Bad temporal operator at head of formula : " + ops);
			}
		}
	}
	
	
}
