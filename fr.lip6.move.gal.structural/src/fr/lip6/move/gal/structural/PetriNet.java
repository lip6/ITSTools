package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.List;

public abstract class PetriNet {

	private String name = "Petri";
	private List<Property> properties = new ArrayList<>();

	public abstract int getPlaceCount();

	public abstract int getTransitionCount();

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public abstract int getTransitionIndex(String name);
	public abstract int getPlaceIndex(String name);
}