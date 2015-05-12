package fr.lip6.move.gal.cegar.abstractor;

import java.util.logging.Logger;

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
					int current = 0;
					IntExpression intExpression = var.getValue();
					if (intExpression instanceof Constant) {
						Constant constant = (Constant) intExpression;
						current = constant.getValue();
						if (current > max)
							max = current;
					}
				}
			}
		}
		log.info("Bounding GAL to K = " + max);

		return max;
	}
	
}
