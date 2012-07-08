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
	public final static Map<String, Float> zoomOptions = new LinkedHashMap<>();
	public final static String zoom100 = "100%";

	private ZoomContainer panel;
	private FigureCanvas canvas;
	private Proyecto proyecto;

	static {
		initZoomOptions();
	}

	private static void initZoomOptions() {
		float zoom = 0;
		while (zoom < ZoomContainer.MAX_ZOOM) {
			zoom += ZoomContainer.DELTA_ZOOM;
			if (zoom >= ZoomContainer.MIN_ZOOM) {
				String key = Integer.toString((int) (zoom * 100));
				zoomOptions.put(key + "%", zoom);
			}
		}
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
	 * 
	 * @return
	 */
	public IFigure getPanel() {
		return this.panel;
	}

	public String getZoom() {
		String zoom = Integer.toString((int) (this.panel.getZoom() * 100));
		return zoom + "%";
	}
}
