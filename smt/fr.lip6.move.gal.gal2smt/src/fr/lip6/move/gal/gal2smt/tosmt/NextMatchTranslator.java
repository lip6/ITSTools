package fr.lip6.gal.gal2smt.tosmt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.smtlib.IExpr;
import org.smtlib.ISort;
import org.smtlib.IExpr.IDeclaration;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.ISort.IApplication;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.semantics.Alternative;
import fr.lip6.move.gal.semantics.Assign;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.NextVisitor;
import fr.lip6.move.gal.semantics.Predicate;
import fr.lip6.move.gal.semantics.Sequence;
import fr.lip6.move.gal.semantics.VariableIndexer;

public class NextMatchTranslator implements NextVisitor<ISymbol> {

	private Script script;
	private GalExpressionTranslator et;
	private int statementIndex;
	private ISort.IFactory sortfactory;
	
	public NextMatchTranslator(Script script, GalExpressionTranslator et, int statementIndex,
			org.smtlib.ISort.IFactory sortfactory) {
		super();
		this.script = script;
		this.et = et;
		this.statementIndex = statementIndex;
		this.sortfactory = sortfactory;
	}

	@Override
	public ISymbol visit(Assign ass) {
		// Assign( v[e], e')
		// match(s,s') : s' = store( s, v+e(s), e'(s))
		
		// use current statement indexer to resolve variables
		VariableIndexer index = ass.getIndexer();
		et.setIndex(index);
		
		IFactory efactory = et.getEfactory();

		
		// find the index of lhs
		VariableReference vref = ass.getAssignment().getLeft();
		IExpr indexlhs = efactory.numeral(index.getIndex(vref.getRef().getName())); 
		if (vref.getIndex() != null) {
			IExpr offset = et.translate(vref.getIndex(), curState());
			
			indexlhs = efactory.fcn(efactory.symbol("+"), indexlhs, offset);
		}
		
		// find the value of rhs
		IExpr rhs = et.translate(ass.getAssignment().getRight(), curState());
		
		switch (ass.getAssignment().getType()) {
		case INCR :
			rhs = efactory.fcn( efactory.symbol("+"), et.translate(ass.getAssignment().getLeft(), curState()), rhs);
			break;
		case DECR :
			rhs = efactory.fcn( efactory.symbol("-"), et.translate(ass.getAssignment().getLeft(), curState()), rhs);
			break;
		default :
			break;
		}

		// update "state" attribute for next statements
		// state = store(state,  getIndex(lhs), rhs)
		final ISymbol store = efactory.symbol("store");
		IExpr updstate = efactory.fcn(store, curState(), indexlhs, rhs);
		
		// match(s,s') : s' = store( s, v+e(s), e'(s))
		IExpr match = efactory.fcn(efactory.symbol("="), nextState(), updstate);
		
		return declareStatement(nextStatementName(), match);		
	}

	ISymbol nextStatementName() {
		return et.getEfactory().symbol("match"+(statementIndex++));
	}
	
	ISymbol declareStatement(ISymbol name, IExpr match) {		
		
		IApplication ints = sortfactory.createSortExpression(et.getEfactory().symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(et.getEfactory().symbol("Array"), ints, ints);
		
		// declare the predicate : match (int [] src, int [] dst) : bool		
		List<IDeclaration> args = new ArrayList<>();
		args.add(et.getEfactory().declaration(curState(), arraySort));
		args.add(et.getEfactory().declaration(nextState(), arraySort));

		C_define_fun defsrctr = new org.smtlib.command.C_define_fun(
				name,    // name
				args, // param (int [] src, int [] dst) 
				Sort.Bool(), // return type
				match); // actions : assertions over S[step] and S[step+1]
		script.commands().add(defsrctr);
		
		return name;
	}



	ISymbol curState() {		
		return et.getEfactory().symbol("cur");
	}

	ISymbol nextState() {		
		return et.getEfactory().symbol("next");
	}


	@Override
	public ISymbol visit(Predicate pred) {
		// Filter( p )
		// match(s,s') : s' = s AND p(s)
		
		// use current statement indexer to resolve variables
		VariableIndexer index = pred.getIndexer();
		et.setIndex(index);
		
		// create a new condition over current state 
		IExpr guard = et.translateBool(pred.getGuard(), curState());
		// create s=s' constraint
		IExpr id = et.getEfactory().fcn(et.getEfactory().symbol("="), nextState(), curState());
		
		IExpr match = et.getEfactory().fcn(et.getEfactory().symbol("and"), guard, id);
		return declareStatement(nextStatementName(), match);	
	}

	
	@Override
	public ISymbol visit(Alternative alt) {
		// Alt( f, f' )
		// match(s,s') : f.match(s,s') OR f'.match(s,s')
		
		List<ISymbol> alts = new ArrayList<>();
		for (INext n : alt.getAlternatives()) {
			alts.add(n.accept(this));
		}
		
		// transform each function name to a function call : m123  ->  (m123 src dest)
		List<IExpr> calls = alts.stream().map( match -> 
			et.getEfactory().fcn(match, curState(), nextState())
				).collect(Collectors.toList());
		
		IExpr alternative = et.getEfactory().fcn(et.getEfactory().symbol("or"), calls);
		
		return declareStatement(nextStatementName(), alternative);
	}
	
	@Override
	public ISymbol visit(Sequence seq) {
		// Seq (f, f')
		// match(s,s'') = exists s', f.match(s,s') AND f'.match(s',s'')

		IApplication ints = sortfactory.createSortExpression(et.getEfactory().symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(et.getEfactory().symbol("Array"), ints, ints);
		
		// we build a list of n-1 states :  mid1..n-1
		List<ISymbol> mids = new ArrayList<>();
		
		// declare the mid : parameter of Exists
		// there are (n-1) intermediate steps
		for (int i=1 ; i < seq.getActions().size() ; i++) {
			mids.add(et.efactory.symbol("mid"+i));	
		}

		

		// create alist of n+1 states : src, mid1..midn-1, next
		List<IExpr> states = new ArrayList<>();
		states.add(curState());
		states.addAll(mids);
		states.add(nextState());
		

		// full list of conditions to check
		List<IExpr> conds = new ArrayList<>();
		
		int index = 0;
		for (INext n : seq.getActions()) {
			// collect the statement functions
			ISymbol nexti = n.accept(this);
			
			// Enforce f[i].match(s[i],s[i+1])
			conds.add(et.getEfactory().fcn(nexti, states.get(index), states.get(index+1)));
			
			index++;
		}
		
		// Build an AND of them 
		IExpr succChain = et.getEfactory().fcn(et.getEfactory().symbol("and"), conds);
		
		
		// Convert the list of intermediate states to declarations for exists
		// transform each state name to a declaration : mid123  ->  mid123: Array<Int,Int>
		List<IDeclaration> decls = mids.stream().map( 
				mid -> 
				et.getEfactory().declaration(mid, arraySort)
				).collect(Collectors.toList());

		// Enforce f.match(src,mid)
		IExpr total = et.getEfactory().exists(decls, succChain);

		return declareStatement(nextStatementName(), total);
	}
}
