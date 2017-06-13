package fr.lip6.move.gal.itstools.launch.devTools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.launch.OptionsTab;

public class ReachFormula {

	
	private static OptionsTab tab;
	private static List<IOption<?>> optionstmp = new LinkedList<>();

	public static void instanciate(OptionsTab tab){
		ReachFormula.tab = tab;
		addVerbosityOptions(tab);		

		addMemoryOptions(tab);

		addSpecialRunOptions(tab);

		addEncodingOptions(tab);				
	}

	private static void addSpecialRunOptions(OptionsTab tab) {
		OptionSeparator separator3 = new OptionSeparator("Special execution options", "Flags that control what is computed");
		tab.addOption(separator3);
		
		OptionText bmc = new OptionText("bmc", "bmc XXX : use limited depth BFS exploration, only explore up to XXX steps from initial state", null);
		bmc.setFlag("-bmc");

		OptionText dump_order_path = new OptionText("dump-order path", "dump the currently used variable order to file designated by path and exit",null);
		dump_order_path.setPathExtension(".txt");
		dump_order_path.setFlag("--dump-order");
		
		tab.addOption(dump_order_path);
		tab.addOption(bmc);
	}

	private static void addVerbosityOptions(OptionsTab tab) {
		OptionSeparator separator3 = new OptionSeparator("Verbosity Options", "Flags that control the output of the tool");
		tab.addOption(separator3);
				
		OptionText path = new OptionText("Export state space to a .dot file.", "Exports the final state space SDD/DDD representation as GraphViz dot files. Specify the path prefix to construct dot state-space graph in.",null);
		path.setPathExtension(".dot");
		path.setFlag("-d");
		
		OptionBoolean quiet = new OptionBoolean ("Quiet flag, to limit verbosity.", "limit output verbosity useful in conjunction with te output --textline for batch performance runs", true);
		quiet.setFlag("--quiet");

		OptionBoolean no_witness = new OptionBoolean("Do not compute witness traces", "disable trace computation and just return a yes/no answer(faster).");
		no_witness.setFlag("--nowitness");
		OptionBoolean trace_states = new OptionBoolean ("In any reported trace, also report intermediate states in the trace ?", "if set, this option will force to print intermediate states (up to print limit) when showing traces. Default behavior is to only print a trace as a list of transition names.", true);
		trace_states.setFlag("--trace-states");
		OptionText print_limit = new OptionText("Set the maximal size of state sets reported in the trace", "State sets with less than this limit will be printed in extenso. DD holding more states will just print their size.", "10");
		print_limit.setFlag("--print-limit");
		
		OptionBoolean stats = new OptionBoolean ("Show statistics on final state space.", "Produces stats on max sum of variables (i.e. maximum tokens in a marking for a Petri net), maximum variable value (bound for a Petri net)", true);
		stats.setFlag("--stats");
		OptionBoolean edgeStat = new OptionBoolean("Show edge count statistics", "Reports the size of the transition relation, i.e the number of unique source to target state pairs it contains.", true);
		edgeStat.setFlag("--edgeCount");

		tab.addOption(quiet);		
		tab.addOption(path);
		tab.addOption(trace_states);
		tab.addOption(print_limit);
		tab.addOption(stats);
		tab.addOption(edgeStat);
		tab.addOption(no_witness);
	}

	private static void addEncodingOptions(OptionsTab tab) {
		OptionSeparator separator2 = new OptionSeparator("Encoding Options", "Flags that control symbolic encoding of the system");
		tab.addOption(separator2);
		
		
		OptionEnum sdd_ddd = new OptionEnum("Privilege SDD (hierarchy) or flat DDD ?", "sdd : privilege SDD storage (Petri net models only\nddd : privilege DDD (no hierarchy) encoding (Petri net models only)", "sdd");	
		HashMap<String, String> sdd_ddd_map = new HashMap<String, String>();		
		sdd_ddd_map.put("sdd", "--sdd");
		sdd_ddd_map.put("ddd", "--ddd");
		sdd_ddd.setPotentialValuesAndFlags(sdd_ddd_map);
		
		OptionEnum fixpoint = new OptionEnum("Saturation fixpoint variant", "This option controls how the saturation algorithm is applied : BFS iterates over transitions at each level, while DFS attempts to exploit self-chaining. Both are variants of saturation not really full DFS or BFS", "BFS");
		fixpoint.setFlag("--fixpoint");
		HashMap<String, String> fixpoint_map = new HashMap<String, String>();
		fixpoint_map.put("BFS", "BFS");
		fixpoint_map.put("DFS", "DFS");
		fixpoint.setPotentialValuesAndFlags(fixpoint_map);
		
		OptionEnumWithText ssD = new OptionEnumWithText("Use recursive encodings for Scalar with block size :", " -ssD2 INT : (depth 2 levels) use 2 level depth for scalar sets. Integer provided defines level 2 block size. [DEFAULT: -ssD2 1]\n-ssDR INT : (depth recursive) use recursive encoding for scalar sets. Integer provided defines number of blocks at highest levels.\n-ssDS INT : (depth shallow recursive) use alternative recursive encoding for scalar sets. Integer provided defines number of blocks at lowest level.", "D2", "1");
		HashMap<String, String> ssD_map = new HashMap<String, String>();
		ssD_map.put("D2", "-ssD2");
		ssD_map.put("DR", "-ssDR");
		ssD_map.put("DS", "-ssDS");
		ssD.setPotentialValuesAndFlags(ssD_map);

		tab.addOption(sdd_ddd);
		tab.addOption(fixpoint);
		tab.addOption(ssD);
	}

	private static void addMemoryOptions(OptionsTab tab) {
		OptionSeparator separator1 = new OptionSeparator("Memory Options", "Flags that control memory usage and garbage collection ");
		tab.addOption(separator1);
		OptionBoolean no_garbage = new OptionBoolean("Avoid any garbage collection", "Disable garbage collection (may be faster, more memory), not usually a good idea.", false);
		no_garbage.setFlag("--no-garbage");
		OptionText gc_threshold = new OptionText("Start GC at resident memory (in GB):", "Set the threshold for first trigger of gc (value in GigaBytes)", "1.3");
		gc_threshold.setFlag("--gc-threshold");
		tab.addOption(no_garbage);
		tab.addOption(gc_threshold);
	}
	
	public static void addFlags(CommandLine cl, ILaunchConfiguration configuration){
		
		
		
		for (IOption<?> opt : optionstmp){
			//for (IOption<?> opt : tab.getOptions()){

			opt.addFlagsToCommandLine(cl, configuration);
		}
	}
	
	public static void setDefaultValue(ILaunchConfigurationWorkingCopy wc){
//		for (IOption<?> opt : tab.getOptions()){
//			opt.setDefaultValue(wc);
//		
//		if (optionstmp.isEmpty()){
//			OptionsTab tmp = new OptionsTab();
//			instanciate(tmp);
//			optionstmp = tmp.getOptions();
//			System.out.println("\n\nAzinta");
//		}
//		System.out.println("ABC"+optionstmp);
//		for (IOption<?> opt : optionstmp){
//			opt.setDefaultValue(wc);
//		}
	}
}
