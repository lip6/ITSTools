package fr.lip6.move.gal.application;

import java.util.HashSet;
import java.util.Set;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;

public class GlobalDonePropertyPrinter extends ConcurrentHashDoneProperties {

	private String examination;
	private Set<String> tech = new HashSet<>();

	public GlobalDonePropertyPrinter(String examination) {
		super();
		this.examination = examination;
	}
	
	public String computeTechniques() {
		
		StringBuilder str = new StringBuilder();
		
		for (String t : tech) {
			str.append(t).append(" ");
		}
		
		return str.toString();
		
	}

	@Override
	public Boolean put(String prop, Boolean value, String techniques) {
		// System.out.println("FORMULA "+prop+(value?" TRUE":" FALSE")+ " TECHNIQUES
		// "+techniques);
		
		for (String t : techniques.split(" "))
			tech.add(t);
		
		switch (examination) {

		case "StableMarking":
			if (value) {
				System.out.println("FORMULA " + examination + " TRUE TECHNIQUES " + computeTechniques());
				throw new GlobalPropertySolverException(examination + " SUCCESS");
			}
		case "OneSafe":
			if (!value) {
				System.out.println("FORMULA " + examination + " FALSE TECHNIQUES " + computeTechniques());
				throw new GlobalPropertySolverException(examination + " FAILED");
			}
			break;
		case "QuasiLiveness":
			if (!value) {
				System.out.println("FORMULA " + examination + " FALSE TECHNIQUES " + computeTechniques());
				throw new GlobalPropertySolverException(examination + " FAILED");
			}
		}

		return super.put(prop, value, techniques);
	}

	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		//
		return super.put(prop, value, techniques);
	}
}
