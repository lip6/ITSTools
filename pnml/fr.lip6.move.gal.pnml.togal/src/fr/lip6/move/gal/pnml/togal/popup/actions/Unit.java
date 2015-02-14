package fr.lip6.move.gal.pnml.togal.popup.actions;

import java.util.ArrayList;
import java.util.List;

public class Unit {

	private final String id;
	private List<Unit> subunits = new ArrayList<Unit>();
	private List<String> places = new ArrayList<String>();
	
	public Unit(String id) {
		this.id = id;
	}
	
	public void addUnit(Unit sub) {
		subunits.add(sub);
	}
	public void addPlace(String place) {
		places.add(place);
	}
	
	public String getId() {
		return id;
	}
	
	public List<String> getPlaces() {
		return places;
	}
	
	public List<Unit> getSubunits() {
		return subunits;
	}
	
}
