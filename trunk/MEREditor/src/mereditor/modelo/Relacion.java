package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Relacion extends ComponenteNombre {
	public enum TipoRelacion {
		ASOCIACION, COMPOSICION
	}

	protected TipoRelacion tipo;
	protected List<Atributo> atributos = new LinkedList<Atributo>();
	protected List<EntidadRelacion> participantes = new LinkedList<EntidadRelacion>();

	public Relacion() {
	}

	public Relacion(String nombre, String id, TipoRelacion tipo) {
		super(nombre, id);
		this.tipo = tipo;
	}

	public void agregarAtributo(Componente atributo) {
		atributos.add((Atributo) atributo);
	}

	public void agregarParticipante(EntidadRelacion participante) {
		participantes.add(participante);
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

	public List<EntidadRelacion> getParticipantes() {
		return participantes;
	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	/*
	 * Contiene la entidad que pertence a la relacion y su informacion
	 * asociada a la misma.
	 */
	public class EntidadRelacion {
		protected Entidad entidad;
		protected String rol;
		protected String cardinalidadMinima;
		protected String cardinalidadMaxima;

		public EntidadRelacion(Entidad entidad, String rol, String cardinalidadMinima, String cardinalidadMaxima) {
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

		public String getCardinalidadMinima() {
			return cardinalidadMinima;
		}

		public void setCardinalidadMinima(String cardinalidadMinima) {
			this.cardinalidadMinima = cardinalidadMinima;
		}

		public void setCardinalidadMaxima(String cardinalidadMaxima) {
			this.cardinalidadMaxima = cardinalidadMaxima;
		}
		
		@Override
		public String toString() {
			String label = "(" + this.cardinalidadMinima + ", " + this.cardinalidadMinima +")";
			if(this.rol != null) label += " " + this.rol;
			return  label;
		}
	}
}
