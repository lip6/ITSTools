package fr.lip6.move.its.pnmcc.popup.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.flatten.popup.actions.DecomposeAction;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Support;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.util.SerializationUtil;
import fr.lip6.move.gal.pnml.togal.popup.actions.ImportFromPNMLToGAL;
import fr.lip6.move.gal.simplify.LogicSimplifier;


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
			
			for (IFolder folder : folders) {
				// build model.gal
				Specification spec = transformPNML (folder);
				// compute constants
				Support constants = GALRewriter.flatten(spec, true);
		
				// decompose gal
				decomposeGal(folder);
				
				
				fr.lip6.move.serialization.SerializationUtil.systemToFile(spec, folder.getFile("model.flat.gal").getLocation().toPortableString());
				
				// build .prop files
				String toadd  = "GAL " + folder.getName().replaceAll("-", "_") + "     " + " from \"model.gal\"      \n\n";
				String toadd2 = "GAL " + folder.getName().replaceAll("-", "_") + "_flat" + " from \"model.flat.gal\" \n\n";
				for (IResource res : folder.members()) {
					if (res instanceof IFile) {
						IFile file = (IFile) res;
						// some filters for stuff we dont have procedures for currently
						if (file.getName().endsWith(".txt") 
								&& ! file.getName().contains("LTL")    // No LTL support in transformation
								&& ! file.getName().contains("model.txt")  // this is actually data for ordering heuristics
								&& ! file.getName().contains("Bounds")  // no bounds/max predicate available in its tools
								&& (!(folder.getName().contains("PT")&&file.getName().contains("Fireability"))  // avoid unfolded transition fireability as syntax is unparseable
										)) {  
							transformTextToProp(toadd, file);							
						}
					}
				}
				folder.refreshLocal(1, null);
				// flatten properties
				for (IResource res : folder.members()) {
					if (res instanceof IFile) {
						IFile file = (IFile) res;
						if (file.getName().endsWith(".prop") && ! file.getName().contains(".flat") ) {
							Properties props = SerializationUtil.fileToProperties(file.getLocationURI().getPath().toString());
							LogicSimplifier.simplify(props);
							LogicSimplifier.simplifyConstants(props, constants);
							LogicSimplifier.simplify(props);
							
							
							IContainer outFold = file.getProject();
							IPath newpath = file.getProjectRelativePath();
							newpath = newpath.removeLastSegments(1);
							newpath = newpath.append(file.getLocation().lastSegment().replace(".prop", ".flat.prop"));
							IFile outfile = outFold.getFile(newpath);

							SerializationUtil.systemToFile(props,outfile.getLocationURI().getPath().toString());
							OutputStream outff = new BufferedOutputStream( new FileOutputStream(outfile.getLocationURI().getPath()+".tmp"));
							BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(outfile.getLocationURI().getPath().toString())));
							
							// kill this line
							String importDir  = br.readLine();
							outff.write(toadd2.getBytes());
							
							while (true) {
								String line  = br.readLine();
								if (line == null)
									break;
								outff.write(line.getBytes());
								outff.write('\n');
							}
							br.close();
							outff.close();
							IPath newpath2 = file.getProjectRelativePath();
							newpath2 = newpath2.removeLastSegments(1);
							newpath2 = newpath2.append(file.getLocation().lastSegment().replace(".prop", ".flat.prop")+".tmp");
							IFile tmpout = outFold.getFile(newpath2);
							tmpout.refreshLocal(0, null);
							outfile.delete(true, null);
							tmpout.move(outfile.getFullPath(), true, null);
						}
					}
				}
				// build .flat.prop files
				folder.refreshLocal(1, null);
				
				
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

	private void decomposeGal(IFolder folder) {
		IResource ff = folder.findMember("model.gal");
		if (ff != null && ff instanceof IFile) {
			DecomposeAction act = new DecomposeAction();
			act.setShell(shell);
			StringBuilder sb = new StringBuilder();
			act.workWithFile((IFile)ff, sb);
			System.err.println(sb);
		}
	}

	private void transformTextToProp(String importDirective, IFile txtfile)
			throws CoreException, IOException {
		IFileSystem fs = EFS.getLocalFileSystem();
		IFileStore source = fs.getStore(txtfile.getLocationURI());
		IFileStore dest = fs.getStore(URI.create(txtfile.getLocationURI().toString().replace(".txt", ".prop")));
		//source.copy(dest, EFS.OVERWRITE, null);
		
		OutputStream out = new BufferedOutputStream(dest.openOutputStream(EFS.OVERWRITE, null));
		out.write(importDirective.getBytes());
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
