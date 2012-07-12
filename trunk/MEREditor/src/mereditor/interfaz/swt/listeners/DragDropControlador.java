package mereditor.interfaz.swt.listeners;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

/**
 * Se encarga del arrastre de las figuras.
 */
public class DragDropControlador extends MouseMotionListener.Stub implements MouseListener {
	private Point startPoint;

	public DragDropControlador(Figure figure) {
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.startPoint = e.getLocation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Figure figure = (Figure) e.getSource();	
		
		if (SeleccionControlador.isSelected(figure))
			for(IFigure figura : SeleccionControlador.getSelected())
				this.actualizarPosicion((Figure)figura, this.startPoint, e.getLocation());
		else {
			this.actualizarPosicion(figure, this.startPoint, e.getLocation());
			SeleccionControlador.deselectAll();
		}
			

		this.startPoint = new Point(e.getLocation());
	}

	public void actualizarPosicion(Figure figure, Point start, Point current) {
		if (start != null && !start.equals(current)) {
			Dimension delta = start.getDifference(current);
			figure.setBounds(figure.getBounds().getTranslated(-delta.width, -delta.height));
		}
	}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
