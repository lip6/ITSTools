package fr.lip6.move.runtime.interfaces;

public interface ITransition {
	String getName();
	boolean guard(final IState entryState);
	IState successor(final IState entryState);
} 
