package fr.lip6.move.gal.itstools.launch.devTools;

import java.util.HashMap;
import org.eclipse.debug.core.ILaunchConfiguration;

import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.launch.OptionsTab;

public class ReachFormula {

	
	private static OptionsTab tab;
	
	public static void instanciate(OptionsTab tab){
		ReachFormula.tab = tab;
		OptionBoolean trace_states = new OptionBoolean ("trace-state", "if set, this option will force to print intermediate states (up to print limimit) when showing traces.", true);
		trace_states.setFlag("--trace-states");
		OptionText print_limit = new OptionText("print_limit", "set the thershold for full printout of states in traces. DD holding more states than threshold will not be printed", "10");
		print_limit.setFlag("--print-limit ");
		OptionSeparator separator1 = new OptionSeparator("General Options 1", "I'm a separator");
		OptionEnum sdd_ddd = new OptionEnum("sdd/ddd", "sdd : privilege SDD storage (Petri net models only\nddd : privilege DDD (no hierarchy) encoding (Petri net models only)", "sdd");	
		HashMap<String, String> sdd_ddd_map = new HashMap<String, String>();
		
		sdd_ddd_map.put("sdd", "--sdd");
		sdd_ddd_map.put("ddd", "--ddd");
		sdd_ddd.setPotentialValuesAndFlags(sdd_ddd_map);
		OptionBoolean no_garbage = new OptionBoolean("no-garbage", "disable garbage collection (may be faster, more memory", true);
		no_garbage.setFlag("--no-garbage");
		OptionText gc_threshold = new OptionText("gc-threshold", "set the threshold for first strarting to do gc (value in GigaBytes)", "1.3");
		gc_threshold.setFlag("--gc-threshold");
//		OptionEnum fixpoint = new OptionEnum("fixpoint", "this options controls which kind of saturation algorithm is applied. Both are variants of saturation not really full DFS or BFS", "BFS");
//		HashMap<String, String> fixpoint_map = new HashMap<String, String>();
//		fixpoint_map.put("BFS", "--fixpoint BFS");
//		fixpoint_map.put("DFS", "--fixpoint DFS");
//		fixpoint.setPotentialValuesAndFlags(fixpoint_map);
		OptionText path = new OptionText("path", "specifies the path prefix to construct dot state-space graph",null);
		path.setPathExtension(".dot");
		path.setFlag("-d");
		OptionText bmc = new OptionText("bmc", "bmc XXX : use limited depth BFS exploration, up to XXX steps from initial state", "");
		bmc.setFlag("-bmc");
		OptionBoolean quiet = new OptionBoolean ("Quiet", "limit output verbosity useful in conjunction with te output --textline for batch performance runs", true);
		quiet.setFlag("--quiet");
		OptionBoolean stats = new OptionBoolean ("stats", "produce stats on max sum of variables (i.e. maximum tokens in a marking for a Petri net), maximum variable value (bound for a Petri net)", true);
		stats.setFlag("--stats");
		OptionBoolean edgeStat = new OptionBoolean("EdgeStat", "produces stats on the size of the transition relation, i.e the number of unique soiurce to target state pairs it contains.", true);
		edgeStat.setFlag("--edgeCount"); //ATTENTION EDGECOUNT OR EDGESTAT
		OptionBoolean no_witness = new OptionBoolean("no-witness", "disable trace computation and just return a yes/no answer(faster).");
		no_witness.setFlag("--nowitness");
		OptionEnumWithText ssD = new OptionEnumWithText("Scalar and Circular Sym. ", " -ssD2 INT : (depth 2 levels) use 2 level depth for scalar sets. Integer provided defines level 2 block size. [DEFAULT: -ssD2 1]\n-ssDR INT : (depth recursive) use recursive encoding for scalar sets. Integer provided defines number of blocks at highest levels.\n-ssDS INT : (depth shallow recursive) use alternative recursive encoding for scalar sets. Integer provided defines number of blocks at lowest level.", "D2", "1");
		
		HashMap<String, String> ssD_map = new HashMap<String, String>();
		ssD_map.put("D2", "-ssD2");
		ssD_map.put("DR", "-ssDR");
		ssD_map.put("DS", "-ssDS");
		ssD.setPotentialValuesAndFlags(ssD_map);
		OptionText dump_order_path = new OptionText("dump-order path", "dump the currently used variable order to file designated by path and exit",null);
		dump_order_path.setPathExtension(".txt");
		dump_order_path.setFlag("--dump-order");
		

		tab.addOption(trace_states);
		tab.addOption(print_limit);
		tab.addOption(separator1);
		tab.addOption(sdd_ddd);
		tab.addOption(no_garbage);
		tab.addOption(gc_threshold);
		//tab.addOption(fixpoint);
		tab.addOption(dump_order_path);
		tab.addOption(path);
		tab.addOption(bmc);
		tab.addOption(quiet);
		tab.addOption(stats);
		tab.addOption(edgeStat);
		tab.addOption(no_witness);
		tab.addOption(ssD);

	}
	
	public static void addFlags(CommandLine cl, ILaunchConfiguration configuration){
		
		for (IOption<?> opt : tab.getOptions()){
			opt.addFlagsToCommandLine(cl, configuration);
		}
	}
}
