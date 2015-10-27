package fr.lip6.move.gal.gal2smt.cover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.ICommand.IScript;
import org.smtlib.IExpr.IDeclaration;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.IFcnExpr;
import org.smtlib.IExpr.INumeral;
import org.smtlib.ISort;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_define_fun;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.gal2smt.smt.IVariableHandler;
import fr.lip6.move.gal.instantiate.Simplifier;

public class FlowMatrix {

	public static final String SUMT = "_SUM_PARIKH_";
	private Map<String, Map<Transition,Integer>> flow = new HashMap<String, Map<Transition,Integer>>();
	private Map<String,List<Transition>> callers=new HashMap<String, List<Transition>>();
	private Map<String,List<Transition>> labels=new HashMap<String, List<Transition>>();
	private final IFactory efactory;
	private final IVariableHandler vh;
	private final ISort ints;
	
	
	public FlowMatrix(Configuration conf, IVariableHandler vh) {
		this.efactory = conf.exprFactory;
		this.vh = vh;
		ints = conf.sortFactory.createSortExpression(efactory.symbol("Int"));
	}
	
	public boolean init (GALTypeDeclaration gal) {

		for (Transition tr : gal.getTransitions()) {
			// define line t of flow Matrix (constants ??)
			if (! Simplifier.isPetriStyle(tr)) {
				Logger.getLogger("fr.lip6.move.gal").warning("Could not use coverability on non petri style GAL.");
				return false;
			}
			String lab;
			if (tr.getLabel() == null) {
				lab = "";
			} else {
				lab = tr.getLabel().getName();
			}
			List<Transition> ltrs = labels.get(lab);
			if (ltrs == null) {
				ltrs = new ArrayList<Transition>();
				labels.put(lab, ltrs);
			}
			ltrs.add(tr);


			for (Statement st : tr.getActions()) {
				if (st instanceof Assignment) {
					Assignment ass = (Assignment) st;
					int val;
					if (ass.getType() == AssignType.ASSIGN) {
						BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
						Constant c = (Constant) bin.getRight();
						val = c.getValue();
						if (bin.getOp().equals("-")) {
							val = -val;
						}
					} else {
						Constant c = (Constant) ass.getRight();
						val = c.getValue();
						if (ass.getType() == AssignType.DECR) {
							val = -val;
						}
					}
					addEffect(tr, ass.getLeft(), val);
				} else if (st instanceof SelfCall) {
					SelfCall scall = (SelfCall) st;
					String labname = scall.getLabel().getName();
					List<Transition> calledLabs = callers.get(labname);
					if (calledLabs == null) {
						calledLabs = new ArrayList<Transition>();
						callers.put(labname, calledLabs);
					}
					calledLabs.add(tr);
				}					
			}
		}
		return true;

	}
	
	
	void addEffect(Transition tr, VariableReference vref, int val) {
		Map<Transition, Integer> line = getLine(vref);
		Integer cur = line.get(tr);
		if (cur==null) {
			cur=0;
		}
		cur+=val;
		line.put(tr, cur);
	}

	public Map<Transition, Integer> getLine(VariableReference var) {
		String vname = getSymbolName(var);
		Map<Transition, Integer> res = flow.get(vname);
		if (res==null) {
			res = new HashMap<Transition, Integer>();
			flow.put(vname, res);
		}
		return res ;
	}


	public String getSymbolName(VariableReference vref) {
		if (vref.getIndex()==null) {
			return vref.getRef().getName();
		} else {
			return getArrName((ArrayPrefix) vref.getRef(), ((Constant) vref.getIndex()).getValue());
		}
	}
	String getArrName(ArrayPrefix arr, int i) {
		return "|"+arr.getName()+"["+i+"]|";
	}


