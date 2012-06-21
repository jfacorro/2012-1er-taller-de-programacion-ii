package mereditor.editores;

import mereditor.interfaz.swt.dialogs.Dialog;
import mereditor.modelo.base.Componente;

public class Editor<T extends Componente> extends Dialog {
	protected T componente;

	public Editor(T componente) {
		super();
		this.componente = componente;
	}
}
