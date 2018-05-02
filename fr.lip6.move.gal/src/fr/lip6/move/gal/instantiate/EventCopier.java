package fr.lip6.move.gal.instantiate;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BitComplement;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.util.GalSwitch;

/** Performs shallow copies of (parts of) a GAL*/
public class EventCopier extends GalSwitch<EObject> {
	private GalFactory gf = GalFactory.eINSTANCE;
	private Parameter p;
	private int i;
	private boolean dirty;
	private Set<Variable> constvars;
	private Map<ArrayPrefix, Set<Integer>> constantArrs;
	
	public EventCopier(Parameter p, int i, Set<Variable> constvars, Map<ArrayPrefix, Set<Integer>> constantArrs) {
		this.p = p;
		this.i = i;
		dirty = false;
		this.constvars = constvars;
		this.constantArrs = constantArrs;
		Simplifier.deepEquals = false;
	}
	
	@Override
	protected void finalize() throws Throwable {
		Simplifier.deepEquals = true;
		super.finalize();
	}

	@Override
	public EObject caseAbort(Abort object) {
		return gf.createAbort();
	}
	
	@Override
	public EObject caseAnd(And o) {
		And ret = gf.createAnd();
		ret.setLeft((BooleanExpression) doSwitch(o.getLeft()));
		ret.setRight((BooleanExpression) doSwitch(o.getRight()));
		return ret;
	}
	
	@Override
	public EObject caseOr(Or o) {
		Or ret = gf.createOr();
		ret.setLeft((BooleanExpression) doSwitch(o.getLeft()));
		ret.setRight((BooleanExpression) doSwitch(o.getRight()));
		return ret;
	}
	
	@Override
	public EObject caseNot(Not o) {
		Not ret = gf.createNot();
		ret.setValue((BooleanExpression) doSwitch(o.getValue()));
		return ret;
	}
	
	@Override
	public EObject caseComparison(Comparison o) {
		Comparison ret = gf.createComparison();
		ret.setLeft((IntExpression) doSwitch(o.getLeft()));
		ret.setRight((IntExpression) doSwitch(o.getRight()));
		ret.setOperator(o.getOperator());
		return ret;
	}

	@Override
	public EObject caseConstant(Constant o) {
		Constant c = gf.createConstant();
		c.setValue(o.getValue());
		return c;
	}
	
	@Override
	public EObject caseVariableReference(VariableReference o) {
		VariableReference vr = gf.createVariableReference();
		vr.setRef(o.getRef());
		if (o.getIndex() != null)
			vr.setIndex((IntExpression) doSwitch(o.getIndex()));
		if (dirty) {
			Simplifier.simplify(vr.getIndex());
		}
		if (vr.getIndex() == null) {
			if (constvars.contains(vr.getRef())) {
				dirty = true;
				return doSwitch(((Variable)vr.getRef()).getValue());
			} 
		} else if (vr.getIndex() instanceof Constant ) {
			int index = ((Constant) vr.getIndex()).getValue();
			Set<Integer> cstIndexes = constantArrs.get(vr.getRef());
			if (cstIndexes != null && cstIndexes.contains(index) ) {
				dirty = true;
				return doSwitch(((ArrayPrefix) vr.getRef()).getValues().get(index));					
			}
		}
		return vr;
	}
	
	@Override
	public EObject caseParamRef(ParamRef o) {
		if (o.getRefParam() == p || o.getRefParam().getName().equals(p.getName())) { 
			dirty = true;
			return GF2.constant(i);
		}
		ParamRef pr = gf.createParamRef();
		pr.setRefParam(o.getRefParam());
		return pr;
	}
	
	@Override
	public EObject caseBinaryIntExpression(BinaryIntExpression o) {
		BinaryIntExpression ret = gf.createBinaryIntExpression();
		ret.setOp(o.getOp());
		ret.setLeft((IntExpression) doSwitch(o.getLeft()));
		ret.setRight((IntExpression) doSwitch(o.getRight()));
		return ret;
	}
	
