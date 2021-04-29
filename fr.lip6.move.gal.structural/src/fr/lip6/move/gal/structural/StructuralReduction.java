package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.util.IntMatrixCol;


/**
 * Implement Haddad/Pradat-Peyre/Berthelot/Thierry-Mieg structural reduction rules.
 * @author ythierry
 *
 */


public class StructuralReduction implements Cloneable, ISparsePetriNet {

	private List<Expression> image;
	private List<Integer> marks;
	private IntMatrixCol flowPT;
	private IntMatrixCol flowTP;
	private List<String> tnames;
	private List<String> pnames;
	private int maxArcValue;
	private BitSet untouchable;
	private BitSet tokeepImages;
	private boolean keepImage = false;
	private boolean isSafe = false;

	private static final int DEBUG = 0;
	
	public StructuralReduction(IDeterministicNextBuilder idnb) {
		FlowMatrix fm = new MatrixBuilder(idnb).getMatrix();
		marks = new ArrayList<>(idnb.getInitial());
		flowPT = fm.getFlowPT();
		flowTP = fm.getFlowTP();
		pnames = new ArrayList<>(idnb.getVariableNames());
		tnames = new ArrayList<>();
		int sz = idnb.getDeterministicNext().size();
		for (int i=0 ; i < sz ; i++) {
			tnames.add("t"+i);
		}
		maxArcValue = findMax(flowPT);
		maxArcValue = Math.max(findMax(flowTP),maxArcValue);
		untouchable = new BitSet();
		tokeepImages = new BitSet();
		image = new ArrayList<> (pnames.size());
		for (int i=0,ie=pnames.size(); i < ie ; i++) {
			image.add(Expression.var(i));
		}
		
	}

	
	private StructuralReduction(IntMatrixCol flowPT, IntMatrixCol flowTP, List<Integer> marks, List<String> tnames,
			List<String> pnames, int maxArcValue, BitSet untouchable) {
		this.flowPT = new IntMatrixCol(flowPT);
		this.flowTP = new IntMatrixCol(flowTP);
		this.marks = new ArrayList<>(marks);
		this.tnames = new ArrayList<>(tnames);
		this.pnames = new ArrayList<>(pnames);
		this.maxArcValue = maxArcValue;
		this.untouchable = (BitSet) untouchable.clone();
		this.tokeepImages = new BitSet();
	}


	public StructuralReduction(ISparsePetriNet spn) {
		this(spn.getFlowPT(),spn.getFlowTP(),spn.getMarks(),spn.getTnames(),spn.getPnames(),spn.getMaxArcValue(),spn.computeSupport());
		if (spn instanceof StructuralReduction) {
			StructuralReduction sr2 = (StructuralReduction) spn;
			this.image = new ArrayList<> (sr2.image);
		} else {
			image = new ArrayList<> (getPlaceCount());
			for (int i=0,ie=getPlaceCount(); i < ie ; i++) {
				image.add(Expression.var(i));
			}
		}
		isSafe = spn.isSafe();
	}

	public List<Expression> getImage() {
		return image;
	}

	public void setKeepImage(boolean keepImage) {
		this.keepImage = keepImage;
	}
	
	public BitSet getTokeepImages() {
		return tokeepImages;
	}
	
	private int findMax(IntMatrixCol mat) {
		int max =0;
		for (int ti = 0 ; ti < mat.getColumnCount() ; ti++) {
			SparseIntArray trcol = mat.getColumn(ti);
			for (int i=0 ; i < trcol.size() ; i++) {
				max = Math.max(max, trcol.valueAt(i));
			}
		}
		return max;
	}
	
	public StructuralReduction clone() {
		StructuralReduction clone = new StructuralReduction(flowPT, flowTP, marks, tnames, pnames, maxArcValue, untouchable);
		clone.image = new ArrayList<> (image);
		clone.keepImage = keepImage;
		clone.tokeepImages = (BitSet) tokeepImages.clone();
		clone.isSafe = isSafe;
		return clone;
	}
	
	@Override
	public BitSet computeSupport() {
		return untouchable;
	}
	
	@Override
	public int getMaxArcValue() {
		return maxArcValue;
	}
	
	public Specification rebuildSpecification () {
		return SpecBuilder.buildSpec(flowPT, flowTP, pnames, tnames, marks);
	}
	
	public enum ReductionType { DEADLOCKS, SAFETY, SI_LTL, LTL, LIVENESS, STATESPACE }
	public int reduce (ReductionType rt) throws NoDeadlockExists, DeadlockFound {
		//ruleSeqTrans(trans,places);
		int initP = pnames.size();
		int initT = tnames.size();
		
		if (DEBUG==2) FlowPrinter.drawNet(this, "Before Reduction Start");
				
		long time = System.currentTimeMillis();
		int total = 0;
		int totaliter=0;
		int iter =0;
		
		if (rt == ReductionType.STATESPACE) {
			// pretty basic stuff only
			total += ruleReducePlaces(rt,false,false);
			total += ruleReduceTrans(rt);
			total += ruleRedundantCompositions(rt);
			total += ruleReducePlaces(rt,false,false);
			total += ruleReduceTrans(rt);			
			return total;
		}
		
		if (findFreeSCC(rt)) {
			total++;
			totaliter += ruleReduceTrans(rt);
		}
			
		if (findAndReduceSCCSuffixes(rt)) 
			total++;
		
		if (rt == ReductionType.SI_LTL) {
			totaliter += ruleReducePlaces(rt,false,true);
		}
		
		int deltatpos = 0;
		do {
			int tsz = tnames.size();
			do {
				totaliter=0;
				totaliter += ruleReducePlaces(rt,false,false);
//				if (totaliter > 0) {
//					FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
//				}
				totaliter += ruleReduceTrans(rt);
				
				totaliter += findAndReduceSCCSuffixes(rt) ? 1:0;

				int implicit = ruleImplicitPlace();
				totaliter +=implicit;
				if (totaliter > 0 && findFreeSCC(rt))
					totaliter++;
				
				int agglo = ruleTrivialPostAgglo(rt);
				totaliter += agglo;
				if (agglo == 0)
					totaliter += rulePostAgglo(false,true,rt);
				
				total += totaliter;
				if (totaliter > 0) {
					System.out.println("Iterating post reduction "+ (iter++) + " with "+ totaliter+ " rules applied. Total rules applied " + total + " place count " + pnames.size() + " transition count " + tnames.size());				
				} else {					
					if (DEBUG>=1) System.out.println("Stability for Post agglomeration reached at "+ (iter++));					
				}				
			} while (totaliter > 0);
			totaliter = 0;
			totaliter += rulePreAgglo(false,rt);
			
			if (totaliter > 0) {
				System.out.println("Pre-agglomeration after "+ (iter) + " with "+ totaliter+ " Pre rules applied. Total rules applied " + total+ " place count " + pnames.size() + " transition count " + tnames.size());				
			} else {
				if (DEBUG>=1) System.out.println("No additional pre-agglomerations found "+ (iter++));
			}
			
			if (tnames.stream().anyMatch(s -> s.length() >= 1024)) {
				System.out.println("Renaming transitions due to excessive name length > 1024 char.");
				for (int i=0; i < tnames.size(); i++) {
					tnames.set(i, "t"+i);
				}
			}
			
			// int sym = ruleSymmetricChoice();
			if (totaliter == 0) {
				int sym = ruleFusePlaceByFuture(rt);
				totaliter += sym;
				total += totaliter;
				if (sym > 0) {
					System.out.println("Symmetric choice reduction at "+ (iter) + " with "+ sym + " rule applications. Total rules  " + total+ " place count " + pnames.size() + " transition count " + tnames.size());				
				}
			}
			
			
			if (totaliter == 0) {
				totaliter += rulePostAgglo(false,false,rt);
			}
		
			if (totaliter == 0) {
				totaliter += rulePostAgglo(true,false,rt);
			}
			
			if (totaliter == 0) {
				totaliter += rulePreAgglo(true,rt);
			}
			
			if (totaliter == 0) {
				totaliter += findFreeSCC(rt) ? 1 :0;
			}
			if (totaliter == 0) {
				totaliter += findAndReduceSCCSuffixes(rt) ? 1 :0;
			}
			totaliter += ruleReducePlaces(rt,true,false);						
			if (totaliter ==0) {
				totaliter += ruleRedundantCompositions(rt);
			}
			
			if (totaliter == 0 && rt == ReductionType.SAFETY) {
				totaliter += ruleFreeAgglo(false);
			}
			if (totaliter == 0 && rt == ReductionType.SAFETY) {
				totaliter += ruleFreeAgglo(true);
			}
			if (totaliter == 0 && rt == ReductionType.SAFETY) {
				totaliter += rulePartialFreeAgglo();
			}			
			if (totaliter == 0 && (rt == ReductionType.SAFETY || (rt == ReductionType.SI_LTL && ! keepImage)) ) {
				// this rule is almost legitimate for SI_LTL
				// but not quite
				totaliter += rulePartialPostAgglo(rt);
			}						
			
			if (totaliter ==0) {
				totaliter += ruleReducePlaces(rt,false,true);
			}
			
			total += totaliter;
			if (totaliter > 0) {
				System.out.println("Iterating global reduction "+ (iter) + " with "+ totaliter+ " rules applied. Total rules applied " + total + " place count " + pnames.size() + " transition count " + tnames.size());				
			} else {					
				if (DEBUG>=1) System.out.println("Stability for reduction rules reached at after "+ (totaliter));					
			}
			if (tnames.size() > tsz) {
				deltatpos ++;
			} else {
				deltatpos = 0;
			}
			System.out.flush();
		} while (totaliter > 0 && deltatpos <= 3);
		System.out.println("Applied a total of "+total+" rules in "+ (System.currentTimeMillis() - time)+ " ms. Remains "+ pnames.size() + " /" +initP + " variables (removed "+ (initP - pnames.size()) +") and now considering "+ flowPT.getColumnCount() + "/" + initT + " (removed "+ (initT - flowPT.getColumnCount()) +") transitions.");
		if (DEBUG==2) FlowPrinter.drawNet(this, "At convergence for reductions without SMT." + (deltatpos > 3?" (Break increasing because of growing trend on transition count)":""));
		System.out.flush();
		
		return total;
	}
	
	
	public int ruleRedundantCompositionsBounds() {
		if (tnames.size() > 20000) {
			// quadratic |T| => 10^8 hurts too much 
			return 0;
		}
		Set<Integer> todel = new HashSet<>();		
		// map effect to list of transition indexes having this effect
		Map<SparseIntArray,List<Integer>> effects = new HashMap<>();
		for (int tid=0, e=tnames.size() ; tid < e ; tid++) {
			final int t =tid;
			// we want positive effects
			SparseIntArray effect = SparseIntArray.sumProd(-1, flowPT.getColumn(tid),1,flowTP.getColumn(tid));
			SparseIntArray posEffect = new SparseIntArray();
			for (int i=0,ie=effect.size(); i < ie ; i++) {
				int v = effect.valueAt(i);
				if (v > 0) {
					posEffect.append(effect.keyAt(i), v);
				}
			}
			effects.compute(posEffect, (k,v) -> {
				if (v==null) {
					v = new ArrayList<>();
					v.add(t);					
				} else {
					for (int to : v) {
						if (SparseIntArray.greaterOrEqual(flowPT.getColumn(t), flowPT.getColumn(to))) {
							todel.add(t);
							Set<Integer> tset = new HashSet<>();
							tset.add(t);
							tset.add(to);
							FlowPrinter.drawNet(this,"Discarding transition "+tnames.get(t)+ " with rule Dominated (bounds)", new HashSet<>(), tset );
						} else if (SparseIntArray.greaterOrEqual(flowPT.getColumn(to), flowPT.getColumn(t))) {
							todel.add(to);
							Set<Integer> tset = new HashSet<>();
							tset.add(t);
							tset.add(to);
							FlowPrinter.drawNet(this,"Discarding transition "+tnames.get(to)+ " with rule Dominated (bounds)", new HashSet<>(), tset );
						}
					}
					v.add(t);
					v.removeAll(todel);
				}
				return v;
			});
		}
		if (! todel.isEmpty()) {
			dropTransitions(new ArrayList<>(todel),"Dominated transitions (bounds rule).");
			System.out.println("Dominated transitions for bounds rules discarded "+todel.size()+ " transitions");
		}		
		return todel.size();
	}
	
	
	/**
	 * Detects and destroys transitions t such that exists t1,t2 such that 
	 * t1 fireable => t1.t2 is fireable for any state
	 * t has the same effects as t1.t2
	 * t has superior or equal preconditions to t1.
	 * @param rt 
	 * @return the number of transitions discarded by the rule
	 */
	private int ruleRedundantCompositions(ReductionType rt) {
		if (tnames.size() > 20000 || rt == ReductionType.LIVENESS) {
			// quadratic |T| => 10^8 hurts too much 
			return 0;
		}
		Set<Integer> todel = new HashSet<>();		
		// map effect to list of transition indexes having this effect
		Map<SparseIntArray,List<Integer>> effects = new HashMap<>();
		List<Integer> tids = new ArrayList<>();
		
		for (int tid=0, e=tnames.size() ; tid < e ; tid++) {
			final int t =tid;
			if (rt == ReductionType.SI_LTL && touches(t)) {
				continue;
			} else {
				tids.add(t);
			}
			effects.compute(SparseIntArray.sumProd(-1, flowPT.getColumn(tid),1,flowTP.getColumn(tid)) , (k,v) -> {
				if (v==null) {
					v = new ArrayList<>();
					v.add(t);					
				} else {
					for (int to : v) {
						if (SparseIntArray.greaterOrEqual(flowPT.getColumn(t), flowPT.getColumn(to))) {
							todel.add(t);
						} else if (SparseIntArray.greaterOrEqual(flowPT.getColumn(to), flowPT.getColumn(t))) {
							todel.add(to);
						}
					}
					v.add(t);
					v.removeAll(todel);
				}
				return v;
			});
		}
		
		if (rt != ReductionType.LTL) {
			IntMatrixCol tflowPT = flowPT.transpose();
			tids.sort((a,b) -> -Integer.compare(flowPT.getColumn(a).size()+ flowTP.getColumn(a).size(), flowPT.getColumn(b).size()+ flowTP.getColumn(b).size()) );
			for (int id=0, e=tids.size() ; id < e ; id++) {
				int tid = tids.get(id);
				if (todel.contains(tid)) {
					continue;
				}
				// preconditions for firing t
				SparseIntArray pre = flowPT.getColumn(tid);
				// state reached after firing t from it's minimal enabling
				SparseIntArray init = flowTP.getColumn(tid);
				SparseIntArray teff = SparseIntArray.sumProd(-1, pre, 1, init);
				Set<Integer> potentialEnable = new HashSet<>();
				SparseIntArray feedT = flowTP.getColumn(tid);
				for (int pi=0, ee = feedT.size(); pi < ee ; pi++) {
					int p=feedT.keyAt(pi);
					SparseIntArray fedByP = tflowPT.getColumn(p);
					for (int ti=0 ;ti < fedByP.size(); ti++) {
						potentialEnable.add(fedByP.keyAt(ti));
					}
				}
				// other transitions enabled by t 
				for (int ttid: potentialEnable) {
					if (todel.contains(ttid)) {
						continue;
					}
					if (SparseIntArray.greaterOrEqual(init, flowPT.getColumn(ttid))) {
						// tid fireable => tid.ttid is possible
						final int t=tid;
						final int tt=ttid;
						SparseIntArray tchain = SparseIntArray.sumProd(1, teff, 1, SparseIntArray.sumProd(-1, flowPT.getColumn(ttid), 1, flowTP.getColumn(ttid)));
						effects.compute(tchain , (k,v) -> {
							if (v!=null) {
								for (int to : v) {
									if (to==t || to==tt) {
										continue;
									}
									if (SparseIntArray.greaterOrEqual(flowPT.getColumn(to), pre)) {
										todel.add(to);
										if (DEBUG >= 1) {
											System.out.println("Discarding "+tnames.get(to)+ " index "+to + " pre :" + flowPT.getColumn(to) + " post :" + flowTP.getColumn(to) +" that is dominated by " + tnames.get(t) + "&" + tnames.get(tt) + " effects " + tchain +  " pre " + pre);
										}
									}
								}
								v.removeAll(todel);
							}
							return v;
						});
					}
				}						
			}
		}
		if (! todel.isEmpty()) {
			dropTransitions(new ArrayList<>(todel),"Redundant composition of simpler transitions.");
			System.out.println("Redundant transition composition rules discarded "+todel.size()+ " transitions");
		}		
		return todel.size();
	}
	
