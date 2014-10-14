package fr.lip6.move.promela.typing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PUserType implements PromelaType {
	private final Map<String, PromelaType> fields;
	private final String name;
	public PUserType() {
		super();
		this.name="";
		fields = new HashMap<String, PromelaType>();
	}
	public PUserType(Map<String, PromelaType> fields, String name){
		this.fields=fields;
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	public void addField(String name, PromelaType t) {

		if (fields.containsKey(name))
			throw new IllegalArgumentException("UserType: field named'" + name
					+ "' already exists");
		fields.put(name, t);
	}
	
	public Collection<PromelaType> getTypeNames(){
		return (Collection<PromelaType>) this.fields.values();
	}

	public Collection<String> getFieldNames() {
		return fields.keySet();

	}

	public boolean isFieldName(String name) {
		return fields.containsKey(name);
	}

	public PromelaType getFieldType(String name) {
		return fields.get(name);
	}

	// TODO: maybe buildeR? (so that final object?)

}
