package fr.lip6.move.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess

class GalInterfacesGenerator {
	
	public static String name_package = "interfaces"
	
	private Resource resource
	private IFileSystemAccess fsa
	
	new (Resource resource, IFileSystemAccess fsa){
		this.resource 	= resource
		this.fsa	 	= fsa
	}
	
	def static void doGenerate (Resource resource, IFileSystemAccess fsa){
		
		val gig = new GalInterfacesGenerator(resource, fsa)
		
		fsa.generateFile("./"+name_package+"/IGAL.java", gig.IGALCompile())
		fsa.generateFile("./"+name_package+"/IState.java", gig.IStateCompile())
		fsa.generateFile("./"+name_package+"/ITransition.java", gig.ITransitionCompile())
	}
	
	def IGALCompile() '''
		package «name_package»;
		
		import java.util.List;
		
		public interface IGAL {
			String getName();
			IState getInitState();
			List<ITransition> getTransitions();
			boolean getTransient(IState entryState);
		}
	'''
	
	def IStateCompile() ''' 
		package «name_package»;
		
		import java.util.List;
		
		public interface IState extends Cloneable{
			int getNumberOfVariables();
			int getNumberOfArrays();
			int getNumberOfLists();
			
			void addVariable(String varName, Integer value);
			void setVariable(String varName, Integer value);
			Integer getVariable(String varName);
			
			void createArray(String arrayName, List<Integer> initValues);
			void setValueInArray(String arrayName, int indexOfValue, Integer value);
			Integer getValueInArray(String arrayName, int indexOfValue);
			int getSizeOfArray(String arrayName);
			
			void createList(String listName, List<Integer> initValues);
			void popInList(String listName);
			Integer peekInList(String listName);
			void pushInList(String listName, Integer valueToPush); 
			int getSizeOfList(String listName);
			
			Object clone();
		}
	'''
	
	def ITransitionCompile() '''
		package «name_package»;
		
		public interface ITransition {
			String getName();
			boolean guard(final IState entryState);
			IState successor(final IState entryState);
		} 
	'''
}
