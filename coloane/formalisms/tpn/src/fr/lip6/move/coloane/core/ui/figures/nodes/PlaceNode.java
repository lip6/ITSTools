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


import fr.lip6.move.coloane.core.ui.figures.AbstractNodeFigure;
import fr.lip6.move.coloane.interfaces.model.INode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;

/**
 * Description of a Place + Marking figure : marking is shown as black dots if <= 3, as integer if < 10.
 * 
 *
 * @author Y. Thierry-Mieg
 */
public class PlaceNode extends AbstractNodeFigure implements PropertyChangeListener {
	private int toshow = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void fillShape(Graphics graphics) {
		graphics.fillOval(getBounds().getResized(-1, -1));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void outlineShape(Graphics graphics) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBounds());
		r.width--;
		r.height--;
		int lineWidth = getLineWidth();
		r.shrink((lineWidth - 1) / 2, (lineWidth - 1) / 2);
		graphics.drawOval(r);

		if (toshow > 0 && toshow < 10) {
			switch (toshow) { 
			case 0 :
				return;
			case 1:
			{			
				drawToken(0.52,0.52,graphics);
				return;
			}
			case 2:
			{			
				drawToken(0.2,0.40,graphics);
				drawToken(0.65,0.50,graphics);
				return;
			}
			case 3:
			{			
				drawToken(0.2,0.3,graphics);
				drawToken(0.4,0.6,graphics);
				drawToken(0.6,0.3,graphics);
				return;
			}
			default :
			{
				Point x = getBounds().getLeft().translate(6, -7);			
				graphics.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT)); 
				graphics.drawString(""+toshow, x);
				return;
			}
			}
		}

	}

	private void drawToken(double y, double x, Graphics graphics) {
		Rectangle r = getBounds();
		Rectangle tocenter = getBounds().getCopy().getTranslated((int)(r.height*y), (int)(r.width*x));			
		tocenter.height = 5;
		tocenter.width = 5;
		Color bg = graphics.getBackgroundColor();
		graphics.setBackgroundColor(graphics.getForegroundColor());
		graphics.fillOval(tocenter);
		graphics.setBackgroundColor(bg);			

	}

	/**
	 * {@inheritDoc}
	 */
	public final ConnectionAnchor getConnectionAnchor() {
		return new EllipseAnchor(this);
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
	 * We update the String toshow and the size here if necessary to reflect model changes.
	 * {@inheritDoc}
	 */
	public void propertyChange(PropertyChangeEvent event) {
		try {
			toshow = Integer.parseInt(getModel().getAttribute("marking").getValue());
		} catch (NumberFormatException e) {
			toshow = 0;
		}
	}
}
