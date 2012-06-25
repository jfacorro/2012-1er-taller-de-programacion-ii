package mereditor.interfaz.swt.editores;

import java.util.List;

import mereditor.control.JerarquiaControl;
import mereditor.modelo.Jerarquia;

public class JerarquiaEditor extends Editor<Jerarquia> {

	public JerarquiaEditor() {
		this(new JerarquiaControl());
	}

	public JerarquiaEditor(Jerarquia jerarquia) {
		super(jerarquia);
	}

	@Override
	protected void cargarDatos() {

	}

	@Override
	protected void aplicarCambios() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean validar(List<String> errors) {
		return errors.size() == 0;
	}
}
