package fr.lip6.ltl.tgba;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Simplifier;

public class TGBAProductBuilder {

	public static TGBA makeProduct (TGBA a, TGBA b) {
		int bsize = b.nbStates(); // to compute state indexes, any pair (astate,bstate) => single index of state in result.
		int nbAccA = a.getNbAcceptance(); // acceptance conditions of B are reindexed to start from nbAccA
		
		TGBA product = new TGBA(a.nbStates()*bsize, a.getApm()); // some of these states are probably unreachable		
		product.setAcceptSize(nbAccA+b.getNbAcceptance()); // union of acceptance of a and b + reindex acceptance in b
		
		assert (a.getApm() == b.getApm()); // we must be working with the same AP manager and thus agree on AP.
		
		// visited states
		boolean [] seen = new boolean[product.nbStates()];
		
		// todo list
		List<Integer> todo = new ArrayList<> (product.nbStates());
		
		int initial = getStateId(a.getInitial(), b.getInitial(), bsize);		
		todo.add(initial);
		product.setInitial(initial);
		seen[initial] = true;
		
		while (! todo.isEmpty()) {
			int current = todo.remove(todo.size()-1);
			int astate = getAindex(current, bsize);
			int bstate = getBindex(current, bsize);
			
			for (TGBAEdge ea : a.getEdges().get(astate)) {
				for (TGBAEdge eb : b.getEdges().get(bstate)) {
					int dest = getStateId(ea.getDest(), eb.getDest(), bsize);
					
					Expression condres = Expression.op(Op.AND, ea.getCondition(), eb.getCondition());
					condres = Simplifier.simplifyBoolean(condres);
					
					if (condres.getOp()== Op.BOOLCONST && condres.getValue() == 0) {
						// FALSE
						continue;
					} else {
						SparseBoolArray acc = new SparseBoolArray();
						for (int i=0, ie=ea.getAcceptance().size() ; i < ie ; i++) {
							acc.append(ea.getAcceptance().keyAt(i), true);
						}
						for (int i=0, ie=eb.getAcceptance().size() ; i < ie ; i++) {
							acc.append(eb.getAcceptance().keyAt(i) + nbAccA , true);
						}
						
						TGBAEdge eres = new TGBAEdge(current, dest, condres, acc);
						product.getEdges().get(current).add(eres);
						if (! seen[dest]) {
							todo.add(dest);
							seen[dest] = true;
						}
					}					
				}				
			}
		}
		
		return product;
	}
	
	
	public static TGBA makeQuotient (TGBA a, TGBA knowledge) {
		int bsize = knowledge.nbStates(); // to compute state indexes, any pair (astate,bstate) => single index of state in result.
		int nbAccA = a.getNbAcceptance(); // acceptance conditions of B are reindexed to start from nbAccA
		
		TGBA product = new TGBA(a.nbStates()*bsize, a.getApm()); // some of these states are probably unreachable		
		product.setAcceptSize(nbAccA+knowledge.getNbAcceptance()); // union of acceptance of a and b + reindex acceptance in b
		
		assert (a.getApm() == knowledge.getApm()); // we must be working with the same AP manager and thus agree on AP.
		
		int edgeCount = a.getEdges().stream().mapToInt(List::size).sum();
		
		// Expressions that are in product with each edge of A.
		List<Expression> prodWith = new ArrayList<>(edgeCount);
		for (int i=0 ; i < edgeCount ; i++) {
			prodWith.add(Expression.constant(false));
		}
		// visited states
		boolean [] seen = new boolean[product.nbStates()];
		
		// todo list
		List<Integer> todo = new ArrayList<> (product.nbStates());
		
		int initial = getStateId(a.getInitial(), knowledge.getInitial(), bsize);		
		todo.add(initial);
		product.setInitial(initial);
		seen[initial] = true;		
		
		while (! todo.isEmpty()) {
			int current = todo.remove(todo.size()-1);
			int astate = getAindex(current, bsize);
			int bstate = getBindex(current, bsize);
								
			
			int indexEdgeA = 0;
			for (int i=0;i < astate ; i++) {
				indexEdgeA += a.getEdges().get(i).size();
			}
			
			for (TGBAEdge ea : a.getEdges().get(astate)) {
				for (TGBAEdge eb : knowledge.getEdges().get(bstate)) {
					int dest = getStateId(ea.getDest(), eb.getDest(), bsize);
					
					Expression condres = Expression.op(Op.AND, ea.getCondition(), eb.getCondition());
					condres = Simplifier.simplifyBoolean(condres);
					
					if (condres.getOp()== Op.BOOLCONST && condres.getValue() == 0) {
						// FALSE
						continue;
					} else {
						// store the expressions we have made a product with
						prodWith.set(indexEdgeA, Expression.op(Op.OR,prodWith.get(indexEdgeA),eb.getCondition()));												
						
						SparseBoolArray acc = new SparseBoolArray();
						for (int i=0, ie=ea.getAcceptance().size() ; i < ie ; i++) {
							acc.append(ea.getAcceptance().keyAt(i), true);
						}
						for (int i=0, ie=eb.getAcceptance().size() ; i < ie ; i++) {
							acc.append(eb.getAcceptance().keyAt(i) + nbAccA , true);
						}
						
						TGBAEdge eres = new TGBAEdge(current, dest, condres, acc);
						product.getEdges().get(current).add(eres);
						if (! seen[dest]) {
							todo.add(dest);
							seen[dest] = true;	
						}
					}					
				}
				indexEdgeA++;
			}
		}
		
		
		// now create the resulting quotient
		TGBA quotient = new TGBA(a.nbStates(), a.getApm());
		quotient.setInitial(a.getInitial());
		quotient.setAcceptSize(nbAccA);
		
		int edgeid = 0;
		for (List<TGBAEdge> edges : a.getEdges()) {
			for (TGBAEdge ea : edges) {
				Expression with = prodWith.get(edgeid);
				if (!(with.getOp() == Op.BOOLCONST && with.getValue()==0)) {
					// only for non empty edges
					Expression expr = Expression.op(Op.AND, ea.getCondition(), with);
					
					// quantify existentially variables in Knowledge but not in A
					Set<AtomicProp> aps = new HashSet<>(knowledge.getAPs());
					aps.removeAll(a.getAPs());
					
					for (AtomicProp ap : aps) {
						expr = Simplifier.existentialQuantification(ap, expr);
					}
					
					quotient.addEdge(ea.getSrc(), ea.getDest(), expr, ea.getAcceptance());										
				}								
				edgeid++;
			}
		}		
		return quotient;
	}
	
	
	
	
	private static int getStateId(int astate, int bstate, int bsize) {
		return astate*bsize + bstate;
	}
	
	private static int getAindex (int state, int bsize) {
		return state / bsize;		
	}
	private static int getBindex (int state, int bsize) {
		return state % bsize;		
	}
	
}