	@Override
	public EObject caseLabel(Label o) {
		Label lab = gf.createLabel();
		lab.setName(o.getName());
		for (IntExpression p : o.getParams()) {
			lab.getParams().add((IntExpression) doSwitch(p));
		}
		return lab;
	}
	
	@Override
	public EObject caseTransition(Transition o) {
		Transition t = gf.createTransition();
		t.setComment(o.getComment());
		t.setGuard((BooleanExpression) doSwitch(o.getGuard()));
		if (dirty) {
			Simplifier.simplify(t.getGuard());
			dirty = false;
		}
		if (o.getLabel() != null) t.setLabel((Label) doSwitch(o.getLabel()));		
		t.setName(o.getName());
		for (Statement p : o.getActions()) {
			t.getActions().add( (Statement) doSwitch(p));
			if (dirty) {
				Simplifier.simplifyAllExpressions(t.getActions().get(t.getActions().size()-1));
				dirty = false;
			}
		}
		return t;
	}
	
	@Override
	public EObject caseSynchronization(Synchronization o) {
		Synchronization t = gf.createSynchronization();
		//t.setComment(o.getComment());
		t.setLabel((Label) doSwitch(o.getLabel()));
		t.setName(o.getName());
		for (Statement p : o.getActions()) {
			t.getActions().add( (Statement) doSwitch(p));
		}
		return t;
	}
	
	@Override
	public EObject caseSelfCall(SelfCall o) {
		SelfCall sc = gf.createSelfCall();
		sc.setComment(o.getComment());
		sc.setLabel(o.getLabel());
		for (IntExpression p : o.getParams()) {
			sc.getParams().add((IntExpression) doSwitch(p));
		}
		if (dirty) {
			Label target = GF2.createLabel(sc.getLabel().getName());
			Instantiator.instantiateLabel(target, sc.getParams());
			sc.setLabel(target);
			dirty = false;
		}		
		return sc;
	}
	
	@Override
	public EObject caseInstanceCall(InstanceCall o) {
		InstanceCall sc = gf.createInstanceCall();
		sc.setComment(o.getComment());
		sc.setLabel(o.getLabel());
		for (IntExpression p : o.getParams()) {
			sc.getParams().add((IntExpression) doSwitch(p));
		}
		sc.setInstance(o.getInstance());
		if (dirty) {
			Label target = GF2.createLabel(sc.getLabel().getName());
			Instantiator.instantiateLabel(target, sc.getParams());
			sc.setLabel(target);
			dirty = false;
		}
		return sc;
	}
	
	@Override
	public EObject caseAssignment(Assignment o) {
		Assignment ret =  gf.createAssignment();
		ret.setComment(o.getComment());
		ret.setType(o.getType());
		ret.setLeft((VariableReference) doSwitch(o.getLeft()));
		ret.setRight((IntExpression) doSwitch(o.getRight()));
		return ret;
	}
	@Override
	public EObject caseFalse(False object) {
		return gf.createFalse();
	}
	
	public EObject caseTrue(True object) {
		return gf.createTrue();
	}
	
	@Override
	public EObject caseUnaryMinus(UnaryMinus o) {
		UnaryMinus ret = gf.createUnaryMinus();
		ret.setValue((IntExpression) doSwitch(o.getValue()));
		return ret;
	}
	
	public EObject caseBitComplement(BitComplement o) {
		BitComplement ret = gf.createBitComplement();
		ret.setValue((IntExpression) doSwitch(o.getValue()));
		return ret;
	}
	
	@Override
	public EObject caseIte(Ite o) {
		Ite ret = gf.createIte();
		ret.setComment(o.getComment());
		ret.setCond((BooleanExpression) doSwitch(o.getCond()));
		for (Statement a : o.getIfTrue()) {
			ret.getIfTrue().add((Statement) doSwitch(a));
		}
		for (Statement a : o.getIfFalse()) {
			ret.getIfFalse().add((Statement) doSwitch(a));
		}
		return ret;
	}
	
}
