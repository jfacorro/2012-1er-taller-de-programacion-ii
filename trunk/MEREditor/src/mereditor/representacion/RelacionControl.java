package mereditor.representacion;

import mereditor.interfaz.swt.Figura;
import mereditor.interfaz.swt.RelacionFigure;
import mereditor.modelo.Relacion;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PolylineConnection;

public class RelacionControl extends Relacion implements Control<Relacion> {
	protected RelacionFigure figure;

	@Override
	public Figura<Relacion> getFigura() {
		return this.figure;
	}

	@Override
	public void dibujar(Figure contenedor) {
		for(EntidadRelacion entidadRelacion : this.participantes) {
			EntidadControl entidad = (EntidadControl) entidadRelacion.getEntidad();
			
			Figure destination = entidad.getFigura();
			PolylineConnection connection = new PolylineConnection();
			ChopboxAnchor anchorGenerica = new ChopboxAnchor(this.figure);
			ChopboxAnchor anchorDerivada = new ChopboxAnchor(destination);
			connection.setSourceAnchor(anchorDerivada);
			connection.setTargetAnchor(anchorGenerica);
			
			contenedor.add(connection);
		}

		contenedor.add(this.figure);
	}
}
