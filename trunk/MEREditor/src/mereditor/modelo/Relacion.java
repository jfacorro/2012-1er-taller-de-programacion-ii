package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;

public class Relacion extends ComponenteNombre {
	enum TipoRelacion {
		ASOCIACION,
		COMPOSICION
	}
	
	protected TipoRelacion tipo;
	protected List<EntidadRelacion> entidades;
	protected List<Atributo> atributos;

	/*
	 * Constructor
	 */
	public Relacion(String nombre){
		super(nombre);
		
		this.atributos = new LinkedList<Atributo>();
		this.entidades = new LinkedList<EntidadRelacion>();
	}
	
	/*
	 * Getter y setters
	 */		
	public TipoRelacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoRelacion tipo) {
		this.tipo = tipo;
	}

	public List<EntidadRelacion> getEntidades() {
		return entidades;
	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	/*
	 * Contiene la entidad que pertence a la relacion 
	 * y su información asociada a la misma.
	 */
	public class EntidadRelacion{
		protected Entidad entidad;
		protected String rol;
		protected int cardinalidadMinima;
		protected int cardinalidadMaxima;
		
		public EntidadRelacion(Entidad entidad, String rol,
				int cardinalidadMinima, int cardinalidadMaxima) {
			this.entidad = entidad;
			this.rol = rol;
			this.cardinalidadMinima = cardinalidadMinima;
			this.cardinalidadMaxima = cardinalidadMaxima;
		}

		public Entidad getEntidad() {
			return entidad;
		}

		public void setEntidad(Entidad entidad) {
			this.entidad = entidad;
		}

		public String getRol() {
			return rol;
		}

		public void setRol(String rol) {
			this.rol = rol;
		}

		public int getCardinalidadMinima() {
			return cardinalidadMinima;
		}

		public void setCardinalidadMinima(int cardinalidadMinima) {
			this.cardinalidadMinima = cardinalidadMinima;
		}

		public int getCardinalidadMaxima() {
			return cardinalidadMaxima;
		}

		public void setCardinalidadMaxima(int cardinalidadMaxima) {
			this.cardinalidadMaxima = cardinalidadMaxima;
		}		
	}
}
