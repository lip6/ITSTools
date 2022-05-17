package fr.lip6.lola.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import fr.lip6.lola.io.LolaParser.MarkContext;
import fr.lip6.lola.io.LolaParser.PlaceContext;
import fr.lip6.lola.io.LolaParser.PostvalueContext;
import fr.lip6.lola.io.LolaParser.PrevalueContext;
import fr.lip6.lola.io.LolaParser.TransitionContext;
import fr.lip6.move.gal.structural.SparsePetriNet;

public class LolaImporter {

	public static SparsePetriNet loadLola (String path) throws IOException {
		long time = System.currentTimeMillis();
		CharStream codePointCharStream = CharStreams.fromFileName(path);
		LolaLexer lexer = new LolaLexer(codePointCharStream);
		LolaParser parser = new LolaParser(new CommonTokenStream(lexer));
		
		final SparsePetriNet pn = new SparsePetriNet();
		pn.setName(path);
		
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

		
		parser.addParseListener(new LolaBaseListener() {
			int curtrans;
			Map<String,Integer> pmap = new HashMap<>(); 
			
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
				int val = 1;
				if (ctx.val != null) {
					val = Integer.parseInt(ctx.val.getText());
				}
				pn.getMarks().set(id, val);
			}
			
			@Override
			public void exitTransition(TransitionContext ctx) {
				String tname = ctx.name.getText();
				curtrans = pn.addTransition(tname);
			}
			
			@Override
			public void exitPrevalue(PrevalueContext ctx) {
				String pname = ctx.pref.getText();
				int pid = pmap.get(pname);
				int val = 1;
				if (ctx.val != null) {
					val = Integer.parseInt(ctx.val.getText());
				}
				pn.addPreArc(pid, curtrans, val);
			}

			@Override
			public void exitPostvalue(PostvalueContext ctx) {
				String pname = ctx.pref.getText();
				int pid = pmap.get(pname);
				int val = 1;
				if (ctx.val != null) {
					val = Integer.parseInt(ctx.val.getText());
				}
				pn.addPostArc(pid, curtrans, val);
			}
		});

		// Start parsing
		parser.net(); 
		
		System.out.println("Parsed Lola format file at "+path+ " to a net with " +pn.getPlaceCount() + " places " + pn.getTransitionCount() + " transitions and " + pn.getArcCount() + " arcs in "+ (System.currentTimeMillis()-time) + " ms.");        	
		
		return pn;				
	}
	
	
}
