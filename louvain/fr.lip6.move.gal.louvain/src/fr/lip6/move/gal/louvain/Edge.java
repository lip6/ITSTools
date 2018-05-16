package fr.lip6.move.gal.louvain;

public class Edge {

	private final int src;
	private final int dest;
	private final double weight;
	public Edge(int src, int dest, double d) {
		this.src = src;
		this.dest = dest;
		this.weight = d;
	}
	public int getSrc() {
		return src;
	}
	public int getDest() {
		return dest;
	}
	public double getWeight() {
		return weight;
	}
	
	
}
