package fr.lip6.move.promela.ui.outline

import fr.lip6.move.promela.promela.Active
import fr.lip6.move.promela.promela.Atomic
import fr.lip6.move.promela.promela.Expression
import fr.lip6.move.promela.promela.Instruction
import fr.lip6.move.promela.promela.Iteration
import fr.lip6.move.promela.promela.NamedProcess
import fr.lip6.move.promela.promela.PromelaSpecification
import fr.lip6.move.promela.promela.Reference
import fr.lip6.move.promela.promela.Selection
import fr.lip6.move.promela.promela.Sequence
import fr.lip6.move.promela.promela.VariableDeclaration
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode
import fr.lip6.move.promela.promela.InitProcess
import fr.lip6.move.promela.promela.LabeledInstruction

/**
 * Promela customization of the default outline structure.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#outline
 */
class PromelaOutlineTreeProvider extends DefaultOutlineTreeProvider {

	/** Indication des feuilles */
	override _isLeaf(EObject o) {
		switch (o) {
			//Falses
			Atomic:
				false
			Selection:
				false
			Iteration:
				false
			// Trues
			VariableDeclaration:
				true
			LabeledInstruction:
				o.instruction._isLeaf
			Reference:
				true
			Expression:
				true
			Instruction:
				true
			Active:
				true
			// Others
			default:
				super._isLeaf(o)
		}
	}

	static val OUTWRAP = 5

	/** Création des Fils */
	def void _createChildren(DocumentRootNode outlineNode, PromelaSpecification ps) {

		// Regroup in subtree [eventuellement conditionné au nombre de vars]
		// TODO: setimage
		if (ps.macros.size < OUTWRAP)
			ps.macros.forEach[macro|createNode(outlineNode, macro)]
		else {
			val macrosNode = new WrapperOutlineNode(outlineNode, "Macros")

			// MV image ailleurs
			//TOFIX : Image!!
			ps.macros.forEach[macro|createNode(macrosNode, macro)]
		}

		if (ps.mtypes.size < OUTWRAP)
			ps.mtypes.forEach[mtype|createNode(outlineNode, mtype)]
		else {
			val mtypeNode = new WrapperOutlineNode(outlineNode, "Messages Types")

			// MV image ailleurs
			ps.mtypes.forEach[createNode(mtypeNode, it)]
		}

		if (ps.userTypes.size < OUTWRAP)
			ps.userTypes.forEach[createNode(outlineNode, it)]
		else {
			val userTypeNode = new WrapperOutlineNode(outlineNode, "User Types")

			// MV image ailleurs
			ps.userTypes.forEach[createNode(userTypeNode, it)]
		}

		if (ps.globalVariables.size < OUTWRAP)
			ps.globalVariables.forEach[createNode(outlineNode, it)]
		else {
			val gvNode = new WrapperOutlineNode(outlineNode, "Variables Globales")

			// MV image ailleurs
			ps.globalVariables.forEach[createNode(gvNode, it)]

		}
		ps.processes.forEach[proc|createNode(outlineNode, proc)]

	//TODO: flatten Sequences!!
	//HOWTO: méthode associé type?
	}

	def void _createChildren(IOutlineNode processNode, NamedProcess np) {
		if (np.active != null)
			createNode(processNode, np.active)

		// Applati séquences
		np.corps.steps.forEach[createNode(processNode, it)]

	}

	def void _createChildren(IOutlineNode outlineNode, LabeledInstruction li) {
		var i = li.instruction
		switch (i) {
			Sequence:
				i.steps.forEach[createNode(outlineNode, it)]
			Iteration: {
				i.options.forEach[createNode(outlineNode, it)]
				if(i.^else != null) createNode(outlineNode, i.^else)
			}
			Selection: {
				i.options.forEach[createNode(outlineNode, it)]
				if(i.^else != null) createNode(outlineNode, i.^else)
			}
		}

	//TODO handle other nested..
	}


		def void _createChildren(IOutlineNode outlineNode, InitProcess i) {
			i.corps.steps.forEach[createNode(outlineNode, it)]
		}

		def void _createChildren(IOutlineNode outlineNode, Iteration i) {

			//HERE
			i.options.forEach[createNode(outlineNode, it)]
			if(i.^else != null) createNode(outlineNode, i.^else)
		}

		def void _createChildren(IOutlineNode outlineNode, Selection s) {
			s.options.forEach[createNode(outlineNode, it)]
			if(s.^else != null) createNode(outlineNode, s.^else)
		}

		def void _createChildren(IOutlineNode outlineNode, Atomic a) {
			a.corps.steps.forEach[createNode(outlineNode, it)]
		}

	//	/**
	// * Util method to handle right Labeled instruction
	// */
	//	def EObject orInstr(Step step) {
	//		switch (step) {
	//			LabeledInstruction: step.instruction
	//			default: step
	//		}
	//	}
	}
