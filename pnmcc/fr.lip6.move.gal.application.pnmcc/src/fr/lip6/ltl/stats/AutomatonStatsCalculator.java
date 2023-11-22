package fr.lip6.ltl.stats;

import java.util.List;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBAEdge;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.structural.expr.Expression;

public class AutomatonStatsCalculator {

    public static AutomatonStats computeStats(TGBA a, String name, String type, long time) {
        AutomatonStats stats = new AutomatonStats(name,type);
        if (a != null) {
        
        // Number of States
        stats.setNumStates(a.nbStates());

        // Number of Acceptance Sets
        stats.setNumAccSets(a.getNbAcceptance());

        // Number of Atomic Propositions
        stats.setNumAP(a.getAPs().size());

        // Check for Stutter Invariance
        stats.setStutterInsensitive(a.isStutterInvariant());

        // Check if Universal Language
        stats.setUniversal(a.isUniversalLanguage());

        // Check if Empty Language
        stats.setEmpty(a.isEmptyLanguage());

        // Calculate number of edges based on the adjacency matrix 'mat'
        int numEdges = a.getEdges().stream().mapToInt(List::size).sum();
        stats.setNumEdges(numEdges);

        List<String> properties = a.getProperties(); // Assuming `a.getProperties()` returns a set of property strings
        stats.setVeryWeak(properties.contains("very-weak"));
        stats.setWeak(properties.contains("weak"));
        stats.setTerminal(properties.contains("terminal"));
        stats.setDeterministic(properties.contains("deterministic"));

        
        int totalFormulaComplexity = 0;
        for (List<TGBAEdge> edgeList : a.getEdges()) {
            for (TGBAEdge edge : edgeList) {
                totalFormulaComplexity += computeExpressionComplexity(edge.getCondition());
            }
        }
        stats.setFormulaComplexity(totalFormulaComplexity);
        
        
        
        {
        	SpotRunner sr = new SpotRunner(10);
        	List<Integer> stat = sr.computeStats(a);
        	if (! stat.isEmpty()) {
        		// %c, number of SCC
        		stats.setNbSCC(stat.get(0));
        		// %[a]c, accepting SCC
        		stats.setNbAccSCC(stat.get(1));
        		// %[r]c, rejecting SCC
        		stats.setNbRejSCC(stat.get(2));
        		// %[v]c, trivial SCC
        		stats.setNbTrivSCC(stat.get(3));
        		// %t, number of transition (with True = 2^AP)
        		stats.setNbTrans(stat.get(4));
        		// %n, number of non deterministic states
        		stats.setNbNonDet(stat.get(5));
        	} else {
        		stats.setNbSCC(-1);
        		// %[a]c, accepting SCC
        		stats.setNbAccSCC(-1);
        		// %[r]c, rejecting SCC
        		stats.setNbRejSCC(-1);
        		// %[v]c, trivial SCC
        		stats.setNbTrivSCC(-1);
        		// %t, number of transition (with True = 2^AP)
        		stats.setNbTrans(-1);
        		// %n, number of non deterministic states
        		stats.setNbNonDet(-1);
        	}
        }

        
    	} else {
    		stats.setFailed(true);
    	}
        
        stats.setTime(System.currentTimeMillis() - time);
        return stats;
    }
    
    public static int computeExpressionComplexity(Expression e) {
        int complexity = 1;  // Count the current node
        switch (e.getOp()) {
            case APREF:
            case BOOLCONST:
                // Leaf nodes, complexity is 1
                break;
            case AND:
            case OR:
                // Binary operators: Complexity is sum of complexities of children
                for (int i = 0; i < e.nbChildren(); ++i) {
                    complexity += computeExpressionComplexity(e.childAt(i));
                }
                break;
            case NOT:
                // Unary operator: Complexity is 1 (itself) + complexity of its child
                complexity += computeExpressionComplexity(e.childAt(0));
                break;
            default:
                throw new IllegalArgumentException("Unrecognized expression " + e);
        }
        return complexity;
    }
    
}
