package fr.lip6.move.gal.structural;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.structural.expr.ArrayVarRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Param;
import fr.lip6.move.gal.structural.expr.ParamRef;
import fr.lip6.move.gal.structural.expr.Simplifier;
import fr.lip6.move.gal.util.Pair;

public class SparseHLPetriNet extends PetriNet {
	
	private List<HLPlace> places = new ArrayList<>();
	private List<HLTrans> transitions= new ArrayList<>();
	int placeCount=0;
	
	public int addTransition (String tname, List<Param> params, Expression guard) {
		transitions.add(new HLTrans(tname,params,guard));
		return transitions.size()-1;
	}
	
	public int addPlace (String pname, int [] init, String sname) {
		int index = placeCount;
		places.add(new HLPlace(pname, index, init, sname));
		placeCount += init.length;
		return places.size()-1;
	}

	public void addPreArc (int p, int t, Expression func, int val) {
		transitions.get(t).pre.add(new Pair<Expression, Integer>(Expression.array(p, func), val));
	}

	public void addPostArc (int p, int t, Expression func, int val) {
		transitions.get(t).post.add(new Pair<Expression, Integer>(Expression.array(p, func), val));
	}

	public int getPlaceCount() {
		return placeCount;
	}
	
	public List<HLPlace> getPlaces() {
		return places;
	}
	public List<HLTrans> getTransitions() {
		return transitions;
	}

	@Override
	public int getTransitionCount() {
		return transitions.size();
	}

