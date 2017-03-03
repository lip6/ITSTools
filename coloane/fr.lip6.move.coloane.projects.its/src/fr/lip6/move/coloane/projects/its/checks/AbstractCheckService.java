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

import fr.lip6.move.coloane.interfaces.utils.ProcessController;
import fr.lip6.move.coloane.interfaces.utils.ProcessController.TimeOutException;
import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * A basic implementation of a Check Service.
 * @author Yann
 *
 */
public abstract class AbstractCheckService extends SimpleObservable implements
		IServiceResultProvider {

	private static final int DEFAULT_TIMEOUT = 60;
	private static final String TIMEOUT_DURATION = "Maximum execution time";


	private String name;
	private ParameterList parameters = new ParameterList();
	private CheckList parent;
	private String reportText;

	private List<ServiceResult> results = new LinkedList<ServiceResult>();

	private String workdir;

	public AbstractCheckService(CheckList parent, String serviceName) {
		this.parent = parent;
		this.name = serviceName;
		parameters.addParameter(TIMEOUT_DURATION, Integer.toString(DEFAULT_TIMEOUT), "Maximum time allowed for process execution before the \"Failed due to timeout\" message is produced.");
	}
	/**
	 * format error message
	 * 
	 * @param errorOutput
	 *            the message
	 * @return the formatted message
	 */
	private static String createContentMessage(ByteArrayOutputStream errorOutput) {
		if (errorOutput.size() == 0) {
			return "";
		}
		return " Process produced the following error output: \n" + errorOutput;
	}

	public void addResult(ServiceResult serviceResult) {
		results.add(serviceResult);
		notifyObservers();
	}

	protected abstract List<String> buildCommandArguments();

	public String getDefaultWorkDir(URI folder) {
		
		File workfolder = new File(folder.getPath(), getParent().getType().getTypeName()
				+ "_" + getName());
		if (! workfolder.exists() ) {
			workfolder.mkdir();
		}
		return workfolder.toURI().getPath();
	}

	public String getName() {
		return name;
	}

	public ParameterList getParameters() {
		return parameters;
	}

	public CheckList getParent() {
		return parent;
	}

	public String getReportText() {
		return reportText;
	}

	protected abstract URI getToolPath();

	public String getWorkDir() {
		if (workdir == null) {
			URI workpos = new File(getParent().getType().getTypeList().getPath()).getParentFile().toURI();
			workdir = getDefaultWorkDir(workpos);
		}
		return workdir;
	}

	public URI getWorkDirPath() {
		return new File(getWorkDir()).toURI();
	}

	public Iterator<ServiceResult> iterator() {
		return results.iterator();
	}

	public abstract String run();

	/**
	 * Bare bones API for launching dot. Command line options are passed to
	 * Graphviz as specified in the options parameter. The location for dot is
	 * obtained from the user preferences.
	 * 
	 * @return a non-zero integer if errors happened
	 * @throws IOException
	 */
	public IStatus runTool(URI workdir) {
		URI toolFullPath = getToolPath();
		if (toolFullPath == null || toolFullPath.getPath().isEmpty() ) {
			return new Status(
					IStatus.ERROR,
					ITSEditorPlugin.getID(),
					"Please specify the absolute path to the tool in the preferences page Coloane->ITS Path.");
		}
		if (! new File(toolFullPath).isFile()) {
			return new Status(IStatus.ERROR, ITSEditorPlugin.getID(),
					"Could not find ITS tool at \"" + toolFullPath + "\"");
		}
		List<String> cmd = buildCommandArguments();
		ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
		ByteArrayOutputStream stdOutput = new ByteArrayOutputStream();

		int timeout = DEFAULT_TIMEOUT;
		try {
			timeout = Integer.parseInt(parameters
					.getParameterValue(TIMEOUT_DURATION));
		} catch (NumberFormatException e) {
			// NOP, stay at 60 secs.
		}

		try {
			final ProcessController controller = new ProcessController(
					timeout * 1000, cmd.toArray(new String[cmd.size()]), null,
					new File(workdir));
			controller.forwardErrorOutput(errorOutput);
			controller.forwardOutput(stdOutput);
			int exitCode = controller.execute();
			if (exitCode != 0) {
				// return new Status(IStatus.WARNING, ITSEditorPlugin.getID(),
				// "ITS exit code: " + exitCode + "."
				// + createContentMessage(errorOutput));
				// }
				if (errorOutput.size() > 0) {
					return new Status(IStatus.WARNING, ITSEditorPlugin.getID(),
							createContentMessage(errorOutput));
				}
			}
			reportText = stdOutput.toString();
			return Status.OK_STATUS;
		} catch (TimeOutException e) {
			return new Status(IStatus.ERROR, ITSEditorPlugin.getID(),
					"Check Service process did not finish in a timely way."
							+ createContentMessage(errorOutput));
		} catch (IOException e) {
			return new Status(IStatus.ERROR, ITSEditorPlugin.getID(),
					"Unexpected exception executing service."
							+ createContentMessage(errorOutput), e);
		}
	}

	protected void setReport(String report) {
		this.reportText = report;
	}

	public void setWorkdir(String workdir) {
		if (!workdir.equals(this.workdir)) {
			this.workdir = workdir;
			notifyObservers();
		}
	}
	
	

}