	public void addCallConstraints(Map<Transition, IExpr> trmap, IScript script) {
		// add callee/caller constraints on Xi
		// Ti calls (Tj,Tk) => Xj + Xk >= Xi
		// Tj called by (Ti,Tb) => Xj <= Xi+Xb
		// Label version :
		// for each label l except "", sum{t.x | self.l \in t.act} = sum{ t.x | t.lab = l }
		for (Entry<String, List<Transition>> e : callers.entrySet()) {
			String labname = e.getKey();
			// skip "private" label
			if ("".equals(labname)) continue;
			
			// an expression for sum of call point occurrences
			List<IExpr> tosum = new ArrayList<IExpr>();
			for (Transition tr : e.getValue()) {
				tosum.add(trmap.get(tr));
			}
			IFcnExpr sumcalls = efactory.fcn(efactory.symbol("+"), tosum);

			// an expression for sum of occurrences of labs with that label
			List<IExpr> tosum2 = new ArrayList<IExpr>();
			for (Transition tr : labels.get(labname)) {
				tosum2.add(trmap.get(tr));
			}
			IExpr sumlabs = efactory.fcn(efactory.symbol("+"), tosum2);
			if (tosum2.isEmpty()) {
				// should not happen due to GAL simplifications, we are calling non existent label here
				sumlabs = efactory.numeral(0);
			}

			// assert caller constraint on X
			script.commands().add(new C_assert(efactory.fcn(efactory.symbol("="), 
					sumcalls,
					sumlabs
					)));
		}		
	}


	public void addLineConstraints(IScript script, GALTypeDeclaration gal, int step, Map<Transition, IExpr> trmap) {
		for (Variable var : gal.getVariables()) {
			int init = ((Constant) var.getValue()).getValue();
			buildLineConstraint(script, GF2.createVariableRef(var), init, step, trmap);
		}
		for (ArrayPrefix arr : gal.getArrays()) {
			int sz = ((Constant) arr.getSize()).getValue();
			for (int i=0; i < sz; i++ ) {
				int init = ((Constant) arr.getValues().get(i)).getValue();
				buildLineConstraint(script, GF2.createArrayVarAccess(arr, GF2.constant(i)), init,step, trmap);
			}
		}		
	}

	public void buildLineConstraint(IScript script, VariableReference var, int init, int step, Map<Transition, IExpr> trmap) {
		// assert : x = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
		List<IExpr> exprs = new ArrayList<IExpr>();
		
		// m0.x
		exprs.add(efactory.numeral(init));
		
		
		//  Xi*C(ti,x)
		for (Entry<Transition, Integer> ent : getLine(var).entrySet()) {
			
			IExpr nbtok ;
			if (ent.getValue() >= 0) 
				nbtok = efactory.numeral(ent.getValue());
			else
				nbtok = efactory.fcn(efactory.symbol("-"), efactory.numeral(-ent.getValue()));
				
			exprs.add(efactory.fcn(efactory.symbol("*"), trmap.get(ent.getKey()), nbtok));
		}
		
		INumeral sstep = efactory.numeral(step);		
		script.commands().add(new C_assert(efactory.fcn(efactory.symbol("="),
				vh.translate(var, sstep),
				efactory.fcn(efactory.symbol("+"), exprs)
				)));
		script.commands().add(new C_assert(efactory.fcn(efactory.symbol(">="),
				vh.translate(var, sstep),
				efactory.numeral(0)
				)));

	}

	public Map<Transition, IExpr> addFlowConstraintsAtStep(int step, IScript script, GALTypeDeclaration gal) {
		Map<Transition, IExpr> trmap = declareTransitionVectorAtStep(step,
				script, gal);

		// build variables M 
		// assert M >=0 positive
		addLineConstraints(script, gal, step, trmap);
		addCallConstraints(trmap, script);	
		return trmap;
	}

	public Map<Transition, IExpr> declareTransitionVectorAtStep(int step,
			IScript script, GALTypeDeclaration gal) {
		Map<Transition,IExpr> trmap = new HashMap<Transition, IExpr>();
		List<IExpr> sum = new ArrayList<IExpr>();
		for (Transition tr : gal.getTransitions()) {				
			// build Xi : Parikh number of occurrences of t
			// assert Xi >= 0
			String tname = getTransParikhName(tr, step);
			IExpr tsymb = efactory.symbol(tname);
			trmap.put(tr,tsymb );
			sum.add(tsymb);
			vh.declarePositiveIntegerVariable(tname, script.commands(),true);
		}
		script.commands().add(new C_define_fun(
					efactory.symbol(SUMT+step), // name
					Collections.<IDeclaration> emptyList(),  // params
					ints, // return type
					efactory.fcn(efactory.symbol("+"), sum)
				));
		return trmap;
	}
	
	
	String getTransParikhName(Transition tr, int step) {
		return tr.getName()+".x"+step;
	}


}
