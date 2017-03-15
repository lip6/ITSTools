package fr.lip6.move.promela.validation

import fr.lip6.move.promela.promela.And
import fr.lip6.move.promela.promela.Assignment
import fr.lip6.move.promela.promela.AssignmentOperators
import fr.lip6.move.promela.promela.AtomicRef
import fr.lip6.move.promela.promela.BasicType
import fr.lip6.move.promela.promela.ChanVariable
import fr.lip6.move.promela.promela.Comparison
import fr.lip6.move.promela.promela.DefineIntMacro
import fr.lip6.move.promela.promela.Expression
import fr.lip6.move.promela.promela.LiteralConstant
import fr.lip6.move.promela.promela.MemVariable
import fr.lip6.move.promela.promela.Not
import fr.lip6.move.promela.promela.Or
import fr.lip6.move.promela.promela.PromelaPackage
import fr.lip6.move.promela.promela.Receive
import fr.lip6.move.promela.promela.ReceiveArg
import fr.lip6.move.promela.promela.Reference
import fr.lip6.move.promela.promela.Send
import fr.lip6.move.promela.promela.StructTypeRef
import fr.lip6.move.promela.promela.TabRef
import fr.lip6.move.promela.promela.VariableDeclaration
import fr.lip6.move.promela.typing.PBasicType
import fr.lip6.move.promela.typing.PUserType
import fr.lip6.move.promela.typing.PromelaType
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.validation.Check

import static extension fr.lip6.move.promela.typing.PromelaTypeProvider.*

class PromelaTypeValidator extends AbstractPromelaValidator {

	public static val PROMELA_WRONG_TYPE = "106"

	def private checkExpectedBoolean(Expression exp, EReference reference) {
		checkExpectedType(exp, PBasicType.BOOL, reference)
	}

	def private checkExpectedType(Expression exp, PromelaType expectedType, EReference reference) {
		val actualType = getTypeAndCheckNotNull(exp, reference)
		if (actualType != expectedType)
			error("expected " + expectedType.toString + " type, but was " + actualType.toString, reference,
				PROMELA_WRONG_TYPE)
	}

	def getTypeAndCheckNotNull(Expression exp, EReference ref) {
		var type = exp.typeFor
		if (type === null)
			error("null type", ref, PROMELA_WRONG_TYPE)
		return type;
	}

	@Check
	def checkType(Not not) {
		checkExpectedBoolean(not.value, PromelaPackage.Literals::NOT__VALUE)
	}

	@Check
	def checkType(And and) {
		checkExpectedBoolean(and.left, PromelaPackage.Literals::AND__LEFT)
		checkExpectedBoolean(and.right, PromelaPackage.Literals::AND__RIGHT)
	}

	@Check
	def checkType(Or or) {
		checkExpectedBoolean(or.left, PromelaPackage.Literals::OR__LEFT)
		checkExpectedBoolean(or.right, PromelaPackage.Literals::OR__RIGHT)
	}

	@Check
	def checkType(Comparison com) {
		if (com.left.typeFor instanceof PBasicType) {
			if (com.right.typeFor instanceof PBasicType) {
				if ((!isSubtypeOf(com.left.typeFor, com.right.typeFor)) &&
					(!isSubtypeOf(com.right.typeFor, com.left.typeFor))) {
					error("wrong type", PromelaPackage.Literals::COMPARISON__LEFT, PROMELA_WRONG_TYPE)
				}
			}
		}

	}

	@Check
	def checkType(MemVariable v) {
		if (v.type instanceof BasicType) {
			if (v.initValue !== null) {
				if (v.initValue.typeFor instanceof PBasicType) {
					if (!isSubtypeOf(v.typeFor, v.initValue.typeFor))
						error(
							"expected " + (v.type as BasicType).name + " type, but was " +
								(v.initValue.typeFor as PBasicType).name(),
							PromelaPackage.Literals::MEM_VARIABLE__INIT_VALUE, PROMELA_WRONG_TYPE)
				}
			}
		}
	}

