package fr.lip6.move.ahg.togal.popup.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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

import lip6.move.ahg.aHG.AttackHyperGraph;
import lip6.move.ahg.serialization.SerializationUtil;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;

public abstract class AbstractAHGToGalAction implements IObjectActionDelegate {


	/** STUFF BELOW IS WRAPPING THE FUNCTIONALITY IN A BUTTON */

	private Shell shell;
	private List<IFile> files = new ArrayList<IFile>();

	/**
	 * Constructor for Action1.
	 */
	public AbstractAHGToGalAction() {
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
			MessageDialog.openWarning(shell, "Convertion Error",
					"No Promela file to Be converted");
			return;
		}

		List<String> producedFiles = new ArrayList<String>();
		Map<String, Exception> errors = new HashMap<String, Exception>();

		for (IFile file : files) {
			if (file != null) {
				final String galName = file.getName().replace(".ahg", "").replace(".", "_").replace(" ", "_");
				try {
					// Parsage du fichier promela
					final AttackHyperGraph s = SerializationUtil.fileToAttackHyperGraph(file.getRawLocationURI().getPath());


					// Transformation de AHG vers Gal
					final Specification galSpec = doTransformation(s, galName);

					// Sauvegarde du résultat

					// Premier fichier GAL:
					String tmpPath = file.getRawLocationURI().getPath();
					if (tmpPath.endsWith(".ahg")) {// It is the case!!
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

		MessageDialog.openInformation(shell,"AttackHyperGraph to GAL transformation result", sb.toString());


		files.clear();
	}
	
	public static void saveGalFile(String name, Specification galSpec)
			throws IOException {
		String outPath = name + ".gal";

		FileOutputStream out = new FileOutputStream(new File(outPath));
		out.write(0);
		out.close();

		fr.lip6.move.serialization.SerializationUtil.systemToFile(galSpec, outPath,true);
		java.lang.System.err.println("GAL model written to file: " + outPath);
	}

	
	public static boolean isAHGFile(IFile file) {
		return file.getFileExtension() != null
				&& file.getFileExtension().equals("ahg");
	}
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
									if (isAHGFile(file)) {
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



	public abstract String getExtension();

	public abstract Specification doTransformation(AttackHyperGraph s, String galName) ;

	

}