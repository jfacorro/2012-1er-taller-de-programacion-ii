package mereditor.interfaz.swt.editores;

import mereditor.interfaz.swt.dialogs.Dialog;
import mereditor.modelo.base.Componente;

public class Editor<T extends Componente> extends Dialog {
	protected T componente;

	public Editor(T componente) {
		super();
		this.componente = componente;
	}
	
	@Override
	protected void aceptar() {
		throw new RuntimeException("No implementada.");		
	}
}
