package fr.lip6.move.runtime.environment;

/** A class which help to represent java boolean as java integer  
 */
public class WrapBool {
	private boolean boolwrapped;

	public WrapBool(boolean boolwrapped){
		this.boolwrapped = boolwrapped;
	}
	
	public int evaluate(){
		if(boolwrapped) return 1;
		return 0;
	}
} 
