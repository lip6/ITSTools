package fr.lip6.move.gal.pnml.togal.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.flatten.popup.actions.ConsoleAdder;
import fr.lip6.move.gal.instantiate.BoundsBuilder;
import fr.lip6.move.gal.nupn.NupnReader;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.UnhandledNetType;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.ptnet.Page;
import fr.lip6.move.pnml.ptnet.ToolInfo;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lip6.move.serialization.SerializationUtil;

public class ImportFromPNMLToGAL implements IObjectActionDelegate {


	private List<IFile> files = new ArrayList<IFile>();

	/**
	 * Constructor for Action1.
	 */
	public ImportFromPNMLToGAL() {
		super();
	}
	
	private Shell shell;
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		setShell(targetPart.getSite().getShell());
	}
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		String filenames = "" ;
		ConsoleAdder.startConsole();
		for (IFile file : files) {

			try {
				Specification spec = transform(file);
			} catch (Exception e) {
				MessageDialog.openInformation(
						shell,
						"PNMLToGAL",
						"ImportToGAL failed." + 			e.getMessage());
				getLog().warning("ImportToGAL failed." + 			e.getMessage());
				e.printStackTrace();
			}

			try {
				ModelRepository.getInstance().destroyCurrentWorkspace();
			} catch (VoidRepositoryException e) {
				e.printStackTrace();
			}

			getLog().info("ImportToGAL was executed on file : " + file.getName());
			filenames += file.getName() + "; ";
		}
		MessageDialog.openInformation(
				shell,
				"PNMLToGAL",
				"ImportToGAL was executed on files : " + filenames);
		ConsoleAdder.stopconsole();
	}

	public Specification transform(IFile file) throws Exception {
	//IOException, BadFileFormatException, UnhandledNetType, ValidationFailedException, InnerBuildException, OCLValidationFailed, OtherException, AssociatedPluginNotFound, InvalidIDException, VoidRepositoryException {
		long debut = System.currentTimeMillis();

		final PnmlImport pim = new PnmlImport();
		try {
			ModelRepository.getInstance().createDocumentWorkspace(file.getLocationURI().getPath());
		} catch (final InvalidIDException e1) {
			e1.printStackTrace();
		}

		pim.setFallUse(true);
		HLAPIRootClass imported = (HLAPIRootClass) pim.importFile(file.getLocationURI().getPath());
		getLog().info("Load time of PNML: " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

		
		Specification spec = GalFactory.eINSTANCE.createSpecification();

		if (testIsSN(imported)) {
			final PetriNetDocHLAPI root = (PetriNetDocHLAPI) imported;

			assert(root.getNets().size()==1);



			HLGALTransformer trans = new HLGALTransformer(); 	
			GALTypeDeclaration gal = trans.transform(root.getNets().get(0), spec);
			if (trans.getOrder() != null) {
				getLog().info("Applying computed order/decomposition : " + trans.getOrder());
//				CompositeBuilder.getInstance().decomposeWithOrder(gal, trans.getOrder());
			}


		} else if (testIsPT(imported)) {
			final fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI root =  (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) imported;

			assert(root.getNets().size()==1);

			PTGALTransformer trans = new PTGALTransformer(); 	
			GALTypeDeclaration gal = trans.transform(root.getNets().get(0));
			spec.getTypes().add(gal);

			// Scan for nupn tool specific unit info
			for (Page page : root.getNets().get(0).getPages()) {
				for (ToolInfo ti : page.getToolspecifics()) {
					if ( "nupn".equals(ti.getTool()) ) {
						IOrder order = NupnReader.loadFromXML(ti.getFormattedXMLBuffer());
						if (order != null) {
							getLog().info("Found NUPN structural information; decomposing GAL"); 
//							CompositeBuilder.getInstance().decomposeWithOrder(gal, order);
						}
					}
				}
			}
			
		} else {
			throw new UnhandledNetType("only valid for SN high level nets and PT nets.");
		}

	//	BoundsBuilder.boundVariable(spec, 4);
		
		
		writeGALfile(file, spec);

		try {
			ModelRepository.getInstance().destroyCurrentWorkspace();
		} catch (VoidRepositoryException e) {
			e.printStackTrace();
		}
		
		return spec;
	}
	
	private void writeGALfile(IFile file, Specification spec)
			throws FileNotFoundException, IOException {	
		String outpath = file.getRawLocationURI().getPath() + ".gal";

		SerializationUtil.systemToFile(spec,outpath);
	}

	private boolean testIsSN(HLAPIRootClass imported) {
		return imported
				.getClass()
				.equals(
						fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI.class) ;		
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	
	private boolean testIsPT(HLAPIRootClass imported) {
		return imported
				.getClass()
				.equals(
						fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI.class) ;		
	}

	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		files .clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ts = (IStructuredSelection) selection;
			for (Object s : ts.toList()) {
				if (s instanceof IFile) {
					IFile file = (IFile) s;
					if (file.getFileExtension().equals("pnml")) {
						this.files.add(file);
					}
				}
			}
		}
	}

}
