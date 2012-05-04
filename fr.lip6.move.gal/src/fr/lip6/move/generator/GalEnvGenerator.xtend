package fr.lip6.move.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess

class GalEnvGenerator {
	
	public static String name_package = "environment"
	private Resource resource
	private IFileSystemAccess fsa
	
	new(Resource resource, IFileSystemAccess fsa) {
		this.resource 	= resource
		this.fsa		= fsa
	}
	
	def static void doGenerate(Resource resource, IFileSystemAccess fsa){
		val gev = new GalEnvGenerator(resource, fsa)
		
		fsa.generateFile(name_package+"/"+"State.java", gev.compileState())
	}
	
	def compileState() '''
		package «name_package»;
		
		import «GalInterfacesGenerator::name_package».IState;
		import java.util.HashMap;
		import java.util.List;
		import java.util.Map;
		import java.util.ArrayList;
		
		public class State implements IState {
			private Map<String, Integer> variables;
			private Map<String, List<Integer>> arrays;
			private Map<String, List<Integer>> lists;
		
			public State(){
				variables 	= new HashMap<String, Integer>();
				arrays		= new HashMap<String, List<Integer>>();
				lists		= new HashMap<String, List<Integer>>();
			}
			
			@Override
			public void addVariable(String varName, Integer value){
				variables.put(varName, value);
			}
			
			@Override
			public void setVariable(String varName, Integer value){
				variables.put(varName, value);
			}
			
			@Override
			public Integer getVariable(String varName){
				return variables.get(varName);
			}
			
			@Override
			public void createArray(String arrayName, List<Integer> initValues){
				arefaire
				ArrayList<Integer> _initValues = (ArrayList<Integer>) initValues.clone();
				arrays.put(arrayName, _initValues);
			}
			
			@Override
			public void setValueInArray(String arrayName, int indexOfValue, Integer value){
				arrays.get(arrayName).set(indexOfValue, value);
			}
			
			@Override
			public Integer getValueInArray(String arrayName, int indexOfValue){
				lever exception si index hors limite !! rare erreur a l'execution !!
				return arrays.get(arrayName).get(indexOfValue);
			}
			
			@Override
			public void createList(String listName, List<Integer> initValues){
				arefaire
				ArrayList<Integer> _initValues = null;
				if (initValues != null)
					_initValues = (ArrayList<Integer>) initValues.clone();
					
				lists.put(listName, _initValues);
			}
			
			@Override
			public void popList(String listName){
				lever exception si liste vide !! rare erreur a l'execution !!
				lists.get(listName).remove(0);
			}
			
			@Override
			public Integer peek(String listName){
				return lists.get(listName).get(0);
			}
			
			@Override
			public void push(String listName, Integer valueToPush){
				lists.get(listName).add(valueToPush);
			}
			
			@Override
			public IState clone(){
				afaire
			}
		}
	'''
}