package mereditor.representacion;

import org.eclipse.draw2d.Figure;

import mereditor.modelo.Relacion;
import mereditor.representacion.base.ComponenteRepresentacion;

public class RelacionRepresentacion extends ComponenteRepresentacion<Relacion> {

	@Override
	public void dibujar(Figure contenedor) {
		contenedor.add(this.figura);
	}
}
