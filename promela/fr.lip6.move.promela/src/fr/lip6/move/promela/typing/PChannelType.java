package fr.lip6.move.promela.typing;

import java.util.Collections;
import java.util.List;

public class PChannelType implements PromelaType {
	private final List<PromelaType> kinds;
	private final int size;

	public PChannelType(List<PromelaType> kinds, int size) {
		super();
		this.kinds = kinds;
		this.size = size;
	}

	public PChannelType(PromelaType kinds, int size) {
		super();
		this.kinds = Collections.singletonList(kinds);
		this.size = size;
	}

	public List<PromelaType> getKinds() {
		return kinds;
	}

	public int getSize() {
		return size;
	}

}
