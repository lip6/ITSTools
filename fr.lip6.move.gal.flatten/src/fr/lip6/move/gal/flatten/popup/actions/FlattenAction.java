package fr.lip6.move.gal.flatten.popup.actions;

import java.io.File;
import java.io.FileOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.gal.System;
import fr.lip6.move.serialization.SerializationUtil;

public class FlattenAction implements IObjectActionDelegate {

	private Shell shell;
	private IFile file;
	
	/**
	 * Constructor for Action1.
	 */
	public FlattenAction() {
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
			System s = SerializationUtil.fileToGalSystem(file.getRawLocationURI().getPath());
			
			try {
				
				System flat = Flattener.flatten(s);
				
				System simpler = Simplifier.simplify(flat);
				
				String path = file.getRawLocationURI().getPath().split(".gal")[0];
                String outpath =  path+".flat.gal";

                //String outpath =  file.getRawLocationURI().getPath()+".flat.gal";
                 
                FileOutputStream out = new FileOutputStream(new File(outpath));
                out.write(0);
				out.close();
				SerializationUtil.systemToFile(simpler,outpath);
				java.lang.System.err.println("On a passe la serialization");
				
			} catch (Exception e) {
				MessageDialog.openWarning(
						shell,
						"Flatten",
						"Flatten GAL operation raised an exception " + e.getMessage());
				return;
				
			}
		}
		
		MessageDialog.openInformation(
			shell,
			"Flatten",
			"Flatten GAL was executed.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof TreeSelection) {
			TreeSelection ts = (TreeSelection) selection;
			for (Object s : ts.toList()) {
				if (s instanceof IFile) {
					IFile file = (IFile) s;
					if (file.getFileExtension().equals("gal")) {
						this.file = file;
						return;
					}
				}
			}
		}
	}

}
