package fr.lip6.move.gal.structural.expr;

public class Param {
	private String name;
	private int sz;
	private String sort;
	public Param(String name, int sz, String sort) {
		this.name = name;
		this.sz = sz;
		this.sort = sort;
	}
	public int size() {
		return sz;
	}
	public String getName() {
		return name;
	}
	public String getSort() {
		return sort;
	}
	@Override
	public String toString() {
		return "$" + name + "(" + sort + ":" + sz + ")";
	}
	@Override
	public int hashCode() {
		final int prime = 5179;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + sz;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Param other = (Param) obj;
		if (sz != other.sz)
			return false;
		return name.equals(other.name);
	}
	
}
