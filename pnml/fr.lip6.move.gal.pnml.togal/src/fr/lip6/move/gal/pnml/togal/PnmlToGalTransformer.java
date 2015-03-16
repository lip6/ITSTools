package fr.lip6.move.gal.pnml.togal;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.nupn.NotAPTException;
import fr.lip6.move.gal.nupn.PTNetReader;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI;
import fr.lip6.move.serialization.SerializationUtil;

public class PnmlToGalTransformer {

	private IOrder order;




	public Specification transform(URI uri) throws Exception {
		//IOException, BadFileFormatException, UnhandledNetType, ValidationFailedException, InnerBuildException, OCLValidationFailed, OtherException, AssociatedPluginNotFound, InvalidIDException, VoidRepositoryException {

		long debut = System.currentTimeMillis();

		Specification spec = GalFactory.eINSTANCE.createSpecification();

		PTNetReader ptreader = new PTNetReader();
		PetriNet ptnet = null; 
		try {
			ptnet = ptreader.loadFromXML(new BufferedInputStream(new FileInputStream(uri.getPath())));
		} catch (NotAPTException ex) {
			getLog().info("Detected file is not PT type :" + ex.getRealType());
		}


		if (ptnet == null) {

			final PnmlImport pim = new PnmlImport();
			try {
				ModelRepository.getInstance().createDocumentWorkspace(uri.getPath());
			} catch (final InvalidIDException e1) {
				e1.printStackTrace();
			}

			pim.setFallUse(true);
			HLAPIRootClass imported = (HLAPIRootClass) pim.importFile(uri.getPath());
			getLog().info("Load time of PNML (colored model parsed with PNMLFW) : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

			final PetriNetDocHLAPI root = (PetriNetDocHLAPI) imported;

			assert(root.getNets().size()==1);



			HLGALTransformer trans = new HLGALTransformer(); 	
			GALTypeDeclaration gal = trans.transform(root.getNets().get(0), spec);
			if (trans.getOrder() != null) {
				getLog().info("Computed order based on color domains : " + trans.getOrder());
				order = trans.getOrder();
			}

			try {
				ModelRepository.getInstance().destroyCurrentWorkspace();
			} catch (VoidRepositoryException e) {
				e.printStackTrace();
			}


		} else {
			PTGALTransformer trans = new PTGALTransformer(); 	
			GALTypeDeclaration gal = trans.transform(ptnet);
			spec.getTypes().add(gal);

			// Scan for nupn tool specific unit info
			if (ptreader.getOrder() != null) {
				getLog().info("Found NUPN structural information;");
				order = ptreader.getOrder();
			}
		}

		//	BoundsBuilder.boundVariable(spec, 4);


		return spec;
	}




	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}




	public IOrder getOrder() {
		return order;
	}

}
