package fr.lip6.move.gal.ltsmin.ui.handlers;

import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.serialization.SerializationUtil;
import fr.lip6.move.ui.utils.FileUtils;

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
	
			Specification spec = SerializationUtil.fileToGalSystem(file.getRawLocationURI().getPath());
			
			try {
				String path = file.getRawLocationURI().getPath();
				String workFolder = file.getParent().getLocation().toPortableString();
				String modelName = file.getName().replace(".gal", "");
				String outpath =  workFolder + "/" + modelName + ".ord";
				
				Logger.getLogger("fr.lip6.move.gal").info("Running " + getServiceName() + " on target :" + path + " to output in "+outpath);
								
				workOnSpec(spec,workFolder);
				
				FileUtils.refreshDisplay(outpath);
				
				Logger.getLogger("fr.lip6.move.gal").info("GAL model written to file : " +outpath);
				
			} catch (Exception e) {
				MessageDialog.openWarning(
						window.getShell(),
						getServiceName(),
						"Problem running order, we raised exception "+e);
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public abstract void workOnSpec(Specification s, String outpath) throws Exception ;
	
	protected abstract String getServiceName() ;
}
