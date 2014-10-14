package fr.lip6.move.promela.togal.transform.representations;

import static fr.lip6.move.promela.togal.utils.GALUtils.*;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeArrayAccess;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeAssign;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeAssignInc;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeComparison;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeGALInt;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeRef;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.unsupported;
import static fr.lip6.move.promela.typing.PromelaTypeProvider.typeFor;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.promela.promela.AtomicRef;
import fr.lip6.move.promela.promela.ChanVariable;
import fr.lip6.move.promela.promela.LiteralConstant;
import fr.lip6.move.promela.promela.Poll;
import fr.lip6.move.promela.promela.Receive;
import fr.lip6.move.promela.promela.ReceiveArg;
import fr.lip6.move.promela.promela.Send;
import fr.lip6.move.promela.togal.transform.Converter;
import fr.lip6.move.promela.typing.PChannelType;
import fr.lip6.move.promela.typing.PromelaType;

public class ChannelRepresentation {

	private ChanVariable pmlChan;
	private String name;
	private int size;
	private List<PromelaType> type;

	private ArrayPrefix gChanArray; // TODO: list when multiple type
	private Variable gChanAvail;

	private ChannelRepresentation(ChanVariable pmlChan, String name) {
		super();
		this.pmlChan = pmlChan;
		this.name = name;
		PChannelType pct = (PChannelType) typeFor(pmlChan);
		this.size = pct.getSize();

		this.type = pct.getKinds();
		if (type.size() != 1)
			throw unsupported("Channel à plusieurs types non gérés");

		// Create new VAR!!
		gChanAvail = GalFactory.eINSTANCE.createVariable();
		gChanAvail.setName(name + "__Channel_Avail");
		gChanAvail.setComment("/** Nombre d'item dans canal " + name + " */");
		gChanAvail.setValue(makeGALInt(size));

		// FIXME: simple channel.
		gChanArray = makeArray(name + "__Channel", size, makeGALInt(0));
		gChanArray.setComment("/** Canal " + name + " */");
	}

	public static ChannelRepresentation createRepresentation(
			ChanVariable pmlChan, String name) {
		return new ChannelRepresentation(pmlChan, name);
	}

	// TODO: tous les méthode de Poll, Send, (isGood?: pour rajouter garde)

	// //// Send

	public List<Actions> makeSend(Send s, Converter c) {
		boolean fifo = s.isFifo();
		if (!fifo)
			throw unsupported("Not fifo à venir");
		List<Actions> acs = new ArrayList<Actions>();
		// Need converter!!
		Actions as = makeAssign(
				makeArrayAccess(gChanArray, makeRef(gChanAvail)),
				c.convertInt(s.getArgs().getArgs().get(0)) // MAYBE RENAME
		);

		as.setComment("/** Emission sur le canal */");
		Actions aau = makeAssignInc(makeRef(gChanAvail));
		aau.setComment("/** Mise à jour du nombre available */");
		acs.add(as);
		acs.add(aau);
		return acs;
	}

	public BooleanExpression makeSendGuard(Converter c) {
		// LATER: appel récursif sur expressuon
		// comparaison avec size
		return makeComparison(makeRef(gChanAvail), makeGALInt(size),
				ComparisonOperators.LT);
	}

	// TODO: maybe, makeSendGuard !

	public List<Actions> makeReceive(Receive r, Converter c) {
		boolean random = !r.isNormal();
		boolean keep = r.isKeep();

		if (random)
			throw unsupported("Random Receive à venir");

		List<Actions> acs = new ArrayList<Actions>();

		// int offset = random ? 2 : 1;
		// RANDOM MORE RAFINE.

		if (r.getArgs().getRecArgs().size() != 1)
			throw unsupported("Only support 1 param receive");

		ReceiveArg rarg = r.getArgs().getRecArgs().get(0);

		if (rarg instanceof AtomicRef) {

			// Retrieve variable et mis eà jour
			Variable v = c.getEnv().getAtomic((AtomicRef) rarg);
			VariableRef vr = makeRef(v);
			Actions a = makeAssign(vr,
					makeArrayAccess(gChanArray, makeGALInt(0)));
			a.setComment("/** Reception depuis dans la variable " + v.getName()
					+ " */");
			acs.add(a);
		}
		// sinon c'est un receive filter!

		// Consommation du message
		if (!keep) {
			// on réécrit.
			// ForImpl f = GalFactory.eINSTANCE.createFor();
			// Bourrin en attendant le for...

			// c.getEnv().getAtomic(null)
			for (int i = 0; i < size - 1; i++) {
				acs.add(makeAssign(makeArrayAccess(gChanArray, makeGALInt(i)),
						makeArrayAccess(gChanArray, makeGALInt(i + 1))));
			}

		}

		return acs;

	}

	public BooleanExpression makeReceiveGuard(Receive r, Converter converter) {
		// LATER improve avec filtres
		BooleanExpression availGuard = makeComparison(makeRef(gChanAvail),
				makeGALInt(0), ComparisonOperators.GT);

		ReceiveArg rarg = r.getArgs().getRecArgs().get(0);
		if (rarg instanceof AtomicRef) // TODO see Macro!!
			return availGuard;
		else {
			if (rarg instanceof LiteralConstant) {
				LiteralConstant lc = (LiteralConstant) rarg;
				BooleanExpression filter =
				makeComparison(makeArrayAccess(gChanArray, makeGALInt(0)),
						makeGALInt(lc.getValue()), ComparisonOperators.EQ);
				return makeAnd(availGuard,filter);
			}
		}
		throw unsupported("Something bad happened in receive");
	}

	public BooleanExpression makePoll(Poll p, Converter c) {
		boolean random = !p.isNormal();
		// CHECK not an action
		// LATER improve avec filtres
		// (factorizer avec la garde du receive?)

		// ReceiveArg rarg = p.getArgs().getRecArgs().get(0);

		return makeComparison(makeRef(gChanAvail), makeGALInt(0),
				ComparisonOperators.GT);

	}

	// / Getters

	public ChanVariable getPmlChan() {
		return pmlChan;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public List<PromelaType> getType() {
		return type;
	}

	public ArrayPrefix getgChanArray() {
		return gChanArray;
	}

	public Variable getgChanAvail() {
		return gChanAvail;
	}

}
