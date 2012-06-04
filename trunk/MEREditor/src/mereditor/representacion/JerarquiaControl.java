package mereditor.representacion;

import mereditor.interfaz.swt.Figura;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PolylineConnection;

public class JerarquiaControl extends Jerarquia implements Control<Jerarquia> {

	@Override
	public Figura<Jerarquia> getFigura() {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor) {
		Figure generica = ((EntidadControl)this.generica).getFigura();
		
		for(Entidad derivada : this.derivadas)
		{
			Figure derivadaFigure = ((Control<?>)derivada).getFigura();
			PolylineConnection connection = new PolylineConnection();
			ChopboxAnchor anchorGenerica = new ChopboxAnchor(generica);
			ChopboxAnchor anchorDerivada = new ChopboxAnchor(derivadaFigure);
			connection.setSourceAnchor(anchorDerivada);
			connection.setTargetAnchor(anchorGenerica);

			contenedor.add(connection);
		}		
	}
}
