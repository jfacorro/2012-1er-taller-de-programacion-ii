package mereditor.interfaz.swt.dialogs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mereditor.control.EntidadControl;
import mereditor.editores.EditorFactory;
import mereditor.modelo.Entidad;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class AgregarEntidadDialog extends Dialog {
	private Combo cboEntidades;
	private Set<Entidad> entidades = new HashSet<>();

	public AgregarEntidadDialog() {
		super();
		this.titulo = "Agregar Entidad";
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));

		Button btnNueva = new Button(composite, SWT.PUSH);
		btnNueva.setText("Nueva Entidad");
		btnNueva.addSelectionListener(this.nueva);

		Label lblEntidades = new Label(composite, SWT.LEFT);
		lblEntidades.setText("Entidades");
		this.cboEntidades = new Combo(composite, SWT.READ_ONLY);
		this.loadEntidades();

		return super.createContents(composite);
	}

	/**
	 * Carga las entidades que pertenecen al diagrama actual y a sus padres en
	 * el combo.
	 */
	private void loadEntidades() {
		this.entidades = this.principal.getProyecto().getDiagramaActual().getEntidades();

		for (Entidad entidad : this.entidades)
			this.cboEntidades.add(entidad.getNombre());

		String[] items = this.cboEntidades.getItems();
		Arrays.sort(items);
		this.cboEntidades.setItems(items);
	}

	protected void aceptar() {
		if (cboEntidades.getSelectionIndex() == -1) {
			throw new RuntimeException("No seleccionó ninguna entidad");
		}

		Entidad entidad = null;

		agregar(entidad);
	}

	protected SelectionAdapter nueva = new SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			Entidad entidad = new EntidadControl();
			EditorFactory.getEditor(entidad).abrir();
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
		principal.actualizar();
		close();
	}
}
