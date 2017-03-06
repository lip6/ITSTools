package fr.lip6.move.runtime.interfaces;

import java.util.List;


/**
 * This interface represents a "state" in a GAL system.
 * A state can be seen like a great set, which contains lists, integers variables, and arrays, 
 * and their associated values, at a precise time.
 */
public interface IState extends Cloneable{
	
	/** Returns the number of variables in the system.*/
	int getNumberOfVariables();
	
	/** Returns the number of arrays in the system.   */
	int getNumberOfArrays();
	
	/** Returns the number of lists in the system.    */
	int getNumberOfLists();
	
	/**
	 * Add a variable in the state. 
	 * @param varName : The name of the new variable
	 * @param value :   Value of this new variable
	 */
	void addVariable(String varName, Integer value);
	
	/** Sets a value to an existing variable.*/
	void setVariable(String varName, Integer value);
	
	/** Returns the value of a variable */
	Integer getVariable(String varName);
	
	
	/** Create an array, initialized with each elements of a list of integers. */
	void createArray(String arrayName, List<Integer> initValues);
	
	/** Sets a value to an array element. */
	void setValueInArray(String arrayName, int indexOfValue, Integer value);
	
	/** Returns the value of an array element */
	Integer getValueInArray(String arrayName, int indexOfValue);
	
	/** Return the size of an array */
	int getSizeOfArray(String arrayName);
	
	/** Create a list, initialized with each elements of the list */
	void createList(String listName, List<Integer> initValues);
	
	/** Remove the first element of the list */
	void popInList(String listName);
	
	/** Returns the first element of a list, without removing it. */ 
	Integer peekInList(String listName);
	
	/** Push an integer to a list */
	void pushInList(String listName, Integer valueToPush);

	
	Integer getValueInList(String listName, int indexOfValue);
	
	/** Returns the size of the list */
	int getSizeOfList(String listName);
	
	Object clone();
}
