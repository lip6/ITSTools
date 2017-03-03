package fr.lip6.move.coloane.core.ui.figures.nodes;

import fr.lip6.move.coloane.core.ui.figures.AbstractNodeFigure;
import fr.lip6.move.coloane.core.ui.figures.ICompositeNodeFigure;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;
import fr.lip6.move.coloane.projects.its.Concept;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;
import fr.lip6.move.coloane.projects.its.ui.forms.OpenEditorAction;


public abstract class AbstractCompositeNodeFigure extends AbstractNodeFigure implements
		ICompositeNodeFigure {

	public void handleDoubleClick() {
		// attempt to find a context.
		INode node = getModel();
		TypeList tl = ITSEditorPlugin.getDefault().getCurrentModel();
		if (tl != null) {
			for (ITypeDeclaration td : tl) {
				if (td.getTypeType().equals(node.getNodeFormalism().getFormalism().getName()))  {
					if (td instanceof CompositeTypeDeclaration) {
						CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
						Concept c = ctd.getConcept(getModel().getAttribute("type").getValue());
						
						if (c!=null && c.getEffective() != null) {
							OpenEditorAction.openEditor(c.getEffective());
						}
					}
				}
			}
		}
	}

}
