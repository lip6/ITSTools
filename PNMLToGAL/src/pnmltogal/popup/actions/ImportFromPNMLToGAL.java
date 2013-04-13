package pnmltogal.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.gal.System;
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI;

import java.io.File;
import java.io.FileOutputStream;
import fr.lip6.move.serialization.SerializationUtil;

public class ImportFromPNMLToGAL implements IObjectActionDelegate {

	private Shell shell;
	
	private IFile file;

	/**
	 * Constructor for Action1.
	 */
	public ImportFromPNMLToGAL() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {


		final PnmlImport pim = new PnmlImport();
		try {

			ModelRepository.getInstance().createDocumentWorkspace(file.getLocationURI().getPath());

		} catch (final InvalidIDException e1) {

			e1.printStackTrace();
		}
		
		pim.setFallUse(true);
		try {
			HLAPIRootClass imported = (HLAPIRootClass) pim.importFile(file.getLocationURI().getPath());
			
			if (! testIsSN(imported)) {
				MessageDialog.openInformation(
						shell,
						"PNMLToGAL",
						"ImportToGAL failed : only valid for SN high level nets." );
				return;
			}
			final PetriNetDocHLAPI root = (PetriNetDocHLAPI) imported;

			assert(root.getNets().size()==1);
			
			
			
			
			GALTransformer trans = new GALTransformer(); 	
			System s = trans.transform(root.getNets().get(0));
			
			String path = file.getRawLocationURI().getPath().split(".pnml")[0];			
            String outpath =  path+".gal";

            //String outpath =  file.getRawLocationURI().getPath()+".flat.gal";
             
            FileOutputStream out = new FileOutputStream(new File(outpath));
            out.write(0);
			out.close();
			SerializationUtil.systemToFile(s,outpath);
			
			
			
		} catch (Exception e) {
			MessageDialog.openInformation(
					shell,
					"PNMLToGAL",
					"ImportToGAL failed." + 			e.getMessage());
			e.printStackTrace();
		}

		try {
			ModelRepository.getInstance().destroyCurrentWorkspace();
		} catch (VoidRepositoryException e) {
			e.printStackTrace();
		}


		MessageDialog.openInformation(
				shell,
				"PNMLToGAL",
				"ImportToGAL was executed.");
	}

	private boolean testIsSN(HLAPIRootClass imported) {
		return imported
				.getClass()
				.equals(
						fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI.class) ;		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ts = (IStructuredSelection) selection;
			for (Object s : ts.toList()) {
				if (s instanceof IFile) {
					IFile file = (IFile) s;
					if (file.getFileExtension().equals("pnml")) {
						this.file = file;
						return;
					}
				}
			}
		}
	}

}
