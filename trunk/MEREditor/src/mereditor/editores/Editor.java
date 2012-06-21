package mereditor.editores;

import mereditor.interfaz.swt.Principal;
import mereditor.modelo.base.Componente;

import org.eclipse.jface.window.ApplicationWindow;

public class Editor<T extends Componente> extends ApplicationWindow {
	protected Principal principal = Principal.getInstance();
	protected T componente;

	public Editor(T componente) {
		super(Principal.getInstance().getShell());
		this.componente = componente;
	}

	/**
	 * Abre el editor como ventana modal.
	 */
	public void abrir() {
		this.setBlockOnOpen(true);
		this.open();
	}
}