	@Check
	def checkType(Assignment e) {
		if (e.kind == AssignmentOperators.STD) {
			var v1 = e.^var
			var v2 = e.newValue
			if ((v1 instanceof Reference) && (v2 instanceof Reference)) {
				if (((v1 as Reference) instanceof TabRef) && ((v2 as Reference) instanceof TabRef)) {

					var t = ((v1 as TabRef).ref as AtomicRef).ref as MemVariable
					var nv = ((v2 as TabRef).ref as AtomicRef).ref as MemVariable

					if (!mayContain(t, nv)) {
						warning(
							"expected " + (t.type as BasicType).name + " type, but was " +
								(nv.type as BasicType).name, PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
							PROMELA_WRONG_TYPE)
					}
				}
			}
			if ((v1 instanceof Reference) && (v2 instanceof AtomicRef)) {
				if ((v1 as Reference)instanceof TabRef) {
					var t = ((v1 as TabRef).ref as AtomicRef).ref as MemVariable
					var nv = (v2 as AtomicRef).ref
					if (nv instanceof MemVariable) {
						if (!mayContain(t, nv)) {
							warning(
								"expected " + (t.type as BasicType).name + " type, but was " +
									(nv.type as BasicType).name, PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
								PROMELA_WRONG_TYPE)
						}
					} else if (nv instanceof DefineIntMacro) {
						if (!isSubtypeOf(t.typeFor, nv.typeFor)) {
							warning(
								"expected " + (t.type as BasicType).name + " type, but was " +
									(nv.typeFor as PBasicType).name(), PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
								PROMELA_WRONG_TYPE)
						}
					}
				}
			}
			if ((v1 instanceof Reference) && (v2 instanceof LiteralConstant)) {
				if (v1 instanceof TabRef) {
					var t = (v1.ref as AtomicRef).ref as MemVariable
					var nv = v2 as LiteralConstant
					if (!isSubtypeOf(t.typeFor, nv.typeFor)) {
						warning(
							"expected " + (t.type as BasicType).name + " type, but was " +
								(nv.typeFor as PBasicType).name(), PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
							PROMELA_WRONG_TYPE)
					}
				}
			}
			if ((v2 instanceof Reference) && (v1 instanceof AtomicRef)) {
				if ((v2 as Reference)instanceof TabRef) {
					var t = ((v2 as TabRef).ref as AtomicRef).ref as MemVariable
					var nv = (v1 as AtomicRef).ref as MemVariable
					if (!mayContain(nv, t)) {
						warning(
							"expected " + (nv.type as BasicType).name + " type, but was " +
								(t.type as BasicType).name, PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
							PROMELA_WRONG_TYPE)
					}
				}
			}
			if ((v1 instanceof AtomicRef) && (v2 instanceof AtomicRef)) {
				if (((v1 as AtomicRef).ref instanceof VariableDeclaration) &&
					((v2 as AtomicRef).ref instanceof VariableDeclaration)) {
					var t = (v1 as AtomicRef).ref as MemVariable
					var nv = (v2 as AtomicRef).ref as MemVariable

					if (!mayContain(t, nv)) {
						warning(
							"expected " + (t.type as BasicType).name + " type, but was " +
								(nv.type as BasicType).name, PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
							PROMELA_WRONG_TYPE)
					}
				}
				if (((v1 as AtomicRef).ref instanceof VariableDeclaration) &&
					((v2 as AtomicRef).ref instanceof DefineIntMacro)) {
					var t = (v1 as AtomicRef).ref as MemVariable
					var nv = (v2 as AtomicRef).ref as DefineIntMacro

					if (!isSubtypeOf(t.typeFor, nv.typeFor)) {
						warning(
							"expected " + (t.type as BasicType).name + " type, but was " +
								(nv.typeFor as PBasicType).name(), PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
							PROMELA_WRONG_TYPE)
					}
				}
			}
			if ((v1 instanceof AtomicRef) && (v2 instanceof LiteralConstant)) {
				if ((v1 as AtomicRef).ref instanceof VariableDeclaration) {
					var t = (v1 as AtomicRef).ref as MemVariable
					var nv = v2 as LiteralConstant
					if (!isSubtypeOf(t.typeFor, nv.typeFor)) {
						warning(
							"expected " + (t.type as BasicType).name + " type, but was " +
								(nv.typeFor as PBasicType).name(), PromelaPackage.Literals::ASSIGNMENT__NEW_VALUE,
							PROMELA_WRONG_TYPE)
					}
				}
			}
		}
	}

