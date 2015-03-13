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
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.nupn.NotAPTException;
import fr.lip6.move.gal.nupn.NupnReader;
import fr.lip6.move.gal.nupn.PTNetReader;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.UnhandledNetType;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.ptnet.Page;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.ptnet.ToolInfo;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
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

		Specification spec = GalFactory.eINSTANCE.createSpecification();

		PTNetReader ptreader = new PTNetReader();
		PetriNet ptnet = null; 
		try {
			ptnet = ptreader.loadFromXML(new BufferedInputStream(new FileInputStream(file.getLocationURI().getPath())));
		} catch (NotAPTException ex) {
			getLog().info("Detected file is not PT type :" + ex.getRealType().getLiteral());
		}


		if (ptnet == null) {

			final PnmlImport pim = new PnmlImport();
			try {
				ModelRepository.getInstance().createDocumentWorkspace(file.getLocationURI().getPath());
			} catch (final InvalidIDException e1) {
				e1.printStackTrace();
			}

			pim.setFallUse(true);
			HLAPIRootClass imported = (HLAPIRootClass) pim.importFile(file.getLocationURI().getPath());
			getLog().info("Load time of PNML (colored model parsed with PNMLFW) : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

			final PetriNetDocHLAPI root = (PetriNetDocHLAPI) imported;

			assert(root.getNets().size()==1);



			HLGALTransformer trans = new HLGALTransformer(); 	
			GALTypeDeclaration gal = trans.transform(root.getNets().get(0), spec);
			if (trans.getOrder() != null) {
				getLog().info("Applying computed order/decomposition : " + trans.getOrder());
				//				CompositeBuilder.getInstance().decomposeWithOrder(gal, trans.getOrder());
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
				getLog().info("Found NUPN structural information; decomposing GAL"); 
				CompositeBuilder.getInstance().decomposeWithOrder(gal, ptreader.getOrder());
			}
		}

		//	BoundsBuilder.boundVariable(spec, 4);


		writeGALfile(file, spec);

		return spec;
	}

	private void writeGALfile(IFile file, Specification spec)
			throws FileNotFoundException, IOException {	
		String outpath = file.getRawLocationURI().getPath() + ".gal";

		SerializationUtil.systemToFile(spec,outpath);
	}



	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
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
