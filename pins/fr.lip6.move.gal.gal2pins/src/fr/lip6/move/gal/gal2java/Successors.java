package fr.lip6.move.gal.gal2java;

import java.util.stream.Stream;

import fr.lip6.move.gal.semantics.Assign;
import fr.lip6.move.gal.semantics.NextStreamVisitor;
import fr.lip6.move.gal.semantics.Predicate;

public class Successors extends NextStreamVisitor<IState> {

	
	public Successors(Stream<IState> current) {
		super(current);
	}

	@Override
	public Stream<IState> visit(Assign ass) {
		return current.map( s -> update(ass,s) );
	}
	
	private IState update (Assign ass, IState s) {
		Evaluator ev = new Evaluator(ass.getIndexer());
		ev.setState(s);
		int val = ev.doSwitch(ass.getAssignment().getRight());
		int var = ev.getIndex(ass.getAssignment().getLeft());
		IState copy = s.copy();
		switch (ass.getAssignment().getType()) {
		case ASSIGN :		
			copy.set(var,val);
			break;
		case DECR :
			copy.set(var, copy.get(var)- val);
			break;
		case INCR :
			copy.set(var, copy.get(var) + val);
			break;			
		}
		return copy;
	}
	

	@Override
	public Stream<IState> visit(Predicate pred) {
		return current.filter( s -> predicate(pred,s) );
	}
		
	private boolean predicate (Predicate pred, IState s) {
		Evaluator ev = new Evaluator(pred.getIndexer());
		ev.setState(s);
		int val = ev.doSwitch(pred.getGuard());
		return val == 1;
	}

}
