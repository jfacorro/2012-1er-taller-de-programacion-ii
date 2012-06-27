package mereditor.interfaz.swt.editores;

import mereditor.control.AtributoControl;
import mereditor.interfaz.swt.dialogs.AgregarIdentificadorDialog;
import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;

public class AtributosTabla extends Tabla<Atributo> {

	public AtributosTabla(Composite parent) {
		super(parent);
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.NOMBRE);
		this.columnas.add(Editor.TIPO);
	}

	@Override
	protected void initEditorsCeldas(Table table) {
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new ComboBoxCellEditor(table, Editor.TiposAtributo, SWT.READ_ONLY));
	}

	@Override
	protected String getTextoColumna(Object element, int columnIndex) {
		Atributo atributo = (Atributo) element;

		switch (this.columnas.get(columnIndex)) {
		case Editor.NOMBRE:
			return atributo.getNombre();
		case Editor.TIPO:
			return atributo.getTipo().name();
		default:
			return null;
		}
	}

	@Override
	protected Object getValorCelda(Object element, String property) {
		Atributo atr = (Atributo) element;

		switch (property) {
		case EntidadEditor.NOMBRE:
			return atr.getNombre();
		case EntidadEditor.TIPO:
			return atr.getTipo().ordinal();
		default:
			return null;
		}
	}

	@Override
	protected void setValorCelda(Object element, String property, Object value) {
		if (element instanceof Item)
			element = ((Item) element).getData();

		Atributo atributo = (Atributo) element;
		switch (property) {
		case EntidadEditor.NOMBRE:
			atributo.setNombre((String) value);
			break;
		case EntidadEditor.TIPO:
			atributo.setTipo(TipoAtributo.class.getEnumConstants()[(int) value]);
			break;
		}

		refresh();
	}

	// Agregar un nuevo atributo cuando se hace click sobre el boton "Nuevo"
	public final SelectionListener nuevo = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			AtributoControl atributo = new AtributoControl();
			atributo.setNombre("Nombre");
			atributo.setTipo(TipoAtributo.CARACTERIZACION);

			elementos.add(atributo);

			refresh();
		}
	};

	// Eliminar atributo
	public final SelectionListener eliminar = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (selection != null) {
				AtributoControl atributo = (AtributoControl) selection.getFirstElement();

				elementos.remove(atributo);
				elementosEliminados.add(atributo);

				refresh();
			}
		}
	};

	public final SelectionListener nuevoId = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			new AgregarIdentificadorDialog(elementos).open();
		}
	};
}
