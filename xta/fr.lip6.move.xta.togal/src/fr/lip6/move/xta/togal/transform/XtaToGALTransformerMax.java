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
import fr.lip6.move.xta.togal.transform.ConverterMax;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Fixpoint;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;

public class XtaToGALTransformerMax {

	// a label for discrete transitions
	private Label dtranslab;
	// a label for elapse
	private Label elapseLab;
	// a map to store prefixes and ensure proper semantical context
	private Map<ProcDecl,ArrayPrefix> prefixes;
	// a map to store typedefs of processes and ensure proper semantical context
	private Map<ProcDecl,TypedefDeclaration> typedefs;
	// two maps to store parameters and param refs (for EGAL 'for' loops)
	private Map<ProcDecl,fr.lip6.move.gal.Parameter> parameters;
	private Map<ProcDecl,ParamRef> prefs;
	// constructor
	public XtaToGALTransformerMax() {
		// create the label for discrete transitions
		dtranslab = GalFactory.eINSTANCE.createLabel();
		dtranslab.setName("dtrans");
		// create the label for elapse transition
		elapseLab = GalFactory.eINSTANCE.createLabel();
		elapseLab.setName("elapseOne");
		// create prefix map
		prefixes = new HashMap<ProcDecl,ArrayPrefix>();
		// create typedef map
		typedefs = new HashMap<ProcDecl,TypedefDeclaration>();
		// create parameter maps
		parameters = new HashMap<ProcDecl,fr.lip6.move.gal.Parameter>();
		prefs = new HashMap<ProcDecl,ParamRef>();
	}

	private static final String SEP = "";
	// stores for each template, how many instances
	// and for each of these instances, what are the parameter values if any
	private Map<ProcDecl,List<InstanceInfo>> instances;

	private ConverterMax conv;
	
	private fr.lip6.move.gal.Transition createElapse() {
		fr.lip6.move.gal.Transition elapse = GalFactory.eINSTANCE.createTransition();
		elapse.setName("elapse");
		elapse.setLabel(elapseLab);
		elapse.setGuard(GalFactory.eINSTANCE.createTrue());
		return elapse;
	}
	
	private void addArrayParameter(	Entry<ProcDecl, List<InstanceInfo>> pi,
									ProcDecl proc,
									int nbinst,
									GALTypeDeclaration gal) {
		int paramindex = 0;
		for (Parameter param: proc.getParams()) {
			ArrayPrefix pvalues = GalFactory.eINSTANCE.createArrayPrefix();
			pvalues.setName(proc.getName()+SEP +param.getName());
			pvalues.setSize(nbinst);

			for (InstanceInfo ins : pi.getValue()) {
				pvalues.getValues().add(ins.paramValues.get(paramindex));
			}
			gal.getArrays().add(pvalues);


			VariableReference ava = GF2.createArrayVarAccess(pvalues, galConstant(0));
			
			conv.addParameter(proc,param,ava);
			paramindex++;
		}
	}
	
	private void addVariableArray(ProcDecl proc, int nbinst, GALTypeDeclaration gal) {
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
					init = conv.convertToGAL(did.getInit().getExpr(),proc);
				}
				for (int i=0; i < nbinst ; i++) {
					vvalues.getValues().add(EcoreUtil.copy(init));
				}

				gal.getArrays().add(vvalues);

