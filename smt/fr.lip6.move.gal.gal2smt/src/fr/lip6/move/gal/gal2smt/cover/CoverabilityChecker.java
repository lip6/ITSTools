package fr.lip6.move.gal.gal2smt.cover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.smtlib.ICommand.IScript;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.IFcnExpr;
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
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.gal2smt.ExpressionTranslator;
import fr.lip6.move.gal.gal2smt.ExpressionTranslator.VarDefStrategy;
import fr.lip6.move.gal.gal2smt.GalToSMT;
import fr.lip6.move.gal.gal2smt.SMTBuilder;
import fr.lip6.move.gal.instantiate.Simplifier;

public class CoverabilityChecker {
	private static final IFactory efact = GalToSMT.getSMT().smtConfig.exprFactory;

	FlowMatrix fm = new FlowMatrix();
	IScript flowEquation = new Script();
	
	public CoverabilityChecker (Specification spec) {
		TypeDeclaration td = spec.getMain();
		if (td instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) td;
			
			Map<String,List<Transition> > callers=new HashMap<String, List<Transition>>();
			Map<String,List<Transition>> labels=new HashMap<String, List<Transition>>();
					
			SMTBuilder builder = new SMTBuilder(spec);
			IScript script = new Script();
			builder.addHeader(script.commands());
			
			for (Transition tr : gal.getTransitions()) {
				// define line t of flow Matrix (constants ??)
				if (! Simplifier.isPetriStyle(tr)) {
					flowEquation = null;
					fm = null;
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
				builder.declarePositiveIntegerVariable(fm.getTransParikhName(tr), script.commands());
			}

			
			// build variables M 
			// assert M >=0 positive
			for (Variable var : gal.getVariables()) {
				String symbol = var.getName();
				int init = ((Constant) var.getValue()).getValue();
				buildLineConstraint(fm, builder, script, symbol, init);
			}
			for (ArrayPrefix arr : gal.getArrays()) {
				int sz = ((Constant) arr.getSize()).getValue();
				for (int i=0; i < sz; i++ ) {
					String symbol = fm.getArrName(arr,i);
					int init = ((Constant) arr.getValues().get(i)).getValue();
					buildLineConstraint(fm, builder, script, symbol, init);
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
					tosum.add(efact.symbol(fm.getTransParikhName(tr)));
				}
				IFcnExpr sumcalls = efact.fcn(efact.symbol("+"), tosum);

				// an expression for sum of occurrences of labs with that label
				List<IExpr> tosum2 = new ArrayList<IExpr>();
				for (Transition tr : labels.get(labname)) {
					tosum2.add(efact.symbol(fm.getTransParikhName(tr)));
				}
				IExpr sumlabs = efact.fcn(efact.symbol("+"), tosum2);
				if (tosum2.isEmpty()) {
					// should not happen due to GAL simplifications, we are calling non existent label here
					sumlabs = efact.numeral(0);
				}

				// assert caller constraint on X
				script.commands().add(new C_assert(efact.fcn(efact.symbol("="), 
						sumcalls,
						sumlabs
						)));
			}

			flowEquation = script;
			// check sat
			
			return ;
		}
		flowEquation = null;
		fm = null;
	}

	public IScript checkProperty (Property prop) {
		if (fm==null)
			return null;
		IScript toret = new Script();
		toret.commands().addAll(flowEquation.commands());

		
		VarDefStrategy oldstrat = ExpressionTranslator.getVarDefStrategy();
		ExpressionTranslator.setVarDefStrategy(new VarDefStrategy() {
			@Override
			public IExpr translate(VariableReference vref, IExpr index) {
				return efact.symbol(fm.getSymbolName(vref));
			}
		});


		LogicProp body = prop.getBody();
		IExpr totest =null ;
		if (body  instanceof ReachableProp || body instanceof NeverProp){
			// SAT = trace to state satisfying P for reach (verdict TRUE)
			// SAT = trace to c-e satisfying P for never (verdict FALSE)
			totest= ExpressionTranslator.translateBool(body.getPredicate(), null);
		} else if (body instanceof InvariantProp) {
			// SAT = trace to c-e satisfying !P for invariant (verdict FALSE)
			totest= efact.fcn(
					efact.symbol("not"),
					ExpressionTranslator.translateBool(body.getPredicate(), null));			
		} 

		toret.commands().add(new C_assert(totest));
		
		ExpressionTranslator.setVarDefStrategy(oldstrat);
		return toret;
	}
	
	public void buildLineConstraint(FlowMatrix fm, SMTBuilder builder,
			IScript script, String symbol, int init) {
		builder.declarePositiveIntegerVariable(symbol, script.commands());

		// assert : x = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
		List<IExpr> exprs = new ArrayList<IExpr>();
		
		// m0.x
		IFactory efact = GalToSMT.getSMT().smtConfig.exprFactory;
		exprs.add(efact .numeral(init));
		
		//  Xi*C(ti,x)
		for (Entry<Transition, Integer> ent : fm.getLine(symbol).entrySet()) {
			exprs.add(efact.fcn(efact.symbol("*"), efact.symbol(fm.getTransParikhName(ent.getKey())), efact.numeral(ent.getValue())));
		}
		
		script.commands().add(new C_assert(efact.fcn(efact.symbol("="),
				efact.symbol(symbol),
				efact.fcn(efact.symbol("+"), exprs)
				)));
	}

	
}
