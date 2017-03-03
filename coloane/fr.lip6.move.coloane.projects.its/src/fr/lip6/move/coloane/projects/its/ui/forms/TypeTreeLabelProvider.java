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
package fr.lip6.move.coloane.projects.its.ui.forms;

import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;
import fr.lip6.move.coloane.projects.its.Concept;
import fr.lip6.move.coloane.projects.its.GALTypeDeclaration;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.expression.IVariableBinding;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A nice tree label provider for the Types tree view.
 * Handles type declarations, concepts, variable bindings
 * @author Yann
 *
 */
public class TypeTreeLabelProvider
			extends LabelProvider
			implements ILabelProvider {


	/**
	 * Return a nice formatted text for this element.
	 * The text includes some markers of being unsatisfied (per the model)
	 * @param element to display (TypeDeclaration, concept or VariableBinding)
	 * @return a nice formatted string
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof ITypeDeclaration) {
			ITypeDeclaration type = (ITypeDeclaration) element;
			String ret = "";
			if (!type.isSatisfied()) {
				ret += "!!!  ";
			}
			ret += type.getTypeName() + ": " + type.getTypeType();
			return ret;
		} else if (element instanceof Concept) {
			Concept concept = (Concept) element;
			if (concept.getEffective() != null) {
				return concept.getName() + ": " + concept.getEffective().getTypeName();
			} else {
				return "!!!  " + concept.getName() + ": ? ";
			}
		} else if (element instanceof IVariableBinding) {
			IVariableBinding vb = (IVariableBinding) element;
			Integer val = vb.getVariableValue();
			if (val != null) {
				return vb.getVariableName() + ":= " + val;
			} else {
				return "!!!  " + vb.getVariableName() + ":= ?";
			}
		}
		return "Unrecognized type in TypeLabelProvider";
	}

	/**
	 * Return a nice graphic fro the item.
	 * @param element to get an icon from
	 * @return an image (uniquely) loaded from the Resources
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof CompositeTypeDeclaration) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_COMPOSITE);
		}
		if (element instanceof GALTypeDeclaration) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_GAL);
		}
		if (element instanceof ITypeDeclaration) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_TPNFORM);
		} else if (element instanceof Concept) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_INSTANCE);
		} else if (element instanceof IVariableBinding) {
			IVariableBinding vb = (IVariableBinding) element;
			if (vb.getVariableValue() != null) {
				return ITSEditorPlugin.getDefault().getImage(
						ITSEditorPlugin.IMG_SETVAR);
			} else {
				return ITSEditorPlugin.getDefault().getImage(
						ITSEditorPlugin.IMG_USETVAR);
			}
		}
		return null;
	}


}
