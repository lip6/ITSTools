package fr.lip6.move.xta.togal.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;


import fr.lip6.move.timedAutomata.*;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Call;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.gal.instantiate.Instantiator;

public class XtaToGALTransformer {

	// stores for each template, how many instances
	// and for each of these instances, what are the parameter values if any
	private Map<ProcDecl,List<InstanceInfo>> instances;
	// maps global variables to their (ref)image in gal
	private Map<DeclId,fr.lip6.move.gal.IntExpression> gvarmap;

	// maps variables to their array in gal
	private Map<DeclId,fr.lip6.move.gal.ArrayVarAccess> varmap;
	// maps parameters to their array in gal	
	private Map<Parameter,fr.lip6.move.gal.ArrayVarAccess> parammap ;

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

		// Build channels
		for (ChannelDecl chan : s.getChannels()) {
			for (ChanId decl : chan.getChans()) {
				fr.lip6.move.gal.Transition rtr = GalFactory.eINSTANCE.createTransition();
				rtr.setName("chan"+decl.getName());

				// no guard
				rtr.setGuard(GalFactory.eINSTANCE.createTrue());
				// label : a discrete transition
				Label lab = GalFactory.eINSTANCE.createLabel();
				lab.setName("dtrans");
				rtr.setLabel(lab);

				// call : chan !
				Label slab = GalFactory.eINSTANCE.createLabel();
				slab.setName("Send"+decl.getName());
				Call scall = GalFactory.eINSTANCE.createCall();
				scall.setLabel(slab);
				rtr.getActions().add(scall);

				// call : chan ?
				Label rlab = GalFactory.eINSTANCE.createLabel();
				rlab.setName("Recv"+decl.getName());
				Call rcall = GalFactory.eINSTANCE.createCall();
				rcall.setLabel(rlab);
				rtr.getActions().add(rcall);

				gal.getTransitions().add(rtr);
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

		fr.lip6.move.gal.Transition elapse = GalFactory.eINSTANCE.createTransition();
		elapse.setName("elapse");
		Label elapselab = GalFactory.eINSTANCE.createLabel();
		elapselab.setName("elapseOne");
		elapse.setLabel(elapselab);
		elapse.setGuard(GalFactory.eINSTANCE.createTrue());
		gal.getTransitions().add(elapse);
		int paramNum=0;


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
			parammap = new HashMap<Parameter, fr.lip6.move.gal.ArrayVarAccess>();
			int paramindex = 0;
			for (Parameter param: proc.getParams()) {
				ArrayPrefix pvalues = GalFactory.eINSTANCE.createArrayPrefix();
				pvalues.setName(proc.getName()+param.getName());
				pvalues.setSize(nbinst);

				for (InstanceInfo ins : pi.getValue()) {
					pvalues.getValues().add(ins.paramValues.get(paramindex));
				}
				gal.getArrays().add(pvalues);


				ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
				ava.setPrefix(pvalues);
				// will be pid shortly
				ava.setIndex(galConstant(0));

				parammap.put(param, ava);
				paramindex++;
			}



			String pidname = proc.getName() +"id";

			TypedefDeclaration ptypedef = GalFactory.eINSTANCE.createTypedefDeclaration();
			ptypedef.setName(pidname+"_t");
			ptypedef.setMin(galConstant(0));
			ptypedef.setMax(galConstant(nbinst-1));
			gal.getTypes().add(ptypedef);


			// add an array for each variable of the template
			varmap = new HashMap<DeclId, fr.lip6.move.gal.ArrayVarAccess>();
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

					ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
					ava.setPrefix(vvalues);
					// will be pid shortly
					ava.setIndex(galConstant(0));

					varmap.put(did, ava);
				}
			}

