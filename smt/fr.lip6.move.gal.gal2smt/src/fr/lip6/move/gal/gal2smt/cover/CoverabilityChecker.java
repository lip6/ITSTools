package fr.lip6.move.gal.gal2smt.cover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.smtlib.ICommand.IScript;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFcnExpr;
import org.smtlib.IResponse;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.bmc.SMTSolver;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.instantiate.Simplifier;

public class CoverabilityChecker extends SMTSolver {
	public CoverabilityChecker(Solver engine, Configuration smtConfig) {
		super(engine, smtConfig,new CoverabilityVariableHandler(smtConfig));
	}

	public boolean isInit=false;
	
	public void init (Specification spec) {
		super.init(spec);
		Script script = new Script();
		TypeDeclaration td = spec.getMain();
		if (td instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) td;
			FlowMatrix fm = new FlowMatrix();			
			isInit = true;
			
			Map<String,List<Transition> > callers=new HashMap<String, List<Transition>>();
			Map<String,List<Transition>> labels=new HashMap<String, List<Transition>>();
								
			for (Transition tr : gal.getTransitions()) {
				// define line t of flow Matrix (constants ??)
				if (! Simplifier.isPetriStyle(tr)) {
					isInit = false;
					Logger.getLogger("fr.lip6.move.gal").warning("Could not use coverability on non petri style GAL.");
					exit();
					return ;
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
						fm.addEffect(tr, ass.getLeft(), val);
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
				
				// build Xi : Parikh number of occurrences of t
				// assert Xi >= 0
				getVH().declarePositiveIntegerVariable(fm.getTransParikhName(tr), script.commands());
			}

			
			// build variables M 
			// assert M >=0 positive
			for (Variable var : gal.getVariables()) {
				String symbol = var.getName();
				int init = ((Constant) var.getValue()).getValue();
				buildLineConstraint(fm, script, symbol, init);
			}
			for (ArrayPrefix arr : gal.getArrays()) {
				int sz = ((Constant) arr.getSize()).getValue();
				for (int i=0; i < sz; i++ ) {
					String symbol = fm.getArrName(arr,i);
					int init = ((Constant) arr.getValues().get(i)).getValue();
					buildLineConstraint(fm, script, symbol, init);
				}
			}
			
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
					tosum.add(efactory.symbol(fm.getTransParikhName(tr)));
				}
				IFcnExpr sumcalls = efactory.fcn(efactory.symbol("+"), tosum);

				// an expression for sum of occurrences of labs with that label
				List<IExpr> tosum2 = new ArrayList<IExpr>();
				for (Transition tr : labels.get(labname)) {
					tosum2.add(efactory.symbol(fm.getTransParikhName(tr)));
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

			// check sat
			IResponse res = script.execute(solver);
			if (res.isError()) {
				throw new RuntimeException("Could not initialize marking equation.");
			}
		}
	}

	private CoverabilityVariableHandler getVH() {
		return (CoverabilityVariableHandler) vh;
	}

	
	@Override
	public Result verify(Property prop) {
		if (!isInit)
			return Result.UNKNOWN;
		
		LogicProp body = prop.getBody();
		IExpr totest =null ;
		if (body  instanceof ReachableProp || body instanceof NeverProp){
			// SAT = trace to state satisfying P for reach (verdict TRUE)
			// SAT = trace to c-e satisfying P for never (verdict FALSE)
			totest= et.translateBool(body.getPredicate(), null);
		} else if (body instanceof InvariantProp) {
			// SAT = trace to c-e satisfying !P for invariant (verdict FALSE)
			totest= efactory.fcn(
					efactory.symbol("not"),
					et.translateBool(body.getPredicate(), null));			
		} 
		return super.verifyAssertion(totest);
	}
	
	public void buildLineConstraint(FlowMatrix fm, IScript script, String var, int init) {
		// assert : x = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
		List<IExpr> exprs = new ArrayList<IExpr>();
		
		// m0.x
		exprs.add(efactory.numeral(init));
		
		//  Xi*C(ti,x)
		for (Entry<Transition, Integer> ent : fm.getLine(var).entrySet()) {
			exprs.add(efactory.fcn(efactory.symbol("*"), efactory.symbol(fm.getTransParikhName(ent.getKey())), efactory.numeral(ent.getValue())));
		}
		
		script.commands().add(new C_assert(efactory.fcn(efactory.symbol("="),
				efactory.symbol(var),
				efactory.fcn(efactory.symbol("+"), exprs)
				)));
	}


	
}
