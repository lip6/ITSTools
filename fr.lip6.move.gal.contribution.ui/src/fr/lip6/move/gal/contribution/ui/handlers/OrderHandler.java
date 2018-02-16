package fr.lip6.move.gal.contribution.ui.handlers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import fr.lip6.move.gal.nupn.NotAPTException;
import fr.lip6.move.gal.nupn.PTNetReader;
import fr.lip6.move.pnml.ptnet.PetriNet;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public abstract class OrderHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get workbench window
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		// set selection service
		ISelectionService service = window.getSelectionService();
		// set structured selection
		IStructuredSelection structured = (IStructuredSelection) service.getSelection();

		//check if it is an IFile
		if (structured.getFirstElement() instanceof IFile) {
			// get the selected file
			IFile file = (IFile) structured.getFirstElement();

			try {

				PTNetReader ptreader = new PTNetReader();
				PetriNet ptnet = null; 
				ptnet = ptreader.loadFromXML(new BufferedInputStream(new FileInputStream(file.getRawLocationURI().getPath())));
				

				String path = file.getRawLocationURI().getPath();
				String workFolder = file.getParent().getLocation().toPortableString();
				String modelName = file.getName().replace(".pnml", "");
				String outpath =  workFolder + "/" ;

				Logger.getLogger("fr.lip6.move.gal").info("Running " + getServiceName() + " on target :" + path + " to output in "+outpath);

				workOnSpec(ptnet,outpath);

			//	FileUtils.refreshDisplay(outpath);
				Logger.getLogger("fr.lip6.move.gal").info("Finsished working on file : " +outpath);

			} catch (Exception e) {
				MessageDialog.openWarning(
						window.getShell(),
						getServiceName(),
						"Problem running order, we raised exception "+e);
				e.printStackTrace();
//			} catch (NotAPTException ex) {
//				Logger.getLogger("fr.lip6.move.gal").info("Detected file is not PT type :" + ex.getRealType());
//			}

			}
		}

		return null;
	}

	public abstract void workOnSpec(PetriNet s, String outpath) throws IOException ;

	protected abstract String getServiceName() ;
}
