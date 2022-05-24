package fr.lip6.mist.io.spec;

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

import android.util.SparseIntArray;
import fr.lip6.mist.io.spec.SpecParser.EqVarContext;
import fr.lip6.mist.io.spec.SpecParser.GeqVarContext;
import fr.lip6.mist.io.spec.SpecParser.Mark2Context;
import fr.lip6.mist.io.spec.SpecParser.MarkContext;
import fr.lip6.mist.io.spec.SpecParser.PlaceContext;
import fr.lip6.mist.io.spec.SpecParser.PostvalueContext;
import fr.lip6.mist.io.spec.SpecParser.PrecondContext;
import fr.lip6.mist.io.spec.SpecParser.PrevalueContext;
import fr.lip6.mist.io.spec.SpecParser.TargetDeclContext;
import fr.lip6.mist.io.spec.SpecParser.TransDeclContext;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class SpecImporter {

	public static SparsePetriNet loadSpec (String path) throws IOException {
		long time = System.currentTimeMillis();
		CharStream codePointCharStream = CharStreams.fromFileName(path);
		SpecLexer lexer = new SpecLexer(codePointCharStream);
		SpecParser parser = new SpecParser(new CommonTokenStream(lexer));
		
		final SparsePetriNet pn = new SparsePetriNet();
		pn.setName("MistSpecification"); // path would be another option
		
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

		
		parser.addParseListener(new SpecBaseListener() {
			int curtrans;
			Map<String,Integer> pmap = new HashMap<>();
			SparseIntArray guard;
			SparseIntArray effect;
			List<Expression> targets;
			
			@Override
			public void exitPlace(PlaceContext ctx) {
				String pname = ctx.name.getText();
				int id = pn.addPlace(pname, 0);
				pmap.put(pname, id);
			}
			
			@Override
			public void exitMark(MarkContext ctx) {
				String pname = ctx.pref.getText();
				int id = pmap.get(pname);
				int val = Integer.parseInt(ctx.val.getText());
				pn.getMarks().set(id, val);
			}
			
			@Override
			public void exitMark2(Mark2Context ctx) {
				String pname = ctx.pref.getText();
				int id = pmap.get(pname);
				int val = Integer.parseInt(ctx.val.getText());
				pn.getMarks().set(id, val);
				System.out.println("Constrained initial marking is not supported. Approximating initial constraint : " + pname + ">=" + val + " by " + pname + "=" + val);
			}
			
			@Override
			public void enterTransDecl(TransDeclContext ctx) {
				String tname = "t" + pn.getTransitionCount();
				curtrans = pn.addTransition(tname);
				guard = new SparseIntArray();
				effect = new SparseIntArray();
			}
						
			@Override
			public void exitPrevalue(PrevalueContext ctx) {
				String pname = ctx.prefl.getText();
				// drop last single quote
				pname = pname.substring(0,pname.length()-1);
				if (! pname.equals(ctx.prefr.getText())) {
					throw new IllegalArgumentException("Syntax error spec does not seem to be a Petri net.");
				}
				int pid = pmap.get(pname);
				int val = Integer.parseInt(ctx.val.getText());
				effect.put(pid, - val);
			}

			@Override
			public void exitPostvalue(PostvalueContext ctx) {
				String pname = ctx.prefl.getText();
				// drop last single quote
				pname = pname.substring(0,pname.length()-1);
				if (! pname.equals(ctx.prefr.getText())) {
					throw new IllegalArgumentException("Syntax error spec does not seem to be a Petri net.");
				}
				int pid = pmap.get(pname);
				int val = Integer.parseInt(ctx.val.getText());
				effect.put(pid,  val);
			}
			
			@Override
			public void exitPrecond(PrecondContext ctx) {
				String pname = ctx.pref.getText();
				int pid = pmap.get(pname);
				int val = Integer.parseInt(ctx.val.getText());
				guard.put(pid, val);
			}
			
			@Override
			public void exitTransDecl(TransDeclContext ctx) {
				// guard array is directly the pre arcs
				for (int i=0,ie=guard.size() ; i < ie ; i++) {
					int pid = guard.keyAt(i);
					int val = guard.valueAt(i);
					pn.addPreArc(pid, curtrans, val);
				}
				// compute post arcs as sum of guard end effects
				SparseIntArray post = SparseIntArray.sumProd(1, guard, 1, effect);
				for (int i=0,ie=post.size() ; i < ie ; i++) {
					int pid = post.keyAt(i);
					int val = post.valueAt(i);
					pn.addPostArc(pid, curtrans, val);
				}
				
				guard=null;
				effect=null;
			}
			
			@Override
			public void enterTargetDecl(TargetDeclContext ctx) {
				targets= new ArrayList<>();
			}
			
			@Override
			public void exitGeqVar(GeqVarContext ctx) {
				String pname = ctx.pref.getText();
				int pid = pmap.get(pname);
				int val = Integer.parseInt(ctx.val.getText());
				targets.add(Expression.op(Op.GEQ, Expression.var(pid), Expression.constant(val)));				
			}

			@Override
			public void exitEqVar(EqVarContext ctx) {
				String pname = ctx.pref.getText();
				int pid = pmap.get(pname);
				int val = Integer.parseInt(ctx.val.getText());
				targets.add(Expression.op(Op.EQ, Expression.var(pid), Expression.constant(val)));				
			}
			
			@Override
			public void exitTargetDecl(TargetDeclContext ctx) {
				Property prop = new Property(Expression.op(Op.EF, Expression.nop(Op.AND, targets), null), PropertyType.INVARIANT, "target");
				pn.getProperties().add(prop);
				targets=null;
			}

		});

		// Start parsing
		parser.spec(); 
		
		System.out.println("Parsed Spec format file at "+path+ " to a net with " +pn.getPlaceCount() + " places " + pn.getTransitionCount() + " transitions and " + pn.getArcCount() + " arcs in "+ (System.currentTimeMillis()-time) + " ms.");        	
		
		return pn;				
	}
	
	
}
