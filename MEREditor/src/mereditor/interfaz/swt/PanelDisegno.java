package mereditor.interfaz.swt;

import mereditor.control.DiagramaControl;
import mereditor.modelo.Diagrama;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;

public class PanelDisegno {
	private Figure panel;
	private FigureCanvas canvas;
	private DiagramaControl diagrama;

	public PanelDisegno(FigureCanvas canvas) {
		this.panel = new Figure();
		this.canvas = canvas;
		this.canvas.setContents(this.panel);
	}

	public void setDiagrama(Diagrama diagrama) {
		if (!DiagramaControl.class.isInstance(diagrama))
			throw new RuntimeException("El diagrama debe ser una instancia de DiagramaControl.");

		this.setDiagrama((DiagramaControl) diagrama);
	}

	public void setDiagrama(DiagramaControl diagrama) {
		this.diagrama = diagrama;
		this.actualizar();
	}

	public void actualizar() {
		this.panel.removeAll();
		this.diagrama.dibujar(this.panel);
	}
}
