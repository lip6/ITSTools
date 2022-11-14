package fr.lip6.move.gal.structural.hlpn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.ArrayVarRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Param;
import fr.lip6.move.gal.structural.expr.ParamRef;
import fr.lip6.move.gal.structural.expr.Simplifier;

public class SparseHLPetriNet extends PetriNet {
	private static final int DEBUG = 0;
	private List<Sort> sorts = new ArrayList<>();
	private List<HLPlace> places = new ArrayList<>();
	private List<HLTrans> transitions= new ArrayList<>();
	int placeCount=0;
	
	public int addTransition (String tname, List<Param> params, Expression guard) {
		transitions.add(new HLTrans(tname,params,guard));
		return transitions.size()-1;
	}
	
	public int addPlace (String pname, int [] init, List<Sort> sort) {
		int index = placeCount;
		places.add(new HLPlace(pname, index, init, sort));
		placeCount += init.length;
		return places.size()-1;
	}
	
	public void addSort (Sort sort) {
		sorts.add(sort);
	}

	public void addPreArc(int p, int t, List<Expression> func, int val) {
		transitions.get(t).getPre().add(new HLArc(p, val, func));
	}

	public void addPostArc(int p, int t, List<Expression> func, int val) {
		transitions.get(t).getPost().add(new HLArc(p, val, func));
	}
	
