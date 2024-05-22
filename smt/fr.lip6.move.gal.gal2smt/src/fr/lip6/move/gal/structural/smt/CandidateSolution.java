package fr.lip6.move.gal.structural.smt;

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

	public void updateWitness(SolverState solver, String prefix) {
		if (reply != SMTReply.SAT) {
			return;
		}
		state = null;
		parikh = null;
		por = null;

		SparseIntArray values = solver.getValues(prefix);

		if (values == null) {
			reply = SMTReply.REAL;
			return;
		}

		switch (prefix) {
		case "s":
			state = values;
			break;
		case "t":
			parikh = values;
			break;
		case "o":
			por = values;
			break;
		default:
			throw new IllegalArgumentException("Unknown prefix : " + prefix);
		}
	}

	public boolean hasWitness() {
		return reply == SMTReply.SAT && parikh != null;
	}

	public SparseIntArray getState() {
		return state;
	}

	public SparseIntArray getParikh() {
		return parikh;
	}

	@Override
	public String toString() {
		return "[" + reply + (reply == SMTReply.SAT && parikh != null ? "witness" : "nowitness") + "]";
	}

	public SparseIntArray getOrder() {
		return por;
	}

}