	public int rulePartialPostAgglo(ReductionType rt) {
		IntMatrixCol tflowTP = null;
		IntMatrixCol tflowPT = flowPT.transpose();
		int done = 0;
		Set<Integer> toreduce = new HashSet<>();
		for (int pid = 0, pide = getPlaceCount(); pid < pide; pid++) {
			if (marks.get(pid) != 0 || untouchable.get(pid))
				continue;
			SparseIntArray tpt = tflowPT.getColumn(pid);
			boolean canReduce = true;
			boolean hasStutter = false;
			boolean hasNonStutter = false;
			for (int i = 0, ie = tpt.size(); i < ie; i++) {
				int tid = tpt.keyAt(i);
				SparseIntArray pt = flowPT.getColumn(tid);
				if (pt.size() == 1 && pt.valueAt(0) == 1) {
					// stuttering transition with one single input from p
					if (touches(tid)) {
						hasNonStutter = true;
					} else {
						hasStutter = true;
					}
				} else {
					canReduce = false;
					break;
				}
			}
			if (canReduce && hasNonStutter && hasStutter) {
				toreduce.add(pid);
			}
		}
		
		List<Integer> todropt = new ArrayList<>();
		if (! toreduce.isEmpty()) {
			for (int pid : toreduce) {
				if (tflowTP == null) {
					tflowTP = flowTP.transpose();
				}
				if (tflowPT == null) {
					tflowPT = flowPT.transpose();
				}
				SparseIntArray ttp = tflowTP.getColumn(pid);
				SparseIntArray tpt = tflowPT.getColumn(pid);
				// avoid any potential explosion
				if (rt != ReductionType.DEADLOCKS && ttp.size() > 1) {
					continue;
				}
				// feeders and consumers should not intersect
				if (SparseIntArray.keysIntersect(tpt, ttp)) {
					continue;
				}
				// only feed arc weights is 1 around p
				boolean ok = true;
				for (int i=0,ie=ttp.size() ; i < ie ; i++) {
					if (ttp.valueAt(i) > 1) {
						ok  = false;
					}
				}
				if (!ok) {
					continue;
				}
				// now for each successor that only consumes in p, create a new agglomerate transition with every input of p
				for (int i=0,ie=tpt.size() ; i < ie ; i++) {
					int tid = tpt.keyAt(i);
					if (touches(tid)) {
						continue;
					}
					if (DEBUG>=1) System.out.println("Net is Partial-Post-aglomerable in transition id "+tid+ " "+tnames.get(tid) + " place " + pid + " pre "+ tpt + " post " + ttp );
					
					int curt = tnames.size();
					if (flowPT.getColumn(tid).size()==1 && flowPT.getColumn(tid).valueAt(0)==1) {
						if (DEBUG >= 2) {
							Set<Integer> hf = new HashSet<>();
							hf.add(tid);
							for (int j=0,je=ttp.size() ; j < je ; j++) {
								int fi = ttp.keyAt(j);
								hf.add(fi);
							}
							FlowPrinter.drawNet(this, "Partial-Post-Agglomerating place :" + pnames.get(pid), Collections.singleton(pid), hf );
						}
						for (int j=0,je=ttp.size() ; j < je ; j++) {
							int hi = ttp.keyAt(j);
							SparseIntArray resPT = SparseIntArray.sumProd(1, flowPT.getColumn(tid), 1, flowPT.getColumn(hi), pid);
							flowPT.appendColumn(resPT);


							SparseIntArray resTP = SparseIntArray.sumProd(1, flowTP.getColumn(tid), 1, flowTP.getColumn(hi),pid);				
							flowTP.appendColumn(resTP);

							String tname = tnames.get(hi)+"."+ tnames.get(tid);
							tnames.add(tname );
							if (DEBUG>=1) System.out.println("Added transition "+tname +" pre:" + resPT  +" post:" + resTP);
							done++;
						}
						tflowTP = null;
						tflowPT = null;
					} else {
						continue;
					}
					todropt.add(tid);					
					if (DEBUG >= 2) {
						Set<Integer> hf = new HashSet<>();
						hf.add(tid);
						for (int j=0,je=tpt.size() ; j < je ; j++) {
							int fi = tpt.keyAt(j);
							hf.add(fi);
						}
						for (int t=curt ; t < tnames.size() ; t++) {
							hf.add(t);
						}
						FlowPrinter.drawNet(this, "After Partial-Post-Agglomerating place :" + pnames.get(pid), Collections.singleton(pid), hf );
					}

				}
			}
		}
		if (done >0) {
			System.out.println("Partial Post-agglomeration rule applied "+done+" times.");
			dropTransitions(todropt, "Partial Post agglomeration");
		}
		
		return done;
	}

	
	public int rulePartialFreeAgglo() {
		IntMatrixCol tflowTP = null;
		IntMatrixCol tflowPT = null;
		int done = 0;
		Set<Integer> toreduce = new HashSet<>();
		for (int tid = 0, te = tnames.size() ; tid < te ; tid ++) {
			SparseIntArray tp = flowTP.getColumn(tid);
			if (tp.size() == 1) {
				// transition with one single output into p
				int pid = tp.keyAt(0);
				if (tp.valueAt(0)==1 && marks.get(pid) == 0 && !untouchable.get(pid) && !touches(tid)) {
					toreduce.add(pid);
				}
			}
		}
		List<Integer> todropt = new ArrayList<>();
		if (! toreduce.isEmpty()) {
			for (int pid : toreduce) {
				if (tflowTP == null) {
					tflowTP = flowTP.transpose();
				}
				if (tflowPT == null) {
					tflowPT = flowPT.transpose();
				}
				SparseIntArray tpt = tflowPT.getColumn(pid);
				// avoid any potential explosion
				if (tpt.size() > 1) {
					continue;
				}
				SparseIntArray ttp = tflowTP.getColumn(pid);
				// feeders and consumers should not intersect
				if (SparseIntArray.keysIntersect(tpt, ttp)) {
					continue;
				}
				// only consume arc weights is 1 around p
				boolean ok = true;
				for (int i=0,ie=tpt.size() ; i < ie ; i++) {
					if (tpt.valueAt(i) > 1) {
						ok  = false;
					}
				}
				if (!ok) {
					continue;
				}
				// now for each predecessor that only feeds p, create a new agglomerate transition with every output of p
				for (int i=0,ie=ttp.size() ; i < ie ; i++) {
					int tid = ttp.keyAt(i);
					if (touches(tid)) {
						continue;
					}
					if (DEBUG>=1) System.out.println("Net is Partial-Free-aglomerable in transition id "+tid+ " "+tnames.get(tid) + " place " + pid + " pre "+ tflowTP.getColumn(pid) + " post " + tflowPT.getColumn(pid) );
					
					int curt = tnames.size();
					if (flowTP.getColumn(tid).size()==1 && flowTP.getColumn(tid).valueAt(0)==1) {
						if (DEBUG >= 2) {
							Set<Integer> hf = new HashSet<>();
							hf.add(tid);
							for (int j=0,je=tpt.size() ; j < je ; j++) {
								int fi = tpt.keyAt(j);
								hf.add(fi);
							}
							FlowPrinter.drawNet(this, "Partial-Free-Agglomerating place :" + pnames.get(pid), Collections.singleton(pid), hf );
						}
						for (int j=0,je=tpt.size() ; j < je ; j++) {
							int fi = tpt.keyAt(j);
							SparseIntArray resPT = SparseIntArray.sumProd(1, flowPT.getColumn(tid), 1, flowPT.getColumn(fi), pid);
							flowPT.appendColumn(resPT);


							SparseIntArray resTP = SparseIntArray.sumProd(1, flowTP.getColumn(tid), 1, flowTP.getColumn(fi),pid);				
							flowTP.appendColumn(resTP);

							String tname = tnames.get(tid)+"."+tnames.get(fi);
							tnames.add(tname );
							if (DEBUG>=1) System.out.println("Added transition "+tname +" pre:" + resPT  +" post:" + resTP);
							done++;
						}
					} else {
						continue;
					}
					todropt.add(tid);					
					if (DEBUG >= 2) {
						Set<Integer> hf = new HashSet<>();
						hf.add(tid);
						for (int j=0,je=tpt.size() ; j < je ; j++) {
							int fi = tpt.keyAt(j);
							hf.add(fi);
						}
						for (int t=curt ; t < tnames.size() ; t++) {
							hf.add(t);
						}
						FlowPrinter.drawNet(this, "After Partial-Free-Agglomerating place :" + pnames.get(pid), Collections.singleton(pid), hf );
					}

				}
			}
		}
		if (done >0) {
			System.out.println("Partial Free-agglomeration rule applied "+done+" times.");
			dropTransitions(todropt, "Partial Free agglomeration");
		}
		
		return done;
	}


