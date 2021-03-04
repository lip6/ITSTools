package fr.lip6.move.gal.application;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;

public class MccDonePropertyPrinter extends ConcurrentHashDoneProperties {
	
	
	@Override
	public Boolean put(String prop, Boolean value, String techniques) {
		System.out.println("FORMULA "+prop+(value?" TRUE":" FALSE")+ " TECHNIQUES "+techniques);
		return super.put(prop, value, techniques);
	}

	
	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		System.out.println("FORMULA "+prop+" "+value+ " TECHNIQUES "+techniques);
		return super.put(prop, value, techniques);
	}
}
