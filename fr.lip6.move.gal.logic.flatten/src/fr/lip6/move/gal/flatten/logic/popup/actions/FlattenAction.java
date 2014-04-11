package fr.lip6.move.gal.flatten.logic.popup.actions;


import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.flatten.popup.actions.FileAction;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.simplify.LogicSimplifier;
import fr.lip6.move.gal.logic.util.SerializationUtil;

public class FlattenAction extends FileAction {

	
	private String name;

	@Override
	protected void workWithFile(IFile file, StringBuilder log) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, file );
		} catch (PartInitException e1) {
			e1.printStackTrace();
		}

//		IDocumentExtension.op
		XtextEditor editor = EditorUtils.getActiveXtextEditor();
		IXtextDocument myDocument = editor.getDocument();
		Properties props =  myDocument.readOnly(
				new IUnitOfWork<Properties, XtextResource>(){
					public Properties exec(XtextResource resource) {
						Properties type = (Properties)resource.getContents().get(0);
						return EcoreUtil.copy(type);
					}
				});

		
//		Properties p = SerializationUtil.fileToProperties(file);

		try {

			

			LogicSimplifier.simplify(props);

//			String path = file.getRawLocationURI().getPath().split(".prop")[0];
			IContainer outFold = file.getProject();
			IPath newpath = file.getProjectRelativePath();
			name = file.getLocation().lastSegment();
			newpath = newpath.removeLastSegments(1);
			newpath = newpath.append(file.getLocation().lastSegment().replace(".prop", ".flat.prop"));
			IFile outfile = outFold.getFile(newpath);

			SerializationUtil.systemToFile(props,outfile);

			log.append(outfile.getProjectRelativePath().toString()+"\n");
			warn (new Exception("Finished work on file : " + outfile));
//			//String outpath =  file.getRawLocationURI().getPath()+".flat.gal";
//
//			FileOutputStream out = new FileOutputStream(new File(outpath));
//			out.write(0);
//			out.close();
//			SerializationUtil.systemToFile(p,outpath);
//			java.lang.System.err.println("On a passe la serialization");

		} catch (Exception e) {
			warn(e);
			return;
		}
	}

	@Override
	protected String getServiceName() {
		return "Flatten Properties";
	}

	@Override
	protected void workWithSystem(Specification s) throws Exception {
	}

	@Override
	protected String getAdditionalExtension() {
		return ".flat";
	}

	@Override
	protected List<String> getForbiddenExtension() {
		return Arrays.asList(".flat");
	}
	
	@Override
	protected String getTargetExtension() {
		return ".prop";
	}

	@Override
	public String getModelName() {
		return name;
	}


}
