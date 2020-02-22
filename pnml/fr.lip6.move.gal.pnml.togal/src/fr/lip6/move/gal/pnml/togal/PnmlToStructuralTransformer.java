package fr.lip6.move.gal.pnml.togal;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import fr.lip6.move.gal.nupn.NotAPTException;
import fr.lip6.move.gal.nupn.PTNetReader;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.IOrderVisitor;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI;

public class PnmlToStructuralTransformer {

	private IOrder order;
	private boolean foundNupn = false;

	public SparsePetriNet transformPT (URI uri) throws IOException {
		long time = System.currentTimeMillis();

		PTNetReader ptreader = new PTNetReader();
		PetriNet ptnet = null; 
		try {
			ptnet = ptreader.loadFromXML(new BufferedInputStream(new FileInputStream(uri.getPath())));
		} catch (NotAPTException ex) {
			getLog().info("Detected file is not PT type :" + ex.getRealType());
			return null;
		}
		PTSRTransformer trans = new PTSRTransformer(); 
		try {
			SparsePetriNet spn = trans.transform(ptnet);
			// Scan for nupn tool specific unit info
			if (ptreader.getOrder() != null) {
				getLog().info("Found NUPN structural information;");
				foundNupn = true;
				order = ptreader.getOrder();

				Set<String> exist = order.getAllVars();
				Set<String> notexist = new HashSet<>();
				List<String> supp = new ArrayList<>();
				// Make sure info is complete
				for (String var : spn.getPnames()) {
					if (! exist.contains(var)) {
						supp.add(var);
						notexist.add(var);
					}
				}
				if (! notexist.isEmpty() ) {
					getLog().info("Completing missing partition info from NUPN : creating a component with " + supp);
					VarOrder vo = new VarOrder(supp, "missing");
					order.accept( new IOrderVisitor<Void>() {

						@Override
						public Void visitComposite(CompositeGalOrder o) {
							o.getChildren().add(vo);
							return null;
						}

						@Override
						public Void visitVars(VarOrder varOrder) {
							varOrder.getVars().addAll(notexist);
							return null;
						}
					});
				}
			}
			getLog().info("Parsed PT model containing " + spn.getPlaceCount() + " places and " + spn.getTransitionCount() + " in " + (System.currentTimeMillis() - time) + " ms.");
			return spn;
		} catch (ArithmeticException e) {
			throw new IOException("Annotations (e.g. markings) use too many bits cannot handle transformation accurately.");
		}
	}

	public SparseHLPetriNet transformHLPN (URI uri) throws IOException {
		long debut = System.currentTimeMillis();


		final PnmlImport pim = new PnmlImport();
		try {
			ModelRepository.getInstance().createDocumentWorkspace(uri.getPath());
		} catch (final InvalidIDException e1) {
			e1.printStackTrace();
		}

		pim.setFallUse(true);
		HLAPIRootClass imported;
		try {
			imported = (HLAPIRootClass) pim.importFile(uri.getPath());
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		getLog().info("Load time of PNML (colored model parsed with PNMLFW) : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

		final PetriNetDocHLAPI root = (PetriNetDocHLAPI) imported;

		assert(root.getNets().size()==1);

		HLSRTransformer trans = new HLSRTransformer();
		SparseHLPetriNet gal = null;
		try {
			gal = trans.transform(root.getNets().get(0));
		} catch (ArithmeticException e) {
			throw new IOException("Annotations (e.g. markings) use too many bits cannot handle transformation accurately.");
		}

		if (trans.getOrder() != null) {
			getLog().info("Computed order based on color domains.");
			getLog().fine("Computed order using colors : " + trans.getOrder());

			order = trans.getOrder();
		}

		try {
			ModelRepository.getInstance().destroyCurrentWorkspace();
		} catch (VoidRepositoryException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return gal;
	} 


	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public IOrder getOrder() {
		return order;
	}
	public boolean foundNupn() {
		return foundNupn;
	}

}
