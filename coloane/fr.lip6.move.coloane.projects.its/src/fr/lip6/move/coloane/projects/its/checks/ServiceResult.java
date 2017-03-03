/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.coloane.projects.its.checks;

import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceResult extends SimpleObservable {

	public enum Status {
		FAIL, OK, NOK
	};

	private Status success;
	private String report;
	private Calendar date;
	private IServiceResultProvider cs;
	private ParameterList parameters;
	private ParameterList results;

	public ServiceResult(Status success, String report, AbstractCheckService cs) {
		this.success = success;
		this.report = report;
		this.date = new GregorianCalendar();
		this.cs = cs;
		this.parameters = new ParameterList(cs.getParameters());
		results = null;
	}

	public ParameterList getParameters() {
		return parameters;
	}

	public String getReport() {
		return report;
	}

	public Status getSuccess() {
		return success;
	}

	public String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy HH:mm:ss");
		return formatter.format(date.getTime());
	}

	public IServiceResultProvider getParent() {
		return cs;
	}

	/** a nice short label describing this result, used in tree label provider. */
	@Override
	public String toString() {
		String ret = success.toString();
		ret += getDate();
		return ret;
	}

	public ParameterList getResults() {
		if (results==null) {
			results = new ParameterList();
			parseParameters(results,report);
		}
		return results;
	}

	private void parseParameters(ParameterList results2, String report2) {
		String [] lines = report2.split("\n");
		boolean isReachStatLine=false;
		int formulaId = 0;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.contains("Model ,")) {
				if (++i < lines.length) {
					line = lines[i];
					String[] stats = line.split(",");
					if (stats.length >= 5) {
						if (! isReachStatLine) {
							results2.addParameter("Nb. states", stats[1], "Number of reachable states");
							results2.addParameter("Time (s)", stats[2], "Time for computation, in seconds");
							results2.addParameter("Mem (kB)", stats[3], "Peak memory used, in kilobytes");
							isReachStatLine = true;
						} else {
							if (formulaId==0) {
								results2.addParameter("Time witness (s)", stats[2], "Time for computation, in seconds, including reachability and witness construction");
								results2.addParameter("Mem witness (kB)", stats[3], "Peak memory used, in kilobytes, for reachability test and witness construction.");
							} else {
								results2.addParameter("Formula "+formulaId+" verdict", stats[2].equals("0")?"false":"true", "Is the CTL formula true in the initial state.");
								results2.addParameter("Time formula "+formulaId, stats[2], "Time for computation, in seconds, total after checking formula.");
								results2.addParameter("Mem formula "+formulaId, stats[2], "Memory for computation, in kB to check formula.");
							}
						}
					}
				}
			} else if (line.contains("reachable states that satisfy your predicate")) {
				String[] stats = line.split(" ");
				if (stats.length>=11) {
					results2.addParameter("Predicate", stats[10], "Reachability predicate");
					results2.addParameter("Nb. states verifying", stats[2], "Number of reachable states that validate the predicate");					 
				}
				if (++i < lines.length) {
					line = lines[i];
					if (line.contains("computing trace")) {
						if (++i < lines.length) {
							line = lines[i];
							// diagnostic line about whether reverse trans rel is exact
							assert(line.contains("Reverse"));
							if (++i < lines.length) {
								line = lines[i];
								StringBuilder trace = new StringBuilder();
								String[] transitions = line.split(" ");
								Pattern p = Pattern.compile("T_\\d+(.+)"); 
								for (String trans : transitions) {
									Matcher m = p.matcher(trans);
									if (m.matches()) {
										trans = m.group(1);
									}
									//									trans.replace("T_\\d+", "");
									trace.append(trans + " ");
								}

								results2.addParameter("Trace", trace.toString(), "Sequence of transitions leading to target states.");
							}
						}
					}
				}
			} else if (line.contains("System contains")&& line.contains("deadlocks")) {
				String[] stats = line.split(" ");
				if (stats.length>=5) {
					results2.addParameter("Deadlocks", stats[2], "System reachable deadlocks");
				}
			} else if (line.contains("original formula")) {
				formulaId++;
				String[] stats = line.split(":");
				if (stats.length>=2) {
					results2.addParameter("Formula "+formulaId, stats[1], "Normalized CTL formula (with ITS Ids)");
				}
			} else if (line.contains("equivalent forward existential")) {
				String[] stats = line.split(":");
				if (stats.length>=2) {
					results2.addParameter("Forward formula "+formulaId, stats[1], "Normalized Forward-CTL formula (with ITS Ids)");
				}
			} else if (line.contains("Formula is")) {
				results2.addParameter("Formula "+formulaId+" verdict", line.replace("Formula ", ""), "Is the CTL formula true in the initial state.");				
			}

		}
	}
}