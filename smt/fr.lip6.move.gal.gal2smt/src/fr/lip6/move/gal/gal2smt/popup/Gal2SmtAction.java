package fr.lip6.move.gal.gal2smt.popup;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.smtlib.ICommand;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.flatten.popup.actions.GalAction;
import fr.lip6.move.gal.gal2smt.GalToSMT;
import fr.lip6.move.gal.gal2smt.PropertySMT;
import fr.lip6.move.gal.gal2smt.SmtSerializationUtil;

public class Gal2SmtAction extends GalAction implements IGalToSMTAction {
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

			String outpath;
			List<ICommand> tmp;
			
			/* Si pas de propertie */
			if(propertiesList.size() == 0){
				outpath =  path+getAdditionalExtension();
				SmtSerializationUtil.commandListToFile(this.getCommandList(), outpath);
			}
			/* Un fichier par properties */
			for (int i = 0; i < propertiesList.size(); i++) {		    	
				outpath =  path+"-"+propertiesList.get(i).getName()+getAdditionalExtension();				
				tmp = addProperty(i);								
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
	
	private List<ICommand> addProperty(int i) {
		List<ICommand> tmp;
		tmp = new ArrayList<ICommand>(this.getCommandList());
		
		/* On place la property juste avant le check sat */
		tmp.addAll(getCommandList().size()-3, 
				PropertySMT.makeProperty(propertiesList.get(i), GalToSMT.PORTEE));
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
