package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.Entidad.Identificador;
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

	public Atributo(String nombre, String id, Componente padre, String min, String cardMax, TipoAtributo tipo,
			List<Atributo> atributos) {
		this(nombre, id, padre);
		this.atributos = atributos;
		this.cardinalidadMinima = min;
		this.cardinalidadMaxima = cardMax;
	}

	public TipoAtributo getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoAtributo tipo) {
		this.tipo = tipo;
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

	@Override
	public boolean contiene(Componente componente) {
		boolean contiene = this.atributos.contains(componente);
		if (contiene)
			return contiene;
		for (Componente hijo : this.atributos) {
			contiene = hijo.contiene(componente);
			if (contiene)
				return contiene;
		}
		return false;
	}

	/**
	 * Indica si el atributo es un identificador principal.
	 * 
	 * @return
	 */
	public boolean esIdentificador() {
		if (Entidad.class.isInstance(this.padre)) {
			List<Identificador> identificadores = ((Entidad) this.padre).identificadores;

			for (Identificador identificador : identificadores) {
				if (identificador.contiene(this) && identificador.getEntidades().isEmpty() && identificador.getAtributos().size() == 1)
					return true;
			}
		}

		return false;
	}
}
