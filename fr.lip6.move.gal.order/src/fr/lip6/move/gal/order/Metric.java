//package fr.lip6.move.gal.order;
//
//import java.util.BitSet;
//
//import fr.lip6.move.gal.semantics.DependencyMatrix;
//import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
//import fr.lip6.move.gal.semantics.INextBuilder;
//
//import fr.lip6.move.gal.order.order.IOrder;
//
//public class Metric implements IMetric {
//	
//	public double compute(INextBuilder inb, IOrder order) {
//		IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(inb);
//
//		DependencyMatrix m =  idnb.getDeterministicDependencyMatrix();
//
//		return normalizedWeightedEventSpan(m, order);
//	}
//	
//	private static double normalizedWeightedEventSpan(DependencyMatrix m, IOrder order) {
//		double weightedEventSPan = 0;
//		double C = numberOfVariablesInUse(m);
//
//		for (int t = 0; t < m.nbRows(); t++) {
//			BitSet union = new BitSet();
//			union.or(m.getRead(t));
//			union.or(m.getWrite(t));
//
//			weightedEventSPan += span(union, order) * ((C - minVariable(union, order))/(C/2.));
//		}
//
//		double V = numberOfVariables(m);
//		return weightedEventSPan/(C*(V - C));
//	}
//
//	private static int numberOfVariables(DependencyMatrix m) {
//		return m.nbCols(); //TODO attention vérifier que c'est le nombre de colonnes
//	}
//
//	private static int numberOfVariablesInUse(DependencyMatrix m) {
//		BitSet union = new BitSet();
//		for (int i = 0; i < m.nbRows(); i++) {
//			union.or(m.getRead(i));
//			union.or(m.getWrite(i));
//		}
//		
//		return union.cardinality();
//	}
//	
//	// minimum index of variable used in transition, or 0 if none
//	private static int minVariable(BitSet union, IOrder order) {
//		if (union.isEmpty())
//			return 0;
//		
//		int min = Integer.MAX_VALUE;
//		for (int i = union.nextSetBit(0); i >= 0; i = union.nextSetBit(i+1)) {
//			int j = order.permute(i);
//			
//			min = Integer.min(min, j);
//		}
//		
//		return min;
//	}
//	
//	private static double span(BitSet union, IOrder order) {
//		if (union.isEmpty())
//			return 0;
//
//		int min = Integer.MAX_VALUE;
//		int max = Integer.MIN_VALUE;
//		for (int i = union.nextSetBit(0); i >= 0; i = union.nextSetBit(i+1)) {
//			int j = order.permute(i);
//
//			min = Integer.min(min, j);
//			max = Integer.max(max, j);
//		}
//			
//		
//		return max-min+1;
//	}
//}
