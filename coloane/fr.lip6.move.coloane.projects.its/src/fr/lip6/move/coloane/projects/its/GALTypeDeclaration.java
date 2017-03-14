package fr.lip6.move.coloane.projects.its;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.lip6.move.coloane.projects.its.expression.EvaluationContext;
import fr.lip6.move.coloane.projects.its.expression.IEvaluationContext;
import fr.lip6.move.coloane.projects.its.variables.GalArrayVariable;
import fr.lip6.move.coloane.projects.its.variables.GalVariable;
import fr.lip6.move.coloane.projects.its.variables.IModelVariable;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.serialization.SerializationUtil;
import fr.lip6.move.ui.utils.FileUtils;

public class GALTypeDeclaration extends AbstractTypeDeclaration {
	private fr.lip6.move.gal.GALTypeDeclaration galSystem;
	
	public GALTypeDeclaration(String name, URI file, TypeList types) throws IOException {
		super (name,file,types);		
		reload();
		galSystem.setName(name);
	}

	public String getTypeType() {
		return "Guarded Action Language";
	}
	
	@Override
	public void setTypeName(String typeName) {
		super.setTypeName(typeName);
		galSystem.setName(typeName);
	}
	

	
	@Override
	public List<IModelVariable> computeVariables() {
		// TODO la liste des variables, il faut creer des classes dérivées de LeafModelVariable, 
		// cf PlaceMarkingVariable par exemple
		// Un pour chaque variable de type int
		// Un (avec des fils ?) pour chaque tableau
		// un par liste ?
		
		List<IModelVariable> variables = new ArrayList<IModelVariable>();
		
		for(Variable var : galSystem.getVariables()){
			variables.add(new GalVariable(var));
		}
		
		for (ArrayPrefix array: galSystem.getArrays()) {
			int max = ((Constant) array.getSize()).getValue();
			for (int i=0 ; i < max ; i++) {
				variables.add(new GalArrayVariable(array, i));
			}
		}
		
		return variables;
	}


	/**
	 * A priori no parameters are needed for GAL models.
	 */
	public IEvaluationContext getParameters() {
		return new EvaluationContext();
	}



	public void reload() throws IOException {
		// Il faut recharger l'instance de systeme en la lisant depuis le fichier + eventuellement flusher les caches
		// i.e. invoquer clearLabels()
		clearCaches();
		galSystem=null;
		// load and store handle to GAL System
		Specification spec = SerializationUtil.fileToGalSystem(getTypeFile().getPath());
		for (fr.lip6.move.gal.TypeDeclaration td : spec.getTypes()) {
			if (td instanceof fr.lip6.move.gal.GALTypeDeclaration) {
				fr.lip6.move.gal.GALTypeDeclaration gal = (fr.lip6.move.gal.GALTypeDeclaration) td;
				this.galSystem = gal;
			}
		}
		
		if (galSystem == null) {
			throw new IOException("Expected gal file to contain a GAL description only.");
		}
		galSystem.setName(getTypeName());
	}

	
	/**
	 * 
	 * @return the set of public labels of this type
	 * A transition is public if it has a Gal label
	 */
	@Override
	protected Set<String> computeLabels() {
		Set<String> labels = new HashSet<String>();
		
		for(fr.lip6.move.gal.Transition trans : galSystem.getTransitions()){
			final Label label = trans.getLabel();
			if(label!=null){
				labels.add(label.getName());
			}
		}
		
		return labels;
	}

	public void writeToFile(String path) throws IOException {
		// create and write a GAL file based on model in memory
		SerializationUtil.systemToFile(galSystem, path);
		FileUtils.refreshDisplay(path);
	}

	
}


