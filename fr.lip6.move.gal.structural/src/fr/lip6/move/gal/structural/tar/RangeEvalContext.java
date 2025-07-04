package fr.lip6.move.gal.structural.tar;

import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class RangeEvalContext {

	// current state description as ranges for some places
	private PlaceRangeVector ranges;
	// computing the subset of sufficient conditions for evaluation
	private PlaceRangeVector sufficient = new PlaceRangeVector();
	// the net
	private ISparsePetriNet net;
	// a counter for usefulness of each place
	private int[] use_count;
	// did the last visit edit some ranges (false),
	// or was it impossible to decide with just ranges (true)
	private boolean bool_result;

	public RangeEvalContext(PlaceRangeVector prv, ISparsePetriNet net, int[] use_count) {
		ranges = prv;
		this.net = net;
		this.use_count = use_count;
	}

	public void visit(Expression e) {
		if (e==null) {
			return;
		} else if (e.getOp() == Op.NOT) {
			throw new IllegalArgumentException("Please push negations before using Range eval context.");
		} else if (e.getOp() == Op.AND) {
			PlaceRangeVector cursuff = new PlaceRangeVector(); 
			cursuff.copy(sufficient);
			PlaceRangeVector best = new PlaceRangeVector(); 
			best.copy(sufficient);
			boolean found = false;
			for (int cid=0, cide=e.nbChildren() ; cid < cide ; cid++) {
				visit(e.childAt(cid));
				if (!bool_result) {
					if (!found|| sufficient.size() < best.size()) {
						best.copy(sufficient);
					}
					cursuff.copy(sufficient);
					found=true;
				}
			}
			if (found) {
				sufficient.copy(best);
			} else {
				sufficient.copy(cursuff);
			}
			bool_result = !found; // true on TOS
		} else if (e.getOp() == Op.OR) {
			
			PlaceRangeVector tmpsuff = new PlaceRangeVector();
			tmpsuff.copy(sufficient);
			
		//	for ()
			
		}
	}

}
