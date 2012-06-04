package mereditor.interfaz.swt.listeners;

import mereditor.interfaz.swt.figuras.Figura;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class MovimientoControlador implements FigureListener {
	protected Figura<?> figura;
	private Point current;

	public MovimientoControlador(Figura<?> figura) {
		this.figura = figura;
		this.current = this.figura.getLocation();
		this.figura.addFigureListener(this);
	}

	@Override
	public void figureMoved(IFigure movedFigure) {
		Dimension delta = this.figura.getLocation().getDifference(this.current);

		for (Figure figure : this.figura.getFigurasLoqueadas()) {
			figure.setBounds(figure.getBounds().getTranslated(delta.width, delta.height));
		}
		
		this.current = this.figura.getLocation();
	}
}
