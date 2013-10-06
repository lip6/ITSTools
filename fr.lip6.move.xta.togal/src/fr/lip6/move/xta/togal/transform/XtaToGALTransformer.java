package fr.lip6.move.xta.togal.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;


import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.timedAutomata.BasicType;
import fr.lip6.move.timedAutomata.BinaryIntExpression;
import fr.lip6.move.timedAutomata.ConstType;
import fr.lip6.move.timedAutomata.Constant;
import fr.lip6.move.timedAutomata.DeclId;
import fr.lip6.move.timedAutomata.FormalDeclaration;
import fr.lip6.move.timedAutomata.Instance;
import fr.lip6.move.timedAutomata.InstantiableInSystem;
import fr.lip6.move.timedAutomata.IntExpression;
import fr.lip6.move.timedAutomata.Parameter;
import fr.lip6.move.timedAutomata.ProcBody;
import fr.lip6.move.timedAutomata.ProcDecl;
import fr.lip6.move.timedAutomata.RangeType;
import fr.lip6.move.timedAutomata.TimedAutomataFactory;
import fr.lip6.move.timedAutomata.Type;
import fr.lip6.move.timedAutomata.TypeDecl;
import fr.lip6.move.timedAutomata.TypedefRef;
import fr.lip6.move.timedAutomata.VarAccess;
import fr.lip6.move.timedAutomata.VariableDecl;
import fr.lip6.move.timedAutomata.XTA;

public class XtaToGALTransformer {
	
	// stores for each template, how many instances
	// and for each of these instances, what are the parameter values if any
	private Map<ProcDecl,List<InstanceInfo>> instances;
	// maps global variables to their (ref)image in gal
	private Map<DeclId,fr.lip6.move.gal.IntExpression> gvarmap;
	
	// maps variables to their array in gal
	private Map<DeclId,ArrayPrefix> varmap;
	// maps parameters to their array in gal	
	private Map<Parameter,ArrayPrefix> parammap ;
	
