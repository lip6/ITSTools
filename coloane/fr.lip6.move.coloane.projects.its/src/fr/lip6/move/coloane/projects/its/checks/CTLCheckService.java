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

import fr.lip6.move.coloane.projects.its.checks.ServiceResult.Status;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class CTLCheckService extends ITSCheckService implements ISimpleObserver {

	private static final String CTL_NAME = "CTL Check";
	private static final String CTL_FILE_NAME = "formula.ctl";
	private static final String CTL_FORWARD_PARAM = "Use Forward CTL model-checking (faster)";
	private static final String CTL_WITNESS_PARAM = "Produce a witness/counter-example";
	private static final String CTL_FAIRTIME_PARAM = "Force fair time elapse";
	private String ctlForm;

	public CTLCheckService(CheckList parent) {
		super(parent, CTL_NAME);
		getParameters().addBooleanParameter(CTL_FORWARD_PARAM, true, "Use forward CTL model-checking. This is usually faster and more efficient than traditional backward model-checking.");
		getParameters().addBooleanParameter(CTL_WITNESS_PARAM, false, "Build an explanation for the truth value of the formula. This is a witness if formula is true, or a counter-example otherwise.");
		getParameters().addBooleanParameter(CTL_FAIRTIME_PARAM, true, "This option eliminates traces which end in time elapsing indefinitely while other transitions are still enabled.");
	}

	@Override
	protected List<String> buildCommandArguments() {
		List<String> cmd = super.buildCommandArguments();
		cmd.add("-ctl");
		if (!isDeadlock()) {
			cmd.add(CTL_FILE_NAME);
		} else {
			cmd.add("DEADLOCK");			
		}
		cmd.add("--legend");
		if (!getParameters().getBoolParameterValue(CTL_FORWARD_PARAM)) {
			cmd.add("--backward");
		} else {
			cmd.add("--forward");
		}
		if (getParameters().getBoolParameterValue(CTL_WITNESS_PARAM)) {
			cmd.add("--witness");
		}
		if (getParameters().getBoolParameterValue(CTL_FAIRTIME_PARAM)) {
			cmd.add("--fair-time");
		}
		
		return cmd;
	}

	private boolean isDeadlock() {
		return "DEADLOCK;".equals(ctlForm);
	}

	@Override
	protected URI getToolPath() {
		try {
			return BinaryToolsPlugin.getProgramURI(Tool.ctl);
		} catch (IOException e) {
			return ITSEditorPlugin.getDefault().getITSReachPath();
		}
	}

	public String run(String ctlFormula,
			IServiceResultProvider ctlFormulaDescription) {
		currentFormula = ctlFormulaDescription;
		ctlForm = ctlFormula;
		try {
			File file = new File(getWorkDir() + "/" + CTL_FILE_NAME);
			FileOutputStream writer = new FileOutputStream(file);
			BufferedWriter sb = new BufferedWriter(new OutputStreamWriter(
					writer));
			file.createNewFile();
			sb.append(ctlFormula);
			sb.newLine();
			sb.close();
		} catch (Exception e) {
			String report = "An error occurred during service invocation :" + e
					+ e.getMessage();
			addResult(new ServiceResult(Status.FAIL, report, this));
			return report;
		}
		String ret = super.run();
		currentFormula = null;
		return ret;
	}

	@Override
	protected Status interpretResult(String report) {
		// Now interpret the result
		if (isDeadlock()) {
			if (report.contains("System contains 0 deadlocks")) {
				return Status.OK;
			} else {
				return Status.NOK;
			}
			
		} else if (report.contains("Formula is TRUE !") ) {		
			return Status.OK;
		} else if (report.contains("Formula is FALSE !")) {
			return Status.NOK;
		} else {
			return Status.FAIL;
		}

	}

	private IServiceResultProvider currentFormula;

	@Override
	public void addResult(ServiceResult serviceResult) {
		currentFormula.addResult(serviceResult);
	}

	List<CTLFormulaDescription> formulae = new ArrayList<CTLFormulaDescription>();

	public void addFormula(String name, String formula, String comments) {
		CTLFormulaDescription form = new CTLFormulaDescription(name, formula,
				comments, this);
		formulae.add(form);
		form.addObserver(this);
		notifyObservers();
	}

	public List<CTLFormulaDescription> getFormulae() {
		return formulae;
	}

	public void update() {
		notifyObservers();
	}
}
