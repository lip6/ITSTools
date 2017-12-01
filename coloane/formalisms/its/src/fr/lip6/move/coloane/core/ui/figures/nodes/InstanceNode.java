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
package fr.lip6.move.coloane.core.ui.figures.nodes;

import fr.lip6.move.coloane.core.ui.figures.ICompositeNodeFigure;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
import fr.lip6.move.coloane.interfaces.model.INode;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.FrameBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.jface.resource.JFaceResources;

/**
 * Description of a Instance Node figure : a box with a required interface.
 *
 * @author Y. Thierry-Mieg
 */
public class InstanceNode extends AbstractCompositeNodeFigure implements PropertyChangeListener, ICompositeNodeFigure {
	private String instanceName = ":";
	private String interfaces = "";
	private List<IArc> edges = new ArrayList<IArc>();
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void fillShape(Graphics graphics) {
		graphics.fillRectangle(getBounds().getResized(-1, -1));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void modelElementChanged(INode oldModelElement,
			INode newModelElement) {
		if (oldModelElement != null) {
			oldModelElement.removePropertyChangeListener(this);
		}
		newModelElement.addPropertyChangeListener(this);		
		// trigger an update.
		propertyChange(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void outlineShape(Graphics graphics) {
		drawInterface(graphics);
	}

	private void setTitle() {
		FrameBorder tb = new FrameBorder(instanceName);
		tb.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		setBorder(tb);
	}

	private void drawInterface(Graphics graphics) {
		Insets inset = getBorder().getInsets(this);
		graphics.drawText(interfaces , getBounds().getTopLeft().translate(inset.left+3, inset.top+3));
	}

	/**
	 * {@inheritDoc}
	 */
	public final ConnectionAnchor getConnectionAnchor() {
		return new ChopboxAnchor(this);
	}

	/**
	 * We update the String instanceName and the size here if necessary to reflect model changes.
	 * {@inheritDoc}
	 */
	public void propertyChange(PropertyChangeEvent event) {
		
		for (IArc a : edges) {
			a.removePropertyChangeListener(this);
		}
		edges.clear();
		
		String name = getModel().getAttribute("name").getValue();
		String type = getModel().getAttribute("type").getValue();
		String toshow2 = name + " : " + type;
		
		Set<String> ops = new TreeSet<String>();
		for (IArc a : getModel().getIncomingArcs()) {
			handleArc(a,ops);
		}
		for (IArc a : getModel().getOutgoingArcs()) {
			handleArc(a,ops);
		}
		StringBuilder sb = new StringBuilder();
		for (String op : ops) {
			sb.append(op+"\n");
		}
		String interfaces2 = "";
		if (sb.length() > 0) {
			interfaces2 = sb.substring(0, sb.length()-1);
		}
		
		if (! (toshow2.equals(instanceName) && interfaces2.equals(interfaces)) ) {
			instanceName = toshow2;
			interfaces = interfaces2;
			setTitle();
			getModel().getGraphicInfo().setSize(computeSize());
		}
	}

	private Dimension computeSize() {
		Dimension bordersize = getBorder().getPreferredSize(this);
		Dimension isize = FigureUtilities.getTextExtents(interfaces, getFont());
		Dimension maxsize = new Dimension(Math.max(bordersize.width, isize.width), 
					bordersize.height + isize.height );
		return maxsize.expand(10,15);
	}

	private void handleArc(IArc a, Set<String> ops) {		
		a.addPropertyChangeListener(this);
		IAttribute att = a.getAttribute("labels");
		if (att != null) {
			String [] labs = att.getValue().split(";");
			for (String lab : labs) {
				if (! "".equals(lab))
					ops.add(lab);
			}
		}
		edges.add(a);
	}

}
