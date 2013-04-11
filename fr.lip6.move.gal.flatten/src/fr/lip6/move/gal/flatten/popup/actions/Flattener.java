package fr.lip6.move.gal.flatten.popup.actions;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.List;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.impl.ParamRefImpl;

public class Flattener {

	public static System flatten(System s) throws Exception {
		/*GalFactory gf = GalFactory.eINSTANCE;
		Transition newTransition = gf.createTransition();	

		for(Transition t : s.getTransitions()){
			True tru = gf.createTrue();
			tru.setValue("True");
			newTransition.setGuard(tru);

			Label lab  = gf.createLabel();
			lab.setName("Test");
			newTransition.setLabel(lab);
			newTransition.setParams(EcoreUtil.copy( t.getParams()));
			//			newTransition.setGuard(t.getGuard());
			//			newTransition.setLabel(t.getLabel());
			//			newTransition.setParams(t.getParams());
			//			newTransition.getActions().addAll(t.getActions());
		}

		s.getTransitions().add(newTransition);*/
		java.util.List<Transition> todo  = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			if (hasParam(t)) {
				todo.add(t);
			}
		}
		while (! todo.isEmpty()) {
			Transition t = todo.remove(0);
			Parameter p = t.getParams().getParamList().get(0);
			for(int i = p.getType().getMin(); i <= p.getType().getMax(); i++){
				Transition tcopy = EcoreUtil.copy(t);
				Parameter param = tcopy.getParams().getParamList().get(0);
				instantiate(tcopy.getLabel(), param, i);
				instantiate(tcopy,param, i);
				tcopy.setName(tcopy.getName()+"_"+ i );
				if (hasParam(tcopy)) {
					todo.add(tcopy);
				}
				s.getTransitions().add(tcopy);
			}
			EcoreUtil.delete(t);
		}
//		
//		Transition t = s.getTransitions().get(0);
//		
//		Parameter p = EcoreUtil.copy(t.getParams().getParamList().get(0));
//		for(Parameter param : t.getParams().getParamList()){
//			
//			for(int i = param.getType().getMin(); i <= param.getType().getMax(); i++){
//				Transition tcopy = EcoreUtil.copy(t);
//				
//				BooleanExpression garde = instantiate(tcopy.getGuard(),param , i);
//				
//				instantiate(tcopy.getActions(), param, i);
//				instantiate(tcopy.getLabel(), param, i);
//				//tcopy.getParams().getParamList().clear();
//				tcopy.getParams().getParamList().remove(0);
//				tcopy.setGuard(garde);
//				tcopy.setName(t.getName() + "_" + i);
//				
//				s.getTransitions().add(tcopy);
//				
//			}
//		}
//		
//		
		
		return s;
	}

	private static boolean hasParam(Transition t) {
		return t.getParams()!=null && t.getParams().getParamList()!=null 
				&& ! t.getParams().getParamList().isEmpty();
	}

	private static void instantiate(Transition src, Parameter param, int value) {
		for (TreeIterator<EObject> it = src.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam().equals(param)) {
					Constant cst = GalFactory.eINSTANCE.createConstant();
					cst.setValue(value);
					EcoreUtil.replace(obj, cst);
				}
			}
		}
		EcoreUtil.delete(param);
	}

	private static void instantiate(Label label, Parameter p, int i) { 
		String paramStr = p.getName();
		label.setName( label.getName().replace(paramStr, Integer.toString(i)));
//		int indexFin = label.getName().indexOf(")");
//		int indexParam = label.getName().indexOf("$");
//		String param = label.getName().substring(indexParam, indexFin);
//		if(param.compareTo(p.getName()) == 0){
//			String newLabel = label.getName().substring(0, label.getName().indexOf("("));
//			newLabel = newLabel + "_" + i;
//			
//			label.setName(newLabel);
//		}
	}

	private static void instantiate(EList<Actions> actions, Parameter p,
			int value) {
		for (Actions a : actions) {
			for (TreeIterator<EObject> it = a.eAllContents(); it.hasNext();) {
				EObject obj = it.next();
				
				if(obj instanceof ParamRefImpl){
					if(((ParamRefImpl) obj).getRefParam().getName().compareTo(p.getName())==0){
					java.lang.System.err.println("===============================================");
					Constant cst = GalFactory.eINSTANCE.createConstant();
					cst.setValue(value);
					EcoreUtil.replace(obj, cst);
					}
					/*else{
						Constant cst = GalFactory.eINSTANCE.createConstant();
						cst.setValue(1);
						EcoreUtil.replace(obj, cst);
					}*/
					java.lang.System.err.println("================================================");
				}
			}
		}
	}

	private static BooleanExpression instantiate (BooleanExpression src, Parameter p, int value) throws Exception {
		for (TreeIterator<EObject> it = src.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			
			
			java.lang.System.err.println(obj.getClass());
			
			if(obj instanceof fr.lip6.move.gal.impl.ConstantImpl){
				fr.lip6.move.gal.impl.ConstantImpl test = (fr.lip6.move.gal.impl.ConstantImpl)obj;
				java.lang.System.err.println(test.getValue());
			}
			
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				//if (pr.getRefParam().equals(p)) {
					Parameter param = EcoreUtil.copy(pr.getRefParam());
					Constant cst = GalFactory.eINSTANCE.createConstant();
					cst.setValue(value);
					EcoreUtil.replace(obj, cst);
				//}
			}
			else{
				java.lang.System.err.println("Unknown Metaclass Object");
				// throw new Exception("Unknown Metaclass Object.");
			}
	
		}
		return src;

	}



	private IntExpression instantiate(IntExpression left, Parameter p, int value) {
		for (TreeIterator<EObject> it = left.eAllContents(); it.hasNext();) {
			EObject obj = it.next();

			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam().equals(p)) {
					Parameter param = EcoreUtil.copy(pr.getRefParam());
					Constant cst = GalFactory.eINSTANCE.createConstant();
					cst.setValue(value);
					EcoreUtil.replace(obj, cst);
				}
			}
	
		}
		return left;
	}

}