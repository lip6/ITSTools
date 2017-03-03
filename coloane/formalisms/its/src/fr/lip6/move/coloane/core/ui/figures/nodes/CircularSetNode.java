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

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Description of a Scalar Set figure : a box with some suggestion of nested boxes inside.
 *
 * @author Y. Thierry-Mieg, based on C. Demoulins RectangleNode class
 */
public class CircularSetNode extends AbstractCompositeNodeFigure {
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
	protected final void outlineShape(Graphics graphics) {
		Rectangle r = getBounds();
		int lineWidth = getLineWidth(); 
		int x = r.x + lineWidth / 2;
		int y = r.y + lineWidth / 2;
		int w = r.width - Math.max(1, lineWidth);
		int h = r.height - Math.max(1, lineWidth);
		// the outline of the set
		graphics.drawRectangle(x, y, w, h);
		// the internal "current" instance rect
		int intx = x + 3* w / 16;
		int inty = y + h / 5;
		int intw = w / 4;
		int inth = 3 * h / 5;
		graphics.drawRectangle(intx, inty, intw, inth);
		int intx2 = intx + intw + w / 8 ;
		graphics.drawRectangle(intx2, inty, intw, inth);
		// The \cdots to left and right
		int lx = x + w / 12;
		int ly = y + h / 2 ;
		graphics.drawString("...", lx, ly);
		int rx = intx + 7 * w / 8;
		graphics.drawString("...", rx, ly);
		
	}

	/**
	 * {@inheritDoc}
	 */
	public final ConnectionAnchor getConnectionAnchor() {
		return new ChopboxAnchor(this);
	}
}
