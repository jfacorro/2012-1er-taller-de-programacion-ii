package mereditor.interfaz.swt.editores;

import mereditor.interfaz.swt.Principal;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Relacion.EntidadRelacion;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class EntidadRelacionTabla extends Tabla<EntidadRelacion> {
	private Relacion relacion;

	public EntidadRelacionTabla(Composite parent, Relacion relacion) {
		super(parent);
		this.relacion = relacion;
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.ENTIDAD);
		this.columnas.add(Editor.ROL);
		this.columnas.add(Editor.CARDINALIDAD_MIN);
		this.columnas.add(Editor.CARDINALIDAD_MAX);
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
		return (String) this.getValorCelda(element, this.columnas.get(columnIndex));
	}

	@Override
	protected Object getValorCelda(EntidadRelacion element, String property) {
		switch (property) {
		case Editor.ENTIDAD:
			return element.getEntidad().getNombre();
		case Editor.ROL:
			return element.getRol();
		case Editor.CARDINALIDAD_MIN:
			return element.getCardinalidadMinima();
		case Editor.CARDINALIDAD_MAX:
			return element.getCardinalidadMaxima();
		default:
			throw new RuntimeException("Propiedad invalida '" + property + "' al obtener su valor.");
		}
	}

	@Override
	protected void setValorCelda(EntidadRelacion element, String property, Object value) {
		switch (property) {
		case Editor.ENTIDAD:
			Diagrama diagrama = (Diagrama) element.getRelacion().getPadre();
			Entidad entidad = diagrama.getEntidadByNombre(value.toString());
			if (entidad != null)
				element.setEntidad(entidad);
			else
				Principal.getInstance().error("No existe la entidad '" + value.toString());
			break;
		case Editor.ROL:
			element.setRol(value.toString());
			break;
		case Editor.CARDINALIDAD_MIN:
			element.setCardinalidadMinima(value.toString());
			break;
		case Editor.CARDINALIDAD_MAX:
			element.setCardinalidadMaxima(value.toString());
			break;
		default:
			throw new RuntimeException("Propiedad invalida '" + property + "' al establecer su valor.");
		}
	}

	@Override
	protected EntidadRelacion nuevoElemento() {
		return relacion.new EntidadRelacion(relacion);
	}

	@Override
	protected void abrirEditor(EntidadRelacion elemento) {
		// TODO: implementar clase EntidadRelacionEditor
		//new EntidadRelacionEditor(elemento).open();		
	}
}
