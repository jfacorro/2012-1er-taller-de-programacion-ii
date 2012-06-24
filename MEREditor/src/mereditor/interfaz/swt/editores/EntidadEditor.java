package mereditor.interfaz.swt.editores;

import java.util.ArrayList;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class EntidadEditor extends Editor<Entidad> {
	// TODO: completar
	public static final String NOMBRE = "Nombre";
	public static final String TIPO = "Tipo";
	public static final String[] PROPS = { NOMBRE, TIPO };

	private ArrayList<Atributo> atributos = new ArrayList<>();

	protected Text txtNombre;
	protected Combo cboTipo;
	protected AtributosTabla tblAtributos;

	/**
	 * Constructor para el editor visual
	 * 
	 * @wbp.parser.constructor
	 */
	protected EntidadEditor(Shell shell) {
		super(shell);
		this.componente = new Entidad();
	}

	public EntidadEditor(Entidad entidad) {
		super(entidad);
		this.titulo = "Editor - " + componente.getNombre();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(400, 400);
	}

	protected Control createDialogArea(final Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		/**
		 * Campos generales.
		 */
		Composite header = new Composite(dialogArea, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		header.setLayout(new GridLayout(2, false));

		this.txtNombre = createLabelText(header, NOMBRE);
		this.txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.cboTipo = createLabelCombo(header, TIPO);
		this.cboTipo.setItems(Editor.TiposEntidades);

		/**
		 * Atributos.
		 */
		Group grupoAtributos = new Group(dialogArea, SWT.NONE);
		grupoAtributos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grupoAtributos.setText("Atributos");
		grupoAtributos.setLayout(new GridLayout(1, true));

		Button btnNuevoAtributo = new Button(grupoAtributos, SWT.PUSH);
		btnNuevoAtributo.setText("Nuevo");

		// TableViewer
		this.tblAtributos = new AtributosTabla(grupoAtributos, this.componente.getAtributos());

		// Agregar un nuevo atributo cuando se hace click sobre el boton
		btnNuevoAtributo.addSelectionListener(this.tblAtributos.nuevo);

		return dialogArea;
	}

	@Override
	protected void cargarDatos() {
		this.txtNombre.setText(this.componente.getNombre());
		this.cboTipo.setText(this.componente.getTipo().name());

		// agrega atributos existentes
		for (Atributo attr : this.componente.getAtributos())
			atributos.add(attr);

		tblAtributos.refresh();

		for (TableColumn column : tblAtributos.getTable().getColumns())
			column.pack();
	}

	@Override
	protected void aplicarCambios() {
		componente.setTipo(TipoEntidad.valueOf(this.cboTipo.getText()));
		componente.setNombre(txtNombre.getText());

		for (Atributo atributo : atributos)
			componente.addAtributo(atributo);
	}
}
