package mereditor.interfaz.swt.editores;

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import mereditor.modelo.Entidad;

public class EntidadTabla extends Tabla<Entidad> {

	public EntidadTabla(Composite parent) {
		super(parent);
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.NOMBRE);
	}

	@Override
	protected void initEditorsCeldas(Table table) {
		this.editoresCeldas.add(new TextCellEditor(table));
	}

	@Override
	protected String getTextoColumna(Entidad element, int columnIndex) {
		return element.getNombre();
	}

	@Override
	protected Object getValorCelda(Entidad element, String property) {
		return element.getNombre();
	}

	@Override
	protected void setValorCelda(Entidad element, String property, Object value) {
		element.setNombre(value.toString());
	}

	@Override
	protected Entidad nuevoElemento() {
		throw new RuntimeException("No se puede generar una nueva Entidad desde la tabla.");
	}
}
