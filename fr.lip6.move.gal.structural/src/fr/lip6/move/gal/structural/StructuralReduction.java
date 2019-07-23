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
import fr.lip6.move.gal.util.MatrixCol;


/**
 * Implement Haddad/Pradat-Peyre/Berthelot/Thierry-Mieg structural reduction rules.
 * @author ythierry
 *
 */


public class StructuralReduction implements Cloneable {

	private List<Integer> marks;
	private MatrixCol flowPT;
	private MatrixCol flowTP;
	private List<String> tnames;
	private List<String> pnames;
	private int maxArcValue;
	private BitSet untouchable;

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
	}

	
	private StructuralReduction(MatrixCol flowPT, MatrixCol flowTP, List<Integer> marks, List<String> tnames,
			List<String> pnames, int maxArcValue, BitSet untouchable) {
		this.flowPT = new MatrixCol(flowPT);
		this.flowTP = new MatrixCol(flowTP);
		this.marks = new ArrayList<>(marks);
		this.tnames = new ArrayList<>(tnames);
		this.pnames = new ArrayList<>(pnames);
		this.maxArcValue = maxArcValue;
		this.untouchable = (BitSet) untouchable.clone();
	}


	private int findMax(MatrixCol mat) {
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
		return new StructuralReduction(flowPT, flowTP, marks, tnames, pnames, maxArcValue, untouchable);
	}
	
	public BitSet getUntouchable() {
		return untouchable;
	}
	
	public Specification rebuildSpecification () {
		return SpecBuilder.buildSpec(flowPT, flowTP, pnames, tnames, marks);
	}
	
	public enum ReductionType { DEADLOCKS, SAFETY }
	public int reduce (ReductionType rt) throws NoDeadlockExists, DeadlockFound {
		//ruleSeqTrans(trans,places);
		int initP = pnames.size();
		int initT = tnames.size();
		
		if (DEBUG==2) FlowPrinter.drawNet(this);
				
		long time = System.currentTimeMillis();
		int total = 0;
		int totaliter=0;
		int iter =0;
		
		if (findFreeSCC())
			total++;

		if (findSCCSuffixes(rt)) 
			total++;
		
		do {
			do {
				totaliter=0;
				totaliter += ruleReducePlaces(rt,false);
//				if (totaliter > 0) {
//					FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
//				}
				totaliter += ruleReduceTrans(rt);
				
				totaliter += findSCCSuffixes(rt) ? 1:0;
				
				totaliter += ruleImplicitPlace();
				
				totaliter += rulePostAgglo(false,true,rt);
				total += totaliter;
				if (totaliter > 0) {
					System.out.println("Iterating post reduction "+ (iter++) + " with "+ totaliter+ " rules applied. Total rules applied " + total + " place count " + pnames.size() + " transition count " + tnames.size());				
				} else {					
					if (DEBUG>=1) System.out.println("Stability for Post agglomeration reached at "+ (iter++));					
				}				
			} while (totaliter > 0);
			totaliter = 0;
			totaliter += rulePreAgglo();
			
			if (totaliter > 0) {
				System.out.println("Pre-agglomeration after "+ (iter) + " with "+ totaliter+ " Pre rules applied. Total rules applied " + total+ " place count " + pnames.size() + " transition count " + tnames.size());				
			} else {
				if (DEBUG>=1) System.out.println("No additional pre-agglomerations found "+ (iter++));
			}
			
			// int sym = ruleSymmetricChoice();
			if (totaliter == 0) {
				int sym = ruleFusePlaceByFuture();
				totaliter += sym;
				total += totaliter;
				if (sym > 0) {
					if (DEBUG==2) FlowPrinter.drawNet(this);
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
				totaliter += findFreeSCC() ? 1 :0;
			}
			if (totaliter == 0) {
				totaliter += findSCCSuffixes(rt) ? 1 :0;
			}
			totaliter += ruleReducePlaces(rt,true);						
			if (totaliter == 0 && rt == ReductionType.SAFETY) {
				totaliter += ruleFreeAgglo(false);
			}
			if (totaliter ==0) {
				totaliter += ruleRedundantCompositions();
			}
			total += totaliter;
			System.out.flush();
		} while (totaliter > 0);
		System.out.println("Applied a total of "+total+" rules in "+ (System.currentTimeMillis() - time)+ " ms. Remains "+ pnames.size() + " /" +initP + " variables (removed "+ (initP - pnames.size()) +") and now considering "+ flowPT.getColumnCount() + "/" + initT + " (removed "+ (initT - flowPT.getColumnCount()) +") transitions.");
		if (DEBUG==2) FlowPrinter.drawNet(this);
		System.out.flush();
		
		return total;
	}
	
	
	/**
	 * Detects and destroys transitions t such that exists t1,t2 such that 
	 * t1 fireable => t1.t2 is fireable for any state
	 * t has the same effects as t1.t2
	 * t has superior or equal preconditions to t1.
	 * @return the number of transitions discarded by the rule
	 */
	private int ruleRedundantCompositions() {
		Set<Integer> todel = new HashSet<>();
		// map effect to list of transition indexes having this effect
		Map<SparseIntArray,List<Integer>> effects = new HashMap<>();
		for (int tid=0, e=tnames.size() ; tid < e ; tid++) {
			final int t =tid;
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
		List<Integer> tids = new ArrayList<>();
		for (int i=0; i < tnames.size() ; i++) {
			tids.add(i);
		}
		tids.sort((a,b) -> -Integer.compare(flowPT.getColumn(a).size()+ flowTP.getColumn(a).size(), flowPT.getColumn(b).size()+ flowTP.getColumn(b).size()) );
		for (int id=0, e=tnames.size() ; id < e ; id++) {
			int tid = tids.get(id);
			if (todel.contains(tid)) {
				continue;
			}
			// preconditions for firing t
			SparseIntArray pre = flowPT.getColumn(tid);
			// state reached after firing t from it's minimal enabling
			SparseIntArray init = flowTP.getColumn(tid);
			SparseIntArray teff = SparseIntArray.sumProd(-1, pre, 1, init);
			// other transitions enabled by t 
			for (int ttid=0 ; ttid < e ; ttid++) {
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
		if (! todel.isEmpty()) {
			dropTransitions(new ArrayList<>(todel));
			System.out.println("Redundant transition composition rules discarded "+todel.size()+ " transitions");
		}		
		return todel.size();
	}


	public int ruleFreeAgglo(boolean doComplex) {
		MatrixCol tflowTP = null;
		int done = 0;
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
			// p has a single input t, that has a single output p
			if (toP.size()==1 && toP.valueAt(0)==1) {
				int tid = toP.keyAt(0);
				if (!doComplex && flowPT.getColumn(tid).size() > 1) {
					continue;
				}				
				// single input to p
				List<Integer> Hids = new ArrayList<>();
				Hids.add(tid);
				List<Integer> Fids = new ArrayList<>();
				boolean ok = true;
				for (int ttid=0 ; ttid < tnames.size() ; ttid++) {
					int val = flowPT.getColumn(ttid).get(pid);
					if ( val == 1) {
						Fids.add(ttid);
					} else if (val > 1) {
						ok = false;
						break;
					}
				}
				if (!ok || Fids.contains(tid) || !checkProtection(Hids, Fids)) {
					continue;
				}
				if (DEBUG>=1) System.out.println("Net is Free-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);					
				agglomerateAround(pid, Hids , Fids);
				done++;
				tflowTP = null;
				if (DEBUG==2) FlowPrinter.drawNet(this);
			}
		}
		
		if (done >0) {
			System.out.println("Free-agglomeration rule applied "+done+ " times.");
		}
		
		return 0;
	}


	private int ruleReduceTrans(ReductionType rt) throws NoDeadlockExists {
		int reduced = ensureUnique(flowPT, flowTP, tnames, null); 
		if (reduced > 0) {
			System.out.println("Reduce isomorphic transitions removed "+ reduced +" transitions.");
		}
		if (rt == ReductionType.DEADLOCKS) {
			for (int i = 0; i < flowPT.getColumnCount() ; i++) {
				if (flowPT.getColumn(i).size()==0) {
					throw new NoDeadlockExists();
				}
			}
		} else if (rt == ReductionType.SAFETY) {
			// transitions with no effect => no use
			List<Integer> todrop = new ArrayList<>();
			for (int i = tnames.size()-1 ;  i >= 0 ; i--) {
				if (flowPT.getColumn(i).equals(flowTP.getColumn(i))) {
					todrop.add(i);
				}
			}
			if (! todrop.isEmpty()) {
				reduced += todrop.size();
				dropTransitions(todrop);
			}
		}
		if (maxArcValue > 1) {
			MatrixCol tflowPT = flowPT.transpose(); 
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

	private int ensureUnique(MatrixCol mPT, MatrixCol mTP, List<String> names, List<Integer> init) {
		Map<SparseIntArray, Set<SparseIntArray>> seen = new HashMap<>();
		List<Integer> todel = new ArrayList<>();
		if (init != null) {
			for (int i = untouchable.nextSetBit(0); i >= 0; i = untouchable.nextSetBit(i+1)) {
				addToSeen(i, seen, mPT, mTP, todel); 
				// operate on index i here
				if (i == Integer.MAX_VALUE) {
					break; // or (i+1) would overflow
				}
			}
			todel.clear();
		}
		for (int trid=mPT.getColumnCount()-1 ; trid >= 0 ; trid--) {
			if (init==null || !untouchable.get(trid))
				addToSeen(trid, seen, mPT, mTP, todel); 
		}
		List<String> rem = new ArrayList<>();
		for (int td : todel) {
			rem.add(names.remove(td));
			mPT.deleteColumn(td);
			mTP.deleteColumn(td);
			if (init != null) {
				init.remove(td);
				removeAt(td,untouchable);
			}
		}
		if (!todel.isEmpty()) {
			System.out.println("Ensure Unique test removed "+ rem.size()+ (init!=null?" places":" transitions") + (DEBUG>=1 ?" : "+ rem  : ""));
		}
		return todel.size();
	}


	private void addToSeen(int trid, Map<SparseIntArray, Set<SparseIntArray>> seen, MatrixCol mPT, MatrixCol mTP,
			List<Integer> todel) {
		SparseIntArray tcolPT = mPT.getColumn(trid);
		Set<SparseIntArray> index = seen.get(tcolPT);
		if (index == null) {
			index = new HashSet<>();
			seen.put(tcolPT, index);
		}
		SparseIntArray tcolTP = mTP.getColumn(trid);
		if (! index.add(tcolTP)) {
			todel.add(trid);
		}
	}

	private int ruleReducePlaces(ReductionType rt, boolean withSyphon) {
		int totalp = 0;
		// find constant marking places
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();
		// reverse ordered set of tindexes to kill
		Set<Integer> todelTrans = new TreeSet<>((x,y) -> -Integer.compare(x, y));

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
				if (rt != ReductionType.DEADLOCKS) {
					break;
				}
			}
		}
		List<String> prem = new ArrayList<>();
		List<String> trem = new ArrayList<>();
		
		Set<Integer> syphon = withSyphon ? computeEmptySyphon(this) : Collections.emptySet();
		// now scan for isomorphic/redundant/useless/constant places
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
			if (untouchable.get(pid)) {
				continue;
			}
			SparseIntArray from = tflowPT.getColumn(pid);
			SparseIntArray to = tflowTP.getColumn(pid);
			
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
			if (syphon.contains(pid) || from.equals(to) || noTrueInputs || (to.size()==0 && marks.get(pid)==0) ) {
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
					}
				}				
				prem.add(dropPlace(pid, tflowPT, tflowTP));
				totalp++;
			} else if (from.size() == 0) {
				prem.add(dropPlace(pid, tflowPT, tflowTP));
				totalp++;
			} 
		}
		totalp += ensureUnique(tflowPT, tflowTP, pnames, marks);
		
		if (rt == ReductionType.SAFETY) {
			// find a place that has a single input
			for (int pid = 0, e=tflowPT.getColumnCount() ; pid < e ; pid++ ) {
				// and is initially empty
				if (marks.get(pid) > 0) {
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
							// Aha, we have a match !
							todelTrans.add(totry);
							
							System.out.println("Remove reverse transitions rule discarded transition " + tnames.get(totry));
						}
					}
				}
			}
		}
		
		if (totalp > 0) {
			// reconstruct updated flow matrices
			tflowPT.transposeTo(flowPT);
			tflowTP.transposeTo(flowTP);
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
			System.out.println("Constant places removed "+totalp + " places and " + todelTrans.size() + " transitions. " + (DEBUG>=1 ? ("Places : " + prem + " Transitions:" + trem):""));

		return totalp;
	}


	private String dropPlace(int pid, MatrixCol tflowPT, MatrixCol tflowTP) {
		// delete line for p
		tflowPT.deleteColumn(pid);
		tflowTP.deleteColumn(pid);
		String ret = pnames.remove(pid);
		removeAt(pid, untouchable);
		// System.out.println("Removing "+pid+":"+remd);
		marks.remove(pid);
		return ret;
	}

	private int ruleImplicitPlace () {
		int totalp = 0;
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();		
		List<String> deleted = new ArrayList<>();
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
			BitSet seen = new BitSet();
			// so other is controlling eatP, see if it is implied by feedP
			if (inducedBy(other,tfeedP,tflowPT,tflowTP,seen,5))  {
				// Hurray ! P is implicit !
				deleted.add(dropPlace(pid, tflowPT, tflowTP));
				tflowPT.transposeTo(flowPT);
				tflowTP.transposeTo(flowTP);
				totalp++;
			}
		}
		if (totalp >0) {
			System.out.println("Implicit places reduction removed "+totalp+" places "+ (DEBUG >=1 ? (" : "+ deleted ) : ""));
			if (DEBUG==2) FlowPrinter.drawNet(this);
		}
		return totalp;
	}
	public void dropTransitions (List<Integer> todrop) {
		dropTransitions(todrop, true);
	}
	public void dropTransitions (List<Integer> todrop, boolean trace) {
		List<String> deleted = new ArrayList<>();
		todrop.sort((a,b) -> - a.compareTo(b));
		for (int tid : todrop) {
			flowPT.deleteColumn(tid);
			flowTP.deleteColumn(tid);
			deleted.add(tnames.remove(tid));
		}
		int totalp = deleted.size();
		if (totalp >0 && trace) {
			System.out.println("Drop transitions removed "+totalp+" transitions "+ (DEBUG >=1 ? (" : "+ deleted ) : ""));
			if (DEBUG==2) FlowPrinter.drawNet(this);
		}
	}
	
	public void dropPlaces (List<Integer> todrop, boolean andOutputs) {
		dropPlaces(todrop, andOutputs, true);
	}
	
	public void dropPlaces (List<Integer> todrop, boolean andOutputs, boolean trace) {
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();	
		List<String> deleted = new ArrayList<>();
		Set<Integer> toremT = new HashSet<>();
		todrop.sort(Integer::compare);
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
			if (DEBUG==2) FlowPrinter.drawNet(this);
		}
		if (andOutputs) {
			List<Integer> kt = new ArrayList<>(toremT);
			// remove transitions that would now be "free"
			kt.removeIf(tid -> flowPT.getColumn(tid).size()!=0 || flowPT.getColumn(tid).size()!=0); 
			if (trace) System.out.println("Also discarding "+kt.size()+" output transitions "+ (DEBUG >=1 ? (" : "+ kt ) : ""));
			dropTransitions(kt);
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


	private boolean inducedBy(int pid, int tcause, MatrixCol tflowPT, MatrixCol tflowTP, BitSet seen, int depth) {
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

	private int rulePostAgglo(boolean doComplex, boolean doSimple, ReductionType rt) {
		int total = 0;
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();
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
					if (rt == ReductionType.SAFETY && !isMarked) {
						// check if the only other controls are test arcs and update test set if so
						for (int ai = 0, e= fPT.size() ; ai < e ; ai++) {
							int pcontrol = fPT.keyAt(ai);
							if (untouchable.get(pcontrol)) {
								ok = false;
								break;
							}
							if (pcontrol == pid) {
								continue;
							} else {
								// is it a read arc
								if (flowTP.getColumn(fid).get(pcontrol) != fPT.valueAt(ai)) {
									// bad
									ok = false;
									break;
								} else {
									testSet.add(pcontrol);
								}
							}
						}
					} else {
						// for deadlocks, this intermediate behavior is interesting
						ok = false;
						break;
					}
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


			if (DEBUG>=1) System.out.println("Net is Post-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);
			if (rt==ReductionType.SAFETY &&  ! untouchable.isEmpty() && touches(Fids)) {
				for (int h : Hids) {
					if (SparseIntArray.sumProd(1, flowPT.getColumn(h), -1, flowTP.getColumn(h)).size()>1) {
						ok = false;
						break;
					}
				}
				if (!ok)
					continue;				
			}
			if (isMarked) {
				// fire the single F continuation until the place is empty
				int fid = fcand.keyAt(0);

				emptyPlaceWithTransition(pid, fid);
				// System.out.println("Pushed tokens out of "+pnames.get(pid));
			}

			agglomerateAround(pid, Hids, Fids);
			if (DEBUG==2) FlowPrinter.drawNet(this);
			total++;
			flowPT.transposeTo(tflowPT);			
			flowTP.transposeTo(tflowTP);
			if (doComplex && total > 100) break;
			
			long deltat = System.currentTimeMillis() - time;
			if (deltat >= 30000) {
				System.out.println("Performed "+total + " Post agglomeration using F-continuation condition.");
				time = System.currentTimeMillis();
			}
			
		}
		
		
		if (total != 0) {
			System.out.println("Performed "+total + " Post agglomeration using F-continuation condition.");
		}
		
		return total;
	}


	private boolean checkProtection(List<Integer> Hids, List<Integer> Fids) {
		// make sure not h and f both touching places
		// or h.f could hide non stuttering behavior		
		 return !(touches(Hids) && touches(Fids));
	}

	/**
	 * Returns true if one of the transitions is touching at least one of "untouchable" places.  
	 * @param Hids
	 */
	private boolean touches(List<Integer> Hids) {
		if (untouchable.isEmpty())
			return false;
		for (int h : Hids) {
			SparseIntArray col = flowPT.getColumn(h);
			for (int i=0; i < col.size() ; i++) {
				if (untouchable.get(col.keyAt(i))) {
					return true;					
				}
			}
			col = flowTP.getColumn(h);
			for (int i=0; i < col.size() ; i++) {
				if (untouchable.get(col.keyAt(i))) {
					return true;
				}
			}
		}
		return false;
	}

	private void emptyPlaceWithTransition(int pid, int fid) {
		SparseIntArray preF = flowPT.getColumn(fid);
		SparseIntArray postF = flowTP.getColumn(fid);
		while (marks.get(pid) > 0) {
			for (int ip = 0 ; ip < preF.size() ; ip++) {
				int p = preF.keyAt(ip);
				int v = preF.valueAt(ip);
				marks.set(p, marks.get(p)- v);
			}						
			for (int ip = 0 ; ip < postF.size() ; ip++) {
				int p = postF.keyAt(ip);
				int v = postF.valueAt(ip);
				marks.set(p, marks.get(p)+ v);
			}
		}
	}

	private void agglomerateAround(int pid, List<Integer> Hids, List<Integer> Fids) {
		List<SparseIntArray> HsPT = new ArrayList<>();
		List<SparseIntArray> HsTP = new ArrayList<>();
		List<String> Hnames = new ArrayList<>();
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
		todel.addAll(Fids);
		todel.sort( (x,y) -> -Integer.compare(x,y));
		for (int i : todel) {
			if (DEBUG>=1) System.out.println("removing transition "+tnames.get(i) +" pre:" + flowPT.getColumn(i) +" post:" + flowTP.getColumn(i));
			flowPT.deleteColumn(i);
			flowTP.deleteColumn(i);
			tnames.remove(i);
		}
		MatrixCol toaddmatPT = new MatrixCol(flowPT.getRowCount(), 0);
		MatrixCol toaddmatTP = new MatrixCol(flowPT.getRowCount(), 0);
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
		ensureUnique(toaddmatPT, toaddmatTP, tnamesadd, null);
		tnames.addAll(tnamesadd);
		for (int i = 0; i < toaddmatPT.getColumnCount() ; i++) {
			flowPT.appendColumn(toaddmatPT.getColumn(i));
			flowTP.appendColumn(toaddmatTP.getColumn(i));
			if (DEBUG>=1) System.out.println("added transition "+tnamesadd.get(i) +" pre:" + toaddmatPT.getColumn(i) +" post:" + toaddmatTP.getColumn(i));
		}
	}
	
	private int rulePreAgglo() {
		int total = 0;
		MatrixCol tflowPT = flowPT.transpose();
		for (int pid = 0 ; pid < pnames.size() ; pid++) {
			if (untouchable.get(pid)) {
				continue;
			}
			List<Integer> Fids = new ArrayList<>();
			List<Integer> Hids = new ArrayList<>();
			if (marks.get(pid) != 0) {
				continue;
			}
			boolean ok = true;
			for (int tid=0; tid < flowPT.getColumnCount() ; tid++) {
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
					if (Hids.size()>1 && Fids.size()>1) {
						ok = false;
						break;
					}
					continue;
				} else {
					// we are a feeder into P
					Hids.add(tid);
					// we want H be a singleton, to ease HF-interchangeability
					// we want H or F be a singleton, to ease HF-interchangeability
					if (Hids.size()>1 && Fids.size()>1) {
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
					if (! isStronglyQuasiPersistent(tid,tflowPT)) {
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
			if (Hids.size()>1 && Fids.size()>1) {
				continue;
			}
			if (Hids.stream().anyMatch(h-> Fids.contains(h))) {
				continue;
			}
			if (! untouchable.isEmpty()) {
				ok = checkProtection(Hids, Fids);
			}
			if (!ok) {
				continue;
			} else {
				if (DEBUG>=1) System.out.println("Net is Pre-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);
				
				agglomerateAround(pid, Hids, Fids);
				if (DEBUG>=2)  FlowPrinter.drawNet(this);
				flowPT.transposeTo(tflowPT);
				total++;
			}
			
		}
		
		if (total != 0) {
			System.out.println("Performed "+total + " Pre agglomeration using Quasi-Persistent + HF-interchangeable + Divergent Free condition.");
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
		
		int seen = -1;
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
				} else if (ka < kb) {
					// mismatch
					if (ka == indi) {
						if (seen==-1) {
							seen = va;
						} else if (seen != va) {
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
						if (seen==-1) {
							seen = vb;
						} else if (seen != vb) {
							return false;
						}
					} else {
						return false;
					}
					j++;
					continue;
				}
		}
		return seen != -1;
	}
	
	private int ruleFusePlaceByFuture() {
		MatrixCol tflowPT = flowPT.transpose();
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
			for (int i = 0; i < list.size() ; i++) {
				if (untouchable.get(i)) {
					continue;
				}
				int pi = list.get(i);
				if (toFuse.containsKey(pi)) 
					continue;
				SparseIntArray piouts = tflowPT.getColumn(pi);
				
				
				for (int j = i+ 1 ; j < list.size() ; j++ ) {
					if (untouchable.get(j)) {
						continue;
					}
					int pj = list.get(j);
					if (toFuse.containsKey(pj)) 
						continue;
					SparseIntArray pjouts = tflowPT.getColumn(pj);
					// so, pi and pj have the same number of outputs
					int ti = 0;
					BitSet matchedj = new BitSet();
					for ( ; ti  < piouts.size() ; ti++) {
						int indti = piouts.keyAt(ti);
						SparseIntArray tiin = flowPT.getColumn(indti);
						SparseIntArray tiout = flowTP.getColumn(indti);
						
						boolean foundmatch = false;
						for (int tj = 0; tj  < pjouts.size() ; tj++) {
							
							int indtj = pjouts.keyAt(tj);
							if (indti == indtj) {
								break;
							}
							if (matchedj.get(tj)) {
								continue;
							}
							SparseIntArray tjin = flowPT.getColumn(indtj);
							SparseIntArray tjout = flowTP.getColumn(indtj);
							if (! tiout.equals(tjout)) {
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
			Set<Integer> todel = new TreeSet<>((x,y)->-Integer.compare(x, y));
			// now work with the tflowTP to find transitions feeding pj we need to update
			MatrixCol tflowTP = flowTP.transpose();
			
			for (Entry<Integer, Integer> ent : toFuse.entrySet()) {
				int pj = ent.getKey();
				int pi = ent.getValue();
				
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
					tjouts.put(pi, v);					
				}
				marks.set(pi, marks.get(pi)+marks.get(pj));
				marks.set(pj, 0);
				
			}
			
			for (int i : todel) {
				// System.out.println("removing transition "+tnames.get(i) +" pre:" + flowPT.getColumn(i) +" post:" + flowTP.getColumn(i));
				flowPT.deleteColumn(i);
				flowTP.deleteColumn(i);
				tnames.remove(i);
			}
		}
		return toFuse.size();
	}
	
	private int ruleSymmetricChoice() {
		MatrixCol tflowPT = flowPT.transpose();
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

	private boolean isStronglyQuasiPersistent(int hid, MatrixCol tflowPT) {
		// sufficient condition : nobody can disable t
		SparseIntArray hPT = flowPT.getColumn(hid);
		SparseIntArray hTP = flowTP.getColumn(hid);
		
		for (int pi = 0; pi < hPT.size() ; pi++) {
			// for every place p, such that h consumes tokens from it
			int pid = hPT.keyAt(pi);
			int vi = hPT.valueAt(pi);
			// look for ANY other consumer ot from p
			SparseIntArray pPT = tflowPT.getColumn(pid);			
			if (pPT.size() > 1) {
				return false;
			}
			// look at other consumer ot from p
//			SparseIntArray pPT = tflowPT.getColumn(pid);			
//			for (int oti =0 ; oti < pPT.size() ; oti++ ) {
//				int otid = pPT.keyAt(oti);
//				if (otid == hid)
//					continue;
//				int otv = pPT.valueAt(oti);
//				if (flowTP.getColumn(otid).get(pid) < otv) {
//					return false;
//				}
//			}
		}
		return true;
	}
	
	private boolean findSCCSuffixes(ReductionType rt) throws DeadlockFound {
		long time = System.currentTimeMillis();
		// extract all transitions to a PxP matrix
		int nbP = pnames.size();
		MatrixCol graph = new MatrixCol(nbP,nbP);

		int nbedges = 0;
		for (int tid = 0; tid < flowPT.getColumnCount() ; tid++) {
			SparseIntArray hPT = flowPT.getColumn(tid);
			SparseIntArray hTP = flowTP.getColumn(tid);
			for (int j =0; j < hTP.size() ; j++) {
				// additional condition : the transition must update the target
				if (rt==ReductionType.DEADLOCKS || hTP.valueAt(j) != hPT.get(hTP.keyAt(j))) {
					for (int i=0; i < hPT.size() ; i++) {
						// suppress self edges
						if (rt==ReductionType.DEADLOCKS ||  hTP.keyAt(j) != hPT.keyAt(i)) {
							// this is the transposed graph					
							graph.set(hTP.keyAt(j), hPT.keyAt(i), 1);
							nbedges++;
						}
					}
				}
			}
		}
		// the set of nodes that are "safe"
		Set<Integer> safeNodes = new HashSet<>(nbP*2);

		// Deadlock case : seed from nodes that are in an SCC
		if (rt==ReductionType.DEADLOCKS) {

			List<List<Integer>> sccs = kosarajuSCC(nbP, graph);

			// remove elementary SCC that are not actually their own successor
			sccs.removeIf(scc -> scc.size()==1 && graph.get(scc.get(0), scc.get(0))==0);

			if (sccs.isEmpty()) {
				System.out.println("Complete graph has no SCC; deadlocks are unavoidable.");
				throw new DeadlockFound();
			}

//			for (List<Integer> scc : sccs) {
//				System.out.println("Scc : " + scc.stream().map(p-> pnames.get(p)).collect(Collectors.toList()) );
//			}	
			
			// System.out.println("A total of "+ (nbP - covered) + " / " + nbP + " places could possibly be suffix of SCC.");
			
			// the set of nodes that are "safe"
			for (List<Integer> s : sccs) 
				safeNodes.addAll(s);
			
		} else if (rt == ReductionType.SAFETY) {
			// Safety case : seed from variables of interest only
			for (int i = untouchable.nextSetBit(0); i >= 0; i = untouchable.nextSetBit(i+1)) {
				// operate on index i here
				safeNodes.add(i);
				if (i == Integer.MAX_VALUE) {
			         break; // or (i+1) would overflow
			    }
			 }			
		}
		
		
		if (safeNodes.size() < nbP) {
			// System.out.println("A total of "+ (nbP - covered) + " / " + nbP + " places could possibly be suffix of SCC.");
			
			// modifies safeNodes to add any prefix of them in the graph
			collectPrefix(safeNodes, graph);
			
			if (safeNodes.size() < nbP) {
				List<Integer> torem = new ArrayList<Integer>();
				for (int p=0; p < nbP ; p++) {
					if (! safeNodes.contains(p)) {
						torem.add(p);
					}
				}
				if (! torem.isEmpty()) {
					System.out.println("Graph (complete) has "+nbedges+ " edges and " + nbP + " vertex of which " + safeNodes.size() + " are kept as prefixes of interest. Removing "+ torem.size() + " places using SCC suffix rule." + (System.currentTimeMillis()- time) + " ms");
					// also discard transitions that take from these places
					dropPlaces(torem,true);
					return true;
				}
			}
		}		
		
		return false;
	}
	
	private void collectPrefix(Set<Integer> safeNodes, MatrixCol graph) {
		// work with predecessor relationship
		MatrixCol tgraph = graph.transpose();
		
		Set<Integer> seen = new HashSet<>();
		List<Integer> todo = new ArrayList<>(safeNodes);
		while (! todo.isEmpty()) {
			List<Integer> next = new ArrayList<>();
			seen.addAll(todo);
			for (int n : todo) {
				SparseIntArray pred = tgraph.getColumn(n);
				for (int i=0; i < pred.size() ; i++) {
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


	private boolean isPrefix(Set<Integer> scc, int p, MatrixCol graph) {
		if (scc.contains(p)) {
			return true;
		}
		SparseIntArray outs = graph.getColumn(p);
		for (int i=0; i <outs.size() ; i++) {
			int succ = outs.keyAt(i);
			if (isPrefix(scc, succ, graph)) {
				scc.add(p);
				return true;
			}
		}		
		return false;
	}

	private boolean findFreeSCC () {
		long time = System.currentTimeMillis();
		// extract simple transitions to a PxP matrix
		int nbP = pnames.size();
		MatrixCol graph = new MatrixCol(nbP,nbP);
		
		int nbedges = 0;
		for (int tid = 0; tid < flowPT.getColumnCount() ; tid++) {
			SparseIntArray hPT = flowPT.getColumn(tid);
			SparseIntArray hTP = flowTP.getColumn(tid);
			if (hPT.size() == 1 && hTP.size() == 1 && hPT.valueAt(0)==1 && hTP.valueAt(0)==1 && ! untouchable.get(hPT.keyAt(0)) && ! untouchable.get(hTP.keyAt(0))) {				
				graph.set(hTP.keyAt(0), hPT.keyAt(0), 1);
				nbedges++;
			}						
		}
		
		List<List<Integer>> sccs = kosarajuSCC(nbP, graph);
		sccs.removeIf(s -> s.size() == 1);
		
		if (sccs.isEmpty()) {
			return false;
		}
		int nbcovered = sccs.stream().collect(Collectors.summingInt(scc->scc.size()));		
		System.out.println("Graph (trivial) has "+nbedges+ " edges and " + nbP + " vertex of which " + + nbcovered + " / " + nbP + " are part of one of the " + sccs.size() +" SCC in " + (System.currentTimeMillis()- time) + " ms");

		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();
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
		if (DEBUG==2) FlowPrinter.drawNet(this);
		return true;
	}

	private List<List<Integer>> kosarajuSCC(int nbP, MatrixCol graph) {
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
		for (int p = 0 ; p < nbP ; p++) {
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

	private void visitNodeBis(MatrixCol graph, List<Integer> curScc, int cur, Set<Integer> visited) {
		if (visited.add(cur)) {
			curScc.add(cur);
			SparseIntArray col = graph.getColumn(cur);
			for (int i=0 ; i < col.size() ; i++) {
				visitNodeBis(graph, curScc,  col.keyAt(i), visited);
			}				
		}
	}

	private void visitNode(MatrixCol graph, Stack<Integer> stack, int p, Set<Integer> visited) {
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
	
	public MatrixCol getFlowPT() {
		return flowPT;
	}
	public MatrixCol getFlowTP() {
		return flowTP;
	}
	public List<Integer> getMarks() {
		return marks;
	}
	public List<String> getPnames() {
		return pnames;
	}
	public List<String> getTnames() {
		return tnames;
	}
	
	// computes a list of integers corresponding to a subset of places, which form an initially empty syphon.
	// the empty set => there are no initially unmarked syphons
	private static Set<Integer> computeEmptySyphon (StructuralReduction srori) {
		long time = System.currentTimeMillis();
		StructuralReduction sr = srori.clone();
		// step 1 : reduce net by removing marked places entirely from the picture
		{
			List<Integer> todrop = new ArrayList<>(sr.getPnames().size());
			for (int i = sr.getMarks().size()-1 ; i >= 0 ; i--) {
				if (sr.getMarks().get(i)>0) {
					todrop.add(i);
				}
			}
			sr.dropPlaces(todrop, false, false);
		}
		// iterate reduction of unfeasible parts
		{
			int doneIter =0;
			do {
				doneIter =0;
				Set<Integer> todropP = new TreeSet<>();
				Set<Integer> todropT = new TreeSet<>();

				for (int tid=sr.getTnames().size()-1 ; tid >= 0 ; tid --) {
					if (sr.getFlowTP().getColumn(tid).size()==0) {
						// discard this transition, it cannot feed anybody
						todropT.add(tid);
						doneIter++;
					} else if (sr.getFlowPT().getColumn(tid).size()==0) {
						SparseIntArray tp = sr.getFlowTP().getColumn(tid);
						// discard the transition, but also it's whole post set
						for (int i=0, e = tp.size() ; i < e ; i++) {
							todropP.add(tp.keyAt(i));							
						}
						doneIter++;
						todropT.add(tid);
					}
				}
				if (!todropT.isEmpty()) {
					sr.dropTransitions(new ArrayList<>(todropT), false);
				}
				if (!todropP.isEmpty()) {
					sr.dropPlaces(new ArrayList<>(todropP), false, false);
				}
			} while (doneIter >0);
		}
		if (sr.getPnames().isEmpty()) {
			// fail
			return new HashSet<>();
		}
		//Logger.getLogger("fr.lip6.move.gal").info("Computed a system of "+sr.getPnames().size()+"/"+ srori.getPnames().size() + " places and "+sr.getTnames().size()+"/"+ srori.getTnames().size() + " transitions for Syphon test. " + (System.currentTimeMillis()-time) +" ms");
		
		if (! sr.getPnames().isEmpty()) {
			// okay so we have a syphon here
			
			Set<Integer> res = new HashSet<>();
			for (String pname : sr.getPnames()) {
				res.add(srori.getPnames().indexOf(pname));
			}
			System.out.println("Deduced a syphon composed of "+sr.getPnames().size()+" places in "+ (System.currentTimeMillis()-time) +" ms");
			return res;
		}
		System.out.println("Deduced a syphon composed of "+sr.getPnames().size()+" places in "+ (System.currentTimeMillis()-time) +" ms");
		return new HashSet<>();
	}

	public int fusePlaces(List<Integer> base, List<Integer> next) {
		Set<Integer> todel = new TreeSet<>((x,y)->-Integer.compare(x, y));
		// now work with the tflowTP to find transitions feeding pj we need to update
		MatrixCol tflowTP = flowTP.transpose();
		MatrixCol tflowPT = flowPT.transpose();
		
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
			todel.add(itarg);
		}
		List<String> prem = new ArrayList<>();
		for (int i : todel) {
			prem.add(dropPlace(i, tflowPT, tflowTP));
		}
		flowPT = tflowPT.transpose();
		flowTP = tflowTP.transpose();
		System.out.println("Place Fusion rule removed "+prem.size()+ " places  "+ (DEBUG >=1 ? (" : "+ prem ) : ""));
		if (DEBUG==2) FlowPrinter.drawNet(this);
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
		int id = marks.size();
		marks.add(m);
		pnames.add(name);
		for (int tid =0, e=tnames.size() ; tid < e ; tid++ ) {
			updateCol(flowPT.getColumn(tid), id, pids);
			updateCol(flowTP.getColumn(tid), id, pids);
		}
		flowPT.addRow();
		flowTP.addRow();
		if (DEBUG==2) FlowPrinter.drawNet(this);
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

}
