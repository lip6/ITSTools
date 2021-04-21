package fr.lip6.move.gal.application;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;

public class MccDonePropertyPrinter extends ConcurrentHashDoneProperties {
	
	
	@Override
	public Boolean put(String prop, Boolean value, String techniques) {
		Boolean b = super.put(prop, value, techniques);
		if (b == null) {
			System.out.println("FORMULA "+prop+(value?" TRUE":" FALSE")+ " TECHNIQUES "+techniques);			
		} else if (b != value) {
			System.out.println("TestFail conflict detected : techniques "+techniques + " answered differently ("+value+ ")on formula " + prop);
		}
		return b;
	}

	
	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		Boolean b = super.put(prop, value, techniques);
		if (b == null) {
			System.out.println("FORMULA "+prop+" "+value+ " TECHNIQUES "+techniques);
		} 
		return b;
	}
}
