package fr.lip6.move.runtime.interfaces;

import java.util.List;

public interface IState extends Cloneable{
	int getNumberOfVariables();
	int getNumberOfArrays();
	int getNumberOfLists();
	
	void addVariable(String varName, Integer value);
	void setVariable(String varName, Integer value);
	Integer getVariable(String varName);
	
	void createArray(String arrayName, List<Integer> initValues);
	void setValueInArray(String arrayName, int indexOfValue, Integer value);
	Integer getValueInArray(String arrayName, int indexOfValue);
	int getSizeOfArray(String arrayName);
	
	void createList(String listName, List<Integer> initValues);
	void popInList(String listName);
	Integer peekInList(String listName);
	void pushInList(String listName, Integer valueToPush);
	Integer getValueInList(String listName, int indexOfValue);
	int getSizeOfList(String listName);
	
	Object clone();
}
