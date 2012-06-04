package mereditor.representacion;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.interfaz.swt.figuras.RelacionFigure;
import mereditor.modelo.Atributo;
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
		
		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			this.figure.conectarAtributo(atributoControl.getFigura());
			atributoControl.dibujar(contenedor);
			this.figure.agregarFiguraLoqueada(atributoControl.getFigura());
		}

		for(EntidadRelacion entidadRelacion : this.participantes) {
			EntidadControl entidadControl = (EntidadControl)entidadRelacion.getEntidad();			
			this.figure.conectarEntidad(entidadControl.getFigura(), entidadRelacion.toString());
		}
	}
}
