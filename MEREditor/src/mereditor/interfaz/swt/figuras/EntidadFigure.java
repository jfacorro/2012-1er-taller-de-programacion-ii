package mereditor.interfaz.swt.figuras;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

public class EntidadFigure extends Figura<Entidad> {

	public EntidadFigure(Entidad entidad) {
		super(entidad);
	}
	
	@Override
	protected void init() {
		super.init();
		
		this.update();
	}

	public void update() {
		this.lblName.setText(this.componente.getNombre());
	}

	public void conectarAtributo(Figura<Atributo> figura) {
		this.getParent().add(Figura.conectar(this, figura));
	}
}
