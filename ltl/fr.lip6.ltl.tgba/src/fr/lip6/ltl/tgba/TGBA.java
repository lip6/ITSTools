package fr.lip6.ltl.tgba;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.expr.Expression;

public class TGBA {
	private List<List<TGBAEdge>> mat=new ArrayList<>();
	private int initial=-1;
	private List<String> aps=new ArrayList<>();
	private int nbAcceptance=-1;
	private List<String> properties = new ArrayList<>();
	private List<String> stateDesc = new ArrayList<>();
	
	public TGBA(int numberOfStates) {
		mat = new ArrayList<>();		
		while (stateDesc.size() < numberOfStates) {
			stateDesc.add("");
			mat.add(new ArrayList<>());
		}
	}

	public void setInitial(int state) {
		this.initial=state;
	}

	public int getInitial() {
		return initial;
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
		if (mat.size() <= id) {
			((ArrayList)stateDesc).ensureCapacity(id+1);
			((ArrayList)mat).ensureCapacity(id+1);
			while (mat.size() <= id) {
				mat.add(new ArrayList<>());
				stateDesc.add("");
			}
		}
		stateDesc.set(id, info);
	}

	public void addEdge(int src, int dest, Expression e, SparseBoolArray sb) {
		List<TGBAEdge> cur = mat.get(src);
		cur.add(new TGBAEdge(src,dest,e,sb));
	}
	
	public List<String> getAPs() {
		return aps;
	}

	public List<List<TGBAEdge>> getEdges() {
		return mat;
	}
	
	@Override
	public String toString() {
		return "TGBA [mat=" + mat + ", initial=" + initial + ", aps=" + aps + ", nbAcceptance=" + nbAcceptance
				+ ", properties=" + properties + ", stateDesc=" + stateDesc + "]";
	}
	
}
