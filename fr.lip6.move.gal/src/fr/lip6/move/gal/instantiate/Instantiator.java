package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AbstractParameter;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;

public class Instantiator {

	public static System instantiateParameters(System s) throws Exception {

		s.setName(s.getName()+"_flat");
		instantiateTypeParameters(s);
		
		List<Transition> todel = new ArrayList<Transition>();
		List<Transition> done = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			List<Transition> list = instantiateParameters(t);
			todel.add(t);
			done.addAll(list);
		}
		s.getTransitions().clear();
		s.getTransitions().addAll(done);
		
		
		return s;
	}

	private static void instantiateTypeParameters(System s) {
		if (!s.getParams().isEmpty()) {
			for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof ParamRef) {
					ParamRef pr = (ParamRef) obj;
					if (pr.getRefParam() instanceof ConstParameter) {
						Constant cte = GalFactory.eINSTANCE.createConstant();
						cte.setValue(((ConstParameter) pr.getRefParam()).getValue());
						EcoreUtil.replace(obj, cte);
					}
				}
			}
		}
		s.getParams().clear();
		s.getTypes().clear();
	}

	public static List<Transition> instantiateParameters(Transition toinst) {

		java.util.List<Transition> todo  = new ArrayList<Transition>();
		java.util.List<Transition> done  = new ArrayList<Transition>();
		if (hasParam(toinst)) {
			todo.add(toinst);
		} else {
			done.add(EcoreUtil.copy(toinst));
		}
		while (! todo.isEmpty()) {
			Transition t = todo.remove(0);
			Parameter p = t.getParams().getParamList().get(0);
			int min = -1;
			IntExpression smin = Simplifier.simplify(p.getType().getMin());
			if (smin instanceof Constant) {
				Constant cte = (Constant) smin;
				min = cte.getValue();
			}
			int max = - 1;
			IntExpression smax = Simplifier.simplify(p.getType().getMax());
			if (smax instanceof Constant) {
				Constant cte = (Constant) smax;
				max = cte.getValue();
			}
			if (min == -1 || max == -1) {
				throw new ArrayIndexOutOfBoundsException("Expected constant as both min and max bounds of type def "+p.getType().getName());
			}
			for(int i = min; i <= max; i++){
				Transition tcopy = EcoreUtil.copy(t);
				Parameter param = tcopy.getParams().getParamList().get(0);
				instantiate(tcopy.getLabel(), param, i);
				instantiate(tcopy,param, i);
				tcopy.setName(tcopy.getName()+"_"+ i );
				if (hasParam(tcopy)) {
					todo.add(tcopy);
				} else {
					done.add(tcopy);
				}
			}
		}
		return done;
	}

	private static boolean hasParam(Transition t) {
		return t.getParams()!=null && t.getParams().getParamList()!=null 
				&& ! t.getParams().getParamList().isEmpty();
	}

	private static void instantiate(Transition src, AbstractParameter param, int value) {
		for (TreeIterator<EObject> it = src.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam().equals(param)) {
					EcoreUtil.replace(obj, constant(value));
				}
			}
		}
		EcoreUtil.delete(param);
		src.setGuard(Simplifier.simplify(src.getGuard()));
	}

	private static void instantiate(Label label, Parameter p, int i) { 
		String paramStr = p.getName();
		if (label != null) {
			label.setName( label.getName().replace(paramStr, Integer.toString(i)));
		}
	}

	public static System instantiateParametersWithAbstractColors(System s) {
		
		s.setName(s.getName()+"_nocolor");
		instantiateTypeParameters(s);
		
		for (TreeIterator<EObject> it = s.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			
			if (obj instanceof ArrayPrefix) {
				ArrayPrefix ap = (ArrayPrefix) obj;
				ap.setSize(1);
				int sum =0;
				for (IntExpression e : ap.getValues().getValues()) {
					IntExpression eprime = Simplifier.simplify(e);
					if (eprime instanceof Constant) {
						Constant cte = (Constant) eprime;
						sum += cte.getValue();
					}
				}
				ap.getValues().getValues().clear();
				ap.getValues().getValues().add(constant(sum));
			
			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;
				av.setIndex(constant(0));
			}
		}
		
		for (Transition t : s.getTransitions()) {
			t.getParams().getParamList().clear();
		}
		
		return s;
	}

	private static IntExpression constant(int val) {
		Constant toret = GalFactory.eINSTANCE.createConstant();
		toret.setValue(val);
		return toret;
	}



}