package mereditor.interfaz.swt.editores;

import mereditor.modelo.Relacion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class RelacionEditor extends Editor<Relacion> {
	protected Text txtNombre;
	protected Combo cboTipo;
	protected AtributosTabla tblAtributos;

	public RelacionEditor(Relacion relacion) {
		super(relacion);
	}
	
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		/**
		 * Campos generales.
		 */
		Composite header = new Composite(dialogArea, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		header.setLayout(new GridLayout(2, false));

		this.txtNombre = createLabelText(header, Editor.NOMBRE);
		this.txtNombre.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.cboTipo = createLabelCombo(header, Editor.TIPO);
		this.cboTipo.setItems(Editor.TiposRelaciones);

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
		this.tblAtributos = new AtributosTabla(grupoAtributos);

		// Agregar un nuevo atributo cuando se hace click sobre el boton
		btnNuevoAtributo.addSelectionListener(this.tblAtributos.nuevo);

		return dialogArea;
	}

	@Override
	protected void cargarDatos() {
		this.txtNombre.setText(this.componente.getNombre());
		this.cboTipo.setText(this.componente.getTipo().name());

		tblAtributos.setAtributos(this.componente.getAtributos());
	}

	@Override
	protected void aplicarCambios() {
		// TODO Auto-generated method stub
		
	}
}