	public int ruleFreeAgglo(boolean doComplex) {
		IntMatrixCol tflowTP = null;
		int done = 0;
		int red = 0;
		for (int pid=0 ; pid < pnames.size() ; pid++) {
			if (untouchable.get(pid)) {
				continue;
			}
			if (marks.get(pid) >0)
				continue;
			if (tflowTP == null) {
				tflowTP = flowTP.transpose();
			}
			SparseIntArray toP = tflowTP.getColumn(pid);
			// p has a (single) input t, that has a single output p
			if ( (toP.size()==1 && toP.valueAt(0)==1) || doComplex) {
				boolean ok = true;
				// single input to p
				List<Integer> Hids = new ArrayList<>();
				for (int i=0 ; i < toP.size(); i++) {
					int tid = toP.keyAt(i);
					if (toP.valueAt(i) != 1) {
						ok = false;
						break;
					}
					if (flowTP.getColumn(tid).size() > 1) {
						ok = false;
						break;
					}
					if (!doComplex && flowPT.getColumn(tid).size() > 1) {
						ok = false;
						break;
					}
					if (touches(tid)) {
						ok = false;
						break;
					}
					if (flowPT.getColumn(tid).get(pid) >0) {
						ok = false;
						break;
					}
					Hids.add(tid);					
				}
				if (!ok) {
					continue;
				}
				List<Integer> Fids = new ArrayList<>();				
				for (int ttid=0 ; ttid < tnames.size() ; ttid++) {
					int val = flowPT.getColumn(ttid).get(pid);
					if ( val == 1) {
						if (flowTP.getColumn(ttid).get(pid) >0) {
							ok = false;
							break;
						}						
						Fids.add(ttid);
					} else if (val > 1) {
						ok = false;
						break;
					}
				}
				if (!ok) {
					continue;
				}
				if (DEBUG>=1) System.out.println("Net is Free-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);					
				red += agglomerateAround(pid, Hids , Fids, "Free",null,tflowTP);
				done++;
			} 
		}
		
		if (done >0) {
			System.out.println("Free-agglomeration rule "+ (doComplex?"(complex) ":" ") +"applied "+done+" times" +
					(red>0?" with reduction of "+red+" identical transitions." : ".")
					);
		}
		
		return done;
	}


	public int ruleReduceTrans(ReductionType rt) throws NoDeadlockExists {
		int reduced = 0;
		if (rt == ReductionType.SAFETY || rt == ReductionType.STATESPACE) {
			
			List<Integer> todrop = new ArrayList<>();
			for (int i = tnames.size()-1 ;  i >= 0 ; i--) {
				if ( (rt == ReductionType.SAFETY || rt == ReductionType.STATESPACE) && flowPT.getColumn(i).equals(flowTP.getColumn(i))) {
					// transitions with no effect => no use to safety
					todrop.add(i);
				} else if (rt == ReductionType.SAFETY && flowTP.getColumn(i).size() == 0 && ! touches(i)) {
					// sink transitions that are stealing tokens from the net are not helpful
					// they lead to strictly weaker nets
					todrop.add(i);
				}
			}
			if (! todrop.isEmpty()) {
				reduced += todrop.size();
				dropTransitions(todrop,"Empty/Sink Transition effects.");
			}
		}
		reduced += ensureUnique(flowPT, flowTP, tnames, null, true); 
		if (reduced > 0) {
			System.out.println("Reduce isomorphic transitions removed "+ reduced +" transitions.");
		}
		if (rt == ReductionType.DEADLOCKS) {
			for (int i = 0; i < flowPT.getColumnCount() ; i++) {
				if (flowPT.getColumn(i).size()==0) {
					System.out.println("Found a source transition so no deadlocks exist. place count " + pnames.size() + " transition count " + tnames.size());					
					throw new NoDeadlockExists();
				}
			}
		} 
		
		if (maxArcValue > 1 && rt != ReductionType.LIVENESS) {
			IntMatrixCol tflowPT = flowPT.transpose(); 
			int modred = 0;
			// reverse ordered set of tindexes to kill
			Set<Integer> todel = new TreeSet<>((x,y) -> -Integer.compare(x, y));
			// look for a place with output arc value > 1			
			for (int pid = 0 ; pid < pnames.size() ; pid++) {
				SparseIntArray line = tflowPT.getColumn(pid);
				for (int i =0 ; i < line.size() ; i++) {
					if (line.valueAt(i) > 1) {
						modred += testModuloIsomorphism(line,todel);
						break;
					}
				}
			}
			if (modred > 0) {
				for (int td : todel) {
					tnames.remove(td);
					flowPT.deleteColumn(td);
					flowTP.deleteColumn(td);
				}
				System.out.println("Reduce isomorphic (modulo) transitions removed "+ reduced +" transitions.");
				reduced += modred;
				maxArcValue = findMax(flowPT);
				maxArcValue = Math.max(findMax(flowTP),maxArcValue);
			}
		}
		return reduced;
	}

	private int testModuloIsomorphism(SparseIntArray line, Set<Integer> todel) {
		int total = 0;
		for (int i = 0 ; i < line.size() ; i++) {
			int ti = line.keyAt(i);
			int vi = line.valueAt(i);
			SparseIntArray coli = flowPT.getColumn(ti);
			for (int j = i+1 ; j < line.size() ; j++) {
				int tj = line.keyAt(j);
				int vj = line.valueAt(j);
				if (vi == vj) {
					// identity will have reduced this
					continue;
				}
				if (vi > vj) {
					int tmp = tj;
					tj = ti;
					ti = tmp;
					tmp = vi;
					vi = vj;
					vj = tmp;
				}
				if (vj % vi != 0) {
					// no possible factor
					continue;
				}
				int factor = vj / vi;
				SparseIntArray colj = flowPT.getColumn(tj);
				if (coli.size() != colj.size() || flowTP.getColumn(ti).size() != flowTP.getColumn(tj).size()) {
					continue;
				}
				// test inputs
				boolean ok = true;
				for (int ii=0 ; ii < coli.size() ; ii++) {
					if (coli.keyAt(ii) != colj.keyAt(ii)) {
						ok  =false;
						break;
					} else  {
						int vvi = coli.valueAt(ii);
						int vvj = colj.valueAt(ii);
						if (vvj % vvi != 0 || vvj / vvi != factor) {
							ok = false;
							break;
						}
					}
				}
				if (ok) {
					total++;
					todel.add(tj);
				}
			}
		}		
		return total;
	}

	private int ensureUnique(IntMatrixCol mPT, IntMatrixCol mTP, List<String> names, List<Integer> init, boolean trace) {
		Map<SparseIntArray, Map<SparseIntArray,Integer>> seen = new HashMap<>();
		List<Integer> todel = new ArrayList<>();
			
		if (init != null) {
			for (int i = untouchable.nextSetBit(0); i >= 0; i = untouchable.nextSetBit(i+1)) {
				SparseIntArray tcolPT = mPT.getColumn(i);
				SparseIntArray tcolTP = mTP.getColumn(i);
				seen.computeIfAbsent(tcolPT, k -> new HashMap<>()).put(tcolTP, i);
			}
		}
		
		if (init == null) {
			// plain iteration order to collect decreasing tokill indexes
			for (int trid=mPT.getColumnCount()-1 ; trid >= 0 ; trid--) {
				SparseIntArray tcolPT = mPT.getColumn(trid);
				SparseIntArray tcolTP = mTP.getColumn(trid);
				Integer b = seen.computeIfAbsent(tcolPT, k -> new HashMap<>()).put(tcolTP, trid);
				if (b != null) {
					todel.add(trid);
				}
			}								
		} else {
			// we need to be more careful about place markings + untouchable
			for (int trid=mPT.getColumnCount()-1 ; trid >= 0 ; trid--) {
				if (untouchable.get(trid)) {
					continue;
				}
				SparseIntArray tcolPT = mPT.getColumn(trid);
				SparseIntArray tcolTP = mTP.getColumn(trid);
				Map<SparseIntArray, Integer> map = seen.computeIfAbsent(tcolPT, k -> new HashMap<>());
				Integer pb = map.get(tcolTP);
				if (pb != null) {
					if (init.get(trid) >= init.get(pb.intValue())) {
						todel.add(trid);							
					} else if (! untouchable.get(pb)) {
						todel.add(pb);
						map.put(tcolTP, init.get(pb.intValue()));
					}
				} else {
					map.put(tcolTP, trid);
				}
			}
			todel.sort((a,b)-> -a.compareTo(b));
		}
		if (DEBUG >= 2 && !todel.isEmpty()) {
			FlowPrinter.drawNet(this, "Unique test discarding "+todel.size()+ " objects ", init != null ? new HashSet<>(todel):Collections.emptySet(), init == null ? new HashSet<>(todel):Collections.emptySet());
		}
		List<String> rem = new ArrayList<>();
		for (int td : todel) {
			rem.add(names.remove(td));
			mPT.deleteColumn(td);
			mTP.deleteColumn(td);
			if (init != null) {
				init.remove(td);
				image.remove(td);
				removeAt(td,untouchable);
				removeAt(td,tokeepImages);
			}
		}
		if (trace && !todel.isEmpty()) {
			System.out.println("Ensure Unique test removed "+ rem.size()+ (init!=null?" places":" transitions") + (DEBUG>=1 ?" : "+ rem  : ""));
		}
		return todel.size();
	}


	private int ruleReducePlaces(ReductionType rt, boolean withSyphon, boolean moveTokens) throws DeadlockFound {
		int totalp = 0;
		// find constant marking places
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();
		// reverse ordered set of tindexes to kill
		Set<Integer> todelTrans = new TreeSet<>((x,y) -> -Integer.compare(x, y));

		StructuralReduction sr2 = null;
		Set<Integer> cstP = null;
		boolean withPreFire = false;
		if (DEBUG >= 2) {
			sr2 = clone();
			cstP = new HashSet<>();
		}
		
		if (rt != ReductionType.LTL && !keepImage && moveTokens && rt != ReductionType.LIVENESS) {
			if (rt == ReductionType.SI_LTL  && marks.stream().mapToInt(i->i).sum() == 1) {
				int pid = marks.indexOf(1);
				SparseIntArray from = tflowPT.getColumn(pid);
				SparseIntArray to = tflowTP.getColumn(pid);
				
				// empty initially marked places that control their output fully
				if (to.size()==0 && marks.get(pid)!=0 && from.size() == 1 && flowPT.getColumn(from.keyAt(0)).size()==1 && !touches(Collections.singletonList(from.keyAt(0)))) {
					emptyPlaceWithTransition(pid, from.keyAt(0));
					withPreFire = true;
				}				
			}
			
			// do this scan and update first to ensure no updates to flowPT/flowTP in emptyPlaces are messed up
			for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
				if (untouchable.get(pid)) {
					continue;
				}
				SparseIntArray from = tflowPT.getColumn(pid);
				SparseIntArray to = tflowTP.getColumn(pid);

				// empty initially marked places that control their output fully
				if (to.size()==0 && marks.get(pid)!=0 && from.size() == 1 && flowPT.getColumn(from.keyAt(0)).size()==1 && !touches(Collections.singletonList(from.keyAt(0)))) {
					// make sure empty place does its job fully
					int val = from.valueAt(0);
					int mark = marks.get(pid);
					if (mark % val != 0) {
						marks.set(pid, (mark / val) * val);
					}
					if (DEBUG>=1) System.out.println("Firing immediate continuation of initial place "+pnames.get(pid) + " emptying place using " + tnames.get(from.keyAt(0)) + " index " + from.keyAt(0));
					emptyPlaceWithTransition(pid, from.keyAt(0));
					withPreFire = true;
					if (rt != ReductionType.DEADLOCKS) {
						break;
					}
				}
			}
		}
		List<String> prem = new ArrayList<>();
		List<String> trem = new ArrayList<>();
		Set<Integer> syphon = withSyphon ? SiphonComputer.computeEmptySyphon(flowPT,flowTP,marks) : Collections.emptySet();
		// now scan for isomorphic/redundant/useless/constant places
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
			
			SparseIntArray from = tflowPT.getColumn(pid);
			SparseIntArray to = tflowTP.getColumn(pid);
			
			if (isConstantPlace(pid, from, to, syphon) && (! keepImage || ! tokeepImages.get(pid))) {
				// constant marking place
				// or zero inputs so no tokens will magically appear in here
				int m = marks.get(pid);
				for (int tpos = 0 ; tpos  < from.size() ; tpos++) {
					int taken = from.valueAt(tpos);
					if (taken <= m) {
						// always ok
						// deleting the line for p will be ok
					} else {
						// always disabled
						// delete t as well
						todelTrans.add(from.keyAt(tpos));
						if (rt == ReductionType.LIVENESS) {
							throw new DeadlockFound();
						}
					}
				}
				if (untouchable.get(pid)) {
					continue;
				}
				prem.add(dropPlace(pid, tflowPT, tflowTP));
				if (DEBUG >= 2) cstP.add(pid);
				totalp++;
			} else if (from.size() == 0) {
				if (rt==ReductionType.STATESPACE || untouchable.get(pid)) {
					continue;
				}
				prem.add(dropPlace(pid, tflowPT, tflowTP));
				if (DEBUG >= 2) cstP.add(pid);
				totalp++;
			} 
		}
		if (totalp > 0) {
			// reconstruct updated flow matrices
			tflowPT.transposeTo(flowPT);
			tflowTP.transposeTo(flowTP);
		}
		if (DEBUG >= 2 && ! cstP.isEmpty()) {
			FlowPrinter.drawNet(sr2, "Constant places reduction"+ (withPreFire ? " with pre firing/single continuation ":"")+ prem, cstP, todelTrans);
			//FlowPrinter.drawNet(this, "Constant places reduction REAL RESULT"+ (withPreFire ? " with pre firing/single continuation ":"")+ prem, cstP, todelTrans);
		}
		int deltap = 0;
		if (rt != ReductionType.STATESPACE)
			deltap = ensureUnique(tflowPT, tflowTP, pnames, marks, true);
		totalp += deltap;
		if (deltap > 0) {
			// reconstruct updated flow matrices
			tflowPT.transposeTo(flowPT);
			tflowTP.transposeTo(flowTP);			
		}
	