	@Check
	def checkType(Send e) {
		var sendargs = e.args
		var left = (e.channel as AtomicRef).ref as ChanVariable
		var args = sendargs.args

		for (Expression exp : args) {
			var i = 0;
			if (exp instanceof Reference) {
				if (exp instanceof AtomicRef) {
					if (exp.ref instanceof MemVariable) {
						if ((exp.ref as MemVariable).type instanceof BasicType) {
							if (!(left.initValue.types.get(i) instanceof BasicType)) {
								error("The channel does not contain this type of message",
									PromelaPackage.Literals::SEND__CHANNEL, PROMELA_WRONG_TYPE)
							} else {
								var typeRef = (exp.ref as MemVariable).typeFor
								if (!isSubtypeOf(PBasicType.get(left.initValue.types.get(i)as BasicType),
									typeRef)) {
									error(
										"The channel's value must be a " +
											(left.initValue.types.get(i) as BasicType).name,
										PromelaPackage.Literals::SEND__CHANNEL, PROMELA_WRONG_TYPE)
								}
							}
						} else if ((exp.ref as MemVariable).type instanceof StructTypeRef) {
							var typeRef = (exp.ref as MemVariable).typeFor
							if (left.initValue.types.get(i) instanceof BasicType) {
								error(
									"The channel's value must be a " +
										(left.initValue.types.get(i) as BasicType).name,
									PromelaPackage.Literals::SEND__CHANNEL, PROMELA_WRONG_TYPE)
							} else if (!isSubtypeOf(left.initValue.types.get(i).typeFor, typeRef)) {
								error("The channel doesn't contain this type of message ",
									PromelaPackage.Literals::SEND__CHANNEL, PROMELA_WRONG_TYPE)
							}
						}
					}
				}
			}
			i = i + 1;
		}
	}

	@Check
	def checkType(Receive e) {
		var args = e.args
		var left = (e.channel as AtomicRef).ref as ChanVariable
		for (ReceiveArg exp : args.recArgs) {
			var i = 0; //WHAT?
			if (exp instanceof Reference) {
				if (exp instanceof AtomicRef) {
					if (exp.ref instanceof MemVariable) {
						if ((exp.ref as MemVariable).type instanceof BasicType) {
							if (!(left.initValue.types.get(i) instanceof BasicType)) {
								error("The channel does not contain this type of message",
									PromelaPackage.Literals::RECEIVE__CHANNEL, PROMELA_WRONG_TYPE)

							} else {
								var typeRef = (exp.ref as MemVariable).typeFor
								if (!isSubtypeOf(PBasicType.get(left.initValue.types.get(i)as BasicType),
									typeRef)) {
									error(
										"The channel's value must be a " +
											(left.initValue.types.get(i) as BasicType).name,
										PromelaPackage.Literals::RECEIVE__CHANNEL, PROMELA_WRONG_TYPE)
								}
							}
						} else if ((exp.ref as MemVariable).type instanceof StructTypeRef) {
							var typeRef = (exp.ref as MemVariable).typeFor
							if (left.initValue.types.get(i) instanceof BasicType) {
								error(
									"The channel's value must be a " +
										(left.initValue.types.get(i) as BasicType).name,
									PromelaPackage.Literals::RECEIVE__CHANNEL, PROMELA_WRONG_TYPE)
							} else if (!isSubtypeOf(left.initValue.types.get(i).typeFor, typeRef)) {
								error("The channel doesn't contain this type of message ",
									PromelaPackage.Literals::RECEIVE__CHANNEL, PROMELA_WRONG_TYPE)
							}
						}
					}
				}
			}
			i = i + 1;
		}
	}

	def boolean mayContain(MemVariable mem1, MemVariable mem2) {
		isSubtypeOf(mem1.typeFor, mem2.typeFor);
	}

	//
	def dispatch  boolean isSubtypeOf(PBasicType t1, PBasicType t2) {
		t1.getBitSize >= t2.getBitSize
	}

	def dispatch  boolean isSubtypeOf(PromelaType t1, PromelaType t2) {

		(t1 instanceof PBasicType) && (t2 instanceof PBasicType) && isSubtypeOf(t1, t2) ||
			(t1 instanceof PUserType) && (t2 instanceof PUserType) && ((t1 as PUserType).name == (t2 as PUserType).name)

	}
}
