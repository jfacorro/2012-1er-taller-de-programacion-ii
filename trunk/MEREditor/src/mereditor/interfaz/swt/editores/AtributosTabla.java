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
import org.eclipse.swt.widgets.Table;

public class AtributosTabla extends Tabla<Atributo> {

	public AtributosTabla(Composite parent) {
		super(parent);
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.NOMBRE);
		this.columnas.add(Editor.TIPO);
		this.columnas.add(Editor.CARDINALIDAD_MIN);
		this.columnas.add(Editor.CARDINALIDAD_MAX);
	}

	@Override
	protected void initEditorsCeldas(Table table) {
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new ComboBoxCellEditor(table, Editor.TiposAtributo, SWT.READ_ONLY));
	}
	
	@Override
	protected String getTextoColumna(Atributo element, int columnIndex) {
		String nombreColumna = this.columnas.get(columnIndex);
		if(nombreColumna.equals(EntidadEditor.TIPO))
			return element.getTipo().name();
		else
			return (String) this.getValorCelda(element, this.columnas.get(columnIndex));
	}

	@Override
	protected Object getValorCelda(Atributo element, String property) {
		switch (property) {
		case Editor.NOMBRE:
			return element.getNombre();
		case Editor.TIPO:
			return element.getTipo().ordinal();
		case Editor.CARDINALIDAD_MIN:
			return element.getCardinalidadMinima();
		case Editor.CARDINALIDAD_MAX:
			return element.getCardinalidadMaxima();
		default:
			return null;
		}
	}

	@Override
	protected void setValorCelda(Atributo element, String property, Object value) {
		switch (property) {
		case Editor.NOMBRE:
			element.setNombre((String) value);
			break;
		case Editor.TIPO:
			element.setTipo(TipoAtributo.class.getEnumConstants()[(int) value]);
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
