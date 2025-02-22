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
package fr.lip6.move.coloane.projects.its.io;

import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;
import fr.lip6.move.coloane.projects.its.Concept;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeDeclarationFactory;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.checks.CheckList;
import fr.lip6.move.coloane.projects.its.expression.IEvaluationContext;
import fr.lip6.move.coloane.projects.its.expression.IVariable;
import fr.lip6.move.coloane.projects.its.expression.Variable;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Classe destinee a la lecture d'un fichier XML. La lecture du fichier permet la construction d'un modele
 * @author yann based on jbvoron
 *
 */
public class ModelHandler extends DefaultHandler {
	private final Logger logger = Logger.getLogger("fr.lip6.move.coloane.its"); //$NON-NLS-1$

	private Deque<Object> stack = new ArrayDeque<Object>();

	// Correspondance entre les id du document xml et celle des nouveaux objets.
	private Map<Integer, Object> ids = new HashMap<Integer, Object>();

	// object constructed
	private TypeList types;

	private String readString;

	private URI workFile;

	public ModelHandler(URI workFile) {
		this.workFile = workFile;
	}



	/** {@inheritDoc} */
	@Override
	public final void startElement(String uri, String localName, String baliseName, Attributes attributes) throws SAXException {
		// Balise MODEL
		if ("model".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("types".equals(baliseName)) { //$NON-NLS-1$
			// stack a new TypeList
			stack.push(new TypeList(workFile));
		} else if ("type".equals(baliseName)) { //$NON-NLS-1$
			startType(attributes);
		} else if ("concept".equals(baliseName)) { //$NON-NLS-1$
			handleConcept(attributes);
		} else if ("concepts".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("parameter".equals(baliseName)) { //$NON-NLS-1$
			handleParameter(attributes);
		} else if ("parameters".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("checks".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("check".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("typeid".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("formula".equals(baliseName)) { //$NON-NLS-1$
			// NOP
			handleFormula(attributes);
		} else if ("formulas".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else {
			logger.warning("Unknown XML tag in source file: " + baliseName); //$NON-NLS-1$
		}
	}



	/** {@inheritDoc} */
	@Override
	public final void endElement(String uri, String localName, String baliseName) throws SAXException {
		// Balise MODEL
		if ("model".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("types".equals(baliseName)) { //$NON-NLS-1$
			// assign the constructed TypeList
			this.types =  (TypeList) stack.pop();
		} else if ("type".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("concept".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("concepts".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("parameter".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("parameters".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("checks".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("check".equals(baliseName)) { //$NON-NLS-1$
			// NOP
			CheckList cl = (CheckList) stack.pop();
			types.addCheckList(cl);
		} else if ("typeid".equals(baliseName)) { //$NON-NLS-1$
			stack.push(handleCheck(readString));
		} else if ("formula".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("formulas".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else {
			logger.warning("Unknown XML tag in source file: " + baliseName); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		readString = new String(arg0, arg1, arg2);
	}
	
	/**
	 * Parse a check description :type declaration field.
	 * @param typeid the type name
	 * @throws SAXException any parse error
	 */
	private CheckList handleCheck(String typeid) throws SAXException {
		// Get effective with maximum safeguards		
		int idEffective ;
		try { 
			idEffective = Integer.parseInt(typeid); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			throw new SAXException("Corrupted XML file, effective id " + typeid + " should be an integer referring to a type declaration");			
		}
		
		TypeDeclaration effective;
		try {
			effective = (TypeDeclaration) ids.get(idEffective);
		} catch (ClassCastException e) {
			throw new SAXException("Corrupted XML file, effective id " + idEffective + " should refer to a type declaration");
		}
		return new CheckList(effective);
	}


	private void handleFormula(Attributes attributes) {
		String name = attributes.getValue("name");
		String comment = attributes.getValue("description");
		String form = StringEscapeUtils.unescapeXml(attributes.getValue("formula"));
		CheckList cl = (CheckList) stack.peek();
		cl.addCTLFormula(name, form, comment);
	}

	
	/**
	 * Parse a concept description
	 * @param attributes the attributes of the concept in XML
	 * @throws SAXException any parse error
	 */
	private void handleConcept(Attributes attributes) throws SAXException {
		String name = attributes.getValue("name"); //$NON-NLS-1$
		int idEffective = Integer.parseInt(attributes.getValue("effective")); //$NON-NLS-1$
		int idParent = Integer.parseInt(attributes.getValue("parent")); //$NON-NLS-1$

		if (idEffective == -1) {
			// concept is not assigned
			return;
		}

		// Get parent with maximum safeguards
		CompositeTypeDeclaration parent;
		try {
			parent = (CompositeTypeDeclaration) ids.get(idParent);
		} catch (ClassCastException e) {
			throw new SAXException("Corrupted XML file, id " + idParent + " should refer to a composite type declaration");
		}
		if (parent == null) {
			throw new SAXException("Corrupted XML file, Dangling parent type id " + idParent + " in concept " + name);
		}
		// Get effective with maximum safeguards
		ITypeDeclaration effective;
		try {
			effective = (ITypeDeclaration) ids.get(idEffective);
		} catch (ClassCastException e) {
			throw new SAXException("Corrupted XML file, effective id " + idEffective + " should refer to a type declaration");
		}
		if (effective == null) {
			throw new SAXException("Corrupted XML file, Dangling Effective type id " + idEffective + " in concept " + name);
		}
		// get the concept
		Concept concept = parent.getConcept(name);
		if (concept == null) {
			logger.warning("Concept effective definition which should belong to CompositeType " + parent
					+ " does not exist in the actual model file. Ignoring Concept setting.");
		} else {
			concept.setEffective(effective);
		}
	}

	/**
	 * Parse a parameter description
	 * @param attributes the attributes of the concept in XML
	 * @throws SAXException any parse error
	 */
	private void handleParameter(Attributes attributes) throws SAXException {
		String name = attributes.getValue("name"); //$NON-NLS-1$
		int value = Integer.parseInt(attributes.getValue("value")); //$NON-NLS-1$
		int idParent = Integer.parseInt(attributes.getValue("parent")); //$NON-NLS-1$


		// Get parent with maximum safeguards
		ITypeDeclaration parent;
		try {
			parent = (ITypeDeclaration) ids.get(idParent);
		} catch (ClassCastException e) {
			throw new SAXException("Corrupted XML file, id " + idParent + " should refer to a type declaration");
		}
		if (parent == null) {
			throw new SAXException("Corrupted XML file, Dangling parent type id " + idParent + " in parameter " + name);
		}
		// get the evaluation context
		IEvaluationContext params = parent.getParameters();
		IVariable var = new Variable(name);
		if (!params.containsVariable(var)) {
			logger.warning("Concept effective definition which should belong to CompositeType " + parent
					+ " does not exist in the actual model file. Ignoring Concept setting.");
		} else {
			params.setVariableValue(var, value);
		}
	}

	/**
	 * Analyze a type declaration
	 * @param attributes Les attributs attachée à la balise
	 * @throws SAXException if the referenced model file is bad.
	 */
	private void startType(Attributes attributes) throws SAXException {
		TypeList types =  (TypeList) stack.peek();

		int id = Integer.parseInt(attributes.getValue("id")); //$NON-NLS-1$

		String name = attributes.getValue("name"); //$NON-NLS-1$
		String formalism = attributes.getValue("formalism"); //$NON-NLS-1$
		
		String workDir = new File(workFile).getParentFile().toURI().getPath();
		String filePath = workDir + "/" + attributes.getValue("path");
		
		
		URI file = new File(filePath).toURI();
		
		if (file == null ||  ! new File(filePath).canRead() ) {
			throw new SAXException("Could not open referenced file " + filePath, null);
		}
		ITypeDeclaration type;
		try {
			type = TypeDeclarationFactory.create(name, file, types);
		} catch (IOException e) {
			throw new SAXException("Could not open referenced file " + filePath, null);
		}
		if (!formalism.equals(type.getTypeType())) {
			String errmsg = "Model formalism type for file " + filePath + " does not match file contents.\n "
			+ "Expected type " + formalism + " read type " + type.getTypeType();
			logger.fine(errmsg);

			throw new SAXParseException(errmsg, null);
		}
		// store type in hash
		ids.put(id, type);
		types.addTypeDeclaration(type);
	}

	/**
	 * @return the type list loaded from the XML file
	 */
	public final TypeList getTypes() {
		return types;
	}
}

