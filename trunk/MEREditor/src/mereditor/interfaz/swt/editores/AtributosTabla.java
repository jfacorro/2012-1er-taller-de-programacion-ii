package mereditor.interfaz.swt.editores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mereditor.control.AtributoControl;
import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class AtributosTabla extends TableViewer {
	public static final String NOMBRE = "Nombre";
	public static final String TIPO = "Tipo";
	public static final String[] PROPS = { NOMBRE, TIPO };
	
	private List<Atributo> atributos;

	public AtributosTabla(Composite parent, Set<Atributo> atributos) {
		super(parent, SWT.FULL_SELECTION);
		this.atributos = new ArrayList<>(atributos);
		
		this.init();
	}

	private void init() {
		this.setContentProvider(new AtributoProvider());
		this.setLabelProvider(new AtributoLabelProvider());
		this.setInput(this.atributos);
		
		// Configurar tabla
		Table tblAtributos = this.getTable();
		tblAtributos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		new TableColumn(tblAtributos, SWT.CENTER).setText(NOMBRE);
		new TableColumn(tblAtributos, SWT.CENTER).setText(TIPO);

		tblAtributos.setHeaderVisible(true);
		tblAtributos.setLinesVisible(true);

		// Crear editores de celda
		CellEditor[] editors = new CellEditor[2];
		editors[0] = new TextCellEditor(tblAtributos);
		editors[1] = new ComboBoxCellEditor(tblAtributos, Editor.TiposAtributo, SWT.READ_ONLY);

		// Establecer el titulo de las columnas, los modificadores y los
		// editores.
		this.setColumnProperties(PROPS);
		this.setCellModifier(new AtributoCellModifier(this));
		this.setCellEditors(editors);
		
	}
	
	// Agregar un nuevo atributo cuando se hace click sobre el boton
	public final SelectionListener nuevo = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			AtributoControl atributo = new AtributoControl();
			atributo.setNombre("Nombre");
			atributo.setTipo(TipoAtributo.CARACTERIZACION);

			atributos.add(atributo);

			refresh();
		}
	};
}