		Set<Integer> toloop = new HashSet<>();
		Set<Integer> moredel = new HashSet<>();
		// Loop back rule is currently disabled in DEADLOCK, we need stronger conditions on other places fed by forward transition
		// find a place that has a single input
		for (int pid = 0, e=tflowPT.getColumnCount() ; rt==ReductionType.SAFETY && pid < e ; pid++ ) {
			// and is initially empty
			if (marks.get(pid) > 0) {
				continue;
			}
			// is not untouchable
			if (untouchable.get(pid)) {
				continue;
			}
			
			// a single feeding transition
			if (tflowTP.getColumn(pid).size()==1) {
				int feeder = tflowTP.getColumn(pid).keyAt(0);
				SparseIntArray oriPT = flowPT.getColumn(feeder);
				SparseIntArray oriTP = flowTP.getColumn(feeder);

				// look for it's inverse within the set of eaters from p
				SparseIntArray eaters = tflowPT.getColumn(pid);
				for (int i=0, ee=eaters.size();i < ee; i++) {
					int totry = eaters.keyAt(i);
					SparseIntArray ttPT = flowPT.getColumn(totry);
					SparseIntArray ttTP = flowTP.getColumn(totry);

					if (oriPT.equals(ttTP) && oriTP.equals(ttPT) ) {
						// Aha, we have a match ! destroy it in safety/dead mode							
						// moredel.add(totry);
						try {
							String tname = tnames.get(totry);
							//if (DEBUG >= 2) FlowPrinter.drawNet(this, "Reverse transition (loop back rule) examining "+tname+ " transition",Collections.emptySet(), Collections.singleton(totry));
							// extract all transitions to a PxP matrix
							IntMatrixCol graph = buildGraph(this, rt, totry);
							// the set of nodes that are "safe"
							Set<Integer> safeNodes = computeSafeNodes(this, rt, graph, untouchable);
							if (safeNodes.size() < pnames.size()) {
								// modifies safeNodes to add any prefix of them in the graph
								collectPrefix(safeNodes, graph, true);
							}
							// Now, make sure all outputs of t are actually irrelevant now
							boolean doit = true;
							for (int j=0, end=oriTP.size() ; j < end ; j++) {
								if (safeNodes.contains(oriTP.keyAt(j))) {
									doit = false;
									break;
								}
							}
							if (doit) {
								int dropped = dropIrrelevant(safeNodes);
								System.out.println("Remove reverse transitions (loop back) rule discarded transition " + tname + " and "+dropped + " places that fell out of Prefix Of Interest.");			
								//if (DEBUG >= 2) FlowPrinter.drawNet(this, "Reverse transition (loop back rule) discarding "+moredel.size()+ " transitions",Collections.emptySet(), moredel);
								return 1;
							}
						} catch (DeadlockFound e1) {
							// should never happen, rt is SAFETY only
							e1.printStackTrace();
						}
						// also replace if deadlock mode by a loop
						if (rt == ReductionType.DEADLOCKS) {			
							toloop.add(feeder);
						}
						break;
					}
				}
			}
		}

		if (!moredel.isEmpty()) {
			System.out.println("Remove reverse transitions rule discarded transitions " + moredel.stream().map(t -> tnames.get(t)).collect(Collectors.toList()));			
			if (DEBUG >= 2) FlowPrinter.drawNet(this, "Reverse transition (loop back rule) discarding "+moredel.size()+ " transitions",Collections.emptySet(), moredel);
			todelTrans.addAll(moredel);
		}
		if (! toloop.isEmpty()) {
			for (int feeder : toloop) {
				// before indexes get messed up
				flowPT.appendColumn(flowPT.getColumn(feeder).clone());
				flowTP.appendColumn(flowPT.getColumn(feeder).clone());
				tnames.add(tnames.get(feeder)+"rev");
			}
		}
		if (! todelTrans.isEmpty()) {
			// delete transitions
			for (int tid : todelTrans) {
				flowPT.deleteColumn(tid);
				flowTP.deleteColumn(tid);
				trem.add(tnames.remove(tid));				
			}
		}
		if (!prem.isEmpty() || !trem.isEmpty())
			System.out.println("Reduce places removed "+totalp + " places and " + todelTrans.size() + " transitions. " + (DEBUG>=1 ? ("Places : " + prem + " Transitions:" + trem):""));

