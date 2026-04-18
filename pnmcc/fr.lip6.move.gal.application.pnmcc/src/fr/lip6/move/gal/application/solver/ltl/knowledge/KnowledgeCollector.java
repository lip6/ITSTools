package fr.lip6.move.gal.application.solver.ltl.knowledge;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;


import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.mcc.properties.MCCExporter;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class KnowledgeCollector {

	
	private static final int DEBUG = 0;

	public static boolean collectKnowledge(String examination, MccTranslator reader, String pwd, boolean exportKnowledge, boolean exportFalseKnowledge) throws IOException {
		if (! examination.startsWith("LTL")) {
			System.err.println("--export_knowledge flag only is compatible with LTL examination (e.g. LTLCardinality or LTLFireability).");
			return false;
		}
		
		if (reader.getHLPN() != null) {
			reader.createSPN(false,false);				
		}
		SparsePetriNet spn = reader.getSPN();
		spn.toPredicates();
		SpotRunner.exportLTLProperties(spn, "raw"+examination, pwd);
		SpotRunner sr = new SpotRunner(10);

		
		List<TGBA> automata = new ArrayList<>();
		AtomicPropManager atoms = new AtomicPropManager();
		Map<String, Expression> pmap = atoms.loadAtomicProps(spn.getProperties());
		
		
		try {
			for (fr.lip6.move.gal.structural.Property prop : spn.getProperties()) {								
				automata.add(sr.computeTGBA(prop, atoms, pmap));
			}
		} catch (IOException|TimeoutException|InterruptedException e) {
			e.printStackTrace();
		}
			
		// cheap knowledge 
		List<Expression> knowledge = new ArrayList<>(); 
		List<Expression> falseKnowledge = new ArrayList<>(); 
		
		// Initialization and other code
		// Open output files before loop
		PrintWriter knowledgePw = null;
		PrintWriter falseKnowledgePw = null;
		if (exportKnowledge) {
			String knowledgeOutput = pwd + "/" + "knowledge" + "-" + examination + ".ltl";
			knowledgePw = new PrintWriter(new File(knowledgeOutput));
		}
		if (exportFalseKnowledge) {
			String falseKnowledgeOutput = pwd + "/" + "falseKnowledge" + "-" + examination + ".ltl";
			falseKnowledgePw = new PrintWriter(new File(falseKnowledgeOutput));
		}

		// First loop: Add initial state knowledge
		for (TGBA tgba : automata) {
			KnowledgeFacts.addInitialStateKnowledge(knowledge, spn, tgba);
		}
		printAndClear(knowledgePw, falseKnowledgePw, knowledge, falseKnowledge);

		// Second loop: Add next state knowledge and false knowledge
		for (TGBA tgba : automata) {
			KnowledgeFacts.addNextStateKnowledge(knowledge, falseKnowledge, spn, tgba);
		}
		
		
		boolean DEBUGKNOWLEDGE = false;
		
		if (DEBUGKNOWLEDGE) {
			SparsePetriNet spn2 = new SparsePetriNet(spn);
			spn2.getProperties().clear();
			int index = 0;
			for (Expression e: knowledge) {
				spn2.getProperties().add(new fr.lip6.move.gal.structural.Property(e,PropertyType.LTL,"k"+(index++)));
			}
			String outform = pwd + "/" + examination + "." + "next" + ".sr.xml";
			String outsr = pwd + "/model."+ "next" +".sr.pnml";
			MCCExporter.exportToMCCFormat(outsr, outform, spn2);
		}
		
		printAndClear(knowledgePw, falseKnowledgePw, knowledge, falseKnowledge);

		for (TGBA tgba : automata) {
			KnowledgeFacts.addConvergenceKnowledge(knowledge, spn, tgba);
		}
		printAndClear(knowledgePw, falseKnowledgePw, knowledge, falseKnowledge);

		
		for (TGBA tgba : automata) {
			KnowledgeFacts.addInvarianceKnowledge(knowledge, falseKnowledge, spn, tgba);
		}
		
		if (DEBUGKNOWLEDGE) {
			SparsePetriNet spn2 = new SparsePetriNet(spn);
			spn2.getProperties().clear();
			int index = 0;
			for (Expression e: knowledge) {
				spn2.getProperties().add(new fr.lip6.move.gal.structural.Property(Expression.nop(Op.AG,e.childAt(0)),PropertyType.INVARIANT,"k"+(index++)));
			}
			String outform = pwd + "/" + examination + "." + "inv" + ".sr.xml";
			String outsr = pwd + "/model."+ "inv" +".sr.pnml";
			MCCExporter.exportToMCCFormat(outsr, outform, spn2);
		}
		
		
		printAndClear(knowledgePw, falseKnowledgePw, knowledge, falseKnowledge);

		if (exportFalseKnowledge) {
			for (TGBA tgba : automata) {
				falseKnowledge.addAll(KnowledgeFacts.computeEGknowledge(spn, tgba));
			}
		}
		printAndClear(knowledgePw, falseKnowledgePw, knowledge, falseKnowledge);
		
		// Close output files
		if (knowledgePw != null) knowledgePw.close();
		if (falseKnowledgePw != null) falseKnowledgePw.close();
		
		return true;
	}

	// printAndClear function
	private static void printAndClear(PrintWriter knowledgePw, PrintWriter falseKnowledgePw, List<Expression> knowledge, List<Expression> falseKnowledge) {
	    flushKnowledge(knowledgePw, knowledge);
	    flushKnowledge(falseKnowledgePw, falseKnowledge);
	    if (DEBUG >= 1) {
	    	System.out.println("Current knowledge :" + knowledge);
	    }
	    knowledge.clear();
	    falseKnowledge.clear();
	}

	// Utility function to flush knowledge to PrintWriter
	private static void flushKnowledge(PrintWriter pw, List<Expression> exprList) {
	    if (pw == null) return;
	    for (Expression factoid : exprList) {
	        pw.println(SpotRunner.printLTLProperty(factoid));
	    }
	    pw.flush();
	}
}
