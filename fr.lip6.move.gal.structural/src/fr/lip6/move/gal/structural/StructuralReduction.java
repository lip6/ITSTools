package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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


public class StructuralReduction {

	private List<Integer> marks;
	private IDeterministicNextBuilder inb;
	private MatrixCol flowPT;
	private MatrixCol flowTP;
	private List<String> tnames;
	private List<String> pnames;
	private int maxArcValue;

	private static final int DEBUG = 0;
	
	public StructuralReduction(IDeterministicNextBuilder idnb) {
		inb = idnb;
		FlowMatrix fm = new MatrixBuilder(idnb).getMatrix();
		marks = new ArrayList<>(idnb.getInitial());
		flowPT = fm.getFlowPT();
		flowTP = fm.getFlowTP();
		pnames = new ArrayList<>(idnb.getVariableNames());
		tnames = new ArrayList<>();
		int sz = inb.getDeterministicNext().size();
		for (int i=0 ; i < sz ; i++) {
			tnames.add("t"+i);
		}
		maxArcValue = findMax(flowPT);
		maxArcValue = Math.max(findMax(flowTP),maxArcValue);
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
	
	public Specification rebuildSpecification () {
		return SpecBuilder.buildSpec(flowPT, flowTP, pnames, tnames, marks);
	}
	
	public int reduce () throws NoDeadlockExists {
		//ruleSeqTrans(trans,places);
		int initP = pnames.size();
		int initT = tnames.size();
		
		if (DEBUG==2) FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		
		long time = System.currentTimeMillis();
		int total = 0;
		int totaliter=0;
		int iter =0;
		do {
			do {
				totaliter=0;
				totaliter += ruleReducePlaces();
//				if (totaliter > 0) {
//					FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
//				}
				totaliter += ruleReduceTrans();
				
				totaliter += ruleImplicitPlace();
				
				totaliter += rulePostAgglo(false,true);
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
					if (DEBUG==2) FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
					System.out.println("Symmetric choice reduction at "+ (iter) + " with "+ sym + " rule applications. Total rules  " + total+ " place count " + pnames.size() + " transition count " + tnames.size());				
				}
			}
			
			
			if (totaliter == 0) {
				totaliter += rulePostAgglo(false,false);
			}
		
			if (totaliter == 0) {
				totaliter += rulePostAgglo(true,false);
			}
			
			System.out.flush();
		} while (totaliter > 0);
		System.out.println("Applied a total of "+total+" rules in "+ (System.currentTimeMillis() - time)+ " ms. Remains "+ pnames.size() + " /" +initP + " variables (removed "+ (initP - pnames.size()) +") and now considering "+ flowPT.getColumnCount() + "/" + initT + " (removed "+ (initT - flowPT.getColumnCount()) +") transitions.");
		if (DEBUG==2) FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		System.out.flush();
		return total;
	}
	
	
	private int ruleReduceTrans() throws NoDeadlockExists {
		int reduced = ensureUnique(flowPT, flowTP, tnames, null); 
		if (reduced > 0) {
			System.out.println("Reduce isomorphic transitions removed "+ reduced +" transitions.");
		}
		for (int i = 0; i < flowPT.getColumnCount() ; i++) {
			if (flowPT.getColumn(i).size()==0 && flowPT.getColumn(i).size()==0) {
				throw new NoDeadlockExists();
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
		for (int trid=mPT.getColumnCount()-1 ; trid >= 0 ; trid--) {
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
		for (int td : todel) {
			names.remove(td);
			mPT.deleteColumn(td);
			mTP.deleteColumn(td);
			if (init != null) {
				init.remove(td);
			}
		}
		return todel.size();
	}

	private int ruleReducePlaces() {
		int totalp = 0;
		// find constant marking places
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();
		// reverse ordered set of tindexes to kill
		Set<Integer> todelTrans = new TreeSet<>((x,y) -> -Integer.compare(x, y));

		// do this scan and update first to ensure no updates to flowPT/flowTP in emptyPlaces are messed up
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
			SparseIntArray from = tflowPT.getColumn(pid);
			SparseIntArray to = tflowTP.getColumn(pid);
			
			// empty initially marked places that control their output fully
			if (to.size() ==0 && marks.get(pid)!=0 && from.size() == 1 && flowPT.getColumn(from.keyAt(0)).size()==1) {
				// make sure empty place does its job fully
				int val = from.valueAt(0);
				int mark = marks.get(pid);
				if (mark % val != 0) {
					marks.set(pid, (mark / val) * val);
				}
				if (DEBUG>=1) System.out.println("Firing immediate continuation of initial place "+pnames.get(pid) + " emptying place using " + tnames.get(from.keyAt(0)) + " index " + from.keyAt(0));
				emptyPlaceWithTransition(pid, from.keyAt(0));
			}
		}
		
		// now scan for isomorphic/redundant/useless/constant places
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
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
			if (from.equals(to) || noTrueInputs || (to.size()==0 && marks.get(pid)==0) ) {
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
				// delete line for p
				tflowPT.deleteColumn(pid);
				tflowTP.deleteColumn(pid);
				pnames.remove(pid);
				// System.out.println("Removing "+pid+":"+remd);
				marks.remove(pid);
				totalp++;
			} else if (from.size() == 0) {
				// sink place behavior
				// delete line for p
				tflowPT.deleteColumn(pid);
				tflowTP.deleteColumn(pid);
				pnames.remove(pid);
				// System.out.println("Removing "+pid+":"+remd);
				marks.remove(pid);
				totalp++;
			} 
		}
		totalp += ensureUnique(tflowPT, tflowTP, pnames, marks);
		
		if (totalp > 0) {
			// reconstruct updated flow matrices
			tflowPT.transposeTo(flowPT);
			tflowTP.transposeTo(flowTP);
			// delete transitions
			for (int tid : todelTrans) {
				flowPT.deleteColumn(tid);
				flowTP.deleteColumn(tid);
				tnames.remove(tid);
			}
			System.out.println("Constant places removed "+totalp + " places and " + todelTrans.size() + " transitions.");
		}
		return totalp;
	}

	private int ruleImplicitPlace () {
		int totalp = 0;
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();		
		List<String> deleted = new ArrayList<>();
		// find a place
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
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
			if (inducedBy(other,tfeedP,tflowPT,tflowTP,seen))  {
				// Hurray ! P is implicit !
				tflowPT.deleteColumn(pid);
				tflowTP.deleteColumn(pid);
				deleted.add(pnames.remove(pid));
				marks.remove(pid);
				tflowPT.transposeTo(flowPT);
				tflowTP.transposeTo(flowTP);
				totalp++;
			}
		}
		if (totalp >0) {
			System.out.println("Implicit places reduction removed "+totalp+" places :"+ deleted);
			if (DEBUG==2) FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		}
		return totalp;
	}
	
