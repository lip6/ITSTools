package fr.lip6.move.gal.gal2smt.tosmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IDeclaration;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.ISort.IApplication;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.semantics.INext;

public class TransitionTranslator {
	private GalExpressionTranslator et;
	private IFactory ef;
	private Script script;
	private NextMatchTranslator nmt;
	private org.smtlib.ISort.IFactory sf;
	
	public TransitionTranslator(Configuration smtConfig, GalExpressionTranslator et, Script script) {
		this.et = et;
		this.ef = smtConfig.exprFactory;
		this.sf = smtConfig.sortFactory;
		this.script = script;
		nmt = new NextMatchTranslator(script, et, 0, smtConfig.sortFactory);
	}

	public void declareTransition (INext transition, int tindex) {
		ISymbol statement = transition.accept(nmt);
		
		// for each transition body, build a : trans(s,s') equivalent to n.match(s,s')
		// this is just aliasing the top level semantic result
		IExpr fired = ef.fcn(statement, nmt.curState(), nmt.nextState());
		// form is homogeneous to statement function, i.e. a pair src,dest are arguments, output is bool
		nmt.declareStatement(getTransition(tindex),fired);

		
		// Also define an enabling function
		// enabled (s) = exists s', fire(s,s')
		
		IApplication ints = sf.createSortExpression(et.getEfactory().symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sf.createSortExpression(et.getEfactory().symbol("Array"), ints, ints);
		
		// we build an argument for the function s 
		ISymbol state = ef.symbol("s");
		IDeclaration sdecl = ef.declaration(state, arraySort);

		// we build a state symbol s' = target
		ISymbol target = ef.symbol("target");
		IDeclaration tdecl = ef.declaration(target, arraySort);
		
		IExpr ex = ef.exists(Collections.singletonList(tdecl), ef.fcn(getTransition(tindex), state, target));
		
		// build a function enabled(State s):bool and declare it
		// declare the predicate : match (int [] src, int [] dst) : bool		
		List<IDeclaration> args = new ArrayList<>();
		args.add(sdecl);
		

		C_define_fun deftr = new org.smtlib.command.C_define_fun(
				getEnabler(tindex),    // name
				args, // param (int [] src, int [] dst) 
				Sort.Bool(), // return type
				ex); // actions : assertions over S[step] and S[step+1]
		script.commands().add(deftr);

	}
	
	public ISymbol getEnabler (int tindex) {
		return ef.symbol("Enabled_"+tindex);
	}
	
	public ISymbol getTransition (int tindex) {
		return ef.symbol("Transition_"+tindex);
	}
}
