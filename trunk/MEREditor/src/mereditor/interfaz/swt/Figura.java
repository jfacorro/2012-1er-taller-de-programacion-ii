package mereditor.interfaz.swt;

import mereditor.modelo.base.Componente;
import mereditor.representacion.base.Representacion;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class Figura<T extends Componente> extends Figure {
	public static Color defaultBackColor = new Color(null, 255, 255, 206);
	public static Color defaultLineColor = new Color(null, 0, 0, 0);
	public static Dimension defaultSize = new Dimension(80, 50);

	protected T componente;
	protected Label lblName;

	public Figura(T componente, Dimension dim) {
		this(componente);
		this.setSize(dim);
	}

	public Figura(T componente) {
		this.componente = componente;
		this.init();
	}

	protected void init() {
		LayoutManager layout = new BorderLayout();
		this.setLayoutManager(layout);
		this.setBorder(new LineBorder(this.getLineColor(), 1));
		this.setBackgroundColor(this.getBackColor());
		this.setOpaque(true);
		this.setSize(Figura.defaultSize);

		this.lblName = new Label();
		this.add(lblName);
		layout.setConstraint(this.lblName, BorderLayout.CENTER);

		// Agregar controlador para arrastre
		new DragDropControlador(this);
	}

	protected Color getBackColor() {
		return Figura.defaultBackColor;
	}

	protected Color getLineColor() {
		return Figura.defaultLineColor;
	}

	public void setRepresentacion(Representacion representacion) {
		if (representacion != null) {
			Rectangle rectangle = new Rectangle();
			rectangle.setX(representacion.getX());
			rectangle.setY(representacion.getY());
			rectangle.setWidth(representacion.getAncho());
			rectangle.setHeight(representacion.getAlto());

			this.setBounds(rectangle);
		}
	}
}
