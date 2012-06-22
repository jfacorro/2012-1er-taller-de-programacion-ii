package mereditor.interfaz.swt;

import mereditor.control.Proyecto;
import mereditor.interfaz.swt.figuras.ZoomContainer;

import org.eclipse.draw2d.FigureCanvas;

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

	public void actualizar() {
		this.panel.removeAll();
		this.proyecto.getDiagramaActual().dibujar(this.panel);
	}

	public void zoomOut() {
		this.panel.setZoom((float)(this.panel.getZoom() - 0.1));		
	}
	
	public void zoomIn() {
		this.panel.setZoom((float)(this.panel.getZoom() + 0.1));		
	}
}
