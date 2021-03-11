package fr.lip6.move.gal.application;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;

public class GlobalDonePropertyPrinter extends ConcurrentHashDoneProperties {

	String examination;

	public GlobalDonePropertyPrinter(String examination) {
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
				throw new GlobalPropertySolverSuccessException(examination + " SUCCESS");
			break;
		case "OneSafe":
			if (!value)
				throw new GlobalPropertySolverFailException(examination + " FAILED");
			break;
		case "QuasiLiveness" :
			if (!value)
				throw new GlobalPropertySolverFailException(examination + " FAILED");
		}

		return super.put(prop, value, techniques);
	}

	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		// System.out.println("FORMULA "+prop+" "+value+ " TECHNIQUES "+techniques);
		return super.put(prop, value, techniques);
	}
}
