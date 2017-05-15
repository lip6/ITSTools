package fr.lip6.move.gal.pnml.togal;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.nupn.NotAPTException;
import fr.lip6.move.gal.nupn.PTNetReader;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.IOrderVisitor;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI;

public class PnmlToGalTransformer {

	private IOrder order;
	private boolean reversible = false;
	private boolean foundNupn = false;




	public Specification transform(URI uri) throws IOException {
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

			// still wip
		//	ParameterBindingHelper.analyze(root.getNets().get(0));
			
			HLGALTransformer trans = new HLGALTransformer();
			trans.setReversible(reversible);
			try {
				GALTypeDeclaration gal = trans.transform(root.getNets().get(0), spec);
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


		} else {
			PTGALTransformer trans = new PTGALTransformer(); 
			trans.setReversible(reversible);
			try {
				GALTypeDeclaration gal = trans.transform(ptnet);
				spec.getTypes().add(gal);
				// Scan for nupn tool specific unit info
				if (ptreader.getOrder() != null) {
					getLog().info("Found NUPN structural information;");
					foundNupn = true;
					order = ptreader.getOrder();
					
					Set<String> exist = order.getAllVars();
					Set<String> notexist = new HashSet<>();
					Support supp = new Support();
					// Make sure info is complete
					for (Variable var : gal.getVariables()) {
						if (! exist.contains(var.getName())) {
							supp.add(var);
							notexist.add(var.getName());
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

			} catch (ArithmeticException e) {
				throw new IOException("Annotations (e.g. markings) use too many bits cannot handle transformation accurately.");
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
	public void setReversible(boolean reversible) {
		this.reversible  = reversible;
	}
	public boolean foundNupn() {
		return foundNupn;
	}

}
