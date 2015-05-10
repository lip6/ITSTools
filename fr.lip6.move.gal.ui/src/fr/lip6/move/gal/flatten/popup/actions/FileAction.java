package fr.lip6.move.gal.flatten.popup.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

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

import fr.lip6.move.gal.Specification;

public abstract class FileAction implements IObjectActionDelegate {

		private Shell shell;
		private List<IFile> files = new ArrayList<IFile>();
		private Logger log =Logger.getLogger("fr.lip6.move.gal");

		public void setShell(Shell shell) {
			this.shell = shell;
		}
		/**
		 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
		 */
		public void setActivePart(IAction action, IWorkbenchPart targetPart) {
			shell = targetPart.getSite().getShell();
		}

		
		public Logger getLog() {
			return log;
		}
		/**
		 * @see IActionDelegate#run(IAction)
		 */
		public void run(IAction action) {
			ConsoleAdder.startConsole();
			// try to treat small input first.
			Collections.sort(files, new Comparator<IFile> (){

				@Override
				public int compare(IFile f1, IFile f2) {
					try {
						// unfortunately static version Long.compare is only introduced in java 1.7 
						return new Long(org.eclipse.core.filesystem.EFS.getStore(f1.getLocationURI()).fetchInfo().getLength()).compareTo(
								org.eclipse.core.filesystem.EFS.getStore(f2.getLocationURI()).fetchInfo().getLength());
					} catch (CoreException e) {
						return f1.getLocation().toString().compareTo(f2.getLocation().toString());
					}

				}

			});

			StringBuilder sb = new StringBuilder();
			for (IFile file : files) {
				if (file != null) {
					workWithFile(file,sb);
				}
				log.info(getServiceName() + " was executed on " + file.getName());
				java.lang.System.err.println(getServiceName() + " was executed on " + file.getName());
				//				MessageDialog.openInformation(
				//						shell,
				//						"Flatten",
				//						"Flatten GAL was executed on " + file.getName());
			}

			MessageDialog.openInformation(
					shell,
					"Gal Transformation result",
					getServiceName() + " operation successfully produced files : " + sb.toString());

			log.info(getServiceName() + " operation successfully produced files : " + sb.toString());
			
			files.clear();
//			ConsoleAdder.stopconsole();
		}


		
		/**
		 * Work on current file; log contents whown at end of operation
		 * @param file
		 * @param log
		 */
		protected abstract void workWithFile(IFile file, StringBuilder log);

		protected abstract String getServiceName() ;

		protected abstract void workWithSystem(Specification s) throws Exception ;

		/**
		 * Newly produced files add this extension before the .gal extension
		 * @return a string to add to file name
		 */
		protected abstract String getAdditionalExtension() ;

		/**
		 * Return the extension of target files both 
		 */
		protected abstract String getTargetExtension();
		
		/**
		 * Return a blacklist of extensions in source files
		 */
		protected List<String> getForbiddenExtension() {
			return Collections.singletonList(getAdditionalExtension());
		}
		
		/**
		 * @see IActionDelegate#selectionChanged(IAction, ISelection)
		 */
		public void selectionChanged(IAction action, ISelection selection) {
			files.clear();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ts = (IStructuredSelection) selection;
				for (Object s : ts.toList()) {
					if (s instanceof IResource) {
						
						try {
							((IResource) s).accept(new IResourceVisitor() {
								
								@Override
								public boolean visit(IResource resource) throws CoreException {
									if (resource instanceof IFile) {
										IFile file = (IFile) resource;
										if (file.getFileExtension()!=null && getTargetExtension().contains(file.getFileExtension()) ) {
											String fname = file.getFullPath().toPortableString();
											boolean ok = true;
											for (String ext : getForbiddenExtension()) {
												if (fname.contains(ext) )  {
													ok = false;
													break;
												}
											}
											if (ok)
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

		
		
		protected void warn(Exception e) {
			MessageDialog.openWarning(
					shell,
					getServiceName(),
					getServiceName() + " operation raised an exception " + e.getMessage() + " while analyzing " + getModelName() + "\n Please make sure your plugins"
					+ "are up to date. If you can reproduce this error, please mail us your model (ddd@lip6.fr) and we will try to correct the problem. " );
			e.printStackTrace();
		}

		public abstract String getModelName() ;
}
