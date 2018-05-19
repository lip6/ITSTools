package fr.lip6.move.gal.ltsmin.launch;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.lip6.move.gal.itstools.launch.devtools.IOption;
import fr.lip6.move.gal.itstools.launch.devtools.OptionBoolean;
import fr.lip6.move.gal.itstools.launch.devtools.OptionEnum;
import fr.lip6.move.gal.itstools.launch.devtools.OptionEnumWithText;
import fr.lip6.move.gal.itstools.launch.devtools.OptionSeparator;
import fr.lip6.move.gal.itstools.launch.devtools.OptionText;


public abstract class OptionsBuilder {

	public static void addAllOptions(List<IOption> options) {
		addAllGeneralOptions(options);
		addAllSeqOptions(options);
		addAllMcOptions(options);
		addAllSymOptions(options);
	}

	public static void addAllGeneralOptions(List<IOption> options) {
		
		addFirstOptions(options);
		
		addReachOption(options);
		
		addPinsOption(options);

	}

	public static void addAllSeqOptions(List<IOption> options) {
		addVerbositySeqOptions(options);
	}
	
	public static void addAllMcOptions(List<IOption> options) {
		addVerbosityMcOptions(options);
	}
	
	public static void addAllSymOptions(List<IOption> options) {
		addVerbositySymOptions(options);
	}

	public static void addReachOption(List<IOption> options) {
		OptionSeparator separator1 = new OptionSeparator("Reach option",
				"Flags that can be used.");
		options.add(separator1);
		
		OptionBoolean noex = new OptionBoolean("Do not exit.",
				" Do not exit when an error is found. Just count errors. Error counts are\n" + 
				"printed with +-v+.", false);
		noex.setFlag("-n");
		options.add(noex);
		
		OptionBoolean deadlock = new OptionBoolean("Increase the level of verbosity.",
				"Find state with no outgoing transitions. Returns with exit code 1 if\n" + 
				"a deadlock is found, 0 or 255 (error) otherwise.", false);
		deadlock.setFlag("-d");
		options.add(deadlock);
		
		OptionBoolean invar = new OptionBoolean("Invariant check.",
				"Find state where the invariant is violated. The file 'PREDFILE'\n" + 
				"contains an expression in a simple predicate language (see\n" + 
				"manpage:ltsmin-pred[5]). Its contents can also be entered directly as\n" + 
				"a 'PREDEXPRESSION'. Returns with exit code 1 if\n" + 
				"a violation is found, 0 or 255 (error) otherwise.", false);
		invar.setFlag("-i");
		options.add(invar);
		
		OptionBoolean action = new OptionBoolean("Find a state.",
				"Find state with an outgoing transition of type 'STRING'. Returns with exit\n" + 
				"code 1 if the action is found, 0 or 255 (error) otherwise.", false);
		action.setFlag("-a");
		options.add(action);
		
		
	}
	
	public static void addPinsOption(List<IOption> options) {
		OptionSeparator separator1 = new OptionSeparator("Pins option",
				"Flags that can be used.");
		options.add(separator1);
		
		OptionBoolean label = new OptionBoolean("Print help text",
				"For mor verbosity", false);
		label.setFlag("--help");
		options.add(label);
		
		OptionBoolean matrix = new OptionBoolean("Print help text",
				"For mor verbosity", false);
		matrix.setFlag("--help");
		options.add(matrix);
		
		OptionBoolean cach = new OptionBoolean("Print help text",
				"For mor verbosity", false);
		cach.setFlag("--help");
		options.add(cach);
		
		
		
		OptionEnum partOpt = new OptionEnum("Partial Order Option",
				"Activate partial-order reduction.",
				"HEUR");
		partOpt.setFlag("--por");
		HashMap<String, String> state_point = new HashMap<String, String>();
		state_point.put("HEUR", "heur");
		state_point.put("DEL", "del");
		partOpt.setPotentialValuesAndFlags(state_point);
		options.add(partOpt);
		
		OptionBoolean weak = new OptionBoolean("Use weak commutativity",
				"Use weak commutativity in partial-order reduction. Possibly yielding better reductions.",
				false);
		weak.setFlag("--weak");
		options.add(weak);
		
		OptionBoolean leap = new OptionBoolean("Use leaping partial-order reduction",
				"Use leaping partial-order reduction, by combining several disjoint stubborn sets sequentially.",
				false);
		leap.setFlag("--leap");
		options.add(leap);
	}

	public static void addFirstOptions(List<IOption> options) {
		OptionSeparator separator1 = new OptionSeparator("General option",
				"Flags that can be used.");
		options.add(separator1);

		OptionBoolean verbos = new OptionBoolean("Increase the level of verbosity.",
				"For mor verbosity", false);
		verbos.setFlag("-v");
		options.add(verbos);
		
		OptionBoolean version = new OptionBoolean("Print version string of this tool.",
				"Version", false);
		version.setFlag("--version");
		options.add(version);
		
		OptionBoolean quiet = new OptionBoolean("Be quiet; do not print anything to the terminal.",
				"For mor verbosity", false);
		quiet.setFlag("-q");
		options.add(quiet);
		
		OptionBoolean usage = new OptionBoolean("Print short usage summary.",
				"For mor verbosity", false);
		usage.setFlag("--usage");
		options.add(usage);
		
		OptionBoolean help = new OptionBoolean("Print help text",
				"For mor verbosity", false);
		help.setFlag("--help");
		options.add(help);
		
		
	}

	public static void addVerbositySeqOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("pins2lts-seq Options",
				"Flags that control the output of the SEQ variant tool");
		options.add(separator3);
		