				VariableReference ava = GF2.createArrayVarAccess(vvalues, galConstant(0));
				conv.addVariable(proc,did,ava);
			}
		}
	}
	
	private int boundGuard(BooleanExpression expr,DeclId clock) {
		if (expr instanceof And || expr instanceof Or || expr instanceof Not) {
			int m = -1;
			for (EObject son: expr.eContents()) {
				// all children should be BooleanExpressions as well
				assert(son instanceof BooleanExpression);
				m = Math.max(m, boundGuard((BooleanExpression) son,clock));
			}
			return m;
		} else if (expr instanceof Comparison) {
			Comparison comp = (Comparison) expr;
			// check lhs for clock
			if (comp.getLeft() instanceof VarAccess) {
				if (((VarAccess)comp.getLeft()).getRef() == clock) {
					// check rhs for constant
					return resolveConstant(comp.getRight());
				}
			}
			// conversely, check rhs for clock
			if (comp.getRight() instanceof VarAccess) {
				if (((VarAccess)comp.getLeft()).getRef() == clock) {
					// check lhs for constant
					return resolveConstant(comp.getLeft());
				}
			}
		}
		return -1;
	}
	
	private ArrayPrefix createArrayPrefix(ProcDecl proc,int nbinst) {
		ArrayPrefix res = GalFactory.eINSTANCE.createArrayPrefix();
		res.setName(proc.getName()+SEP+"state");
		res.setSize(nbinst);
		// compute initial state
		int initid = proc.getBody().getStates().indexOf(proc.getBody().getInitState());
		for (int i=0; i<nbinst; i++) {
			res.getValues().add(galConstant(initid));
		}
		return res;
	}

	private int computeBound(ProcDecl proc,StateDecl st,DeclId clock) {
		Set<StateDecl> seen = new HashSet<StateDecl>();
		Set<StateDecl> todo = new HashSet<StateDecl>();
		todo.add(st);
		
		int m = -1;
		while (! todo.isEmpty()) {
			StateDecl cur = todo.iterator().next();
			todo.remove(cur);
			if (! seen.contains(cur)) {
				seen.add(cur);
				m = Math.max(m,computeInvariant(cur,clock));
				// scan outgoing edges
				// for each transition with matching initial location
				for (Transition tr: proc.getBody().getTransitions()) {
					if (tr.getSrc() == cur) {
						m = Math.max(m, boundGuard(tr.getGuard(),clock));
						// if the clock is reset, stop here
						if (! isReset(clock,tr)) {
							todo.add(tr.getDest());
						}
					}
				} // end scan of outgoing edges
			}
		} // end while (search for ceiling)
		return m;
	}
	
	private Ite createElapseLocal(VariableReference ava,ProcDecl proc,StateDecl st,DeclId clock,int c) {
		// gal [P.state == st]
		fr.lip6.move.gal.Comparison testsrc = testSource(proc,st,ava);
		
		// we want to build:
		// if (state == st) { if (x > c) { } else { x++; } ; checkInvariant }
		
		// main 'if'
		Ite ite = GalFactory.eINSTANCE.createIte();
		ite.setCond(testsrc);
		
		// secondary 'if'
		// if the ceiling is -1, the test x > c is always true, we can simplify
		// if (x > c) {} else {x++}  becomes nop
		if (c >= 0) {
			// the 'if' itself
			Ite incr = GalFactory.eINSTANCE.createIte();
			fr.lip6.move.gal.IntExpression cref = EcoreUtil.copy(conv.getImage(proc,clock));
			// the increment statement
			Assignment assignment = buildIncrement(cref);
			// the test [x > c] (poorly chosen name)
			fr.lip6.move.gal.Comparison xGtk = GalFactory.eINSTANCE.createComparison();
			xGtk.setOperator(fr.lip6.move.gal.ComparisonOperators.GT);
			xGtk.setLeft(cref);
			xGtk.setRight(galConstant(c));
			
			// set the condition
			incr.setCond(xGtk);
			// set the increment
			incr.getIfFalse().add(assignment);
			ite.getIfTrue().add(incr);
		}
		// check the invariant if necessary
		if (st.getInvariant() != null) {
			// 'if' to check the invariant after incrementing the clock
			Ite checkInv = GalFactory.eINSTANCE.createIte();
			checkInv.setCond(conv.convertToGAL(st.getInvariant(),proc));
			checkInv.getIfFalse().add(GalFactory.eINSTANCE.createAbort());
			ite.getIfTrue().add(checkInv);
		}
		return ite;
	}
	
	public GALTypeDeclaration transformToGAL(XTA s, String name) {
		// the resulting gal
		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		// set GAL name
		gal.setName(name);
		// initialize the converter
		conv = new ConverterMax();
		// build global variables in GAL
		buildGlobalVars(s, gal);
		// build channels in GAL
		buildChannels(s, gal, dtranslab);

		// first objective is to collect info on all instances that need to be built
		// We need to know : 
		// * for each template, how many instances
		// * for each of these instances, what are the parameter values if any
		instances = new HashMap<ProcDecl, List<InstanceInfo>>();
		computeInstances(s);

		// the elapse transition
		fr.lip6.move.gal.Transition elapse = createElapse();
		gal.getTransitions().add(elapse);

		// a map to store the ceilings of global clocks
		Map<DeclId,Map<ProcDecl,Map<StateDecl,Integer>>> globalCeilings = new HashMap<DeclId,Map<ProcDecl,Map<StateDecl,Integer>>>();

		// now we have the info, build the stuff
		// for each process declaration
		for (Entry<ProcDecl, List<InstanceInfo>> pi : instances.entrySet()) {
			ProcDecl proc = pi.getKey();
			int nbinst = pi.getValue().size();
			ArrayPrefix pstates = createArrayPrefix(proc,nbinst);
			prefixes.put(proc, pstates);
			gal.getArrays().add(pstates);

			// add an array for each parameter of the template
//			conv.clearParams();
			addArrayParameter(pi,proc,nbinst,gal);
			
			// declare a type for the number of instances
			TypedefDeclaration ptypedef = GalFactory.eINSTANCE.createTypedefDeclaration();
			ptypedef.setName(getPidName(proc)+"_t");
			ptypedef.setMin(galConstant(0));
			ptypedef.setMax(galConstant(nbinst-1));
			typedefs.put(proc,ptypedef);
			gal.getTypedefs().add(ptypedef);

			// add an array for each variable of the template
//			conv.clearVars();
			addVariableArray(proc,nbinst,gal);		

			// create EGAL 'for' loop
			For rfor = GalFactory.eINSTANCE.createFor();
			fr.lip6.move.gal.Parameter param = createParameter(proc, ptypedef);
			rfor.setParam(param);
			// create a param ref
			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
			pref.setRefParam(param);
			//parameters.put(proc,param);
			prefs.put(proc,pref);

			conv.updateParam(proc,pref);
			
			// a map to store the ceilings of local clocks
			Map<DeclId,Map<StateDecl,Integer>> localCeilings = new HashMap<DeclId,Map<StateDecl,Integer>>();
			// for each location
			for (StateDecl st: proc.getBody().getStates()) {
				// compute clock ceilings
				// for each local clock
				for (VariableDecl var: proc.getBody().getVariables()) {
					if (var.getType() instanceof ClockType) {
						for (DeclId clock: var.getDeclid()) {
							if (localCeilings.get(clock) == null) {
								localCeilings.put(clock, new HashMap<StateDecl,Integer>());
							}
							localCeilings.get(clock).put(st,computeBound(proc,st,clock));
							// check for urgency
							if (computeInvariant(st,clock) == 0) {
								// NB: k = 0 add to urgent locations
								proc.getBody().getUrgentStates().add(st);
							}
						}
					}
				}
				// for each global clock
				for (VariableDecl var: s.getVariables()) {
					if (var.getType() instanceof ClockType) {
						for (DeclId clock: var.getDeclid()) {
							if (globalCeilings.get(clock) == null) {
								globalCeilings.put(clock, new HashMap<ProcDecl,Map<StateDecl,Integer>>());
							}
							if (globalCeilings.get(clock).get(proc) == null) {
								globalCeilings.get(clock).put(proc, new HashMap<StateDecl,Integer>());
							}
							globalCeilings.get(clock).get(proc).put(st,computeBound(proc,st,clock));
						}
					}
				}
			}
			
			// elapse for all instances
			elapse.getActions().add(rfor);
			
			// at this point, we have computed the ceilings of all local clocks relative to each location
			// we can thus add to the elapse transition
			// for each clock
			for (Map.Entry<DeclId, Map<StateDecl, Integer>> entry: localCeilings.entrySet()) {
				// for each location
				for (Map.Entry<StateDecl, Integer> st: entry.getValue().entrySet()) {
					// GAL ref to the current location
					VariableReference ava = GF2.createArrayVarAccess(pstates, EcoreUtil.copy(pref));

					rfor.getActions().add(createElapseLocal(ava,proc,st.getKey(),entry.getKey(),st.getValue()));
				}
			}
			
			// handle urgent locations
			// ensure states are unique
			for (StateDecl st : new HashSet<StateDecl>(proc.getBody().getUrgentStates())) {
				// a predicate to test the location
				fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
				testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);

				VariableReference ava = GF2.createArrayVarAccess(pstates, EcoreUtil.copy(pref));
				testsrc.setLeft(ava);
				testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));

				Ite ite = GalFactory.eINSTANCE.createIte();
				ite.setCond(testsrc);
				// invariant expresses urgency, simply abort if in the location (like an urgent location)
				ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());

				rfor.getActions().add(ite);
			}

			// build the transitions
			buildTransitions(gal, proc, pstates, ptypedef);
		}
		
		// handle the global clocks and add them to elapse
		// for each global clock
		for (Map.Entry<DeclId,Map<ProcDecl,Map<StateDecl,Integer>>> mi: globalCeilings.entrySet()) {
			Map<Integer,Set<MyPair>> rgCeilings = new HashMap<Integer,Set<MyPair>>();
			for (Map.Entry<ProcDecl, Map<StateDecl,Integer>> mj: mi.getValue().entrySet()) {
				for (Map.Entry<StateDecl, Integer> mk: mj.getValue().entrySet()) {
					if (rgCeilings.get(mk.getValue()) == null) {
						rgCeilings.put(mk.getValue(), new HashSet<MyPair>());
					}
					rgCeilings.get(mk.getValue()).add(new MyPair(mj.getKey(),mk.getKey()));
				}
			}
			
			fr.lip6.move.gal.BooleanExpression or = GalFactory.eINSTANCE.createFalse();
			// IntExpression to refer to the current clock
			fr.lip6.move.gal.IntExpression cref = EcoreUtil.copy(conv.getGImage(mi.getKey()));
			// statement to increment the current clock
			Assignment incr = buildIncrement(cref);
			// walk rgCeilings
			for (Map.Entry<Integer, Set<MyPair>> si: rgCeilings.entrySet()) {
				fr.lip6.move.gal.Comparison comp = GalFactory.eINSTANCE.createComparison();
				comp.setOperator(fr.lip6.move.gal.ComparisonOperators.LE);
				comp.setLeft(cref);
				comp.setRight(galConstant(si.getKey()));
				fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
				and.setLeft(comp);
				and.setRight(buildGlobalElapseGuard(si.getValue()));
				fr.lip6.move.gal.Or aux = GalFactory.eINSTANCE.createOr();
				aux.setLeft(or);
				aux.setRight(and);
				or = aux;
			}
			// if ''or'' then incr x else nop
			Ite ite = GalFactory.eINSTANCE.createIte();
			ite.setCond(or);
			ite.getIfTrue().add(incr);
			elapse.getActions().add(ite);
		}
		// add the invariants check for the global clocks
		// not an easy part :'(
		for (Entry<ProcDecl,List<InstanceInfo>> pi: instances.entrySet()) {
			ProcDecl proc = pi.getKey();
			int nbinst = pi.getValue().size();
			
			// declare a type for the number of instances
			TypedefDeclaration ptypedef = typedefs.get(proc);
			
			// create EGAL 'for' loop
			For rfor = GalFactory.eINSTANCE.createFor();
			fr.lip6.move.gal.Parameter param = GalFactory.eINSTANCE.createParameter();
			param.setName("$"+getPidName(proc)+"2");
			param.setType(ptypedef);
			rfor.setParam(param);
			// create a param ref
//			ParamRef pref = prefs.get(proc);
			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
			pref.setRefParam(param);
			conv.updateParam(proc,pref);
//			ParamRef pref = GalFactory.eINSTANCE.createParamRef();
//			pref.setRefParam(param);
			
			// for each location
			for (StateDecl st: pi.getKey().getBody().getStates()) {
				// if it has an invariant
				if (st.getInvariant() != null && conv.convertToGAL(st.getInvariant(),proc) != GalFactory.eINSTANCE.createTrue()) {
					// a predicate to test the location
					fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
					testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);

					VariableReference ava = GF2.createArrayVarAccess(prefixes.get(proc),EcoreUtil.copy(pref));
					testsrc.setLeft(ava);
					testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));
					
					fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
					and.setLeft(testsrc);
					fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
					not.setValue(conv.convertToGAL(st.getInvariant(),proc));
					and.setRight(not);
					
					Ite ite = GalFactory.eINSTANCE.createIte();
					ite.setCond(and);
					ite.getIfTrue().add(GalFactory.eINSTANCE.createAbort());

					rfor.getActions().add(ite);
				}
			}
			elapse.getActions().add(rfor);
		}
		
		boolean isEssentialSemantics = true;

		addSemantics(gal, dtranslab, elapseLab, isEssentialSemantics);

		Instantiator.normalizeCalls(gal);	

		return gal;
	}

	private fr.lip6.move.gal.BooleanExpression buildGlobalElapseGuard(Set<MyPair> s) {
		fr.lip6.move.gal.BooleanExpression result = GalFactory.eINSTANCE.createFalse();
		for (MyPair si: s) {
			ProcDecl proc = si.proc;
			int nbinst = instances.get(proc).size();
			for (int i = 0 ; i < nbinst; ++i) {
				// GAL ref to the current location
				VariableReference ava = GF2.createArrayVarAccess(prefixes.get(proc),galConstant(i));
				fr.lip6.move.gal.Or aux = GalFactory.eINSTANCE.createOr();
				aux.setLeft(result);
				aux.setRight(testSource(proc,si.loc,ava));
				result = aux;
			}
		}
		return result;
	}

	class MyPair {
		public ProcDecl proc;
		public StateDecl loc;
		public MyPair(ProcDecl p,StateDecl s) {
			proc = p;
			loc = s;
		}
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

			conv.updateParam(proc,pref);
			
			VariableReference ava = GF2.createArrayVarAccess(pstates, EcoreUtil.copy(pref));
			
			fr.lip6.move.gal.Comparison testsrc = testSource(proc, tr.getSrc(), ava);
			
			fr.lip6.move.gal.BooleanExpression guard = testsrc;

			if (tr.getGuard() != null) {
				fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
				and.setLeft(guard);
				and.setRight(conv.convertToGAL(tr.getGuard(),proc));
				guard = and;
			}

			rtr.setGuard(guard);

			// updates now

			// state update, if needed
			if (tr.getSrc() != tr.getDest()) {
				VariableReference pst = GF2.createArrayVarAccess(pstates, EcoreUtil.copy(pref));

				Assignment ass = GalFactory.eINSTANCE.createAssignment();
				ass.setLeft(pst);
				ass.setRight(galConstant(proc.getBody().getStates().indexOf(tr.getDest())));
				rtr.getActions().add(ass);
			}

			for (Assign ass : tr.getAssigns()) {
				Assignment rass = GalFactory.eINSTANCE.createAssignment();
				rass.setLeft((fr.lip6.move.gal.VariableReference) conv.convertToGAL(ass.getLhs(),proc));
				rass.setRight(conv.convertToGAL(ass.getRhs(),proc));
				rtr.getActions().add(rass);
			}

			// check that the invariant in the new location is enforced
			Ite checkInv = GalFactory.eINSTANCE.createIte();
			if (tr.getDest().getInvariant() != null) {
				checkInv.setCond(conv.convertToGAL(tr.getDest().getInvariant(),proc));
			} else {
				checkInv.setCond(GalFactory.eINSTANCE.createTrue());
			}
			checkInv.getIfFalse().add(GalFactory.eINSTANCE.createAbort());
			rtr.getActions().add(checkInv);
			
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

			gal.getTransitions().add(id);

			fr.lip6.move.gal.Transition succ = GalFactory.eINSTANCE.createTransition();
			succ.setName("succ");
			succ.setGuard(GalFactory.eINSTANCE.createTrue());
			Fixpoint fix = GalFactory.eINSTANCE.createFixpoint();
			SelfCall call = GalFactory.eINSTANCE.createSelfCall();
			call.setLabel(elapselab);
			fix.getActions().add(call);
			succ.getActions().add(fix);

			SelfCall calldtrans = GalFactory.eINSTANCE.createSelfCall();
			calldtrans.setLabel(EcoreUtil.copy(dtranslab));
			succ.getActions().add(calldtrans);

			gal.getTransitions().add(succ);

		} else {
			
			fr.lip6.move.gal.Transition succ1 = GalFactory.eINSTANCE.createTransition();
			succ1.setName("succ1");
			succ1.setGuard(GalFactory.eINSTANCE.createTrue());
			SelfCall call = GalFactory.eINSTANCE.createSelfCall();
			call.setLabel(EcoreUtil.copy(elapselab));
			succ1.getActions().add(call);
			gal.getTransitions().add(succ1);
			
			fr.lip6.move.gal.Transition succ2 = GalFactory.eINSTANCE.createTransition();
			succ2.setName("succ2");
			succ2.setGuard(GalFactory.eINSTANCE.createTrue());
			SelfCall call2 = GalFactory.eINSTANCE.createSelfCall();
			call2.setLabel(EcoreUtil.copy(dtranslab));
			succ2.getActions().add(call2);
			gal.getTransitions().add(succ2);
			
			
		}
	}


	private fr.lip6.move.gal.Comparison testSource(ProcDecl proc, StateDecl st,
			VariableReference ava) {
		fr.lip6.move.gal.Comparison testsrc = GalFactory.eINSTANCE.createComparison();
		testsrc.setOperator(fr.lip6.move.gal.ComparisonOperators.EQ);
		testsrc.setLeft(ava);
		testsrc.setRight(galConstant(proc.getBody().getStates().indexOf(st)));
		return testsrc;
	}


	private Assignment buildIncrement(fr.lip6.move.gal.IntExpression variable) {
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft((fr.lip6.move.gal.VariableReference) EcoreUtil.copy(variable));
		fr.lip6.move.gal.BinaryIntExpression add = GalFactory.eINSTANCE.createBinaryIntExpression();
		add.setOp("+");
		add.setLeft(EcoreUtil.copy(variable));
		add.setRight(galConstant(1));
		ass.setRight(add);
		return ass;
	}