			// for each clock of the template
			for (VariableDecl var : proc.getBody().getVariables()) {
				if (var.getType() instanceof ClockType) {
					for (DeclId clock : var.getDeclid()) {

						// for each location, compute clock visibility
						for (StateDecl st : proc.getBody().getStates()) {
							// for non urgent states
							if (! proc.getBody().getUrgentStates().contains(st)) {

								int k = computeInvariant(st,clock);
								
								if (k != -1) {
									// create for loop
									For rfor = GalFactory.eINSTANCE.createFor();
									fr.lip6.move.gal.ForParameter param = GalFactory.eINSTANCE.createForParameter();
									param.setName("$"+pidname+paramNum++);
									param.setType(ptypedef);
									rfor.setParam(param);

									ParamRef pref = GalFactory.eINSTANCE.createParamRef();
									pref.setRefParam(param);
									// so we have found a normalized "x < k" location invariant
									// a predicate to test the location
									fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
									testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);

									ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
									ava.setPrefix(pstates);
									ava.setIndex(EcoreUtil.copy(pref));
									testsrc.setLeft(ava);
									testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));

									// add a test to allow elapse
									// clock ref

									Ite ite = GalFactory.eINSTANCE.createIte();
									ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());

									if (k > 0) {
										ArrayVarAccess cref = EcoreUtil.copy(varmap.get(clock));
										cref.setIndex(EcoreUtil.copy(pref));

										fr.lip6.move.gal.Comparison xEqk = GalFactory.eINSTANCE.createComparison();
										xEqk.setOperator(fr.lip6.move.gal.ComparisonOperators.GE);
										xEqk.setLeft(cref);
										xEqk.setRight(galConstant(k));

										// and the two conditions
										fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
										and.setLeft(testsrc);
										and.setRight(xEqk);

										ite.setCond(and);

									} else {
										// invariant expresses urgency, simply abort if in the location (like an urgent location)
										ite.setCond(testsrc);
									}

									rfor.getActions().add(ite);

									elapse.getActions().add(rfor);
								}
							} else {
								// no invariant case : find max tracking value, if any
								
								
								
							}
						}
					}
				}
			}

			// get rid of urgent states they obviously never need to know whether the clock X is ticking.
			for (StateDecl st : proc.getBody().getUrgentStates()) {
				For rfor = GalFactory.eINSTANCE.createFor();
				fr.lip6.move.gal.ForParameter param = GalFactory.eINSTANCE.createForParameter();
				param.setName("$"+pidname);
				param.setType(ptypedef);
				rfor.setParam(param);

				ParamRef pref = GalFactory.eINSTANCE.createParamRef();
				pref.setRefParam(param);

				fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
				testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);

				ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
				ava.setPrefix(pstates);
				ava.setIndex(EcoreUtil.copy(pref));
				testsrc.setLeft(ava);
				testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));

				Ite ite = GalFactory.eINSTANCE.createIte();
				ite.setCond(testsrc);
				ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());

				rfor.getActions().add(ite);

				elapse.getActions().add(0, rfor);
			}

			// transitions
			int tid=1;
			for (Transition tr : proc.getBody().getTransitions()) {
				fr.lip6.move.gal.Transition rtr = GalFactory.eINSTANCE.createTransition();
				rtr.setName("t"+ tid++ +proc.getName()+tr.getSrc().getName()+"_"+tr.getDest().getName());


				Label lab = GalFactory.eINSTANCE.createLabel();
				if (tr.getSync() != null) {
					String chan = tr.getSync().getChannel().getName();
					if (tr.getSync() instanceof Send) {
						chan = "Send"+chan;
					} else {
						chan = "Recv"+chan;
					}
					lab.setName(chan);
				} else {
					lab.setName("dtrans");
				}
				rtr.setLabel(lab);


				fr.lip6.move.gal.Parameter param = GalFactory.eINSTANCE.createParameter();
				param.setName("$"+pidname);
				param.setType(ptypedef);
				rtr.getParams().add(param);

				ParamRef pref = GalFactory.eINSTANCE.createParamRef();
				pref.setRefParam(param);

				// update varmap with current context
				for (Entry<DeclId, ArrayVarAccess> e : varmap.entrySet()) {
					e.getValue().setIndex(EcoreUtil.copy(pref));
				}
				for (Entry<Parameter, ArrayVarAccess> e : parammap.entrySet()) {
					e.getValue().setIndex(EcoreUtil.copy(pref));
				}

				fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
				testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);

				ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
				ava.setPrefix(pstates);
				ava.setIndex(EcoreUtil.copy(pref));
				testsrc.setLeft(ava);
				testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(tr.getSrc())));

				fr.lip6.move.gal.BooleanExpression guard = testsrc;

				if (tr.getGuard() != null) {
					fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
					and.setLeft(guard);
					and.setRight(convertToGAL(tr.getGuard()));

					guard = and;
				}

				rtr.setGuard(guard);

				// updates now

				// state update, if needed
				if (tr.getSrc() != tr.getDest()) {
					ArrayVarAccess pst = GalFactory.eINSTANCE.createArrayVarAccess();
					pst.setPrefix(pstates);
					pst.setIndex(EcoreUtil.copy(pref));

					Assignment ass = GalFactory.eINSTANCE.createAssignment();
					ass.setLeft(pst);
					ass.setRight(galConstant(proc.getBody().getStates().indexOf(tr.getDest())));
					rtr.getActions().add(ass);
				}

				if (tr.getAssign() != null) {
					for (Assign ass : tr.getAssign().getAssigns()) {
						Assignment rass = GalFactory.eINSTANCE.createAssignment();
						rass.setLeft((fr.lip6.move.gal.VarAccess) convertToGAL(ass.getLhs()));
						rass.setRight(convertToGAL(ass.getRhs()));
						rtr.getActions().add(rass);
					}
				}


				gal.getTransitions().add(rtr);
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


		Instantiator.normalizeCalls(gal);	

		return gal;
	}



	private int computeInvariant(StateDecl st, DeclId clock) {
		// if there is a state invariant over clock, that takes precedence.
		if (st.getInvariant() != null) {
			if (st.getInvariant() instanceof Comparison) {
				Comparison inv = (Comparison) st.getInvariant();
				if (inv.getLeft() instanceof VarAccess) {
					if (((VarAccess)inv.getLeft()).getRef() == clock ) {
						int k = resolveConstant (inv.getRight());
						if (inv.getOperator() == ComparisonOperators.LT) {
							// let clock grow to constant
							k = k+1;
						} else if (inv.getOperator() == ComparisonOperators.LE) {
							// nop, k is good value for tracking
						} else {
							throw new ArrayIndexOutOfBoundsException("expected clock invariant of form x < k or x <= k, got bad comparison operator "+st.getInvariant() );	
						}
						return k;
					}
				} else {
					throw new ArrayIndexOutOfBoundsException("expected clock invariant of form x < k or x <= k, got strange comparison "+st.getInvariant() );
				}				
			} else {
				throw new ArrayIndexOutOfBoundsException("expected clock invariant and could not handle :"+st.getInvariant() );				
			}
		}
		return -1;
	}



	private int resolveConstant(IntExpression expr) {
		if (expr instanceof Constant) {
			Constant cte = (Constant) expr;
			return cte.getValue();
		} else if (expr instanceof VarAccess) {
			VarAccess va = (VarAccess) expr;
			FormalDeclaration fd = va.getRef();
			if (fd instanceof DeclId) {
				DeclId did = (DeclId) fd;
				Initialiser val = did.getInit();
				return resolveConstant(val.getExpr());
			}
		} else {
			throw new ArrayIndexOutOfBoundsException("Expected expression to refer to a constant value " + expr);
		}


		return 0;
	}



	private int computeBound(ProcDecl proc, StateDecl st, DeclId clock) {
		return computeBoundrec(proc, st, clock, new HashSet<StateDecl>());
	}

	private int computeBoundrec(ProcDecl proc, StateDecl st, DeclId clock, Set<StateDecl> seen) {
		if (!seen.contains(st)) {
			seen.add(st);
		} else {
			return -1;
		}



		// otherwise, scan outgoing edges
		for (Transition tr : proc.getBody().getTransitions()) {
			if (tr.getSrc() == st) {
				if (tr.getGuard() != null) { 
					for (Iterator<EObject> it = tr.getGuard().eAllContents() ; it.hasNext() ; ) {
						EObject obj = it.next();
						if (obj instanceof VarAccess) {
							VarAccess va = (VarAccess) obj;
							if (va.getRef() == clock) {

							}
						}
					}
				}

			}
		}

		return 0;
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



	private fr.lip6.move.gal.BooleanExpression convertToGAL(
			fr.lip6.move.timedAutomata.BooleanExpression b) {
		if (b instanceof True) {
			return GalFactory.eINSTANCE.createTrue();
		} else if (b instanceof False) {
			return GalFactory.eINSTANCE.createFalse();
		} else if (b instanceof And) {
			And and = (And) b;
			fr.lip6.move.gal.And rand = GalFactory.eINSTANCE.createAnd();
			rand.setLeft(convertToGAL(and.getLeft()));
			rand.setRight(convertToGAL(and.getRight()));
			return rand;
		} else if (b instanceof Or) {
			Or and = (Or) b;
			fr.lip6.move.gal.Or rand = GalFactory.eINSTANCE.createOr();
			rand.setLeft(convertToGAL(and.getLeft()));
			rand.setRight(convertToGAL(and.getRight()));
			return rand;
		} else if (b instanceof Not) {
			Not and = (Not) b;
			fr.lip6.move.gal.Not rand = GalFactory.eINSTANCE.createNot();
			rand.setValue(convertToGAL(and.getValue()));
			return rand;
		} else if (b instanceof Comparison) {
			Comparison comp = (Comparison) b;
			fr.lip6.move.gal.Comparison rcomp = GalFactory.eINSTANCE.createComparison();
			rcomp.setOperator(fr.lip6.move.gal.ComparisonOperators.get(comp.getOperator().getValue()));
			rcomp.setLeft(convertToGAL(comp.getLeft()));
			rcomp.setRight(convertToGAL(comp.getRight()));
			return rcomp;
		} 
		throw new ArrayIndexOutOfBoundsException("Unexpected boolean expression type in conversion "+b);
	}


	private fr.lip6.move.gal.IntExpression convertToGAL(IntExpression value) {
		if (value instanceof VarAccess) {
			FormalDeclaration fd = ((VarAccess) value).getRef();
			if (fd instanceof DeclId) {
				DeclId did = (DeclId) fd;
				// Global var 
				fr.lip6.move.gal.IntExpression vd = gvarmap.get(did);				
				if (vd != null) {					
					return EcoreUtil.copy(vd);
				} else {
					// local var
					ArrayVarAccess va = varmap.get(did);
					if (va==null) {
						throw new ArrayIndexOutOfBoundsException("Unmapped variable "+did);
					}
					return EcoreUtil.copy(va);
				}
			} else {
				Parameter param = (Parameter) fd;
				ArrayVarAccess va = parammap.get(param);
				if (va==null) {
					throw new ArrayIndexOutOfBoundsException("Unmapped variable "+param);
				}
				return EcoreUtil.copy(va);
			}						
		} else if (value instanceof Constant) {
			Constant cte = (Constant) value;
			fr.lip6.move.gal.Constant tcte = GalFactory.eINSTANCE.createConstant();
			tcte.setValue(cte.getValue());
			return tcte;

		} else if (value instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) value;
			fr.lip6.move.gal.BinaryIntExpression rbin = GalFactory.eINSTANCE.createBinaryIntExpression();
			rbin.setOp(bin.getOp());
			rbin.setLeft(convertToGAL(bin.getLeft()));
			rbin.setRight(convertToGAL(bin.getRight()));
			return rbin;
		}		
		throw new ArrayIndexOutOfBoundsException("Unexpected integer expression type in conversion "+value);
	}

}

class ClockConstraint {
	DeclId clock;
	int upTo;
	public ClockConstraint(DeclId clock, int upTo) {
		this.clock = clock;
		this.upTo = upTo;
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
