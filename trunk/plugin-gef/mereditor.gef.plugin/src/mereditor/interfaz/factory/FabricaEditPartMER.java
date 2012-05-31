package mereditor.interfaz.factory;

import mereditor.interfaz.editpart.DiagramaEditPart;
import mereditor.interfaz.editpart.EntidadEditPart;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Fabrica de EditParts para el diagrama
 * 
 */
public class FabricaEditPartMER implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart editPart;

		if (model instanceof Diagrama) {
			editPart = new DiagramaEditPart();
		} else if (model instanceof Entidad) {
			editPart = new EntidadEditPart();
		} else {
			throw new IllegalArgumentException();
		}

		editPart.setModel(model);

		return editPart;
	}
}
