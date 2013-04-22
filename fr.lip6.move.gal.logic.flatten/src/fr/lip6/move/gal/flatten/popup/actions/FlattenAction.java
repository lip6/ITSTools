package fr.lip6.move.gal.flatten.popup.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.simplify.LogicSimplifier;
import fr.lip6.move.gal.logic.util.SerializationUtil;

public class FlattenAction implements IObjectActionDelegate {

	private Shell shell;
	private List<IFile> files = new ArrayList<IFile>();

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
		for (IFile file : files) {
			if (file != null) {
				
				Properties p = SerializationUtil.fileToProperties(file.getRawLocationURI().getPath());

				try {

					

					LogicSimplifier.simplify(p);

					String path = file.getRawLocationURI().getPath().split(".prop")[0];
					String outpath =  path+".flat.prop";

					//String outpath =  file.getRawLocationURI().getPath()+".flat.gal";

					FileOutputStream out = new FileOutputStream(new File(outpath));
					out.write(0);
					out.close();
					SerializationUtil.systemToFile(p,outpath);
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
					"simplify props was executed on " + file.getName());
		}
		
		files.clear();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		files.clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ts = (IStructuredSelection) selection;
			for (Object s : ts.toList()) {
				if (s instanceof IFile) {
					IFile file = (IFile) s;
					if (file.getFileExtension().equals("prop")) {
						files.add(file);
					}
				}
			}
		}
	}

}
