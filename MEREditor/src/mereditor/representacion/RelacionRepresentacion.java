package mereditor.representacion;

import org.eclipse.draw2d.Figure;

import mereditor.modelo.Relacion;
import mereditor.representacion.base.Representacion;

public class RelacionRepresentacion extends Representacion<Relacion> {

	@Override
	public void dibujar(Figure contenedor) {
		contenedor.add(this.figura);
	}
}
