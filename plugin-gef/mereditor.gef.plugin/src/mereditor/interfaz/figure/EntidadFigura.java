package mereditor.interfaz.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Figura para Entidad
 * 
 * @author hordia
 * 
 */
public class EntidadFigura extends Figure {

	private Label label;
	private RectangleFigure rectangleFigure;

	public EntidadFigura(String nombre, Point posicionIncial) {
		int ancho = 120;
		int alto = 100;

		setLayoutManager(new XYLayout());

		this.rectangleFigure = new RectangleFigure();
		this.rectangleFigure.setBackgroundColor(ColorConstants.lightGray);

		this.rectangleFigure.setLocation(posicionIncial);

		this.rectangleFigure.setSize(new Dimension(ancho, alto));
		add(this.rectangleFigure);

		this.label = new Label();
		this.label.setText(nombre);
		// this.label.setLocation(new Point(100, 100));
		add(this.label);
	}

	@Override
	public void paintFigure(Graphics g) {
		Rectangle r = getBounds().getCopy();
		setConstraint(this.rectangleFigure, new Rectangle(0, 0, r.width,
				r.height));
		setConstraint(this.label, new Rectangle(0, 0, r.width, r.height));
		this.rectangleFigure.invalidate();
		this.label.invalidate();
	}

	public RectangleFigure getRectangleFigure() {
		return this.rectangleFigure;
	}
}
