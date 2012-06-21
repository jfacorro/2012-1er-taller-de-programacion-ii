package mereditor.interfaz.swt;

import mereditor.control.Proyecto;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;

public class PanelDisegno {
	private Figure panel;
	private FigureCanvas canvas;
	private Proyecto proyecto;

	public PanelDisegno(FigureCanvas canvas, Proyecto proyecto) {
		this.panel = new Figure();
		this.canvas = canvas;
		this.canvas.setContents(this.panel);
		this.proyecto = proyecto;
	}

	public void actualizar() {
		this.panel.removeAll();
		this.proyecto.getDiagramaActual().dibujar(this.panel);
	}
}
