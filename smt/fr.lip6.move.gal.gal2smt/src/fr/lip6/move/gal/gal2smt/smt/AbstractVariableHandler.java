package fr.lip6.move.gal.gal2smt.smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.ISort.IApplication;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

public abstract class AbstractVariableHandler implements IVariableHandler {
	protected final IFactory efactory;
	protected final org.smtlib.ISort.IFactory sortfactory;
	// integer sort
	protected final IApplication ints;
	// an array, indexed by integers, containing integers : (Array Int Int) 
	protected final IApplication arraySort;

	protected final List<IExpr> allAccess = new ArrayList<IExpr>();

	public AbstractVariableHandler(Configuration conf) {
		efactory = conf.exprFactory;
		sortfactory = conf.sortFactory;
		// integer sort
		ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);
	}
	
	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#addInitialConstraint(org.smtlib.impl.Script, fr.lip6.move.gal.GALTypeDeclaration)
	 */
	@Override
	public void addInitialConstraint(Script script, GALTypeDeclaration gal) {
		/* VARIABLES */				
		for (Variable var : gal.getVariables()) {
			script.commands().add(
					new C_assert(
							efactory.fcn(efactory.symbol("="), 
									accessVar(var, efactory.numeral(0)),
									efactory.numeral(((Constant)var.getValue()).getValue()))
							)
					);
			
		}
		/* ARRAYS */
		for (ArrayPrefix array : gal.getArrays()) {
			for (int index =0 ; index < ((Constant) array.getSize()).getValue() ; index++) {
				script.commands().add(
						new C_assert(
								efactory.fcn(efactory.symbol("="), 
										accessArray(array, index ,efactory.numeral(0)),
										efactory.numeral( ((Constant)array.getValues().get(index)).getValue()))
								)
						);
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#declareVariables(org.smtlib.impl.Script, fr.lip6.move.gal.GALTypeDeclaration)
	 */
	@Override
	public void declareVariables(Script script, GALTypeDeclaration gal) {

		// Declare variables
		/* VARIABLES */				
		for (Variable var : gal.getVariables()) {
			// a new variable with this type
			declareVariable(var, script.commands());
			allAccess.add(accessVar(var, efactory.numeral(0)));
		}
		/* ARRAYS */
		for (ArrayPrefix array : gal.getArrays()) {
			// a new variable with this type
			declareArray(array, script.commands());
			for (int i = 0; i < ((Constant) array.getSize()).getValue(); i++) {
				allAccess.add(accessArray(array, i, efactory.numeral(0)));
			}
		}
	}

	public IExpr translate(VariableReference vref, IExpr step) {
		if(vref.getRef() != null){				
			if(vref.getIndex() != null){
				/* Array */
				return accessArray((ArrayPrefix) vref.getRef(),((Constant) vref.getIndex()).getValue() , step);
			}else{
				return accessVar((Variable) vref.getRef(), step);
			}
		}			
		throw new RuntimeException("Variable Reference with no target, malformed model !");
	}

	@Override
	public List<IExpr> getAllAccess() {
		return allAccess;
	}

}
