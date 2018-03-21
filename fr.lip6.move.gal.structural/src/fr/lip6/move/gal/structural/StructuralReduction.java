package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.util.SparseIntArray;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.util.MatrixCol;


/**
 * Implement Haddad/Pradat-Peyre structural reduction rules.
 * @author ythierry
 *
 */


public class StructuralReduction {

	private List<Integer> marks;
	private FlowMatrix fm;
	private IDeterministicNextBuilder inb;
	private MatrixCol flowPT;
	private MatrixCol flowTP;
	private List<String> tnames;
	private List<String> pnames;

	public StructuralReduction(IDeterministicNextBuilder idnb) {
		inb = idnb;
		fm = new MatrixBuilder(idnb).getMatrix();
		marks = new ArrayList<>(idnb.getInitial());
		flowPT = fm.getFlowPT();
		flowTP = fm.getFlowTP();
		pnames = new ArrayList<>(idnb.getVariableNames());
		tnames = new ArrayList<>();
		for (int i=0 ; i < inb.getDeterministicNext().size() ; i++) {
			tnames.add("t"+i);
		}
	}
	
	public int reduce () {
		//ruleSeqTrans(trans,places);
		int initP = pnames.size();
		int initT = tnames.size();
		
		FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		
		int total = 0;
		int totaliter=0;
		int iter =0;
		do {
			do {
				totaliter=0;
				totaliter += ruleReducePlaces();
				if (totaliter > 0) {
					FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
				}
				totaliter += ruleReduceTrans();
				totaliter += rulePostAgglo();
				total += totaliter;
				if (totaliter > 0) {
					System.out.println("Iterating post reduction "+ (iter++) + " with "+ totaliter+ " rules applied. Total rules applied " + total);				
				} else {
					System.out.println("Stability for Post agglomeration reached at "+ (iter++));
				}
			} while (totaliter > 0);
			totaliter = 0;
			totaliter += rulePreAgglo();
			total += totaliter;
			if (totaliter > 0) {
				System.out.println("Pre-agglomeration after "+ (iter) + " with "+ totaliter+ " Pre rules applied. Total rules applied " + total);				
			} else {
				System.out.println("No additional pre-agglomerations found "+ (iter++));
			}
			
		} while (totaliter > 0);
		System.out.println("Applied a total of "+total+" rules. Remains "+ pnames.size() + " /" +initP + " variables (removed "+ (initP - pnames.size()) +") and now considering "+ flowPT.getColumnCount() + "/" + initT + " (removed "+ (initT - flowPT.getColumnCount()) +") transitions.");
		FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		
		return total;
	}
	
	
	private int ruleReduceTrans() {
		int reduced = ensureUnique(flowPT, flowTP, tnames, null); 
		if (reduced > 0) {
			System.out.println("Reduce isomorphic transitions removed "+ reduced +" transitions.");
		}
		return reduced;
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
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
			SparseIntArray from = tflowPT.getColumn(pid);
			SparseIntArray to = tflowTP.getColumn(pid);
			if (from.equals(to)) {
				// constant marking place
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
			} 
		}
		totalp += ensureUnique(tflowPT, tflowTP, pnames, marks);
		
		if (totalp > 0) {
			// reconstruct updated flow matrices
			flowPT = tflowPT.transpose();
			flowTP = tflowTP.transpose();
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

	private int rulePostAgglo() {
		int total = 0;
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
					if (flowPT.getColumn(tid).size() > 1) {
						// a transition controlled also by someone else than P
						ok = false;
						break;
					} else {
						// ok we have an F candidate
						Fids.add(tid);
						continue;
					}
				} else {
					// we are a feeder into P
					Hids.add(tid);
					continue;
				}
			}
			if (Fids.isEmpty() || Hids.isEmpty()) {
				// empty
				continue;
			}
			if (!ok) {
				continue;
			} else {
				// System.out.println("Net is P-aglomerable in place id "+pid+ " "+inb.getVariableNames().get(pid) + " H->F : " + Hids + " -> " + Fids);
				
				agglomerateAround(pid, Hids, Fids);
				total++;
			}
			
		}
		
		if (total != 0) {
			System.out.println("Performed "+total + " Post agglomeration using F-continuation condition.");
		}
		
		return total;
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
			// System.out.println("removing transition "+tnames.get(i) +" pre:" + flowPT.getColumn(i) +" post:" + flowTP.getColumn(i));
			flowPT.deleteColumn(i);
			flowTP.deleteColumn(i);
			tnames.remove(i);
		}
		// Now add resulting columns
		for (int hi=0; hi < Hids.size() ; hi++) {
			for (int fi=0; fi < Fids.size() ; fi++) {
				SparseIntArray resPT = HsPT.get(hi).clone();
				SparseIntArray toaddPT = FsPT.get(fi);
				for (int i=0;  i < toaddPT.size() ; i++) {
					int p = toaddPT.keyAt(i);
					if (p != pid) {
						resPT.put(p, resPT.get(p) + toaddPT.valueAt(i));
					}
				}
				flowPT.appendColumn(resPT);
				
				SparseIntArray resTP = FsTP.get(fi).clone();
				SparseIntArray toadd = HsTP.get(hi);
				for (int i=0;  i < toadd.size() ; i++) {
					int p = toadd.keyAt(i);
					if (p != pid) {
						resTP.put(p, resTP.get(p) + toadd.valueAt(i));
					}
				}
				flowTP.appendColumn(resTP);
				
				tnames.add(Hnames.get(hi)+"."+Fnames.get(fi));	
				// System.out.println("added transition "+tnames.get(tnames.size()-1) +" pre:" + flowPT.getColumn(flowPT.getColumnCount()-1) +" post:" + flowTP.getColumn(flowTP.getColumnCount()-1));
			}
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
				// System.out.println("Net is P-aglomerable in place id "+pid+ " "+inb.getVariableNames().get(pid) + " H->F : " + Hids + " -> " + Fids);
				
				agglomerateAround(pid, Hids, Fids);
				tflowPT = flowPT.transpose();
				total++;
			}
			
		}
		
		if (total != 0) {
			System.out.println("Performed "+total + " Pre agglomeration using Quasi-Persistent + HF-interchangeable + Divergent Free condition.");
		}
		
		return total;
	}

	private boolean isStronglyQuasiPersistent(int hid, MatrixCol tflowPT) {
		// sufficient condition : nobody can disable t
		SparseIntArray hPT = flowPT.getColumn(hid);
		SparseIntArray hTP = flowTP.getColumn(hid);
		
		for (int pi = 0; pi < hPT.size() ; pi++) {
			// for every place p, such that h consumes tokens from it
			int pid = hPT.keyAt(pi);
			int vi = hPT.valueAt(pi);
			// look at other consumer ot from p
			SparseIntArray pPT = tflowPT.getColumn(pid);			
			for (int oti =0 ; oti < pPT.size() ; oti++ ) {
				int otid = pPT.keyAt(oti);
				if (otid == hid)
					continue;
				int otv = pPT.valueAt(oti);
				if (flowTP.getColumn(otid).get(pid) < otv) {
					return false;
				}
			}
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
}
