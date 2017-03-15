package fr.lip6.move.promela.typing

import fr.lip6.move.promela.promela.And
import fr.lip6.move.promela.promela.AtomicRef
import fr.lip6.move.promela.promela.BasicType
import fr.lip6.move.promela.promela.ChanVariable
import fr.lip6.move.promela.promela.Comparison
import fr.lip6.move.promela.promela.DefineIntMacro
import fr.lip6.move.promela.promela.Expression
import fr.lip6.move.promela.promela.False
import fr.lip6.move.promela.promela.LiteralConstant
import fr.lip6.move.promela.promela.MemVariable
import fr.lip6.move.promela.promela.Not
import fr.lip6.move.promela.promela.Or
import fr.lip6.move.promela.promela.StructField
import fr.lip6.move.promela.promela.StructTypeRef
import fr.lip6.move.promela.promela.TabRef
import fr.lip6.move.promela.promela.True
import fr.lip6.move.promela.promela.Type
import fr.lip6.move.promela.promela.TypeDef
import java.util.ArrayList
import java.util.HashMap

import static extension fr.lip6.move.promela.utils.PromelaUtils.*

class PromelaTypeProvider {

	// Prevent instantiation
	private new() {
	}

	// Bornes pour les Shorts
	static final val MAXS = Math.pow(2, 15) - 1
	static final val MINS = - Math.pow(2, 15)

	def static dispatch PromelaType typeFor(Expression e) {
		switch (e) {
			True:
				PBasicType.BOOL
			False:
				PBasicType.BOOL
			Or:
				PBasicType.BOOL
			And:
				PBasicType.BOOL
			Not:
				PBasicType.BOOL
			Comparison:
				PBasicType.BOOL
		}
	}

	def static dispatch PromelaType typeFor(LiteralConstant lc) {

		//différencier les différentes tailles pour LiteralConstant qui est un int
		var v = lc.value
		if ((v == 0) || (v == 1))
			return PBasicType.BIT
		else if ((v >= 2) && (v < 256))
			return PBasicType.BYTE
		else if ((v >= MINS) && (v < MAXS ))
			return PBasicType.SHORT
		else
			return PBasicType.INT
	}
	def static dispatch PromelaType typeFor(DefineIntMacro di) {
		var v = di.initValue
		if ((v == 0) || (v == 1))
			return PBasicType.BIT
		else if ((v >= 2) && (v < 256))
			return PBasicType.BYTE
		else if ((v >= MINS) && (v < MAXS ))
			return PBasicType.SHORT
		else
			return PBasicType.INT
	}

	def static dispatch PromelaType typeFor(AtomicRef varRef) {

		var v = varRef.ref
		if(v === null) return null
		switch (v) {
			MemVariable: return v.typeFor
			ChanVariable: return v.typeFor
			DefineIntMacro: return v.typeFor
		}
	}

	def static dispatch PromelaType typeFor(TabRef tabRef) {
		if(tabRef.ref === null) return null
		return tabRef.ref.typeFor
	}

	def static dispatch PromelaType typeFor(StructTypeRef varRef) {
		if(varRef.ref === null) return null
		return varRef.ref.typeFor
	}

	def static dispatch PromelaType typeFor(MemVariable mem) {
		var type = mem.type

		if (type instanceof BasicType) {
			var btype = type as BasicType
			if (mem.array) {
				new PArrayType(PBasicType.get(btype), mem.length.getIntValue)
				return PBasicType.get(btype);

			//TODO: utils
			} else {
				return PBasicType.get(btype); //?
			}
		} else if (type instanceof StructTypeRef) {
			var name = type.ref.name
			var struct = new HashMap<String, PromelaType>();
			val f = type.ref.champs
			for (StructField t : f) {
				struct.put(t.name, PBasicType.get(t.type as BasicType))
			}
			return new PUserType(struct, name);
		}
	}

	def static dispatch PChannelType typeFor(ChanVariable chan) {

		val size = (chan.initValue.size as LiteralConstant).value;
		val varL = new ArrayList<PromelaType>

		for (Type t : chan.initValue.types) {
			if (t instanceof BasicType) {
				varL.add(PBasicType.get(t))
			} else if (t instanceof StructTypeRef) {
				var name = t.ref.name
				var struct = new HashMap<String, PromelaType>();
				val f = t.ref.champs
				for (StructField ty : f) {
					struct.put(ty.name, PBasicType.get(ty.type as BasicType))
				}
				varL.add(new PUserType(struct, name))
			}
		}

		return new PChannelType(varL, size);
	}

	def static dispatch PUserType typeFor(TypeDef typedef) {
		val types = new HashMap<String, PromelaType>();
		var name = typedef.name
		typedef.champs.forEach [
			switch (it.type) {
				BasicType:
					types.put(it.name, PBasicType.get(it.type as BasicType))
			}
		]
		return new PUserType(types, name);
	}
}
