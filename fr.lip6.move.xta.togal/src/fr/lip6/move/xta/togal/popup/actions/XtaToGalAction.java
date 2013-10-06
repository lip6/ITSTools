package fr.lip6.move.xta.togal.popup.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;








//https://srcdev.lip6.fr/svn/research/thierry/PSTL/GAL/
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.timedAutomata.*;
import fr.lip6.move.xta.serialization.*;
import fr.lip6.move.xta.togal.transform.XtaToGALTransformer;

public class XtaToGalAction implements IObjectActionDelegate {



	/** STUFF BELOW IS WRAPPINGTHE FUNCTIONALITY I A BUTTON */
	
	private Shell shell;
	private IFile file;

	/**
	 * Constructor for Action1.
	 */
	public XtaToGalAction() {
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
		if (file != null) {
			XTA s = SerializationUtil.fileToXtaSystem(file.getRawLocationURI().getPath());

			try {
				XtaToGALTransformer trans = new XtaToGALTransformer();
				GALTypeDeclaration gal = trans.transformToGAL (s);

				String path = file.getRawLocationURI().getPath();
				if (path.endsWith(".xta")) {
					path = path.substring(0,path.length()-4);
				}
				String outpath =  path+ ".gal";

				FileOutputStream out = new FileOutputStream(new File(outpath));
				out.write(0);
				out.close();
				fr.lip6.move.serialization.SerializationUtil.systemToFile(gal,outpath);
				java.lang.System.err.println("GAL model written to file : " +outpath);
			} catch (Exception e) {
				MessageDialog.openWarning(
						shell,
						"Transform xta to GAL operation raised an exception " + e.getMessage(), null);
				e.printStackTrace();
				return;

			}
		}
		java.lang.System.err.println(" xta To GAL was executed on " + file.getName());
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection sel) {
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			for (Object elt : selection.toArray()) {
				if (elt instanceof IFile) {
					IFile file = (IFile) elt;
					if (file.getFileExtension().equals("xta")) {
						this.file=file;
					}

				}

			}

		}

	}

}