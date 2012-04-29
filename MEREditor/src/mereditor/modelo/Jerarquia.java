package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;

public class Jerarquia extends Componente {
	enum TipoJerarquia {
		TOTAL_EXCLUSIVA,
		TOTAL_SUPERPUESTA,
		PARCIAL_EXCLUSIVA,
		PARCIAL_SUPERPUESTA
	}
	
	protected Componente entidadGenerica;
	protected List<Componente> entidadesDerivadas;
	
	protected TipoJerarquia tipo;
	
	public Jerarquia() {
		super();
		entidadGenerica= null;
		entidadesDerivadas= new LinkedList<Componente> ();
	}
	
	public Jerarquia(String idJerarquia, String idContenedor){
		super (idJerarquia,idContenedor);
		entidadGenerica= null;
		entidadesDerivadas= new LinkedList<Componente> ();
	}

	public List<Componente> getEntidadesDerivadas() {
		return entidadesDerivadas;
	}
	
	public void setEntidadGenerica (Entidad generica){
		entidadGenerica= generica;
	}
	
}
