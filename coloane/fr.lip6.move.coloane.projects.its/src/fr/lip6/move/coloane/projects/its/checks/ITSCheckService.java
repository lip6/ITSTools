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

import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.projects.its.checks.ServiceResult.Status;
import fr.lip6.move.coloane.projects.its.io.model.ITSModelWriter;
import fr.lip6.move.coloane.projects.its.order.ITSOrderWriter;
import fr.lip6.move.coloane.projects.its.order.Ordering;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;

public abstract class ITSCheckService extends AbstractCheckService {

	private static final String ORDER_FILE_NAME = "model.ord";
	private static final String QUIET_PARAMETER = "Low Verbosity";
	private static final String DDDSDD_PARAMETER = "Privilege SDD encoding";
	private static final String SCALAR_PARAMETER = "Use recursive encodings for scalar";
	private static final String DEPTH2 = "Depth2";
	private static final String DEPTHREC = "DepthRec";
	private static final String DEPTHSHALLOW = "DepthShallow";
	private static final String GCTHRESHOLD = "GC Threshold, in MB";
	
	private static final String [] SCALAR_POTENTIAL = {DEPTH2,DEPTHREC,DEPTHSHALLOW};
	private static final String BLOCK_SIZE_PARAMETER = "Block size in Scalar encoding";
	
	private OrderParameter order;

	public ITSCheckService(CheckList parent, String serviceName) {
		super(parent, serviceName);
		order = new OrderParameter(parent.getOrders());
		getParameters().addBooleanParameter(QUIET_PARAMETER, true, "Lower the verbosity of output. On verbose mode, the tool will print the model's internal representation as well as more traces on what is going on.");
		getParameters()
				.addBooleanParameter(
						DDDSDD_PARAMETER,
						true,
						"Primarily use SDD to encode variables. Sometimes (but rarely overall) less efficient than DDD (i.e. leave unchecked) for models with large marking values.");
		getParameters()
				.addEnumParameter(
						SCALAR_PARAMETER,
						DEPTH2,
						"Set a recursive encoding strategy for scalar sets. \n Depth2 : (depth 2 levels) use 2 level depth for scalar sets. Integer provided defines level 2 block size. [DEFAULT: Depth2, with blocks size 1] \n   -depthRec INT : (depth recursive) use recursive encoding for scalar sets. Integer provided defines number of blocks at highest levels. \n    -DepthShallow INT : (depth shallow recursive) use alternative recursive encoding for scalar sets. Integer provided defines number of blocks at lowest level.",
						SCALAR_POTENTIAL);
		getParameters().addParameter(BLOCK_SIZE_PARAMETER, "1", "Sets the block size used as additional setting for encoding of Scalar set.");
		getParameters().addParameter(GCTHRESHOLD, "1300", "Soft memory limit before starting to invoke GC, should be set to 50-80% of system memory.Default is 1300MB=1.3GB. Lower values decrease memory footprint but increase runtime.");
		
	}

	@Override
	protected List<String> buildCommandArguments() {
		List<String> cmd = new ArrayList<String>();
		cmd.add(getToolPath().getPath());
		if (getParameters().getBoolParameterValue(QUIET_PARAMETER)) {
			cmd.add("--quiet");
		}
		cmd.add("-i");
		cmd.add("modelMain.xml");
		cmd.add("-t");
		cmd.add("ITSXML");
		// Handle order input
		if (buildOrderFile()) {
			cmd.add("--load-order");
			cmd.add(getOrderFileName());
		}
		if (getParameters().getBoolParameterValue(DDDSDD_PARAMETER)) {
			cmd.add("--sdd");
		} else {
			cmd.add("--ddd");
		}
		
		String strat = getParameters().getEnumParameterValue(SCALAR_PARAMETER);
		if (strat.equals(DEPTH2)) {
			cmd.add("-ssD2");			
		} else if (strat.equals(DEPTHREC)) {
			cmd.add("-ssDR");
		} else if (strat.equals(DEPTHSHALLOW)) {
			cmd.add("-ssDS");
		}
		cmd.add(getParameters().getParameterValue(BLOCK_SIZE_PARAMETER));
		
		cmd.add("--gc-threshold");
		cmd.add(getParameters().getParameterValue(GCTHRESHOLD));
		
		return cmd;
	}
	

	private boolean buildOrderFile() {
		Ordering o = order.getSelection();
		if (o == null) {
			return false;
		}

		// build the file
		ITSOrderWriter ow = new ITSOrderWriter();
		try {
			ow.writeOrder(getOrderFileName(), getParent().getType(), o);
		} catch (ExtensionException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private String getOrderFileName() {
		return getWorkDir() + "/" + ORDER_FILE_NAME;
	}

	@Override
	public final String run() {
		ITSModelWriter mw = new ITSModelWriter();
		String report;
		Status success = Status.OK;
		try {
			mw.exportITSModel(getParent().getType().getTypeList(), getParent().getType(),
					getWorkDir());
			report = "Run successful in folder " + getWorkDir();
		} catch (Exception e) {
			success = Status.FAIL;
			report = "An error occurred during Export phase of service invocation :"
					+ e + e.getMessage();
		}

		// RUN THE SERVICE
		IStatus status = runTool(getWorkDirPath());
		if (!status.isOK()) {
			success = Status.FAIL;
			report += "An error occurred during ITS service invocation :"
					+ status.getMessage();
		} else {
			report = getReportText();

			success = interpretResult(report);
		}
		addResult(new ServiceResult(success, report, this));
		return report;
	}

	protected Status interpretResult(String report) {
		return Status.OK;
	}

	public OrderParameter getOrder() {
		return order;
	}
}
