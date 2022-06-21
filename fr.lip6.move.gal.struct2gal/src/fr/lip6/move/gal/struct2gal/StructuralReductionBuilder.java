package fr.lip6.move.gal.struct2gal;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.structural.FlowMatrix;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.IntMatrixCol;

public class StructuralReductionBuilder {

	public static StructuralReduction createStructuralReduction(IDeterministicNextBuilder idnb) {
		FlowMatrix fm = new MatrixBuilder(idnb).getMatrix();
		List<String> tnames = new ArrayList<>();
		int sz = idnb.getDeterministicNext().size();
		for (int i=0 ; i < sz ; i++) {
			tnames.add("t"+i);
		}
		List<Expression> image = new ArrayList<> (idnb.getVariableNames().size());
		for (int i=0,ie=idnb.getVariableNames().size(); i < ie ; i++) {
			image.add(Expression.var(i));
		}
		List<Integer> marks = new ArrayList<>(idnb.getInitial());
		IntMatrixCol flowPT = fm.getFlowPT();
		IntMatrixCol flowTP = fm.getFlowTP();
		List<String> pnames = new ArrayList<>(idnb.getVariableNames());
		
		int maxArcValue = flowPT.findMax();
		maxArcValue = Math.max(flowTP.findMax(),maxArcValue);
		BitSet untouchable = new BitSet();
		BitSet tokeepImages = new BitSet();
		return new StructuralReduction(image, marks, flowPT, flowTP, tnames, pnames, maxArcValue, untouchable, tokeepImages, false, false);
	}

}
