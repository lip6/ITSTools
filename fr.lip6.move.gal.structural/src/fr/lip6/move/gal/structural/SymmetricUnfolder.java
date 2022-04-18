package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.lip6.move.gal.structural.expr.ArrayVarRef;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Param;
import fr.lip6.move.gal.structural.expr.ParamRef;
import fr.lip6.move.gal.util.Pair;

public class SymmetricUnfolder {

	private static boolean isPure(Expression cfunc) {
		if (cfunc instanceof ArrayVarRef) {
			ArrayVarRef aref = (ArrayVarRef) cfunc;
			Expression e = aref.index;
			if (e.getOp() == Op.PARAMREF) {
				return true;
			} else if (e.getOp() == Op.CONST) {
				return false;
			} else if (e.getOp() == Op.ADD) {
				for (int i=0; i < e.nbChildren() ; i++) {
					Expression m = e.childAt(i);
					if (m.getOp() == Op.PARAMREF) {
						continue;
					} else if (m.getOp() == Op.MULT && m.childAt(0).getOp()==Op.PARAMREF && m.childAt(1).getOp() == Op.CONST) {
						continue;
					} else {
						return false;
					}
				}
			}			
		}
		return false;
	}

	private static Void computeParams(Expression e,  List<Param> params) {
		if (e == null) {
			return null;
		} else if (e instanceof ParamRef) {
			ParamRef pr = (ParamRef) e;
			if (!params.contains(pr.parameter))
				params.add(pr.parameter);
		} else {
			e.forEachChild(c -> computeParams(c, params));
		}
		return null;
	}

	private static int sumprod(int[] cur, int[] multipliers) {
		int res = 0;
		for (int i=0,ie=cur.length; i < ie ; i++) {
			res += cur[i]*multipliers[i];
		}
		return res;
	}

