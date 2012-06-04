package mereditor.interfaz.swt;

import mereditor.modelo.Atributo;

public class AtributoFigure extends Figura<Atributo> {

	public AtributoFigure(Atributo componente) {
		super(componente);
		this.lblName.setText(this.componente.getNombre());
	}
}
