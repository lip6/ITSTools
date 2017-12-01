package fr.lip6.move.coloane.core.ui.figures.nodes;

import fr.lip6.move.coloane.core.ui.figures.AbstractNodeFigure;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.JFaceResources;

public abstract class AbstractDelegate extends AbstractNodeFigure {
	

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
	protected final void outlineShape(final Graphics graphics) {
		Rectangle r = getBounds();

		boolean isPublic = getModel().getAttribute("visibility").getValue()
				.equals("public");

		int lineWidth = getLineWidth();
		int x = r.x + lineWidth / 2;
		int y = r.y + lineWidth / 2;
		int w = r.width - Math.max(1, lineWidth);
		int h = r.height - Math.max(1, lineWidth);
		graphics.drawRectangle(x, y, w, h);

		// double the line for public transition : gives a "bold" effect.
		if (isPublic) {
			int ix = x + 2;
			int iy = y + 2;
			int iw = Math.max(1, w - 4);
			int ih = Math.max(1, h - 4);
			graphics.drawRectangle(ix, iy, iw, ih);
		}
		
		String type = getNodeType();
		graphics.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));		
		graphics.drawText(type , getBounds().getTopLeft().translate(5, 3));
		

	}

	protected abstract String getNodeType() ;

	/**
	 * {@inheritDoc}
	 */
	public final ConnectionAnchor getConnectionAnchor() {
		return new ChopboxAnchor(this);
	}

}
