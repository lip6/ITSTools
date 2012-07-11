package fr.lip6.move.runtime.environment;

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