		OptionEnum strategyOpt = new OptionEnum("Strategy Options",
				"Select exploration strategy",
				"BFS");
		strategyOpt.setFlag("--strategy");
		HashMap<String, String> fixpoint_map = new HashMap<String, String>();
		fixpoint_map.put("BFS", "bfs");
		fixpoint_map.put("DFS", "dfs");
		fixpoint_map.put("SCC", "scc");
		strategyOpt.setPotentialValuesAndFlags(fixpoint_map);
		options.add(strategyOpt);
		
		OptionEnum stateOpt = new OptionEnum("State Option",
				"Select type of data structure for storing visited states.",
				"TREE");
		stateOpt.setFlag("--state");
		HashMap<String, String> state_point = new HashMap<String, String>();
		state_point.put("TREE", "tree");
		state_point.put("TABLE", "table");
		state_point.put("VSET", "vset");
		stateOpt.setPotentialValuesAndFlags(state_point);
		options.add(stateOpt);

		OptionEnum provOpt = new OptionEnum("Change the proviso implementation for partial order reduction (ltl)",
				"Change the proviso used to detect that an accepting cycle is closed.",
				"CLOSEDSET");
		provOpt.setFlag("--state");
		HashMap<String, String> prov_map = new HashMap<String, String>();
		prov_map.put("CLOSEDSET", "closedset");
		prov_map.put("STACK", "stack");
		prov_map.put("COLOR", "color");
		provOpt.setPotentialValuesAndFlags(prov_map);
		options.add(provOpt);
				
	}

	public static void addVerbosityMcOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("pins2lts-mc Options",
				"Flags that control the output of the MC variant tool");
		options.add(separator3);
		
		OptionEnum strategyOpt = new OptionEnum("Strategy Options",
				"This option controls how the saturation algorithm is applied : BFS iterates over transitions at each level, while DFS attempts to exploit self-chaining. Both are variants of saturation not really full DFS or BFS",
				"BFS");
		strategyOpt.setFlag("--strategy");
		HashMap<String, String> fixpoint_map = new HashMap<String, String>();
		fixpoint_map.put("BFS", "bfs");
		fixpoint_map.put("sBFS", "sbfs");
		fixpoint_map.put("DFS", "dfs");
		fixpoint_map.put("nDFS", "ndfs");
		fixpoint_map.put("nnDFS", "nndfs");
		fixpoint_map.put("lDFS", "ldfs");
		fixpoint_map.put("enDFS", "endfs");
		fixpoint_map.put("DFSfifo", "dfsfifo");
		fixpoint_map.put("UFSCC", "ufscc");
		fixpoint_map.put("TARJAN", "tarjan");
		fixpoint_map.put("RENAULT", "renault");
		strategyOpt.setPotentialValuesAndFlags(fixpoint_map);
		options.add(strategyOpt);
		
		OptionEnum permOpt = new OptionEnum("Permutation Options",
				"Select the transition permutation, which is used to guide\n" + 
				"different threads to different parts of the state space.\n" + 
				"A good permutation can significantly speed up bug hunting.\n" + 
				"each type has different properties in terms of performance and effectiveness " + 
				"summarized as (perf./eff.)",
				"NONE");
		permOpt.setFlag("--perm");
		HashMap<String, String> perm_map = new HashMap<String, String>();
		perm_map.put("DYNAMIC", "dynamic");
		perm_map.put("SORT", "sort");
		perm_map.put("RANDOM", "random");
		perm_map.put("RR", "rr");
		perm_map.put("SHIFT", "shift");
		perm_map.put("OTF", "otf");
		perm_map.put("NONE", "none");
		permOpt.setPotentialValuesAndFlags(perm_map);
		options.add(permOpt);
		
		OptionEnum stateOpt = new OptionEnum("State option",
				"Select type of data structure for storing visited states.",
				"TREE");
		stateOpt.setFlag("--state");
		HashMap<String, String> state_point = new HashMap<String, String>();
		state_point.put("TREE", "tree");
		state_point.put("TABLE", "table");
		state_point.put("CLEARY-TREE", "cleary-tree");
		stateOpt.setPotentialValuesAndFlags(state_point);
		options.add(stateOpt);
		
		OptionText max = new OptionText("max",
				"Maximum search depth.", "10000000");
		max.setFlag("--max");
		options.add(max);
		
		OptionBoolean strict = new OptionBoolean("strict",
				"Forces DFS-FIFO to use strict BFS ordering for finding shorter lassos.",
				false);
		strict.setFlag("--strict");
		options.add(strict);
		
		OptionBoolean progressstates = new OptionBoolean("progress-states",
				"Forcess DFS-FIFO to use progress state labels, even if progress transition\n" + 
				"labels are present.",
				false);
		progressstates.setFlag("--progess-states");
		options.add(progressstates);
		
	}

	public static void addVerbositySymOptions(List<IOption> options) {
		OptionSeparator separator3 = new OptionSeparator("pins2lts-sym Options",
				"Flags that control the output of the SYM variant tool");
		options.add(separator3);

		OptionBoolean precise = new OptionBoolean("Compute the final number.",
				"Compute the final number of states precisely", false);
		precise.setFlag("--precise");
		options.add(precise);
		
		OptionBoolean nomatrix = new OptionBoolean("no print matrix.",
				"Do not print the dependency matrix if -v (verbose) is used.",
				false);
		nomatrix.setFlag("--no-matrix");
		options.add(nomatrix);
		
		
		OptionBoolean invpar = new OptionBoolean("check invariant",
				"Check all invariants in parallel.", false);
		invpar.setFlag("--inv-par");
		options.add(invpar);
	}
	


}
