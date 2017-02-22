package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.util.GalSwitch;

/**
 * Builds an INext representation of the semantics of a GAL type.
 * Uses an offset to handle multiple instances within a composite.
 * Please use the factory operation in INextBuilder to instantiate this class.
 * @author ythierry
 *
 */
public class GalNextBuilder extends GalSwitch<INext> implements INextBuilder {
	private VariableIndexer index;
	private Map<String, List<Transition>> labMap;

	public GalNextBuilder(GALTypeDeclaration gal, int offset) {
		index = new VariableIndexer(gal, offset);
		labMap = gal.getTransitions().stream()
				.collect(Collectors.groupingBy(t -> t.getLabel() != null ? t.getLabel().getName() : ""));
	}

	@Override
	public int size() {
		return index.getSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lip6.move.gal.gal2java.INextBuilder#getNextForLabel(java.lang.String)
	 */
	@Override
	public List<INext> getNextForLabel(String lab) {
		List<INext> total = new ArrayList<INext>();
		List<Transition> l = labMap.get(lab);
		if (l == null) {
			if (! "".equals(lab))
				Logger.getLogger("fr.lip6.move.gal").warn("No such label :" + lab + ":");
			return Collections.singletonList(caseAbort(null));
		}
		for (Transition t : l) {
			total.add(doSwitch(t));
		}
		return total;
	}

	@Override
	public INext caseGALTypeDeclaration(GALTypeDeclaration gal) {
		return Alternative.alt(getNextForLabel(""));
	}

	@Override
	public INext caseBooleanExpression(BooleanExpression be) {
		return new Predicate(be, index);
	}

	@Override
	public INext caseTransition(Transition trans) {
		List<INext> full = new ArrayList<>(trans.getActions().size() + 1);
		if (!(trans.getGuard() instanceof True)) {
			INext g = doSwitch(trans.getGuard());
			full.add(g);
		}
		for (Statement st : trans.getActions()) {
			full.add(doSwitch(st));
		}
		return Sequence.seq(full);
	}

	@Override
	public INext caseAbort(Abort object) {
		return new Predicate(GalFactory.eINSTANCE.createFalse(), index);
	}

	@Override
	public INext caseAssignment(Assignment ass) {
		return new Assign(ass, index);
	}

	@Override
	public INext caseSelfCall(SelfCall call) {
		return Alternative.alt(getNextForLabel(call.getLabel().getName()));
	}

	@Override
	public INext caseIte(Ite ite) {
		BooleanExpression be = ite.getCond();
		List<INext> iftrue = new ArrayList<INext>();
		iftrue.add(doSwitch(be));
		for (Statement st : ite.getIfTrue()) {
			iftrue.add(doSwitch(st));
		}
		INext tr = Sequence.seq(iftrue);

		BooleanExpression ne = GF2.not(be);
		List<INext> iffalse = new ArrayList<INext>();
		iffalse.add(doSwitch(ne));
		for (Statement st : ite.getIfFalse()) {
			iffalse.add(doSwitch(st));
		}
		INext fr = Sequence.seq(iffalse);

		List<INext> total = new ArrayList<INext>(2);
		total.add(tr);
		total.add(fr);

		return Alternative.alt(total);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lip6.move.gal.gal2java.INextBuilder#getInitial()
	 */
	@Override
	public List<Integer> getInitial() {
		return index.getInitial();
	}

	@Override
	public int getIndex(Reference ref) {
		if (ref instanceof VariableReference) {
			VariableReference vref = (VariableReference) ref;
			int ind = index.getIndex(vref.getRef().getName());
			if (vref.getIndex() != null) {
				ind += Instantiator.evalConst(vref.getIndex());
			}			
			return ind;
		}
		throw new UnsupportedOperationException();
	}

}