	public GALTypeDeclaration transformToGAL(XTA s) {
		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		gal.setName("Fromxta");
		
		
		// Set up global variables if any
		gvarmap = new HashMap<DeclId, fr.lip6.move.gal.IntExpression> ();
		for (VariableDecl var : s.getVariables()) {
			BasicType type = var.getType();
			String typename = getTypeName(type);
			for (DeclId did : var.getDeclid()) {
				String varname = did.getName();

				// test for true variable case
				boolean isVar = true;
				if (type instanceof ConstType) {
					ConstType ctype = (ConstType) type;
					if (ctype.isConst()) {
						isVar = false;
					}
				}
				
				if (isVar) {
					Variable vvalues = GalFactory.eINSTANCE.createVariable();				
					vvalues.setName("g"+typename+varname);

					fr.lip6.move.gal.IntExpression init = galConstant(0);
					if (did.getInit() != null) {
						init = convertToGAL(did.getInit().getExpr());
					}
					vvalues.setValue(init);

					gal.getVariables().add(vvalues);

					VariableRef vr  =GalFactory.eINSTANCE.createVariableRef();
					vr.setReferencedVar(vvalues);
					gvarmap.put(did, vr);
				} else {
					ConstParameter cp = GalFactory.eINSTANCE.createConstParameter();
					cp.setName("$"+varname);
					int init = 0;
					if (did.getInit() != null) {
						fr.lip6.move.gal.IntExpression inite = convertToGAL(did.getInit().getExpr());
						if (inite instanceof fr.lip6.move.gal.Constant) {
							fr.lip6.move.gal.Constant cte = (fr.lip6.move.gal.Constant) inite;
							init = cte.getValue();
						} else {
							throw new ArrayIndexOutOfBoundsException("Initial values of constants should be constants");
						}
					} 
					cp.setValue(init);					
					gal.getParams().add(cp);
					
					ParamRef pref = GalFactory.eINSTANCE.createParamRef();
					pref.setRefParam(cp);
					gvarmap.put(did, pref);
				}
			}
		}
		
		// first objective is to collect info on all instances that need to be built
		// We need to know : 
		// * for each template, how many instances
		// * for each of these instances, what are the parameter values if any
		instances = new HashMap<ProcDecl, List<InstanceInfo>>();
		for (InstantiableInSystem pd : s.getSystem().getInstances() ) {
			
			// two cases possible : direct reference to a template 
			// or reference to a declared instance
			if (pd instanceof Instance) {
				Instance ins = (Instance) pd;
				// this is simple; 
				addInstance(ins.getName(), ins.getType(), ins.getArgs());
			} else if (pd instanceof ProcDecl) {
				ProcDecl proc = (ProcDecl) pd;
				// If the template has parameters we might have several instances to build
				if (proc.getParams().isEmpty()) {
					// only a single instance
					addInstance("i"+proc.getName(), proc, Collections.EMPTY_LIST);
				} else {
					// gather min max values for unbound parameter
					int min=-1;
					int max=-1;
					if (proc.getParams().size() > 1) {
						// support this later if needed
						throw new ArrayIndexOutOfBoundsException("Transformation does not yet support having several parameters that vary when using direct refernce to a template declaration in system definition. Please define instances explicitly if that feature is needed.");
					}
					Parameter param = proc.getParams().get(0);
					Type ptype = param.getType();
					if (ptype instanceof TypedefRef) {
						TypedefRef tref = (TypedefRef) ptype;
						TypeDecl typed = tref.getRef();
						if (typed.getType() instanceof RangeType) {
							RangeType rt = (RangeType) typed.getType();
							min = rt.getMin();
							max = rt.getMax();
						} else {
							throw new ArrayIndexOutOfBoundsException("Transformation parameters that vary to have a type that is a typedef'd integer range.");	
						}
					} else {
						throw new ArrayIndexOutOfBoundsException("Transformation parameters that vary should be typedef'd integer range.");	
					}
					// looks good, we have min max.
					for (int i=min; i <=max; i++) {
						addInstance(proc.getName()+i, proc, Collections.singletonList((IntExpression)constant(i)));
					}
				}
			}
		}
		
		
		
		
		// now we have the info, build the stuff
		for (Entry<ProcDecl, List<InstanceInfo>> pi : instances.entrySet()) {
			ProcDecl proc = pi.getKey();
			int nbinst = pi.getValue().size();
			ArrayPrefix pstates = GalFactory.eINSTANCE.createArrayPrefix();
			pstates.setName(proc.getName()+"states");
			pstates.setSize(nbinst);
			
			// compute initial state
			int initid = proc.getBody().getStates().indexOf(proc.getBody().getInitState());
			for (int i=0; i<nbinst; i++ ) {
				pstates.getValues().add(galConstant(initid));
			}
			gal.getArrays().add(pstates);
			
			// add an array for each parameter of the template
			parammap = new HashMap<Parameter, ArrayPrefix>();
			int paramindex = 0;
			for (Parameter param: proc.getParams()) {
				ArrayPrefix pvalues = GalFactory.eINSTANCE.createArrayPrefix();
				pvalues.setName(proc.getName()+param.getName());
				pvalues.setSize(nbinst);
				
				for (InstanceInfo ins : pi.getValue()) {
					pvalues.getValues().add(ins.paramValues.get(paramindex));
				}
				gal.getArrays().add(pvalues);
				parammap.put(param, pvalues);
				paramindex++;
			}
									
			// add an array for each variable of the template
			varmap = new HashMap<DeclId, ArrayPrefix>();
			for (VariableDecl var : proc.getBody().getVariables()) {
				BasicType type = var.getType();
				String typename = getTypeName(type);
				for (DeclId did : var.getDeclid()) {
					String varname = did.getName();
					
					ArrayPrefix vvalues = GalFactory.eINSTANCE.createArrayPrefix();
					vvalues.setName(proc.getName()+typename+varname);
					vvalues.setSize(nbinst);
					
					fr.lip6.move.gal.IntExpression init = galConstant(0);
					if (did.getInit() != null) {
						init = convertToGAL(did.getInit().getExpr());
					}
					for (int i=0; i < nbinst ; i++) {
						vvalues.getValues().add(EcoreUtil.copy(init));
					}
					
					gal.getArrays().add(vvalues);
					varmap.put(did, vvalues);
				}
			}
			
			for (InstanceInfo ins : pi.getValue()) {
				
				
			}
		}
		
		
		

		//	gal.setName(pd.getName());
			
			
//			//declaration of variables
//			for (VarDecl vd : pd.getVars()) {
//				fr.lip6.move.gal.VarDecl tvd = GalFactory.eINSTANCE.createVarDecl();
//				tvd.setName(vd.getName());
//				Variable tvar = GalFactory.eINSTANCE.createVariable();
//				tvar.setName(vd.getName());
//				tvar.setValue( convertToGAL(vd.getValue(),gal));
//
//				gal.getVariables().add(tvar);
//			}
//			
//			//declaration of states & state initial
//			Map<String, Integer> states= new HashMap<String,Integer>(); 
//			for (StateDeclaration sd : pd.getStates()) {
//				if (!(states.containsKey(sd.getName()))){
//					states.put(sd.getName(), states.size());
//				}
//			}
//			String init = pd.getInitialState().getName();
//			fr.lip6.move.gal.VarDecl varStateDecl = GalFactory.eINSTANCE.createVarDecl();
//			varStateDecl.setName("varStates");
//			Variable varState = GalFactory.eINSTANCE.createVariable();
//			varState.setName("varStates");
//			
//			Constant tcons = GalFactory.eINSTANCE.createConstant();
//			tcons.setValue((int) (states.get(init)));
//			varState.setValue(tcons);
			

			
				
		return gal;
	}
		
