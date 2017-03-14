package fr.lip6.move.promela.typing;

import fr.lip6.move.promela.promela.BasicType;

public enum PBasicType implements PromelaType {
	
	BIT("bit", 1), 
	BOOL("bool", 1),
	BYTE("byte", 8),
	SHORT("short", 16),
	INT("int", 32);
	//CHECK

	private String name;
	private int size;

	private PBasicType(String typename, int taille) {
		this.name = typename;
		this.size = taille;
	}

	public int getBitSize(){
		return this.size;
	}
	
	public String getName(){
		return this.name;
	}
	// Pour l'instant vérification de tye: vérifie que ce qqu'il y a à gauche a
	// une taille assez rande.....
	
	public static PBasicType get(String name){
		return PBasicType.valueOf(name.toUpperCase());
	}
	public static PBasicType get(BasicType t){
		return PBasicType.valueOf(t.getName().toUpperCase());
	}
	
}
