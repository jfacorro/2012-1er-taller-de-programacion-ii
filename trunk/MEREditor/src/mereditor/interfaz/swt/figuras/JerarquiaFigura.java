package mereditor.interfaz.swt.figuras;

import mereditor.modelo.Jerarquia;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;

public class JerarquiaFigura extends Figura<Jerarquia> {

	public JerarquiaFigura(Jerarquia componente) {
		super(componente);
	}
	
	@Override
	protected void init() {
		super.init();
		this.setSize(new Dimension(this.getSize().width, 10));
	}

	public void conectarGenerica(Figure figura, Figure generica) {
		PolylineConnection connection = (PolylineConnection)Figura.conectar(figura, generica);
		PolygonDecoration polyDecoration = new PolygonDecoration();
		connection.setTargetDecoration(polyDecoration);
		this.getParent().add(connection);
	}
	
	public void conectarDerivada(Figure figura, Figure derivada) {
		PolylineConnection connection = (PolylineConnection)Figura.conectar(figura, derivada);
		this.getParent().add(connection);
	}
}
