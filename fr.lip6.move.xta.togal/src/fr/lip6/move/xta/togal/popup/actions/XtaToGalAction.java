package fr.lip6.move.xta.togal.popup.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
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
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.timedAutomata.*;
import fr.lip6.move.xta.serialization.*;

public abstract class XtaToGalAction implements IObjectActionDelegate {


	/** STUFF BELOW IS WRAPPINGTHE FUNCTIONALITY I A BUTTON */
	
	private Shell shell;
	private List<IFile> files = new ArrayList<IFile>();

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
	public void run(IAction a) {
		StringBuilder sb = new StringBuilder();
		for (IFile file: files) {
			if (file != null) {
				XTA s = SerializationUtil.fileToXtaSystem(file.getRawLocationURI().getPath());
			
				try {
					String galName = file.getName().replace(".xta", "");
					GALTypeDeclaration gal = doTransformation(s, galName);
					Specification spec = GalFactory.eINSTANCE.createSpecification();
					spec.getTypes().add(gal);
					
					String path = file.getRawLocationURI().getPath();
					if (path.endsWith(".xta")) {
						path = path.substring(0,path.length()-4);
					}
					String outpath = path + getExtension() + ".gal";
				
					FileOutputStream out = new FileOutputStream(new File(outpath));
					out.write(0);
					out.close();					
					fr.lip6.move.serialization.SerializationUtil.systemToFile(spec, outpath);
					java.lang.System.err.println("GAL model written to file: " + outpath);
					sb.append(" " + outpath);
					
					GALRewriter.flatten(spec, true);
					
					String outpath2 = path + getExtension() +".flat" + ".gal";
					FileOutputStream out2 = new FileOutputStream(new File(outpath));
					out2.write(0);
					out2.close();					
					fr.lip6.move.serialization.SerializationUtil.systemToFile(spec, outpath2);
					java.lang.System.err.println("GAL flat model written to file: " + outpath2);
					sb.append(" " + outpath2);
					
					
				} catch (Exception e) {
					MessageDialog.openWarning(
						shell,
						"Transform XTA to GAL operation raised an exception " + e.getMessage(), null);
					e.printStackTrace();
					return;
				}
			}
		}
		
		MessageDialog.openInformation(
				shell,
				"XTA to GAL transformation result",
				"operation successfully produced files: " + sb.toString());
		
		files.clear();
	}

	public abstract String getExtension();

	public abstract GALTypeDeclaration doTransformation(XTA s, String galName) ;

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
							public boolean visit(IResource resource) throws CoreException {
								if (resource instanceof IFile) {
									IFile file = (IFile) resource;
									if (file.getFileExtension() != null && file.getFileExtension().equals("xta")) {
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