package fr.lip6.move.gal.structural;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;


/**
 * Implement Berthelot's structural reduction rules.
 * @author ythierry
 *
 */


public class StructuralReduction {

	
	public void reduce (FlowMatrix fm) {
		MatrixCol trans = fm.getSparseIncidenceMatrix();
		MatrixCol places = trans.transpose();
		//ruleSeqTrans(trans,places);
		ruleSeqPlace(trans,places, new  SparseIntArray());
		
	}

	private void ruleSeqPlace(MatrixCol trans, MatrixCol places, SparseIntArray marks) {
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
			
		}
	}

	private void ruleSeqTrans(MatrixCol trans, MatrixCol places) {
		for (int itr = 0 ; itr < trans.getColumnCount() ; itr++) {
			SparseIntArray col = trans.getColumn(itr);
			
		}
	}

}
