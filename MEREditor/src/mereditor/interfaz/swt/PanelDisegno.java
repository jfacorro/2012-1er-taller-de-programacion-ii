package mereditor.interfaz.swt;

import mereditor.control.Proyecto;
import mereditor.interfaz.swt.figuras.ZoomContainer;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class PanelDisegno {
	private ZoomContainer panel;
	private FigureCanvas canvas;
	private Proyecto proyecto;

	public PanelDisegno(FigureCanvas canvas, Proyecto proyecto) {
		this.panel = new ZoomContainer();
		this.canvas = canvas;
		this.canvas.setContents(this.panel);
		this.proyecto = proyecto;
	}

	/**
	 * Actualiza la vista con el diagrama actual.
	 */
	public void actualizar() {
		this.panel.removeAll();
		this.proyecto.getDiagramaActual().dibujar(this.panel);
	}

	/**
	 * Aplica una disminución al zoom.
	 */
	public void zoomOut() {
		this.panel.zoomIn();		
	}
	
	/**
	 * Aplica un aumento al zoom.
	 */
	public void zoomIn() {
		this.panel.zoomOut();		
	}

	/**
	 * Devuelve un thumbnail del diagrama actual.
	 * @return
	 */
	public Image getImagen() {
		Rectangle rootFigureBounds = this.panel.getBounds();
		GC figureCanvasGC = new GC(this.canvas);
		
		Image image = new Image(this.canvas.getDisplay(), rootFigureBounds.width, rootFigureBounds.height);
		GC imageGC = new GC(image);
		
		imageGC.setBackground(figureCanvasGC.getBackground());
		imageGC.setForeground(figureCanvasGC.getForeground());
		imageGC.setFont(figureCanvasGC.getFont());
		imageGC.setLineStyle(figureCanvasGC.getLineStyle());
		imageGC.setLineWidth(figureCanvasGC.getLineWidth());
		//imageGC.setXORMode(figureCanvasGC.getXORMode());

		Graphics imgGraphics = new SWTGraphics(imageGC);
		this.panel.paint(imgGraphics);

		return image;
	}
}
