package fr.lip6.move.xta.togal.popup.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
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
import fr.lip6.move.timedAutomata.*;
import fr.lip6.move.xta.serialization.*;

public class XtaToGal implements IObjectActionDelegate {

	private Shell shell;
	private IFile file;

	/**
	 * Constructor for Action1.
	 */
	public XtaToGal() {
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
		if (file != null) {
			XTA s = SerializationUtil.fileToXtaSystem(file.getRawLocationURI().getPath());

			try {				
				GALTypeDeclaration gal = transformToGAL (s);

				String path = file.getRawLocationURI().getPath();
				if (path.endsWith(".xta")) {
					path = path.substring(0,path.length()-4);
				}
				String outpath =  path+ ".gal";

				FileOutputStream out = new FileOutputStream(new File(outpath));
				out.write(0);
				out.close();
				fr.lip6.move.serialization.SerializationUtil.systemToFile(gal,outpath);
				java.lang.System.err.println("GAL model written to file : " +outpath);
			} catch (Exception e) {
				MessageDialog.openWarning(
						shell,
						"Transform xta to GAL operation raised an exception " + e.getMessage(), null);
				e.printStackTrace();
				return;

			}
		}
		java.lang.System.err.println(" xta To GAL was executed on " + file.getName());
	}

	private GALTypeDeclaration transformToGAL(XTA s) {
		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		gal.setName("Fromxta");
		
		for (InstantiableInSystem pd : s.getSystem().getInstances() ) {
			gal.setName(pd.getName());
			
//			//declaration of variables
//			for (VarDecl vd : pd.getVars()) {
//				fr.lip6.move.gal.VarDecl tvd = GalFactory.eINSTANCE.createVarDecl();
//				tvd.setName(vd.getName());
//				Variable tvar = GalFactory.eINSTANCE.createVariable();
//				tvar.setName(vd.getName());
//				tvar.setValue( convertToGAL(vd.getValue(),gal));
//
//				gal.getVariables().add(tvar);
//			}
//			
//			//declaration of states & state initial
//			Map<String, Integer> states= new HashMap<String,Integer>(); 
//			for (StateDeclaration sd : pd.getStates()) {
//				if (!(states.containsKey(sd.getName()))){
//					states.put(sd.getName(), states.size());
//				}
//			}
//			String init = pd.getInitialState().getName();
//			fr.lip6.move.gal.VarDecl varStateDecl = GalFactory.eINSTANCE.createVarDecl();
//			varStateDecl.setName("varStates");
//			Variable varState = GalFactory.eINSTANCE.createVariable();
//			varState.setName("varStates");
//			
//			Constant tcons = GalFactory.eINSTANCE.createConstant();
//			tcons.setValue((int) (states.get(init)));
//			varState.setValue(tcons);
			

			
		}		
		return gal;
	}

	private fr.lip6.move.gal.IntExpression convertToGAL(IntExpression value, GALTypeDeclaration gal) {
		if (value instanceof Constant) {
			Constant cte = (Constant) value;
			fr.lip6.move.gal.Constant tcte = GalFactory.eINSTANCE.createConstant();
			tcte.setValue(cte.getValue());
			return tcte;
		} else if (value instanceof VarAccess) {
			//blblablblablba
		} else if (value instanceof BinaryIntExpression) {
//			fr.lip6.move.xta.VarRef varRef = (fr.lip6.move.xta.VarRef) value;
//			fr.lip6.move.gal.VariableRef tvarRef = GalFactory.eINSTANCE.createVariableRef();
//			
//			Boolean inconnue= true;
//			for(Variable v : gal.getVariables()){
//				if(varRef.getVar().getName().equals(v.getName())){
//					inconnue= false;
//					tvarRef.setReferencedVar(v);
//					break;
//				}	
//			}
//			
//			if (inconnue == true)  {
//				java.lang.System.err.println("The referenced variable does not exists !!!");
//				return null;
//			}
//				
//			return tvarRef;
		}		
		return null;
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection sel) {
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			for (Object elt : selection.toArray()) {
				if (elt instanceof IFile) {
					IFile file = (IFile) elt;
					if (file.getFileExtension().equals("xta")) {
						this.file=file;
					}

				}

			}

		}

	}

}