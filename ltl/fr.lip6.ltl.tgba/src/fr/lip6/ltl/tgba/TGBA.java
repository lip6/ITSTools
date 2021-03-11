package fr.lip6.ltl.tgba;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class TGBA {
	private List<List<TGBAEdge>> mat=new ArrayList<>();
	private int initial=-1;
	private List<AtomicProp> aps=new ArrayList<>();
	private int nbAcceptance=-1;
	private List<String> properties = new ArrayList<>();
	private List<String> stateDesc = new ArrayList<>();
	private List<Expression> infStutter = null;
	
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
	
	public void setAPnames(List<String> apnames) {
		this.aps = new ArrayList<>(apnames.size());
		for (String s : apnames) {
			aps.add(new AtomicProp(s,null));
		}
	}
	
	public void registerAtom(Expression e, int apIndex) {
		this.aps.get(apIndex).setExpression(e);
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
	
	public List<AtomicProp> getAPs() {
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
	
	
	public void exportAsHOA (PrintWriter pw) {

		//example target
//		HOA: v1
//		name: "FG((!p1 & GFp1) M !p2)"
//		States: 2
//		Start: 0
//		AP: 2 "p2" "p1"
//		acc-name: generalized-Buchi 2
//		Acceptance: 2 Inf(0)&Inf(1)
//		properties: trans-labels explicit-labels trans-acc stutter-invariant
		
		pw.println("HOA: v1");
		pw.println("name: \"Formula\"");
		pw.println("States: "+mat.size());
		pw.println("Start: "+initial);
		
		
		Map<Expression,Integer> atomMap = new HashMap<>();
		{
			int id=0;
			for (AtomicProp ap : aps) {
				atomMap.put(ap.getExpression(), id++);
			}
		}
		for (List<TGBAEdge> list : mat) {
			for (TGBAEdge arc : list) {
				testAndCollectAtoms(arc.getCondition(), atomMap);
			}
		}
		
		pw.print("AP: "+aps.size());
		for (AtomicProp ap : aps) {
			pw.print(" \"" + ap.getName() + "\"");
		}
		pw.println();
		
		pw.println("acc-name: Buchi");
		
		pw.print("Acceptance: "+nbAcceptance+ " ");
		boolean first = true;
		for (int i=0; i < nbAcceptance ; i++) {
			if (first) first=false;
			else pw.print("&");
			pw.print("Inf("+i+")");
		}
		pw.println();
		
		pw.print("properties:");
		for (String p : properties) {			
			pw.print(" ");
			pw.print(p);
		}
		pw.println();
		
//		--BODY--
//		State: 0
//		[t] 0
//		[!0&!1] 1
//		State: 1
//		[!0&!1] 1 {1}
//		[!0&1] 1 {0}
//		--END--
		
		
		
		pw.println("--BODY--");
		for (int state=0; state < mat.size(); state++) {
			pw.println("State: "+state);
			for (TGBAEdge arc : mat.get(state)) {
				pw.print("[");
				printExprHOAF(arc.getCondition(),pw,atomMap);
				pw.print("]");

				pw.print(" "+arc.getDest());
				
				if (arc.getAcceptance().size() > 0) {
					pw.print("{");
					for (int i=0,ie=arc.getAcceptance().size() ; i < ie ; i++) {
						if (i >0) {
							pw.print(",");
						}
						pw.print(arc.getAcceptance().keyAt(i));
					}
					pw.print("}");
				}
				pw.println();
			}
		}
		pw.println("--END--");
	}

	private Void testAndCollectAtoms(Expression e, Map<Expression, Integer> atomMap) {
		Integer AP = atomMap.get(e);
		if (AP != null) {			
			return null;
		}
		switch (e.getOp()) {
		case BOOLCONST :			
			return null;
		case AND : case OR :
		case NOT :
			e.forEachChild(c -> testAndCollectAtoms(c, atomMap));
			return null;
		default :
			aps.add(new AtomicProp("s"+aps.size(),e));
			atomMap.put(e, aps.size()-1);
		}
		return null;
	}

	private void printExprHOAF(Expression e, PrintWriter pw, Map<Expression, Integer> atomMap) {
		Integer AP = atomMap.get(e);
		if (AP != null) {
			pw.print(AP);
			return;
		}
		
		switch (e.getOp()) {
		case BOOLCONST :
			if (e.getValue()==0) {
				pw.print("f");
			} else {
				pw.print("t");
			}
			return;
		case AND : case OR :
			if (e.getOp() == Op.OR) {
				pw.print("(");
			}
			for (int cid=0; cid < e.nbChildren() ; cid++) {
				if (cid > 0) {
					if (e.getOp() == Op.AND) {
						pw.print('&');
					} else {
						pw.print('|');
					}
				}
				printExprHOAF(e.childAt(cid), pw,atomMap);
			}
			if (e.getOp() == Op.OR) {
				pw.print(")");
			}
			return;
		case NOT :
			pw.print("!(");
			printExprHOAF(e.childAt(0), pw,atomMap);			
			pw.print(")");
			return;
		default :
			throw new IllegalArgumentException("Unrecognized expression "+ e);
		}
	}

	public void setInfStutterConditions(List<Expression> infStutter) {
		this.infStutter  = infStutter;
	}

	public List<Expression> getInfStutter() {
		return infStutter;
	}

}
