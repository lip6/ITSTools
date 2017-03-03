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

import fr.lip6.move.coloane.extension.importExportRomeo.exportToRomeo.ExportToRomeo;
import fr.lip6.move.coloane.extensions.importExportCAMI.exportToCAMI.ExportToImpl;
import fr.lip6.move.coloane.interfaces.exceptions.ModelException;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeDeclaration;
import fr.lip6.move.coloane.projects.its.antlrutil.ErrorReporter;
import fr.lip6.move.coloane.projects.its.checks.ServiceResult.Status;
import fr.lip6.move.coloane.projects.its.flatten.ModelFlattener;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.order.Ordering;
import fr.lip6.move.coloane.projects.its.order.Orders;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import main.antlr3.fr.lip6.move.coloane.projects.its.order.parser.JSONOrderParserLexer;
import main.antlr3.fr.lip6.move.coloane.projects.its.order.parser.JSONOrderParserParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;

public class OrderingService extends AbstractCheckService implements
		ISimpleObserver {

	private static final String ORDER_NAME = "Variable Ordering";
	private static final String ORDER_HEURISTIC_PARAM = "Heuristic chosen";
	private static final String FLAT_ORDER_PARAM = "Use flat order";

	private static final String ORDER_FILE_NAME = "model.json";
	private static final String CAMI_FILE_NAME = "model.cami";
	private static final String ROMEO_FILE_NAME = "model.xml";

	private Orders orders;

	public OrderingService(CheckList parent) {
		super(parent, ORDER_NAME);
		getParameters().addParameter(ORDER_HEURISTIC_PARAM,"","Choose a heuristic to compute a variable order used in subsequent model-checking.");
		getParameters().addBooleanParameter(FLAT_ORDER_PARAM, false,"Force to use a flat ordering scheme (no SDD hierarchy used).");
		orders = new Orders();
		orders.addObserver(this);
	}

	@Override
	protected URI getToolPath() {
		// return "python";
		return ITSEditorPlugin.getDefault().getPythonPath();
	}

	@Override
	protected List<String> buildCommandArguments() {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(getToolPath().getPath());
		cmd.add(ITSEditorPlugin.getDefault().getOrderingPath().getPath());
		// cmd.add("--quiet");
		// cmd.add("-T" + format);
		cmd.add("--model");
		cmd.add(CAMI_FILE_NAME);
		cmd.add("--porder");
		cmd.add(ORDER_FILE_NAME);
		cmd.add("--order");
		cmd.add(getParameters().getParameterValue(ORDER_HEURISTIC_PARAM));
		if (getParameters().getBoolParameterValue(FLAT_ORDER_PARAM)) {
			cmd.add("--flat");
		}
		return cmd;
	}

	@Override
	public String run() {
		Status success = Status.OK;
		String report = "";
		IGraph graph;
		// Step 1 : flatten the model if necessary
		ITypeDeclaration td = getParent().getType();
		if (td instanceof CompositeTypeDeclaration) {
			ModelFlattener mf = new ModelFlattener();
			try {
				mf.doFlatten((CompositeTypeDeclaration) td, true);
			} catch (ModelException e) {
				success = Status.FAIL;
				report = "An error occurred during flatten model phase :" + e
						+ e.getMessage();
				addResult(new ServiceResult(success, report, this));
				return report;
			}
			graph = mf.getFlatModel();
		} else if (td instanceof TypeDeclaration) {
			graph = ((TypeDeclaration)td).getInstantiatedGraph();
		} else {
			throw new UnsupportedOperationException("Ordering heuristic is only available for TPN.");
		}

		// Step 2 : export to CAMI to build input for Silien's ordering tool
		try {
			new ExportToImpl().export(graph, getWorkDir() + "/"
					+ CAMI_FILE_NAME, new NullProgressMonitor());
		} catch (Exception e) {
			success = Status.FAIL;
			report = "An error occurred during export to CAMI phase :" + e
					+ e.getMessage();
			addResult(new ServiceResult(success, report, this));
			return report;
		}
		try {
			// export the TPN to Romeo xml format.
			new ExportToRomeo().export(graph, getWorkDir() + "/"
					+ ROMEO_FILE_NAME, new NullProgressMonitor());
		} catch (Exception e) {
			success = Status.FAIL;
			report = "An error occurred during export to Romeo phase :" + e
					+ e.getMessage();
			addResult(new ServiceResult(success, report, this));
			return report;
		}

		// Step 3 : invoke Silien's tool
		// RUN THE SERVICE
		IStatus status = runTool(getWorkDirPath());
		if (!status.isOK()) {
			success = Status.FAIL;
			report = "An error occurred during service invocation :"
					+ status.getMessage();
		} else {
			report = getReportText();
		}
		addResult(new ServiceResult(success, report, this));

		// Step 4 : load the output of Silien's tool
		{
			JSONOrderParserLexer lexer;
			try {
				lexer = new JSONOrderParserLexer(new ANTLRFileStream(
						getWorkDir() + "/" + ORDER_FILE_NAME));
			} catch (IOException e1) {
				report = "Failed to open the model.json file that should have been generated by Ordering tool."
						+ e1.getLocalizedMessage();
				success = Status.FAIL;
				return report;
			}

			CommonTokenStream tokens = new CommonTokenStream(lexer);

			JSONOrderParserParser parser = new JSONOrderParserParser(tokens);
			Ordering order = null;
			ErrorReporter reporter = new ErrorReporter();
			parser.setErrorReporter(reporter);
			try {
				order = parser.prog();
				for (String error : reporter) {
					report += error;
					success = Status.FAIL;
				}
			} catch (RecognitionException e) {
				report = e.getLocalizedMessage();
				success = Status.FAIL;
			}
			if (success == Status.OK) {

				order = addTransitions(order, graph);
				getOrders().addOrder("Ordering " + getParameters().toString(),
						order);

				// convert place names appropriately
				for (Ordering o : order) {
					String oldName = o.getName();
					for (INode node : graph.getNodes()) {
						IAttribute a = node.getAttribute("name");
						if (a != null && a.getValue().equals(oldName)) {
							// found
							o.setName("P_" + node.getId() + oldName);
							break;
						}
					}
				}

			}
		}

		return report;
	}

	private Ordering addTransitions(Ordering order, IGraph graph) {
		if (getParent().getType().getTypeType().equals("Time Petri Net")) {
			for (INode node : graph.getNodes()) {
				if (node.getNodeFormalism().getName().equals("transition")) {
					if (!(node.getAttribute("earliestFiringTime").getValue()
							.equals("0") && (node
							.getAttribute("latestFiringTime").getValue()
							.equals("inf")) ||  node
							.getAttribute("latestFiringTime").getValue().equals("0") )) {
						int index = 0;
						for (IArc a : node.getIncomingArcs()) {
							index = Math.max(
									order.getVarIndex(a.getSource()
											.getAttribute("name").getValue()),
									index);
						}
						order.insertVarAtIndex("__clock_T_" + node.getId()
								+ node.getAttribute("label").getValue(),
								index + 1);
					}
				}
			}
		}
		return order;
	}

	public void update() {
		notifyObservers();
	}

	public Orders getOrders() {
		return orders;
	}

}
