package mereditor.interfaz.swt;

import mereditor.modelo.Atributo;

public class AtributoFigure extends Figura<Atributo> {

	public AtributoFigure(Atributo componente) {
		super(componente);
		this.lblName.setText(this.componente.getNombre());
		this.setBackgroundColor(null);
		this.setBorder(null);
		this.setOpaque(false);
	}

	public void conectarAtributo(Figura<Atributo> figura) {
		this.getParent().add(Figura.conectar(this, figura));
	}
}
