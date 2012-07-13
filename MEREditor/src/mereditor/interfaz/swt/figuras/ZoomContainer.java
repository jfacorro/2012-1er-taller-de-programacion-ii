/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package mereditor.interfaz.swt.figuras;

import mereditor.interfaz.swt.listeners.DragDropControlador;
import mereditor.interfaz.swt.listeners.SeleccionControlador;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;

public class ZoomContainer extends Figure {
	public static final float MIN_ZOOM = 0.25f;
	public static final float MAX_ZOOM = 2.0f;
	public static final float DELTA_ZOOM = 0.25f;

	private float zoom = 1;

	private MouseListener selection = new MouseListener.Stub() {
		public void mousePressed(MouseEvent me) {
			SeleccionControlador.deselectAll(null);
		};
	};

	public ZoomContainer() {
		super();
		this.addMouseListener(this.selection);
		this.addMouseMotionListener(new DragDropControlador(this));
	}

	/**
	 * @see org.eclipse.draw2d.Figure#getClientArea()
	 */
	public Rectangle getClientArea(Rectangle rect) {
		super.getClientArea(rect);
		rect.width /= zoom;
		rect.height /= zoom;
		return rect;
	}

	/**
	 * @see org.eclipse.draw2d.Figure#paintClientArea(Graphics)
	 */
	protected void paintClientArea(Graphics graphics) {
		if (getChildren().isEmpty())
			return;

		boolean optimizeClip = getBorder() == null || getBorder().isOpaque();

		ScaledGraphics g = new ScaledGraphics(graphics);

		if (!optimizeClip)
			g.clipRect(getBounds().getShrinked(getInsets()));
		g.translate(getBounds().x + getInsets().left, getBounds().y
				+ getInsets().top);
		g.scale(zoom);
		g.pushState();
		paintChildren(g);
		g.popState();
		g.dispose();
		graphics.restoreState();
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
		revalidate();
		repaint();
	}

	/**
	 * Devuelve el zoom actual
	 * 
	 * @return
	 */
	public float getZoom() {
		return this.zoom;
	}

	/**
	 * @see org.eclipse.draw2d.Figure#translateToParent(Translatable)
	 */
	public void translateToParent(Translatable t) {
		t.performScale(zoom);
		super.translateToParent(t);
	}

	/**
	 * @see org.eclipse.draw2d.Figure#translateFromParent(Translatable)
	 */
	public void translateFromParent(Translatable t) {
		super.translateFromParent(t);
		t.performScale(1 / zoom);
	}

	/**
	 * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
	 */
	protected boolean useLocalCoordinates() {
		return true;
	}

	public void zoomIn() {
		if (this.getZoom() - DELTA_ZOOM >= MIN_ZOOM)
			this.setZoom(this.getZoom() - DELTA_ZOOM);
	}

	public void zoomOut() {
		if (this.getZoom() + DELTA_ZOOM <= MAX_ZOOM)
			this.setZoom(this.getZoom() + DELTA_ZOOM);
	}
}
