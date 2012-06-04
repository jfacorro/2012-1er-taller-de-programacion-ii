package mereditor.representacion;

import mereditor.modelo.Relacion;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.Figure;

public class RelacionRepresentacion extends Control<Relacion> {

	@Override
	public void dibujar(Figure contenedor) {
		contenedor.add(this.figura);
	}
}
