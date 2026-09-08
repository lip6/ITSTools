package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.Ender;
import fr.lip6.move.gal.application.runner.IRunner;
import fr.lip6.move.gal.application.runner.its.ITSRunner;
import fr.lip6.move.gal.application.runner.its.MultiOrderRunner;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.semantics.DependencyMatrix;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.semantics.NextSupportAnalyzer;

/**
 * The Louvain decomposition alone : what the graph is built from, what it costs, and what the
 * symbolic engine makes of the hierarchy it yields.
 *
 * The decomposition is out of reach of an ordinary run on a model small enough to iterate on:
 * the walker and the SMT solving answer every property before it is reached, and StateSpace
 * carries no property, hence no comparison to honour. This runs it on its own, right after the
 * model and its properties are read, and hands the result to ITS-Tools when asked -- so the
 * heuristic is measured through the contest harness, on the corpus, like any other run.
 */
public class LouvainBench {

	/**
	 * @param reduce applies the structural reductions before decomposing: the heuristic meets
	 *        agglomerated nets in a real run, not the net as the contest writes it.
	 * @param doITS runs the symbolic engine on the hierarchy, so the run answers properties.
	 */
	public static void run(MccTranslator reader, DoneProperties doneProps, String examination, boolean reduce,
			boolean doITS, long timeout, AtomicBoolean wasKilled, List<IRunner> runners, Ender ender)
			throws IOException, InterruptedException {
		reader.createSPN(reduce, reduce);
		reader.rebuildSpecification(doneProps);

		INextBuilder inb = INextBuilder.build(reader.getSpec());
		reportTransitions(inb);
		reportConstraints(inb, reader);

		reader.setLouvain(true);
		reader.setOrder(null);
		long time = System.currentTimeMillis();
		reader.flattenSpec(true);
		int types = reader.getSpec().getTypes().size();
		System.out.println("Louvain bench : decomposition took " + (System.currentTimeMillis() - time) + " ms, "
				+ (types > 1 ? types + " GAL types" : "one flat GAL type"));

		if (doITS) {
			IRunner its = new ITSRunner(examination, reader, true, false, reader.getFolder(), timeout, null);
			MultiOrderRunner.startRunner(true, reader, doneProps, wasKilled, runners, ender, its);
		}
	}

	/** The hyper edges the net contributes : a transition relates its control places to the ones it writes. */
	private static void reportTransitions(INextBuilder inb) {
		DependencyMatrix dm = new DependencyMatrix(inb.size(), inb.getNextForLabel(""));
		int[] ctrl = new int[dm.nbCols()];
		int[] prod = new int[dm.nbCols()];
		long edges = 0;
		int wideCtrl = 0;
		int wideProd = 0;
		for (int t = 0; t < dm.nbCols(); t++) {
			BitSet bsctrl = dm.getControl(t);
			BitSet bswrite = (BitSet) dm.getWrite(t).clone();
			bswrite.andNot(bsctrl);
			ctrl[t] = bsctrl.cardinality();
			prod[t] = ctrl[t] * bswrite.cardinality();
			edges += prod[t];
			if (ctrl[t] > 8) {
				wideCtrl++;
			}
			if (prod[t] > 64) {
				wideProd++;
			}
		}
		java.util.Arrays.sort(ctrl);
		java.util.Arrays.sort(prod);
		System.out.println("Louvain bench : " + dm.nbRows() + " variables, " + dm.nbCols() + " transitions inducing "
				+ edges + " edges; control places " + median(ctrl) + " median " + max(ctrl) + " max, "
				+ wideCtrl + " above 8; induced edges per transition " + median(prod) + " median " + max(prod)
				+ " max, " + wideProd + " above 64");
	}

	/** The hyper edges the properties contribute : a comparison must not be split across the hierarchy. */
	private static void reportConstraints(INextBuilder inb, MccTranslator reader) {
		List<Integer> sizes = new ArrayList<>();
		long induced = 0;
		for (Property p : reader.getSpec().getProperties()) {
			for (TreeIterator<EObject> it = p.eAllContents(); it.hasNext();) {
				EObject obj = it.next();
				if (obj instanceof Comparison) {
					BitSet tmp = new BitSet();
					NextSupportAnalyzer.computeQualifiedSupport((Comparison) obj, tmp, inb);
					if (tmp.cardinality() > 1) {
						sizes.add(tmp.cardinality());
						induced += ((long) tmp.cardinality()) * (tmp.cardinality() - 1);
					}
				}
			}
		}
		int[] all = sizes.stream().mapToInt(Integer::intValue).sorted().toArray();
		System.out.println("Louvain bench : " + all.length + " constraints inducing " + induced + " edges; support "
				+ median(all) + " median " + max(all) + " max");
	}

	private static int median(int[] sorted) {
		return sorted.length == 0 ? 0 : sorted[sorted.length / 2];
	}

	private static int max(int[] sorted) {
		return sorted.length == 0 ? 0 : sorted[sorted.length - 1];
	}
}
