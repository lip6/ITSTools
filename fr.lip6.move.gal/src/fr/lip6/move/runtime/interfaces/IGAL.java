package fr.lip6.move.runtime.interfaces;

import java.util.List;

public interface IGAL {
	String getName();
	IState getInitState();
	List<ITransition> getTransitions();
	boolean getTransient(IState entryState);
}
