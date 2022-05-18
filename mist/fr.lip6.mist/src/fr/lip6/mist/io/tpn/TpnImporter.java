package fr.lip6.mist.io.tpn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import fr.lip6.mist.io.tpn.TpnParser.PlaceContext;
import fr.lip6.mist.io.tpn.TpnParser.PostPlaceContext;
import fr.lip6.mist.io.tpn.TpnParser.PrePlaceContext;
import fr.lip6.mist.io.tpn.TpnParser.TransitionContext;
import fr.lip6.move.gal.structural.SparsePetriNet;

public class TpnImporter {

	public static SparsePetriNet loadSpec (String path) throws IOException {
		long time = System.currentTimeMillis();
		CharStream codePointCharStream = CharStreams.fromFileName(path);
		TpnLexer lexer = new TpnLexer(codePointCharStream);
		TpnParser parser = new TpnParser(new CommonTokenStream(lexer));
		
		final SparsePetriNet pn = new SparsePetriNet();
		pn.setName(path);
		
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

		
		parser.addParseListener(new TpnBaseListener() {
			int curtrans;
			Map<String,Integer> pmap = new HashMap<>();

			
			@Override
			public void exitPlace(PlaceContext ctx) {
				String pname = ctx.name.getText();
				int val=0;
				if (ctx.val != null) {
					val = Integer.parseInt(ctx.val.getText());
				}
				int id = pn.addPlace("p"+pn.getPlaceCount(), val);
				pmap.put(pname, id);
			}
			
			@Override
			public void enterTransition(TransitionContext ctx) {
				String tname = "t" + pn.getTransitionCount();
				curtrans = pn.addTransition(tname);
			}
			
			@Override
			public void exitPrePlace(PrePlaceContext ctx) {
				String pname = ctx.pref.getText();
				int pid = pmap.get(pname);
				pn.addPreArc(pid, curtrans, 1);
			}
						
			@Override
			public void exitPostPlace(PostPlaceContext ctx) {
				String pname = ctx.pref.getText();
				int pid = pmap.get(pname);
				pn.addPostArc(pid, curtrans, 1);
			}

		});

		// Start parsing
		parser.spec(); 
		
		System.out.println("Parsed Tpn format file at "+path+ " to a net with " +pn.getPlaceCount() + " places " + pn.getTransitionCount() + " transitions and " + pn.getArcCount() + " arcs in "+ (System.currentTimeMillis()-time) + " ms.");        	
		
		return pn;				
	}
	
	
}