	@Override
	public int getTransitionIndex(String name) {
		for (int i = 0, ie = transitions.size(); i < ie; i++) {
			if (transitions.get(i).name.equals(name)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getPlaceIndex(String name) {
		for (int i = 0, ie = places.size(); i < ie; i++) {
			if (places.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "SparseHLPetriNet [places=" + places + ",\n transitions=" + transitions + ",\n getName()=" + getName()
				+ ",\n getProperties()=" + getProperties() + "]";
	}
	
	
	public SparsePetriNet skeleton () {
		long time = System.currentTimeMillis();
		SparsePetriNet spn = new SparsePetriNet();
		spn.setName(getName() +"_skel");
		
		// generate places with appropriate indexes
		for (HLPlace p : places) {
			spn.addPlace(p.getName(), Arrays.stream(p.getInitial()).sum());
		}
		for (HLTrans t : transitions) {
			spn.addTransition(t.name);

			SparseIntArray pt = computeCardinality(t.pre);
			for (int i=0,ie=pt.size();i<ie;i++) {
				spn.addPreArc(pt.keyAt(i), spn.getTransitionCount()-1, pt.valueAt(i));
			}
			SparseIntArray tp = computeCardinality(t.post);
			for (int i=0,ie=tp.size();i<ie;i++) {
				spn.addPostArc(tp.keyAt(i), spn.getTransitionCount()-1, tp.valueAt(i));
			}
		}


		Logger.getLogger("fr.lip6.move.gal").info("Built PT skeleton of HLPN with "+spn.getPlaceCount()+ " places and " + spn.getTransitionCount() + " transitions in " + (System.currentTimeMillis()- time) + " ms.");
		time = System.currentTimeMillis();
		// now resolve enabled + cardinality predicates
		for (Property p : getProperties()) {
			spn.getProperties().add(new Property(bindSkeletonColors(p.getBody()),p.getType(),p.getName()));
		}
		Logger.getLogger("fr.lip6.move.gal").info("Skeletonized HLPN properties in " + (System.currentTimeMillis()- time) + " ms.");
		 FlowPrinter.drawNet(new StructuralReduction(spn), "Skeleton net", new HashSet<>(), new HashSet<>());
		return spn;
	}



	private SparseIntArray computeCardinality(List<Pair<Expression, Integer>> arcs) {
		SparseIntArray pt = new SparseIntArray();
		for (Pair<Expression, Integer> arc : arcs) {
			Expression place = arc.getFirst();
			if (place.getOp() == Op.HLPLACEREF) {
				ArrayVarRef aref = (ArrayVarRef) place;
				int pind = aref.base;
				int val = pt.get(pind) + arc.getSecond(); 
				pt.put(pind, val);
			}
		}
		return pt;
	}

	public SparsePetriNet unfold () {
		List<List<Integer>> enablings = new ArrayList<>(transitions.size());
		return unfold(enablings);
	}
	
	public SparsePetriNet unfold (List<List<Integer>> enablings) {
		long time = System.currentTimeMillis();
		SparsePetriNet spn = new SparsePetriNet();
		spn.setName(getName() +"_unf");
		
		// generate places with appropriate indexes
		{
			StringBuilder sb = new StringBuilder();
			for (HLPlace p : places) {
				String hlname = p.getName();
				sb.append(hlname);
				sb.append('_');
				for (int i=0,ie=p.getInitial().length ; i < ie ; i++) {
					sb.setLength(hlname.length()+1);
					sb.append(i);
					spn.addPlace(sb.toString(), p.getInitial()[i]);
				}
				sb.setLength(0);
			}
		}
		// generate transitions + a predicate for enabling of a colored transition
		
		for (HLTrans t : transitions) {
			Queue<HLTrans> todo  = new ArrayDeque<>();
			fuseEqualParameters(t);
			t.params.sort((a,b) -> Integer.compare(a.size(),b.size()));
			todo.add(t);
			List<Integer> done = new ArrayList<>();
			while (! todo.isEmpty()) {
				HLTrans bt = todo.poll();
				if (bt.params.isEmpty()) {
					int tind = transformHLtoPT(bt,spn);
					if (tind >= 0) {
						done.add(tind);
					}
				} else {
					List<Param> pcopy = new ArrayList<>(bt.params.subList(1, bt.params.size()));
					Param cur = bt.params.get(0);
					for (int val=0; val < cur.size() ; val++) {
						Expression guard = bind(cur,val,bt.guard);
						if (guard.getOp() == Op.BOOLCONST && guard.getValue() == 0) {
							// false guard, abort
							continue;
						}
						HLTrans tcopy = new HLTrans(bt.name +cur.getName()+val, pcopy, guard);
						
						boolean skip = false;
						for (Pair<Expression, Integer> arc : bt.pre) {
							Expression r = bind(cur,val,arc.getFirst());
							Integer value = arc.getSecond();
							if (r.getOp()==Op.HLPLACEREF) {
								ArrayVarRef aref = (ArrayVarRef)r;
								if (places.get(aref.base).isConstant() && aref.index.getOp() == Op.CONST) 
								{
									// fully simplified
									if (places.get(aref.base).getInitial()[aref.index.getValue()] < value) {
										// no can do ! place is constantly insufficiently marked.
										skip = true;
										break;
									} else {
										// skip arc
										continue;
									}
								}
							}							
							tcopy.pre.add(new Pair<> (r,value));
						}
						if (skip) {
							continue;
						}						
						for (Pair<Expression, Integer> arc : bt.post) {
							Expression r = bind(cur,val,arc.getFirst());
							Integer value = arc.getSecond();
							if (r.getOp()==Op.HLPLACEREF) {
								ArrayVarRef aref = (ArrayVarRef)r;
								if (places.get(aref.base).isConstant() && aref.index.getOp() == Op.CONST) 
								{
									continue;
								}
							}
							tcopy.post.add(new Pair<> (r,value));							
						}
						
						todo.add(tcopy);
					}
					
				}
			}
			enablings.add(done);			
		}
		Logger.getLogger("fr.lip6.move.gal").info("Unfolded HLPN to a Petri net with "+spn.getPlaceCount()+ " places and " + spn.getTransitionCount() + " transitions " + (spn.getFlowPT().getColumns().stream().mapToInt(c->c.size()).sum() + spn.getFlowTP().getColumns().stream().mapToInt(c->c.size()).sum()) + " arcs in " + (System.currentTimeMillis()- time) + " ms.");
		time = System.currentTimeMillis();
		// now resolve enabled + cardinality predicates
		for (Property p : getProperties()) {
			spn.getProperties().add(new Property(bindColors(p.getBody(),enablings),p.getType(),p.getName()));
		}
		Logger.getLogger("fr.lip6.move.gal").info("Unfolded HLPN properties in " + (System.currentTimeMillis()- time) + " ms.");
		return spn;
	}

	private void fuseEqualParameters(HLTrans t) {
		if (t.params.size() < 2) {
			return ;
		} else {
			if (t.guard.getOp() != Op.BOOLCONST) {
				while (handleEqual(t.guard,t)) {
					continue;
				}
			}
		}
	}

	public void simplifyLogic() {
		for (Property prop : getProperties()) {
			prop.setBody(Simplifier.pushNegation(prop.getBody()));
			prop.setBody(Simplifier.assumeVarsPositive(prop.getBody()));
			prop.setBody(Simplifier.simplifyBoolean(prop.getBody()));
		}
	}
	
	private boolean handleEqual(Expression guard, HLTrans t) {
		if (guard.getOp() == Op.AND) {
			guard.forEachChild(c -> handleEqual(c, t));
		} else if (guard.getOp() == Op.EQ) {
			if (guard instanceof BinOp) {
				BinOp bin = (BinOp) guard;
				if (bin.left.getOp() == Op.PARAMREF && bin.right.getOp() == Op.PARAMREF) {
					if (! bin.left.equals(bin.right)) {
						remapParameter(t,((ParamRef)bin.left).parameter,((ParamRef)bin.right).parameter);
					}					
				}
			}
		}
		return false;
	}

	private void remapParameter(HLTrans t, Param tokeep, Param todel) {
		for (Pair<Expression, Integer> arc : t.pre) {
			remapParameter(arc.getFirst(), tokeep, todel);
		}
		for (Pair<Expression, Integer> arc : t.post) {
			remapParameter(arc.getFirst(), tokeep, todel);
		}
		remapParameter(t.guard, tokeep, todel);
		t.params.remove(todel);		
	}

	private Void remapParameter(Expression expr, Param tokeep, Param todel) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			bin.forEachChild(c -> remapParameter(c, tokeep, todel));			
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			nop.forEachChild(c -> remapParameter(c, tokeep, todel));
		} else if (expr instanceof ParamRef) {
			ParamRef pref = (ParamRef) expr;
			if (pref.parameter == todel) { 
				pref.parameter = tokeep;
			}
		} else if (expr instanceof ArrayVarRef) {
			ArrayVarRef aref = (ArrayVarRef) expr;
			remapParameter(aref.index, tokeep, todel);
		}
		return null;
	}

	private Expression bindSkeletonColors(Expression expr) {
		if (expr == null) {
			return expr;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			Expression l = bindSkeletonColors(bin.left);
			Expression r = bindSkeletonColors(bin.right);
			if (l == bin.left && r == bin.right) {
				return expr;
			}
			return Expression.op(bin.op, l, r);
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>();
			if (expr.getOp() == Op.CARD || expr.getOp() == Op.BOUND) {
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.PLACEREF) {
						if (places.get(child.getValue()).isConstant()) {
							int sum = 0;
							for (int v : places.get(child.getValue()).getInitial()) {
								sum +=v;
							}
							resc.add(Expression.constant(sum));
						} else {
							resc.add(Expression.var(child.getValue()));
						}
					}					
				}
				return Expression.nop(nop.getOp(), resc);
			} else if (expr.getOp() == Op.ENABLED) {
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.TRANSREF) {
						resc.add(Expression.trans(child.getValue()));
					}					
				}
				return Expression.nop(nop.getOp(), resc);				
			} else {
				boolean changed = false;
				for (Expression child : nop.getChildren()) {
					Expression nc = bindSkeletonColors(child);
					resc.add(nc);
					if (nc != child) {
						changed = true;
					}
				}
				if (!changed) {
					return expr;
				}
				return Expression.nop(nop.getOp(), resc);
			}
		} else if (expr instanceof ArrayVarRef) {
			ArrayVarRef aref = (ArrayVarRef) expr;
			return Expression.var( aref.base );
		}
		return expr;
	}
	
	private Expression bindColors(Expression expr, List<List<Integer>> en) {				
		if (expr == null) {
			return expr;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			Expression l = bindColors(bin.left,en);
			Expression r = bindColors(bin.right,en);
			if (l == bin.left && r == bin.right) {
				return expr;
			}
			return Expression.op(bin.op, l, r);
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>();
			if (expr.getOp() == Op.CARD || expr.getOp() == Op.BOUND) {
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.PLACEREF) {
						if (places.get(child.getValue()).isConstant()) {
							int sum = 0;
							for (int v : places.get(child.getValue()).getInitial()) {
								sum +=v;
							}
							resc.add(Expression.constant(sum));
						} else {
							int si = places.get(child.getValue()).startIndex;
							for (int i=0,ie=places.get(child.getValue()).getInitial().length ; i<ie;i++) {
								resc.add(Expression.var(si + i));
							}
						}
					}					
				}
				return Expression.nop(nop.getOp(), resc);
			} else if (expr.getOp() == Op.ENABLED) {
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.TRANSREF) {
						for (Integer timg : en.get(child.getValue())) {
							resc.add(Expression.trans(timg));
						}
					}					
				}
				return Expression.nop(nop.getOp(), resc);				
			} else {
				boolean changed = false;
				for (Expression child : nop.getChildren()) {
					Expression nc = bindColors(child,en);
					resc.add(nc);
					if (nc != child) {
						changed = true;
					}
				}
				if (!changed) {
					return expr;
				}
				return Expression.nop(nop.getOp(), resc);
			}
		} else if (expr instanceof ArrayVarRef) {
			ArrayVarRef aref = (ArrayVarRef) expr;
			return Expression.var( places.get(aref.base).startIndex + aref.index.eval(null));
		}
		return expr;
	}

	private Expression bind(Param cur, int val, Expression expr) {
		if (expr == null) {
			return expr;
		} else if (expr.getOp() == Op.PARAMREF) {
			ParamRef pref = (ParamRef) expr;
			if (pref.parameter.equals(cur)) {
				return Expression.constant(val);
			}			
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			Expression l = bind(cur, val, bin.left);
			Expression r = bind(cur, val, bin.right);
			if (l.getOp()==Op.BOOLCONST && (r==null || r.getOp()==Op.BOOLCONST)) {
				boolean v = new BinOp(expr.getOp(),l,r).eval(null)==1;
				return Expression.constant(v);
			}
			if (l == bin.left && r == bin.right) {
				return expr;
			}
			return Expression.op(bin.op, l, r);
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>();
			boolean changed = false;
			boolean allConst = true;
			for (Expression child : nop.getChildren()) {
				Expression nc = bind(cur,val,child);
				resc.add(nc);
				if (nc != child) {
					changed = true;
					// lazy boolean cases
					if (nc.getOp() == Op.BOOLCONST
							&& ((nc.getValue() == 0 && expr.getOp() == Op.AND) || (nc.getValue() == 1 && expr.getOp() == Op.OR)))
						return nc;										
				}
				if (allConst && !(nc.getOp()==Op.BOOLCONST || nc.getOp()==Op.CONST)) {
					allConst = false;
				}
			}
			if (!changed) {
				return expr;
			}
			if (allConst) {
				int v = Expression.nop(nop.getOp(), resc).eval(null);
				if (nop.getOp() == Op.AND || nop.getOp() == Op.OR) {
					return Expression.constant(v==1);
				} else {
					return Expression.constant(v);
				}
			} else {
				return Expression.nop(nop.getOp(), resc);
			}						
		} else if (expr instanceof ArrayVarRef) {
			ArrayVarRef aref = (ArrayVarRef) expr;
			Expression index = bind(cur,val,aref.index);
			if (index == aref.index) {
				return aref;
			} else {
				return new ArrayVarRef(aref.base, index);
			}			
		}
		return expr;
	}

	private int transformHLtoPT(HLTrans bt, SparsePetriNet spn) {
		// finished binding, go to PT					
		boolean hasNeg = false;
		SparseIntArray pt = new SparseIntArray();
		for (Pair<Expression, Integer> arc : bt.pre) {
			Expression place = arc.getFirst();
			if (place.getOp() == Op.HLPLACEREF) {
				ArrayVarRef aref = (ArrayVarRef) place;
				int pind = places.get(aref.base).startIndex + aref.index.eval(null);
				int val = pt.get(pind) + arc.getSecond(); 
				pt.put(pind, val);
				if (val < 0) 
					hasNeg = true;
			}
		}
		if (hasNeg) {
			hasNeg = false;
			for (int i=0,ie=pt.size() ; i<ie;i++) {
				if (pt.valueAt(i)< 0) {
					Logger.getLogger("fr.lip6.move.gal").info("Discarding a negative binding.");
					return -1;
				}
			}
		}
		
		SparseIntArray tp = new SparseIntArray();
		for (Pair<Expression, Integer> arc : bt.post) {
			Expression place = arc.getFirst();
			if (place.getOp() == Op.HLPLACEREF) {
				ArrayVarRef aref = (ArrayVarRef) place;
				int pind = places.get(aref.base).startIndex + aref.index.eval(null);
				int val = tp.get(pind) + arc.getSecond(); 
				tp.put(pind, val);
				if (val < 0) 
					hasNeg = true;
			}
		}
		if (hasNeg) {
			for (int i=0,ie=tp.size() ; i<ie;i++) {
				if (tp.valueAt(i)< 0) {
					Logger.getLogger("fr.lip6.move.gal").info("Discarding a negative binding.");
					return -1;
				}
			}
		}
		// done !
		int tind = spn.addTransition(bt.name);
		for (int i=0,ie=pt.size() ; i<ie;i++) {
			spn.addPreArc(pt.keyAt(i), tind, pt.valueAt(i));
		}
		for (int i=0,ie=tp.size() ; i<ie;i++) {
			spn.addPostArc(tp.keyAt(i), tind, tp.valueAt(i));						
		}
		return tind;
	}

	public void dropAllExcept(Set<Integer> safeNodes) {

		int [] perm = new int [places.size()];
		for (int i = 0, ind =0 ; i < places.size() ; i++) {
			if (safeNodes.contains(i)) {
				perm[i]=ind++;
			} else {
				perm[i]=-1;
			}
		}

		int trem=0,prem=0;
		for (int tid = transitions.size()-1 ; tid >= 0 ;  tid -- ) {
			for (Pair<Expression, Integer> arc : transitions.get(tid).pre) {
				int pid = ((ArrayVarRef) arc.getFirst()).base;
				if (!safeNodes.contains(pid)) {
					transitions.remove(tid);
					trem++;
					break;
				} else {
					((ArrayVarRef) arc.getFirst()).base = perm[pid];
				}
			}			
		}
		for (int tid = transitions.size()-1 ; tid >= 0 ;  tid -- ) {
			for (Pair<Expression, Integer> arc : transitions.get(tid).post) {
				int pid = ((ArrayVarRef) arc.getFirst()).base;
				((ArrayVarRef) arc.getFirst()).base = perm[pid];
			}
			transitions.get(tid).post.removeIf(arc -> ((ArrayVarRef) arc.getFirst()).base == -1);
		}
		for (int pid = places.size()-1 ; pid >= 0 ;  pid -- ) {
			if (! safeNodes.contains(pid)) {
				places.remove(pid);
				prem++;
			}
		}
		
		placeCount = 0;
		for (int pid = 0, pide=places.size() ; pid < pide ;  pid ++ ) {
			places.get(pid).startIndex = placeCount;
			placeCount += places.get(pid).getInitial().length;
		}

		System.out.println("Prefix of Interest using HLPN skeleton for deadlock discarded "+prem+" places and "+trem + " transitions.");		
	}
	
	
	public IOrder computeOrder () {
		Map<String,List<HLPlace>> placeSort = new HashMap<>();
		for (HLPlace p : places) {
			placeSort.computeIfAbsent(p.getSort(), v -> new ArrayList<>()).add(p);
		}
		List<IOrder> orders = new ArrayList<>();
		for (Entry<String, List<HLPlace>> ps : placeSort.entrySet()) {
			List<HLPlace> places = ps.getValue(); 
			if (! places.isEmpty())
			{
				//Sort psort = places.get(0).getType().getStructure();
				int sz = places.get(0).getInitial().length; 

				if (sz > 1) {
					
					for (int i=0 ; i < sz ; i++) {
						List<String> supp = new ArrayList<>();
						for (HLPlace p : places) {
							supp.add( p.getName() +"_" + i);
						}
						orders.add(new VarOrder(supp, ps.getKey()+i));
					}
				} else {
					// dot case mostly
					for (HLPlace p : places) {
						List<String> supp = new ArrayList<>();
						String pname = p.getName();
						supp.add(pname+"_0");
						orders.add(new VarOrder(supp, pname));
					}
				}
			}
		}
		IOrder order = new CompositeGalOrder(orders, "main");
		return order;
	}
	
}

class HLTrans {
	String name;
	List<Param> params;
	Expression guard;
	
	List<Pair<Expression,Integer>> pre= new ArrayList<>();
	List<Pair<Expression,Integer>> post=new ArrayList<>();
	public HLTrans(String name, List<Param> params, Expression guard) {
		this.name = name;
		this.params = params;
		this.guard = guard;
	}
	@Override
	public String toString() {
		return "HLTrans [name=" + name + ", params=" + params + ", guard=" + guard + ", pre=" + pre + ", post=" + post
				+ "]";
	}
	
	
}