package fr.lip6.move.gal.gal2java;

public interface IState {

	public int get (int varIndex);
	public void set (int varIndex, int varValue);
	public IState copy ();
	
}
