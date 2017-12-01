package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeterministicNextBuilder extends NextBuilderDecorator implements IDeterministicNextBuilder {

	public DeterministicNextBuilder(INextBuilder deco) {
		super(deco);
	}

	private List<List<INext>> deterministic = null;
	private DependencyMatrix dm = null;

	public List<List<INext>> getDeterministicNext() {
		if (deterministic == null) {
			List<INext> nextRel = getNextForLabel("");
			INext allTrans = Alternative.alt(nextRel);

			List<INext> bootstrap = new ArrayList<>();
			Determinizer det = new Determinizer(Collections.singleton(bootstrap).stream());
			Stream<List<INext>> nextStream = allTrans.accept(det);
			deterministic = nextStream.collect(Collectors.toList());
		}
		return deterministic;
	}

	public DependencyMatrix getDeterministicDependencyMatrix() {
		if (dm == null) {
			dm = new DependencyMatrix(getDeterministicNext());
		}
		return dm;
	}

}