package fr.lip6.move.xta.togal.transform;

import java.util.ArrayList;
import java.util.Collection;
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


	private static final String SEP = "_";
	private static final int IDLE_CLOCK_VALUE = -1;
	// stores for each template, how many instances
	// and for each of these instances, what are the parameter values if any
	private Map<ProcDecl,List<InstanceInfo>> instances;

	private Converter conv;

	public GALTypeDeclaration transformToGAL(XTA s, String name, boolean essentialSemantics, boolean usehotbit) {
		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		gal.setName(name);

		conv = new Converter();

		buildGlobalVars(s, gal);

		// label : a discrete transition
		Label dtranslab = GalFactory.eINSTANCE.createLabel();
		dtranslab.setName("dtrans");

		buildChannels(s, gal, dtranslab);

		// first objective is to collect info on all instances that need to be built
		// We need to know : 
		// * for each template, how many instances
		// * for each of these instances, what are the parameter values if any
		instances = new HashMap<ProcDecl, List<InstanceInfo>>();
		computeInstances(s);

		// the elapse transition
		Label elapselab = GalFactory.eINSTANCE.createLabel();
		fr.lip6.move.gal.Transition elapse = GalFactory.eINSTANCE.createTransition();
		elapse.setName("elapse");
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

			if (usehotbit) {
				TypedefDeclaration loctype = GalFactory.eINSTANCE.createTypedefDeclaration();
				loctype.setName(proc.getName()+SEP+"state_t");
				loctype.setMin(galConstant(0));
				loctype.setMax(galConstant(proc.getBody().getStates().size()-1));
				gal.getTypes().add(loctype);

				pstates.setHotbit(true);
				pstates.setHottype(loctype);
			}

			// compute initial state
			int initid = proc.getBody().getStates().indexOf(proc.getBody().getInitState());
			for (int i=0; i<nbinst; i++ ) {
				pstates.getValues().add(galConstant(initid));
			}
			gal.getArrays().add(pstates);

			// add an array for each parameter of the template
			conv.clearParams();
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

				conv.addParameter(param,ava);
				paramindex++;
			}




			TypedefDeclaration ptypedef = GalFactory.eINSTANCE.createTypedefDeclaration();
			ptypedef.setName(getPidName(proc)+"_t");
			ptypedef.setMin(galConstant(0));
			ptypedef.setMax(galConstant(nbinst-1));
			gal.getTypes().add(ptypedef);


			// add an array for each variable of the template
			conv.clearVars();
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
						init = conv.convertToGAL(did.getInit().getExpr());
					}
					for (int i=0; i < nbinst ; i++) {
						vvalues.getValues().add(EcoreUtil.copy(init));
					}

					gal.getArrays().add(vvalues);

					ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
					ava.setPrefix(vvalues);
					// will be pid shortly
					ava.setIndex(galConstant(0));
					conv.addVariable(did,ava);
				}
			}		


			// create for loop
			For rfor = GalFactory.eINSTANCE.createFor();
			fr.lip6.move.gal.Parameter param = createParameter(proc, ptypedef);
			rfor.setParam(param);

			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
			pref.setRefParam(param);





			int clockseen =0;
			// for each clock of the template
			for (VariableDecl var : proc.getBody().getVariables()) {
				if (var.getType() instanceof ClockType) {

					for (DeclId clock : var.getDeclid()) {					
						// one label for all possible ways of updating clock, for each parameter value
						Label labupd = GalFactory.eINSTANCE.createLabel();
						labupd.setName("updateClock"+proc.getName()+clock.getName()+"_"+param.getName());

						Call call = GalFactory.eINSTANCE.createCall();
						call.setLabel(labupd);

						rfor.getActions().add(call);
						clockseen++;
						buildUpdateClockTransition(proc, pstates, param, clock,gal,labupd);
					}
				}
			}
			if (clockseen == 0 && ! proc.getBody().getUrgentStates().isEmpty() ) {


				// one label for all possible ways of updating clock, for each parameter value
				Label labreset = GalFactory.eINSTANCE.createLabel();
				labreset.setName("passUrgent"+proc.getName()+"_"+param.getName());

				Call call = GalFactory.eINSTANCE.createCall();
				call.setLabel(labreset);

				rfor.getActions().add(call);
				elapse.getActions().add(rfor);

				buildResetUrgentLocations(proc, pstates, param, gal, labreset);
			} else if (clockseen != 0) {
				elapse.getActions().add(rfor);
			}

			conv.updateParam(pref);
			//			// handle urgent locations
			//			// ensure states are unique
			//			for (StateDecl st : new HashSet<StateDecl>(proc.getBody().getUrgentStates())) {
			//				// a predicate to test the location
			//				fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
			//				testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);
			//
			//
			//				ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
			//				ava.setPrefix(pstates);
			//				ava.setIndex(EcoreUtil.copy(pref));
			//				testsrc.setLeft(ava);
			//				testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));
			//
			//
			//				Ite ite = GalFactory.eINSTANCE.createIte();
			//				ite.setCond(testsrc);
			//				// invariant expresses urgency, simply abort if in the location (like an urgent location)
			//				ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());
			//
			//				rfor.getActions().add(ite);
			//			}


			buildTransitions(gal, proc, pstates, ptypedef);
		}


		addSemantics(gal, dtranslab, elapselab, essentialSemantics);

		Instantiator.normalizeCalls(gal);	

		return gal;
	}


	private void buildResetUrgentLocations(ProcDecl proc, ArrayPrefix pstates,
			fr.lip6.move.gal.Parameter paramsrc, GALTypeDeclaration gal,
			Label labreset) {
		List<StateDecl> nonurgent = new ArrayList<StateDecl>(proc.getBody().getStates());
		nonurgent.removeAll(proc.getBody().getUrgentStates());

		// for each location, compute clock visibility
		for (StateDecl st : nonurgent) {
			fr.lip6.move.gal.Transition tupd = GalFactory.eINSTANCE.createTransition();
			tupd.setName("passUrgent"+proc.getName() +"_"+st.getName());
			tupd.setLabel(EcoreUtil.copy(labreset));
			tupd.setComment("/** State " + st.getName() + " is not urgent. */");

			fr.lip6.move.gal.Parameter param = GalFactory.eINSTANCE.createParameter();
			param.setName(paramsrc.getName());
			param.setType(paramsrc.getType());
			tupd.getParams().add(param);

			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
			pref.setRefParam(param);

			conv.updateParam(pref);
			
			// a predicate to test the location
			ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
			ava.setPrefix(pstates);
			ava.setIndex(EcoreUtil.copy(pref));

			fr.lip6.move.gal.Comparison testsrc = testSource(proc, st, EcoreUtil.copy(ava));
			
			tupd.setGuard(testsrc);
			gal.getTransitions().add(tupd);
		}

	}


	private void buildUpdateClockTransition(ProcDecl proc, ArrayPrefix pstates, fr.lip6.move.gal.Parameter paramsrc, DeclId clock, GALTypeDeclaration gal, Label labupd) {

		// for each location, compute clock visibility
		for (StateDecl st : proc.getBody().getStates()) {

			fr.lip6.move.gal.Transition tupd = GalFactory.eINSTANCE.createTransition();
			tupd.setName("updateClock"+proc.getName()+clock.getName()+"_"+st.getName());
			tupd.setLabel(EcoreUtil.copy(labupd));

			fr.lip6.move.gal.Parameter param = GalFactory.eINSTANCE.createParameter();
			param.setName(paramsrc.getName());
			param.setType(paramsrc.getType());
			tupd.getParams().add(param);

			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
			pref.setRefParam(param);

			conv.updateParam(pref);

			// a predicate to test the location
			ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
			ava.setPrefix(pstates);
			ava.setIndex(EcoreUtil.copy(pref));

			fr.lip6.move.gal.Comparison testsrc = testSource(proc, st, EcoreUtil.copy(ava));

			int k = computeInvariant(st,clock);
			if (k==0 || proc.getBody().getUrgentStates().contains(st)) {
				// NB: k = 0 add to urgent locations
				// proc.getBody().getUrgentStates().add(st);

				// Do not build a transition testing for this local state, hence cannot elapse in these states.
				// try next local state
				continue;
			}
			if (k > 0) {

				// so we have found a normalized "x < k" location invariant

				// add a test to allow elapse
				// clock ref
				fr.lip6.move.gal.IntExpression cref = EcoreUtil.copy(conv.getImage(clock));

				// is max bound reached ?
				fr.lip6.move.gal.Comparison xGEk = GalFactory.eINSTANCE.createComparison();
				xGEk.setOperator(fr.lip6.move.gal.ComparisonOperators.GE);
				xGEk.setLeft(cref);
				xGEk.setRight(galConstant(k));

				// inner test : if (x >= bound) abort ; else x++ 
				Ite incr = GalFactory.eINSTANCE.createIte();
				incr.setCond(xGEk);
				incr.getIfTrue().add(GalFactory.eINSTANCE.createAbort());
				// increment of clock				
				incr.getIfFalse().add(buildIncrement(cref));

				tupd.setGuard(testsrc);
				tupd.getActions().add(incr);
				tupd.setComment("/** State " + st.getName() + " has invariant constraint. */");

				gal.getTransitions().add(tupd);

			} else {
				// no invariant case : find max tracking value, if any
				fr.lip6.move.gal.BooleanExpression incrIf = computeHighestTrackingValue(proc, clock, st, k);


				if (! EcoreUtil.equals(incrIf, GalFactory.eINSTANCE.createFalse())) {
					fr.lip6.move.gal.IntExpression cref = EcoreUtil.copy(conv.getImage(clock));

					// test : if ( incrIf ) x++ ; else nop; 
					Ite incr = GalFactory.eINSTANCE.createIte();
					incr.setCond(incrIf);					
					incr.getIfTrue().add(buildIncrement(cref));

					tupd.setGuard(EcoreUtil.copy(testsrc));					
					tupd.getActions().add(incr);
					tupd.setComment("/** State " + st.getName() + " has a max tracking value. */");
					gal.getTransitions().add(tupd);


				} else {
					// current state is "inactive" for clock x, reset clock on entering the state
					// implement this by adding an assign clock to -1 in entering transition assign statements
					// these will be translated below when handling transition translation
					
					for (Transition itr : proc.getBody().getTransitions()) {
						if (itr.getDest()==st) {
							boolean doIt = true;
							for (Assign stat : itr.getAssigns()) {
								if (stat.getLhs().getRef()==clock) {
									stat.setRhs(constant(IDLE_CLOCK_VALUE));
									doIt =false;
									break;
								}
							}
							if (doIt) {
								Assign ass = TimedAutomataFactory.eINSTANCE.createAssign();
								VarAccess va = TimedAutomataFactory.eINSTANCE.createVarAccess();
								va.setRef(clock);
								ass.setLhs(va);
								ass.setRhs(constant(IDLE_CLOCK_VALUE));
								itr.getAssigns().add(ass);
							}
						}
					}
					
					// also test whether the initial state is inactive, if so, set clock to -1 initially.
					if (proc.getBody().getInitState()==st) {
						ArrayVarAccess ctabref = (ArrayVarAccess) conv.getImage(clock);
						ctabref.getPrefix().getValues().clear();
						for (int i=0; i < ctabref.getPrefix().getSize() ; i++) {
							ctabref.getPrefix().getValues().add(galConstant(-1));
						}
					}

					tupd.setGuard(EcoreUtil.copy(testsrc));
					tupd.setComment("/** State " + st.getName() + " is inactive. */");
					gal.getTransitions().add(tupd);
				}
			}

		}
	}


	private fr.lip6.move.gal.BooleanExpression computeHighestTrackingValue(ProcDecl proc, DeclId clock, StateDecl st, int k) {
		BooleanExpression fals = GalFactory.eINSTANCE.createFalse();
		fr.lip6.move.gal.BooleanExpression incrIf = fals ;

		Set<StateDecl> seen = new HashSet<StateDecl>();
		Set<StateDecl> todo = new HashSet<StateDecl>();
		todo.add(st);
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
						incrIf = handleTransitionClockGuard(clock, tr,	incrIf);

						// see if the clock is reset
						if (! isReset(clock,tr)) {
							todo.add(tr.getDest());
						}


					}
				} // end scan of outgoing transitions

			}
		} // end of processing edges
		return incrIf;
	}


	private void buildTransitions(GALTypeDeclaration gal, ProcDecl proc,
			ArrayPrefix pstates, TypedefDeclaration ptypedef) {
		// transitions
		int tid=1;
		for (Transition tr : proc.getBody().getTransitions()) {
			fr.lip6.move.gal.Transition rtr = GalFactory.eINSTANCE.createTransition();
			rtr.setName("t"+ tid++ +proc.getName()+tr.getSrc().getName()+"_"+tr.getDest().getName());


			Label lab = computeLabel(tr);
			rtr.setLabel(lab);


			fr.lip6.move.gal.Parameter tparam = GalFactory.eINSTANCE.createParameter();
			tparam.setName("$"+getPidName(proc));
			tparam.setType(ptypedef);

			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
			pref.setRefParam(tparam);

			rtr.getParams().add(tparam);

			conv.updateParam(pref);

			ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
			ava.setPrefix(pstates);
			ava.setIndex(EcoreUtil.copy(pref));

			fr.lip6.move.gal.Comparison testsrc = testSource(proc, tr.getSrc(), ava);

			fr.lip6.move.gal.BooleanExpression guard = testsrc;

			if (tr.getGuard() != null) {
				fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
				and.setLeft(guard);
				and.setRight(conv.convertToGAL(tr.getGuard()));

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
				rass.setLeft((fr.lip6.move.gal.VarAccess) conv.convertToGAL(ass.getLhs()));
				rass.setRight(conv.convertToGAL(ass.getRhs()));
				rtr.getActions().add(rass);
			}


			gal.getTransitions().add(rtr);
		}
	}


	private fr.lip6.move.gal.Parameter createParameter(ProcDecl proc,
			TypedefDeclaration ptypedef) {
		fr.lip6.move.gal.Parameter param = GalFactory.eINSTANCE.createParameter();
		param.setName("$"+getPidName(proc));
		param.setType(ptypedef);
		return param;
	}


	private String getPidName(ProcDecl proc) {
		return proc.getName() +"id";
	}


	private void addSemantics(GALTypeDeclaration gal, Label dtranslab,
			Label elapselab, boolean isEssentialSematics) {
		if (isEssentialSematics) {
			fr.lip6.move.gal.Transition id = GalFactory.eINSTANCE.createTransition();
			id.setName("id");
			id.setLabel(EcoreUtil.copy(elapselab));
			id.setGuard(GalFactory.eINSTANCE.createTrue());
			id.setComment("/** to accumulate states in the fixpoint :do nothing */");
			gal.getTransitions().add(id);

			fr.lip6.move.gal.Transition succ = GalFactory.eINSTANCE.createTransition();
			succ.setName("succ");
			succ.setGuard(GalFactory.eINSTANCE.createTrue());
			Fixpoint fix = GalFactory.eINSTANCE.createFixpoint();
			fix.setComment("/** Explore and accumulate all states reachable by letting time elapse */");
			Call call = GalFactory.eINSTANCE.createCall();
			call.setLabel(elapselab);
			fix.getActions().add(call);
			succ.getActions().add(fix);

			Call calldtrans = GalFactory.eINSTANCE.createCall();
			calldtrans.setLabel(EcoreUtil.copy(dtranslab));
			calldtrans.setComment("/** Fire one step of the normal discrete transitions : result is essential states.*/");
			succ.getActions().add(calldtrans);

			succ.setComment("/** Go to successor essential states, in one atomic step. */");
			gal.getTransitions().add(succ);
			gal.setName(gal.getName()+"_pop");
		} else {

			fr.lip6.move.gal.Transition succ1 = GalFactory.eINSTANCE.createTransition();
			succ1.setName("succ1");
			succ1.setGuard(GalFactory.eINSTANCE.createTrue());
			Call call = GalFactory.eINSTANCE.createCall();
			call.setLabel(EcoreUtil.copy(elapselab));
			succ1.getActions().add(call);
			succ1.setComment("/** Allow locally (no label) to fire one time step. */"); 
			gal.getTransitions().add(succ1);

			fr.lip6.move.gal.Transition succ2 = GalFactory.eINSTANCE.createTransition();
			succ2.setName("succ2");
			succ2.setGuard(GalFactory.eINSTANCE.createTrue());
			Call call2 = GalFactory.eINSTANCE.createCall();
			call2.setLabel(EcoreUtil.copy(dtranslab));
			succ2.getActions().add(call2);
			succ1.setComment("/** Allow locally (no label) to fire one discrete transition. */"); 
			
			gal.getTransitions().add(succ2);


		}
	}


	private fr.lip6.move.gal.Comparison testSource(ProcDecl proc, StateDecl st,
			ArrayVarAccess ava) {
		fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
		testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);
		testsrc.setLeft(ava);
		testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));
		return testsrc;
	}


	private Assignment buildIncrement(fr.lip6.move.gal.IntExpression variable) {
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft((fr.lip6.move.gal.VarAccess) EcoreUtil.copy(variable));
		fr.lip6.move.gal.BinaryIntExpression add = GalFactory.eINSTANCE.createBinaryIntExpression();
		add.setOp("+");
		add.setLeft(EcoreUtil.copy(variable));
		add.setRight(galConstant(1));
		ass.setRight(add);
		return ass;
	}


	private fr.lip6.move.gal.BooleanExpression handleTransitionClockGuard(DeclId clock,
			Transition tr, fr.lip6.move.gal.BooleanExpression addTo) {
		if (tr.getGuard() != null) { 
			for (Iterator<EObject> it = tr.getGuard().eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof VarAccess) {
					VarAccess va = (VarAccess) obj;
					if (va.getRef() == clock) {
						EObject par = va.eContainer();
						if (par instanceof Comparison) {
							Comparison comp = (Comparison) par;
							fr.lip6.move.gal.BooleanExpression rcomp = conv.convertToGAL(comp);
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
							if (! EcoreUtil.equals(addTo, GalFactory.eINSTANCE.createFalse())) {
								fr.lip6.move.gal.Or or = GalFactory.eINSTANCE.createOr();
								or.setLeft(addTo);
								or.setRight(rcomp);
								addTo = or;
							} else {
								addTo = rcomp;
							}
						}
					}
				}
			}
		} // end of potential guard handling
		return addTo;
	}


	private Label computeLabel(Transition tr) {
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
		return lab;
	}


	/// suppress warning about generic type of Collections.EMPTY_LIST (who cares ?)
	@SuppressWarnings("unchecked")
	private void computeInstances(XTA s) {
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
	}


	private void buildGlobalVars(XTA s, GALTypeDeclaration gal) {
		// Set up global variables if any
		conv.clearGlobals();
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
						init = conv.convertToGAL(did.getInit().getExpr());
					}
					vvalues.setValue(init);

					gal.getVariables().add(vvalues);

					VariableRef vr  =GalFactory.eINSTANCE.createVariableRef();
					vr.setReferencedVar(vvalues);
					conv.addGlobal(did,vr);
				} else {
					ConstParameter cp = GalFactory.eINSTANCE.createConstParameter();
					cp.setName("$"+varname);
					int init = 0;
					if (did.getInit() != null) {
						fr.lip6.move.gal.IntExpression inite = conv.convertToGAL(did.getInit().getExpr());
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
					conv.addGlobal(did, pref);
				}
			}
		}
	}


	private void buildChannels(XTA s, GALTypeDeclaration gal, Label dtranslab) {
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


	private void addInstance(String name, ProcDecl type, Collection<IntExpression> list) {
		List<InstanceInfo> inslist = instances.get(type);
		if (inslist == null) {
			inslist = new ArrayList<InstanceInfo>();
			instances.put(type, inslist);
		}
		int id = inslist.size();

		// convert the arguments
		List<fr.lip6.move.gal.IntExpression> rargs = new  ArrayList<fr.lip6.move.gal.IntExpression>(list.size());
		for (IntExpression e : list ) {
			rargs.add(conv.convertToGAL(e));
		}
		inslist.add(new InstanceInfo(name, rargs, id));		
	}




}


