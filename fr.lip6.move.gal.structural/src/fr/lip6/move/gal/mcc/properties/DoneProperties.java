package fr.lip6.move.gal.mcc.properties;

import java.util.Map.Entry;
import java.util.Set;

public interface DoneProperties {

	int size();

	// A boolean result
	Boolean put(String prop, Boolean value, String techniques);

	// A numeric result
	Boolean put(String prop, Integer value, String techniques);

	Set<Entry<String, Boolean>> entrySet();

	boolean containsKey(Object arg0);

	Set<String> keySet();

	public Boolean getValue(String prop);
}
