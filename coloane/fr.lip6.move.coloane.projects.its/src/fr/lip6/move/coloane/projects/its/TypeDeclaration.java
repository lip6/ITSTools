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
package fr.lip6.move.coloane.projects.its;

import fr.lip6.move.coloane.core.model.factory.GraphModelFactory;
import fr.lip6.move.coloane.core.ui.files.ModelLoader;
import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.interfaces.formalism.INodeFormalism;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
import fr.lip6.move.coloane.interfaces.model.IElement;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.projects.its.expression.Constant;
import fr.lip6.move.coloane.projects.its.expression.EvaluationContext;
import fr.lip6.move.coloane.projects.its.expression.ExpressionFactory;
import fr.lip6.move.coloane.projects.its.expression.ExpressionParseResult;
import fr.lip6.move.coloane.projects.its.expression.IEvaluationContext;
import fr.lip6.move.coloane.projects.its.expression.IVariable;
import fr.lip6.move.coloane.projects.its.expression.IVariableBinding;
import fr.lip6.move.coloane.projects.its.expression.Infinity;
import fr.lip6.move.coloane.projects.its.expression.IntegerExpression;
import fr.lip6.move.coloane.projects.its.expression.Variable;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.variables.IModelVariable;
import fr.lip6.move.coloane.projects.its.variables.PlaceMarkingVariable;
import fr.lip6.move.coloane.projects.its.variables.TransitionClockVariable;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A type declaration, base class for {@link CompositeTypeDeclaration}. Handles
 * : * load from XML of a graph (factory style) * parameters (variables in the
 * models) * model role : notification of updates
 * 
 * @author Yann
 * 
 */
public class TypeDeclaration extends AbstractTypeDeclaration implements ISimpleObserver {
	/** The underlying coloane Graph */
	private IGraph graph;
	private IEvaluationContext context;
	private Map<IAttribute, IntegerExpression> attribs = new HashMap<IAttribute, IntegerExpression>();

	/**
	 * Protected ctor used to initialize a td.
	 * 
	 * @param typeName
	 *            name of type
	 * @param modelFile
	 *            file (resource) holding the model
	 * @param graph
	 *            graph loaded from file
	 * @param types
	 *            types to add to (parent)
	 */
	protected TypeDeclaration(String typeName, URI modelFile, IGraph graph,
			TypeList types) {
		super(typeName,modelFile,types);
		this.graph = graph;
		checkAndSetUnique();
	}


	private void checkAndSetUnique() {
		// for unnamed objects
		int nextId = 0;
		Map<INodeFormalism, Set<String>> idMap = new HashMap<INodeFormalism, Set<String>>();
		for (INode node : graph.getNodes()) {
			Set<String> formMap = idMap.get(node.getNodeFormalism());
			if (formMap == null) {
				formMap = new HashSet<String>();
				idMap.put(node.getNodeFormalism(), formMap);
			}
			IAttribute name = node.getAttribute("name");
			if (name==null) {
				continue;	
			}
			if (name.getValue()==null || name.getValue().isEmpty()) {
				name.setValue(node.getNodeFormalism().getName().substring(0,4)+nextId++);
			}
			if (formMap.contains(name.getValue())) {
				for (int i=0; i < graph.getNodes().size() ; i++) {
					String test = name.getValue()+i;
					if (! formMap.contains(test)) {
						name.setValue(test);
						break;
					}
				}
			}
			assert(!formMap.contains(name.getValue()));
			formMap.add(name.getValue());
		}
	}


	/**
	 * Compute the resulting value of the attribute, when substituting variables
	 * by their values.
	 * 
	 * @param a the attribute whose value should be an IntegerExpression
	 * @return the integer value post-substitution
	 */
	public final int getIntegerAttributeValue(IAttribute a) {
		IntegerExpression expr = attribs.get(a);
		if (expr == null) {
			try {
				return Integer.parseInt(a.getValue());
			} catch (NumberFormatException e) {
				return 0;
			}
		} else {
			return expr.evaluate(getParameters());
		}
	}



	/* (non-Javadoc)
	 * @see fr.lip6.move.coloane.projects.its.ITypeDeclaration#getTypeType()
	 */
	public final String getTypeType() {
		return graph.getFormalism().getName();
	}

	/**
	 * The graph of the underlying coloane model.
	 * 
	 * @return the graph
	 */
	public final IGraph getGraph() {
		return graph;
	}

