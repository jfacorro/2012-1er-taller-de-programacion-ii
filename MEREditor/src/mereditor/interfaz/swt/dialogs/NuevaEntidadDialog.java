package mereditor.interfaz.swt.dialogs;

import mereditor.control.EntidadControl;
import mereditor.control.Proyecto;
import mereditor.modelo.Entidad;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NuevaEntidadDialog extends Dialog {
	private Entidad entidad;
	private Text txtNombre;
	private Combo cboEntidades;
	
	public NuevaEntidadDialog() {
		super();
		this.titulo = "Agregar Entidad";
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		
		Label lblNombre = new Label(composite, SWT.LEFT);
		lblNombre.setText("Nombre");
		this.txtNombre = new Text(composite, SWT.BORDER | SWT.SINGLE);

		Label lblEntidades = new Label(composite, SWT.LEFT);
		lblEntidades.setText("Entidades");
		this.cboEntidades = new Combo(composite, SWT.READ_ONLY);

		return super.createContents(composite);
	}
	
	protected void aceptar() {
		// Si no hay ningun item seleccionado. 
		if (cboEntidades.getSelectionIndex() == -1) {
			entidad = new EntidadControl(); 
			entidad.setNombre(txtNombre.getText());
		}

		Proyecto proyecto = principal.getProyecto();
		proyecto.agregar(entidad);
		principal.actualizar();
		close();
	}
}
