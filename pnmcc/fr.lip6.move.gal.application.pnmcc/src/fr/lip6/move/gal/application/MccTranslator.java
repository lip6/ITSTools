package fr.lip6.move.gal.application;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.pnml.togal.PnmlToGalTransformer;
import fr.lip6.move.gal.support.Support;

public class MccTranslator {

	
	
	private Specification spec;
	private IOrder order;
	private String folder;
	private String examination;
	private Support simplifiedVars = new Support();
	
	public MccTranslator(String pwd, String examination) {
		this.folder = pwd;
		this.examination = examination;
	}


	public Specification getSpec() {
		return spec;
	}
	/**
	 * Sets the spec and order attributes, spec is set to result of PNML tranlsation and order is set to null if no nupn/computed order is available.
	 * @param folder input folder absolute path, containing a model.pnml file
	 * @param reversible set to true to add P >= 0 constraints in guards of transitions adding to P, ensuring predecessor relation is inverse to succ. 
	 * @throws IOException if file can't be found
	 */
	public void transformPNML() throws IOException {
		File ff = new File(folder+ "/"+ "model.pnml");
		if (ff != null && ff.exists()) {
			getLog().info("Parsing pnml file : " + ff.getAbsolutePath());

			PnmlToGalTransformer trans = new PnmlToGalTransformer();
			spec = trans.transform(ff.toURI());
			order = trans.getOrder();
			// SerializationUtil.systemToFile(spec, ff.getPath() + ".gal");
			if (spec.getMain() == null) {
				spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
			}
		} else {
			throw new IOException("Cannot open file "+ff.getAbsolutePath());
		}
	}
	
	
	public boolean applyOrder(Support supp) {
		if (order != null) {
			getLog().info("Applying decomposition ");
			getLog().fine(order.toString());
			Specification saved = EcoreUtil.copy(spec);
			try {
				supp.addAll(CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), order));
			} catch (Exception e) {
				getLog().warning("Could not apply decomposition. Using flat GAL structure.");
				e.printStackTrace();
				spec = saved ;
				return false ;
			}
			return true;
		}
		return false;
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
		
	}


	public boolean hasStructure() {
		return order != null;
	}


	public void flattenSpec(boolean withHierarchy) {
		if (!applyOrder(simplifiedVars)) {
			simplifiedVars.addAll(GALRewriter.flatten(spec, true));
		}
	}

	
}
