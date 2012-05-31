package mereditor.interfaz.editpart;

import mereditor.interfaz.figure.EntidadFigura;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * EditPart para la Entidad
 * 
 * @author hordia
 * 
 */
public class EntidadEditPart extends AbstractGraphicalEditPart {

	@Override
	public boolean isSelectable() {
		// FIXME
		return false;
	}

	@Override
	protected void createEditPolicies() {
		// FIXME
	}

	@Override
	protected IFigure createFigure() {
		String nombre = ((Entidad) getModel()).getNombre();
		int id = ((Entidad) getModel()).getId();
		Point posicionIncial = new Point(200 * id, 50);
		return new EntidadFigura(nombre, posicionIncial);
	}

	@Override
	protected void refreshVisuals() {
		EntidadFigura nodeFigure = (EntidadFigura) getFigure();
		Point location = nodeFigure.getRectangleFigure().getLocation();
		Dimension size = nodeFigure.getRectangleFigure().getSize();
		DiagramaEditPart graph = (DiagramaEditPart) getParent();
		Rectangle constraint = new Rectangle(location, size);
		graph.setLayoutConstraint(this, nodeFigure, constraint);
	}
}