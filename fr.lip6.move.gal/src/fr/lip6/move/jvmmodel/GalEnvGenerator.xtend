package fr.lip6.move.jvmmodel

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
		
			public State(
			Map<String, Integer> variables, 
			Map<String, List<Integer>> arrays,
			Map<String, List<Integer>> lists){
		
				this.variables 	= new HashMap<String, Integer>(variables);
		
				this.arrays = new HashMap<String, List<Integer>>();
				for(String k : arrays.keySet()){
					this.arrays.put(k, new ArrayList<Integer>(arrays.get(k)));
				}
		
				this.lists	= new HashMap<String, List<Integer>>();
				for(String k : lists.keySet()){
					this.lists.put(k, new ArrayList<Integer>(lists.get(k)));
				}
			}
			
			@Override
			public int getNumberOfVariables(){
				return variables.keySet().size();
			}
			
			@Override
			public int getNumberOfArrays(){
				return arrays.keySet().size();
			}
			
			@Override
			public int getNumberOfLists(){
				return lists.keySet().size();
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
			public int getSizeOfArray(String arrayName){
				return arrays.get(arrayName).size();
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
				lists.get(listName).add(0, valueToPush);
			}
			
			@Override
			public Integer getValueInList(String listName, int indexOfValue){
				return lists.get(listName).get(indexOfValue);
			}
			
			@Override
			public int getSizeOfList(String listName){
				return lists.get(listName).size();
			}
			
			@Override
			public int hashCode(){
				return lists.hashCode()+arrays.hashCode()+variables.hashCode();
			}
			
			@Override
			public Object clone(){
				return new State(variables, arrays, lists);
			}
			
			@Override
			public boolean equals(Object other){
				if(other == null) return false;
				if(this == other) return true;
				if(!(other instanceof IState)) return false; 
				
				IState os = (IState) other;
				
				if(getNumberOfVariables() != os.getNumberOfVariables()) return false;
				if(getNumberOfArrays() != os.getNumberOfArrays()) return false;
				if(getNumberOfLists() != os.getNumberOfLists()) return false;
				
				for(String k : variables.keySet()){
					if(!os.getVariable(k).equals(getVariable(k))) return false;
				}
				
				for(String k : arrays.keySet()){
					if(os.getSizeOfArray(k) != getSizeOfArray(k)) return false;
					for(Integer i=0; i<getSizeOfArray(k); i++){
						if(!os.getValueInArray(k,i).equals(getValueInArray(k,i))) return false;
					}
				}
				
				for(String k : lists.keySet()){
					if(os.getSizeOfList(k) != getSizeOfList(k)) return false;
					Integer i = 0;
					while(i<getSizeOfList(k)){
						if(!os.getValueInList(k,i).equals(getValueInList(k,i))) return false;
						i++;
					}
				}
				
				return true;
			}
			
			public String toString(){
				String s = "variables: "+variables.toString()+"\n";
				s+="arrays: "+arrays.toString()+"\n";
				s+="lists: "+lists.toString();
				return s;
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
