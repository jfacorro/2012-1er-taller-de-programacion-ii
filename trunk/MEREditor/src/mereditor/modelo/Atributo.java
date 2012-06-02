package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;

public class Atributo extends ComponenteNombre {
	public enum TipoAtributo {
		CARACTERIZACION, DERIVADO_COPIA, DERIVADO_CALCULO
	}

	protected String cardinalidadMin;
	protected String cardinalidadMax;
	protected TipoAtributo tipo;
	protected List<Atributo> atributos = new LinkedList<Atributo>();

	// Derivado copia
	protected Atributo original;

	// Derivado calculo
	protected String formula;
	
	public Atributo() {}

	public Atributo(String nombre, String id, String idPadre) {
		super(nombre, id, idPadre);
	}

	public Atributo(String nombre, String id, String idPadre, String min,
			String cardMax, TipoAtributo tipo,
			List<Atributo> atributos) {
		this(nombre, id, idPadre);
		this.atributos = atributos;
		this.cardinalidadMin = min;
		this.cardinalidadMax = cardMax;
	}
}
