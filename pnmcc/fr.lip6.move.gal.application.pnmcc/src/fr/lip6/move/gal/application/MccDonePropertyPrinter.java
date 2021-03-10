package fr.lip6.move.gal.application;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;

public class MccDonePropertyPrinter extends ConcurrentHashDoneProperties {

	String examination;

	public MccDonePropertyPrinter(String examination) {
		super();
		this.examination = examination;
	}

	@Override
	public Boolean put(String prop, Boolean value, String techniques) {
		// System.out.println("FORMULA "+prop+(value?" TRUE":" FALSE")+ " TECHNIQUES
		// "+techniques);
		switch (examination) {
		
		case "StableMarking":
			if (value)
				throw new GlobalPropertySolvedException(examination + " SUCCESS EXCEPTION");
			break;
		case "OneSafe":
			if (!value)
				throw new GlobalPropertySolvedException(examination + " FAILED EXCEPTION");
			break;
		case "QuasiLiveness" :
			if (!value)
				throw new GlobalPropertySolvedException(examination + " FAILED EXCEPTION");
		}

		return super.put(prop, value, techniques);
	}

	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		// System.out.println("FORMULA "+prop+" "+value+ " TECHNIQUES "+techniques);
		return super.put(prop, value, techniques);
	}
}