	public void dropPlaces (List<Integer> todrop) {
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();	
		List<String> deleted = new ArrayList<>();
		for (int i = todrop.size() - 1 ; i >= 0; i--) {
			int pid= todrop.get(i);
			// Hurray ! P is implicit !
			tflowPT.deleteColumn(pid);
			tflowTP.deleteColumn(pid);
			deleted.add(pnames.remove(pid));
			marks.remove(pid);
		}
		tflowPT.transposeTo(flowPT);
		tflowTP.transposeTo(flowTP);
		int totalp = deleted.size();
		if (totalp >0) {
			System.out.println("Implicit places reduction (with SMT) removed "+totalp+" places :"+ deleted);
			if (DEBUG==2) FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		}
	}
	
	private boolean inducedBy(int pid, int tcause, MatrixCol tflowPT, MatrixCol tflowTP, BitSet seen) {
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
			if (inducedBy(predid, tcause, tflowPT, tflowTP,newseen)) {
				return true;
			}
		}
		
		return false;
	}

	private int rulePostAgglo(boolean doComplex, boolean doSimple) {
		int total = 0;
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();
		long time = System.currentTimeMillis();
		for (int pid = 0 ; pid < pnames.size() ; pid++) {
			SparseIntArray fcand = tflowPT.getColumn(pid);
			SparseIntArray hcand = tflowTP.getColumn(pid);
			if (fcand.size() == 0 || hcand.size() == 0) {
				continue;
			}
			// refuse to expand anything that is not 1 to 1
			if (doSimple && (fcand.size() > 1 || hcand.size() > 1)) {			
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

			for (int hi=0; hi < hcand.size() ; hi++) {
				int hid = hcand.keyAt(hi);
				// Make sure no transition is both input and output for p
				SparseIntArray hPT = flowPT.getColumn(hid);				
				if (hPT.get(pid)!=0) {
					ok = false;
					break;
				}
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

			if (DEBUG>=1) System.out.println("Net is Post-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);
			if (isMarked) {
				// fire the single F continuation until the place is empty
				int fid = fcand.keyAt(0);

				emptyPlaceWithTransition(pid, fid);
				// System.out.println("Pushed tokens out of "+pnames.get(pid));
			}


			agglomerateAround(pid, Hids, Fids);
			if (DEBUG==2) FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
			total++;
			flowPT.transposeTo(tflowPT);			
			flowTP.transposeTo(tflowTP);
			if (doComplex && total > 100) break;
			
			long deltat = System.currentTimeMillis() - time;
			if (deltat >= 60000) {
				System.out.println("Performed "+total + " Post agglomeration using F-continuation condition.");
				time = System.currentTimeMillis();
			}
			
		}
		
		
		if (total != 0) {
			System.out.println("Performed "+total + " Post agglomeration using F-continuation condition.");
		}
		
		return total;
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
					continue;
				} else {
					// we are a feeder into P
					Hids.add(tid);
					// we want H be a singleton, to ease HF-interchangeability
					if (Hids.size() > 1) {
						ok =false;
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
			if (Fids.isEmpty() || Hids.isEmpty()) {
				// empty
				continue;
			}
			if (!ok) {
				continue;
			} else {
				if (DEBUG>=1) System.out.println("Net is Pre-aglomerable in place id "+pid+ " "+pnames.get(pid) + " H->F : " + Hids + " -> " + Fids);
				
				agglomerateAround(pid, Hids, Fids);
				if (DEBUG>=2)  FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
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
			ints.add(i);
		}
		Map<Integer,List<Integer>> byNbOutputs = ints.stream().collect(Collectors.groupingBy(a -> tflowPT.getColumn(a).size()));
		
		Map<Integer,Integer> toFuse = new HashMap<>();
		
		for (Entry<Integer, List<Integer>> ent : byNbOutputs.entrySet()) {
			int nbt = ent.getKey();
			List<Integer> list = ent.getValue();
			for (int i = 0; i < list.size() ; i++) {
				int pi = list.get(i);
				if (toFuse.containsKey(pi)) 
					continue;
				SparseIntArray piouts = tflowPT.getColumn(pi);
				
				
				for (int j = i+ 1 ; j < list.size() ; j++ ) {
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
}
