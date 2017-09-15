package fr.lip6.move.promela.togal.popup.actions;

import static fr.lip6.move.promela.togal.popup.utils.PopUpUtils.isPromelaFile;
import static fr.lip6.move.promela.togal.popup.utils.PopUpUtils.rawName;
import static fr.lip6.move.promela.togal.popup.utils.PopUpUtils.saveGalFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.promela.promela.PromelaSpecification;
import fr.lip6.move.promela.serialization.SerializationUtil;


public abstract class PromelaToGalAction implements IObjectActionDelegate {

	// MAYBE: PAss to Command and Handler
	// TODO: apparait que dans projet ou ya des fichiers promela? (ajouter une
	// nature prom)

	// TODO: action depuis un clic dans le fichier GAL!!

	/** STUFF BELOW IS WRAPPING THE FUNCTIONALITY IN A BUTTON */

	private Shell shell;
	private List<IFile> files = new ArrayList<IFile>();

	/**
	 * Constructor for Action1.
	 */
	public PromelaToGalAction() {
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
	public void run(IAction a) {

		if (files.isEmpty()) {
			MessageDialog.openWarning(shell, "Conversion Error",
					"No Promela file to be converted");
			return;
		}

		List<String> producedFiles = new ArrayList<String>();
		Map<String, Exception> errors = new HashMap<String, Exception>();

		for (IFile file : files) {
			if (file != null) {
				final String galName = rawName(file).replace(".", "_").replace(" ", "_");
				try {
					// Parsage du fichier promela
					final PromelaSpecification s = SerializationUtil
							.fileToPromelaSpecification(file.getRawLocationURI()
									.getPath());


					// Pour éviter Gal se plaint des nom mon toto.34_glat



					// Transformation de Promela vers Gal
					final Specification galSpec = doTransformation(s, galName);

					// Sauvegarde du résultat

					// Premier fichier GAL:
					String tmpPath = file.getRawLocationURI().getPath();
					if (tmpPath.endsWith(".pml")) {// It is the case!!
						tmpPath = tmpPath.substring(0, tmpPath.length() - 4);
					}

					String galFileName = tmpPath + getExtension();
					saveGalFile(galFileName, galSpec);

					producedFiles.add(galName + getExtension());

					// Fichier Gal applati
					GALRewriter.flatten(galSpec, true);

					String galFlatFileName = tmpPath + getExtension() + ".flat";
					saveGalFile(galFlatFileName, galSpec);

					producedFiles.add(galName + getExtension() + ".flat");

				} catch (Exception e) {
					errors.put(galName, e);
					// MessageDialog.openWarning(shell, "Convertion Error",
					// "Transform Promela to GAL operation raised an exception :\n"
					// + e.getMessage());
					e.printStackTrace();
					// return;
				}
			}
		}

		// Message succès conditionné... (au vrai succès)

		StringBuffer sb = new StringBuffer();
		if (! producedFiles.isEmpty()) {
			sb.append("Operation successfully produced "+ producedFiles.size() +" files:\n");
			for (String f : producedFiles) {
				sb.append(f).append(", ");
			}
			sb.setLength(sb.length() - 2);
			sb.append(".");
		}

		if (!errors.isEmpty()) {
			sb.append("\n Some files were not transformed due to errors ("+ errors.size() +" files)  : \n");

			for (Entry<String, Exception> e : errors.entrySet()) {
				sb.append(e.getKey() + " : " + e.getValue().getMessage() +"\n");
				e.getValue().printStackTrace();
			}
		}

		MessageDialog.openInformation(shell,"Promela to GAL transformation result", sb.toString());


		files.clear();
	}

	public abstract String getExtension();

	public abstract Specification doTransformation(PromelaSpecification s,
			String galName);

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection sel) {
		files.clear();
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			for (Object elt : selection.toArray()) {
				if (elt instanceof IResource) {
					try {
						((IResource) elt).accept(new IResourceVisitor() {
							@Override
							public boolean visit(IResource resource)
									throws CoreException {
								if (resource instanceof IFile) {
									IFile file = (IFile) resource;
									if (isPromelaFile(file)) {
										files.add(file);
									}
								}
								// descend into subfolders
								return true;
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