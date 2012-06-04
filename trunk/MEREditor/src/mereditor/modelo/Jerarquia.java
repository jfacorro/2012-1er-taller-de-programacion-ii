package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;

public class Jerarquia extends Componente {
	public enum TipoJerarquia {
		TOTAL_EXCLUSIVA, TOTAL_SUPERPUESTA, PARCIAL_EXCLUSIVA, PARCIAL_SUPERPUESTA
	}

	protected Entidad generica;
	protected List<Entidad> derivadas = new LinkedList<Entidad>();

	protected TipoJerarquia tipo;

	public Jerarquia() {
		super();
	}

	public Jerarquia(String id) {
		super(id);
	}

	public List<Entidad> getDerivadas() {
		return derivadas;
	}

	public void setGenerica(Entidad generica) {
		this.generica = generica;
	}

	public Object getGenerica() {
		return this.generica;
	}
}
