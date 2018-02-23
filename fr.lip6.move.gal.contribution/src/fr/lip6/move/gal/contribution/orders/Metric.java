package fr.lip6.move.gal.contribution.orders;

import java.util.BitSet;

import fr.lip6.move.gal.semantics.DependencyMatrix;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;

public class Metric {

	//TODO remplacer double par une classe genre tuple personnalisé permettant des comparaisons floues sur plusieurs métriques (paramétrable par tuple -> double)
	
	//TODO bof le paramètre order, peut on utiliser un adapteur et passer un objet matrice
	//TODO permutée (sans forcément la permuter)
	//TODO on voudrait pouvoir calculer les metrics sans order
	//TODO en fait on peut avec un order identity
	public double compute(INextBuilder inb, IOrder order) {
		IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(inb);

		DependencyMatrix m =  idnb.getDeterministicDependencyMatrix();

		return sumOfSpansNormalized(m, order);
	}

	//DOC plus c'est proche de 1 et mieux c'est
	private static double sumOfSpansNormalized(DependencyMatrix m, IOrder order) {
		double sum = 0;
		double minimumSum = 0;

		for (int tindex = 0; tindex < m.nbRows(); tindex++) {
			sum += spanRW(m.getRead(tindex), m.getWrite(tindex), order);
			minimumSum += minSpanRW(m.getRead(tindex), m.getWrite(tindex));
		}
		
		return minimumSum/sum;
	}

	//TODO On peut peut être trouver mieux
	private static double minSpanRW(BitSet read, BitSet write) {
		return read.cardinality() + write.cardinality();
	}

	private static double spanRW(BitSet read, BitSet write, IOrder order) {
		int min_read = -1;
		for (int i = read.nextSetBit(0); i >= 0; i = read.nextSetBit(i+1))
			min_read = Math.min(min_read, order.permute(i));
		
		int min_write = -1;
		for (int i = write.nextSetBit(0); i >= 0; i = write.nextSetBit(i+1))
			min_write = Math.min(min_write, order.permute(i));

		int min_readwrite = Math.min(min_read, min_write);
		
		// nothing in this event
		if (min_readwrite == -1)
			return 0;		
		
		int max_write = -1;
		for (int i = write.nextSetBit(0); i >= 0; i = write.nextSetBit(i+1))
			max_write = Math.max(max_write, order.permute(i));

		// no write
		if (max_write == -1) {
			// event span for read
			int max_read = -1;
			for (int i = read.nextSetBit(0); i >= 0; i = read.nextSetBit(i+1))
				max_read = Math.max(max_read, order.permute(i));
			
			return min_readwrite - max_read + 1;
		}
		
		// cas non dégénéré
		return max_write - min_readwrite + 1;
	}

	//TODO implémenter sum of tops
	private static double sumOfTops(DependencyMatrix m, IOrder order) {
		double sum = 0;
		
		for (int tindex = 0; tindex < m.nbRows(); tindex++) {
			sum += top(m.getRead(tindex), m.getWrite(tindex), order);
		}

		return m.nbRows()*m.nbCols() / sum;
	}

	private static double top(BitSet read, BitSet write, IOrder order) {
		int max_index = -1;
		
		for (int i = read.nextSetBit(0); i >= 0; i = read.nextSetBit(i+1)) {
			max_index = Math.max(max_index, order.permute(i)+1);
		}
		
		for (int i = write.nextSetBit(0); i >= 0; i = write.nextSetBit(i+1)) {
			max_index = Math.max(max_index, order.permute(i)+1);
		}

		return max_index;
	}
}
