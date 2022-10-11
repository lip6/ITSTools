package fr.lip6.move.gal.pnml.togal.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.flatten.popup.actions.ConsoleAdder;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.IOrderVisitor;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.pnml.togal.PnmlToGalTransformer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lip6.move.serialization.SerializationUtil;

public class ImportFromPNMLToGAL implements IObjectActionDelegate {


	private List<IFile> files = new ArrayList<IFile>();

	/**
	 * Constructor for Action1.
	 */
	public ImportFromPNMLToGAL() {
		super();
	}

	private Shell shell;
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		setShell(targetPart.getSite().getShell());
	}
	public void setShell(Shell shell) {
		this.shell = shell;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		ConsoleAdder.startConsole();
		String filenames = "" ;
		for (IFile file : files) {

			try {
				PnmlToGalTransformer trans = new PnmlToGalTransformer();
				Specification spec = trans.transform(file.getLocationURI());
				if (spec.getMain() == null) {
					spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
				}
				SerializationUtil.systemToFile(spec,file.getLocationURI().getPath()+".img.gal",true);
				if (trans.getOrder() != null) {
					getLog().info("Applying decomposition : " + trans.getOrder());
					GALRewriter.flatten(spec, true);
					CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
					GALRewriter.flatten(spec, true);
					IOrder order = trans.getOrder();
						order.accept(new IOrderVisitor<Void>() {

							@Override
							public Void visitComposite(CompositeGalOrder o) {
								for (IOrder sub : o.getChildren()) {
									sub.accept(this);
								}
								return null;
							}

							@Override
							public Void visitVars(VarOrder varOrder) {
								for (int i = 0 ; i < varOrder.getVars().size() ; i++) {
									if (varOrder.getVars().get(i).contains("[")) {
										varOrder.getVars().set(i, varOrder.getVars().get(i).replace('[', '_').replaceAll("]", ""));
									}
								}
								return null;
							}
						});
						CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), trans.getOrder());
					
				} else {
					GALRewriter.flatten(spec, true);
				}
				writeGALfile(file.getLocationURI(), spec);
			} catch (Exception e) {
				MessageDialog.openInformation(
						shell,
						"PNMLToGAL",
						"ImportToGAL failed." + 			e.getMessage());
				getLog().warning("ImportToGAL failed." + 			e.getMessage());
				e.printStackTrace();
			}


			getLog().info("ImportToGAL was executed on file : " + file.getName());
			filenames += file.getName() + "; ";
		}
		MessageDialog.openInformation(
				shell,
				"PNMLToGAL",
				"ImportToGAL was executed on files : " + filenames);
	}


	private void writeGALfile(URI uri, Specification spec)
			throws FileNotFoundException, IOException {	
		String outpath = uri.getPath() + ".gal";

		SerializationUtil.systemToFile(spec,outpath, true);
	}




	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		files .clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ts = (IStructuredSelection) selection;
			for (Object s : ts.toList()) {
				if (s instanceof IFile) {
					IFile file = (IFile) s;
					if (file.getFileExtension().equals("pnml")) {
						this.files.add(file);
					}
				}
			}
		}
	}

	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

}