//	private fr.lip6.move.gal.BooleanExpression handleTransitionClockGuard(DeclId clock,
//			Transition tr, fr.lip6.move.gal.BooleanExpression addTo,
//			fr.lip6.move.gal.False fals) {
//		if (tr.getGuard() != null) { 
//			for (Iterator<EObject> it = tr.getGuard().eAllContents() ; it.hasNext() ; ) {
//				EObject obj = it.next();
//				if (obj instanceof VarAccess) {
//					VarAccess va = (VarAccess) obj;
//					if (va.getRef() == clock) {
//						EObject par = va.eContainer();
//						if (par instanceof Comparison) {
//							Comparison comp = (Comparison) par;
//							fr.lip6.move.gal.BooleanExpression rcomp = conv.convertToGAL(comp);
//							if (comp.getLeft() == va) {
//								//  x op expr
//								switch (comp.getOperator()) {
//								case GE :
//								case GT :
//									fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
//									not.setValue(rcomp);
//									rcomp = not;
//									break;
//								case LT :
//								case LE :
//									// keep positive form of guard
//									break;
//								case EQ :
//								case NE :
//									throw new ArrayIndexOutOfBoundsException("equality ==  and difference != on clocks is not supported currently.");
//								}
//							} else if (comp.getRight() == va) {
//								//  expr op x
//								switch (comp.getOperator()) {
//								case GE :
//								case GT :
//									// keep positive form of guard
//									break;
//								case LT :
//								case LE :
//									fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
//									not.setValue(rcomp);
//									rcomp = not;
//									break;
//								case EQ :
//								case NE :
//									throw new ArrayIndexOutOfBoundsException("equality ==  and difference != on clocks is not supported currently.");
//								}
//							} else {
//								// should not happen
//								throw new ArrayIndexOutOfBoundsException("Unexpected test failed, internal error, sorry.");																			
//							}
//							if (addTo != fals) {
//								fr.lip6.move.gal.Or or = GalFactory.eINSTANCE.createOr();
//								or.setLeft(addTo);
//								or.setRight(rcomp);
//								addTo = or;
//							} else {
//								addTo = rcomp;
//							}
//						}
//					}
//				}
//			}
//		} // end of potential guard handling
//		return addTo;
//	}
//

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
						init = conv.convertToGAL(did.getInit().getExpr(),null);
					}
					vvalues.setValue(init);

					gal.getVariables().add(vvalues);

					VariableReference vr  =GF2.createVariableRef(vvalues);
					conv.addGlobal(did,vr);
				} else {
					ConstParameter cp = GalFactory.eINSTANCE.createConstParameter();
					cp.setName("$"+varname);
					int init = 0;
					if (did.getInit() != null) {
						fr.lip6.move.gal.IntExpression inite = conv.convertToGAL(did.getInit().getExpr(),null);
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
				SelfCall scall = GalFactory.eINSTANCE.createSelfCall();
				scall.setLabel(slab);
				rtr.getActions().add(scall);

				// call : chan ?
				Label rlab = GalFactory.eINSTANCE.createLabel();
				rlab.setName("Recv"+decl.getName());
				SelfCall rcall = GalFactory.eINSTANCE.createSelfCall();
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

	//@TODO handle invariant conjunctions
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
//			} else if (st.getInvariant() instanceof And) {
//				return Math.max(computeInvariant(((And)st.getInvariant()).getLeft(), ((And)st.getInvariant()).getRight()));
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

	private fr.lip6.move.gal.IntExpression galConstant(int i) {
		return GF2.constant(i);
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
			rargs.add(conv.convertToGAL(e,type));
		}
		inslist.add(new InstanceInfo(name, rargs, id));		
	}




}


