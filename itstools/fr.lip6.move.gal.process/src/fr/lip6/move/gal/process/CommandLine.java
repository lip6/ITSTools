package fr.lip6.move.gal.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandLine {

	private List<String> args = new ArrayList<String>();
	private File workingDir;

	public void addArg(String arg) {
		args .add(arg);
	}
	
	public void setWorkingDir (File dir) {
		this.workingDir = dir;
	}
	
	public String[] getArgs() {
		return args.toArray(new String[args.size()]);
	}
	
	public File getWorkingDir() {
		return workingDir;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (workingDir != null) sb.append("cd ").append(workingDir).append(";");
		for (String arg : args) {
			sb.append("'").append(arg).append("'").append(" ");
		}
		return sb.toString();
	}
 
	
}
