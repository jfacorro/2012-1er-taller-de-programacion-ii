package mereditor.interfaz.swt.editores;

import mereditor.interfaz.swt.Principal;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion.EntidadRelacion;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class EntidadRelacionTabla extends Tabla<EntidadRelacion> {

	public EntidadRelacionTabla(Composite parent) {
		super(parent);
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(RelacionEditor.ENTIDAD);
		this.columnas.add(RelacionEditor.ROL);
		this.columnas.add(RelacionEditor.CARDINALIDAD_MIN);
		this.columnas.add(RelacionEditor.CARDINALIDAD_MAX);
	}

	@Override
	protected void initEditorsCeldas(Table table) {
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new TextCellEditor(table));
	}

	@Override
	protected String getTextoColumna(EntidadRelacion element, int columnIndex) {
		switch (this.columnas.get(columnIndex)) {
		case RelacionEditor.ENTIDAD:
			return element.getEntidad().getNombre();
		case RelacionEditor.ROL:
			return element.getRol();
		case RelacionEditor.CARDINALIDAD_MIN:
			return element.getCardinalidadMinima();
		case RelacionEditor.CARDINALIDAD_MAX:
			return element.getCardinalidadMaxima();
		default:
			return null;
		}
	}

	@Override
	protected Object getValorCelda(EntidadRelacion element, String property) {
		switch (property) {
		case RelacionEditor.ENTIDAD:
			return element.getEntidad().getNombre();
		case RelacionEditor.ROL:
			return element.getRol();
		case RelacionEditor.CARDINALIDAD_MIN:
			return element.getCardinalidadMinima();
		case RelacionEditor.CARDINALIDAD_MAX:
			return element.getCardinalidadMaxima();
		default:
			throw new RuntimeException("Propiedad invalida '" + property + "' al obtener su valor.");
		}
	}

	@Override
	protected void setValorCelda(EntidadRelacion element, String property, Object value) {
		switch (property) {
		case RelacionEditor.ENTIDAD:
			Diagrama diagrama = (Diagrama) element.getRelacion().getPadre();
			Entidad entidad = diagrama.getEntidadByNombre(value.toString());
			if (entidad != null)
				element.setEntidad(entidad);
			else
				Principal.getInstance().error("No existe la entidad '" + value.toString());
			break;
		case RelacionEditor.ROL:
			element.setRol(value.toString());
			break;
		case RelacionEditor.CARDINALIDAD_MIN:
			element.setCardinalidadMinima(value.toString());
			break;
		case RelacionEditor.CARDINALIDAD_MAX:
			element.setCardinalidadMaxima(value.toString());
			break;
		default:
			throw new RuntimeException("Propiedad invalida '" + property + "' al establecer su valor.");
		}
	}
}
