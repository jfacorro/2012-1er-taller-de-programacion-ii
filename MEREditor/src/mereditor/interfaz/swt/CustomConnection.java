package mereditor.interfaz.swt;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;

public class CustomConnection extends PolylineConnection {
	@Override
	public void anchorMoved(ConnectionAnchor anchor) {
		super.anchorMoved(anchor);
		Point p = new Point();
		anchor.getLocation(p);
		this.setLocation(p);
	}
}
