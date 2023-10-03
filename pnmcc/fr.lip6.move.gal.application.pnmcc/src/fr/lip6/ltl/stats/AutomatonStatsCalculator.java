package fr.lip6.ltl.stats;

import java.util.List;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBAEdge;
import fr.lip6.move.gal.structural.expr.Expression;

public class AutomatonStatsCalculator {

    public static AutomatonStats computeStats(TGBA a, String name) {
        AutomatonStats stats = new AutomatonStats(name);

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
