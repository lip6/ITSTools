package fr.lip6.move.promela.typing;

public class PArrayType implements PromelaType {
	private final PromelaType kind;
	private final int size;

	public PromelaType getKind() {
		return kind;
	}

	public PArrayType(PromelaType kind, int size) {
		this.kind = kind;
		this.size = size;
	}

	public int getSize() {
		return size;
	}
}
