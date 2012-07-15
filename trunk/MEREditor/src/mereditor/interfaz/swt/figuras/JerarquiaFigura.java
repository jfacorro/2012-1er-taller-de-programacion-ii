package mereditor.interfaz.swt.figuras;

import mereditor.modelo.Jerarquia;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;

public class JerarquiaFigura extends Figura<Jerarquia> {

	public JerarquiaFigura(Jerarquia componente) {
		super(componente);
		this.setRepresentacion(EstilosFiguras.get(Jerarquia.class,
				this.componente.getTipo()));
	}

	@Override
	protected void init() {
		super.init();
		this.setSize(new Dimension(this.getSize().width, 5));
	}

	public void conectarGenerica(Figure figura, Figure generica) {
		PolylineConnection connection = (PolylineConnection) Figura.conectarChopbox(
				figura, generica);

		this.applyLineStyle(connection);

		connection.setConnectionRouter(new ManhattanConnectionRouter());
		connection.setTargetDecoration(new PolygonDecoration());
		this.getParent().add(connection);
	}

	public void conectarDerivada(Figure figura, Figure derivada) {
		PolylineConnection connection = (PolylineConnection) Figura.conectarChopbox(
				figura, derivada);
		connection.setLineStyle(this.lineStyle);
		connection.setConnectionRouter(new ManhattanConnectionRouter());
		this.getParent().add(connection);
	}

	@Override
	public void actualizar() {

	}
}
