package fr.lip6.move.gal.structural;

import java.util.List;

import fr.lip6.move.gal.structural.expr.Expression;

/**
 * Represents a HLPN Arc, it has a place, a multiplier coefficient, and a list of expressions representing a tuple.
 * For instance, an arc taking from "p" with 2'<$x,3,($y+1)%12> would have place p's index in place, 2 as multiplier,
 *  and the expressions in the tuple. 
 * The tuple has the same number of elements as the domain of p.
 * @author ythierry
 *
 */
public class HLArc {
	private int place;
	private int coeff;
	private List<Expression> cfunc;

	public HLArc(int place, int coeff, List<Expression> cfunc) {
		this.place = place;
		this.coeff = coeff;
		this.cfunc = cfunc;
	}

	public int getPlace() {
		return place;
	}

	public int getCoeff() {
		return coeff;
	}

	public List<Expression> getCfunc() {
		return cfunc;
	}

	@Override
	public String toString() {
		return "[" + place + ":" + coeff + "*" + cfunc + "]";
	}

	public void setPlace(int place) {
		this.place = place;
	}

}
