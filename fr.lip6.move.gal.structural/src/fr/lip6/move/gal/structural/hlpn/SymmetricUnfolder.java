package fr.lip6.move.gal.structural.hlpn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Param;
import fr.lip6.move.gal.structural.expr.ParamRef;

public class SymmetricUnfolder {
	private static int DEBUG=1;

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


	public static void testSymmetryConditions(SparseHLPetriNet net) {
		for (HLTrans trans : net.getTransitions()) {
			net.fuseEqualParameters(trans);
		}
		
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
			Map<Integer,Integer> touchedPlaces = new HashMap<>();
			{
				int placeid=-1;
				for (HLPlace place : net.getPlaces()) {
					placeid++;
					// find if we contain this domain
					int sortindex = -1;
					for (int i=0; i < place.getSort().size() ; i++) {
						if (place.getSort().get(i).equals(sort)) {
							if (sortindex != -1) {
								// condition : no cross products of this domain TODO improve
								isSym = false;
								if (DEBUG >=1)
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
					} else {
						touchedPlaces.put(placeid,sortindex);
					}
				}
			}
			

			if (!isSym) {
				// break for this sort, successor test broke us
				continue;
			}
			
			// test for successor/predecessor
			for (HLTrans trans : net.getTransitions()) {
				for (HLArc arc : trans.getPre()) {
					Integer sortind = touchedPlaces.get(arc.getPlace());
					if (sortind != null) {
						if (arc.getCfunc().get(sortind).getOp()==Op.MOD) {
							isSym = false;
							if (DEBUG >=1)
								System.out.println(
									"Arc "+arc+" contains successor/predecessor on variables of sort "+ sort.getName());
							break;
						}
					}
				}
				if (!isSym) {
					break;
				}
				for (HLArc arc : trans.getPost()) {
					Integer sortind = touchedPlaces.get(arc.getPlace());
					if (sortind != null) {
						if (arc.getCfunc().get(sortind).getOp()==Op.MOD) {
							isSym = false;
							if (DEBUG >=1)
								System.out.println(
									"Arc "+arc+" contains successor/predecessor on variables of sort "+ sort.getName());
							break;
						}
					}
				}
				if (!isSym) {
					break;
				}
			}
			
			if (!isSym) {
				// break for this sort, successor test broke us
				continue;
			}
			
			// tests for synchronization
			for (HLTrans trans : net.getTransitions()) {				
				for (Param param : trans.getParams()) {					
					if (param.getSort().equals(sort.getName())) {
						int seenplace = -1;
						for (HLArc arc : trans.getPre()) {
							Integer sortind = touchedPlaces.get(arc.getPlace());
							if (sortind != null) {
								List<Param> refs = new ArrayList<>();
								computeParams(arc.getCfunc().get(sortind),refs);
								if (refs.contains(param)) {
									if (seenplace == -1) {
										seenplace = arc.getPlace();
									} else if (seenplace != arc.getPlace()) {
										if (DEBUG >=1)
											System.out.println("Transition "+trans.getName()+ " forces synchronizations/join behavior on parameter "+ param.getName() + " of sort "+ sort.getName());
										isSym = false;
										break;
									}
								}
							}
						}						
					}
				}
				if (!isSym) {
					break;
				}
			}

			if (!isSym) {
				// break for this sort, synchronization test broke us
				continue;
			}
			
			if (DEBUG >=1)
				System.out.println("Symmetric sort wr.t. initial and guards and successors and join/free detected :" + sort.getName());
			
			
			Partition partition = new Partition(sort.size());
			
			// in fact, being join free makes this condition useless, who cares about constant colors when they have the same role and are not joined.
			// take a good look at color functions on arcs
			// test for constants
			// TODO : recognize ALL as being symmetric => take into account the set of arcs from p to t, not just each arc one by one
			for (HLTrans trans : net.getTransitions()) {
				AtomicBoolean ab = new AtomicBoolean(isSym);
				partition = computeArcSymmetry(net, sort, trans, trans.getPre(), touchedPlaces, partition, ab);
				
				isSym = ab.get();
				if (!isSym) {
					break;
				}
				
				partition = computeArcSymmetry(net, sort, trans, trans.getPost(), touchedPlaces, partition, ab);
				isSym = ab.get();
				if (!isSym) {
					break;
				}
			}

			if (!isSym) {
				// break for this sort, constants test broke us
				continue;
			}
				
			if (false) {
			int placeid = -1;
			for (HLPlace place : net.getPlaces()) {
				placeid++;
				Integer sortindex = touchedPlaces.get(placeid);
				if (sortindex == null) {
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

					int val = place.getInitial()[Partition.sumprod(cur,multipliers)];
					// for each domain value
					for (int x = 1; x < sort.size(); x++) {
						cur[sortindex]=x;
						int index = Partition.sumprod(cur,multipliers);
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
					if (DEBUG >=1)
						System.out.println("Place "+place+" leaves sort "+sort.getName()+ " symmetric.");
				} else {
					if (DEBUG >=1)
						System.out.println("Place "+place+" breaks symmetry on sort:"+sort.getName()+ "");
					break;
				}
				
			} // places loop
			}



			if (!isSym) {
				// break for this sort, place markings or self cross product domains broke us
				continue;
			}
			if (DEBUG >=1)
				System.out.println("Symmetric sort wr.t. initial detected :" + sort.getName());

			

			// test transition guards now
			for (HLTrans trans : net.getTransitions()) {
				// non trivial
				if (trans.getGuard() != null && trans.getGuard().getOp() != Op.BOOLCONST) {
					List<Param> refs = new ArrayList<>();
					computeParams(trans.getGuard(),refs);
					for (Param cur : refs) {
						// check Sort of Param
						if (cur.getSort().equals(sort.getName())) {

							// compute symmetry
							Map<Expression, List<Integer>> localpart = new HashMap<>();
							for (int val = 0; val < cur.size(); val++) {
								Expression guard = SparseHLPetriNet.bind(cur, val, trans.getGuard());
								localpart.computeIfAbsent(guard, k -> new ArrayList<>()).add(val);
							}
							if (localpart.size()==1) {
								// trivial partition
								continue;
							}
							if (DEBUG >=1)
								System.out.println(
										"Transition "+trans.getName()+" : guard parameter "+ cur + " in guard " + trans.getGuard() + "introduces in " + sort + " partition with " + localpart.size() + " elements");
							if (DEBUG >= 2)
								System.out.println("Partition :" + localpart);
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
							if (containsSyncsOn(trans.getGuard(),sort.getName())) {
								if (DEBUG >=1)
									System.out.println(
										"Transition "+trans.getName()+" : guard " + trans.getGuard() + " contains nasty synchronization on some of "+ refs);

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
				if (DEBUG >=1)
					System.out.println("Symmetric sort wr.t. initial and guards detected :" + sort.getName());
			} else {
				if (DEBUG >=1)
					System.out.println("Sort wr.t. initial and guards " + sort.getName()+ " has partition " + partition);
			}


			
			if (partition.getNbSubs()==1) {
				System.out.println("Applying symmetric unfolding of full symmetric sort :" + sort.getName() + " domain size was " + sort.size());
			} else {
				System.out.println("Applying symmetric unfolding of partitioned symmetric sort :" + sort.getName() + " domain size was " + sort.size() + " reducing to " + partition.getNbSubs() + " values.");
			}
			
			{
				// looking good we can rewrite
				int pind = -1;
				for (HLPlace place : net.getPlaces()) {
					pind++;
					Integer sortindex = touchedPlaces.get(pind);
					if (sortindex != null) {						
						int[] after = partition.rewriteMarking(place,sortindex);
						if (DEBUG >=2)
							System.out.println("For place "+place.getName()+ ":" + Arrays.toString(place.getInitial()) + "\n->\n "+ Arrays.toString(after) );						 
					    place.setInitial(after);						
					}
				}
				net.resetPlaceCount();
				for (HLTrans trans : net.getTransitions()) {
					List<Param> refs = new ArrayList<>();
					computeParams(trans.getGuard(), refs);
					for (Param par : trans.getParams()) {
						if (par.getSort().equals(sort.getName())) {
							boolean touchGuard = refs.contains(par);
								
							Map<Expression, List<Integer>> partition2 = new HashMap<>();
							
							if (touchGuard) {
								for (int val = 0; val < par.size(); val++) {
									Expression guard = SparseHLPetriNet.bind(par, val, trans.getGuard());
									partition2.computeIfAbsent(guard, k -> new ArrayList<>()).add(val);
								}
							}
							// resize potential bindings of parameter							
							par.setSize(partition.getNbSubs());
							
							if (touchGuard) {
								List<Expression> result = new ArrayList<>();							
								for (Entry<Expression, List<Integer>> ent:partition2.entrySet()) {
									List<Integer> cover = partition.covers(ent.getValue());
									List<Expression> toOr = new ArrayList<>();
									for (Integer i: cover) {
										toOr.add(Expression.op(Op.EQ, Expression.paramRef(par), Expression.constant(i)));
									}
									result.add(Expression.op(Op.AND, ent.getKey(), Expression.nop(Op.OR,toOr)));
								}
								Expression newguard = Expression.nop(Op.OR,result);
								if (DEBUG >=1)
									System.out.println("For transition "+ trans.getName()+ ":" + trans.getGuard() + " -> "+ newguard );
								trans.setGuard(newguard);
							}
						}
					}
					//now deal with potential constants on arcs
					for (HLArc arc : trans.getPre()) {
						Integer sortindex = touchedPlaces.get(arc.getPlace());
						if (sortindex != null) {
							if (arc.getCfunc().get(sortindex).getOp()==Op.CONST) {
								int target = arc.getCfunc().get(sortindex).getValue();
								int img = partition.getImage(target);								
								arc.setCFuncElt(sortindex, Expression.constant(img));
							}
						}
					}
					for (HLArc arc : trans.getPost()) {
						Integer sortindex = touchedPlaces.get(arc.getPlace());
						if (sortindex != null) {
							if (arc.getCfunc().get(sortindex).getOp()==Op.CONST) {
								int target = arc.getCfunc().get(sortindex).getValue();
								int img = partition.getImage(target);
								arc.setCFuncElt(sortindex, Expression.constant(img));
							}
						}
					}
				}
				sort.setSize(partition.getNbSubs());
			} // rewrite step

		}
	}


	public static Partition computeArcSymmetry(SparseHLPetriNet net, Sort sort, HLTrans trans, List<HLArc> arcs,
			Map<Integer, Integer> touchedPlaces, Partition partition, AtomicBoolean ab) {
		Map<Integer,List<HLArc>> constFuncArcs = new HashMap<>();
		for (HLArc arc : arcs) {
			Integer sortind = touchedPlaces.get(arc.getPlace());
			if (sortind != null) {
				if (arc.getCfunc().get(sortind).getOp()==Op.CONST) {
					constFuncArcs.computeIfAbsent(arc.getPlace(), x -> new ArrayList<>()).add(arc);
					if (DEBUG >=1)
						System.out.println(
								"Arc "+arc+" contains constants of sort "+ sort.getName());
					//break;
				}
			}
		}
		if (! constFuncArcs.isEmpty()) {
			// try to recognize a partition/All
			for (Entry<Integer, List<HLArc>> ent:constFuncArcs.entrySet()) {
				int place = ent.getKey();
				int sortindex = touchedPlaces.get(place);

				for (HLArc a : ent.getValue()) {
					if (a.getCoeff() != 1) {
						// TODO : improve this test
						ab.set(false);
						break;
					}
				}

				if (!ab.get()) {
					break;
				}

				// compute symmetry
				Map<List<Expression>, List<Integer>> localpart = new HashMap<>();

				List<Sort> psort = net.getPlaces().get(place).getSort();

				for (HLArc a : ent.getValue()) {
					List<Expression> l = new ArrayList<>(psort.size()-1);
					for (int i=0; i < psort.size() ; i++) {
						if (i != sortindex) {
							l.add(a.getCfunc().get(i));
						}
					}
					localpart.computeIfAbsent(l, k -> new ArrayList<>()).add(a.getCfunc().get(sortindex).getValue());							
				}
				
				for (List<Integer> l : localpart.values()) {
					Partition p2 = new Partition(sort.size(),l);
					partition = Partition.refine(p2, partition);
					
					if (partition.getNbSubs()==sort.size()) {
						ab.set(false);
						break;
					}
				}
				
				if (DEBUG >=1)
					System.out.println(
							"Transition "+trans.getName()+" : constants on arcs in "+ ent.getValue() + " introduces in " + sort + " partition with " + localpart.size() + " elements that refines current partition to "+partition.getNbSubs() + " subsets.");
				if (DEBUG >= 2)
					System.out.println("Partition :" + localpart);

			}
		}
		return partition;
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
