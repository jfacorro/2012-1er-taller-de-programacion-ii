package mereditor.interfaz.swt.dialogs;

import java.util.Set;

import mereditor.interfaz.swt.editores.Editor;
import mereditor.interfaz.swt.editores.EntidadEditor;
import mereditor.modelo.Entidad;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class AgregarEntidadDialog extends Dialog {
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

		Button btnSeleccionar = new Button(container, SWT.PUSH);
		btnSeleccionar.setText("Seleccionar Existente");
		btnSeleccionar.addSelectionListener(this.seleccionar);
		btnSeleccionar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Button btnNueva = new Button(container, SWT.PUSH);
		btnNueva.setText("Nueva Entidad");
		btnNueva.addSelectionListener(this.nueva);
		btnNueva.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		return container;
	}

	/**
	 * Carga las entidades que pertenecen al diagrama actual y a sus padres en
	 * el combo.
	 */
	private Set<Entidad> loadEntidades() {
		// Obtener las entidades de los ancestros
		Set<Entidad> entidades = this.principal.getProyecto()
				.getEntidadesDisponibles();
		Set<Entidad> entidadesDiagrama = this.principal.getProyecto()
				.getEntidadesDiagrama();
		// Quitar las que ya tiene
		entidades.removeAll(entidadesDiagrama);

		return entidades;
	}

	/**
	 * Devuelve la entidad seleccionada o creada.
	 * 
	 * @return
	 */
	public Entidad getComponente() {
		return this.entidad;
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

	protected SelectionAdapter seleccionar = new SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			SeleccionarComponenteDialog<Entidad> dialog = new SeleccionarComponenteDialog<Entidad>(
					loadEntidades());
			int result = dialog.open();
			entidad = dialog.getComponente();
			setReturnCode(result);
			close();
		};
	};
}
