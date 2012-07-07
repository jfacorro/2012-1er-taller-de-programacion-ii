package mereditor.interfaz.swt.editores;

import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AtributoEditor extends Editor<Atributo> {
	protected Text txtNombre;
	protected Combo cboTipo;
	protected Text txtCardinalidadMin;
	protected Text txtCardinalidadMax;
	protected AtributosTabla tblAtributos;
	
	/**
	 * Constructor para el editor visual
	 * 
	 * @wbp.parser.constructor
	 */
	protected AtributoEditor(Shell shell) {
		super(shell);
		this.componente = new Atributo();
	}

	public AtributoEditor(Atributo atributo) {
		super(atributo);
		this.titulo = "Editor - " + componente.getNombre();
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
		this.cboTipo.setItems(Editor.TiposAtributo);
		
		this.txtCardinalidadMin = createLabelText(header, Editor.CARDINALIDAD_MIN);
		GridData gridDataCardMin = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gridDataCardMin.widthHint = 15;
		this.txtCardinalidadMin.setLayoutData(gridDataCardMin);

		this.txtCardinalidadMax = createLabelText(header, Editor.CARDINALIDAD_MAX);
		this.txtCardinalidadMax.setSize(100, this.txtCardinalidadMax.getSize().y);
		GridData gridDataCardMax = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gridDataCardMax.widthHint = 15;
		this.txtCardinalidadMax.setLayoutData(gridDataCardMax);

		/**
		 * Atributos.
		 */
		Group grupoAtributos = new Group(dialogArea, SWT.NONE);
		grupoAtributos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grupoAtributos.setText(Editor.ATRIBUTOS);
		grupoAtributos.setLayout(new GridLayout(1, true));
		
		Composite botonesAtributos = new Composite(grupoAtributos, SWT.NONE);
		botonesAtributos.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1));
		botonesAtributos.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNuevoAtributo = new Button(botonesAtributos, SWT.PUSH);
		btnNuevoAtributo.setText(Editor.NUEVO);
		
		Button btnEliminarAtributo = new Button(botonesAtributos, SWT.PUSH);
		btnEliminarAtributo.setText(Editor.ELIMINAR);

		this.tblAtributos = new AtributosTabla(grupoAtributos);

		btnNuevoAtributo.addSelectionListener(this.tblAtributos.nuevo);
		btnEliminarAtributo.addSelectionListener(this.tblAtributos.eliminar);

		return dialogArea;
	}

	@Override
	protected void cargarDatos() {
		this.txtNombre.setText(this.componente.getNombre());
		this.cboTipo.setText(this.componente.getTipo().name());
		this.txtCardinalidadMin.setText(this.componente.getCardinalidadMinima());
		this.txtCardinalidadMax.setText(this.componente.getCardinalidadMaxima());

		this.tblAtributos.setElementos(this.componente.getAtributos());
	}

	@Override
	protected void aplicarCambios() {
		this.componente.setNombre(txtNombre.getText());
		this.componente.setTipo(TipoAtributo.valueOf(this.cboTipo.getText()));
		this.componente.setCardinalidadMinima(this.txtCardinalidadMin.getText());
		this.componente.setCardinalidadMaxima(this.txtCardinalidadMax.getText());

		for (Atributo atributo : this.tblAtributos.getElementos())
			this.componente.addAtributo(atributo);

		for (Atributo atributo : this.tblAtributos.getElementoEliminados())
			this.componente.removeAtributo(atributo);
	}

	@Override
	protected boolean validar(List<String> errors) {
		if (txtNombre.getText().trim().isEmpty())
			errors.add("Debe completar el nombre.");

		return errors.isEmpty();
	}
}
