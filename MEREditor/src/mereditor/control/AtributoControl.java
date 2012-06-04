package mereditor.control;

import java.util.HashMap;
import java.util.Map;

import mereditor.interfaz.swt.figuras.AtributoFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;

import org.eclipse.draw2d.Figure;

public class AtributoControl extends Atributo implements Control<Atributo> {
	private Map<String, AtributoFigure> figures = new HashMap<>();

	@Override
	public Figura<Atributo> getFigura(String idDiagrama) {
		if (!this.figures.containsKey(idDiagrama))
			this.figures.put(idDiagrama, new AtributoFigure(this));

		return this.figures.get(idDiagrama);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		AtributoFigure figure = (AtributoFigure) this.getFigura(idDiagrama);
		contenedor.add(figure);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;
			figure.conectarAtributo(atributoControl.getFigura(idDiagrama));

			atributoControl.dibujar(contenedor, idDiagrama);
			// Agregar atributo a los hijos.
			figure.agregarFiguraLoqueada(atributoControl
					.getFigura(idDiagrama));
		}
	}
}