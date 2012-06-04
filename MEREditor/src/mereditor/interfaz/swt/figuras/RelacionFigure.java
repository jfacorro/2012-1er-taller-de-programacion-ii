package mereditor.interfaz.swt.figuras;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.control.base.Representacion;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
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

		LayoutManager layout = new BorderLayout();
		this.rombo = new PolygonShape();
		this.rombo.setLayoutManager(layout);
		this.rombo.setBackgroundColor(this.getBackColor());
		this.rombo.setOpaque(false);
		this.rombo.add(this.lblName);
		layout.setConstraint(this.lblName, BorderLayout.CENTER);
		this.add(rombo);
		this.generatePoints();

		this.lblName.setText(this.componente.getNombre());
	}
	
	public void update() {
		//this.lblName.setText(this.componente.getNombre());
	}
	
	private void generatePoints() {
		this.generatePoints(this.getSize());
	}
	
	private void generatePoints(Dimension dim) {
		this.rombo.removeAllPoints();
		this.rombo.setSize(dim);
		int w = dim.width;
		int h = dim.height;
		this.rombo.addPoint(new Point(w / 2, 0));
		this.rombo.addPoint(new Point(w, h / 2));
		this.rombo.addPoint(new Point(w / 2 , h));
		this.rombo.addPoint(new Point(0, h / 2));
	}
	
	@Override
	public void setRepresentacion(Representacion representacion) {
		super.setRepresentacion(representacion);
		// Regenerar los puntos del rombo
		this.generatePoints();
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
