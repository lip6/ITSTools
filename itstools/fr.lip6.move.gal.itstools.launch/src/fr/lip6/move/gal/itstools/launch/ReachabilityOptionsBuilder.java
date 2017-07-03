package fr.lip6.move.gal.itstools.launch;

import java.util.HashMap;
import java.util.List;

import fr.lip6.move.gal.itstools.launch.devtools.IOption;
import fr.lip6.move.gal.itstools.launch.devtools.OptionBoolean;
import fr.lip6.move.gal.itstools.launch.devtools.OptionEnum;
import fr.lip6.move.gal.itstools.launch.devtools.OptionEnumWithText;
import fr.lip6.move.gal.itstools.launch.devtools.OptionSeparator;
import fr.lip6.move.gal.itstools.launch.devtools.OptionText;

public abstract class ReachabilityOptionsBuilder {
	
	public static void addAllOptions(List<IOption<?>> options) {

		addVerbosityOptions(options);

		addMemoryOptions(options);

		addSpecialRunOptions(options);

		addEncodingOptions(options);
	}
	
	public static void addMemoryOptions(List<IOption<?>> options) {
		OptionSeparator separator1 = new OptionSeparator("Memory Management",
				"Flags that control memory usage and garbage collection threshold.");
		options.add(separator1);
		
		OptionText gc_threshold = new OptionText("Start GC at resident memory (in GB):",
				"Set the threshold for first trigger of gc (value in GigaBytes). GC is then triggered every time memory exceeds the last peak memory measured, so this threshold grows over long runs.", "1.3");
		gc_threshold.setFlag("--gc-threshold");		
		options.add(gc_threshold);
		
		OptionBoolean no_garbage = new OptionBoolean("No garbage collection at all.",
				"Disable garbage collection (may be faster, more memory), not usually a good idea.", false);
		no_garbage.setFlag("--no-garbage");
		options.add(no_garbage);
	}
	
	public static void addEncodingOptions(List<IOption<?>> options) {
		OptionSeparator separator = new OptionSeparator("State and Transition Relation Encoding",
				"Flags that control symbolic encoding of the system");
		options.add(separator);
		
		OptionEnum fixpoint = new OptionEnum("Saturation fixpoint variant",
				"This option controls how the saturation algorithm is applied : BFS iterates over transitions at each level, while DFS attempts to exploit self-chaining. Both are variants of saturation not really full DFS or BFS",
				"BFS");
		fixpoint.setFlag("--fixpoint");
		HashMap<String, String> fixpoint_map = new HashMap<String, String>();
		fixpoint_map.put("BFS", "BFS");
		fixpoint_map.put("DFS", "DFS");
		fixpoint.setPotentialValuesAndFlags(fixpoint_map);
		options.add(fixpoint);
		
		OptionEnumWithText ssD = new OptionEnumWithText("(Scalar and Circular composite only) Use recursive encoding with block size :",
				" -ssD2 INT : (depth 2 levels) use 2 level depth for scalar sets. Integer provided defines level 2 block size. [DEFAULT: -ssD2 1]\n-ssDR INT : (depth recursive) use recursive encoding for scalar sets. Integer provided defines number of blocks at highest levels.\n-ssDS INT : (depth shallow recursive) use alternative recursive encoding for scalar sets. Integer provided defines number of blocks at lowest level.",
				"D2", "1");
		HashMap<String, String> ssD_map = new HashMap<String, String>();
		ssD_map.put("D2", "-ssD2");
		ssD_map.put("DR", "-ssDR");
		ssD_map.put("DS", "-ssDS");
		ssD.setPotentialValuesAndFlags(ssD_map);		
		options.add(ssD);
		
		OptionEnum sdd_ddd = new OptionEnum("(Petri net models only) Privilege SDD (hierarchy) or flat DDD ?",
				"sdd : each place is an SDD variable.\nddd : each place is a DDD variable",
				"sdd");
		HashMap<String, String> sdd_ddd_map = new HashMap<String, String>();
		sdd_ddd_map.put("sdd", "--sdd");
		sdd_ddd_map.put("ddd", "--ddd");
		sdd_ddd.setPotentialValuesAndFlags(sdd_ddd_map);
		options.add(sdd_ddd);
	}

	public static void addVerbosityOptions(List<IOption<?>> options) {
		OptionSeparator separator3 = new OptionSeparator("Verbosity Options",
				"Flags that control the output of the tool");
		options.add(separator3);

		OptionBoolean quiet = new OptionBoolean("Limit tool verbosity (--quiet).",
				"Limit output verbosity; if disabled typically prints a lot of traces (e.g. input model is echoed in internal format) and explanations.",
				true);
		quiet.setFlag("--quiet");
		options.add(quiet);

		OptionText path = new OptionText("Export state space to a .dot file.",
				"Exports the final state space SDD/DDD representation as GraphViz dot files. Specify the path prefix to construct dot state-space graph in. Two dot files, one with DDD and one with SDD graphical representations are built. Avoid if more than 10k nodes.",
				null);
		path.setPathExtension(".dot");
		path.setFlag("-d");
		options.add(path);

		OptionBoolean no_witness = new OptionBoolean("Do not compute witness traces",
				"disable trace computation and just return a yes/no answer(faster).", false);
		no_witness.setFlag("--nowitness");
		options.add(no_witness);

		OptionBoolean trace_states = new OptionBoolean(
				"In any reported trace, also report intermediate states in the trace ?",
				"if set, this option will force to print intermediate states (up to print limit) when showing traces. Default behavior is to only print a trace as a list of transition names.",
				true);
		trace_states.setFlag("--trace-states");
		options.add(trace_states);

		OptionText print_limit = new OptionText("Set the maximal size of state sets reported in the trace",
				"State sets with less than this limit will be printed in extenso. DD holding more states will just print their size.",
				"10");
		print_limit.setFlag("--print-limit");
		options.add(print_limit);

	}
	
	
	public static void addSpecialRunOptions(List<IOption<?>> options) {
		OptionSeparator separator3 = new OptionSeparator("Special execution options",
				"Flags that control what is computed");
		options.add(separator3);

		OptionBoolean stats = new OptionBoolean("Show statistics on final state space.",
				"Produces stats on max sum of variables (i.e. maximum tokens in a marking for a Petri net), maximum variable value (bound for a Petri net)",
				false);
		stats.setFlag("--stats");
		options.add(stats);

		OptionBoolean edgeStat = new OptionBoolean("Show edge count statistics",
				"Reports the size of the transition relation, i.e the number of unique source to target state pairs it contains.",
				false);
		edgeStat.setFlag("--edgeCount");
		options.add(edgeStat);		

		
		OptionText bmc = new OptionText("bmc",
				"bmc XXX : use limited depth BFS exploration, only explore up to XXX steps from initial state", null);
		bmc.setFlag("-bmc");
		options.add(bmc);

		OptionText dump_order_path = new OptionText("dump-order path",
				"dump the currently used variable order to file designated by path and exit", null);
		dump_order_path.setPathExtension(".txt");
		dump_order_path.setFlag("--dump-order");
		options.add(dump_order_path);
	}
}
