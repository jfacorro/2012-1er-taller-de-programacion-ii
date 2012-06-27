package mereditor.interfaz.swt.editores;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mereditor.control.JerarquiaControl;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.Jerarquia.TipoJerarquia;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

public class JerarquiaEditor extends Editor<Jerarquia> {
	public static final String GENERICA = "Generica";

	protected Combo cboTipo;
	protected Combo cboGenerica;
	protected EntidadTabla tblDerivadas;

	protected Map<String, Entidad> entidades = new HashMap<>();

	public JerarquiaEditor() {
		this(new JerarquiaControl());
	}

	public JerarquiaEditor(Jerarquia jerarquia) {
		super(jerarquia);
		this.titulo = "Editor - Jerarquia";
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

		this.cboTipo = createLabelCombo(header, TIPO);
		this.cboTipo.setItems(Editor.TiposJerarquias);

		this.cboGenerica = createLabelCombo(header, GENERICA);
		this.loadGenerica(this.cboGenerica);
		
		/**
		 * Atributos.
		 */
		Group grupoDerivadas = new Group(dialogArea, SWT.NONE);
		grupoDerivadas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grupoDerivadas.setText("Entidades Derivadas");
		grupoDerivadas.setLayout(new GridLayout(1, true));

		// TableViewer
		this.tblDerivadas = new EntidadTabla(grupoDerivadas);

		return dialogArea;
	}

	/**
	 * Carga las entidades que pertenecen al diagrama actual y a sus padres en
	 * el combo.
	 */
	private void loadGenerica(Combo combo) {
		Diagrama diagrama = this.principal.getProyecto().getDiagramaActual();

		// Obtener las entidades de este diagrama
		Set<Entidad> entidades = diagrama.getEntidades();

		for (Entidad entidad : entidades) {
			this.entidades.put(entidad.getNombre(), entidad);
			combo.add(entidad.getNombre());
		}

		String[] items = combo.getItems();
		Arrays.sort(items);
		combo.setItems(items);
	}

	@Override
	protected void cargarDatos() {
		this.cboTipo.setText(this.componente.getTipo().name());
		this.cboGenerica.setText(this.componente.getGenerica().getNombre());

		this.tblDerivadas.setElementos(this.componente.getDerivadas());
	}

	@Override
	protected void aplicarCambios() {
		this.componente.setTipo(TipoJerarquia.valueOf(this.cboTipo.getText()));
		String nombre = this.cboGenerica.getText();
		Entidad entidad = this.entidades.get(nombre);
		this.componente.setGenerica(entidad);
	}

	@Override
	protected boolean validar(List<String> errors) {
		return errors.size() == 0;
	}
}
