package fr.lip6.move.gal.cegar.checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.interfaces.IResult;
import fr.lip6.move.gal.cegar.interfaces.ITraceChecker;

public class ITSTraceChecker implements ITraceChecker {
	private ITSLauncher launcher;

	public ITSTraceChecker(ITSLauncher launcher) {
		this.launcher = launcher;
	}

	@Override
	public IResult check(Specification gal, String[] trace) throws IOException  {

		// trivial trace, no replay needed
		if (trace.length == 0)
			return new CheckResult(true, false, null);

		// get trace info and pass it as argument to ITS trace checker

		launcher.setModel(gal);
		launcher.setProperty("cegar");

		String traceStr = join(trace);

		launcher.setTrace(traceStr);
		launcher.run();

		BufferedReader in;
		in = new BufferedReader(launcher.getResult());
		String line;
		while ((line = in.readLine()) != null) {

			getLog().finest("read trace : " + line);

			if (line.contains("Replay trace successfully played your trace")) {
				return new CheckResult(true, false, null);
			} else if (line.contains("could not execute transition :")) {
				String[] toRet = {line.split(":")[1]};
				return new CheckResult(false, true, toRet);
			}

		}
		
		throw new IOException("Parser failed.");
	}

	/**
	 * unfortunately String.join(" ", trace) is java >= 1.8
	 * @param trace an array to join
	 * @return a String with spaces between trace's entries
	 */
	private String join(String[] trace) {
		StringBuilder sb = new StringBuilder();
		boolean first = false ;
		for (String tr : trace) {
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(tr);
		}
		String traceStr = sb.toString();
		return traceStr;
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}
}
