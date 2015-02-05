package fr.lip6.move.promela.togal.transform;

// BEWARE import!! (souvent Construction Promela ont meme nom de les Gal)
import static fr.lip6.move.promela.togal.utils.GALUtils.combineAnd;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeAssignDec;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeAssignInc;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeGALBool;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.illegal;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.unsupported;
import static fr.lip6.move.promela.utils.PromelaUtils.asInstruction;
import static fr.lip6.move.promela.utils.PromelaUtils.getChannelRef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.WrapBoolExpr;
import fr.lip6.move.promela.promela.And;
import fr.lip6.move.promela.promela.Assignment;
import fr.lip6.move.promela.promela.AssignmentOperators;
import fr.lip6.move.promela.promela.Atomic;
import fr.lip6.move.promela.promela.AtomicRef;
import fr.lip6.move.promela.promela.BinaryIntExpression;
import fr.lip6.move.promela.promela.BitComplement;
import fr.lip6.move.promela.promela.Break;
import fr.lip6.move.promela.promela.Comparison;
import fr.lip6.move.promela.promela.Condition;
import fr.lip6.move.promela.promela.DefineIntMacro;
import fr.lip6.move.promela.promela.Expression;
import fr.lip6.move.promela.promela.False;
import fr.lip6.move.promela.promela.Goto;
import fr.lip6.move.promela.promela.Instruction;
import fr.lip6.move.promela.promela.LiteralConstant;
import fr.lip6.move.promela.promela.MTypeSymbol;
import fr.lip6.move.promela.promela.MemVariable;
import fr.lip6.move.promela.promela.Not;
import fr.lip6.move.promela.promela.Or;
import fr.lip6.move.promela.promela.Poll;
import fr.lip6.move.promela.promela.ProcessDefinition;
import fr.lip6.move.promela.promela.Receive;
import fr.lip6.move.promela.promela.Referable;
import fr.lip6.move.promela.promela.Reference;
import fr.lip6.move.promela.promela.Run;
import fr.lip6.move.promela.promela.Send;
import fr.lip6.move.promela.promela.Skip;
import fr.lip6.move.promela.promela.Step;
import fr.lip6.move.promela.promela.StructRef;
import fr.lip6.move.promela.promela.TabRef;
import fr.lip6.move.promela.promela.True;
import fr.lip6.move.promela.promela.UnaryMinus;
import fr.lip6.move.promela.togal.transform.environment.Environment;
import fr.lip6.move.promela.togal.transform.representations.ChannelRepresentation;
import fr.lip6.move.promela.togal.transform.representations.RunRepresentation;
import fr.lip6.move.promela.typing.PBasicType;
import fr.lip6.move.promela.typing.PromelaType;
import fr.lip6.move.promela.typing.PromelaTypeProvider;

public class Converter {

	/** Data Stores */
	final Environment env;
	private Map<Run, RunRepresentation> runs;

	public Converter(Environment env) {
		super();
		this.env = env;
		runs = new HashMap<Run, RunRepresentation>();
	}

	public Environment getEnv() {
		return env;
	}

	/** Expression */

