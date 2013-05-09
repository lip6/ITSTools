package fr.lip6.move.gal.flatten.logic.popup.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocumentExtension;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

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
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file );
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}

//				IDocumentExtension.op
				XtextEditor editor = EditorUtils.getActiveXtextEditor();
				IXtextDocument myDocument = editor.getDocument();
				Properties props =  myDocument.readOnly(
						new IUnitOfWork<Properties, XtextResource>(){
							public Properties exec(XtextResource resource) {
								Properties type = (Properties)resource.getContents().get(0);
								return EcoreUtil.copy(type);
							}
						});

				
//				Properties p = SerializationUtil.fileToProperties(file);

				try {

					

					LogicSimplifier.simplify(props);

//					String path = file.getRawLocationURI().getPath().split(".prop")[0];
					IContainer outFold = file.getParent();
					IPath newpath = file.getProjectRelativePath();
					newpath = newpath.removeLastSegments(1);
					newpath = newpath.append(file.getLocation().lastSegment().replace(".prop", ".flat.prop"));
					IFile outfile = outFold.getFile(newpath);

					SerializationUtil.systemToFile(props,outfile);
					java.lang.System.err.println("On a passe la serialization");
					
//					//String outpath =  file.getRawLocationURI().getPath()+".flat.gal";
//
//					FileOutputStream out = new FileOutputStream(new File(outpath));
//					out.write(0);
//					out.close();
//					SerializationUtil.systemToFile(p,outpath);
//					java.lang.System.err.println("On a passe la serialization");

				} catch (Exception e) {
					MessageDialog.openWarning(
							shell,
							"Flatten",
							"Flatten GAL operation raised an exception " + e.getMessage());
					e.printStackTrace();
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
