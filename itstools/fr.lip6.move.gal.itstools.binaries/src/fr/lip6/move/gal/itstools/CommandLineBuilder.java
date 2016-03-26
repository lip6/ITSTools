package fr.lip6.move.gal.itstools;

import java.io.IOException;

import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;

public class CommandLineBuilder {

	
	private CommandLine cl;

	
	public CommandLineBuilder(Tool tool) throws IOException {
		cl = new CommandLine();

		// Path to ITS-reach exe				
		String itsReachPath = BinaryToolsPlugin.getProgramURI(tool).getPath().toString();
		cl.addArg(itsReachPath);

		// 2 Gig
		cl.addArg("--gc-threshold");
		cl.addArg("2000000");
		if (tool != Tool.ltl)
			// quiet
			cl.addArg("--quiet");
	}
	
	public void setModelFile(String modelff) {
		cl.addArg("-i");
		cl.addArg(modelff);
	}
	
	public void setModelType(String type) {
		cl.addArg("-t");
		cl.addArg(type);
	}

	public void setReachableProp (String prop) {
		cl.addArg("-reachable");
		cl.addArg(prop);
	}

	public void setPropFile (String propff) {
		cl.addArg("-reachable-file");
		cl.addArg(propff);
	}
	
	public CommandLine getCommandLine() {
		return cl;
	}

	public void setTrace(String trace) {
		cl.addArg("-trace");
		cl.addArg(trace);		
	}
}
