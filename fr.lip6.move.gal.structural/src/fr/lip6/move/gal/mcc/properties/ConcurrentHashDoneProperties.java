package fr.lip6.move.gal.mcc.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashDoneProperties implements DoneProperties {
	private static final int DEBUG = 0;
	private Map<String, Boolean> map = new ConcurrentHashMap<>();
	// Read by put on every solver thread while addAlias may still be writing.
	// The lists are replaced rather than mutated, so a reader never observes one
	// half built.
	protected Map<String, List<String>> alias = new ConcurrentHashMap<>();

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
		if (DEBUG >= 1) {
			System.out.println(prop + "=" + value + "; " + techniques);
		}
		Boolean b = map.put(prop, value);
		if (b == null) {
			for (String aka : alias.getOrDefault(prop, Collections.emptyList())) {
				put(aka, value, techniques);
			}
		}
		return b;

	}

	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		Boolean b = map.put(prop, true);
		if (b == null) {
			for (String aka : alias.getOrDefault(prop, Collections.emptyList())) {
				put(aka, value, techniques);
			}
		}
		return b;
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Boolean getValue(String prop) {
		return map.get(prop);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void addAlias(String propToCheck, String aka) {
		alias.compute(propToCheck, (k, v) -> {
			List<String> next = (v == null) ? new ArrayList<>() : new ArrayList<>(v);
			next.add(aka);
			return next;
		});
	}
}
