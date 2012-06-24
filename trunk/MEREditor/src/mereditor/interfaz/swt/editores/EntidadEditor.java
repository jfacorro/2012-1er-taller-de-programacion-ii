package mereditor.interfaz.swt.editores;

import java.util.ArrayList;

import mereditor.control.AtributoControl;
import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.Entidad;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class EntidadEditor extends Editor<Entidad> {

	private ArrayList<Atributo> atributos;

	// TODO: completar
	public static final String NOMBRE = "Nombre";
	public static final String TIPO = "Tipo";
	public static final String[] PROPS = { NOMBRE, TIPO };

	public static final String[] TIPOS_STR = {
			TipoAtributo.CARACTERIZACION.name(),
			TipoAtributo.DERIVADO_COPIA.name(),
			TipoAtributo.DERIVADO_CALCULO.name() };

	private Text txtNombre;

	/**
	 * @wbp.parser.constructor
	 */
	protected EntidadEditor(Shell shell) {
		super(shell);
		this.componente = new Entidad();
	}

	public EntidadEditor(Entidad entidad) {
		super(entidad);
		this.componente = entidad;
		this.atributos = new ArrayList<>();
		this.titulo = "Editor - " + componente.getNombre();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(400, 400);
	}

	protected Control createDialogArea(final Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		dialogArea.setLayout(layout);
		dialogArea.setLayoutData(new GridData(GridData.FILL_BOTH));
		// dialogArea.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
		// true, true));

		Label lblNombre = new Label(dialogArea, SWT.LEFT);
		lblNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		lblNombre.setText("Nombre");

		this.txtNombre = new Text(dialogArea, SWT.BORDER);
		txtNombre.setText(this.componente.getNombre());
		this.txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Group grupoAtributos = new Group(dialogArea, SWT.NONE);
		grupoAtributos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grupoAtributos.setText("Atributos");
		grupoAtributos.setLayout(new GridLayout(1, true));

		Button btnNuevoAtributo = new Button(grupoAtributos, SWT.PUSH);
		btnNuevoAtributo.setText("Nuevo");

		// TableViewer
		final TableViewer tablev = new TableViewer(grupoAtributos,
				SWT.FULL_SELECTION);
		tablev.setContentProvider(new AtributoProvider());
		tablev.setLabelProvider(new AtributoLabelProvider());
		tablev.setInput(this.atributos);

		// Configurar tabla
		Table table = tablev.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		new TableColumn(table, SWT.CENTER).setText(NOMBRE);
		new TableColumn(table, SWT.CENTER).setText(TIPO);

		for (TableColumn column : table.getColumns())
			column.pack();

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// agrega atributos existentes
		for (Atributo attr : this.componente.getAtributos())
			atributos.add(attr);

		tablev.refresh();

		// Agregar un nuevo atributo cuando se hace click sobre el boton
		btnNuevoAtributo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AtributoControl atr = new AtributoControl();
				atr.setNombre("Nombre");
				atr.setTipo(TipoAtributo.CARACTERIZACION);

				atributos.add(atr);

				// Actualizacion del modelo
				componente.addAtributo(atr);

				tablev.refresh();
			}
		});

		// Crear editores de celda
		CellEditor[] editors = new CellEditor[2];
		editors[0] = new TextCellEditor(table);
		editors[1] = new ComboBoxCellEditor(table, TIPOS_STR, SWT.READ_ONLY);

		//
		tablev.setColumnProperties(PROPS);
		tablev.setCellModifier(new AtributoCellModifier(tablev));
		tablev.setCellEditors(editors);

		return dialogArea;
	}
	
	@Override
	protected void okPressed() {
		componente.setNombre(txtNombre.getText());
		principal.actualizarVista();
		super.okPressed();
	}
}
