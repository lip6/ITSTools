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
package fr.lip6.move.coloane.projects.its.checks.ui;

import fr.lip6.move.coloane.projects.its.checks.AbstractCheckService;
import fr.lip6.move.coloane.projects.its.checks.CTLFormulaDescription;
import fr.lip6.move.coloane.projects.its.checks.IServiceResultProvider;
import fr.lip6.move.coloane.projects.its.checks.ServiceResult;
import fr.lip6.move.coloane.projects.its.order.Ordering;
import fr.lip6.move.coloane.projects.its.order.Orders;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;
import fr.lip6.move.coloane.projects.its.ui.forms.TypeTreeLabelProvider;
import fr.lip6.move.coloane.projects.its.variables.GalArrayVariable;
import fr.lip6.move.coloane.projects.its.variables.GalVariable;
import fr.lip6.move.coloane.projects.its.variables.IModelVariable;
import fr.lip6.move.coloane.projects.its.variables.InstanceVariable;
import fr.lip6.move.coloane.projects.its.variables.PlaceMarkingVariable;
import fr.lip6.move.coloane.projects.its.variables.ScalarInstanceVariable;
import fr.lip6.move.coloane.projects.its.variables.TransitionClockVariable;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The tree label provider for checklists.
 * @author Yann
 *
 */
public final class CheckListTreeLabelProvider extends TypeTreeLabelProvider implements
		IBaseLabelProvider {

	/**
	 * Return a nice formatted text for this element. The text includes some
	 * markers of being unsatisfied (per the model)
	 * 
	 * @param element
	 *            to display (TypeDeclaration, concept or VariableBinding)
	 * @return a nice formatted string
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof AbstractCheckService) {
			IServiceResultProvider cs = (IServiceResultProvider) element;
			return cs.getName();
		} else if (element instanceof ServiceResult) {
			ServiceResult sr = (ServiceResult) element;
			return sr.toString();
		} else if (element instanceof Orders) {
			return "Variable Orders";
		} else if (element instanceof Ordering) {
			return ((Ordering) element).getName();
		} else if (element instanceof CTLFormulaDescription) {
			CTLFormulaDescription ctl = (CTLFormulaDescription) element;
			return ctl.getName();
		} else if (element instanceof InstanceVariable) {
			InstanceVariable var = (InstanceVariable) element;
			return var.getInstanceName();
		} else if (element instanceof ScalarInstanceVariable) {
			ScalarInstanceVariable var = (ScalarInstanceVariable) element;
			return var.getInstanceName();
		} else if (element instanceof IModelVariable) {
			IModelVariable var = (IModelVariable) element;
			return var.getName();
		}
		return super.getText(element);
	}

	/**
	 * Return a nice graphic for the item.
	 * 
	 * @param element
	 *            to get an icon from
	 * @return an image (uniquely) loaded from the Resources
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof AbstractCheckService) {
			// CheckService cs = (CheckService) element;
			// TODO : an icon per service
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_REACH_SERVICE);
		} else if (element instanceof ServiceResult) {
			ServiceResult sr = (ServiceResult) element;
			switch (sr.getSuccess()) {
			case OK:
				return ITSEditorPlugin.getDefault().getImage(
						ITSEditorPlugin.IMG_RESULTOK);
			case NOK:
				return ITSEditorPlugin.getDefault().getImage(
						ITSEditorPlugin.IMG_RESULTNOK);
			case FAIL:
				return ITSEditorPlugin.getDefault().getImage(
						ITSEditorPlugin.IMG_RESULTFAIL);
			}
		} else if (element instanceof Orders) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_COMPOSITE);
		} else if (element instanceof TransitionClockVariable) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_TRANSITION);
		} else if (element instanceof PlaceMarkingVariable) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_PLACE);
		} else if (element instanceof InstanceVariable
				|| element instanceof ScalarInstanceVariable) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_INSTANCE);
		} else if (element instanceof CTLFormulaDescription) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_VARIABLE);
		} else if (element instanceof GalVariable || element instanceof GalArrayVariable) {
			return ITSEditorPlugin.getDefault().getImage(
					ITSEditorPlugin.IMG_VARIABLE);
		}
		return super.getImage(element);
	}

}
