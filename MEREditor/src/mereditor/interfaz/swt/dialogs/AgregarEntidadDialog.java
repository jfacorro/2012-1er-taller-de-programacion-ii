package mereditor.interfaz.swt.dialogs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mereditor.control.EntidadControl;
import mereditor.interfaz.swt.editores.EditorFactory;
import mereditor.modelo.Diagrama;
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
		Diagrama diagrama = this.principal.getProyecto().getDiagramaActual();

		// Obtener las entidades de los ancestros
		Set<Entidad> entidades = diagrama.getEntidades(true);
		// Quitar las que ya tiene
		entidades.removeAll(diagrama.getEntidades());

		for (Entidad entidad : entidades) {
			this.entidades.put(entidad.getNombre(), entidad);
			this.cboEntidades.add(entidad.getNombre());
		}

		String[] items = this.cboEntidades.getItems();
		Arrays.sort(items);
		this.cboEntidades.setItems(items);
	}

	@Override
	protected void okPressed() {
		if (cboEntidades.getSelectionIndex() == -1) {
			this.principal.error("No seleccionó ninguna entidad");
		} else {
			String nombre = cboEntidades.getText();
			Entidad entidad = this.entidades.get(nombre);
			agregar(entidad);
			super.okPressed();
		}
	}

	protected SelectionAdapter nueva = new SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			close();
			Entidad entidad = new EntidadControl();
			EditorFactory.getEditor(entidad).open();
			agregar(entidad);
		};
	};

	/**
	 * Agrega la entidad seleccionada y cierra el dialogo.
	 * 
	 * @param entidad
	 */
	private void agregar(Entidad entidad) {
		principal.getProyecto().agregar(entidad);
		principal.actualizarVista();
	}
}
