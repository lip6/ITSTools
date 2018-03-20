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
 * Implement Berthelot's structural reduction rules.
 * @author ythierry
 *
 */


public class StructuralReduction {

	private MatrixCol trans;
	private MatrixCol places;
	private SparseIntArray marks;
	private FlowMatrix fm;
	private IDeterministicNextBuilder inb;
	private MatrixCol flowPT;
	private MatrixCol flowTP;
	private List<String> tnames;
	private List<String> pnames;
	private List<String> pdeleted;

	public StructuralReduction(IDeterministicNextBuilder idnb) {
		inb = idnb;
		fm = new MatrixBuilder(idnb).getMatrix();
		trans = new MatrixCol(fm.getIncidenceMatrix());
		places = trans.transpose();
		marks = new SparseIntArray();
		List<Integer> initial = idnb.getInitial();
		for (int i = 0 ; i < initial.size(); i++) {
			int v = initial.get(i);
			if (v != 0) {
				marks.append(i, v);
			}
		}		
		flowPT = fm.getFlowPT();
		flowTP = fm.getFlowTP();
		pnames = new ArrayList<>(idnb.getVariableNames());
		tnames = new ArrayList<>();
		for (int i=0 ; i < inb.getDeterministicNext().size() ; i++) {
			tnames.add("t"+i);
		}
		pdeleted = new ArrayList<>();
	}
	
	public int reduce () {
		//ruleSeqTrans(trans,places);
		int initP = pnames.size();
		int initT = tnames.size();
		
		int total = 0;
		int totaliter=0;
		int iter =0;
		do {
			totaliter=0;
			totaliter += ruleReducePlaces();
			totaliter += ruleReduceTrans();
			totaliter += rulePostAgglo();
			total += totaliter;
			if (totaliter > 0) {
				System.out.println("Iterating reduction "+ (iter++));
			} else {
				System.out.println("Stability reached at "+ (iter++));
			}
		} while (totaliter > 0);
			
		System.out.println("Applied a total of "+total+" rules. Removed "+ pdeleted.size() + " /" +initP + " variables and now considering "+ flowPT.getColumnCount() + "/" + initT + " transitions.");
		
		return total;
	}
	
	
	private int ruleReduceTrans() {
		int reduced = ensureUnique(flowPT, flowTP, tnames); 
		if (reduced > 0) {
			System.out.println("Reduce isomorphic transitions removed "+ reduced +" transitions.");
		}
		return reduced;
	}

	private int ensureUnique(MatrixCol mPT, MatrixCol mTP, List<String> names) {
		int reduced;
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
			if (index.contains(tcolTP)) {
				todel.add(trid);
			} else {
				index.add(tcolTP);
			}
		}
		for (int td : todel) {
			names.remove(td);
			mPT.deleteColumn(td);
			mTP.deleteColumn(td);
		}
		reduced = todel.size();
		return reduced;
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
				totalp++;
			} else {
				// check for isomorphic/implicit places
				
			}
		}
		
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
		for (int pid = 0 ; pid < places.getColumnCount() ; pid++) {
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
				System.out.println("Net is P-aglomerable in place id "+pid+ " "+inb.getVariableNames().get(pid) + " H->F : " + Hids + " -> " + Fids);
				
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
					}
				}
				total++;
			}
			
		}
		
		
		return total;
	}

	private int ruleSeqPlace(MatrixCol trans, MatrixCol places, SparseIntArray marks) {
		int totalReduced =0; 
		for (int pid = 0 ; pid < places.getColumnCount() ; pid++) {
			SparseIntArray place = places.getColumn(pid);
			if (place.size() != 2) {
				// find a place, with exactly one input, and one output
				continue;
			}
			if (place.valueAt(0) != - place.valueAt(1)) {
				// weights must be complementary
				continue;
			}
			if (marks.get(pid) != 0) {
				// place must be initially unmarked
				continue;
			}
			// id of the transition h feeding p, of the transition f caused by p
			int hid, fid;
			int val = place.valueAt(0);
			if (val > 0) {
				hid = place.keyAt(0);
				fid = place.keyAt(1);
			} else {
				hid = place.keyAt(1);
				fid = place.keyAt(0);
			}
			SparseIntArray ftrans = trans.getColumn(fid);
			boolean ok = true;
			for (int p = 0 ; p < ftrans.size() ; p++) {
				if (ftrans.keyAt(p) != pid && ftrans.valueAt(p) < 0) {
					ok = false;
					break;
				}
			}
			if (!ok) {
				// transition f has other input places
				continue;
			}
			
			
			// reduce now
			
			totalReduced ++;
		}
		return totalReduced;
	}

	private void ruleSeqTrans(MatrixCol trans, MatrixCol places) {
		for (int itr = 0 ; itr < trans.getColumnCount() ; itr++) {
			SparseIntArray col = trans.getColumn(itr);
			
		}
	}

}
