package fr.lip6.ltl.tgba;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;
import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.MatrixCol;

public class TGBA {
	private MatrixCol<List<TGBAEdge>> mat=new MatrixCol<> (0,0);
	private int initial=-1;
	private List<String> aps=new ArrayList<>();
	private int nbAcceptance=-1;
	private List<String> properties = new ArrayList<>();
	private List<String> stateDesc = new ArrayList<>();
	
	public TGBA(int numberOfStates) {
		mat = new MatrixCol<> (numberOfStates,numberOfStates);
		while (stateDesc.size() < numberOfStates) {
			stateDesc.add("");
		}
	}

	public void setInitial(int state) {
		this.initial=state;
	}

	public void setAPs(List<String> aps) {
		this.aps = new ArrayList<>(aps);
	}

	public void setAcceptSize(int nbAcceptance) {
		this.nbAcceptance = nbAcceptance;
	}

	public void addProperties(List<String> properties) {
		this.properties.addAll(properties);
	}

	public void addState(int id, String info) {
		// grow as needed
		if (mat.getColumnCount() <= id) {
			((ArrayList)stateDesc).ensureCapacity(id+1);
			
			while (mat.getColumnCount() <= id) {
				mat.addRow();
				mat.appendColumn(new SparseArray<>());
				stateDesc.add(null);
			}
		}
		stateDesc.set(id, info);
	}

	public void addEdge(int src, int dest, Expression e, SparseBoolArray sb) {
		List<TGBAEdge> cur = mat.get(dest, src);
		if (cur == null) {
			cur = new ArrayList<>();
		}
		cur.add(new TGBAEdge(e,sb));
		mat.set(dest, src, cur);
	}
	
	public List<String> getAPs() {
		return aps;
	}

	@Override
	public String toString() {
		return "TGBA [mat=" + mat + ", initial=" + initial + ", aps=" + aps + ", nbAcceptance=" + nbAcceptance
				+ ", properties=" + properties + ", stateDesc=" + stateDesc + "]";
	}
	
}
