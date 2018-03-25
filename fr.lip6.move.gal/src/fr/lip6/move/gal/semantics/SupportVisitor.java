package fr.lip6.move.gal.semantics;

import java.util.BitSet;

import fr.lip6.move.gal.AssignType;

public class SupportVisitor implements NextVisitor<Boolean> {

	protected BitSet read;
	protected BitSet write;
	protected BitSet control;

	public SupportVisitor(BitSet read, BitSet write, BitSet control) {
		this.read = read;
		this.write = write;
		this.control = control;
	}

	
	@Override
	public Boolean visit(Assign ass) {
		NextSupportAnalyzer.computeSupport(ass.getAssignment().getRight(), read, ass.getIndexer());
		if (!NextSupportAnalyzer.computeSupportTerminals(ass.getAssignment().getLeft(), write, ass.getIndexer())) {
			NextSupportAnalyzer.computeSupport(ass.getAssignment().getLeft().getIndex(), read, ass.getIndexer());
		}
		if (ass.getAssignment().getType() != AssignType.ASSIGN) {
			// these vars are also read
			NextSupportAnalyzer.computeSupportTerminals(ass.getAssignment().getLeft(), read, ass.getIndexer());
		}
		return true;
	}

	@Override
	public Boolean visit(Predicate pred) {
		BitSet s = new BitSet();
		NextSupportAnalyzer.computeSupport(pred.getGuard(), s, pred.getIndexer());
		read.or(s);
		control.or(s);
		return true;
	}

	@Override
	public Boolean visit(Alternative alt) {
		for (INext n : alt.getAlternatives()) {
			n.accept(this);
		}
		return true;
	}

	@Override
	public Boolean visit(Sequence seq) {
		for (INext n : seq.getActions()) {
			n.accept(this);
		}
		return true;
	}
	
}
