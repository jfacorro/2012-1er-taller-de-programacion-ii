package mereditor.interfaz.swt;

import java.util.LinkedHashMap;
import java.util.Map;

import mereditor.control.Proyecto;
import mereditor.interfaz.swt.figuras.ZoomContainer;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class PanelDisegno {

	private ZoomContainer panel;
	private FigureCanvas canvas;
	private Proyecto proyecto;
	public final static Map<String, Float> zoomOptions = new LinkedHashMap<>();
	public final static String zoom100 = "100%";

	static {
		zoomOptions.put("25%", 0.25f);
		zoomOptions.put("50%", 0.5f);
		zoomOptions.put("75%", 0.75f);
		zoomOptions.put(zoom100, 1.0f);
		zoomOptions.put("150%", 1.5f);
		zoomOptions.put("200%", 2.0f);
	}

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

	public void zoom(String zoom) {
		if (zoomOptions.containsKey(zoom))
			this.panel.setZoom(zoomOptions.get(zoom));
		else {
			float zoomFloat = Float.parseFloat(zoom.replace("%", ""));
			this.panel.setZoom(zoomFloat);
		}
	}

	/**
	 * Devuelve un thumbnail del diagrama actual.
	 * 
	 * @return
	 */
	public Image getImagen() {
		Rectangle rootFigureBounds = this.panel.getBounds();
		GC figureCanvasGC = new GC(this.canvas);

		Image image = new Image(this.canvas.getDisplay(),
				rootFigureBounds.width, rootFigureBounds.height);
		GC imageGC = new GC(image);

		imageGC.setBackground(figureCanvasGC.getBackground());
		imageGC.setForeground(figureCanvasGC.getForeground());
		imageGC.setFont(figureCanvasGC.getFont());
		imageGC.setLineStyle(figureCanvasGC.getLineStyle());
		imageGC.setLineWidth(figureCanvasGC.getLineWidth());
		// imageGC.setXORMode(figureCanvasGC.getXORMode());

		Graphics imgGraphics = new SWTGraphics(imageGC);
		this.panel.paint(imgGraphics);

		return image;
	}

	/**
	 * Devuelve la figura sobre la cual se dibuja la figura.
	 * @return
	 */
	public IFigure getPanel() {
		return this.panel;
	}
}