	/**
	 * Load a IGraph from a file
	 * 
	 * @param typePath
	 *            the file to load from
	 * @return the coloane graph model
	 * @throws IOException
	 *             if any problems during parse or file load.
	 */
	static IGraph loadGraph(URI typePath) throws IOException {
		// Construction d'un modele en memoire a partir de se representation en
		// XML
		IGraph graph = ModelLoader.loadGraphFromXML(typePath);

		// Si le chargement a �chou�, on annule l'ouverture de l'�diteur
		if (graph == null) {
			throw new IOException("Load from XML file failed !");
		}
		return graph;
	}

	/**
	 * Compute the interface of a type.
	 * 
	 * @return the set of public labels of this type (ITS action alphabet)
	 * 
	 */
	protected Set<String> computeLabels() {
		Set<String> labels = new HashSet<String>();
		if (graph.getFormalism().getName().equals("Time Petri Net")) {
			Collection<INode> nodes = graph.getNodes();
			for (INode node : nodes) {
				if ("transition".equals(node.getNodeFormalism().getName())) {
					IAttribute visibility = node.getAttribute("visibility");
					if ("public".equals(visibility.getValue())) {
						IAttribute atts = node.getAttribute("label");
						labels.add(atts.getValue());
					}
				}
			}
		}
		return labels;
	}


	/** 
	 * Computes the set of variables of this model.
	 * This version tests for Time Petri net and returns all clocks and places.
	 * @return the list of model variables.
	 */
	protected List<IModelVariable> computeVariables() {
		List<IModelVariable> variables = new ArrayList<IModelVariable>();
		if (graph.getFormalism().getName().equals("Time Petri Net")) {
			Collection<INode> nodes = graph.getNodes();
			for (INode node : nodes) {
				if ("transition".equals(node.getNodeFormalism().getName())) {
					IAttribute early = node.getAttribute("earliestFiringTime");
					try {
						if (0 == Integer.parseInt(early.getValue())) {
							String late = node.getAttribute("latestFiringTime")
									.getValue();
							if ("inf".equalsIgnoreCase(late)
									|| 0 == Integer.parseInt(late)) {
								continue;
							}
						}
						variables.add(new TransitionClockVariable(node));

					} catch (NumberFormatException e) {
						continue;
					}
				} else if ("place".equals(node.getNodeFormalism().getName())) {
					variables.add(new PlaceMarkingVariable(node));
				}
			}
		}
		return variables;
	}


	

	/* (non-Javadoc)
	 * @see fr.lip6.move.coloane.projects.its.ITypeDeclaration#getParameters()
	 */
	public final IEvaluationContext getParameters() {
		if (context == null) {
			try {
				IEvaluationContext ct = computeParameters();
				if (ct instanceof EvaluationContext) {
					EvaluationContext context = (EvaluationContext) ct;
					context.addObserver(this);
				}
				this.context = ct;
			} catch (ExtensionException e) {
				final Logger logger = Logger
						.getLogger("fr.lip6.move.coloane.its"); //$NON-NLS-1$
				logger.warning("Model contains syntax errors. Please validate it through syntax check before import. Some model elements were not fully parsed."
						+ e);

			}
		}
		return context;
	}

	/**
	 * load the attributes that use int expressions.
	 * 
	 * @return an evaluation context
	 * @throws ExtensionException
	 *             in case of parse errors.
	 */
	protected IEvaluationContext computeParameters() throws ExtensionException {
		IEvaluationContext context = new EvaluationContext();
		for (INode node : graph.getNodes()) {
			if ("place".equals(node.getNodeFormalism().getName())) {
				IAttribute attrib = node.getAttribute("marking");
				parseIntExpression(attrib, context);
			} else if ("transition".equals(node.getNodeFormalism().getName())) {
				IAttribute eft = node.getAttribute("earliestFiringTime");
				parseIntExpression(eft, context);
				IAttribute lft = node.getAttribute("latestFiringTime");
				parseIntExpression(lft, context);
			}
		}
		for (IArc arc : graph.getArcs()) {
			// supports null attribute passing: some arcs have no valuation
			parseIntExpression(arc.getAttribute("valuation"), context);
		}
		for (IAttribute att : graph.getAttributes()) {
			if (att.getName().equals("size")) {
				parseIntExpression(att, context);
			}
		}
		return context;
	}

