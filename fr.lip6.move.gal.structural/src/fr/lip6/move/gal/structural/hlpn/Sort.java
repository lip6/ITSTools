package fr.lip6.move.gal.structural.hlpn;

public class Sort {
	private String name;
	private long size;
	public Sort(String name, long size) {
		this.name = name;
		this.size = size;
	}
	public long size() {
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
