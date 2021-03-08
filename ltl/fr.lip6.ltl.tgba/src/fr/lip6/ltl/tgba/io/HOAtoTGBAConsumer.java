package fr.lip6.ltl.tgba.io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.SparseBoolArray;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import jhoafparser.ast.AtomAcceptance;
import jhoafparser.ast.AtomAcceptance.Type;
import jhoafparser.ast.AtomLabel;
import jhoafparser.ast.BooleanExpression;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerException;

public class HOAtoTGBAConsumer implements HOAConsumer {
	private TGBA tgba = new TGBA(0);
	private Map<String,AtomicProp> atomMap = new HashMap<>(); 
	
	public HOAtoTGBAConsumer(List<AtomicProp> atoms) {
		for (AtomicProp ap : atoms) {
			atomMap.put(ap.getName(), ap);
		}
	}

	public TGBA getTGBA() {
		return tgba;
	}
	
	@Override
	public boolean parserResolvesAliases() {
		// aliases are just for decoration
		return true;
	}

	@Override
	public void notifyHeaderStart(String version) throws HOAConsumerException {
		// NOP, who cares.
	}

	@Override
	public void setNumberOfStates(int numberOfStates) throws HOAConsumerException {
		// This is called at beginning, and gives dimension to our TGBA.
		tgba = new TGBA(numberOfStates);
	}

	@Override
	public void addStartStates(List<Integer> stateConjunction) throws HOAConsumerException {
		if (stateConjunction.size() == 1) {
			// let's not have to deal with multiple initial states
			tgba.setInitial(stateConjunction.get(0));
		} else {
			throw new HOAConsumerException("Multiple initial states are not supported.");
		}
	}

	@Override
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException {
		// just ignore these cosmetics
	}

	@Override
	public void setAPs(List<String> aps) throws HOAConsumerException {
		// we should probably already know about these
		tgba.setAPs(aps);
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr)
			throws HOAConsumerException {
		// We only parse TGBA currently
		// This means our acceptance set is composed exclusively of Inf
		// e.g. 2 Inf(0)&Inf(1)
		checkAcceptanceForm(accExpr);		
		
		tgba.setAcceptSize(numberOfSets);
	}

	private static void checkAcceptanceForm(BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException {
		
		if (accExpr.isAND()) {
			checkAcceptanceForm(accExpr.getLeft());
			checkAcceptanceForm(accExpr.getRight());			
		} else if (accExpr.isAtom()) {
			if (accExpr.getAtom().isNegated() || accExpr.getAtom().getType() != Type.TEMPORAL_INF) {
				throw new HOAConsumerException("Only TGBA style acceptance conditions supported currently. Found :" + accExpr);
			}
		}
		
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) throws HOAConsumerException {
		// meh, more cosmetics, just forget it.
	}

	@Override
	public void setName(String name) throws HOAConsumerException {
		// meh, more cosmetics, just forget it.
	}

	@Override
	public void setTool(String name, String version) throws HOAConsumerException {
		// meh, more cosmetics, just forget it.
	}

	@Override
	public void addProperties(List<String> properties) throws HOAConsumerException {
		// let's parse these, they may be non trivial and interesting to us.
		// trans-labels explicit-labels trans-acc stutter-invariant
		// like stutter-invariant is nice to know.
		tgba.addProperties(properties);
	}

	@Override
	public void addMiscHeader(String name, List<Object> content) throws HOAConsumerException {
		// more optional stuff we can ignore 
	}

	@Override
	public void notifyBodyStart() throws HOAConsumerException {
		// ok, go ahead
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature)
			throws HOAConsumerException {
		if (labelExpr != null || accSignature != null) {
			throw new HOAConsumerException("Only TGBA style acceptance conditions supported currently. Found acceptance conditions on state :" + id);
		}
		tgba.addState(id,info);
	}

	@Override
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors, List<Integer> accSignature)
			throws HOAConsumerException {
		throw new HOAConsumerException("Only TGBA style edges supported currently. Found unlabeled edge from:" + stateId + " to " + conjSuccessors);
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors,
			List<Integer> accSignature) throws HOAConsumerException {
		Expression e = toExpression(labelExpr);
		SparseBoolArray sb = new SparseBoolArray();
		if (accSignature != null) {
			for (Integer i : accSignature) {
				sb.append(i, true);
			}
		}
		for (Integer dest : conjSuccessors) {
			tgba.addEdge(stateId, dest, e, sb);
		}
	}

	private Expression toExpression(BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException {
		if (labelExpr.isAND()) {
			return Expression.op(Op.AND, toExpression(labelExpr.getLeft()), toExpression(labelExpr.getRight()));
		} else if (labelExpr.isOR()) {
			return Expression.op(Op.OR, toExpression(labelExpr.getLeft()), toExpression(labelExpr.getRight()));
		} else if (labelExpr.isNOT()) {
			return Expression.not(toExpression(labelExpr.getLeft()));
		} else if (labelExpr.isFALSE()) {
			return Expression.constant(false);
		} else if (labelExpr.isTRUE()) {
			return Expression.constant(true);
		} else if (labelExpr.isAtom()) {
			AtomLabel atom = labelExpr.getAtom();
			return atomMap.get(tgba.getAPs().get(atom.getAPIndex())).getExpression();			 
		}
		throw new HOAConsumerException("Unexpected operator in expression labeling TGBA edge." + labelExpr);
	}

	@Override
	public void notifyEndOfState(int stateId) throws HOAConsumerException {
		// good for you !
	}

	@Override
	public void notifyEnd() throws HOAConsumerException {
		// done

	}

	@Override
	public void notifyAbort() {
		// meh ??
		tgba = null;
	}

	@Override
	public void notifyWarning(String warning) throws HOAConsumerException {
		throw new HOAConsumerException("Warnings are treated as  errors :" +warning);
	}

}
