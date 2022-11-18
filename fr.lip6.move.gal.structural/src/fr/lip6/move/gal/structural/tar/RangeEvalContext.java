package fr.lip6.move.gal.structural.tar;

import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class RangeEvalContext {

	private PlaceRangeVector ranges;
	private PlaceRangeVector sufficient = new PlaceRangeVector();
	private ISparsePetriNet net;
	private int[] use_count;
	private boolean negated;
	private boolean bool_result;

	public RangeEvalContext(PlaceRangeVector prv, ISparsePetriNet net, int[] use_count) {
		ranges = prv;
		this.net = net;
		this.use_count = use_count;
		this.negated = false;
	}

	public void visit(Expression e) {
		if (e==null) {
			return;
		} else if (e.getOp() == Op.NOT) {
			negated = !negated;
			visit(e.childAt(0));
			negated = !negated;			
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
			
			
		}
	}

}
