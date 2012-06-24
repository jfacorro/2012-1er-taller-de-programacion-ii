package mereditor.interfaz.swt.editores;

import java.util.ArrayList;

import mereditor.interfaz.swt.Principal;
import mereditor.interfaz.swt.dialogs.Dialog;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.Jerarquia.TipoJerarquia;
import mereditor.modelo.Relacion.TipoRelacion;
import mereditor.modelo.base.Componente;

import org.eclipse.swt.widgets.Shell;

public class Editor<T extends Componente> extends Dialog {
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
	
	private static <T> String[] getTipos(Class<T> enumClass) {
		java.util.List<String> tipos = new ArrayList<>();
		
		for(T tipo : enumClass.getEnumConstants())
			tipos.add(tipo.toString());
		
		return tipos.toArray(new String[] {});
	}
}
