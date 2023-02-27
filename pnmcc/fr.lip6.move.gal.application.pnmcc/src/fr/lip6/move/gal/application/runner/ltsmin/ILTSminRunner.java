package fr.lip6.move.gal.application.runner.ltsmin;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.runner.IRunner;
import fr.lip6.move.gal.structural.SparsePetriNet;

public interface ILTSminRunner extends IRunner {

	void setNet(SparsePetriNet spn);

	void setTGBA(TGBA negProp, String stateBasedHOA);

}