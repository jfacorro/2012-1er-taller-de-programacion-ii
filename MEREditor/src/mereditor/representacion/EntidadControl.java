package mereditor.representacion;

import mereditor.interfaz.swt.EntidadFigure;
import mereditor.interfaz.swt.Figura;
import mereditor.modelo.Entidad;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.Figure;

public class EntidadControl extends Entidad implements Control<Entidad> {
	protected EntidadFigure figure;

	@Override
	public Figura<Entidad> getFigura() {
		return this.figure;
	}

	@Override
	public void dibujar(Figure contenedor) {
		contenedor.add(this.figure);
	}
}