	public BooleanExpression convertBoolean(Expression src) {

		if (src instanceof LiteralConstant) {
			return makeGALBool(((LiteralConstant) src).getValue());

		}
		if (src instanceof True) {
			return GalFactory.eINSTANCE.createTrue();

		} else if (src instanceof False) {
			return GalFactory.eINSTANCE.createFalse();
		} else if (src instanceof Reference) {
			// HERE HACK
			IntExpression ie = convertInt(src);
			return GF2.createComparison(ie, ComparisonOperators.NE, GF2.constant(0));

		} else if (src instanceof MTypeSymbol) {
			// MAYBE: treat as an error?
			// Convertit vers bool.
			MTypeSymbol mes = (MTypeSymbol) src;
			Constant res = env.getSymbConst(mes);
			return makeGALBool(res.getValue());

		} else if (src instanceof And) {
			And a = (And) src;

			BooleanExpression l, r;
			l = convertBoolean(a.getLeft());
			r = convertBoolean(a.getRight());

			fr.lip6.move.gal.And and = GalFactory.eINSTANCE.createAnd();
			and.setLeft(l);
			and.setRight(r);
			return and;
		} else if (src instanceof Or) {
			Or o = (Or) src;

			BooleanExpression l, r;
			l = convertBoolean(o.getLeft());
			r = convertBoolean(o.getRight());

			fr.lip6.move.gal.Or or = GalFactory.eINSTANCE.createOr();
			or.setLeft(l);
			or.setRight(r);
			return or;
		} else if (src instanceof Not) {
			Not n = (Not) src;
			BooleanExpression be = convertBoolean(n.getValue());
			fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
			not.setValue(be);
			return not;
		} else if (src instanceof Comparison) {
			Comparison c = (Comparison) src;
			fr.lip6.move.gal.Comparison nc = GalFactory.eINSTANCE
					.createComparison();

			IntExpression l, r;
			l = convertInt(c.getLeft());
			r = convertInt(c.getRight());
			nc.setLeft(l);
			nc.setRight(r);
			fr.lip6.move.promela.promela.ComparisonOperators cop = c
					.getOperator();
			ComparisonOperators ncop;

			switch (cop) {
			case GT:
				ncop = ComparisonOperators.GT;
				break;
			case LT:
				ncop = ComparisonOperators.LT;
				break;
			case GE:
				ncop = ComparisonOperators.GE;
				break;
			case LE:
				ncop = ComparisonOperators.LE;
				break;
			case EQ:
				ncop = ComparisonOperators.EQ;
				break;
			case NE:
				ncop = ComparisonOperators.NE;
				break;
			default:
				throw illegal("This is not a comparison");
			}
			nc.setOperator(ncop);
			return nc;
		}
		throw unsupported(src);

	}

