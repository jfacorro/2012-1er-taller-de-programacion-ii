package mereditor.interfaz.swt.figuras;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.representacion.Representacion;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonShape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class RelacionFigure extends Figura<Relacion> {
	private PolygonShape rombo;

	public RelacionFigure(Relacion relacion, Dimension dim) {
		super(relacion, dim);
	}

	public RelacionFigure(Relacion relacion) {
		super(relacion);
	}

	@Override
	protected void init() {
		super.init();
		this.setBackgroundColor(null);
		this.setBorder(null);
		this.setOpaque(false);

		// Quitar todos los controles del padre
		this.removeAll();

		this.rombo = new PolygonShape();
		this.rombo.setLayoutManager(new BorderLayout());
		this.rombo.setBackgroundColor(this.getBackColor());
		this.rombo.setOpaque(false);
		this.rombo.add(this.lblName, BorderLayout.CENTER);
		this.add(rombo, BorderLayout.CENTER);
		this.generarPuntos();

		this.lblName.setText(this.componente.getNombre());
	}

	public void update() {
		// this.lblName.setText(this.componente.getNombre());
	}

	/**
	 * Calcula y genera los puntos para formar el rombo
	 */
	private void generarPuntos() {
		Dimension dim = this.getSize();
		this.rombo.removeAllPoints();
		this.rombo.setSize(dim);
		int w = dim.width - 2;
		int h = dim.height - 2;
		this.rombo.addPoint(new Point(w / 2, 0));
		this.rombo.addPoint(new Point(w, h / 2));
		this.rombo.addPoint(new Point(w / 2, h));
		this.rombo.addPoint(new Point(0, h / 2));
	}

	@Override
	public void setRepresentacion(Representacion representacion) {
		super.setRepresentacion(representacion);
		// Regenerar los puntos del rombo
		this.generarPuntos();
	}

	public void conectarEntidad(Figura<Entidad> figura, String label) {
		Connection conecccion = Figura.conectar(this, figura);

		// Agregad cardinalidad y rol
		Label lblCardinalidad = new Label(label);
		conecccion.add(lblCardinalidad, new MidpointLocator(conecccion, 0));

		this.getParent().add(conecccion);
	}

	public void conectarAtributo(Figura<Atributo> figura) {
		this.getParent().add(Figura.conectar(this, figura));
	}
}
