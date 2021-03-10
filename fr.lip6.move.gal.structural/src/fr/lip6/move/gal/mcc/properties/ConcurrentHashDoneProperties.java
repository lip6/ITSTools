package fr.lip6.move.gal.mcc.properties;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashDoneProperties implements DoneProperties {
	private Map<String,Boolean> map = new ConcurrentHashMap<>();

	@Override
	public boolean containsKey(Object arg0) {
		return map.containsKey(arg0);
	}

	@Override
	public Set<Entry<String, Boolean>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Boolean put(String prop, Boolean value, String techniques) {
		return map.put(prop, value);
	}
	
	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		return map.put(prop, true);
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public int size() {
		return map.size();
	}


	
}
