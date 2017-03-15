package fr.lip6.move.promela.utils

import fr.lip6.move.promela.promela.Assignment
import fr.lip6.move.promela.promela.AtomicRef
import fr.lip6.move.promela.promela.DefineIntMacro
import fr.lip6.move.promela.promela.Instruction
import fr.lip6.move.promela.promela.LiteralConstant
import fr.lip6.move.promela.promela.Poll
import fr.lip6.move.promela.promela.PromelaFactory
import fr.lip6.move.promela.promela.Receive
import fr.lip6.move.promela.promela.Send
import fr.lip6.move.promela.promela.Step

class PromelaUtils {

	def static makePmlInt(int i) {
		val pint = PromelaFactory.eINSTANCE.createLiteralConstant();
		pint.setValue(i);
		pint
	}

	def dispatch static int getIntValue(AtomicRef ai) {
		var v = ai.ref
		switch (v) {
			DefineIntMacro: v.initValue
			default: throw new UnsupportedOperationException("Ceci n'est pas un Atomic Integer")
		}
	}

	def dispatch static int getIntValue(LiteralConstant lc) {
		lc.value
	}

	def getVarRef(Assignment a) {
		var r = a.^var
		switch (r) {
			AtomicRef: r //MAYBE ref?
			default: throw illegal("This is not a correct Assignment:" + a)
		}
	}

	def static getChannelRef(Send s) {
		var c = s.channel
		switch (c) {
			AtomicRef: c
			default: throw illegal("This is not a correct Send:" + s)
		}
	}

	def static asInstruction(Step step) {
		if (!(step instanceof Instruction)) {
			throw illegal(step + "Should be an Instruction!");
		}
		step as Instruction
	}

	def static getChannelRef(Receive r) {
		var c = r.channel
		switch (c) {
			AtomicRef: c
			default: throw illegal("This is not a correct Receive:" + r)
		}
	}

	def static getChannelRef(Poll p) {
		var c = p.channel
		switch (c) {
			AtomicRef: c
			default: throw illegal("This is not a correct Poll" + p)
		}
	}

	def private static illegal(String m) {
		new IllegalArgumentException(m)

	}

}
