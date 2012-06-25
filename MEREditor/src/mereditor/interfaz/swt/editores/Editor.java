package mereditor.interfaz.swt.editores;

import java.util.ArrayList;
import java.util.List;

import mereditor.interfaz.swt.Principal;
import mereditor.interfaz.swt.dialogs.Dialog;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.Jerarquia.TipoJerarquia;
import mereditor.modelo.Relacion.TipoRelacion;
import mereditor.modelo.base.Componente;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public abstract class Editor<T extends Componente> extends Dialog {
	public static final String NOMBRE = "Nombre";
	public static final String TIPO = "Tipo";

	public final static String[] TiposAtributo = getTipos(TipoAtributo.class);
	public final static String[] TiposEntidades = getTipos(TipoEntidad.class);
	public final static String[] TiposRelaciones = getTipos(TipoRelacion.class);
	public final static String[] TiposJerarquias = getTipos(TipoJerarquia.class);

	protected T componente;

	/**
	 * Constructor para el editor visual.
	 */
	protected Editor(Shell shell) {
		super(shell);
	}

	public Editor(T componente) {
		super(Principal.getInstance().getShell());
		this.componente = componente;
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(400, 400);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		this.cargarDatos();

		return control;
	}

	private static <T> String[] getTipos(Class<T> enumClass) {
		List<String> tipos = new ArrayList<>();

		for (T tipo : enumClass.getEnumConstants())
			tipos.add(tipo.toString());

		return tipos.toArray(new String[] {});
	}

	@Override
	protected void okPressed() {
		this.aplicarCambios();
		principal.actualizarVista();
		super.okPressed();
	}
	
	protected static Text createLabelText(Composite parent, String name) {
		Label lblNombre = new Label(parent, SWT.LEFT);
		lblNombre.setText(name);

		Text txtField = new Text(parent, SWT.BORDER);
		
		return txtField;
	}
	
	protected static Combo createLabelCombo(Composite parent, String name) {
		Label lblNombre = new Label(parent, SWT.LEFT);
		lblNombre.setText(name);

		Combo cboField = new Combo(parent, SWT.BORDER);
		
		return cboField;
	}
	
	/**
	 * Carga los datos del componente en los controles.
	 */
	protected abstract void cargarDatos();

	/**
	 * Aplica los cambios hechos al componente.
	 */
	protected abstract void aplicarCambios();
}
