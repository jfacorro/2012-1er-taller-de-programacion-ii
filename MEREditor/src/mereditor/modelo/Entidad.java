package mereditor.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.modelo.base.ComponenteAtributos;

public class Entidad extends ComponenteNombre implements ComponenteAtributos {

	public enum TipoEntidad {
		MAESTRA_COSA, MAESTRA_DOMINIO, TRANSACCIONAL_HISTORICA, TRANSACCIONAL_PROGRAMADA
	}

	protected TipoEntidad tipo = TipoEntidad.MAESTRA_COSA;
	protected Set<Atributo> atributos = new HashSet<Atributo>();
	protected Set<Identificador> identificadores = new HashSet<Identificador>();

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
	
	public void removeAtributo(Atributo atributo) {
		this.atributos.remove(atributo);
		// Lista donde se guardan los identificadores a editar
		// se debe hacer de esta forma dado que no se puede modificar
		// una collection que se esta recorriendo con un iterador.
		List<Identificador> identificadores = new ArrayList<>();

		for(Identificador identificador : this.identificadores)
			if(identificador.contiene(atributo))
				identificadores.add(identificador);
		
		for(Identificador identificador : this.identificadores)
				identificador.removeAtributo(atributo);
	}

	public Collection<Atributo> getAtributos() {
		return Collections.unmodifiableCollection(this.atributos);
	}

	public Set<Identificador> getIdentificadores() {
		return Collections.unmodifiableSet(this.identificadores);
	}

	public TipoEntidad getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoEntidad tipo) {
		this.tipo = tipo;
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
			if (this.atributos.contains(atributo))
				throw new RuntimeException(
						"Un atributo no puede estar dos veces en el mismo identificador.");

			if (!this.entidad.contiene(atributo))
				throw new RuntimeException(
						"Un atributo debe pertenecer a la entidad para ser identificador.");

			this.atributos.add(atributo);
		}
		
		public void removeAtributo(Atributo atributo) {
			this.atributos.remove(atributo);
			this.verificarVacio();
		}

		public void addEntidad(Entidad entidad) {
			if (this.entidad.equals(entidad))
				throw new RuntimeException(
						"Una Entidad no puede ser su propio identificador.");

			this.entidades.add(entidad);
		}
		
		public void removeEntidad(Entidad entidad) {
			this.entidades.remove(entidad);
			this.verificarVacio();
		}
		
		/**
		 * Verifica si el identificador no tiene ningun atributo
		 * o entidad y lo elimina en ese caso.
		 */
		private void verificarVacio() {
			if(this.atributos.size() == 0 && this.entidades.size() == 0)
				this.entidad.identificadores.remove(this);			
		}

		public List<Atributo> getAtributos() {
			return Collections.unmodifiableList(atributos);
		}

		public List<Entidad> getEntidades() {
			return Collections.unmodifiableList(entidades);
		}

		public boolean contiene(Componente componente) {
			return this.atributos.contains(componente)
					|| this.entidades.contains(componente);
		}
	}
}
