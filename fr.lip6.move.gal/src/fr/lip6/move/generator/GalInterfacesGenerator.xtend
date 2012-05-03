package fr.lip6.move.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess

class GalInterfacesGenerator {
	
	static String name_package = "interfaces"
	
	def doGenerate (Resource resource, IFileSystemAccess fsa){
		fsa.generateFile("./"+name_package+"/IGAL.java", IGALCompile())
		fsa.generateFile("./"+name_package+"/IState.java", IStateCompile())
		fsa.generateFile("./"+name_package+"/ITransition.java", ITransitionCompile())
	}
	
	def IGALCompile() '''
		package «name_package»;
		
		import java.util.List;
		
		public interface IGAL {
			String getName();
			IState getInitState();
			List<ITransition> getTransitions();
		}
	'''
	
	def IStateCompile() '''ine de se retrouver avec une CloneNotSupportedException. 
		package «name_package»;
		
		public interface IState{
			void addVariable(String var, int value);
			
			IState clone();
		}
	'''
	
	def ITransitionCompile() '''
		package «name_package»;
		
		public interface ITransition {
			boolean getGuard();
		} 
	'''
}