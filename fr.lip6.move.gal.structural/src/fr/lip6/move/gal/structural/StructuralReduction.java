package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.util.SparseIntArray;
import fr.lip6.move.gal.Specification;
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
	private int maxArcValue;

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
		
//		FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		
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
			
			if (totaliter > 0) {
				System.out.println("Pre-agglomeration after "+ (iter) + " with "+ totaliter+ " Pre rules applied. Total rules applied " + total);				
			} else {
				System.out.println("No additional pre-agglomerations found "+ (iter++));
			}
			
			int sym = ruleSymmetricChoice();
			if (sym > 0) {
				System.out.println("Symmetric choice reduction at "+ (iter) + " with "+ sym + " rule applications. Total rules  " + total);				
			}
			totaliter += sym;
			total += totaliter;
		} while (totaliter > 0);
		System.out.println("Applied a total of "+total+" rules. Remains "+ pnames.size() + " /" +initP + " variables (removed "+ (initP - pnames.size()) +") and now considering "+ flowPT.getColumnCount() + "/" + initT + " (removed "+ (initT - flowPT.getColumnCount()) +") transitions.");
		FlowPrinter.drawNet(flowPT, flowTP, marks, pnames, tnames);
		
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
		for (int pid = pnames.size() - 1 ; pid >= 0 ; pid--) {
			SparseIntArray from = tflowPT.getColumn(pid);
			SparseIntArray to = tflowTP.getColumn(pid);
			if (from.equals(to) || (to.size()==0 && marks.get(pid)==0) ) {
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
			boolean isMarked = marks.get(pid) != 0 ; 
			
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
						if (isMarked && Fids.size() > 1) {
							ok =false;
							break;
						}
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
				if (isMarked) {
					// fire the single F continuation until the place is empty
					int fid = Fids.get(0);
					int cur = marks.get(pid);
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
							int v = preF.valueAt(ip);
							marks.set(p, marks.get(p)+ v);
						}
					}
				}
				
				
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
