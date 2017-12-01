package fr.lip6.move.gal.semantics;

import java.util.List;

import fr.lip6.move.gal.Reference;

public class NextBuilderDecorator implements INextBuilder {
	private INextBuilder deco;
		
	public NextBuilderDecorator(INextBuilder deco) {
		this.deco = deco;
	}

	public List<INext> getNextForLabel(String lab) {
		return deco.getNextForLabel(lab);
	}

	public List<Integer> getInitial() {
		return deco.getInitial();
	}

	public List<String> getVariableNames() {
		return deco.getVariableNames();
	}

	public int size() {
		return deco.size();
	}

	public int getIndex(Reference vref) {
		return deco.getIndex(vref);
	}
}
