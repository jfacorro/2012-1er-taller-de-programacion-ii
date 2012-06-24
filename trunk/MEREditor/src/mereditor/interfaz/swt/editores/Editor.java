package mereditor.interfaz.swt.editores;

import org.eclipse.swt.widgets.Shell;

import mereditor.interfaz.swt.Principal;
import mereditor.interfaz.swt.dialogs.Dialog;
import mereditor.modelo.base.Componente;

public class Editor<T extends Componente> extends Dialog {
	protected T componente;
	
	protected Editor(Shell shell) {
		super(shell);
	}

	public Editor(T componente) {
		super(Principal.getInstance().getShell());
		this.componente = componente;
	}
}
