package fr.lip6.move.gal.pnml.togal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lip6.move.pnml.symmetricnet.finiteEnumerations.FEConstant;
import fr.lip6.move.pnml.symmetricnet.integers.NumberConstant;
import fr.lip6.move.pnml.symmetricnet.multisets.NumberOf;
import fr.lip6.move.pnml.symmetricnet.partitions.PartitionElement;
import fr.lip6.move.pnml.symmetricnet.terms.NamedSort;
import fr.lip6.move.pnml.symmetricnet.terms.OperatorDecl;
import fr.lip6.move.pnml.symmetricnet.terms.Sort;
import fr.lip6.move.pnml.symmetricnet.terms.SortDecl;
import fr.lip6.move.pnml.symmetricnet.terms.Term;
import fr.lip6.move.pnml.symmetricnet.terms.UserOperator;
import fr.lip6.move.pnml.symmetricnet.terms.UserSort;

public class HLUtils {

	public static List<Integer> getConstantIndexes(PartitionElement el) {
		List<Integer> indexes = new ArrayList<>();
		for (Term decl : el.getPartitionelementconstants()) {
			if (decl instanceof UserOperator) {
				indexes.add(getConstantIndex((UserOperator) decl));
			} else {
				getLog().warning("Expected an enumeration constant as child of PartitionElement, encountered " + decl.getClass().getName());
			}
		}
		return indexes;
	}
	
	public static int getConstantIndex(UserOperator uo) {
		OperatorDecl decl = uo.getDeclaration();
		if (decl instanceof FEConstant) {
			FEConstant fec = (FEConstant) decl;
			int index = fec.getSort().getElements().indexOf(fec);
			return index;
		} else {
			getLog().warning("Expected an enumeration constant as child of UserOperator, encountered " + decl.getClass().getName());
		}
		return 0;
	}

	public static int getCardinality(NumberOf no) {
		Term num = no.getSubterm().get(0);

		if (num instanceof NumberConstant) {
			NumberConstant nc = (NumberConstant) num;
			return nc.getValue().intValue();
		} else {
			getLog().warning("Expected a number constant in first son of NumberOf expression; inferring cardinality 1.");			
		}

		return 1;
	}
	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public static Sort getTrueSort(Sort sort) {
		if (sort instanceof UserSort) {
			UserSort us = (UserSort) sort;
			SortDecl sdecl = us.getDeclaration();
			if (sdecl instanceof NamedSort) {
				NamedSort names = (NamedSort) sdecl;
				Sort trueSort = names.getSortdef();
				return trueSort;
			}
		}

		return sort;
	}


}
