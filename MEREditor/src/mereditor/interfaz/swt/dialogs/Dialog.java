package mereditor.interfaz.swt.dialogs;

import mereditor.interfaz.swt.Principal;

import org.eclipse.jface.window.ApplicationWindow;

public class Dialog extends ApplicationWindow {
	protected Principal principal = Principal.getInstance();

	public Dialog() {
		super(Principal.getInstance().getShell());
	}

	/**
	 * Abre el dialogo indicando si se quiere una modal o no.
	 */
	public void abrir(boolean modal) {
		this.setBlockOnOpen(modal);
		this.open();
	}

	/**
	 * Abre el dialogo como ventana modal.
	 */
	public void abrir() {
		this.abrir(true);
	}
}
