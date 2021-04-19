package fr.lip6.move.gal.structural;

import java.util.BitSet;
import java.util.List;

import fr.lip6.move.gal.util.IntMatrixCol;

public interface ISparsePetriNet {

	List<Integer> getMarks();

	IntMatrixCol getFlowTP();

	IntMatrixCol getFlowPT();

	int getPlaceCount();

	int getTransitionCount();

	List<String> getTnames();

	List<String> getPnames();

	int getMaxArcValue();

	BitSet computeSupport();

	boolean isSafe();

	void setSafe(boolean isSafe);

}
