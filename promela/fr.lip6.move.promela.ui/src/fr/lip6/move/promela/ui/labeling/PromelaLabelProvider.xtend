package fr.lip6.move.promela.ui.labeling

import com.google.inject.Inject
import fr.lip6.move.promela.promela.Active
import fr.lip6.move.promela.promela.Assignment
import fr.lip6.move.promela.promela.Atomic
import fr.lip6.move.promela.promela.Condition
import fr.lip6.move.promela.promela.DefineIntMacro
import fr.lip6.move.promela.promela.DefineMacro
import fr.lip6.move.promela.promela.Goto
import fr.lip6.move.promela.promela.InitProcess
import fr.lip6.move.promela.promela.Iteration
import fr.lip6.move.promela.promela.LabeledInstruction
import fr.lip6.move.promela.promela.LiteralConstant
import fr.lip6.move.promela.promela.MTypeDef
import fr.lip6.move.promela.promela.NamedProcess
import fr.lip6.move.promela.promela.Poll
import fr.lip6.move.promela.promela.PromelaSpecification
import fr.lip6.move.promela.promela.Receive
import fr.lip6.move.promela.promela.Reference
import fr.lip6.move.promela.promela.Run
import fr.lip6.move.promela.promela.Selection
import fr.lip6.move.promela.promela.Send
import fr.lip6.move.promela.promela.StructField
import fr.lip6.move.promela.promela.TypeDef
import fr.lip6.move.promela.promela.VariableDeclaration
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider

import static extension fr.lip6.move.promela.ui.labeling.PromelaNameUtils.*
import fr.lip6.move.promela.promela.Sequence

/**
 * Promela labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#labelProvider
 */
class PromelaLabelProvider extends DefaultEObjectLabelProvider {

	//TODO: type!!
	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate)
	}

	def String text(EObject o) {

		switch (o) {
			LabeledInstruction: o.labels.get(0).name +": "+ o.instruction.text
			
			DefineIntMacro: "Macro " + o.name + " " + o.initValue
			InitProcess: "InitProcess"
			NamedProcess: "Process '" + o.name + "'"
			Active: "nb initiales instances " + ((o?.activeVal?.text) ?: "1")
			VariableDeclaration: "var: " + o.name
			TypeDef: "TypeDef " + o.name
			StructField: "Field " + o.name
			Assignment: "Assignment: " + o.^var.refName
			LiteralConstant: o.value.toString 
			Reference: "Ref '"+ o.refName + "'"//TODO impr
			Send: "Emission sur canal '" + o.channel.refName + "'"
			Receive: "Reception sur canal '" + o.channel.refName + "'"
			Poll: "Poll sur canal '" +o.channel.refName + "'"
			Atomic: { var t = if(o.deterministic) "deterministe" else "atomic"
                     "Bloc "+ t + " avec "+ o.corps.steps.size + " instructions"   }
            Condition: { var c = o.cond;
            	if(c instanceof Run)
            	  "Run processus "+ (c as Run).process.name // Lien vers process? 
            	else "Condition" //LATER: applatir condition
            	
            }
            Sequence: {
            	var p =	o.eContainer
				switch (p){
            		Selection: "Option" // improve: index, + else
            		Iteration: "Option"
            		default: "Sequence"
            	}
            }
            // Sequence Name should be changed when in option?
            // ElseSeq, Opt ast constr?
            // SEE atomic validation
            
            // 	val statements = (goto.eContainer().eGet(goto.eContainmentFeature()) as EList<Step>);
			//	var index = statements.indexOf(goto);
            
            // Nom par défault, nom de classe moins le "Impl"
			default: { var s = o.class.simpleName	
				s. substring(0,s.length -4)
			}		
		}
	}


	/** Icones:   */
	// Fichier à placer 'icons' folder of this project
	// liste icones: http://eclipse-icons.i24.cc/
	def String image(EObject o) {
		switch (o) {
			
			LabeledInstruction: o.instruction.image
			
			NamedProcess:
				if (o.active != null)
					"targetinternal_obj.gif"
					else "filter_internal_targets.gif"
			InitProcess:
				"targetpublic_obj.gif"
			// Initialy reverse	
			
			Active:	"correction_change.gif"
			TypeDef: "type.gif" // ou types
			StructField: "field_default_obj.gif"
			VariableDeclaration:
				if (o.eContainer instanceof PromelaSpecification) {
					"public_co.gif"
				} else {
					"private_co.gif"
				}
			MTypeDef: "th_automatic.gif"
			DefineMacro: "macrodef_obj.gif"
			
			// Instruments
			Assignment: "occ_write.gif" // or "write_obj.gif"
			Send: "stepinto_co.gif" // or "sub_co.gif
			Receive: "stepreturn_co.gif" // "super_co.gif"
		    Condition: {  if(o.cond instanceof Run)
            	             "runlast_co.gif"
            	          else  "watch_exp.gif" } // or "changevariablevalue_co.gif"
            Iteration: "loop_obj.gif"
            Selection: "choice_sc_obj.gif" // or "ch_callees.gif"	         
            Atomic: "seq_sc_obj.gif" 
            Goto: "correction_move.gif"
            Sequence:
				if(o.eContainer instanceof Selection || o.eContainer instanceof Iteration) "correction_cast.gif" 
				else "det_pane_hide.gif"
            	          
			default:
				//super.image(o)
				null
		}
	}


}
