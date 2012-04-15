package mereditor.modelo;

import mereditor.modelo.base.Componente;

public class Jerarquia extends Componente {
	enum TipoJerarquia {
		TOTAL_EXCLUSIVA,
		TOTAL_SUPERPUESTA,
		PARCIAL_EXCLUSIVA,
		PARCIAL_SUPERPUESTA
	}
	
	protected TipoJerarquia tipo;
	
	public Jerarquia() {
		
	}
}
