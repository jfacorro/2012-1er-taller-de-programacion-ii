package mereditor.control;

import java.util.HashMap;
import java.util.Map;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.interfaz.swt.figuras.RelacionFigure;
import mereditor.modelo.Atributo;
import mereditor.modelo.Relacion;

import org.eclipse.draw2d.Figure;

public class RelacionControl extends Relacion implements Control<Relacion> {
	private Map<String, RelacionFigure> figures = new HashMap<>();

	@Override
	public Figura<Relacion> getFigura(String idDiagrama) {
		if (!this.figures.containsKey(idDiagrama))
			this.figures.put(idDiagrama, new RelacionFigure(this));

		return this.figures.get(idDiagrama);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		RelacionFigure figure = (RelacionFigure) this.getFigura(idDiagrama);
		contenedor.add(figure);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			figure.conectarAtributo(atributoControl.getFigura(idDiagrama));
			atributoControl.dibujar(contenedor, idDiagrama);
			figure.agregarFiguraLoqueada(atributoControl.getFigura(idDiagrama));
		}

		for (EntidadRelacion entidadRelacion : this.participantes) {
			EntidadControl entidadControl = (EntidadControl) entidadRelacion
					.getEntidad();
			figure.conectarEntidad(entidadControl.getFigura(idDiagrama),
					entidadRelacion.toString());
		}
	}
}
