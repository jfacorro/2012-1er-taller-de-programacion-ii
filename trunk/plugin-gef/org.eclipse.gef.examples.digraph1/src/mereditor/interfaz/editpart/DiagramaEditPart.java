package mereditor.interfaz.editpart;

import java.util.List;

import mereditor.interfaz.policy.Digraph1XYLayoutEditPolicy;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * EditPart para el Diagrama
 * 
 */
public class DiagramaEditPart extends AbstractGraphicalEditPart {

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new Digraph1XYLayoutEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		FreeformLayer freeformLayer = new FreeformLayer();
		freeformLayer.setLayoutManager(new FreeformLayout());
		return freeformLayer;
	}

	@Override
	protected List<Entidad> getModelChildren() {
		List<Entidad> nodes = ((Diagrama) getModel()).getNodes();
		return nodes;
	}

}
