package fr.lip6.move.gal.structural;

import android.util.SparseIntArray;
import uniol.apt.analysis.invariants.MatrixCol;


/* Based off :
 * File:   Reducer.cpp
 * Author: srba
 *
 * Created on 15 February 2014, 10:50
 * Original file is released under GPL v3.
 * 
 */

/**
 * Strongly based on verifypn Reducer functionality, as integrated in Tapaal's verifypn tool.
 * Source file : revision 199 of 
 * https://bazaar.launchpad.net/~verifypn-maintainers/verifypn/new-trunk/view/head:/PetriEngine/Reducer.cpp
 * @author Jiri Srba, Yann Thierry-Mieg
 *
 */


public class StructuralReduction {

	
	public void reduce (FlowMatrix fm) {
		MatrixCol trans = fm.getSparseIncidenceMatrix();
		MatrixCol places = trans.transpose();
		ruleSeqTrans(trans,places);
		ruleSeqPlace(trans,places);
		
	}

	private void ruleSeqPlace(MatrixCol trans, MatrixCol places) {
		
	}

	private void ruleSeqTrans(MatrixCol trans, MatrixCol places) {
		for (int itr = 0 ; itr < trans.getColumnCount() ; itr++) {
			SparseIntArray col = trans.getColumn(itr);
			
		}
	}

}
