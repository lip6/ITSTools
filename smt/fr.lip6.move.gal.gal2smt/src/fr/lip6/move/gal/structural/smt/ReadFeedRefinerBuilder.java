package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.command.C_assert;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

public class ReadFeedRefinerBuilder {

    public static StaticRefiner buildReadFeedRefiner(ISparsePetriNet sr, IntMatrixCol sumMatrix, List<Integer> representative, SolverState solver) {
        StaticRefiner readFeedRefiner = new StaticRefiner("ReadFeed");
        IFactory ef = solver.getSMT().smtConfig.exprFactory;
        int readConstraints = 0;

        IntMatrixCol tsum = sumMatrix.transpose();
        Map<Integer, List<Integer>> images = SMTUtils.computeImages(representative);

        for (int tid = 0; tid < sumMatrix.getColumnCount(); tid++) {
            List<IExpr> perImage = new ArrayList<>();
            for (int img : images.get(tid)) {
                SparseIntArray pt = sr.getFlowPT().getColumn(img);
                SparseIntArray tp = sr.getFlowTP().getColumn(img);
                List<IExpr> prePlace = new ArrayList<>();

                for (int i = 0; i < pt.size(); i++) {
                    int p = pt.keyAt(i);
                    int v = pt.valueAt(i);
                    if (v > sr.getMarks().get(p) && tp.get(p) > 0) {
                        int delta = v - sr.getMarks().get(p);
                        List<IExpr> couldFeed = new ArrayList<>();
                        SparseIntArray feeders = tsum.getColumn(p);
                        for (int j = 0; j < feeders.size(); j++) {
                            int t2 = feeders.keyAt(j);
                            int v2 = feeders.valueAt(j);
                            if (v2 > 0 && (t2 != tid || images.get(tid).stream().anyMatch(t -> sr.getFlowPT().getColumn(t).get(p) < v))) {
                                if (v2 != 1) {
                                    couldFeed.add(ef.fcn(ef.symbol("*"), ef.symbol("t" + t2), ef.numeral(v2)));
                                } else {
                                    couldFeed.add(ef.symbol("t" + t2));
                                }
                            }
                        }
                        prePlace.add(ef.fcn(ef.symbol(">="), SMTUtils.buildSum(couldFeed), ef.numeral(delta)));
                    }
                }
                if (!prePlace.isEmpty()) {
                    perImage.add(SMTUtils.makeAnd(prePlace));
                } else {
                    perImage.clear();
                    break;
                }
            }
            if (!perImage.isEmpty()) {
                IExpr causal = ef.fcn(ef.symbol("=>"), ef.fcn(ef.symbol(">="), ef.symbol("t" + tid), ef.numeral(1)), SMTUtils.makeOr(perImage));
                VarSet support = SMTUtils.computeSupport(causal);
				readFeedRefiner.addConstraint(new SMTConstraint(new C_assert(causal), support ));
                readConstraints++;
            }
        }

        if (readConstraints > 0) {
            Logger.getLogger("fr.lip6.move.gal").info("State equation strengthened by " + readConstraints + " read => feed constraints.");        
            return readFeedRefiner;
		} else {
			return null;
		}
    }
}
