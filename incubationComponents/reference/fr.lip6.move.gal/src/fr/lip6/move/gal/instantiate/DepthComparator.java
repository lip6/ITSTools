package fr.lip6.move.gal.instantiate;

import java.util.Comparator;

import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.TypeDeclaration;

public class DepthComparator implements Comparator<TypeDeclaration> {

	private DepthFathomer df = new DepthFathomer();

	@Override
	public int compare(TypeDeclaration td1, TypeDeclaration td2) {
		Integer d1 = new Integer(df.getDepth(td1));
		int cmp = d1.compareTo(df.getDepth(td2));
		if (cmp != 0 || d1==0) {
			return cmp;
		}
		return new Integer(((CompositeTypeDeclaration)td1).getSynchronizations().size()).compareTo(((CompositeTypeDeclaration)td2).getSynchronizations().size());
	}
	
}
