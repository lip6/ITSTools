package fr.lip6.move.its.pnmcc.popup.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Support;
import fr.lip6.move.gal.pnml.togal.popup.actions.ImportFromPNMLToGAL;


public class PrepareItsFiles implements IObjectActionDelegate {

	private Shell shell;
	private List<IFolder> folders = new ArrayList<IFolder>();

	/**
	 * Constructor for Action1.
	 */
	public PrepareItsFiles() {
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
		try {
			IFileSystem fs = EFS.getLocalFileSystem();
			for (IFolder folder : folders) {
				Specification spec = transformPNML (folder);
				// compute constants
				Support constants = GALRewriter.flatten(spec, true);
				
				// build .prop files
				String toadd = "GAL " + folder.getName().replaceAll("-", "_") + " from \"model.gal\"\n\n";
				for (IResource res : folder.members()) {
					if (res instanceof IFile) {
						IFile file = (IFile) res;
						if (file.getName().endsWith(".txt") && ! file.getName().contains("LTL") && ! file.getName().contains("Bounds")) {
							IFileStore source = fs.getStore(file.getLocationURI());
							IFileStore dest = fs.getStore(URI.create(file.getLocationURI().toString().replace(".txt", ".prop")));
							//source.copy(dest, EFS.OVERWRITE, null);
							
							OutputStream out = new BufferedOutputStream(dest.openOutputStream(EFS.OVERWRITE, null));
							out.write(toadd.getBytes());
							InputStream in = new BufferedInputStream(source.openInputStream(EFS.NONE, null));
							byte [] b = new byte[1024];
							while (true) {
								int read = in.read(b);
								if (read == -1) {
									break;
								}
								out.write(b,0,read);
							}
							in.close();
							out.close();							
						}
					}
				}
			}
		
		
		
		MessageDialog.openInformation(
				shell,
				"Pnmcc",
				"Prepare run was executed.");		
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private Specification transformPNML(IFolder folder) throws Exception {
		Specification spec = null;
		IResource ff = folder.findMember("model.pnml");
		if (ff != null && ff instanceof IFile) {
			ImportFromPNMLToGAL ifpg = new ImportFromPNMLToGAL();
			ifpg.setShell(shell);
			spec = ifpg.transform((IFile)ff);
		}
		return spec;
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		folders .clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ts = (IStructuredSelection) selection;
			for (Object s : ts.toList()) {
				if (s instanceof IResource) {

					try {
						((IResource) s).accept(new IResourceVisitor() {

							@Override
							public boolean visit(IResource resource) throws CoreException {
								if (resource instanceof IFolder) {
									IFolder folder = (IFolder) resource;
									if (folder.exists(Path.fromPortableString("model.pnml"))) {
										folders.add(folder);
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
