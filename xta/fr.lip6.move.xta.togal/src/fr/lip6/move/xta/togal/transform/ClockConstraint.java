package fr.lip6.move.xta.togal.transform;

import fr.lip6.move.timedAutomata.DeclId;


class ClockConstraint {
	DeclId clock;
	int upTo;
	public ClockConstraint(DeclId clock, int upTo) {
		this.clock = clock;
		this.upTo = upTo;
	}

}