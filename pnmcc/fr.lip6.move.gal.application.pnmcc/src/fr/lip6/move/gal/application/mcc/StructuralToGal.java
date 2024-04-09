package fr.lip6.move.gal.application.mcc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import fr.lip6.move.gal.AF;
import fr.lip6.move.gal.AG;
import fr.lip6.move.gal.AU;
import fr.lip6.move.gal.AX;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.BoundsProp;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.EF;
import fr.lip6.move.gal.EG;
import fr.lip6.move.gal.EU;
import fr.lip6.move.gal.EX;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LTLFuture;
import fr.lip6.move.gal.LTLGlobally;
import fr.lip6.move.gal.LTLNext;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.LTLUntil;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;

public class StructuralToGal {

	
	public static Property toGal(AtomicProp atom, EList<Variable> variables) {
		Property img = GalFactory.eINSTANCE.createProperty();
		img.setName(atom.getName());
		fr.lip6.move.gal.AtomicProp aprop = GalFactory.eINSTANCE.createAtomicProp();
		aprop.setPredicate(toGal(atom.getExpression(),variables));		
		img.setBody(aprop);
		return img;
	}

	public static Property toGal(fr.lip6.move.gal.structural.Property prop, EList<Variable> variables) {
		Property img = GalFactory.eINSTANCE.createProperty();
		img.setName(prop.getName());
		if (prop.getType() == PropertyType.INVARIANT) {	
			if (prop.getBody().getOp() == Op.EF) {
				ReachableProp rprop = GalFactory.eINSTANCE.createReachableProp();
				rprop.setPredicate(toGal(((BinOp)prop.getBody()).left,variables));
				img.setBody(rprop);
			} else if (prop.getBody().getOp() == Op.AG) {
				InvariantProp rprop = GalFactory.eINSTANCE.createInvariantProp();
				rprop.setPredicate(toGal(((BinOp)prop.getBody()).left,variables));								
				img.setBody(rprop);
			} else if (prop.getBody().getOp() == Op.BOOLCONST) {
				InvariantProp rprop = GalFactory.eINSTANCE.createInvariantProp();
				rprop.setPredicate(toGal(prop.getBody(),variables));
				img.setBody(rprop);
			}
		} else if (prop.getType() == PropertyType.CTL) {
			CTLProp rprop = GalFactory.eINSTANCE.createCTLProp();
			rprop.setPredicate(toGal(prop.getBody(),variables));
			img.setBody(rprop);
		} else if (prop.getType() == PropertyType.LTL) {
			LTLProp rprop = GalFactory.eINSTANCE.createLTLProp();
			rprop.setPredicate(toGal(prop.getBody(),variables));
			img.setBody(rprop);
		} else if (prop.getType() == PropertyType.BOUNDS) {
			BoundsProp bp = GalFactory.eINSTANCE.createBoundsProp();
			bp.setTarget(toGalInt(prop.getBody(), variables));
			img.setBody(bp);
		} else if (prop.getType() == PropertyType.DEADLOCK) {
			CTLProp rprop = GalFactory.eINSTANCE.createCTLProp();
			rprop.setPredicate(toGal(prop.getBody(),variables));
			img.setBody(rprop);
		}
		return img;
	}


