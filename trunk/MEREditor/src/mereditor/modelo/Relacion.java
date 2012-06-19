package mereditor.modelo;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Relacion extends ComponenteNombre {
	public enum TipoRelacion {
		ASOCIACION, COMPOSICION
	}

	protected TipoRelacion tipo;
	protected List<Atributo> atributos = new ArrayList<>();
	protected List<EntidadRelacion> participantes = new ArrayList<>();

	public Relacion() {
		super();
	}

	public Relacion(String nombre) {
		super(nombre);
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

	@Override
	public boolean contiene(Componente componente) {
		boolean contiene = this.atributos.contains(componente);
		
		if (contiene)
			return contiene;
		
		// Verificar los hijos de los atributos
		for (Componente hijo : this.atributos) {
			contiene = hijo.contiene(componente);
			if (contiene)
				return contiene;
		}

		// Verificar las entidades participantes
		for (EntidadRelacion entidadRelacion : this.participantes) {
			if (entidadRelacion.getEntidad().equals(componente))
				return true;
		}

		return super.contiene(componente);
	}

	/*
	 * Contiene la entidad que pertence a la relacion y su informacion asociada
	 * a la misma.
	 */
	public class EntidadRelacion {
		protected Entidad entidad;
		protected String rol;
		protected String cardinalidadMinima = "1";
		protected String cardinalidadMaxima = "1";

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

		public String getCardinalidadMaxima() {
			return cardinalidadMaxima;
		}

		public void setCardinalidadMinima(String cardinalidadMinima) {
			this.cardinalidadMinima = cardinalidadMinima;
		}

		public void setCardinalidadMaxima(String cardinalidadMaxima) {
			this.cardinalidadMaxima = cardinalidadMaxima;
		}

		@Override
		public String toString() {
			String label = "";
			if (!this.cardinalidadMinima.equals("1") || !this.cardinalidadMaxima.equals("1"))
				label = "(" + this.cardinalidadMinima + ", " + this.cardinalidadMaxima + ")";

			if (this.rol != null)
				label += " " + this.rol;

			return label;
		}
	}
}
