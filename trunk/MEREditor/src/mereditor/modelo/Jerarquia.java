package mereditor.modelo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mereditor.modelo.base.Componente;

public class Jerarquia extends Componente {
	public enum TipoJerarquia {
		TOTAL_EXCLUSIVA, TOTAL_SUPERPUESTA, PARCIAL_EXCLUSIVA, PARCIAL_SUPERPUESTA
	}

	protected Entidad generica;
	protected Set<Entidad> derivadas = new HashSet<>();

	protected TipoJerarquia tipo;

	public Jerarquia() {
		super();
	}

	public Jerarquia(String id) {
		super(id);
	}

	public Set<Entidad> getDerivadas() {
		return Collections.unmodifiableSet(derivadas);
	}

	public void setGenerica(Entidad generica) {
		this.generica = generica;
	}

	public Entidad getGenerica() {
		return this.generica;
	}
	
	public TipoJerarquia getTipo() {
		return this.tipo;
	}
	
	@Override
	public String toString() {
		return "Jerarquia: " + this.generica.getNombre();
	}
}