		return totalp;
	}

	
	public List<Integer> computeConstants () {
		List<Integer> list = new ArrayList<>();
		// find constant marking places
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();

		Set<Integer> syphon =  SiphonComputer.computeEmptySyphon(flowPT,flowTP,marks);
		// now scan for isomorphic/redundant/useless/constant places
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {			
			SparseIntArray from = tflowPT.getColumn(pid);
			SparseIntArray to = tflowTP.getColumn(pid);

			if (isConstantPlace(pid, from, to, syphon)) {
				list.add(pid);
			}
		}
		if (DEBUG >=2) FlowPrinter.drawNet(this, "Simplifying constants used in the logic.",new HashSet<>(list),Collections.emptySet());
		return list;
	}

	public boolean isConstantPlace(int pid, SparseIntArray from, SparseIntArray to, Set<Integer> syphon) {
		return syphon.contains(pid) || from.equals(to)  || (to.size()==0 && marks.get(pid)==0) || hasNoTrueInputs(pid, from, to);
	}


	public boolean hasNoTrueInputs(int pid, SparseIntArray from, SparseIntArray to) {
		boolean noTrueInputs = false;
		if (marks.get(pid)==0) {
			noTrueInputs = true;
			for (int i=0; i < to.size() ; i++) {
				if (to.valueAt(i) > from.get(to.keyAt(i))) {
					noTrueInputs = false;
					break;
				}
			}
		}
		return noTrueInputs;
	}


	private String dropPlace(int pid, IntMatrixCol tflowPT, IntMatrixCol tflowTP) {
		// delete line for p
		tflowPT.deleteColumn(pid);
		tflowTP.deleteColumn(pid);
		String ret = pnames.remove(pid);
		removeAt(pid, untouchable);				
		// System.out.println("Removing "+pid+":"+remd);
		marks.remove(pid);
		if (keepImage) {
			image.remove(pid);
			removeAt(pid, tokeepImages);
		}
		return ret;
	}

	private int ruleImplicitPlace () {
		int totalp = 0;
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();		
		List<String> deleted = new ArrayList<>();
		Set<Integer> todel = new HashSet<>();
		// find a place
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
			if (untouchable.get(pid)) {
				continue;
			}
			SparseIntArray to = tflowTP.getColumn(pid);
			// single input, feeding a single token to us
			if (to.size() != 1 || to.valueAt(0)!=1) {
				continue;
			}
			int tfeedP = to.keyAt(0);
			SparseIntArray from = tflowPT.getColumn(pid);
			// single output, taking a single token to us
			if (from.size() != 1 || from.valueAt(0)!=1) {
				continue;
			}
			int teatP = from.keyAt(0);
			
			// our feeder is a fork transition.
			if (flowTP.getColumn(tfeedP).size() != 2) {
				continue;
			}
			// our eater is a join transition.
			if (flowPT.getColumn(teatP).size() != 2) {
				continue;
			}
			int other=-1;
			SparseIntArray eatPT = flowPT.getColumn(teatP);
			for (int i =0 ; i < eatPT.size() ; i++) {
				int k = eatPT.keyAt(i);
				if (k == pid) {
					continue;
				} else {
					if (eatPT.valueAt(i)==1)
						other = k;
					break;
				}
			}
			if (other ==-1) {
				continue;
			}
			if (todel.contains(other)) {
				continue;
			}
			BitSet seen = new BitSet();
			// so other is controlling eatP, see if it is implied by feedP
			if (inducedBy(other,tfeedP,tflowPT,tflowTP,seen,5))  {
				// Hurray ! P is implicit !
				todel.add(pid);
				totalp++;
			}
		}
		if (totalp >0) {
			dropPlaces(new ArrayList<>(todel), false, "Implicit places test (without SMT)");

			System.out.println("Implicit places reduction removed "+totalp+" places "+ (DEBUG >=1 ? (" : "+ deleted ) : ""));
			if (DEBUG==2) FlowPrinter.drawNet(this, "After Implicit Reduction of "+totalp + " places.");
		}
		return totalp;
	}
	public void dropTransitions (List<Integer> todrop,String rule) {
		dropTransitions(todrop, true,rule);
	}
	public void dropTransitions (List<Integer> todrop, boolean trace, String rule) {
		List<String> deleted = new ArrayList<>();
		todrop.sort((a,b) -> - a.compareTo(b));
		if (DEBUG==2 && !todrop.isEmpty() && trace) {
			FlowPrinter.drawNet(this, "Discarding "+todrop.size()+" transitions with rule "+rule,Collections.emptySet(),new HashSet<>(todrop));
		}
		for (int tid : todrop) {
			flowPT.deleteColumn(tid);
			flowTP.deleteColumn(tid);
			deleted.add(tnames.remove(tid));
		}
		int totalp = deleted.size();
		if (totalp >0 && trace) {
			System.out.println("Drop transitions removed "+totalp+" transitions "+ (DEBUG >=1 ? (" : "+ deleted ) : ""));			
		}
	}
	
	public void dropPlaces (List<Integer> todrop, boolean andOutputs, String rule) {
		dropPlaces(todrop, andOutputs, true, rule);
	}
	
	public void dropPlaces (List<Integer> todrop, boolean andOutputs, boolean trace, String rule) {
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();	
		List<String> deleted = new ArrayList<>();
		Set<Integer> toremT = new HashSet<>();
		todrop.sort(Integer::compare);
		
		if (DEBUG==2 && trace) {			
			Set<Integer> trans = new HashSet<>();
			if (andOutputs) {
				for (int i = todrop.size() - 1 ; i >= 0; i--) {
					int pid= todrop.get(i);
					if (andOutputs) {
						SparseIntArray outs = tflowPT.getColumn(pid);
						for (int j=0; j < outs.size(); j++) {
							trans.add(outs.keyAt(j));
						}
					}
				}
			}
			FlowPrinter.drawNet(this,"Discarding "+todrop.size()+" places with rule "+rule, new HashSet<>(todrop), trans);
		}
		for (int i = todrop.size() - 1 ; i >= 0; i--) {
			int pid= todrop.get(i);
			if (andOutputs) {
				SparseIntArray outs = tflowPT.getColumn(pid);
				for (int j=0; j < outs.size(); j++) {
					toremT.add(outs.keyAt(j));
				}
			}
			deleted.add(dropPlace(pid, tflowPT, tflowTP));
		}
		tflowPT.transposeTo(flowPT);
		tflowTP.transposeTo(flowTP);
		int totalp = deleted.size();
		if (totalp >0 && trace) {
			System.out.println("Discarding "+ totalp+ " places :"+ (DEBUG >=1 ? (" : "+ deleted ) : ""));			
		}
		if (andOutputs) {
			List<Integer> kt = new ArrayList<>(toremT);
			// remove transitions that would now be "free"
			kt.removeIf(tid -> flowPT.getColumn(tid).size()!=0 || flowPT.getColumn(tid).size()!=0); 
			if (trace) System.out.println("Also discarding "+kt.size()+" output transitions "+ (DEBUG >=1 ? (" : "+ kt ) : ""));
			dropTransitions(kt,"Output transitions of discarded places.");
		}
	}
	
	private void removeAt(int pid, BitSet bs) {
		bs.clear(pid);
		for (int i = bs.nextSetBit(pid); i >= 0; i = bs.nextSetBit(i+1)) {
		    bs.set(i-1);
		    bs.clear(i);
			// operate on index i here
		     if (i == Integer.MAX_VALUE) {
		         break; // or (i+1) would overflow
		     }
		 }
	}


	private boolean inducedBy(int pid, int tcause, IntMatrixCol tflowPT, IntMatrixCol tflowTP, BitSet seen, int depth) {
		if (depth ==0) {
			return false;
		}
		if (marks.get(pid)!=0) {
			return false;
		}
		if (seen.get(pid)) {
			return false;
		}
		BitSet newseen = (BitSet) seen.clone();
		newseen.set(pid);
		// Test if tcause is through a chain causing pid
		SparseIntArray to = tflowTP.getColumn(pid);
		// single input, feeding a single token to us
		if (to.size() != 1 || to.valueAt(0)!=1) {
			return false;
		}
		int tfeedP = to.keyAt(0);
		if (tfeedP == tcause) {
			return true;
		}
		// recurse to find one predecessor that works
		SparseIntArray inputFeed = flowPT.getColumn(tfeedP);
		for (int i=0 ; i < inputFeed.size() ; i++) {
			int predid = inputFeed.keyAt(i);
			if (inputFeed.valueAt(i)!=1) {
				continue;
			}			
			if (inducedBy(predid, tcause, tflowPT, tflowTP,newseen, depth-1)) {
				return true;
			}
		}
		
		return false;
	}
	private int ruleTrivialPostAgglo(ReductionType rt) {
		if (rt == ReductionType.LTL || keepImage) {
			return 0;
		}
		int total = 0;
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();
		int initt = tnames.size();
		long time = System.currentTimeMillis();
		List<Integer> todel = new ArrayList<>();
		for (int pid = 0 ; pid < pnames.size() ; pid++) {
			if (untouchable.get(pid)) {
				continue;
			}
			SparseIntArray fcand = tflowPT.getColumn(pid);
			SparseIntArray hcand = tflowTP.getColumn(pid);
			if (fcand.size() == 0 || hcand.size() == 0) {
				continue;
			}
			// refuse to expand anything that is not 1 to 1
			if (fcand.size() > 1 || hcand.size() > 1) {			
				continue;
			}
			// refuse to do anything other than trivial
			if (flowTP.getColumn(hcand.keyAt(0)).size() > 1) {
				continue;
			}

			// is marked strategy relies on a single output to be triggered
			boolean isMarked = marks.get(pid) != 0 ; 
			if (isMarked) {
				continue;
			}
			if (fcand.valueAt(0) != 1 || hcand.valueAt(0) != 1) {
				continue;
			}
						
			List<Integer> Hids = new ArrayList<>();
			List<Integer> Fids = new ArrayList<>();
			
			int fid = fcand.keyAt(0);
			SparseIntArray fPT = flowPT.getColumn(fid);				
			if (fPT.size() > 1) {
				// a transition controlled also by someone else than P
				continue;
			}
			Fids.add(fid);
			
			int hid = hcand.keyAt(0);
			if (hid == fid) {
				// Make sure no transition is both input and output for p
				continue;
			}
			Hids.add(hid);
			
			
			if (touches(Hids) || touches(Fids))
				continue;

			if (DEBUG>=1) System.out.println("Net is trivially Post-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);
			
			// substitute output of h by output of f
			SparseIntArray fTP = flowTP.getColumn(fid);
			flowTP.setColumn(hid, fTP);
			// clear f
			flowPT.setColumn(fid, new SparseIntArray());
			flowTP.setColumn(fid, new SparseIntArray());			
			todel.add(fid);
			tnames.set(hid, tnames.get(hid) + "." + tnames.get(fid));
			for (int j=0, je=fTP.size() ; j < je ; j++ ) {
				int pfed = fTP.keyAt(j);
				int val = tflowTP.getColumn(pfed).get(fid);
				tflowTP.getColumn(pfed).put(fid,0);
				tflowTP.getColumn(pfed).put(hid,val);
			}
			
			if (DEBUG>=1) System.out.println("Built transition "+tnames.get(hid) +" pre:" + flowPT.getColumn(hid) +" post:" + flowTP.getColumn(hid));
			total++;
			
			long deltat = System.currentTimeMillis() - time;
			if (deltat >= 30000) {
				System.out.println("Performed "+total + " Post agglomeration using trivial condition.");
				time = System.currentTimeMillis();
			}
			
		}
		if (! todel.isEmpty()) {
			dropTransitions(todel,"Trivial Post-Agglo cleanup.");
			System.out.println("Trivial Post-agglo rules discarded "+todel.size()+ " transitions");
		}
		
		if (total != 0) {
			System.out.println("Performed "+total + " trivial Post agglomeration. Transition count delta: " + (initt -tnames.size()));
		}
		
		return total;
		
	}
	private int rulePostAgglo(boolean doComplex, boolean doSimple, ReductionType rt) {
		if (rt == ReductionType.LTL) {
			return 0;
		}
		int total = 0;
		int red = 0;
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();
		int initt = tnames.size();
		long time = System.currentTimeMillis();
		for (int pid = 0 ; pid < pnames.size() ; pid++) {
			if (untouchable.get(pid)) {
				continue;
			}
			SparseIntArray fcand = tflowPT.getColumn(pid);
			SparseIntArray hcand = tflowTP.getColumn(pid);
			if (fcand.size() == 0 || hcand.size() == 0) {
				continue;
			}
			// refuse to expand anything that is not 1 to 1
			if (doSimple && (fcand.size() > 1 || hcand.size() > 1)) {			
				continue;
			}
			// refuse to do anything other than trivial
			if (doSimple && flowTP.getColumn(hcand.keyAt(0)).size() > 1) {
				continue;
			}

			// refuse to expand cross-products, it grows number of transitions
			if (! doComplex && (fcand.size() > 1 && hcand.size() > 1)) {			
				continue;
			}
			
			// is marked strategy relies on a single output to be triggered
			boolean isMarked = marks.get(pid) != 0 ; 
			if (isMarked && fcand.size() > 1) {
				continue;
			}			
			if (isMarked && !doComplex) {
				continue;
			}
			// for the case we need to empty stuff/several F for one H cards need to concord
			List<Integer> seenFrom = new ArrayList<>();
			List<Integer> seenTo = new ArrayList<>();
			boolean checkWeights = false;
			
			List<Integer> Hids = new ArrayList<>();
			List<Integer> Fids = new ArrayList<>();
			
			
			boolean ok =true;
			Set<Integer> testSet = new HashSet<>();
			for (int fi=0; fi < fcand.size() ; fi++) {
				int fid = fcand.keyAt(fi);
				SparseIntArray fPT = flowPT.getColumn(fid);				
				if (fPT.size() > 1) {
					// a transition controlled also by someone else than P
					ok = false;
					break;
				}

				int val = fcand.valueAt(fi);
				if (val > 1) {
					checkWeights = true;
				}
				if (marks.get(pid) % val != 0) {
					ok =false;
					break;
				}
				seenFrom.add(val);
				Fids.add(fid);
			}
			if (!ok) continue;
			
			for (int hi=0; hi < hcand.size() ; hi++) {
				int hid = hcand.keyAt(hi);
				// Make sure no transition is both input and output for p
				SparseIntArray hPT = flowPT.getColumn(hid);				
				if (hPT.get(pid)!=0) {
					ok = false;
					break;
				}
				// make sure we don't touch the test set
				if (!testSet.isEmpty()) {
					for (int i=0,e=hPT.size() ; i < e ; i++) {
						if (testSet.contains(hPT.keyAt(i))) {
							ok = false;
							break;
						}
					}
					if (ok) {
						SparseIntArray hTP = flowTP.getColumn(hid);
						for (int i=0,e=hTP.size() ; i < e ; i++) {
							if (testSet.contains(hTP.keyAt(i))) {
								ok = false;
								break;
							}
						}						
					}
				}
				if (!ok) break;
				
				int val = hcand.valueAt(hi);
				if (val > 1) {
					checkWeights = true;
				}
				seenTo.add(val);
				Hids.add(hid);
				
			}
			if (!ok) continue;
			
			// check token cardinalities :
			if (checkWeights)
				for (int feeder : seenTo) {
					for (int cons : seenFrom) {
						if (feeder % cons != 0 || cons > feeder) {
							ok = false;
							break;
						}
						if (feeder > cons && seenFrom.size() > 1) {
							// we could decide to send some tokens left and some right
							ok = false;
							break;
						}
					}
					if (!ok) {
						break;
					}
				}
			if (!ok) {
				continue;
			}
			// avoid degenerating, e.g DLC Shifumi could go nasty here
			if (Hids.size()!=1 && Fids.size()!=1 && Hids.size() * Fids.size() >= 32) {
				continue;
			}
			if (! untouchable.isEmpty()) {
				ok = checkProtection(Hids, Fids);
				if (!ok) {
					continue;
				}
				if (isMarked) {
					// fire the single F continuation until the place is empty
					// unless this messes with any place of interest
					int fid = fcand.keyAt(0);
					SparseIntArray tp = flowTP.getColumn(fid);
					for (int i=0, e=tp.size() ; i < e ; i++) {
						if (untouchable.get(tp.keyAt(i))) {
							ok = false;
							break;
						}
					}
				}
			}
			if (!ok) {
				continue;
			}
			if (rt==ReductionType.SAFETY &&  ! untouchable.isEmpty() && touches(Fids)) {
				for (int h : Hids) {
					if (SparseIntArray.sumProd(1, flowPT.getColumn(h), -1, flowTP.getColumn(h)).size()>1) {
						ok = false;
						break;
					}
				}
				if (!ok)
					continue;				
			} else if (rt==ReductionType.SI_LTL && touches(Fids)) {
				continue;
			}

			if (DEBUG>=1) System.out.println("Net is Post-agglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);
			
			if (isMarked) {
				// fire the single F continuation until the place is empty
				int fid = fcand.keyAt(0);

				emptyPlaceWithTransition(pid, fid);
				// System.out.println("Pushed tokens out of "+pnames.get(pid));
			}

			red += agglomerateAround(pid, Hids, Fids,"Post",tflowPT,tflowTP);
			total++;
			if (doComplex && total > 100) break;
			
			long deltat = System.currentTimeMillis() - time;
			if (deltat >= 30000) {
				System.out.println("Performed "+total + " Post agglomeration using F-continuation condition.");
				time = System.currentTimeMillis();
			}
			
		}
		
		
		if (total != 0) {
			System.out.println("Performed "+total + " Post agglomeration using F-continuation condition"+
					(red>0?" with reduction of "+red+" identical transitions." : "." + "Transition count delta: " + (initt -tnames.size())));
		}
		
		return total;
	}


	private boolean checkProtection(List<Integer> Hids, List<Integer> Fids) {
		// make sure not h and f both touching places
		// or h.f could hide non stuttering behavior		
		 return !(touches(Hids) && touches(Fids));
	}

	private boolean touches(SparseIntArray hcand) {
		if (untouchable.isEmpty())
			return false;
		for (int i=0, e=hcand.size(); i<e; i++) {
			int h = hcand.keyAt(i);
			if (touches(h))
				return true;
		}
		return false;
	}
	/**
	 * Returns true if one of the transitions is touching at least one of "untouchable" places.  
	 * @param Hids
	 */
	private boolean touches(List<Integer> Hids) {
		if (untouchable.isEmpty())
			return false;
		for (int h : Hids) {
			if (touches(h))
				return true;
		}
		return false;
	}

	private static boolean touches(int h, ISparsePetriNet pn, BitSet untouchable) {
		SparseIntArray col = pn.getFlowPT().getColumn(h);
		for (int i=0; i < col.size() ; i++) {
			if (untouchable.get(col.keyAt(i))) {
				if (col.valueAt(i) != pn.getFlowTP().getColumn(h).get(col.keyAt(i)))
					return true;					
			}
		}
		col = pn.getFlowTP().getColumn(h);
		for (int i=0; i < col.size() ; i++) {
			if (untouchable.get(col.keyAt(i))) {
				if (col.valueAt(i) != pn.getFlowPT().getColumn(h).get(col.keyAt(i)))
					return true;
			}
		}
		return false;
		
	}
	
	private boolean touches(int h) {
		return touches(h, this, untouchable);
	}

	private void emptyPlaceWithTransition(int pid, int fid) {
		SparseIntArray preF = flowPT.getColumn(fid);
		SparseIntArray postF = flowTP.getColumn(fid);
		while (marks.get(pid) > 0) {
			for (int ip = 0 ; ip < preF.size() ; ip++) {
				int p = preF.keyAt(ip);
				int v = preF.valueAt(ip);
				marks.set(p, marks.get(p)- v);
				image.set(p, Expression.op(Op.MINUS, image.get(p), Expression.constant(v)));
			}						
			for (int ip = 0 ; ip < postF.size() ; ip++) {
				int p = postF.keyAt(ip);
				int v = postF.valueAt(ip);
				marks.set(p, marks.get(p)+ v);
				image.set(p, Expression.op(Op.ADD, image.get(p), Expression.constant(v)));
			}
		}
	}

	private int agglomerateAround(int pid, List<Integer> Hids, List<Integer> Fids, String type, IntMatrixCol tflowPT, IntMatrixCol tflowTP) {
		List<SparseIntArray> HsPT = new ArrayList<>();
		List<SparseIntArray> HsTP = new ArrayList<>();
		List<String> Hnames = new ArrayList<>();
		if (DEBUG >= 2) {
			Set<Integer> hf = new HashSet<>(Hids);
			hf.addAll(Fids);
			FlowPrinter.drawNet(this, type+"-Agglomerating place :" + pnames.get(pid), Collections.singleton(pid), hf );
		}
		for (int i : Hids) {
			HsPT.add(flowPT.getColumn(i));
			HsTP.add(flowTP.getColumn(i));
			Hnames.add(tnames.get(i));
		}
		List<SparseIntArray> FsPT = new ArrayList<>();
		List<SparseIntArray> FsTP = new ArrayList<>();
		List<String> Fnames = new ArrayList<>();
		for (int i : Fids) {
			FsPT.add(flowPT.getColumn(i));
			FsTP.add(flowTP.getColumn(i));
			Fnames.add(tnames.get(i));
		}
		List<Integer> todel = new ArrayList<>(Hids);
		if (!keepImage)
			todel.addAll(Fids);
		else
			tokeepImages.set(pid);
		
		todel.sort( (x,y) -> -Integer.compare(x,y));
		
		if (tflowPT != null) {
			tflowPT.deleteRows(todel);
		}
		if (tflowTP != null) {
			tflowTP.deleteRows(todel);
		}
		for (int i : todel) {
			if (DEBUG>=1) System.out.println("removing transition "+tnames.get(i) +" pre:" + flowPT.getColumn(i) +" post:" + flowTP.getColumn(i));
			flowPT.deleteColumn(i);
			flowTP.deleteColumn(i);
			tnames.remove(i);
		}
		IntMatrixCol toaddmatPT = new IntMatrixCol(flowPT.getRowCount(), 0);
		IntMatrixCol toaddmatTP = new IntMatrixCol(flowPT.getRowCount(), 0);
		List<String> tnamesadd = new ArrayList<>();
		// Now add resulting columns
		for (int hi=0; hi < Hids.size() ; hi++) {
			int hiv = HsTP.get(hi).get(pid);
			for (int fi=0; fi < Fids.size() ; fi++) {
				int fiv = FsPT.get(fi).get(pid);
				int nbocc = hiv / fiv;
								
				SparseIntArray resPT = SparseIntArray.sumProd(1, HsPT.get(hi), nbocc, FsPT.get(fi), pid);
				toaddmatPT.appendColumn(resPT);
				
				
				SparseIntArray resTP = SparseIntArray.sumProd(1, HsTP.get(hi).clone(), nbocc, FsTP.get(fi),pid);				
				toaddmatTP.appendColumn(resTP);

				String tname = Hnames.get(hi)+"."+Fnames.get(fi);
				tnamesadd.add(tname );	
			}
		}
		int red = ensureUnique(toaddmatPT, toaddmatTP, tnamesadd, null, false);
		tnames.addAll(tnamesadd);
		for (int i = 0; i < toaddmatPT.getColumnCount() ; i++) {
			int tid = flowPT.getColumnCount();
			SparseIntArray col = toaddmatPT.getColumn(i);			
			if (tflowPT != null) {
				for (int k=0; k < col.size() ; k++) {
					int ppid = col.keyAt(k);
					SparseIntArray row = tflowPT.getColumn(ppid);
					row.append(tid, col.valueAt(k));
				}
				tflowPT.addRow();
			}
			flowPT.appendColumn(col);			
			col = toaddmatTP.getColumn(i);			
			if (tflowTP != null) {
				for (int k=0; k < col.size() ; k++) {
					int ppid = col.keyAt(k);
					SparseIntArray row = tflowTP.getColumn(ppid);
					row.append(tid, col.valueAt(k));
				}
				tflowTP.addRow();
			}
			flowTP.appendColumn(col);
			if (DEBUG>=1) System.out.println("added transition "+tnamesadd.get(i) +" pre:" + toaddmatPT.getColumn(i) +" post:" + toaddmatTP.getColumn(i));
		}
		if (DEBUG >= 3) {
			if (tflowPT != null) {
				IntMatrixCol control = flowPT.transpose();
				if (! control.equals(tflowPT)) {
					for (int i=0; i < control.getColumnCount() ; i++) {
						if (!control.getColumn(i).equals(tflowPT.getColumn(i))) {
							System.out.println("Control :"+control.getColumn(i) + " real :"+tflowPT.getColumn(i));
						}
					}
				}
			}
			if (tflowTP != null) {
				IntMatrixCol control = flowTP.transpose();
				if (! control.equals(tflowTP)) {
					for (int i=0; i < control.getColumnCount() ; i++) {
						if (!control.getColumn(i).equals(tflowTP.getColumn(i))) {
							System.out.println("Cont :"+control.getColumn(i) + "\nReal :"+tflowTP.getColumn(i));
						}
					}
				}
			}
		}
		return red;
	}
	
	private int rulePreAgglo(boolean doComplex, ReductionType rt) {
		if (rt == ReductionType.LTL) {
			return 0;
		}
		int total = 0;
		int red = 0;
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();
		for (int pid = 0 ; pid < pnames.size() ; pid++) {
			if (untouchable.get(pid)) {
				continue;
			}
			List<Integer> Fids = new ArrayList<>();
			List<Integer> Hids = new ArrayList<>();
			if (marks.get(pid) != 0) {
				continue;
			}
			Set<Integer> touching = new HashSet<>();
			{
				SparseIntArray col = tflowPT.getColumn(pid);
				for (int i=0, e=col.size() ; i < e ; i++) {
					touching.add(col.keyAt(i));
				}
				col = tflowTP.getColumn(pid);
				for (int i=0, e=col.size() ; i < e ; i++) {
					touching.add(col.keyAt(i));
				}
			}
			boolean ok = true;
			for (int tid: touching) {
				int consumesFromP = flowPT.getColumn(tid).get(pid);
				int feedsIntoP = flowTP.getColumn(tid).get(pid);
				if (consumesFromP == 0 && feedsIntoP == 0) {
					// t has no connection to p
					continue;
				} else if (consumesFromP!=0 && feedsIntoP!=0) {
					// loops on p suck : can't agglomerate p
					ok = false;
					break;
				} else if (consumesFromP > 1 || feedsIntoP > 1) {
					// arc weights mess structural hypothesis up
					ok = false;
					break;
				} else if (consumesFromP != 0) {
					// ok we have an F candidate
					Fids.add(tid);
					// we want H or F be a singleton, to ease HF-interchangeability
					if (!doComplex && Hids.size()>1 && Fids.size()>1) {
						ok = false;
						break;
					}
					continue;
				} else {
					// we are a feeder into P
					Hids.add(tid);
					// we want H be a singleton, to ease HF-interchangeability
					// we want H or F be a singleton, to ease HF-interchangeability
					if (!doComplex && Hids.size()>1 && Fids.size()>1) {
						ok = false;
						break;
					}
					// we want h to not trigger anything else than f.
					if (flowTP.getColumn(tid).size() > 1) {
						ok = false;
						break;
					}
					if (! isDivergentFree(tid)) {
						ok = false;
						break;
					} 
					if (! isStronglyQuasiPersistent(tid,tflowPT,rt)) {
						ok = false;
						break;			
					}
				}
			}
			if (!ok) {
				continue;
			}
			if (Fids.isEmpty() || Hids.isEmpty()) {
				// empty
				continue;
			}
			// we want H or F be a singleton, to ease HF-interchangeability
			if (!doComplex && Hids.size()>1 && Fids.size()>1) {
				continue;
			}
			if (Hids.stream().anyMatch(h-> Fids.contains(h))) {
				continue;
			}
			if (! untouchable.isEmpty()) {
				ok = ! touches(Hids);
			}
			if (!ok) {
				continue;
			} else {
				if (DEBUG>=1) System.out.println("Net is Pre-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);
				
				red += agglomerateAround(pid, Hids, Fids,"Pre",tflowPT,tflowTP);
				total++;
			}
			
		}
		
		if (total != 0) {
			System.out.println("Performed "+total + (doComplex?"(complex)":"") +" Pre agglomeration using Quasi-Persistent + Divergent Free condition."+
					(red>0?" with reduction of "+red+" identical transitions." : "."));
		}
		
		return total;
	}

	
	/**
	 * Return true iff. sa is equal to sb on all entries except that sa[i]=sb[j] and sa[j]=sb[i] and sa[j] is 0.
	 * @param sa first array
	 * @param sb second 
	 * @param indi
	 * @param indj
	 * @return
	 */
	private static boolean equalUptoPerm (SparseIntArray sa, SparseIntArray sb, int indi, int indj) {
		if (sa.size() != sb.size()) 
			return false;
		if (sa.size() == 0) {
			return true;
		}
		int seenA = -1;
		int seenB = -1;
		int j=0 ;
		for (int i=0; i < sa.size() && j < sb.size(); ) {
			
				int ka = sa.keyAt(i);
				int va = sa.valueAt(i);
				int kb = sb.keyAt(j);
				int vb = sb.valueAt(j);
							
				if (ka == kb) {
					// both columns are non zero in this entry ?
					if (ka == indi || ka == indj) {
						return false;
					}
					if (va != vb) {
						return false;
					}
					i++;
					j++;
					continue;
				} else if (ka == indi &&  kb == indj) {
					if (va != vb) {
						return false;
					} else {
						seenA = va;
						seenB = vb;
					}
					i++;
					j++;
				} else if (ka < kb) {
					// mismatch
					if (ka == indi) {
						if (seenA==-1) {
							seenA = va;
						} 
						if (seenB != -1 && seenB != va) {
							return false;
						}
					} else {
						return false;
					}
					i++;
					continue;
				} else if (kb < ka) {
					// mismatch
					if (kb == indj) {
						if (seenB==-1) {
							seenB = vb;
						} 
						if (seenA!=-1 && seenA != vb) {
							return false;
						}
					} else {
						return false;
					}
					j++;
					continue;
				}
		}
		return seenA == seenB ;
	}
	
	private int ruleFusePlaceByFuture(ReductionType rt) {
		IntMatrixCol tflowPT = flowPT.transpose();
		List<Integer> ints = new ArrayList<>(tflowPT.getColumnCount());
		for (int i=0 ; i < tflowPT.getColumnCount() ; i++ ) {
			if (untouchable.get(i)) {
				continue;
			}
			ints.add(i);
		}
		Map<Integer,List<Integer>> byNbOutputs = ints.stream().collect(Collectors.groupingBy(a -> tflowPT.getColumn(a).size()));
		
		Map<Integer,Integer> toFuse = new HashMap<>();
		
		for (Entry<Integer, List<Integer>> ent : byNbOutputs.entrySet()) {
			int nbt = ent.getKey();
			List<Integer> list = ent.getValue();
			if (list.size() >= 10000) {
				// we can't afford this 10^8 loops
				continue;
			}
			for (int i = 0; i < list.size() ; i++) {				
				int pi = list.get(i);
				if (untouchable.get(pi)) {
					continue;
				}
				if (toFuse.containsKey(pi)) 
					continue;
				SparseIntArray piouts = tflowPT.getColumn(pi);
				
				boolean tokeepi = false;
				if (keepImage)
					tokeepi=tokeepImages.get(pi);
				for (int j = i+ 1 ; j < list.size() ; j++ ) {					
					int pj = list.get(j);
					if (untouchable.get(pj)) {
						continue;
					}
					if (rt == ReductionType.LIVENESS && (marks.get(pi) != 0 || marks.get(pj) != 0)) {
						continue;
					}
					if (toFuse.containsKey(pj)) 
						continue;
					if (keepImage && tokeepi !=tokeepImages.get(pj)) {
						continue;
					}
					SparseIntArray pjouts = tflowPT.getColumn(pj);
					// so, pi and pj have the same number of outputs
					int ti = 0;
					BitSet matchedj = new BitSet();
					for ( ; ti  < piouts.size() ; ti++) {
						int indti = piouts.keyAt(ti);
						SparseIntArray tiin = flowPT.getColumn(indti);
						SparseIntArray tiout = flowTP.getColumn(indti);
						
						if (tiout.size() == 0 && rt == ReductionType.LIVENESS) {
							break;
						}
						boolean foundmatch = false;
						for (int tj = 0; tj  < pjouts.size() ; tj++) {
							
							int indtj = pjouts.keyAt(tj);
							
							// TODO : possibly this test could be removed, if the "equal up to perm" criterion matches, 
							// in the result we must now take two tokens from the fused place however.
							if (indti == indtj) {
								break;
							}
							if (matchedj.get(tj)) {
								continue;
							}
							SparseIntArray tjin = flowPT.getColumn(indtj);
							SparseIntArray tjout = flowTP.getColumn(indtj);
							if (rt != ReductionType.LIVENESS && ! equalUptoPerm(tiout, tjout, pi, pj)) {
								continue;
							} else if (rt == ReductionType.LIVENESS && ! tiout.equals(tjout)) {
								// self loops are dangerous in Liveness scenario
								continue;
							}
							if (equalUptoPerm(tiin, tjin, pi, pj)) {
								foundmatch = true;
								matchedj.set(tj);
								break;
							}
						}
						if (!foundmatch) {
							break ;
						}
					}
					if (ti != piouts.size()) {
						continue;
					}
					
					// We have a hit : Pj and Pi are future equivalent.
					// update all predecessors of pj to feed into pi
					// tflowPT is unimpacted; we update flowTP only.
					toFuse.put(pj, pi);
				}				
			}
		}
		
		if (!toFuse.isEmpty()) {
			isSafe = false;
			List<Integer> todelp = new ArrayList<>();
			Set<Integer> todel = new TreeSet<>((x,y)->-Integer.compare(x, y));
			// now work with the tflowTP to find transitions feeding pj we need to update
			IntMatrixCol tflowTP = flowTP.transpose();
			
			for (Entry<Integer, Integer> ent : toFuse.entrySet()) {
				int pj = ent.getKey();
				int pi = ent.getValue();

				if (DEBUG >=2) {
					Set<Integer> ps = new HashSet<>(); ps.add(pi); ps.add(pj);
					Set<Integer> ts = new HashSet<>(); 
					addCol(ts,tflowPT.getColumn(pi));addCol(ts,tflowPT.getColumn(pj));
					addCol(ts,tflowTP.getColumn(pi));addCol(ts,tflowTP.getColumn(pj));					
					FlowPrinter.drawNet(this, "Symmetric choice/Future Equivalent : fusing "+pnames.get(pj) + " into "+ pnames.get(pi), ps, ts);
				}

				
				if (DEBUG>=1) System.out.println("Fusing place "+pnames.get(pj) +" id " + pj + "=" + marks.get(pj) +" pre:" + tflowTP.getColumn(pj) +" post:" + tflowPT.getColumn(pj));
				if (DEBUG>=2) for (int i=0 ; i < tflowPT.getColumn(pj).size() ; i++) {
					int t = tflowPT.getColumn(pj).keyAt(i);
					System.out.println("transition "+tnames.get(t) +" id " + t +" pre:" + flowPT.getColumn(t) +" post:" + flowTP.getColumn(t));
				}
				if (DEBUG>=1) System.out.println("Into place "+pnames.get(pi) +" id " + pi+ "=" + marks.get(pi) +" pre:" + tflowTP.getColumn(pi) +" post:" + tflowPT.getColumn(pi));
				if (DEBUG>=2) for (int i=0 ; i < tflowPT.getColumn(pi).size() ; i++) {
					int t = tflowPT.getColumn(pi).keyAt(i);
					System.out.println("transition "+tnames.get(t) +" id " + t +" pre:" + flowPT.getColumn(t) +" post:" + flowTP.getColumn(t));
				}
				SparseIntArray jin = tflowTP.getColumn(pj);
				for (int i =0 ; i < jin.size() ; i++) {
					int tid = jin.keyAt(i);
					
					SparseIntArray tjouts = flowTP.getColumn(tid);
					int v = tjouts.get(pj);
					tjouts.delete(pj);
					tjouts.put(pi,tjouts.get(pi)+v);					
				}
				marks.set(pi, marks.get(pi)+marks.get(pj));
				marks.set(pj, 0);
				image.set(pi, Expression.op(Op.ADD, image.get(pi), image.get(pj)));				
				todelp.add(pj);
			}

			
			if (! todelp.isEmpty()) {
				for (int pid : todelp) {
					SparseIntArray tpt = tflowPT.getColumn(pid);
					for (int i=0;i<tpt.size();i++) {
						todel.add(tpt.keyAt(i));
					}
				}
				dropPlaces(todelp, false, "Symmetric choice cleanup.");
			}
			if (!todel.isEmpty()) {
				dropTransitions(new ArrayList<>(todel), false, "Symmetric choice");
			}
			
		}
		return toFuse.size();
	}
	
	private void addCol(Set<Integer> ts, SparseIntArray column) {
		for (int i=0,e=column.size() ; i < e ; i++) {
			ts.add(column.keyAt(i));
		}
	}


	private int ruleSymmetricChoice() {
		IntMatrixCol tflowPT = flowPT.transpose();
		Set<Integer> todel = new TreeSet<>((x,y)->-Integer.compare(x, y));
		for (int pid = 0 ; pid < pnames.size() ; pid++) {
			// p can choose between two outputs, tidi or tidj 
			// tidj and tidi differ by a single output place pouti or poutj
			// pouti and poutj have the same connections, they are symmetric of one another.
			SparseIntArray pout = tflowPT.getColumn(pid);
			for (int iti = 0 ; iti < pout.size() ; iti++ ) {
				int tidi = pout.keyAt(iti);
				if (todel.contains(tidi)) 
					continue;
				for (int itj = iti+1 ; itj < pout.size() ; itj++ ) {
					int tidj = pout.keyAt(itj);
					if (todel.contains(tidj)) 
						continue;
					// tidi and tidj have exactly the same inputs
					SparseIntArray inti = flowPT.getColumn(tidi);
					SparseIntArray intj = flowPT.getColumn(tidj);
					if (! inti.equals(intj)) {
						continue;
					} else {
						// find the single differing output pouti for ti and poutj for tj
						SparseIntArray outi = flowTP.getColumn(tidi);
						SparseIntArray outj = flowTP.getColumn(tidj);
						if (outi.size() != outj.size()) {
							continue;
						}
						int indpi = -1;
						int indpj = -1;
						for (int i=0, j=0; i < outi.size() && j < outj.size(); ) {							
							int pi = outi.keyAt(i);
							int vi = outi.valueAt(i);
							int pj = outj.keyAt(j);
							int vj = outj.valueAt(j);
							if (pi==pj && vi == vj) {
								// equal, fine
								i++;
								j++;
							} else if (pi < pj) {
								// found inserted element of pi
								if (indpi != -1) {
									// value difference or second difference 
									indpi = -1;
									break;
								} else {
									indpi = i;
								}
								i++;
							} else if (pj < pi) {
								// found inserted element of pj
								if (indpj != -1) {
									// value difference or second difference 
									indpj = -1;
									break;
								} else {
									indpj = j;
								}
								j++;
							} else {
								// pi==pj but vi != vj
								indpi = -1;
								indpj = -1;
								break;
							}
							if (i == outi.size() && j == outj.size()- 1) {
								// ok
								if (indpj == -1) {
									indpj = j;
								} else {
									indpj = -1;
									break;
								}
							}
							if (i == outi.size()-1 && j == outj.size()) {
								// ok
								if (indpi == -1) {
									indpi = i;
								} else {
									indpi = -1;
									break;
								}
							}
						}
						if (indpi == -1 || indpj == -1) {
							continue;
						}
						int vi = outi.valueAt(indpi);							
						int vj = outj.valueAt(indpj);
						int pouti = outi.keyAt(indpi);
						int poutj = outj.keyAt(indpj);
						
						if (vi != vj) {
							continue;
						}
						
						// so, we now have valid pouti/poutj indexes
						// make sure these places are symmetric of one another.
						//System.out.println("pi " +pouti +":" + pnames.get(pouti) + " pj "+ poutj +":" + pnames.get(poutj) );
						// require single output transition for pi/pj to ease comparisons (restriction avoids cartesian product comparisons)
						SparseIntArray tpouti = tflowPT.getColumn(pouti);
						SparseIntArray tpoutj = tflowPT.getColumn(poutj);
						if (tpouti.size() == 1 && tpoutj.size() == 1) {
							int tti = tpouti.keyAt(0);
							int ttj = tpoutj.keyAt(0);
							
							SparseIntArray inputstti = flowPT.getColumn(tti).clone();
							int v = inputstti.get(pouti);
							inputstti.delete(pouti);
							inputstti.put(poutj, v);
							
							if (inputstti.equals(flowPT.getColumn(ttj))) {
								// also test output
								if (flowTP.getColumn(tti).equals(flowTP.getColumn(ttj))) {
									// we can discard pj and tidj !
									todel.add(tidj);
								}
							}
						}
					}
				}
			}
		}
		for (int i : todel) {
			// System.out.println("removing transition "+tnames.get(i) +" pre:" + flowPT.getColumn(i) +" post:" + flowTP.getColumn(i));
			flowPT.deleteColumn(i);
			flowTP.deleteColumn(i);
			tnames.remove(i);
		}
		return todel.size();
	}

	private boolean isStronglyQuasiPersistent(int hid, IntMatrixCol tflowPT, ReductionType rt) {
		// sufficient condition : nobody can disable t
		SparseIntArray hPT = flowPT.getColumn(hid);
		
		for (int pi = 0; pi < hPT.size() ; pi++) {
			// for every place p, such that h consumes tokens from it
			int pid = hPT.keyAt(pi);
			// does it have any other consumers than h ? 
			SparseIntArray pPT = tflowPT.getColumn(pid);			
			if (pPT.size() > 1) {
				return false;
			}
		}
		return true;
	}
	
	private boolean findAndReduceSCCSuffixes(ReductionType rt) throws DeadlockFound {
		if (rt == ReductionType.LTL || rt == ReductionType.LIVENESS)
			return false;
		Set<Integer> safeNodes = findSCCSuffixes(this,rt,untouchable);
		if (safeNodes.size() < getPlaceCount()) {
			dropIrrelevant(safeNodes);
			return true;
		}
		return false;
	}
	
	public static Set<Integer> findSCCSuffixes(ISparsePetriNet pn, ReductionType rt, BitSet untouchable) throws DeadlockFound {
		long time = System.currentTimeMillis();
		// extract all transitions to a PxP matrix
		IntMatrixCol graph = buildGraph(pn, rt, -1);
		// the set of nodes that are "safe"
		Set<Integer> safeNodes = computeSafeNodes(pn, rt, graph, untouchable);
		
		if (DEBUG >= 3) {
			FlowPrinter.drawNet(pn, "Safe nodes", safeNodes, Collections.emptySet());
		}
		int nbP = pn.getPlaceCount();
		
		if (safeNodes.size() < nbP) {
			// System.out.println("A total of "+ (nbP - covered) + " / " + nbP + " places could possibly be suffix of SCC.");
			
			// modifies safeNodes to add any prefix of them in the graph
			collectPrefix(safeNodes, graph, true);
			
			if (DEBUG >= 3) {
				FlowPrinter.drawNet(pn, "Safe nodes + prefix", safeNodes, Collections.emptySet());
			}		
		}
		if (safeNodes.size() < nbP) {
			int nbedges = graph.getColumns().stream().mapToInt(col -> col.size()).sum();
			System.out.println("Graph (complete) has "+nbedges+ " edges and " + nbP + " vertex of which " + safeNodes.size() + " are kept as prefixes of interest. Removing "+ (nbP - safeNodes.size()) + " places using SCC suffix rule." + (System.currentTimeMillis()- time) + " ms");
			return safeNodes;
		}
		
		return safeNodes;
	}


	public int dropIrrelevant(Set<Integer> safeNodes) {
		List<Integer> torem = new ArrayList<Integer>();
		for (int p=0, nbP = pnames.size() ; p < nbP ; p++) {
			if (! safeNodes.contains(p)) {
				torem.add(p);
			}
		}
		if (! torem.isEmpty()) {					
			// also discard transitions that take from these places
			dropPlaces(torem,true, "Prefix Of Interest discarding "+torem.size() + " places" );					
		}
		return torem.size();
	}


	public static IntMatrixCol buildGraph(ISparsePetriNet pn, ReductionType rt, int skipped) {
		int nbP = pn.getPlaceCount();
		IntMatrixCol graph = new IntMatrixCol(nbP,nbP);
		
		for (int tid = 0, tide = pn.getTransitionCount(); tid < tide ; tid++) {
			if (tid == skipped) {
				continue;
			}
			SparseIntArray hPT = pn.getFlowPT().getColumn(tid);
			SparseIntArray hTP = pn.getFlowTP().getColumn(tid);
			// TODO : for LTL, we need to know if we have a free stutter available or not.
			for (int j =0; j < hTP.size() ; j++) {
				// additional condition : the transition must update the target				
				if (rt==ReductionType.SI_LTL || rt==ReductionType.DEADLOCKS || hTP.valueAt(j) != hPT.get(hTP.keyAt(j))) {
					for (int i=0; i < hPT.size() ; i++) {
						// suppress self edges
						if (rt==ReductionType.SI_LTL || rt==ReductionType.DEADLOCKS ||  hTP.keyAt(j) != hPT.keyAt(i)) {
							// this is the transposed graph					
							graph.set(hTP.keyAt(j), hPT.keyAt(i), 1);
						}
					}
				}
			}			
		}
		
		return graph;
	}


	public static Set<Integer> computeSafeNodes(ISparsePetriNet pn, ReductionType rt, IntMatrixCol graph, BitSet untouchable) throws DeadlockFound {
		int nbP = graph.getColumnCount();
		Set<Integer> safeNodes = new HashSet<>(nbP*2);

		// Deadlock case : seed from nodes that are in an SCC
		if (rt==ReductionType.SI_LTL || rt==ReductionType.DEADLOCKS) {

			List<List<Integer>> sccs = kosarajuSCC(graph);

			// remove elementary SCC that are not actually their own successor
			sccs.removeIf(scc -> scc.size()==1 && graph.get(scc.get(0), scc.get(0))==0);

			if (sccs.isEmpty() && rt==ReductionType.DEADLOCKS) {
				System.out.println("Complete graph has no SCC; deadlocks are unavoidable." +" place count " + pn.getPlaceCount() + " transition count " + pn.getTransitionCount());
				throw new DeadlockFound();
			}

//			for (List<Integer> scc : sccs) {
//				System.out.println("Scc : " + scc.stream().map(p-> pnames.get(p)).collect(Collectors.toList()) );
//			}	
			
			// System.out.println("A total of "+ (nbP - covered) + " / " + nbP + " places could possibly be suffix of SCC.");
			
			// the set of nodes that are "safe"
			for (List<Integer> s : sccs) 
				safeNodes.addAll(s);
			
		} 
		if (rt==ReductionType.SI_LTL || rt == ReductionType.SAFETY) {
			// Safety case : seed from variables of interest only
			for (int i = untouchable.nextSetBit(0); i >= 0; i = untouchable.nextSetBit(i+1)) {
				// operate on index i here
				safeNodes.add(i);
				if (i == Integer.MAX_VALUE) {
			         break; // or (i+1) would overflow
			    }
			 }			
			// add all preconditions of transitions touching p				
			for (int tid=0, e=pn.getTransitionCount() ; tid < e ; tid++) {
				if (rt == ReductionType.SAFETY && touches(tid, pn, untouchable)) {
					for (int i=0, ee=pn.getFlowPT().getColumn(tid).size(); i < ee ; i++) {
						safeNodes.add(pn.getFlowPT().getColumn(tid).keyAt(i));
					}
				} else if (rt==ReductionType.SI_LTL) {
					// stronger condition
					boolean touches = false;
					SparseIntArray pt = pn.getFlowPT().getColumn(tid);
					for (int i=0,ie=pt.size() ; i < ie ; i++) {
						if (safeNodes.contains(pt.keyAt(i))) {
							touches = true;
							break;
						}
					}
					for (int i=0, ee=pn.getFlowPT().getColumn(tid).size(); i < ee ; i++) {
						safeNodes.add(pn.getFlowPT().getColumn(tid).keyAt(i));
					}
				}
			}
		}
		return safeNodes;
	}
	
	public static void collectPrefix(Set<Integer> safeNodes, IntMatrixCol graph, boolean transpose) {
		if (safeNodes.size() == graph.getColumnCount()) {
			return;
		}
		// work with predecessor relationship
		IntMatrixCol tgraph;
		if (transpose)
			tgraph = graph.transpose();
		else
			tgraph = graph;

		Set<Integer> seen = new HashSet<>();
		List<Integer> todo = new ArrayList<>(safeNodes);
		while (!todo.isEmpty()) {
			List<Integer> next = new ArrayList<>();
			seen.addAll(todo);
			for (int n : todo) {
				SparseIntArray pred = tgraph.getColumn(n);
				for (int i = 0; i < pred.size(); i++) {
					int pre = pred.keyAt(i);
					if (seen.add(pre)) {
						next.add(pre);
					}
				}
			}
			todo = next;
		}
		safeNodes.addAll(seen);
	}

	public boolean findFreeSCC (ReductionType rt) {
		if (rt == ReductionType.LTL) {
			return false;
		}
		long time = System.currentTimeMillis();
		// extract simple transitions to a PxP matrix
		int nbP = pnames.size();
		IntMatrixCol graph = new IntMatrixCol(nbP,nbP);
		
		int nbedges = 0;
		for (int tid = 0; tid < flowPT.getColumnCount() ; tid++) {
			SparseIntArray hPT = flowPT.getColumn(tid);
			SparseIntArray hTP = flowTP.getColumn(tid);
			if (hPT.size() == 1 && hTP.size() == 1 && hPT.valueAt(0)==1 && hTP.valueAt(0)==1 && ! untouchable.get(hPT.keyAt(0)) && ! untouchable.get(hTP.keyAt(0))) {				
				graph.set(hTP.keyAt(0), hPT.keyAt(0), 1);
				nbedges++;
			}						
		}
		
		List<List<Integer>> sccs = kosarajuSCC(graph);
		sccs.removeIf(s -> s.size() == 1);
		
		if (sccs.isEmpty()) {
			return false;
		}
		int nbcovered = sccs.stream().collect(Collectors.summingInt(scc->scc.size()));		
		System.out.println("Graph (trivial) has "+nbedges+ " edges and " + nbP + " vertex of which " + nbcovered + " / " + nbP + " are part of one of the " + sccs.size() +" SCC in " + (System.currentTimeMillis()- time) + " ms");
		if (DEBUG>=2) {
			Set<Integer> set = new HashSet<>();
			for (List<Integer> s : sccs) {
				set.addAll(s);
			}
			FlowPrinter.drawNet(this, "Free SCC rule, fusing "+sccs.size()+ " SCCs covering " + nbcovered+ " places",set, Collections.emptySet());
		}
		IntMatrixCol tflowPT = flowPT.transpose();
		IntMatrixCol tflowTP = flowTP.transpose();
		List<Integer> tokill = new ArrayList<>();		
		for (List<Integer> sccl : sccs) {
			int kept = sccl.get(0);						
			for (int other : sccl.subList(1,sccl.size()) ) {
				SparseIntArray fromO = tflowPT.getColumn(other);
				// the set of transitions taking from Pi => redirect to P0
				for (int i=0; i < fromO.size() ; i++) {
					int tid = fromO.keyAt(i);
					int val = fromO.valueAt(i);
					flowPT.getColumn(tid).put(kept, flowPT.getColumn(tid).get(kept) + val);
				}
				fromO = tflowTP.getColumn(other);
				// the set of transitions taking from Pi => redirect to P0
				for (int i=0; i < fromO.size() ; i++) {
					int tid = fromO.keyAt(i);
					int val = fromO.valueAt(i);
					flowTP.getColumn(tid).put(kept, flowTP.getColumn(tid).get(kept) + val);
				}
				marks.set(kept, marks.get(kept)+marks.get(other));
				tokill.add(other);
			}			
		}
		
		// at this stage, the other places in each SCC are now redundant, kill them
		tokill.sort( (a,b) -> - a.compareTo(b));
		tflowPT = flowPT.transpose();
		tflowTP = flowTP.transpose();		
		List<String> prem = new ArrayList<>();
		for (int i:tokill) {
			prem.add(dropPlace(i, tflowPT, tflowTP));
		}
		flowPT = tflowPT.transpose();
		flowTP = tflowTP.transpose();
		System.out.println("Free SCC test removed "+prem.size()+ " places " + (DEBUG >=1 ? (" : "+ prem ) : ""));
		if (DEBUG==2) FlowPrinter.drawNet(this, "After Free SCC fused "+ sccs.size() + " scc, discarding "+ prem.size()+ " places.");
		return true;
	}

	private static List<List<Integer>> kosarajuSCC(IntMatrixCol graph) {
		// This part implements Kosaraju to find SCC
		// not the best time complexity algo for that, but enough for us.
		Stack<Integer> stack = new Stack<>();
		Set<Integer> visited = new HashSet<>();

		// recursive version, implicit stack is java's call stack
//		for (int p = 0 ; p < nbP ; p++) {
//			visitNode(graph, stack, p, visited);
//		}
		
		// derecursed version uses a todo stack
		Stack<Integer> todo = new Stack<>();		
		for (int p = 0 ; p < graph.getColumnCount() ; p++) {
			todo.add(p);			
		}
		while (! todo.isEmpty()) {
			int p = todo.pop();
			if (p==-1) {
				stack.push(todo.pop());
				continue;
			}
			SparseIntArray col = graph.getColumn(p);
			if (col.size() > 0) {
				if (! visited.add(p)) {
					continue;
				}
				todo.push(p);
				todo.push(-1);
				for (int i=0 ; i < col.size() ; i++) {
					todo.push(col.keyAt(i));
				}
			}
		}
		
		List<List<Integer>> sccs = new ArrayList<>();
		List<Integer> curScc = new ArrayList<>();
		visited.clear();
		graph = graph.transpose();
		while (! stack.isEmpty()) {
			int cur = stack.pop();
			visitNodeBis(graph, curScc, cur, visited);
			if (! curScc.isEmpty()) {
				sccs.add(curScc);
				curScc = new ArrayList<>();
			}
		}
		return sccs;
	}

	private static void visitNodeBis(IntMatrixCol graph, List<Integer> curScc, int cur, Set<Integer> visited) {
		if (visited.add(cur)) {
			curScc.add(cur);
			SparseIntArray col = graph.getColumn(cur);
			for (int i=0 ; i < col.size() ; i++) {
				visitNodeBis(graph, curScc,  col.keyAt(i), visited);
			}				
		}
	}

	private void visitNode(IntMatrixCol graph, Stack<Integer> stack, int p, Set<Integer> visited) {
		SparseIntArray col = graph.getColumn(p);
		if (col.size() > 0) {
			if (! visited.add(p)) {
				return;
			}
			for (int i=0 ; i < col.size() ; i++) {
				visitNode(graph, stack, col.keyAt(i), visited);
			}
			stack.push(p);
		}
	}

	private boolean isDivergentFree(int hid) {
		SparseIntArray hPT = flowPT.getColumn(hid);
		SparseIntArray hTP = flowTP.getColumn(hid);
		// divergent free
		
		int j=0;
		for (int i=0; i < hPT.size() ; i++) {
			int pi = hPT.keyAt(i);
			int vi = hPT.valueAt(i);
			// TP must be less or equal to this
			for (; j < hTP.size() ; ) {
				int pj = hTP.keyAt(j);
				int vj = hTP.valueAt(j);
				if (pj < pi) {
					j++;
				} else if (pi < pj) {
					return true;
				} else if (vi > vj) {
					return true;
				} else {
					j++;
					break;
				}
			}
			if (j== hTP.size()) {
				return true;
			}
		}
		return false;
	}
	
	public IntMatrixCol getFlowPT() {
		return flowPT;
	}
	public IntMatrixCol getFlowTP() {
		return flowTP;
	}
	public List<Integer> getMarks() {
		return marks;
	}
	@Override
	public List<String> getPnames() {
		return pnames;
	}
	@Override
	public List<String> getTnames() {
		return tnames;
	}
	
	public int fusePlaces(List<Integer> base, List<Integer> next) {
		Set<Integer> todel = new TreeSet<>((x,y)->-Integer.compare(x, y));
		// now work with the tflowTP to find transitions feeding pj we need to update
		IntMatrixCol tflowTP = flowTP.transpose();
		IntMatrixCol tflowPT = flowPT.transpose();
		
		for (int i =0; i< base.size() ; i++) {			
			int ibase = base.get(i)-1;
			if (ibase >= getPnames().size()) {
				break;
			}
			int itarg = next.get(i)-1;
			if (DEBUG>=1) {
				System.out.println("Fusing places "+ pnames.get(ibase) + " and " + pnames.get(itarg));
			}
			tflowPT.setColumn(ibase, 
					SparseIntArray.sumProd(1, tflowPT.getColumn(ibase), 
										   1, tflowPT.getColumn(itarg)));
			tflowTP.setColumn(ibase, 
					SparseIntArray.sumProd(1, tflowTP.getColumn(ibase), 
										   1, tflowTP.getColumn(itarg)));
			marks.set(ibase, marks.get(ibase)+marks.get(itarg));
			image.set(ibase, Expression.op(Op.ADD, image.get(ibase), image.get(itarg)));
			todel.add(itarg);
		}
		List<String> prem = new ArrayList<>();
		for (int i : todel) {
			prem.add(dropPlace(i, tflowPT, tflowTP));
		}
		flowPT = tflowPT.transpose();
		flowTP = tflowTP.transpose();
		System.out.println("Place Fusion rule removed "+prem.size()+ " places  "+ (DEBUG >=1 ? (" : "+ prem ) : ""));
		if (DEBUG==2) FlowPrinter.drawNet(this, "After fusion of "+base.size()+" places");
		return todel.size();
	}


	public void setProtected(BitSet support) {
		this.untouchable = support;
		
	}

	/**Define a new place, whose marking is the sum of markings of target places.
	 * 
	 * @param pids
	 */
	public int createSumOfVars(Set<Integer> pids, String name) {
		int m = 0;
		for (int p : pids) {
			m+= marks.get(p);
		}
		List<Expression> tosum = new ArrayList<> (pids.size());
		for (int p : pids) {
			tosum.add(image.get(p));
		}
		int id = marks.size();
		image.add(Expression.nop(Op.ADD,tosum));
		marks.add(m);
		pnames.add(name);
		for (int tid =0, e=tnames.size() ; tid < e ; tid++ ) {
			updateCol(flowPT.getColumn(tid), id, pids);
			updateCol(flowTP.getColumn(tid), id, pids);
		}
		flowPT.addRow();
		flowTP.addRow();
		if (DEBUG==2) FlowPrinter.drawNet(this, "With newly created Sum Of place "+name, Collections.singleton(pnames.size()-1), Collections.emptySet());
		return id;
	}


	private void updateCol(SparseIntArray col, int sumRep, Set<Integer> toSum) {
		int s = 0;
		for (int i=0, ee=col.size(); i < ee ; i++) {
			if (toSum.contains(col.keyAt(i))) {
				s += col.valueAt(i);
			}
		}
		if (s != 0) {
			col.append(sumRep, s);
		}
	}

	public void setMarks(List<Integer> marks) {
		this.marks = marks;
	}


	public void abstractReads() {
		for (int tid = 0 ; tid < tnames.size() ; tid++) {
			SparseIntArray res = SparseIntArray.sumProd(-1, flowPT.getColumn(tid), 1, flowTP.getColumn(tid));
			SparseIntArray pre = new SparseIntArray();
			SparseIntArray post = new SparseIntArray();
			for (int i=0,ie=res.size(); i < ie ;  i++) {
				if (res.valueAt(i) < 0) {
					pre.append(res.keyAt(i), - res.valueAt(i));
				} else {
					post.append(res.keyAt(i), res.valueAt(i));
				}
			}
			flowPT.setColumn(tid, pre);
			flowTP.setColumn(tid, post);
		}		
	}


	@Override
	public int getPlaceCount() {
		return pnames.size();
	}

	@Override
	public int getTransitionCount() {
		return tnames.size();
	}


	@Override
	public boolean isSafe() {
		return isSafe ;
	}
	@Override
	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}


	public boolean isKeepImage() {
		return keepImage;
	}


	
}