	public Sort findSort(String name) {
		for (Sort s : sorts) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		System.err.println("Warning : Sort "+ name+ " not registered in HL net. Existing sorts :" + sorts);
		return null;
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
			if (transitions.get(i).getName().equals(name)) {
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
		return "SparseHLPetriNet\n[places=" + places + ",\n transitions=" + transitions + ",\n getName()=" + getName()
				+ ",\n getProperties()=" + getProperties() + "]";
	}
	
	
	public SparsePetriNet skeleton () {
		long time = System.currentTimeMillis();
		SparsePetriNet spn = new SparsePetriNet();
		spn.setName(getName() +"_skel");
		spn.setSkeleton(true);
		
		// generate places with appropriate indexes
		for (HLPlace p : places) {
			spn.addPlace(p.getName(), Arrays.stream(p.getInitial()).sum());
		}
		for (HLTrans t : transitions) {
			spn.addTransition(t.getName());

			SparseIntArray pt = computeCardinality(t.getPre());
			for (int i=0,ie=pt.size();i<ie;i++) {
				spn.addPreArc(pt.keyAt(i), spn.getTransitionCount()-1, pt.valueAt(i));
			}
			SparseIntArray tp = computeCardinality(t.getPost());
			for (int i=0,ie=tp.size();i<ie;i++) {
				spn.addPostArc(tp.keyAt(i), spn.getTransitionCount()-1, tp.valueAt(i));
			}
		}

		Logger.getLogger("fr.lip6.move.gal").info("Built PT skeleton of HLPN with "+spn.getPlaceCount()+ " places and " + spn.getTransitionCount() + " transitions " + spn.getArcCount() + " arcs in " + (System.currentTimeMillis()- time) + " ms.");
		
		time = System.currentTimeMillis();
		int removed = 0;
		// now resolve enabled + cardinality predicates
		for (Property p : getProperties()) {
			// also check that we don't have nasty AND combinations of enablings that overlap and are guarded
			if (p.getType() == PropertyType.CTL || p.getType() == PropertyType.LTL) {
				if (! hasNoGuardedEnablingCombinations(p)) {
					removed++;
					continue;
				}				
			}
			spn.getProperties().add(new Property(bindSkeletonColors(p.getBody()),p.getType(),p.getName()));
		}
		Logger.getLogger("fr.lip6.move.gal").info("Skeletonized "+spn.getProperties().size() +" HLPN properties in " + (System.currentTimeMillis()- time) + " ms." 
						  + (removed>0 ? (" Removed "+removed+ " properties that had guard overlaps."):""));
		if (DEBUG >=1)
			FlowPrinter.drawNet(new StructuralReduction(spn), "Skeleton net", new HashSet<>(), new HashSet<>());
		return spn;
	}



	private SparseIntArray computeCardinality(List<HLArc> pre) {
		SparseIntArray pt = new SparseIntArray();
		for (HLArc arc : pre) {
			int pind = arc.getPlace();
			int val = pt.get(pind) + arc.getCoeff(); 
			pt.put(pind, val);			
		}
		return pt;
	}

	public SparsePetriNet unfold (ReductionType rt) {
		List<List<Integer>> enablings = new ArrayList<>(transitions.size());
		return unfold(enablings, rt);
	}
	public List<Sort> getSorts() {
		return sorts;
	}
	
	public SparsePetriNet unfold (List<List<Integer>> enablings, ReductionType rt) {
		long time = System.currentTimeMillis();
		if (rt != ReductionType.STATESPACE)
			SymmetricUnfolder.testSymmetryConditions(this);
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
			t.getParams().sort((a,b) -> Integer.compare(a.size(),b.size()));
			todo.add(t);
			List<Integer> done = new ArrayList<>();
			while (! todo.isEmpty()) {
				HLTrans bt = todo.poll();
				if (bt.getParams().isEmpty()) {
					int tind = transformHLtoPT(bt,spn);
					if (tind >= 0) {
						done.add(tind);
					}
				} else {
					List<Param> pcopy = new ArrayList<>(bt.getParams().subList(1, bt.getParams().size()));
					Param cur = bt.getParams().get(0);
					for (int val=0; val < cur.size() ; val++) {
						Expression guard = bind(cur,val,bt.getGuard());
						if (guard.getOp() == Op.BOOLCONST && guard.getValue() == 0) {
							// false guard, abort
							continue;
						}
						HLTrans tcopy = new HLTrans(bt.getName() +cur.getName()+val, pcopy, guard);
						
						boolean skip = false;
						// to please compiler in lambda captures
						final int fval = val;
						for (HLArc arc : bt.getPre()) {							
							List<Expression> r = arc.getCfunc().stream().map(e -> bind(cur,fval,e)).collect(Collectors.toList());
							int value = arc.getCoeff();
							int pind = arc.getPlace();
							if (places.get(pind).isConstant() && r.stream().allMatch(e->e.getOp() == Op.CONST)) 
							{
								// fully simplified
								if (places.get(pind).getInitial()[places.get(pind).resolve(r)] < value) {
									// no can do ! place is constantly insufficiently marked.
									skip = true;
									break;
								} else {
									// skip arc
									continue;
								}
							}							
							tcopy.getPre().add(new HLArc(pind,value,r));
						}
						if (skip) {
							continue;
						}						
						for (HLArc arc : bt.getPost()) {
							List<Expression> r = arc.getCfunc().stream().map(e -> bind(cur,fval,e)).collect(Collectors.toList());
							int value = arc.getCoeff();
							int pind = arc.getPlace();
							if (places.get(pind).isConstant() && r.stream().allMatch(e->e.getOp() == Op.CONST)) 
							{
								continue;
							}
							tcopy.getPost().add(new HLArc(pind,value,r));							
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
		Logger.getLogger("fr.lip6.move.gal").info("Unfolded "+spn.getProperties().size() +" HLPN properties in " + (System.currentTimeMillis()- time) + " ms.");
		if (DEBUG >=1)
			FlowPrinter.drawNet(spn, "After Unfold", Collections.emptySet(), Collections.emptySet());
		
		return spn;
	}

	void fuseEqualParameters(HLTrans t) {
		if (t.getParams().size() < 2) {
			return ;
		} else {
			if (t.getGuard().getOp() != Op.BOOLCONST) {
				while (handleEqual(t.getGuard(),t)) {
					continue;
				}
			}
		}
	}

	public void simplifyLogic() {
		for (Property prop : getProperties()) {
			prop.setBody(Simplifier.pushNegation(prop.getBody()));
			prop.setBody(Simplifier.simplifySumComparisons(prop.getBody()));
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
		for (HLArc arc : t.getPre()) {
			for (Expression e : arc.getCfunc()) {
				remapParameter(e, tokeep, todel);
			}
		}
		for (HLArc arc : t.getPost()) {
			for (Expression e : arc.getCfunc()) {
				remapParameter(e, tokeep, todel);
			}
		}
		remapParameter(t.getGuard(), tokeep, todel);
		t.getParams().remove(todel);		
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
							int si = places.get(child.getValue()).getStartIndex();
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
			throw new IllegalArgumentException("Expression "+ expr+ " is an array.");
//			ArrayVarRef aref = (ArrayVarRef) expr;
//			return Expression.var( places.get(aref.base).startIndex + aref.index.eval(null));
		}
		return expr;
	}

	public static Expression bind(Param cur, int val, Expression expr) {
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
		for (HLArc arc : bt.getPre()) {
			HLPlace hlplace = places.get(arc.getPlace());
			int pind = hlplace.getStartIndex();
			int offset = hlplace.resolve(arc.getCfunc());
			int finalp = pind+offset;
			int val = pt.get(finalp) + arc.getCoeff();
			pt.put(finalp, val);
			if (val < 0) 
				hasNeg = true;
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
		for (HLArc arc : bt.getPost()) {
			HLPlace hlplace = places.get(arc.getPlace());
			int pind = hlplace.getStartIndex();
			int offset = hlplace.resolve(arc.getCfunc());
			int finalp = pind+offset;
			int val = tp.get(finalp) + arc.getCoeff();
			tp.put(finalp, val);
			if (val < 0) 
				hasNeg = true;
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
		int tind = spn.addTransition(bt.getName());
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
			for (HLArc arc : transitions.get(tid).getPre()) {
				int pid = arc.getPlace();
				if (!safeNodes.contains(pid)) {
					transitions.remove(tid);
					trem++;
					break;
				} else {
					arc.setPlace(perm[pid]);
				}
			}			
		}
		for (int tid = transitions.size()-1 ; tid >= 0 ;  tid -- ) {
			for (HLArc arc : transitions.get(tid).getPost()) {
				int pid = arc.getPlace();
				arc.setPlace(perm[pid]);
			}
			transitions.get(tid).getPost().removeIf(arc -> arc.getPlace() == -1);
		}
		for (int pid = places.size()-1 ; pid >= 0 ;  pid -- ) {
			if (! safeNodes.contains(pid)) {
				places.remove(pid);
				prem++;
			}
		}
		
		resetPlaceCount();

		System.out.println("Prefix of Interest using HLPN skeleton for deadlock discarded "+prem+" places and "+trem + " transitions.");		
	}

	public void resetPlaceCount() {
		placeCount = 0;
		for (int pid = 0, pide=places.size() ; pid < pide ;  pid ++ ) {
			places.get(pid).setStartIndex(placeCount);
			placeCount += places.get(pid).getInitial().length;
		}
	}
	
	public static String sortName(List<Sort> sort) {
		StringBuilder sb = new StringBuilder();
		for (Sort s : sort) {
			sb.append(s.getName());
		}
		return sb.toString();
	}
	
	private boolean hasNoGuardedEnablingCombinations(Property p) {
		Set<Integer> targets = new HashSet<>(); 
		findEnablings(p.getBody(),targets);
		boolean hasGuards = targets.stream().anyMatch(tid -> getTransitions().get(tid).getGuard().getOp() != Op.BOOLCONST);
		if (hasGuards) {
			Set<Integer> guardedPlaces = new HashSet<>();
			// pick up colored places that are guarded (touch a guarded transition)
			for (int tid : targets) {
				HLTrans trans = getTransitions().get(tid);
				if (trans.getGuard().getOp() != Op.BOOLCONST) {
					for (HLArc arc : trans.getPre()) {
						int pid = arc.getPlace();
						// any overlap is bad
						if (!guardedPlaces.add(pid)) {
							return false;
						}
					}
				}
			}
			
			// any overlap of unguarded transition with guarded places is also bad
			for (int tid : targets) {
				HLTrans trans = getTransitions().get(tid);
				if (trans.getGuard().getOp() == Op.BOOLCONST) {
					for (HLArc arc : trans.getPre()) {
						int pid = arc.getPlace();
						// any overlap is bad
						if (guardedPlaces.contains(pid)) {
							return false;
						}
					}
				}
			}						
		}
		return true;
	}
	
	private static void findEnablings(Expression e, Set<Integer> targets) {
		if (e == null || Op.isComparison(e.getOp())) {
			return ;
		} else if (e.getOp() == Op.ENABLED) {
			for (int cid = 0, cide = e.nbChildren() ; cid < cide ; cid++) {
				targets.add(e.childAt(cid).getValue());
			}
		} else {
			for (int cid = 0, cide = e.nbChildren() ; cid < cide ; cid++) {
				findEnablings(e.childAt(cid), targets);
			}
		}		
	}
	
}
