package mereditor.interfaz.swt.editores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import mereditor.interfaz.swt.Principal;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Relacion.EntidadRelacion;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class EntidadRelacionTabla extends Tabla<EntidadRelacion> {
	private Relacion relacion;
	private List<String> options;

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
		Set<Entidad> entidades = Principal.getInstance().getProyectoProxy()
				.getEntidadesDiagrama();

		this.options = new ArrayList<String>();
		for (Entidad entidad : entidades)
			options.add(entidad.getNombre());

		Collections.sort(options);

		this.editoresCeldas.add(new ComboBoxCellEditor(table, options
				.toArray(new String[options.size()]), SWT.READ_ONLY));
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new TextCellEditor(table));
	}

	@Override
	protected String getTextoColumna(EntidadRelacion element, int columnIndex) {
		String property = this.columnas.get(columnIndex);
		switch (property) {
		case Editor.ENTIDAD:
			return element.getEntidad().getNombre();
		default:
			return (String) this.getValorCelda(element, property);
		}
	}

	@Override
	protected Object getValorCelda(EntidadRelacion element, String property) {
		switch (property) {
		case Editor.ENTIDAD:
			return options.indexOf(element.getEntidad().getNombre());
		case Editor.ROL:
			return element.getRol();
		case Editor.CARDINALIDAD_MIN:
			return element.getCardinalidadMinima();
		case Editor.CARDINALIDAD_MAX:
			return element.getCardinalidadMaxima();
		default:
			throw new RuntimeException("Propiedad invalida '" + property
					+ "' al obtener su valor.");
		}
	}

	@Override
	protected void setValorCelda(EntidadRelacion element, String property,
			Object value) {
		switch (property) {
		case Editor.ENTIDAD:
			Diagrama diagrama = (Diagrama) element.getRelacion().getPadre();
			String nombre = this.options.get((int) value);
			Entidad entidad = diagrama.getEntidadByNombre(nombre);
			if (entidad != null)
				element.setEntidad(entidad);
			else
				Principal.getInstance().error(
						"No existe la entidad '" + value.toString());
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
			throw new RuntimeException("Propiedad invalida '" + property
					+ "' al establecer su valor.");
		}
	}

	@Override
	protected EntidadRelacion nuevoElemento() {
		return relacion.new EntidadRelacion(relacion);
	}

	@Override
	protected void abrirEditor(EntidadRelacion elemento) {
		// TODO: implementar clase EntidadRelacionEditor
		// new EntidadRelacionEditor(elemento).open();
	}
}
