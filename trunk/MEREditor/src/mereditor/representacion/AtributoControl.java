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
		@SuppressWarnings("rawtypes")
		Figure figuraPadre = ((Control)this.getPadre()).getFigura();
		
		PolylineConnection connection = new PolylineConnection();
		ChopboxAnchor source = new ChopboxAnchor(figuraPadre);
		ChopboxAnchor destination = new ChopboxAnchor(this.figure);
		connection.setSourceAnchor(destination);
		connection.setTargetAnchor(source);

		contenedor.add(connection);
		
		contenedor.add(this.figure);
	}
}
