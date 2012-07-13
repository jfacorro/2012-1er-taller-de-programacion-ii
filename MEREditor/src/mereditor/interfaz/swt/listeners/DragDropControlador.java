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
public class DragDropControlador extends MouseMotionListener.Stub implements
		MouseListener {
	private static Point startPoint;

	public DragDropControlador(Figure figure) {
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startPoint = e.getLocation();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDoubleClicked(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		SeleccionControlador.startedDragging();

		for (IFigure figura : SeleccionControlador.getSelected())
			this.actualizarPosicion((Figure) figura, startPoint,
					e.getLocation());

		startPoint = e.getLocation().getCopy();
	}

	public void actualizarPosicion(Figure figure, Point src, Point dest) {
		if (dest != null && src != null && !dest.equals(src)) {
			Dimension diff = dest.getDifference(src);
			figure.translate(diff.width, diff.height);
		}
	}
}
