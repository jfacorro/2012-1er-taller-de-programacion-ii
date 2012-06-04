package mereditor.representacion;

import mereditor.interfaz.swt.Figura;
import mereditor.interfaz.swt.RelacionFigure;
import mereditor.modelo.Relacion;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.Figure;

public class RelacionControl extends Relacion implements Control<Relacion> {
	protected RelacionFigure figure;

	@Override
	public Figura<Relacion> getFigura() {
		return this.figure;
	}

	@Override
	public void dibujar(Figure contenedor) {
		contenedor.add(this.figure);

		for(EntidadRelacion entidadRelacion : this.participantes) {
			EntidadControl entidadControl = (EntidadControl)entidadRelacion.getEntidad();			
			this.figure.conectarEntidad(entidadControl.getFigura(), entidadRelacion);			
			this.figure.agregar(entidadControl.getFigura());
		}
	}
}
