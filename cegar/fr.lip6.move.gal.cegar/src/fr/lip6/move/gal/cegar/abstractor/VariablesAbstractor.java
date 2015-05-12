package fr.lip6.move.gal.cegar.abstractor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.support.Support;

/**
 * A utility class (static only) for GAL abstractions with a method to abstract the Specification's Variables using the specified Support.
 * @author Anonymous
 *
 */
public class VariablesAbstractor {
	
	/**
	 * Remove all Variables of the Specification that are not include in the specified Support. 
	 * 
	 * @param specification
	 * @param toKeep
	 */
	public static void abstractUsingSupport(Specification specification, Support toKeep) {

		Logger log = Logger.getLogger("fr.lip6.move.gal");
		
		for(TypeDeclaration typeDeclaration : specification.getTypes()) {
			
			if(typeDeclaration instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration)typeDeclaration;
				List<Variable> toRemoveVariables = new ArrayList<Variable>();
				
				List<String> removedVariables = new ArrayList<String>();
				
				for(Variable variable : gal.getVariables()) {
					if( ! toKeep.contains(variable)) {
						toRemoveVariables.add(variable);
						removedVariables.add(variable.getName());
					}
				}

				log.info("Abstracting GAL, removed " + removedVariables.size() +" / " + gal.getVariables().size() +" variable(s) ");
				log.fine("Removed : " + removedVariables.toString());

				gal.getVariables().removeAll(toRemoveVariables);
				
			}
		}
	}
}
