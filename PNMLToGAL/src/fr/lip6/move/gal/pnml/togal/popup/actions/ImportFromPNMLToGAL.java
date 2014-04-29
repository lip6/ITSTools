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
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.UnhandledNetType;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

		for (IFile file : files) {

			try {
				Specification spec = transform(file);
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

			java.lang.System.err.println("ImportToGAL was executed on file : " + file.getName());
			filenames += file.getName() + "; ";
		}
		MessageDialog.openInformation(
				shell,
				"PNMLToGAL",
				"ImportToGAL was executed on files : " + filenames);
	}

	public Specification transform(IFile file) throws Exception {
	//IOException, BadFileFormatException, UnhandledNetType, ValidationFailedException, InnerBuildException, OCLValidationFailed, OtherException, AssociatedPluginNotFound, InvalidIDException, VoidRepositoryException {
		final PnmlImport pim = new PnmlImport();
		try {
			ModelRepository.getInstance().createDocumentWorkspace(file.getLocationURI().getPath());
		} catch (final InvalidIDException e1) {
			e1.printStackTrace();
		}

		pim.setFallUse(true);
		HLAPIRootClass imported = (HLAPIRootClass) pim.importFile(file.getLocationURI().getPath());

		GALTypeDeclaration s = null;

		if (testIsSN(imported)) {
			final PetriNetDocHLAPI root = (PetriNetDocHLAPI) imported;

			assert(root.getNets().size()==1);




			HLGALTransformer trans = new HLGALTransformer(); 	
			s = trans.transform(root.getNets().get(0));
		} else if (testIsPT(imported)) {
			final fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI root =  (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) imported;

			assert(root.getNets().size()==1);

			PTGALTransformer trans = new PTGALTransformer(); 	
			s = trans.transform(root.getNets().get(0));

		} else {
			throw new UnhandledNetType("only valid for SN high level nets and PT nets." );
		}
		Specification spec = GalFactory.eINSTANCE.createSpecification();
		spec.getTypes().add(s);

		writeGALfile(file, spec);

		return spec;
	}
	private void writeGALfile(IFile file, Specification spec)
			throws FileNotFoundException, IOException {
		String path = file.getRawLocationURI().getPath().split(".pnml")[0];			
		String outpath =  path+".gal";

		//String outpath =  file.getRawLocationURI().getPath()+".flat.gal";

		FileOutputStream out = new FileOutputStream(new File(outpath));
		out.write(0);
		out.close();
		SerializationUtil.systemToFile(spec,outpath);
	}

	private boolean testIsSN(HLAPIRootClass imported) {
		return imported
				.getClass()
				.equals(
						fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI.class) ;		
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
