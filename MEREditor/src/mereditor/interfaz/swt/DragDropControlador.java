package mereditor.interfaz.swt;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Point;

public class DragDropControlador implements MouseListener, MouseMotionListener {
	private Point startPoint;

	public DragDropControlador() {

	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if (this.startPoint != null) {
			this.actualizarPosicion((Figure)evt.getSource(), this.startPoint, evt.getLocation());
			this.startPoint = new Point(evt.getLocation());
		}
	}

	@Override
	public void mouseEntered(MouseEvent evt) {}

	@Override
	public void mouseExited(MouseEvent evt) {}

	@Override
	public void mouseHover(MouseEvent evt) {}

	@Override
	public void mouseMoved(MouseEvent evt) {}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent e) {
		this.startPoint = new Point(e.getLocation());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.startPoint = null;
	}
	
	public void actualizarPosicion(Figure figure, Point start, Point current) {
		if (!start.equals(current)) {
			int dx = current.x - start.x;
			int dy = current.y - start.y;

			Point location = figure.getLocation();
			figure.setLocation(new Point(location.x + dx, location.y + dy));
		}		
	}
}