	public fr.lip6.move.gal.IntExpression convertInt(Expression src) {

		if (src instanceof LiteralConstant) {
			LiteralConstant def = (LiteralConstant) src;
			return GF2.constant(def.getValue());
		}
		if (src instanceof True) {
			return GF2.constant(1);
		} else if (src instanceof False) {
			return GF2.constant(0);
		} else if (src instanceof AtomicRef) {
			return convertInt((AtomicRef) src);
		} else if (src instanceof TabRef) {
			return convertInt((TabRef) src);
		} else if (src instanceof StructRef) {
			return convertInt((StructRef) src);

		} else if (src instanceof MTypeSymbol) {
			// Convertit vers entier
			MTypeSymbol mes = (MTypeSymbol) src;
			return env.getSymbConst(mes);

		} else if (src instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) src;
			// PREFIX KILL ME NOW
			fr.lip6.move.gal.BinaryIntExpression nbin = GalFactory.eINSTANCE
					.createBinaryIntExpression();
			IntExpression l, r;
			l = convertInt(bin.getLeft());
			r = convertInt(bin.getRight());
			nbin.setLeft(l);
			nbin.setRight(r);

			nbin.setOp(bin.getOp());
			// > d'ou la chaine de craactère plutot que l'énum dans la grammaire
			// GAL...

			return nbin;

		} else if (src instanceof BitComplement) {
			BitComplement bc = (BitComplement) src;
			fr.lip6.move.gal.BitComplement nbc = GalFactory.eINSTANCE
					.createBitComplement();
			nbc.setValue(convertInt(bc.getValue()));
			return nbc;
		} else if (src instanceof UnaryMinus) {
			UnaryMinus bc = (UnaryMinus) src;
			fr.lip6.move.gal.UnaryMinus nbc = GalFactory.eINSTANCE
					.createUnaryMinus();
			nbc.setValue(convertInt(bc.getValue()));
			return nbc;
		} else if (src instanceof Poll) {
			Poll p = (Poll) src;
			AtomicRef r = getChannelRef(p);
			ChannelRepresentation cr = env.getChannel(r);
			BooleanExpression bep = cr.makePoll(p, this);

			WrapBoolExpr wbe = GalFactory.eINSTANCE.createWrapBoolExpr();
			wbe.setValue(bep);
			return wbe;
		} else if (src instanceof Run) {
			throw unsupported("Run are not yet supported as expression");
		} else if (src instanceof Comparison) {
			Comparison c = (Comparison) src;
			WrapBoolExpr wbe = GalFactory.eINSTANCE.createWrapBoolExpr();
			wbe.setValue(this.convertBoolean(c));
			return wbe;
		}
		// FIXME!! ALL THE THINGSSSSSSS
		throw unsupported(src);

	}

	public IntExpression convertInt(final StructRef ref) {
		throw unsupported("Struct non supporté actuellement");
	}

	public IntExpression convertInt(final AtomicRef ref) {

		final Referable target = ref.getRef();

		if (target instanceof DefineIntMacro) {
			return env.getMacroRef(ref);

		} else if (target instanceof MemVariable) {
			MemVariable var = (MemVariable) ref.getRef();
			// HERE: local vs global.

			// Variable atomique
			if (!var.isArray()) {
				Variable varGal = // varMap.get(var);
				env.getAtomic(var);
				// HERE

				if (varGal == null) {
					throw illegal("Could not find variable ref :"
							+ var.getName());
				}

				return GF2.createVariableRef(varGal);

			} else {
				throw illegal("Cannot access Tab array from AtomicRef");
			}
		}
		// Note: no convertion of Chan variable!
		// else TODO
		throw illegal("What is this ref? " + ref);
	}

	public IntExpression convertInt(final TabRef ref) {

		final Reference base = ref.getRef();
		if (!(base instanceof AtomicRef))
			throw unsupported("Struc are not supported for now");
		final AtomicRef baseref = (AtomicRef) base;
		Referable target = baseref.getRef();
		if (target instanceof MemVariable) {
			MemVariable var = (MemVariable) target;
			ArrayPrefix ap = env.getArray(var);
			if (ap == null) {
				throw illegal("Could not find tab ref ");
			}

			return GF2.createArrayVarAccess(ap, this.convertInt(ref.getIndex()));
			// CHECK
		}

		throw illegal("What is this ref? " + ref);
	}

	/*-*****************-*/

	public BooleanExpression getGuard(Instruction i) {

		if (i instanceof Assignment) {
			Assignment a = (Assignment) i;
			if (a.getKind() == AssignmentOperators.STD)
				return getGuard(a.getNewValue());
		}

		if (i instanceof Atomic) {
			Atomic at = (Atomic) i;

			Step fs = at.getCorps().getSteps().get(0);

			return getGuard(asInstruction(fs));
		}

		if (i instanceof Condition) {
			Condition c = (Condition) i;
			Expression cond = c.getCond();

			if (cond instanceof Run)
				return GalFactory.eINSTANCE.createTrue();
			// LATER improve

			// Regarde si dans l'expression il n'y a pas des canaux ou non non
			// exécutables,
			BooleanExpression tmp = getGuard(cond);
			BooleanExpression be;
			// HERE!!
			PromelaType type = PromelaTypeProvider.typeFor(cond);
			if (type instanceof PBasicType
					&& ((PBasicType) type) == PBasicType.BOOL)
				// REFACT!!!
				be = convertBoolean(cond);
			else
				be = GF2.createComparison(this.convertInt(cond),
						ComparisonOperators.NE, GF2.constant(0));
			// CHECK
			if (tmp instanceof fr.lip6.move.gal.True)
				return be;
			else
				return GF2.and(be, tmp);

		} else if (i instanceof Send) {
			Send s = (Send) i;
			AtomicRef r = getChannelRef(s);
			ChannelRepresentation cr = env.getChannel(r);
			// HERE recursive expression!!

			return cr.makeSendGuard(this);
		} else if (i instanceof Receive) {
			Receive re = (Receive) i;
			AtomicRef r = getChannelRef(re);
			ChannelRepresentation cr = env.getChannel(r);
			return cr.makeReceiveGuard(re, this);
		}

		// Par défault c'est OOOOOKKKKAAAAAY
		return GalFactory.eINSTANCE.createTrue();
	}

	public BooleanExpression getGuard(Expression e) {
		// I wich I could use Xtend for a Recursive one.

		TreeIterator<EObject> tit = e.eAllContents();
		// Bansique en mode and, à) rafiner
		List<BooleanExpression> conds = new ArrayList<BooleanExpression>();

		while (tit.hasNext()) {
			Expression ex = (Expression) tit.next();
			if (ex instanceof Poll) {
				Poll p = (Poll) ex;
				AtomicRef r = getChannelRef(p);
				ChannelRepresentation cr = env.getChannel(r);
				BooleanExpression bep = cr.makePoll(p, this);
				conds.add(bep);
			}
			// LATER: run
			// ?

		}

		// si aucune condition récoltée, on envoit vrai.
		if (conds.isEmpty())
			return GalFactory.eINSTANCE.createTrue();
		else
			return combineAnd(conds);
	}

	/*-**************************************************************-*/

	public List<Statement> convertInstr(Instruction i) {
		if (i instanceof Condition) {
			Expression tmp = ((Condition) i).getCond();
			if (tmp instanceof Run) {
				// Runs souvent capturés sous forme de condition
				// (puisqu'expression)
				Run r = (Run) tmp;
				return runs.get(r).makeInit(this);
			} else {
				// Sinon Pas d'action, guarde gérée à part.
				return Collections.emptyList();
			}
		} else if (i instanceof Send) {
			Send s = (Send) i;
			AtomicRef r = getChannelRef(s);
			ChannelRepresentation cr = env.getChannel(r);
			return cr.makeSend(s, this);
		} else if (i instanceof Receive) {
			Receive re = (Receive) i;
			AtomicRef r = getChannelRef(re);
			ChannelRepresentation cr = env.getChannel(r);
			return cr.makeReceive(re, this);

			// Simple dispatches
		} else if (i instanceof Assignment) {
			return convertInstr((Assignment) i);
		} else if (i instanceof Atomic) {
			return convertInstr((Atomic) i);
		} else if (i instanceof Skip || i instanceof Break || i instanceof Goto) {
			// CHECK maybe print a warning?
			// Renvoi action vide. s'ils apparaissent c'est qu'il faut juste une
			// transition pour mettre à jour le PC
			return Collections.emptyList();
		}

		// FIXME All the other instructinos!
		throw unsupported(i);
	}

	public List<Statement> convertInstr(Atomic a) {
		// LATER: à revoir avec révision traduciton sémantique des atomic atomic
		EList<Step> steps = a.getCorps().getSteps();
		List<Statement> acs = new ArrayList<Statement>();
		for (Step s : steps) {
			acs.addAll(convertInstr((Instruction) s));
		}
		if (!acs.isEmpty())
			acs.get(0).setComment("/** First atomic instruction */");
		return acs;
	}

	public List<Statement> convertInstr(Assignment a) { // Action?
		// Retrieve Var
		Expression eref = a.getVar();
		if (!(eref instanceof Reference)) {
			throw illegal("Variable should be a reference rather than a lambda.");
		}
		fr.lip6.move.gal.Reference galRef = env.getRef((Reference) eref, this);
		fr.lip6.move.gal.Statement ass = null;

		switch (a.getKind()) {
		case STD:
			ass = GF2.createAssignment(galRef, convertInt(a.getNewValue()));
			break;
		case INC:
			ass = makeAssignInc(galRef);
			break;
		case DEC:
			ass = makeAssignDec(galRef);
		}
		ass.setComment("/** Assignment */");
		// pas évident récupérer nom.
		return Collections.<Statement> singletonList(ass);
	}

	/*-**************************************************************-*/

	public int getMacroValue(AtomicRef ref) {
		return env.getMacroValue(ref).getValue();
	}

	/*-**************************************************************-*/
	// Gestion des runs:

	public void registerRun(Run r) {
		if (runs.containsKey(r))
			throw illegal("This Run as already been handled");

		// Crée la Pc Var
		RunRepresentation rr = new RunRepresentation(r);
		// Enregistre liste des runs
		runs.put(r, rr);
	}

	public RunRepresentation retrieveRepresentation(ProcessDefinition processDef) {
		// LATEr version du consult?
		for (RunRepresentation r : runs.values()) {
			if (r.getProcDef() == processDef && !r.isUsed()) {
				r.setUsed();
				return r;
			}
		}
		throw illegal("This ProcessDef should retrieve a Run representation");
		// LATER: indexing
	}

}
