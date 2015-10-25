package fr.lip6.move.gal.gal2smt.cover;

import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.IResponse;
import org.smtlib.SMT.Configuration;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.gal2smt.smt.SMTSolver;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class CoverabilityChecker extends SMTSolver {
	public CoverabilityChecker(Solver engine, Configuration smtConfig) {
		super(engine, smtConfig,new CoverabilityVariableHandler(smtConfig));
		setShowSatState(true);
	}

	public boolean isInit=false;
	
	public void init (Specification spec) {
		super.init(spec);
		Script script = new Script();
		TypeDeclaration td = spec.getMain();
		if (td instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) td;
			FlowMatrix fm = new FlowMatrix(conf,vh);			
			isInit = fm.init(gal);
			
			if (! isInit) {
				exit();
			}
			int step = 0;

			fm.addFlowConstraintsAtStep(step,script,gal);

			// check sat
			IResponse res = script.execute(solver);
			if (res.isError()) {
				throw new RuntimeException("Could not initialize marking equation.");
			}
		}
	}

	@Override
	public Result verify(Property prop) {
		if (!isInit)
			return Result.UNKNOWN;
		
		Logger.getLogger("fr.lip6.move.gal").info("Verifying coverability of "+prop.getName());
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
}
