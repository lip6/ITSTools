package fr.lip6.move.gal.louvain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import android.util.SparseIntArray;
import fr.lip6.move.gal.louvain.LouvainTools.Tool;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.IOrderVisitor;
import fr.lip6.move.gal.order.OrderFactory;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.semantics.DependencyMatrix;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.util.IntMatrixCol;

public class GraphBuilder {

	/** 2 keeps the graph, binary, weights and tree files of a run and traces the tool calls. */
	private static final int DEBUG = 0;

	/**
	 * A transition holding more control places than this is a synchronisation rather than the
	 * progression of a process: it relates places of components that only meet there, and the
	 * edges it induces are quadratic in its support. It is left out of the graph.
	 */
	private static final int MAX_CONTROL = 8;

	/** The edges a single transition may induce, whichever strategy builds them. */
	private static final int MAX_INDUCED = 64;

	/**
	 * A comparison over more variables than this is a global statement, a sum over a whole
	 * component or the net : keeping its variables in one place would forbid any cut, so it is
	 * dropped from the graph and the partition may split it.
	 */
	private static final int MAX_CONSTRAINT = 20;

	/**
	 * Comparisons that share variables are held together as one, and a group so grown may not
	 * hold more than this share of the net : contracting it would leave nothing to cut, so it
	 * is dissolved and its comparisons are left to the partition, like an oversized one.
	 */
	private static final double MAX_GROUP_RATIO = 0.5;

	/**
	 * The edges of the graph, one "src dest weight" line each, written as they are produced:
	 * a hyper edge of arity k induces k*(k-1)/2 of them, so a net of a few thousand places
	 * reaches tens of millions and holding them costs more than the clustering.
	 */
	private static class EdgeSink implements AutoCloseable {
		private final PrintWriter pw;
		private int nbEdges = 0;

		EdgeSink(String path) throws FileNotFoundException {
			pw = new PrintWriter(path);
		}

		void add(int src, int dest, double weight) {
			pw.println(src + " " + dest + " " + ((float) weight));
			nbEdges++;
		}

		int size() {
			return nbEdges;
		}

		@Override
		public void close() {
			pw.close();
		}
	}

