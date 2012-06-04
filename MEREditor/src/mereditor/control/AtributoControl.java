package mereditor.control;

import mereditor.interfaz.swt.figuras.AtributoFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;

import org.eclipse.draw2d.Figure;

public class AtributoControl extends Atributo implements Control<Atributo> {
	private AtributoFigure figure;

	public AtributoControl() {
		super();
		this.figure = new AtributoFigure(this);
	}

	@Override
	public Figura<Atributo> getFigura() {
		return this.figure;
	}

	@Override
	public void dibujar(Figure contenedor) {
		contenedor.add(this.figure);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;
			this.figure.conectarAtributo(atributoControl.getFigura());

			atributoControl.dibujar(contenedor);
			// Agregar atributo a los hijos.
			this.figure.agregarFiguraLoqueada(atributoControl.getFigura());
		}
	}
}