	public static void testSymmetryConditions(SparseHLPetriNet net) {
		Map<String,Boolean> isSortSymmetric = new HashMap<>();

		// examine each sort one by one.
		// start with larger domains
		net.getSorts().sort((a,b)->-Integer.compare(a.size(),b.size()));
		for (Sort sort : net.getSorts()) {

			if (sort.size()==1) {
				// "dot" domain
				// not much to be symmetric about
				continue;
			}

			// until proved wrong
			boolean isSym = true;

			// examine places

			for (HLPlace place : net.getPlaces()) {
				// find if we contain this domain
				int sortindex = -1;
				for (int i=0; i < place.getSort().size() ; i++) {
					if (place.getSort().get(i).equals(sort)) {
						if (sortindex != -1) {
							// condition : no cross products of this domain TODO improve
							isSym = false;
							System.out.println("Domain "+ place.getSort() + " of place " + place.getName() + " breaks symmetries in sort "+ sort.getName());
							break;
						} else {
							sortindex=i;
						}
					}
				}
				if (!isSym) {
					break;
				}

				if (sortindex == -1) {
					// try next place
					continue;
				}

				// length of sub vectors to be compared

				// setup data structures for iterating over variants 
				int prodSize = place.getSort().size();

				// to reinterpret a vector of indexes to an index in compound array
				int [] multipliers = new int [prodSize];
				multipliers[prodSize-1]=1;						
				for (int i=prodSize-2 ; i >= 0 ; i--) {
					multipliers[i] = multipliers[i+1]*place.getSort().get(i+1).size();
				}

				// increment each element using its basis
				int [] sizes = new int [prodSize];
				for (int i=0;i<prodSize;i++) {
					sizes[i]=place.getSort().get(i).size();
				}


				// start from [0,0,0]
				int [] cur = new int [prodSize];
				while (true) {

					int val = place.getInitial()[sumprod(cur,multipliers)];
					// for each domain value
					for (int x = 1; x < sort.size(); x++) {
						cur[sortindex]=x;
						int index = sumprod(cur,multipliers);
						if (val != place.getInitial()[index]) {
							isSym=false;
							break;
						}
					}
					if (!isSym) {
						break;
					}
					// reset x coordinate to 0
					cur[sortindex]=0;

					// increment other domains
					int j;
					for (j = cur.length - 1; j >= 0; j--) {
						if (j == sortindex) {
							// increment across this one
							continue;
						} else {
							if (cur[j] == sizes[j] - 1) {
								cur[j] = 0;
								continue;
							} else {
								cur[j]++;
								break;
							}
						}
					}
					if (j == -1) {
						// overflow happened
						break;
					}
				}

				if (isSym) {
					System.out.println("Place "+place+" leaves sort "+sort.getName()+ " symmetric.");
				} else {
					System.out.println("Place "+place+" breaks symmetry on sort:"+sort.getName()+ "");
					break;
				}

			} // places loop




			if (!isSym) {
				// break for this sort, place markings or self cross product domains broke us
				continue;
			}

			System.out.println("Symmetric sort wr.t. initial detected :" + sort.getName());

			Partition partition = new Partition(sort.size());

			// test transition guards now
			for (HLTrans trans : net.getTransitions()) {
				// non trivial
				if (trans.guard != null && trans.guard.getOp() != Op.BOOLCONST) {
					List<Param> refs = new ArrayList<>();
					computeParams(trans.guard,refs);
					for (Param cur : refs) {
						// check Sort of Param
						if (cur.getSort().equals(sort.getName())) {

							// compute symmetry
							Map<Expression, List<Integer>> localpart = new HashMap<>();
							for (int val = 0; val < cur.size(); val++) {
								Expression guard = SparseHLPetriNet.bind(cur, val, trans.guard);
								localpart.computeIfAbsent(guard, k -> new ArrayList<>()).add(val);
							}
							if (localpart.size()==1) {
								// trivial partition
								continue;
							}
							System.out.println(
									"Transition "+trans.getName()+" : guard parameter "+ cur + " in guard " + trans.guard + "introduces in " + sort + " partition :" + localpart);
							partition = Partition.refine(new Partition(localpart.values()), partition);
							if (partition.getNbSubs()==sort.size()) {
								isSym = false;
								break;
							}
						}
					}
					if (isSym) {
						refs.removeIf(p -> ! p.getSort().equals(sort.getName()));
						if (refs.size() > 1) {
							// look for x<y or x!=y predicates, we don't like them.
							if (containsSyncsOn(trans.guard,sort.getName())) {
								System.out.println(
										"Transition "+trans.getName()+" : guard " + trans.guard + " contains nasty synchronization on some of "+ refs);

								isSym = false;
							}
						}
					}
				}
				if (!isSym) {
					break;
				}
			}

			if (!isSym) {
				// break for this sort, guards broke us
				continue;
			}
			if (partition.getNbSubs()==1) {
				System.out.println("Symmetric sort wr.t. initial and guards detected :" + sort.getName());
			} else {
				System.out.println("Sort wr.t. initial and guards " + sort.getName()+ " has partition " + partition);
			}

			// tests for synchronization
			// take a good look at color functions on arcs


		}


		System.out.println("Symmetric sorts wr.t. initial :" + isSortSymmetric);
		Map<String, Partition> sortParts = new HashMap<>();
		for (Sort s : net.getSorts()) {
			sortParts.put(s.getName(), new Partition(s.size()));
		}

		Map<String,Boolean> isSortJoinFree = new HashMap<>();
		for (String sort : isSortSymmetric.keySet()) {
			isSortJoinFree.put(sort, true);
		}
		boolean allArcsArePure = true;
		for (Entry<String, Boolean> entry : isSortSymmetric.entrySet()) {
			String sort = entry.getKey();
			if (entry.getValue()) {				
				for (HLTrans trans : net.getTransitions()) {
					if (trans.guard != null && trans.guard.getOp() != Op.BOOLCONST) {

						{
							List<Param> refs = new ArrayList<>();
							computeParams(trans.guard,refs);
							for (Param cur : refs) {
								if (cur.getSort().equals(sort)) {
									Map<Expression, List<Integer>> partition = new HashMap<>();
									for (int val = 0; val < cur.size(); val++) {
										Expression guard = SparseHLPetriNet.bind(cur, val, trans.guard);
										partition.computeIfAbsent(guard, k -> new ArrayList<>()).add(val);
									}
									System.out.println("Guard :" + trans.guard + "introduces in " + sort
											+ " partition :" + partition);
									sortParts.compute(sort, (k, v) -> {
										Partition p = new Partition(partition.values());
										if (v == null) {
											return p;
										} else {
											return Partition.refine(v, p);
										}
									});
								}
							}
						}
					}

					Set<Param> params = new HashSet<>();
//					for (Pair<Expression, Integer> arc : trans.pre) {
//						List<Param> refs = new ArrayList<>();
//						computeParams(arc.getFirst(),refs);
//						for (Param p : refs) {
//							if (params.contains(p)) {
//								isSortJoinFree.put(p.getSort(), false);
//							}
//						}
//						params.addAll(refs);
//						if (net.getPlaces().get(arc.getSecond()).getInitial().length>1) {
//							Expression e = arc.getFirst();
//							if (isPure(e)) {
//								//		System.out.println("Pure arc " + arc);							
//							} else {
//								System.out.println("Impure arc " + arc);
//								allArcsArePure = false;
//							}
//						}
//					}
//					for (Pair<Expression, Integer> arc : trans.post) {
//						if (net.getPlaces().get(arc.getSecond()).getInitial().length>1) {
//							Expression e = arc.getFirst();
//							if (isPure(e)) {
//								//		System.out.println("Pure arc " + arc);							
//							} else {
//								System.out.println("Impure arc " + arc);
//								allArcsArePure = false;
//							}
//						}
//					}
				}
			}
		}

		System.out.println("Sort partitions :" + sortParts);
		System.out.println("Are sorts join free ? " + isSortJoinFree);

		for (String sort : isSortSymmetric.keySet()) {
			if (allArcsArePure && isSortSymmetric.get(sort) && isSortJoinFree.get(sort)) {
				Partition p = sortParts.get(sort);
				if (p!= null && p.getNbSubs() < p.size() && p.getNbSubs() > 1) {
					// looking good we can rewrite
					int base = 0;
					for (HLPlace place : net.getPlaces()) {
						if (place.getSort().get(0).getName().equals(sort)) {
							int[] after = p.rewriteMarking(place.getInitial());
							System.out.println("For place "+place.getName()+ ":" + Arrays.toString(place.getInitial()) + " -> "+ Arrays.toString(after) );
							place.setInitial(after);
						}
						place.startIndex = base;
						base += place.getInitial().length;
					}
					for (HLTrans trans : net.getTransitions()) {
						for (Param par : trans.params) {
							if (par.getSort().equals(sort)) {

								Map<Expression, List<Integer>> partition = new HashMap<>();
								for (int val = 0; val < par.size(); val++) {
									Expression guard = SparseHLPetriNet.bind(par, val, trans.guard);
									partition.computeIfAbsent(guard, k -> new ArrayList<>()).add(val);
								}
								par.setSize(p.getNbSubs());
								List<Expression> result = new ArrayList<>();
								for (Entry<Expression, List<Integer>> ent:partition.entrySet()) {
									List<Integer> cover = p.covers(ent.getValue());
									List<Expression> toOr = new ArrayList<>();
									for (Integer i: cover) {
										toOr.add(Expression.op(Op.EQ, Expression.paramRef(par), Expression.constant(i)));
									}
									result.add(Expression.op(Op.AND, ent.getKey(), Expression.nop(Op.OR,toOr)));
								}
								Expression newguard = Expression.nop(Op.OR,result);

								System.out.println("For transition "+ trans.getName()+ ":" + trans.guard + " -> "+ newguard );
								trans.guard = newguard;
							}
						}

					}
				}
			}
		}
	}

	private static boolean containsSyncsOn(Expression e, final String sortname) {
		if (e==null)
			return false;
		switch (e.getOp()) {
		case LT:case LEQ:case NEQ:case EQ:case GT:case GEQ:
			// comparisons/terminals
		{
			List<Param> refs = new ArrayList<>();
			computeParams(e,refs);
			refs.removeIf(p -> ! p.getSort().equals(sortname));
			return refs.size() >= 2;
		}
		default :
		{
			for (int i=0; i < e.nbChildren() ; i++) {
				if (containsSyncsOn(e.childAt(i), sortname)) {
					return true;
				}
			}
			return false;
		}
		}
	}
}