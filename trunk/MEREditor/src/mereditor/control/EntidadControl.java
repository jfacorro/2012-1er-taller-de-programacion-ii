package mereditor.control;

import mereditor.interfaz.swt.figuras.EntidadFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.Figure;

public class EntidadControl extends Entidad implements Control<Entidad> {
	private EntidadFigure figure;

	public EntidadControl() {
		super();
		this.figure = new EntidadFigure(this);
	}

	@Override
	public Figura<Entidad> getFigura() {
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
	}
}
