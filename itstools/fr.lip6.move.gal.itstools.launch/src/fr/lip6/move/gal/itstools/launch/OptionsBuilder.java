package fr.lip6.move.gal.itstools.launch;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.lip6.move.gal.options.ui.IOption;
import fr.lip6.move.gal.options.ui.IOptionsBuilder;
import fr.lip6.move.gal.options.ui.OptionBoolean;
import fr.lip6.move.gal.options.ui.OptionEnum;
import fr.lip6.move.gal.options.ui.OptionEnumWithText;
import fr.lip6.move.gal.options.ui.OptionSeparator;
import fr.lip6.move.gal.options.ui.OptionText;

public class OptionsBuilder implements IOptionsBuilder {

	public void addAllOptions(List<IOption> options) {
		addAllCommonOptions(options);
		addAllReachOptions(options);
		addAllCTLOptions(options);
		addAllLTLOptions(options);
		addAllOrderOptions(options);
	}

	public static void addAllCommonOptions(List<IOption> options) {

		// addVerbosityOptions(options);

		addMemoryOptions(options);

		addEncodingOptions(options);

		addTrace(options);
	}

	public static void addAllReachOptions(List<IOption> options) {
		addVerbosityOptions(options);

		addSpecialRunOptions(options);
	}

	public static void addAllCTLOptions(List<IOption> options) {
		addCTLVerbosityOptions(options);

		addCTLTranslateOptions(options);
	}

	public static void addAllLTLOptions(List<IOption> options) {
		addLTLVerbosityOptions(options);

		addLTLTranslateOptions(options);
	}

	public static void addAllOrderOptions(List<IOption> options) {
		addLouvainOptions(options);
		
		// TODO
		// addGreatSPNOptions(options);
	}
	
	private static void addLouvainOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("Hierarchy Options",
				"Flags that control whether the system will be decomposed hierarchically.");
		options.add(separator3);
		