	public static BooleanExpression toGal(Expression expr, EList<Variable> variables) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			
			switch (bin.getOp()) {
			case GEQ:case GT:case EQ:case NEQ:case LEQ:case LT: {
				// int expression children
				IntExpression il = toGalInt(bin.left,variables);
				IntExpression ir = toGalInt(bin.right,variables);
				return GF2.createComparison(il, toComparisonOp(bin.getOp()), ir);
			}
			case NOT: {
				return GF2.not(toGal(bin.left,variables));
			}
			// unary CTL temporal operators
			case EF: {
				EF ef = GalFactory.eINSTANCE.createEF();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			case AF: {
				AF ef = GalFactory.eINSTANCE.createAF();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			case EG: {
				EG ef = GalFactory.eINSTANCE.createEG();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			case AG: {
				AG ef = GalFactory.eINSTANCE.createAG();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			case EX: {
				EX ef = GalFactory.eINSTANCE.createEX();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			case AX: {
				AX ef = GalFactory.eINSTANCE.createAX();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			// unary LTL temporal operators
			case F: {
				LTLFuture ef = GalFactory.eINSTANCE.createLTLFuture();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			case X: {
				LTLNext ef = GalFactory.eINSTANCE.createLTLNext();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			case G: {
				LTLGlobally ef = GalFactory.eINSTANCE.createLTLGlobally();
				ef.setProp(toGal(bin.left, variables));
				return ef;
			}
			// binary CTL/LTL until
			case EU: {
				EU ef = GalFactory.eINSTANCE.createEU();
				ef.setLeft(toGal(bin.left, variables));
				ef.setRight(toGal(bin.right, variables));
				return ef;
			}
			case AU: {
				AU ef = GalFactory.eINSTANCE.createAU();
				ef.setLeft(toGal(bin.left, variables));
				ef.setRight(toGal(bin.right, variables));
				return ef;
			}
			case U : {
				LTLUntil ef = GalFactory.eINSTANCE.createLTLUntil();
				ef.setLeft(toGal(bin.left, variables));
				ef.setRight(toGal(bin.right, variables));
				return ef;
			}
			default :
			}
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<BooleanExpression> resc = new ArrayList<>(); 
			for (int i=0; i < nop.nbChildren() ; i++) {
				resc.add(toGal(nop.childAt(i), variables));	
			}
			
			// avoid building unbalanced deep trees			
			while (resc.size() > 1) {
				List<BooleanExpression> next = new ArrayList<>();
				for (int i=0 ; i < resc.size() ; i+=2) {					
					if (i == resc.size()-1) {
						next.add(resc.get(i));
					} else {
						BooleanExpression l = resc.get(i);
						BooleanExpression r = resc.get(i+1);
						if (nop.getOp() == Op.AND) {
							next.add( GF2.and(l,r));
						} else {
							next.add( GF2.or(l,r));
						}
					}
				}
				resc=next;
			}
			BooleanExpression sum = resc.get(0);			
			return sum;
		} else if (expr.getOp() == Op.BOOLCONST) {
			if (expr.getValue()==1) {
				return GalFactory.eINSTANCE.createTrue();
			} else {
				return GalFactory.eINSTANCE.createFalse();
			}
		} else if (expr.getOp() == Op.APREF) {
			return toGal(((AtomicPropRef)expr).getAp().getExpression(), variables);
		}
		throw new UnsupportedOperationException();
	}


	private static ComparisonOperators toComparisonOp(Op op) {
		switch (op)  {
		case GEQ :
			return ComparisonOperators.GE;
		case GT :
			return ComparisonOperators.GT;
		case NEQ :
			return ComparisonOperators.NE;
		case EQ :
			return ComparisonOperators.EQ;
		case LT :
			return ComparisonOperators.LT;
		case LEQ :
			return ComparisonOperators.LE;
		default :
		}		
		return null;
	}


	private static IntExpression toGalInt(Expression expr, EList<Variable> variables) {
		if (expr.getOp() == Op.CONST) {
			return GF2.constant(expr.getValue());
		} else if (expr.getOp() == Op.PLACEREF) {
			return GF2.createVariableRef(variables.get(expr.getValue()));
		} else if (expr.getOp() == Op.ADD) {
			List<IntExpression> resc = new ArrayList<>(); 
			for (int i=0; i < expr.nbChildren() ; i++) {
				resc.add(toGalInt(expr.childAt(i), variables));	
			}
			
			// avoid building unbalanced deep trees			
			while (resc.size() > 1) {
				List<IntExpression> next = new ArrayList<>();				
				for (int i=0 ; i < resc.size() ; i+=2) {					
					if (i == resc.size()-1) {
						next.add(resc.get(i));
					} else {
						IntExpression l = resc.get(i);
						IntExpression r = resc.get(i+1);
						next.add(GF2.createBinaryIntExpression(l, "+", r));
					}
				}
				resc=next;				
			}
			IntExpression sum = resc.get(0);			
			return sum;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			switch (bin.getOp()) {
			case DIV :
				return GF2.createBinaryIntExpression(toGalInt(bin.left, variables), "/", toGalInt(bin.right, variables));
			case MOD :
				return GF2.createBinaryIntExpression(toGalInt(bin.left, variables), "%", toGalInt(bin.right, variables));
			case MINUS :
				return GF2.createBinaryIntExpression(toGalInt(bin.left, variables), "-", toGalInt(bin.right, variables));				
			}			
		}
		throw new UnsupportedOperationException();
	}

}
