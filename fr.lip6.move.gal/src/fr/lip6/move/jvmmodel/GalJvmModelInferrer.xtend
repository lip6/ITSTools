package fr.lip6.move.jvmmodel

import com.google.inject.Inject
import fr.lip6.move.gal.Actions
import fr.lip6.move.gal.ArrayVarAccess
import fr.lip6.move.gal.Assignment
import fr.lip6.move.gal.Pop
import fr.lip6.move.gal.Push
import fr.lip6.move.gal.System
import fr.lip6.move.gal.Transient
import fr.lip6.move.gal.VariableRef
import fr.lip6.move.runtime.interfaces.IState
import org.eclipse.xtext.common.types.util.TypeReferences
import org.eclipse.xtext.xbase.compiler.TypeReferenceSerializer
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class GalJvmModelInferrer extends AbstractModelInferrer {

    /**
     * convenience API to build and initialize JVM types and their members.
     */
	@Inject extension JvmTypesBuilder
	
	@Inject extension TypeReferences
	@Inject extension TypeReferenceSerializer
	
	/**
	 * The dispatch method {@code infer} is called for each instance of the
	 * given element's type that is contained in a resource.
	 * 
	 * @param element
	 *            the model to create one or more
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType declared
	 *            types} from.
	 * @param acceptor
	 *            each created
	 *            {@link org.eclipse.xtext.common.types.JvmDeclaredType type}
	 *            without a container should be passed to the acceptor in order
	 *            get attached to the current resource. The acceptor's
	 *            {@link IJvmDeclaredTypeAcceptor#accept(org.eclipse.xtext.common.types.JvmDeclaredType)
	 *            accept(..)} method takes the constructed empty type for the
	 *            pre-indexing phase. This one is further initialized in the
	 *            indexing phase using the closure you pass to the returned
	 *            {@link org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor.IPostIndexingInitializing#initializeLater(org.eclipse.xtext.xbase.lib.Procedures.Procedure1)
	 *            initializeLater(..)}.
	 * @param isPreIndexingPhase
	 *            whether the method is called in a pre-indexing phase, i.e.
	 *            when the global index is not yet fully updated. You must not
	 *            rely on linking using the index if isPreIndexingPhase is
	 *            <code>true</code>.
	 */
   	def dispatch void infer(System system, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
		
		// API Gal Type cast into JvmTypeReference
   		val iStateType	= system.newTypeRef(typeof(fr.lip6.move.runtime.interfaces.IState))
   		val iTransitionArrayListType = system.newTypeRef(typeof(java.util.ArrayList), 
												system.newTypeRef(typeof(fr.lip6.move.runtime.interfaces.ITransition)))
		val iGalType 	= system.newTypeRef(typeof(fr.lip6.move.runtime.interfaces.IGAL))
		//val iTransitionType = system.newTypeRef(typeof(fr.lip6.move.runtime.interfaces.ITransition))
		val stringType 	=   system.newTypeRef(typeof(java.lang.String))
   		val booleanType = system.newTypeRef(typeof(boolean))
		
		val systemName = system.name.replace(".", "_")
		   
		// Building transitions to java classes
		for(transition : system.transitions)
		{
			acceptor.accept(transition.toClass( "transitions." + systemName + "." + transition.name)).initializeLater [
				superTypes += transition.newTypeRef(typeof(fr.lip6.move.runtime.interfaces.ITransition))
				
				// getName method
				members += transition.toMethod("getName", stringType)[
					annotations += transition.toAnnotation("java.lang.Override")
					body = [
						it.append("return \"" + transition.name + "\" ;")
					]
				]
				
				// guard method
				members += transition.toMethod("guard", booleanType)[
					annotations += transition.toAnnotation("java.lang.Override")
					parameters += transition.toParameter("entryState", iStateType)
					body = [
						it.newLine.append('''return «GalGeneratorUtils::parseBoolExpression(transition.guard, "entryState")» ;''')
					]
				]
				
				// successor method    IState successor(final IState entryState);
				members += transition.toMethod("successor", iStateType)[
					annotations += transition.toAnnotation("java.lang.Override")
					parameters += transition.toParameter("entryState", iStateType)
					body = [
						//var child = it.newLine.append("IState stateRes = (IState)entryState.clone();
						val stateResName = declareVariable(this, "stateRes")
						var child = trace(transition, true)
						transition.newTypeRef(typeof(IState)).serialize(transition, child)
						child.append(''' «stateResName» = entryState; ''')
						
						for(a : transition.actions){
							child = trace(a, true)
							parse(a, child, "entryState", "stateRes")
						}
						
						trace(transition).newLine.append("return stateRes ;")
					]
				]
			]
		}// end of transitions building
		   
		   
		// Build gal system class
   		acceptor.accept(system.toClass("gal."+system.name.replace('.','_')))
   			.initializeLater([
   				
				// A Gal system implements IGAL interface
   				superTypes += iGalType
   				
				// Fields of a Gal system						
   				members += system.toField("initState", iStateType)
				members += system.toField("transitions", iTransitionArrayListType)
				members += system.toField("name", stringType)
				
				// initialize the initial state of the system and its transitions 
				members += system.toConstructor()[
   					body = [
   						it.append("this.initState = new fr.lip6.move.runtime.environment.State();")
   						it.newLine.append("this.transitions = new ArrayList<ITransition>();")
   						it.newLine.append("this.name = \""+system.name+"\";")
   						
   						// add variables
   						for (variable : system.variables){
   							it.newLine.append("initState.addVariable(\""
   							+variable.name+"\","
   							+variable.value
 							+");")
   						}
   						
   						// add arrays
   						for (array : system.arrays){
   							it.newLine.append("initState.createArray(\""
   							+array.name+"\","
   							+"new java.util.ArrayList<Integer>(java.util.Arrays.asList("
   							+array.values.values.toString.substring(1, array.values.values.toString.length-1)
 							+")));")
   						}
   						
   						// build transitions classes and add it in the system
   						for (transition : system.transitions) {
   							var child = it
   							child = child.newLine.append("transitions.add(") ; 
   						   
   						    child = child.append("new transitions." + systemName + "." + transition.name + "());")
   						}
   						
   					]
				]
   				
   				members += system.toGetter("initState", iStateType)
   				members += system.toGetter("transitions", iTransitionArrayListType)
   				members += system.toGetter("name", stringType)
   				
   				// parse and return the transient according to a given state
   				members += system.toMethod("getTransient", "boolean".getTypeForName(system)) [
   					parameters += system.toParameter("entryState", iStateType)	
   					body = [
   						parse(system.transient, it, "entryState") 
   					]
   				]
   				
   			])//end class init
   			
   			
   			generateNewMainFile(system, acceptor)
   	}//end infer method
   	
   	
   	
   	// parse an action of gal and return a CharSequence
   	def parse (Actions a, ITreeAppendable it, String entryState, String destination) {
   			var child = it
   			switch a {
   			Assignment case (a.left instanceof VariableRef):
   				child.newLine.append('''
   					«destination».setVariable("«(a.left as VariableRef).referencedVar.name»",
   							«GalGeneratorUtils::parseIntExpression(a.right, entryState)»);
   				''')
   			Assignment case (a.left instanceof ArrayVarAccess):
   				child.newLine.append('''
   					«destination».setValueInArray("«(a.left as ArrayVarAccess).prefix.name»",
   						«GalGeneratorUtils::parseIntExpression((a.left as ArrayVarAccess).index,entryState)»,
   						«GalGeneratorUtils::parseIntExpression(a.right, entryState)»);
				''')
			Push  	:
				child.newLine.append('''
				«destination».pushInList("«a.list.name»",«GalGeneratorUtils::parseIntExpression(a.value, "stateRes")»);
				''')
			Pop  	:
				child.newLine.append('''
				stateRes.popInList("«a.list.name»");
				''')
   		}
   	}
   	
   	// parse transient of the system if it doesn't exist print "return false"
   	def parse (Transient transient, ITreeAppendable it, String destination){
   		if (transient == null) it.append("return false;")
   		
   		else {
   			it.append("return ");
   			it.append(GalGeneratorUtils::parseBoolExpression(transient.value, "entryState"))
   			it.append(" ;")
   		
   		
   		}
   	}
   	
   	// Create a main for an instance of a gal system
   	def generateNewMainFile(System system,IJvmDeclaredTypeAcceptor acceptor )
   	{
   		acceptor.accept(system.toClass("main." + system.name.replace('.','_'))).initializeLater() [
   			val stringArrayType = addArrayTypeDimension(system.newTypeRef(typeof(java.lang.String)))
   			val methodMain = system.toMethod("main", system.newTypeRef("void")) [
   				
   				parameters += system.toParameter("args",stringArrayType )
   				body = [
   					it.append(
   					'''
   					
   					fr.lip6.move.runtime.interfaces.IGAL system = new gal.«system.name.replace('.', '_')»();
   					
   					// Parsing of args array
   					// Format :
   					// --trace file 
   					// --keyboard
   					// --random
   					// --store file
   					boolean isTrace = false, isKeyboard = false, isRandom = false;
   					
   					String traceFile = null, 
   					storeFile = null;

   					Iterable<Integer> intList ;
   					
   					for(int i=0; i<args.length; i++)
   					{
   						// Chargement d'une trace
   						if("--trace".equals(args[i]))  
   						{
   						isTrace = true ;  
   						traceFile = args[i+1] ; 
   						intList = fr.lip6.move.runtime.environment.Util.loadTrace(traceFile);
   						if(intList == null)
   					 	{
   							System.err.println("Error on file '"+ traceFile + "'") ; 
   							System.exit(1) ; 
   						}
   						fr.lip6.move.runtime.environment.Util.setTrace(intList) ; 
   					
   					}
   					// SAVING a trace
   					if("--store".equals(args[i]))
   					{
   						storeFile = args[i+1] ; 
   					
   					}
   					// KEYBOARD mode
   					if("--keyboard".equals(args[i]))
   					{
   						isKeyboard = true ;
   						fr.lip6.move.runtime.environment.Util.setStrategy(fr.lip6.move.runtime.environment.Util.Strategy.KEYBOARD) ; 
   					
   					}
   					// RANDOM MODE
   					if("--random".equals(args[i]))
   					{
   						isRandom = true ;
   						fr.lip6.move.runtime.environment.Util.setStrategy(fr.lip6.move.runtime.environment.Util.Strategy.RANDOM) ; 
   					
   					}
   					}


if(!isRandom && !isKeyboard && !isTrace) // Default Mode
{
   						System.out.println("Default launch mode : keyboard") ; 
   						fr.lip6.move.runtime.environment.GALStrategy.proceedDefaultStrategy(system, storeFile);
   						System.exit(0) ; 
}

if(isRandom)
{
   						System.out.println("RANDOM launch mode") ;
   						fr.lip6.move.runtime.environment.GALStrategy.proceedRandomStrategy(system, storeFile) ; 
   					
}
else if(isTrace)
{
   						System.out.println("TRACE launch mode") ;
   						fr.lip6.move.runtime.environment.GALStrategy.proceedTraceStrategy(system, storeFile) ;
}
else if(isKeyboard)
{
   						System.out.println("KEYBOARD launch mode") ;
   						fr.lip6.move.runtime.environment.GALStrategy.proceedKeyboardStrategy(system, storeFile) ;
}


   					''')
   				]
   				
   			]
   			methodMain.setStatic(true) ;
   			members += methodMain ;
   			
   		]
   	}
}

