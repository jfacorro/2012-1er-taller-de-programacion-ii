package mereditor.control;

import mereditor.interfaz.swt.figuras.AtributoFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;
import mereditor.control.base.Control;

import org.eclipse.draw2d.Figure;

public class AtributoControl extends Atributo implements Control<Atributo> {
	protected AtributoFigure figure;

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
