package fr.lip6.move.gal.cegar.abstractor;

import java.util.logging.Logger;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;

public class BoundComputer {
	
	public static int compute(Specification spec) {
		int max = 0;
		Logger log = Logger.getLogger("fr.lip6.move.gal");

		for (TypeDeclaration declaration : spec.getTypes()) {
			if (declaration instanceof GALTypeDeclaration) {
				GALTypeDeclaration gtd = (GALTypeDeclaration) declaration;
				for (Variable var : gtd.getVariables()) {
					IntExpression ie = var.getValue();
					if (ie instanceof Constant) {
						int current = ((Constant) ie).getValue();
						if (current > max)
							max = current;
					}
				}
				for (ArrayPrefix arr : gtd.getArrays()) {
					for (IntExpression ie : arr.getValues()) {
						if (ie instanceof Constant) {
							int current = ((Constant) ie).getValue();
							if (current > max)
								max = current;
						}
					}
				}
			}
		}
		log.info("Bounding GAL to K = " + max);

		return max;
	}
	
}
