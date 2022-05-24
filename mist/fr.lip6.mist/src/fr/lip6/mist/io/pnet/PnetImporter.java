package fr.lip6.mist.io.pnet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import fr.lip6.mist.io.pnet.PnetParser.ArcContext;
import fr.lip6.mist.io.pnet.PnetParser.InitDeclContext;
import fr.lip6.mist.io.pnet.PnetParser.NetContext;
import fr.lip6.mist.io.pnet.PnetParser.PlaceContext;
import fr.lip6.mist.io.pnet.PnetParser.PlaceDeclContext;
import fr.lip6.mist.io.pnet.PnetParser.SingleArcContext;
import fr.lip6.mist.io.pnet.PnetParser.TransDeclContext;
import fr.lip6.mist.io.pnet.PnetParser.TransitionContext;
import fr.lip6.move.gal.structural.SparsePetriNet;

public class PnetImporter {

	public static SparsePetriNet loadPnet (String path) throws IOException {
		long time = System.currentTimeMillis();
		CharStream codePointCharStream = CharStreams.fromFileName(path);
		PnetLexer lexer = new PnetLexer(codePointCharStream);
		PnetParser parser = new PnetParser(new CommonTokenStream(lexer));
		
		final SparsePetriNet pn = new SparsePetriNet();
		
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

		
		parser.addParseListener(new PnetBaseListener() {
			Map<String,Integer> pmap = new HashMap<>();
			Map<String,Integer> tmap = new HashMap<>();
			
			@Override
			public void exitNet(NetContext ctx) {
				pn.setName(ctx.name.getText());
			}

			@Override
			public void exitPlaceDecl(PlaceDeclContext ctx) {
				for (PlaceContext p : ctx.decl.places) {
					String pname = p.name.getText();
					int id = pn.addPlace(pname, 0);
					pmap.put(pname, id);
				}
			}
			
			@Override
			public void exitTransDecl(TransDeclContext ctx) {
				for (TransitionContext t : ctx.transitions) {
					String tname = t.name.getText();
					int id = pn.addTransition(tname);
					tmap.put(tname, id);
				}
			}
			
			@Override
			public void exitArc(ArcContext ctx) {
				String tname = ctx.tref.getText();
				int tid = tmap.get(tname);
				if (ctx.pre != null) {
					for (PlaceContext p : ctx.pre.places) {
						String pname=p.name.getText();
						int pid = pmap.get(pname);
						pn.addPreArc(pid, tid, 1);
					}
				}
				if (ctx.post != null) {
					for (PlaceContext p : ctx.post.places) {
						String pname=p.name.getText();
						int pid = pmap.get(pname);
						pn.addPostArc(pid, tid, 1);
					}
				}
			}
			
			
			@Override
			public void exitSingleArc(SingleArcContext ctx) {
				String tname = ctx.tref.getText();
				int tid = tmap.get(tname);
				{
					String pname=ctx.pre.getText();
					int pid = pmap.get(pname);
					pn.addPreArc(pid, tid, 1);
				}
				{
					String pname=ctx.post.getText();
					int pid = pmap.get(pname);
					pn.addPostArc(pid, tid, 1);
				}
			}
			
			@Override
			public void exitInitDecl(InitDeclContext ctx) {
				for (PlaceContext p : ctx.init.places) {
					String pname=p.name.getText();
					int pid = pmap.get(pname);
					pn.getMarks().set(pid, 1);
				}			
			}

		});

		// Start parsing
		parser.net(); 
		
		System.out.println("Parsed Pnet format file at "+path+ " to a net with " +pn.getPlaceCount() + " places " + pn.getTransitionCount() + " transitions and " + pn.getArcCount() + " arcs in "+ (System.currentTimeMillis()-time) + " ms.");        	
		
		return pn;				
	}
	
	
}