		OptionBoolean form = new OptionBoolean("Decompose with Louvain communities",
				"Use Louvain community detection to heuristically decompose model.", false);
		form.setFlag("-louvain");
		options.add(form);
	}

	private static void addLTLVerbosityOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("Verbosity Options",
				"Flags that control traces and output verbosity");
		options.add(separator3);

		OptionBoolean form = new OptionBoolean("Print Formula automaton",
				"Print in dot format the TGBA resulting from formula.", false);
		form.setFlag("-s");
		options.add(form);

		OptionBoolean sys = new OptionBoolean("Print System Description",
				"Print in internal format (e.g. GAL) the model.", false);
		sys.setFlag("-p");
		options.add(sys);

		OptionBoolean prod = new OptionBoolean("Print Product automaton",
				"Print in dot format the Product constructed between the system and the formula automaton.", false);
		prod.setFlag("-g");
		options.add(prod);


		OptionBoolean witness = new OptionBoolean("Display witness traces",
				"If the product is non empty (violation of the formula found), print a witness (expressed as a trace of the product automaton).", false);
		witness.setFlag("-e");
		options.add(witness);
	}

	private static void addLTLTranslateOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("LTL Algorithms",
				"Flags that control which LTL algorithms are used");		
		options.add(separator3);
		
		OptionBoolean stutter = new OptionBoolean("Stutter in deadlock", 
				"Extend finite traces to infinite traces that stutter in final state. By default (flag disabled) LTL only considers infinite traces (i.e. no deadlock states can be observed)", false);
		stutter.setFlag("-stutter-deadlock");
		options.add(stutter);
		
		OptionEnum sogtype = new OptionEnum("Product construction ", "Build one of the following : \n"
				+ "Full LTL :\n"
				+ " * Hybrid Constructions : SLAP, SLAP-FST, SLAP-FSA  ; The FST variant switches to fully symbolic emptiness check in terminal states, and FSA in any potentially accepting automaton state.\n" 
				+ " * Fully Symbolic : FSEL, FSOWCTY, BCZ99 ; The Emerson-Lei or One-Way-Catch-Them-Young SCC hull detection algorithms, BCZ explores in breadth-first manner.\n" 
				+ "Stutter-invariant LTL :\n"
				+ " * Hybrid Constructions : SOP, SOG ; SOP is a dynamic improvement of the Symbolic Observation Graph SOG.\n" 
				//				+ " * Fully Symbolic : FSOWCTY-TGTA ; How do we get the ETF ??.\n" 
				, "SLAP-FST");
		Map<String, String> pvf = new LinkedHashMap<>();
		pvf.put("SLAP-FST", "-SSLAP-FST");
		pvf.put("SLAP", "-SSLAP");
		pvf.put("SLAP-FSA", "-SSLAP-FSA");
		pvf.put("FSEL", "-SFSEL");
		pvf.put("FSOWCTY", "-SFSOWCTY");
		pvf.put("SOP", "-SSOP");
		pvf.put("SOG", "-SSOG");
		//		pvf.put("FSOWCTY-TGTA", "-SFSOWCTY-TGTA");
		sogtype.setPotentialValuesAndFlags(pvf);

		options.add(sogtype);

		OptionEnum atype = new OptionEnum("Emptiness Check ", "For Hybrid approaches, emptiness-check algorithm driving the procedure : \n"
				+ "Please visit Spot homepage http://spot.lrde.epita.fr for more details on these algorithms."
				, "Cou99");

		Map<String, String> pvf2 = new LinkedHashMap<>();
		pvf2.put("Cou99", "-aCou99");
		pvf2.put("CVWY90", "-aCVWY90");
		pvf2.put("GV04", "-aGV04");
		pvf2.put("SE05", "-aSE05");
		pvf2.put("Tau03", "-aTau03");
		pvf2.put("Tau03_opt", "-aTau03_opt");

		//		pvf.put("FSOWCTY-TGTA", "-SFSOWCTY-TGTA");
		atype.setPotentialValuesAndFlags(pvf2);

		options.add(atype);	
	}

	private static void addCTLTranslateOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("Translation Options",
				"Flags that control CTL translation");		
		options.add(separator3);

		OptionBoolean bw = new OptionBoolean("Use backward translation.",
				"Forward CTL is usually faster and more efficient, but it also makes witness traces harder to read.",
				false);
		bw.setFlag("--backward");
		options.add(bw);		

		OptionBoolean fair = new OptionBoolean("Force fair time.",
				"When model-checking discrete time systems, force an alternation between time steps and discrete events.",
				false);
		fair.setFlag("--fair-time");
		options.add(fair);		
	}

	private static void addCTLVerbosityOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("Verbosity Options",
				"Flags that control traces and output verbosity");
		options.add(separator3);

		OptionBoolean quiet = new OptionBoolean("Lower verbosity.",
				"Limit output verbosity (--quiet); if disabled typically prints a lot of traces (e.g. input model is echoed in internal format) and explanations.",
				true);
		quiet.setFlag("--quiet");
		options.add(quiet);

		OptionBoolean witness = new OptionBoolean("Compute witness traces",
				"Enable trace computation instead of just returning a yes/no answer.", false);
		witness.setFlag("--witness");
		options.add(witness);
	}

	public static void addMemoryOptions(List<IOption> options) {
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

	public static void addEncodingOptions(List<IOption> options) {
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

	public static void addVerbosityOptions(List<IOption> options) {
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
				"states");
		path.setPathExtension(".dot");
		path.setFlag("-d");
		options.add(path);

		OptionBoolean no_witness = new OptionBoolean("Do not compute witness traces",
				"disable trace computation and just return a yes/no answer(faster).", false);
		no_witness.setFlag("--nowitness");
		options.add(no_witness);		
	}

	private static void addTrace(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("Witness traces",
				"Options controlling verbosity of witness and counter-example traces.");
		options.add(separator3);

		OptionBoolean trace_states = new OptionBoolean(
				"In any reported trace, also report intermediate states in the trace",
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

	public static void addSpecialRunOptions(List<IOption> options) {
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
				"bmc XXX : use limited depth BFS exploration, only explore up to XXX steps from initial state", "10");
		bmc.setFlag("-bmc");
		options.add(bmc);

		OptionText dump_order_path = new OptionText("dump-order path",
				"dump the currently used variable order to file designated by path and exit", "order");
		dump_order_path.setPathExtension(".txt");
		dump_order_path.setFlag("--dump-order");
		options.add(dump_order_path);
	}

}