	/**
	 * Do the actual loading of a given attribute value = parse an int
	 * expression
	 * 
	 * @param attrib
	 *            attrib to load or null
	 * @param context
	 *            the current context (can be updated)
	 * @throws ExtensionException
	 *             if syntax errors occur
	 */
	private void parseIntExpression(IAttribute attrib,
			IEvaluationContext context) throws ExtensionException {
		if (attrib == null) {
			return;
		}
		String mark = attrib.getValue();
		if (mark != null && !"".equals(mark)) {

			ExpressionParseResult epr = ExpressionFactory.parseExpression(mark);
			int nberr = epr.getErrorCount();
			if (nberr != 0) {
				context.declareVariable(new Variable(
						"SYNTAX ERRORS IN MODEL, PLEASE RUN SYNTAX CHECK"
								+ epr.getErrors()));
			} else {
				IntegerExpression expr = epr.getExpression();

				if (!(expr instanceof Constant) && expr != null
						&& !(expr instanceof Infinity)) {
					// dont store the mapping for trivial integers
					attribs.put(attrib, expr);
					// could be empty for simple expressions, eg 3+ 2
					for (IVariable var : expr.supportingVariables()) {
						context.declareVariable(var);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see fr.lip6.move.coloane.projects.its.ITypeDeclaration#update()
	 */
	public final void update() {
		notifyObservers();
	}

	/**
	 * Build a new graph by replacing attribute values with int expressions by
	 * their concrete values.
	 * 
	 * @return the new graph
	 */
	public final IGraph getInstantiatedGraph() {
		// first build a copy of the graph in fr.lip6.move.coloane.its original
		// state
		IGraph copy = new GraphModelFactory().copyGraph(graph);
		// ensure attribs and context is up to date
		getParameters();
		// now edit the graph = update all attributes hit by int expressions
		for (Entry<IAttribute, IntegerExpression> it : attribs.entrySet()) {
			IAttribute att = it.getKey();
			IElement parent = att.getReference();
			IAttribute toupd;
			if (graph.getId() == parent.getId()) {
				toupd = copy.getAttribute(att.getName());
			} else {
				toupd = copy.getObject(parent.getId()).getAttribute(
						att.getName());
			}
			String newval = Integer.toString(it.getValue().evaluate(context));
			toupd.setValue(newval);
		}
		return copy;
	}

	/* (non-Javadoc)
	 * @see fr.lip6.move.coloane.projects.its.ITypeDeclaration#reload()
	 */
	public void reload() throws IOException {
		// free the current data
		attribs.clear();
		// force cache clear
		IEvaluationContext oldcontext = getParameters();
		if (oldcontext instanceof EvaluationContext) {
			EvaluationContext ct = (EvaluationContext) oldcontext;
			ct.deleteObserver(this);
		}
		context = null;
		clearCaches();
		graph = loadGraph(getTypeFile());
		checkAndSetUnique();
		// refresh the caches
		getLabels();
		getParameters();
		// copy old valuations back in
		for (IVariableBinding vb : oldcontext.getBindings()) {
			if (context.containsVariable(vb.getVariable())) {
				context.setVariableValue(vb.getVariable(),
						vb.getVariableValue());
			}
		}
	}

	
	@Override
	public boolean isSatisfied() {
		if (!super.isSatisfied()) {
			return false;
		}
		for (IVariableBinding concept : getParameters().getBindings()) {
			if (concept.getVariableValue() == null) {
				return false;
			}
		}
		return true;
	}	
	
	
	// protected List<INode> computeStateVariables () {
	// List<INode> vars;
	// EvaluationContext context = new EvaluationContext();
	// for (INode node : graph.getNodes()) {
	// if ("place".equals(node.getNodeFormalism().getName())) {
	// IAttribute attrib = node.getAttribute("marking");
	// parseIntExpression(attrib, context);
	// } else if ("transition".equals(node.getNodeFormalism().getName())) {
	// IAttribute eft = node.getAttribute("earliestFiringTime");
	// parseIntExpression(eft, context);
	// IAttribute lft = node.getAttribute("latestFiringTime");
	// parseIntExpression(lft, context);
	// }
	// }
	// for (IArc arc : graph.getArcs()) {
	// // supports null attribute passing: some arcs have no valuation
	// parseIntExpression(arc.getAttribute("valuation"), context);
	// }
	// for (IAttribute att : graph.getAttributes()) {
	// if (att.getName().equals("size")) {
	// parseIntExpression(att, context);
	// }
	// }
	// return context;
	// }
	//
	// public List<INode> getStateVariables () {
	// if (stateVariables == null) {
	//
	// }
	// return stateVariables;
	// }
}
