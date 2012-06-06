package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Atributo extends ComponenteNombre {
	public enum TipoAtributo {
		CARACTERIZACION, DERIVADO_COPIA, DERIVADO_CALCULO
	}

	protected TipoAtributo tipo;
	protected String cardinalidadMinima;
	protected String cardinalidadMaxima;
	protected List<Atributo> atributos = new LinkedList<Atributo>();

	// Derivado copia
	protected Atributo original;

	// Derivado calculo
	protected String formula;
	
	public Atributo() {
		super();
	}
	
	public Atributo(String nombre) {
		super(nombre);
	}

	public Atributo(String nombre, String id, Componente padre) {
		super(nombre, id, padre);
	}

	public Atributo(String nombre, String id, Componente padre, String min,
			String cardMax, TipoAtributo tipo,
			List<Atributo> atributos) {
		this(nombre, id, padre);
		this.atributos = atributos;
		this.cardinalidadMinima = min;
		this.cardinalidadMaxima = cardMax;
	}

	public TipoAtributo getTipo() {
		return this.tipo;
	}

	public String getCardinalidadMaxima() {
		return this.cardinalidadMaxima;
	}

	public String getCardinalidadMinima() {
		return this.cardinalidadMinima;
	}

	public Atributo getOriginal() {
		return this.original;
	}

	public String getFormula() {
		return this.formula;
	}

	public List<Atributo> getAtributos() {
		return this.atributos;
	}
}
