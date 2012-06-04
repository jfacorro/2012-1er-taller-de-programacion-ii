package mereditor.representacion;

import mereditor.interfaz.swt.AtributoFigure;
import mereditor.interfaz.swt.Figura;
import mereditor.modelo.Atributo;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PolylineConnection;

public class AtributoControl extends Atributo implements Control<Atributo> {
	protected AtributoFigure figure;

	@Override
	public Figura<Atributo> getFigura() {
		return this.figure;
	}

	@Override
	public void dibujar(Figure contenedor) {
		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			PolylineConnection connection = new PolylineConnection();
			ChopboxAnchor source = new ChopboxAnchor(this.figure);
			ChopboxAnchor destination = new ChopboxAnchor(atributoControl.getFigura());
			connection.setSourceAnchor(destination);
			connection.setTargetAnchor(source);

			// Agregar conexion
			contenedor.add(connection);
			// Agregar atributo
			atributoControl.dibujar(contenedor);
			this.figure.agregar(atributoControl.getFigura());
		}

		contenedor.add(this.figure);
	}
}
