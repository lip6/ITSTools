package fr.lip6.move.gal.structural;

public class Sort {
	private String name;
	private int size;
	public Sort(String name, int size) {
		this.name = name;
		this.size = size;
	}
	public int size() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name + "(" + size + ")";
	}
	
}
