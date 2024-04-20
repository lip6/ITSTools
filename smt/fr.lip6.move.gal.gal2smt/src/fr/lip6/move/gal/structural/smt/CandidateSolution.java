package fr.lip6.move.gal.structural.smt;

import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.ext.C_get_model;
import org.smtlib.sexpr.ISexpr;
import org.smtlib.sexpr.ISexpr.ISeq;

import android.util.SparseIntArray;

public class CandidateSolution {
	private static final int DEBUG = 0;
	private SMTReply reply = SMTReply.SAT;
	private SparseIntArray state;
	private SparseIntArray parikh;
	private SparseIntArray por;
	
		
	public SMTReply getReply() {
		return reply;
	}

	public void setReply(SMTReply reply) {
        this.reply = reply;
		if (reply == SMTReply.UNSAT) {
			state = null;
			parikh = null;
			por = null;
		}
	}
	
	public void updateWitness(ISolver solver) {
		if (reply != SMTReply.SAT) {
            return;
        }
		SparseIntArray state = new SparseIntArray();
		SparseIntArray parikh = new SparseIntArray();
		SparseIntArray order = new SparseIntArray();
		boolean hasReals = queryVariables(state, parikh, order, solver);
		if (hasReals) {
            reply = SMTReply.REAL;
        } else {
            this.parikh = parikh;
            this.state = state;
            this.por = order;
        }
	}
	
	private static boolean queryVariables(SparseIntArray state, SparseIntArray parikh, SparseIntArray order, ISolver solver) {
		boolean hasReals = false;
		IResponse r = new C_get_model().execute(solver);
		if (r.isError()) {
			return true;
		}
		if (r instanceof ISeq) {
			ISeq seq = (ISeq) r;
			for (ISexpr v : seq.sexprs()) {
				if (v instanceof ISeq) {
					ISeq vseq = (ISeq) v;
					if (vseq.sexprs().get(1).toString().startsWith("t")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value ;
						try { value = (int) Float.parseFloat( vseq.sexprs().get(vseq.sexprs().size()-1).toString() ); }
						catch (NumberFormatException e) { 
							hasReals = true;
							value = 1; 
						}
						if (value != 0) 
							parikh.put(tid, value);
					} else if (vseq.sexprs().get(1).toString().startsWith("s")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value;
						try { value = (int) Float.parseFloat( vseq.sexprs().get(vseq.sexprs().size()-1).toString() ); }
						catch (NumberFormatException e) { 
							hasReals = true;
							value = 1; 
						}
						if (value != 0) 
							state.put(tid, value);							
					} else if (vseq.sexprs().get(1).toString().startsWith("o")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value;
						try {
							String s = vseq.sexprs().get(vseq.sexprs().size()-1).toString();
							s = s.replaceAll("[() ]", "");
							value = (int) Float.parseFloat( s ); 
						}
						catch (NumberFormatException e) {
							//System.out.println( vseq.sexprs().get(vseq.sexprs().size()-1).toString());
							value = 1; 
						}
						if (value != 0) 
							order.put(tid, value);							
					}
				}
			}
		}
		if (DEBUG >= 1) {
			System.out.println("Read State : " + state);
			System.out.println("Read Parikh : " + parikh);
			System.out.println("Read Order : " + order);
		}
		return hasReals;
	}

	@Override
	public String toString() {
		return "[" + reply + (reply==SMTReply.SAT && parikh!=null?"witness":"nowitness")+ "]";
	}
	
	
	
}