	private String getTypeName(BasicType type) {
		return type.getClass().getSimpleName().replace("Type", "").replace("Impl", "");
	}

	private Constant constant(int i) {
		Constant c = TimedAutomataFactory.eINSTANCE.createConstant();
		c.setValue(i);
		return c;
	}

	private fr.lip6.move.gal.Constant galConstant(int i) {
		fr.lip6.move.gal.Constant c = GalFactory.eINSTANCE.createConstant();
		c.setValue(i);
		return c;
	}

	
	private void addInstance(String name, ProcDecl type, List<IntExpression> list) {
		List<InstanceInfo> inslist = instances.get(type);
		if (inslist == null) {
			inslist = new ArrayList<InstanceInfo>();
			instances.put(type, inslist);
		}
		int id = inslist.size();
		
		// convert the arguments
		List<fr.lip6.move.gal.IntExpression> rargs = new  ArrayList<fr.lip6.move.gal.IntExpression>(list.size());
		for (IntExpression e : list ) {
			rargs.add(convertToGAL(e));
		}
		inslist.add(new InstanceInfo(name, rargs, id));		
	}

	private fr.lip6.move.gal.IntExpression convertToGAL(IntExpression value) {
		return convertToGAL(value, -3);
	}
	
	private fr.lip6.move.gal.IntExpression convertToGAL(IntExpression value, int instance) {
		if (value instanceof Constant) {
			Constant cte = (Constant) value;
			fr.lip6.move.gal.Constant tcte = GalFactory.eINSTANCE.createConstant();
			tcte.setValue(cte.getValue());
			return tcte;
		} else if (value instanceof VarAccess) {
			FormalDeclaration fd = ((VarAccess) value).getRef();
			if (fd instanceof DeclId) {
				DeclId did = (DeclId) fd;
				// Global var 
				fr.lip6.move.gal.IntExpression vd = gvarmap.get(did);				
				if (vd != null) {					
					return EcoreUtil.copy(vd);
				} else {
					// local var
					ArrayPrefix va = varmap.get(did);
					if (va==null) {
						throw new ArrayIndexOutOfBoundsException("Unmapped variable "+did);
					}
					ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
					ava.setPrefix(va);
					ava.setIndex(galConstant(instance));
					return ava;
				}
			} else {
				Parameter param = (Parameter) fd;
				ArrayPrefix va = parammap.get(param);
				if (va==null) {
					throw new ArrayIndexOutOfBoundsException("Unmapped variable "+param);
				}
				ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
				ava.setPrefix(va);
				ava.setIndex(galConstant(instance));
				return ava;
			}
			
			
		} else if (value instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) value;
			fr.lip6.move.gal.BinaryIntExpression rbin = GalFactory.eINSTANCE.createBinaryIntExpression();
			rbin.setOp(bin.getOp());
			rbin.setLeft(convertToGAL(bin.getLeft()));
			rbin.setRight(convertToGAL(bin.getRight()));
			return rbin;
			
//			fr.lip6.move.xta.VarRef varRef = (fr.lip6.move.xta.VarRef) value;
//			fr.lip6.move.gal.VariableRef tvarRef = GalFactory.eINSTANCE.createVariableRef();
//			
//			Boolean inconnue= true;
//			for(Variable v : gal.getVariables()){
//				if(varRef.getVar().getName().equals(v.getName())){
//					inconnue= false;
//					tvarRef.setReferencedVar(v);
//					break;
//				}	
//			}
//			
//			if (inconnue == true)  {
//				java.lang.System.err.println("The referenced variable does not exists !!!");
//				return null;
//			}
//				
//			return tvarRef;
		}		
		return null;
	}

}

class InstanceInfo {
	String name;
	List<fr.lip6.move.gal.IntExpression> paramValues;
	int id;
	public InstanceInfo(String name, List<fr.lip6.move.gal.IntExpression> paramValues, int id) {
		this.name = name;
		this.paramValues = paramValues;
		this.id = id;
	}
	
}
