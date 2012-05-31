package mereditor.interfaz.swt;

import mereditor.modelo.Relacion;

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
		this.rombo.setBackgroundColor(this.getBackColor());
		this.rombo.setOpaque(false);
		this.rombo.add(this.lblName);
		this.add(rombo);
		this.generatePoints();
	}
	
	public void update() {
		//this.lblName.setText(this.componente.getNombre());
	}
	
	private void generatePoints() {
		this.generatePoints(this.getSize());
	}
	
	private void generatePoints(Dimension dim) {
		this.rombo.setSize(dim);
		int w = dim.width;
		int h = dim.height;
		this.rombo.addPoint(new Point(w / 2, 0));
		this.rombo.addPoint(new Point(w, h / 2));
		this.rombo.addPoint(new Point(w / 2 , h));
		this.rombo.addPoint(new Point(0, h / 2));
	}
	
//	@Override
//	public void setBounds(Rectangle rect) {
//		if(!this.getBounds().getSize().equals(rect.getSize()))
//			this.addPoints(rect);
//
//		super.setBounds(rect);
//	} 
}
