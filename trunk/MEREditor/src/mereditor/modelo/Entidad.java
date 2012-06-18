package mereditor.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Entidad extends ComponenteNombre {

	public enum TipoEntidad {
		MAESTRA_COSA, MAESTRA_DOMINIO, TRANSACCIONAL_HISTORICA, TRANSACCIONAL_PROGRAMADA
	}

	protected List<Atributo> atributos = new LinkedList<Atributo>();
	/**
	 * Pueden ser tanto Atributos como Entidades
	 */
	protected List<Identificador> identificadores = new ArrayList<Identificador>();
	protected TipoEntidad tipo;

	public Entidad() {
		super();
	}
	
	public Entidad(String nombre) {
		super(nombre);
	}

	public Entidad(String nombre, String id, TipoEntidad tipo) {
		super(nombre, id);
		this.tipo = tipo;
	}

	public Entidad(String nombre, String id, Componente padre, TipoEntidad tipo) {
		super(nombre, id, padre);
		this.tipo = tipo;
	}

	public void agregarIdentificador(Identificador identificador) {
		identificadores.add(identificador);
	}

	public void addAtributo(Atributo atributo) {
		this.atributos.add(atributo);
	}

	public List<Atributo> getAtributos() {
		return this.atributos;
	}
	
	public List<Identificador> getIdentificadores() {
		return this.identificadores;
	}

	public TipoEntidad getTipo() {
		return this.tipo;
	}
	
	@Override
	public boolean contiene(Componente componente) {
		boolean contiene = this.atributos.contains(componente);
		if(contiene) return contiene;
		for(Componente hijo : this.atributos) {
			contiene = hijo.contiene(componente);
			if(contiene) return contiene;
		}
		return false;
	}
	
	public class Identificador {
		private Entidad entidad;
		private List<Atributo> atributos = new ArrayList<>();
		private List<Entidad> entidades = new ArrayList<>();
		
		public Identificador(Entidad entidad) {
			if (entidad == null)
				throw new RuntimeException("Entidad no puede ser null.");

			this.entidad = entidad;
		}
		
		public void addAtributo(Atributo atributo) {
			if(this.atributos.contains(atributo))
				throw new RuntimeException("Un atributo no puede estar dos veces en el mismo identificador.");

			if(!this.entidad.contiene(atributo))
				throw new RuntimeException("Un atributo debe pertenecer a la entidad para ser identificador.");
				
			this.atributos.add(atributo);
		}
		
		public void addEntidad(Entidad entidad) {
			if(this.entidad.equals(entidad))
				throw new RuntimeException("Una Entidad no puede ser su propio identificador.");
			
			this.entidades.add(entidad);
		}
		
		public List<Atributo> getAtributos() {
			return Collections.unmodifiableList(atributos);
		}

		public List<Entidad> getEntidades() {
			return Collections.unmodifiableList(entidades);
		}
		
		public boolean contiene(Componente componente) {
			return this.atributos.contains(componente) || this.entidades.contains(componente);
		}
	}
}
