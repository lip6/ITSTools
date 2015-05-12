package fr.lip6.move.gal.gal2smt.popup;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Display;
import org.smtlib.ICommand;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.flatten.popup.actions.GalAction;
import fr.lip6.move.gal.gal2smt.GalToSMT;
import fr.lip6.move.gal.gal2smt.IGalToSMTAction;
import fr.lip6.move.gal.gal2smt.PropertySMT;
import fr.lip6.move.gal.gal2smt.SmtSerializationUtil;

public class Gal2SmtAction extends GalAction implements IGalToSMTAction {
	private static final int PORTEE = 10;
	protected List<Property> propertiesList;
	protected List<ICommand> commandList;
	
	
	@Override
	protected String getServiceName() {		
		return "GAL to SMT";
	} 

	@Override
	protected void workWithSystem(Specification s) throws Exception {
		commandList = GalToSMT.transformeGal(s, this);	
	}

	@Override
	public void workWithFile(IFile file, StringBuilder log) {
		super.workWithFile(file, log);
		String directory = file.getParent().getRawLocationURI().getPath();
		try {
			String path = file.getRawLocationURI().getPath();
			if (path.endsWith(getTargetExtension())) {
				path = path.substring(0,path.length()-getTargetExtension().length());
			}

			List<ICommand> tmp;
			
			/* Si pas de propertie */
			if(propertiesList.size() == 0){
				final String outpath =  path+getAdditionalExtension();
				SmtSerializationUtil.commandListToFile(this.getCommandList(), outpath);
				
				// force refresh like seen in SerializationUtil
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						try{ 	
							for (IFile file  : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new java.net.URI("file://" +outpath.replace('\\', '/')))) {
								file.refreshLocal(IResource.DEPTH_ZERO, null);
							}
						} catch (Exception e) {
							getLog().warning("Error when refreshing explorer view, please refresh manually to ensure new GAL files are visible in eclipse.");
							e.printStackTrace();
						} 
					}
				});

			}
			/* Un fichier par properties */
			for (int i = 0; i < propertiesList.size(); i++) {		    	
				final String outpath =  path+"-"+propertiesList.get(i).getName()+getAdditionalExtension();				
				tmp = addProperty(i,PORTEE);								
				SmtSerializationUtil.commandListToFile(tmp, outpath);
			}
			
			/* delete des fichier gal.smt2 */
			SmtSerializationUtil.deleteFiles(directory, ".smt2.gal");
			
		} catch (Exception e) {
			warn(e);
			e.printStackTrace();
			return;
		}
	}
	
	private List<ICommand> addProperty(int i, int portee) {
		List<ICommand> tmp;
		tmp = new ArrayList<ICommand>(this.getCommandList());
		
		/* On place la property juste avant le check sat */
		tmp.addAll(getCommandList().subList(0, getCommandList().size()-3)); 
		PropertySMT.addProperty(propertiesList.get(i), portee, tmp);
		tmp.addAll(getCommandList().subList(getCommandList().size() -3, getCommandList().size()));
		return tmp;
	}

	public List<ICommand> getCommandList() {
		return this.commandList;
	}
	
	public void setPropertiesList(List<Property> properties){
		propertiesList = properties;
	}
	
	@Override
	protected String getAdditionalExtension() {
		return ".smt2";
	}
	
	@Override
	protected String getTargetExtension() {
		return ".gal";
	}
}
