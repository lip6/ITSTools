package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeterministicNextBuilder extends NextBuilderDecorator implements IDeterministicNextBuilder {

	public DeterministicNextBuilder(INextBuilder deco) {
		super(deco);
	}

	private List<List<INext>> deterministic = null;
	private DependencyMatrix dm = null;

	public synchronized List<List<INext>> getDeterministicNext() {
		if (deterministic == null) {
			List<INext> nextRel = getNextForLabel("");
			int total = 0;
			int[] nondet = new int[nextRel.size()];
			NextVisitor<Integer> counter = new NextVisitor<Integer>() {

				@Override
				public Integer visit(Assign ass) {
					return 1;
				}

				@Override
				public Integer visit(Predicate pred) {
					return 1;
				}

				@Override
				public Integer visit(Alternative alt) {
					int total = 1;
					for (INext act : alt.getAlternatives()) {
						total += act.accept(this);
					}
					return total;
				}

				@Override
				public Integer visit(Sequence seq) {
					int total = 1;
					for (INext act : seq.getActions()) {
						total *= act.accept(this);
					}
					return total;
				}
			};
			int index =0;
			for (INext  n : nextRel) {
				int val = n.accept(counter);
				nondet[index++]=val;
				total += val;
			}
			if (total == nextRel.size()) {
				deterministic = new ArrayList<>();
				
				for (INext n : nextRel) {
					final List<INext> list = new ArrayList<>();
					NextVisitor<Void> normalizer = new NextVisitor<Void>() {

						@Override
						public Void visit(Assign ass) {
							list.add(ass);
							return null;
						}

						@Override
						public Void visit(Predicate pred) {
							list.add(pred);
							return null;
						}

						@Override
						public Void visit(Alternative alt) {
							throw new IllegalArgumentException();
						}

						@Override
						public Void visit(Sequence seq) {
							for (INext n : seq.getActions()) {
								n.accept(this);
							}
							return null;
						}
						
					};
					n.accept(normalizer);
					deterministic.add(list);
				}
				Logger.getLogger("fr.lip6.move.gal").info("Input system was already deterministic with "+deterministic.size()+" transitions.");
			} else {
				Logger.getLogger("fr.lip6.move.gal").info("Input system was not deterministic with "+nextRel.size()+" transitions. Expanding to a total of " + total + " deterministic transitions.");
				long time = System.currentTimeMillis();
				INext allTrans = Alternative.alt(nextRel);
				deterministic = allTrans.accept(new DeterminizerList());
				Logger.getLogger("fr.lip6.move.gal").info("Determinization took " + (System.currentTimeMillis() - time) + " ms."+ ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) / 1000) + " KB memory used");
			}
		}
		return deterministic;
	}

	public synchronized DependencyMatrix getDeterministicDependencyMatrix() {
		if (dm == null) {
			dm = new DependencyMatrix(getDeterministicNext(),size());
		}
		return dm;
	}

}