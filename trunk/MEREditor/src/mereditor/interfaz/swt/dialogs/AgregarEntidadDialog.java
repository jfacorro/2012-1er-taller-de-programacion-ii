package mereditor.interfaz.swt.dialogs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mereditor.control.EntidadControl;
import mereditor.interfaz.swt.editores.EditorFactory;
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
	protected Control createContents(Composite parent) {
		Label lblEntidades = new Label(parent, SWT.LEFT);
		lblEntidades.setText("Entidades");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.cboEntidades = new Combo(parent, SWT.READ_ONLY);
		this.cboEntidades.setLayoutData(gridData);
		this.loadEntidades();
		
		Button btnNueva = new Button(parent, SWT.PUSH);
		btnNueva.setText("Nueva Entidad");
		btnNueva.addSelectionListener(this.nueva);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		btnNueva.setLayoutData(gridData);

		return super.createContents(parent);
	}

	/**
	 * Carga las entidades que pertenecen al diagrama actual y a sus padres en
	 * el combo.
	 */
	private void loadEntidades() {
		Set<Entidad> entidades = this.principal.getProyecto()
				.getDiagramaActual().getEntidades(true);

		for (Entidad entidad : entidades) {
			this.entidades.put(entidad.getNombre(), entidad);
			this.cboEntidades.add(entidad.getNombre());
		}

		String[] items = this.cboEntidades.getItems();
		Arrays.sort(items);
		this.cboEntidades.setItems(items);
	}

	protected void aceptar() {
		if (cboEntidades.getSelectionIndex() == -1) {
			this.principal.error("No seleccionó ninguna entidad");
		} else {
			String nombre = cboEntidades.getText();
			Entidad entidad = this.entidades.get(nombre);
			agregar(entidad);
		}
	}

	protected SelectionAdapter nueva = new SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
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
		close();
	}
}
