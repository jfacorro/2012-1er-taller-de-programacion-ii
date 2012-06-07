package mereditor.control;

import java.util.HashMap;
import java.util.Map;

import mereditor.interfaz.swt.figuras.EntidadFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.Figure;

public class EntidadControl extends Entidad implements Control<Entidad> {
	protected Map<String, EntidadFigure> figures = new HashMap<>();

	@Override
	public Figura<Entidad> getFigura(String id) {
		if (!this.figures.containsKey(id))
			this.figures.put(id, new EntidadFigure(this));

		return this.figures.get(id);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		EntidadFigure figure = (EntidadFigure) this.getFigura(idDiagrama);
		contenedor.add(figure);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			figure.conectarAtributo(atributoControl.getFigura(idDiagrama));
			atributoControl.dibujar(contenedor, idDiagrama);
			figure.agregarFiguraLoqueada(atributoControl.getFigura(idDiagrama));
		}
	}

	public Map<String, EntidadFigure> getFiguras() {
		return this.figures;
	}
}
