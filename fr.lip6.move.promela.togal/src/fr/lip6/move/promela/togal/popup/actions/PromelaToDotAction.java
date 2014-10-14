package fr.lip6.move.promela.togal.popup.actions;

import static fr.lip6.move.promela.togal.popup.utils.PopUpUtils.isPromelaFile;
import static fr.lip6.move.promela.togal.popup.utils.PopUpUtils.rawName;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.promela.promela.PromelaSpecification;
import fr.lip6.move.promela.serialization.SerializationUtil;
import fr.lip6.move.promela.togal.transform.PromelaToDotProcessRepresentation;

public class PromelaToDotAction implements IObjectActionDelegate {

	private IFile pmlFile;
	private Shell shell;

	// TODO: passer au command format!!
	//HERE: fix message si pas un fichier pml
	@Override
	public void run(IAction a) {
		try {
			final PromelaSpecification s = SerializationUtil
				.fileToPromelaSpecification(pmlFile.getRawLocationURI()
						.getPath());

		// List<PromDot> : get access to this level?

		PromelaToDotProcessRepresentation res = new PromelaToDotProcessRepresentation(
				s);

		String dirpath = pmlFile.getParent().getRawLocationURI().getPath();
		String file = rawName(pmlFile);
		String prefix = dirpath + "/" + file;
		// HERE

		// MAYBE: option version pour dossier
		// File dir = new File(prefix);
		// dir.mkdir();

		
			// TODO: create as dependant ressources!!
			res.saveToFile(prefix);
			res.convertToDot(prefix, "png");
			res.convertToDot(prefix, "svg");

			MessageDialog.openInformation(shell, "Promela Dot Representation",
					"Representation of the " + res.getNbProcessDef()
							+ " process def was a Success");

		} catch (IOException e) {
			MessageDialog.openWarning(
					shell,
					"Save Error",
					"Error while saving dot representation :\n"
							+ e.getMessage());
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		// TODO Why??
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection sel) {

		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			for (Object elt : selection.toArray()) {
				if (elt instanceof IResource) {
					try {
						//TODO: visitor proxy.
						((IResource) elt).accept(new IResourceVisitor() {
							@Override
							public boolean visit(IResource resource)
									throws CoreException {
								if (resource instanceof IFile) {
									IFile file = (IFile) resource;
									if (isPromelaFile(file)) {
										pmlFile = file;
									}
								}
								// descend PAS into subfolders
								return false;
							}
						});
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
