package mereditor.modelo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mereditor.modelo.Entidad.Identificador;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteAtributos;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.modelo.validacion.GeneradorDeObservaciones;

public class Atributo extends ComponenteNombre implements ComponenteAtributos {
	public enum TipoAtributo {
		CARACTERIZACION, DERIVADO_COPIA, DERIVADO_CALCULO
	}

	protected TipoAtributo tipo;
	protected String cardinalidadMinima = "";
	protected String cardinalidadMaxima = "";
	protected Set<Atributo> atributos = new HashSet<Atributo>();

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
			String cardMax, TipoAtributo tipo, Set<Atributo> atributos) {
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

	public void setCardinalidadMaxima(String cardinalidad) {
		this.cardinalidadMaxima = cardinalidad;
	}

	public String getCardinalidadMinima() {
		return this.cardinalidadMinima;
	}

	public void setCardinalidadMinima(String cardinalidad) {
		this.cardinalidadMinima = cardinalidad;
	}

	public Atributo getOriginal() {
		return this.original;
	}

	public void setOriginal(Atributo original) {
		this.original = original;
	}

	public String getFormula() {
		return this.formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void addAtributo(Atributo atributo) {
		this.atributos.add(atributo);
		atributo.setPadre(this);
	}

	public void removeAtributo(Atributo atributo) {
		this.atributos.remove(atributo);
	}

	public Collection<Atributo> getAtributos() {
		return Collections.unmodifiableCollection(this.atributos);
	}

	/**
	 * Indica si tiene atributos hijos.
	 * 
	 * @return
	 */
	public boolean esCompuesto() {
		return !this.atributos.isEmpty();
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
		if (Entidad.class.isInstance(this.getPadre())) {
			Set<Identificador> identificadores = ((Entidad) this.getPadre()).identificadores;

			for (Identificador identificador : identificadores) {
				if (identificador.contiene(this)
						&& identificador.getEntidades().isEmpty()
						&& identificador.getAtributos().size() == 1)
					return true;
			}
		}

		return false;
	}

	@Override
	public String validar() {
		GeneradorDeObservaciones gen = new GeneradorDeObservaciones();
		if (this.tipo == null) {
			gen.agregarCaracteristicaNoDefinida("Tipo");
		} else if (this.tipo.equals(TipoAtributo.DERIVADO_CALCULO)
				&& formula == null) {
			gen.agregarCaracteristicaNoDefinida("Formula");
		} else if (this.tipo.equals(TipoAtributo.DERIVADO_COPIA)
				&& this.original == null) {
			gen.agregarCaracteristicaNoDefinida("Atributo Original");
		}

		if (this.cardinalidadMinima.isEmpty()) {
			gen.agregarCaracteristicaNoDefinida("Cardinalidad Minima");
		}
		if (this.cardinalidadMaxima.isEmpty()) {
			gen.agregarCaracteristicaNoDefinida("Cardinalidad Maxima");
		}
		for (Atributo a : this.atributos) {
			gen.observacionSobreItemDeColeccion(a.getNombre(), a.validar());
		}
		return gen.getObservaciones();
	}
}
