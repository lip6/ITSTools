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
		fsa.generateFile(name_package+"/"+"WrapBool.java", gev.compileWrapBool())
	}
	
	def compileState() '''
		package «name_package»;
		
		import «GalInterfacesGenerator::name_package».IState;
		import java.util.HashMap;
		import java.util.List;
		import java.util.Map;
		
		public class State implements IState {
			private Map<String, Integer> variables;
			private Map<String, List<Integer>> arrays;
			private Map<String, List<Integer>> lists;
		
			public State(){
				variables 	= new HashMap<String, Integer>();
				arrays		= new HashMap<String, List<Integer>>();
				lists		= new HashMap<String, List<Integer>>();
			}
			
			public State(
				Map<String, Integer> variables, 
				Map<String, List<Integer>> arrays,
				Map<String, List<Integer>> lists){
				
				this.variables 	= new HashMap<String, Integer>(variables);
				this.arrays		= new HashMap<String, List<Integer>>(arrays);
				this.lists		= new HashMap<String, List<Integer>>(lists);
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
				arrays.put(arrayName, initValues);
			}
			
			@Override
			public void setValueInArray(String arrayName, int indexOfValue, Integer value){
				arrays.get(arrayName).set(indexOfValue, value);
			}
			
			@Override
			public Integer getValueInArray(String arrayName, int indexOfValue){
				return arrays.get(arrayName).get(indexOfValue);
			}
			
			@Override
			public void createList(String listName, List<Integer> initValues){
				lists.put(listName, initValues);
			}
			
			@Override
			public void popInList(String listName){
				lists.get(listName).remove(0);
			}
			
			@Override
			public Integer peekInList(String listName){
				return lists.get(listName).get(0);
			}
			
			@Override
			public void pushInList(String listName, Integer valueToPush){
				lists.get(listName).add(valueToPush);
			}
			
			@Override
			public Object clone(){
				return new State(variables, arrays, lists);
			}
		}
	'''
	
	def compileWrapBool() '''
		package «name_package»;
		
		public class WrapBool {
			private boolean boolwrapped;
		
			public WrapBool(boolean boolwrapped){
				this.boolwrapped = boolwrapped;
			}
			
			public int evaluate(){
				if(boolwrapped) return 1;
				return 0;
			}
		} 
	'''
}