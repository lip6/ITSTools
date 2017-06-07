package fr.lip6.move.gal.itstools.launch.devTools;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.launch.OptionsTab;

public class ReachFormula {

	
	private static Map<String, Map<String, String>> optionFlagMap ;
	public static void instanciate(OptionsTab tab){
		OptionBoolean trace_states = new OptionBoolean ("trace-state", "if set, this option will force to print intermediate states (up to print limimit) when showing traces.", true);
		Map<String, String> trace_states_map = new HashMap<String, String>();
		trace_states_map.put("true", "--trace-states");
		OptionText2 print_limit = new OptionText2("print_limit", "set the thershold for full printout of states in traces. DD holding more states than threshold will not be printed", "10.");
		Map<String, String> print_limit_map = new HashMap<String, String>();
		print_limit_map.put("true", "--print-limit ");
		OptionEnum sdd_ddd = new OptionEnum("sdd/ddd", "sdd : privilege SDD storage (Petri net models only\nddd : privilege DDD (no hierarchy) encoding (Petri net models only)", "sdd");	
		Map<String, String> sdd_ddd_map = new HashMap<String, String>();
		sdd_ddd.setPotentialValues(new String[] {"sdd", "ddd"});
		sdd_ddd_map.put("sdd", "--sdd");
		sdd_ddd_map.put("ddd", "--ddd");
		OptionBoolean no_garbage = new OptionBoolean("no-garbage", "disable garbage collection (may be faster, more memory", true);
		Map<String, String> no_garbage_map = new HashMap<String, String>();
		no_garbage_map.put("true", "--no-garbage");
		OptionText2 gc_threshold = new OptionText2("gc-threshold", "set the threshold for first strarting to do gc (value in GigaBytes)", "1.3");
		Map<String, String> gc_threshold_map = new HashMap<String, String>();
		gc_threshold_map.put("true", "--gc-threshold");
		OptionEnum fixpoint = new OptionEnum("fixpoint", "this options controls which kind of saturation algorithm is applied. Both are variants of saturation not really full DFS or BFS", "BFS");
		Map<String, String> fixpoint_map = new HashMap<String, String>();
		fixpoint_map.put("BFS", "--fixpoint BFS");
		fixpoint_map.put("DFS", "--fixpoint DFS");
		fixpoint.setPotentialValues(new String[] {"BFS", "DFS"});
		OptionText2 path = new OptionText2("path", "specifies the path prefix to construct dot state-space graph","");
		Map<String, String> path_map = new HashMap<String, String>();
		path_map.put("true", "-d");
		OptionText2 bmc = new OptionText2("bmc", "bmc XXX : use limited depth BFS exploration, up to XXX steps from initial state", "");
		Map<String, String> bmc_map = new HashMap<String, String>();
		bmc_map.put("true", "-bmc");
		OptionBoolean quiet = new OptionBoolean ("Quiet", "limit output verbosity useful in conjunction with te output --textline for batch performance runs", true);
		Map<String, String> quiet_map = new HashMap<String, String>();
		quiet_map.put("true","--quiet");
		OptionBoolean stats = new OptionBoolean ("stats", "produce stats on max sum of variables (i.e. maximum tokens in a marking for a Petri net), maximum variable value (bound for a Petri net)", true);
		Map<String, String> stats_map = new HashMap<String, String>();
		stats_map.put("true", "--stats");
		OptionBoolean edgeStat = new OptionBoolean("EdgeStat", "produces stats on the size of the transition relation, i.e the number of unique soiurce to target state pairs it contains.", true);
		Map<String, String> edgeStat_map = new HashMap<String, String>();
		edgeStat_map.put("true", "--edgeCount"); //ATTENTION EDGECOUNT OR EDGESTAT
		OptionBoolean no_witness = new OptionBoolean("no-witness", "disable trace computation and just return a yes/no answer(faster).");
		Map<String, String> no_witness_map = new HashMap<String, String>();
		no_witness_map.put("true", "--nowitness");
		OptionEnumWithText ssD = new OptionEnumWithText("Scalar and Circular Sym. ", " -ssD2 INT : (depth 2 levels) use 2 level depth for scalar sets. Integer provided defines level 2 block size. [DEFAULT: -ssD2 1]\n-ssDR INT : (depth recursive) use recursive encoding for scalar sets. Integer provided defines number of blocks at highest levels.\n-ssDS INT : (depth shallow recursive) use alternative recursive encoding for scalar sets. Integer provided defines number of blocks at lowest level.", "D2", "1");
		ssD.setPotentialValues(new String[] { "D2", "DR", "DS"});
		Map<String, String> ssD_map = new HashMap<String, String>();
		ssD_map.put("D2", "-ssD2");
		ssD_map.put("DR", "-ssDR");
		ssD_map.put("DS", "-ssDS");
		OptionText dump_order_path = new OptionText("dump-order path", "dump the currently used variable order to file designated by path and exit","path");
		Map<String, String> dump_order_path_map = new HashMap<String, String>();
		dump_order_path_map.put("true", "--dump-order ");
		
		optionFlagMap = new HashMap<String, Map<String, String>>();
		
		optionFlagMap.put("trace-state", trace_states_map);
		optionFlagMap.put("print_limit", print_limit_map);
		optionFlagMap.put("sdd/ddd", sdd_ddd_map);
		optionFlagMap.put("no-garbage", no_witness_map);
		optionFlagMap.put("gc-threshold", gc_threshold_map);
		optionFlagMap.put("fixpoint", fixpoint_map);
		optionFlagMap.put("path", path_map);
		optionFlagMap.put("bmc", bmc_map);
		optionFlagMap.put("Quiet", quiet_map);
		optionFlagMap.put("stats", stats_map);
		optionFlagMap.put("EdgeStat", edgeStat_map);
		optionFlagMap.put("no-witness", no_witness_map);
		optionFlagMap.put("Scalar and Circular Sym. ", ssD_map);
		optionFlagMap.put("dump-order pathh", dump_order_path_map);
		
		tab.addOption(trace_states);
		tab.addOption(print_limit);
		tab.addOption(sdd_ddd);
		tab.addOption(no_garbage);
		tab.addOption(gc_threshold);
		tab.addOption(fixpoint);
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
		
		for (Entry<String, Map<String, String>> entry : optionFlagMap.entrySet()){
			try {
				String value = configuration.getAttribute(entry.getKey(), "");
				if (value.length() > 0){
					String[] split_result = value.split(" ");
					if (split_result.length == 2) {
						cl.addArg(entry.getValue().get(split_result[0]));
						cl.addArg(split_result[1]);
					}else{
						cl.addArg(entry.getValue().get(value));
					}
				}
				System.out.println(cl);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
