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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
		setBlockOnOpen(true);
		setShellStyle(SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		this.componente = new Entidad();
	}

	public EntidadEditor(Entidad entidad) {
		super(entidad);
		this.componente = entidad;
		this.atributos = new ArrayList<>();
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Editor - " + componente.getNombre());
		shell.setSize(400, 400);
	}

	protected Control createContents(final Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Label lblNombre = new Label(composite, SWT.LEFT);
		lblNombre.setText("Nombre");

		this.txtNombre = new Text(composite, SWT.BORDER);
		txtNombre.setText(this.componente.getNombre());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.txtNombre.setLayoutData(gridData);
		this.txtNombre.addModifyListener(this.modificacionNombre);

		Button btnNuevoAtributo = new Button(composite, SWT.PUSH);
		btnNuevoAtributo.setText("Crear un nuevo atributo");

		// TableViewer
		final TableViewer tablev = new TableViewer(composite,
				SWT.FULL_SELECTION);
		tablev.setContentProvider(new AtributoProvider());
		tablev.setLabelProvider(new AtributoLabelProvider());
		tablev.setInput(this.atributos);

		// Configurar tabla
		Table table = tablev.getTable();
		GridData gd_table = new GridData(GridData.FILL_BOTH);
		gd_table.heightHint = 97;
		gd_table.widthHint = 201;
		table.setLayoutData(gd_table);

		new TableColumn(table, SWT.CENTER).setText(NOMBRE);
		new TableColumn(table, SWT.CENTER).setText(TIPO);

		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).pack();
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// agrega atributos existentes
		for (Atributo attr : this.componente.getAtributos()) {
			atributos.add(attr);
		}
		tablev.refresh();

		// Agregar un nuevo atributo cuando se hace click sobre el boton
		btnNuevoAtributo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AtributoControl atr = new AtributoControl();
				atr.setNombre("Nombre");
				atr.setTipo(TipoAtributo.CARACTERIZACION);

				atributos.add(atr);

				// Actualización del modelo
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

		return super.createContents(parent);
	}

	@Override
	protected void aceptar() {
		principal.actualizarVista();
		this.close();
	}
	
	private ModifyListener modificacionNombre = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			componente.setNombre(txtNombre.getText());			
		}
	};
}
