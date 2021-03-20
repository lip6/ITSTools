package fr.lip6.ltl.tgba;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class TGBA {
	private List<List<TGBAEdge>> mat=new ArrayList<>();
	private int initial=-1;
	private AtomicPropManager apm;
	private int nbAcceptance=-1;
	private List<String> properties = new ArrayList<>();
	private List<String> stateDesc = new ArrayList<>();
	private List<Expression> infStutter = null;
	private List<AtomicProp> atoms = new ArrayList<>();
	
	public TGBA(int numberOfStates,AtomicPropManager apm) {
		mat = new ArrayList<>();		
		while (stateDesc.size() < numberOfStates) {
			stateDesc.add("");
			mat.add(new ArrayList<>());
		}
		this.apm = apm;
	}

	public void setInitial(int state) {
		this.initial=state;
	}

	public int getInitial() {
		return initial;
	}
	
	public int getNbAcceptance() {
		return nbAcceptance;
	}
	
	public void setAPnames(List<String> apnames) {
		atoms.clear();
		for (String apn : apnames) {
			atoms.add(apm.findAP(apn));
		}
	}
	
	public void setAcceptSize(int nbAcceptance) {
		this.nbAcceptance = nbAcceptance;
	}

	public List<String> getProperties() {
		return properties;
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
		return atoms ;
	}

	public AtomicPropManager getApm() {
		return apm;
	}
	
	public List<List<TGBAEdge>> getEdges() {
		return mat;
	}
	
	@Override
	public String toString() {
		return "TGBA [mat=" + mat + ", initial=" + initial + ", aps=" + atoms + ", nbAcceptance=" + nbAcceptance
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
		

		
		Map<String, Integer> atomMap = collectAtoms();
		
		
		pw.print("AP: "+atoms.size());
		for (AtomicProp ap : atoms) {
			pw.print(" \"" + ap.getName() + "\"");
		}
		
		pw.println();
		
		if (nbAcceptance <= 1) {
			pw.println("acc-name: Buchi");
		} else {
			pw.println("acc-name: generalized-Buchi "+nbAcceptance);
		}
		
		pw.print("Acceptance: "+nbAcceptance+ " ");
		boolean first = true;
		for (int i=0; i < nbAcceptance ; i++) {
			if (first) first=false;
			else pw.print("&");
			pw.print("Inf("+i+")");
		}
		if (nbAcceptance == 0) {
			pw.print("t");
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
					pw.print(" {");
					for (int i=0,ie=arc.getAcceptance().size() ; i < ie ; i++) {
						if (i >0) {
							pw.print(" ");
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

	public Map<String, Integer> collectAtoms() {
		Map<String,Integer> atomMap = new HashMap<>();
		atoms.clear();
		for (List<TGBAEdge> list : mat) {
			for (TGBAEdge arc : list) {
				testAndCollectAtoms(arc.getCondition(), atomMap);
			}
		}
		return atomMap;
	}

	private Void testAndCollectAtoms(Expression e, Map<String, Integer> atomMap) {
		switch (e.getOp()) {
		case BOOLCONST :			
			return null;
		case APREF :
		{
			AtomicPropRef ar= (AtomicPropRef) e;
			String name = ar.getAp().getName();
			Integer index = atomMap.get(name);
			if (index == null) {
				atomMap.put(name, atomMap.size());
				atoms.add(ar.getAp());
			}
			return null;
		}
		default :
			e.forEachChild(c -> testAndCollectAtoms(c, atomMap));
			return null;
		}
	}

	private void printExprHOAF(Expression e, PrintWriter pw, Map<String, Integer> atomMap) {		
		
		switch (e.getOp()) {
		case APREF:
			{
				AtomicPropRef ar= (AtomicPropRef) e;
				String name = ar.getAp().getName();	
				Integer index = atomMap.get(name);
				pw.print(index);
				return;
			}			
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
