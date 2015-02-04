package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

import fr.lip6.move.gal.instantiate.GALRewriter;

public class AutoTagHotbit implements IObjectActionDelegate, IElementUpdater {

	

	@Override
	public void run(IAction action) {
		GALRewriter.autoTagHotbit = ! GALRewriter.autoTagHotbit;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setChecked(GALRewriter.autoTagHotbit);
	}

}
