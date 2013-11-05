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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;


import fr.lip6.move.timedAutomata.*;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Call;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Fixpoint;
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


	private static final String SEP = "";
	// stores for each template, how many instances
	// and for each of these instances, what are the parameter values if any
	private Map<ProcDecl,List<InstanceInfo>> instances;
	// maps global variables to their (ref)image in gal (could be type parameters or variables)
	private Map<DeclId,fr.lip6.move.gal.IntExpression> gvarmap;
	
	// maps variables to their array in gal
	private Map<DeclId,fr.lip6.move.gal.ArrayVarAccess> varmap;
	// maps parameters to their array in gal
	private Map<Parameter,fr.lip6.move.gal.ArrayVarAccess> parammap ;

	public GALTypeDeclaration transformToGAL(XTA s, String name) {
		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		gal.setName(name);


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
					vvalues.setName("glob"+SEP+typename+SEP+varname);

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

		// label : a discrete transition
		Label dtranslab = GalFactory.eINSTANCE.createLabel();
		dtranslab.setName("dtrans");

		// Build channels
		for (ChannelDecl chan : s.getChannels()) {
			for (ChanId decl : chan.getChans()) {
				fr.lip6.move.gal.Transition rtr = GalFactory.eINSTANCE.createTransition();
				rtr.setName("chan"+decl.getName());

				// no guard
				rtr.setGuard(GalFactory.eINSTANCE.createTrue());

				rtr.setLabel(EcoreUtil.copy(dtranslab));

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
					addInstance("i"+proc.getName(), proc, Collections.<IntExpression> emptyList());
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


		// now we have the info, build the stuff
		for (Entry<ProcDecl, List<InstanceInfo>> pi : instances.entrySet()) {
			ProcDecl proc = pi.getKey();
			int nbinst = pi.getValue().size();
			ArrayPrefix pstates = GalFactory.eINSTANCE.createArrayPrefix();
			pstates.setName(proc.getName()+SEP+"state");
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
				pvalues.setName(proc.getName()+SEP +param.getName());
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
					vvalues.setName(proc.getName()+SEP+typename+SEP+varname);
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

			// create for loop
			For rfor = GalFactory.eINSTANCE.createFor();
			fr.lip6.move.gal.ForParameter param = GalFactory.eINSTANCE.createForParameter();
			param.setName("$"+pidname);
			param.setType(ptypedef);
			rfor.setParam(param);

			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
			pref.setRefParam(param);

			// update varmap with current context
			for (Entry<DeclId, ArrayVarAccess> e : varmap.entrySet()) {
				e.getValue().setIndex(EcoreUtil.copy(pref));
			}
			for (Entry<Parameter, ArrayVarAccess> e : parammap.entrySet()) {
				e.getValue().setIndex(EcoreUtil.copy(pref));
			}

			elapse.getActions().add(rfor);


			// for each clock of the template
			for (VariableDecl var : proc.getBody().getVariables()) {
				if (var.getType() instanceof ClockType) {
					for (DeclId clock : var.getDeclid()) {
						// for each location, compute clock visibility
						for (StateDecl st : proc.getBody().getStates()) {

							// a predicate to test the location
							fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
							testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);


							ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
							ava.setPrefix(pstates);
							ava.setIndex(EcoreUtil.copy(pref));
							testsrc.setLeft(ava);
							testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));

							int k = computeInvariant(st,clock);
							if (k==0) {
								// NB: k = 0 add to urgent locations
								proc.getBody().getUrgentStates().add(st);
							}
							if (k > 0) {

								// so we have found a normalized "x < k" location invariant

								// add a test to allow elapse
								// clock ref

								Ite ite = GalFactory.eINSTANCE.createIte();
								ite.setCond(testsrc);

								if (k > 0) {
									ArrayVarAccess cref = EcoreUtil.copy(varmap.get(clock));
									cref.setIndex(EcoreUtil.copy(pref));

									// is max bound reached ?
									fr.lip6.move.gal.Comparison xEqk = GalFactory.eINSTANCE.createComparison();
									xEqk.setOperator(fr.lip6.move.gal.ComparisonOperators.GE);
									xEqk.setLeft(cref);
									xEqk.setRight(galConstant(k));

									// inner test : if (x >= bound) abort ; else x++ 
									Ite incr = GalFactory.eINSTANCE.createIte();
									incr.setCond(xEqk);

									incr.getIfTrue().add(GalFactory.eINSTANCE.createAbort());

									// increment of clock
									Assignment ass = GalFactory.eINSTANCE.createAssignment();
									ass.setLeft(EcoreUtil.copy(cref));
									fr.lip6.move.gal.BinaryIntExpression add = GalFactory.eINSTANCE.createBinaryIntExpression();
									add.setOp("+");
									add.setLeft(EcoreUtil.copy(cref));
									add.setRight(galConstant(1));
									ass.setRight(add);

									incr.getIfFalse().add(ass);

									ite.getIfTrue().add(incr);

								} else {
									// invariant expresses urgency, simply abort if in the location (like an urgent location)
									ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());
								}

								rfor.getActions().add(ite);

							} else {
								// no invariant case : find max tracking value, if any

								Set<StateDecl> seen = new HashSet<StateDecl>();
								Set<StateDecl> todo = new HashSet<StateDecl>();
								todo.add(st);

								fr.lip6.move.gal.False fals = GalFactory.eINSTANCE.createFalse();
								BooleanExpression incrIf = fals;
								while (! todo.isEmpty()) {
									StateDecl cur = todo.iterator().next();
									todo.remove(cur);
									if (!seen.contains(st)) {
										seen.add(cur);

										// invariant wins
										int newk = computeInvariant(cur, clock);
										if (newk >= k)
											k=newk;
										if (k!=-1) 
											continue;


										// otherwise, scan outgoing edges
										for (Transition tr : proc.getBody().getTransitions()) {
											if (tr.getSrc() == cur) {
												if (tr.getGuard() != null) { 
													for (Iterator<EObject> it = tr.getGuard().eAllContents() ; it.hasNext() ; ) {
														EObject obj = it.next();
														if (obj instanceof VarAccess) {
															VarAccess va = (VarAccess) obj;
															if (va.getRef() == clock) {
																EObject par = va.eContainer();
																if (par instanceof Comparison) {
																	Comparison comp = (Comparison) par;
																	fr.lip6.move.gal.BooleanExpression rcomp = convertToGAL(comp);
																	if (comp.getLeft() == va) {
																		//  x op expr
																		switch (comp.getOperator()) {
																		case GE :
																		case GT :
																			fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
																			not.setValue(rcomp);
																			rcomp = not;
																			break;
																		case LT :
																		case LE :
																			// keep positive form of guard
																			break;
																		case EQ :
																		case NE :
																			throw new ArrayIndexOutOfBoundsException("equality ==  and difference != on clocks is not supported currently.");
																		}
																	} else if (comp.getRight() == va) {
																		//  expr op x
																		switch (comp.getOperator()) {
																		case GE :
																		case GT :
																			// keep positive form of guard
																			break;
																		case LT :
																		case LE :
																			fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
																			not.setValue(rcomp);
																			rcomp = not;
																			break;
																		case EQ :
																		case NE :
																			throw new ArrayIndexOutOfBoundsException("equality ==  and difference != on clocks is not supported currently.");
																		}
																	} else {
																		// should not happen
																		throw new ArrayIndexOutOfBoundsException("Unexpected test failed, internal error, sorry.");																			
																	}
																	if (incrIf != fals) {
																		fr.lip6.move.gal.Or or = GalFactory.eINSTANCE.createOr();
																		or.setLeft(incrIf);
																		or.setRight(rcomp);
																		incrIf = or;
																	} else {
																		incrIf = rcomp;
																	}
																}
															}
														}
													}
												} // end of potential guard handling

												// see if the clock is reset
												if (! isReset(clock,tr)) {
													todo.add(tr.getDest());
												}


											}
										} // end scan of outgoing transitions

									}
								} // end of processing edges
								if (incrIf != fals) {
									ArrayVarAccess cref = EcoreUtil.copy(varmap.get(clock));
									cref.setIndex(EcoreUtil.copy(pref));

									fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
									and.setLeft(EcoreUtil.copy(testsrc));
									and.setRight(incrIf);
									// inner test : if (x >= bound) abort ; else x++ 
									Ite incr = GalFactory.eINSTANCE.createIte();
									incr.setCond(and);


									// increment of clock
									Assignment ass = GalFactory.eINSTANCE.createAssignment();
									ass.setLeft(EcoreUtil.copy(cref));
									fr.lip6.move.gal.BinaryIntExpression add = GalFactory.eINSTANCE.createBinaryIntExpression();
									add.setOp("+");
									add.setLeft(EcoreUtil.copy(cref));
									add.setRight(galConstant(1));
									ass.setRight(add);

									incr.getIfTrue().add(ass);

									rfor.getActions().add(incr);
								} else {
									// current state is "inactive" for clock x, reset clock on entering the state
									// implement this by adding an assign clock to 0 in entering transition assign statements
									// these will be translated below when handling transition translation
									Assign ass = TimedAutomataFactory.eINSTANCE.createAssign();
									VarAccess va = TimedAutomataFactory.eINSTANCE.createVarAccess();
									va.setRef(clock);
									ass.setLhs(va);
									ass.setRhs(constant(0));
									
									for (Transition itr : proc.getBody().getTransitions()) {
										if (itr.getDest()==st) {
											itr.getAssigns().add(EcoreUtil.copy(ass));
										}
									}
								}
							}
						}
					}
				}
			}

			// handle urgent locations
			// ensure states are unique
			for (StateDecl st : new HashSet<StateDecl>(proc.getBody().getUrgentStates())) {
				// a predicate to test the location
				fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
				testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);


				ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
				ava.setPrefix(pstates);
				ava.setIndex(EcoreUtil.copy(pref));
				testsrc.setLeft(ava);
				testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));


				Ite ite = GalFactory.eINSTANCE.createIte();
				ite.setCond(testsrc);
				// invariant expresses urgency, simply abort if in the location (like an urgent location)
				ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());

				rfor.getActions().add(ite);
			}


			//			// get rid of urgent states they obviously never need to know whether the clock X is ticking.
			//			for (StateDecl st : proc.getBody().getUrgentStates()) {
			//
			//				fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
			//				testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);
			//
			//				ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
			//				ava.setPrefix(pstates);
			//				ava.setIndex(EcoreUtil.copy(pref));
			//				testsrc.setLeft(ava);
			//				testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));
			//
			//				Ite ite = GalFactory.eINSTANCE.createIte();
			//				ite.setCond(testsrc);
			//				ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());
			//
			//				rfor.getActions().add(ite);
			//
			//				elapse.getActions().add(0, rfor);
			//			}


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


				fr.lip6.move.gal.Parameter tparam = GalFactory.eINSTANCE.createParameter();
				tparam.setName("$"+pidname);
				tparam.setType(ptypedef);
				rtr.getParams().add(tparam);

				pref.setRefParam(tparam);

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

				for (Assign ass : tr.getAssigns()) {
					Assignment rass = GalFactory.eINSTANCE.createAssignment();
					rass.setLeft((fr.lip6.move.gal.VarAccess) convertToGAL(ass.getLhs()));
					rass.setRight(convertToGAL(ass.getRhs()));
					rtr.getActions().add(rass);
				}


				gal.getTransitions().add(rtr);
			}
		}

		boolean isEssentialSematics = true;
		if (isEssentialSematics) {
			fr.lip6.move.gal.Transition id = GalFactory.eINSTANCE.createTransition();
			id.setName("id");
			id.setLabel(EcoreUtil.copy(elapselab));
			id.setGuard(GalFactory.eINSTANCE.createTrue());

			gal.getTransitions().add(id);

			fr.lip6.move.gal.Transition succ = GalFactory.eINSTANCE.createTransition();
			succ.setName("succ");
			succ.setGuard(GalFactory.eINSTANCE.createTrue());
			Fixpoint fix = GalFactory.eINSTANCE.createFixpoint();
			Call call = GalFactory.eINSTANCE.createCall();
			call.setLabel(elapselab);
			fix.getActions().add(call);
			succ.getActions().add(fix);

			Call calldtrans = GalFactory.eINSTANCE.createCall();
			calldtrans.setLabel(EcoreUtil.copy(dtranslab));
			succ.getActions().add(calldtrans);

			gal.getTransitions().add(succ);

		} else {
			
			fr.lip6.move.gal.Transition succ1 = GalFactory.eINSTANCE.createTransition();
			succ1.setName("succ1");
			succ1.setGuard(GalFactory.eINSTANCE.createTrue());
			Call call = GalFactory.eINSTANCE.createCall();
			call.setLabel(EcoreUtil.copy(elapselab));
			succ1.getActions().add(call);
			gal.getTransitions().add(succ1);
			
			fr.lip6.move.gal.Transition succ2 = GalFactory.eINSTANCE.createTransition();
			succ2.setName("succ2");
			succ2.setGuard(GalFactory.eINSTANCE.createTrue());
			Call call2 = GalFactory.eINSTANCE.createCall();
			call2.setLabel(EcoreUtil.copy(dtranslab));
			succ2.getActions().add(call2);
			gal.getTransitions().add(succ2);
			
			
		}

		Instantiator.normalizeCalls(gal);	

		return gal;
	}


	private boolean isReset(DeclId clock, Transition tr) {
		for (Assign ass : tr.getAssigns()) {
			if (ass.getLhs().getRef() == clock) {
				return true;
			}
		}

		return false;
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





	private String getTypeName(BasicType type) {
		return type.getClass().getSimpleName().replace("Type", "").replace("Impl", "").toLowerCase();
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