	/**
	 * The graph handed to louvain : a node per variable, or one node for a group of variables a
	 * property compares, since libits cannot evaluate a comparison across a hierarchy border.
	 * Contracting the group enforces that, where relating its variables pairwise only asked for
	 * it, at a cost quadratic in the group.
	 */
	private static void writeGraph (String path, DependencyMatrix dm, boolean allToAll, int[] nodeOf, int nbNodes) throws FileNotFoundException {
		int nbEdges;
		try (EdgeSink g = new EdgeSink(path)) {
			if (allToAll) {
				// basic strategy for hyper graph to graph : the support of a transition, related pairwise
				for (int tindex = 0; tindex < dm.nbCols() ; tindex++) {
					BitSet bs = (BitSet) dm.getControl(tindex).clone();
					bs.or(dm.getRead(tindex));
					bs.or(dm.getWrite(tindex));

					int nbTouched = bs.cardinality();
					int nbElts = nbTouched * (nbTouched -1) / 2;
					if (nbElts == 0 || nbElts > MAX_INDUCED) {
						continue;
					}
					for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
						for (int j=bs.nextSetBit(i+1) ; j >= 0 ; j = bs.nextSetBit(j+1)) {
							// weight is one over the number of induced arcs
							if (nodeOf[i] != nodeOf[j]) {
								g.add(nodeOf[i], nodeOf[j], 1.0/nbElts);
							}
						}
					}
				}
			} else {
				// flow like strategy : an edge from every control variable to every written variable
				for (int n = 0; n < nbNodes ; n++) {
					g.add(n, n, 0.001);
				}
				for (int tindex = 0; tindex < dm.nbCols() ; tindex++) {
					BitSet bsctrl = dm.getControl(tindex);
					BitSet bswrite = (BitSet) dm.getWrite(tindex).clone();
					// drops some constraints
					bswrite.andNot(bsctrl);

					int nbElts = bsctrl.cardinality() * bswrite.cardinality();
					if (nbElts == 0 || nbElts > MAX_INDUCED || bsctrl.cardinality() > MAX_CONTROL) {
						continue;
					}
					for (int i = bsctrl.nextSetBit(0); i >= 0; i = bsctrl.nextSetBit(i+1)) {
						for (int j=bswrite.nextSetBit(0) ; j >= 0 ; j = bswrite.nextSetBit(j+1)) {
							// weight is one over the number of induced arcs
							if (nodeOf[i] != nodeOf[j]) {
								g.add(nodeOf[i], nodeOf[j], 1.0/nbElts);
							}
						}
					}
				}
			}
			nbEdges = g.size();
		}
		if (nbEdges == 0 && !allToAll) {
			// the flow strategy relates nothing, fall back on the full support of transitions
			writeGraph(path, dm, true, nodeOf, nbNodes);
		}
	}

	/**
	 * The node each variable belongs to : variables a property compares share one, the others
	 * stand alone.
	 */
	private static int[] contract(int nbVars, List<BitSet> constraints) {
		List<BitSet> groups = new ArrayList<>();
		int dropped = 0;
		for (BitSet c : constraints) {
			if (c.cardinality() > MAX_CONSTRAINT) {
				dropped++;
				continue;
			}
			BitSet merged = (BitSet) c.clone();
			// a group that meets the new one joins it, which may in turn bring in a group already passed
			for (boolean fused = true ; fused ; ) {
				fused = false;
				for (Iterator<BitSet> it = groups.iterator(); it.hasNext(); ) {
					BitSet g = it.next();
					if (g.intersects(merged)) {
						merged.or(g);
						it.remove();
						fused = true;
					}
				}
			}
			groups.add(merged);
		}
		int dissolved = 0;
		for (Iterator<BitSet> it = groups.iterator(); it.hasNext(); ) {
			if (it.next().cardinality() > MAX_GROUP_RATIO * nbVars) {
				it.remove();
				dissolved++;
			}
		}
		if (dropped > 0 || dissolved > 0) {
			System.out.println("Decomposition : of " + constraints.size() + " comparisons, " + dropped
					+ " relate more than " + MAX_CONSTRAINT + " variables and " + dissolved
					+ " groups of them more than half the net : left to the partition. "
					+ groups.size() + " groups are held together.");
		}
		int[] nodeOf = new int[nbVars];
		Arrays.fill(nodeOf, -1);
		int next = 0;
		for (BitSet g : groups) {
			boolean used = false;
			for (int v = g.nextSetBit(0); v >= 0 && v < nbVars; v = g.nextSetBit(v+1)) {
				nodeOf[v] = next;
				used = true;
			}
			if (used) {
				next++;
			}
		}
		for (int v = 0; v < nbVars; v++) {
			if (nodeOf[v] < 0) {
				nodeOf[v] = next++;
			}
		}
		return nodeOf;
	}

	/** The variables each node stands for, in the order louvain numbers them. */
	private static List<List<String>> nodeNames(List<String> varNames, int[] nodeOf, int nbNodes) {
		List<List<String>> names = new ArrayList<>(nbNodes);
		for (int n = 0; n < nbNodes; n++) {
			names.add(new ArrayList<>());
		}
		for (int v = 0; v < nodeOf.length; v++) {
			names.get(nodeOf[v]).add(varNames.get(v));
		}
		return names;
	}

	public static void writeGraph (String path, StructuralReduction sr) throws FileNotFoundException {
		IntMatrixCol flowPT = sr.getFlowPT();
		IntMatrixCol flowTP = sr.getFlowTP();
		try (EdgeSink g = new EdgeSink(path)) {
			for (int tindex =0; tindex < sr.getTnames().size() ; tindex++) {
				SparseIntArray flow = SparseIntArray.sumProd(1, flowPT.getColumn(tindex), 1, flowTP.getColumn(tindex));
				int nbTouched = flow.size();
				int nbElts = nbTouched * (nbTouched -1) * 2;
				if (nbElts != 0) {
					for (int i = 0 ; i < nbTouched ; i++) {
						for (int j=i+1 ; j < nbTouched ; j++) {
							g.add(flow.keyAt(i), flow.keyAt(j), 1.0/nbElts);
						}
					}
				}
			}
		}
	}

	public static IOrder computeLouvain(StructuralReduction sr, boolean rec) throws IOException, TimeoutException, InterruptedException {
		File ff = File.createTempFile("graph", ".txt");
		writeGraph(ff.getCanonicalPath(), sr);

		int[] nodeOf = new int[sr.getPnames().size()];
		for (int v = 0; v < nodeOf.length; v++) {
			nodeOf[v] = v;
		}
		return computeLouvain(ff, nodeNames(sr.getPnames(), nodeOf, nodeOf.length), rec);
	}

	public static IOrder computeLouvain(INextBuilder inb, boolean rec, List<BitSet> constraints) throws IOException, TimeoutException, InterruptedException {
		DependencyMatrix dm = new DependencyMatrix(inb.size(), inb.getNextForLabel(""));
		List<String> varNames = inb.getVariableNames();
		int[] nodeOf = contract(dm.nbRows(), constraints);
		int nbNodes = 0;
		for (int n : nodeOf) {
			nbNodes = Math.max(nbNodes, n + 1);
		}
		File ff = File.createTempFile("graph", ".txt");
		writeGraph(ff.getCanonicalPath(), dm, false, nodeOf, nbNodes);

		IOrder ord = computeLouvain(ff, nodeNames(varNames, nodeOf, nbNodes), rec);

		ord = ord.accept(new IOrderVisitor<IOrder>() {

			@Override
			public IOrder visitComposite(CompositeGalOrder o) {
				if (o.getChildren().size() == 1) {
					return o.getChildren().get(0);
				} else {
					List<IOrder> list = new ArrayList<>();
					for (IOrder child : o.getChildren()) {
						list.add(child.accept(this));
					}
					return new CompositeGalOrder(list , o.getName());
				}
			}

			@Override
			public IOrder visitVars(VarOrder varOrder) {
				return varOrder;
			}
		});

		return ord;
	}


	private static IOrder computeLouvain(File graphff, List<List<String>> nodeNames, boolean rec)
			throws IOException, TimeoutException, InterruptedException {
		String fbin = graphff.getCanonicalPath().replace(".txt", ".bin");
		String fw = graphff.getCanonicalPath().replace(".txt", ".weights");
		String ftree = graphff.getCanonicalPath().replace(".txt", ".tree");

		try {
			convertGraphToBin(graphff,  fbin, fw);

			runLouvain(graphff,  fbin, fw);

			IOrder ord = OrderFactory.parseLouvainNodes(ftree, nodeNames, rec);
			return ord;
		} finally {
			if (DEBUG >= 2) {
				System.out.println("Louvain files of this run : " + graphff.getCanonicalPath() + " " + fbin + " " + fw + " " + ftree);
			} else {
				deleteFiles(graphff.getCanonicalPath(), fbin, fw, ftree);
			}
		}
	}

	/** The files a run leaves behind reach hundreds of megabytes, and a run killed by its budget leaves them all. */
	private static void deleteFiles(String... paths) {
		for (String path : paths) {
			new File(path).delete();
		}
	}

	private static String runLouvain(File ff, String fbin, String fw)
			throws IOException, TimeoutException, InterruptedException {
		CommandLine cl = new CommandLine();
		cl.addArg(LouvainTools.getProgramURI(Tool.louvain).getPath().toString());
		cl.addArg(fbin);
		
		// -l k    displays the graph of level k rather than the hierachical structure
        // if k=-1 then displays the hierarchical structure rather than the graph at a given level
		cl.addArg("-l");
		cl.addArg("-1");
		
		// -v      verbose mode: gives computation time, information about the hierarchy and quality
		cl.addArg("-v");
		
		// -w file read the graph as a weighted one (weights are set to 1 otherwise)
		cl.addArg("-w");
		cl.addArg(fw);
		
//		-q id   the quality function used to compute partition of the graph (modularity is chosen by default):
//
//	        id = 0   -> the classical Newman-Girvan criterion (also called "Modularity")
//	        id = 1   -> the Zahn-Condorcet criterion
//	        id = 2   -> the Owsinski-Zadrozny criterion (you should specify the value of the parameter with option -c)
//	        id = 3   -> the Goldberg Density criterion
//	        id = 4   -> the A-weighted Condorcet criterion
//	        id = 5   -> the Deviation to Indetermination criterion
//	        id = 6   -> the Deviation to Uniformity criterion
//	        id = 7   -> the Profile Difference criterion
//	        id = 8   -> the Shi-Malik criterion (you should specify the value of kappa_min with option -k)
//	        id = 9   -> the Balanced Modularity criterion
		cl.addArg("-q");
		cl.addArg("0");
		
//		To ensure a faster computation (with a loss of quality), one can use
//		the -e option to specify that the program must stop if the increase of
//		modularity is below epsilon for a given iteration or pass:
		cl.addArg("-e");
		cl.addArg("0.001");
		
		
		String ftree = ff.getCanonicalPath().replace(".txt", ".tree");
		Runner.runTool(10, cl, new File(ftree), false);

		if (DEBUG >= 2) {
			System.out.println("Built communities with : " + cl);
		}
		return ftree;
	}

	private static void convertGraphToBin(File ff,  String fbin, String fw)
			throws IOException, TimeoutException, InterruptedException {
		CommandLine clConvert = new CommandLine();
		clConvert.addArg(LouvainTools.getProgramURI(Tool.convert).getPath().toString());
		clConvert.addArg("-i");
		clConvert.addArg(ff.getCanonicalPath());
		
		clConvert.addArg("-o");
		
		clConvert.addArg(fbin);
		
		clConvert.addArg("-w");
		clConvert.addArg(fw);
		
		Runner.runTool(10, clConvert );
		if (DEBUG >= 2) {
			System.out.println("Converted graph to binary with : " + clConvert);
		}
	}
}
