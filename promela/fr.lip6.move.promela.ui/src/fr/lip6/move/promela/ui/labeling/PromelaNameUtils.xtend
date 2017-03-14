package fr.lip6.move.promela.ui.labeling

import fr.lip6.move.promela.promela.AtomicRef
import fr.lip6.move.promela.promela.Expression
import fr.lip6.move.promela.promela.StructRef
import fr.lip6.move.promela.promela.TabRef

class PromelaNameUtils {

	def static String refName(Expression e) {
		switch (e) {
			AtomicRef: e.ref.name
			TabRef: e.ref.refName
			StructRef: e.ref.refName
			default: throw new IllegalArgumentException("Not a Ref!")
		}

	}
}
