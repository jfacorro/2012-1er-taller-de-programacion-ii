package mereditor.interfaz.swt.editores;

import java.util.List;

import mereditor.modelo.Atributo;

public class AtributoEditor extends Editor<Atributo> {

	public AtributoEditor(Atributo atributo) {
		super(atributo);
		this.titulo = "Editor - " + componente.getNombre();
	}

	@Override
	protected void cargarDatos() {
		// TODO Auto-generated method stub
		
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
