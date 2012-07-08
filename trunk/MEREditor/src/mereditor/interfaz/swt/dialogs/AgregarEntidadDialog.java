package mereditor.interfaz.swt.dialogs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mereditor.interfaz.swt.editores.Editor;
import mereditor.interfaz.swt.editores.EntidadEditor;
import mereditor.modelo.Entidad;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AgregarEntidadDialog extends Dialog {
	private Combo cboEntidades;
	private Map<String, Entidad> entidades = new HashMap<>();
	private Entidad entidad = null;	

	/**
	 * @wbp.parser.constructor
	 */
	protected AgregarEntidadDialog(Shell shell) {
		super(shell);
	}

	public AgregarEntidadDialog() {
		super();
		this.titulo = "Agregar Entidad";
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		Label lblEntidades = new Label(container, SWT.LEFT);
		lblEntidades.setText("Entidades");

		this.cboEntidades = new Combo(container, SWT.READ_ONLY);
		this.cboEntidades.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		this.loadEntidades();

		Button btnNueva = new Button(container, SWT.PUSH);
		btnNueva.setText("Nueva Entidad");
		btnNueva.addSelectionListener(this.nueva);
		btnNueva.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		return container;
	}

	/**
	 * Carga las entidades que pertenecen al diagrama actual y a sus padres en
	 * el combo.
	 */
	private void loadEntidades() {
		// Obtener las entidades de los ancestros
		Set<Entidad> entidades = this.principal.getProyecto().getEntidadesDisponibles();
		Set<Entidad> entidadesDiagrama = this.principal.getProyecto().getEntidadesDiagrama();
		// Quitar las que ya tiene
		entidades.removeAll(entidadesDiagrama);

		for (Entidad entidad : entidades) {
			this.entidades.put(entidad.getNombre(), entidad);
			this.cboEntidades.add(entidad.getNombre());
		}

		String[] items = this.cboEntidades.getItems();
		Arrays.sort(items);
		this.cboEntidades.setItems(items);
	}
	
	/**
	 * Devuelve la entidad seleccionada o creada.
	 * @return
	 */
	public Entidad getComponente() {
		return this.entidad;
	}

	@Override
	protected void okPressed() {
		if (cboEntidades.getSelectionIndex() == -1) {
			this.principal.error("No seleccionó ninguna entidad");
		} else {
			String nombre = cboEntidades.getText();
			entidad = this.entidades.get(nombre);
			super.okPressed();
		}
	}

	protected SelectionAdapter nueva = new SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			Editor<Entidad> editor = new EntidadEditor();
			int result = editor.open();
			entidad = editor.getComponente();
			setReturnCode(result);
			close();
		};
	};
}
