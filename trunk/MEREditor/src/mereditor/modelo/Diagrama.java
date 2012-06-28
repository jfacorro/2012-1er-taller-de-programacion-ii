package mereditor.modelo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Diagrama extends ComponenteNombre {

	protected Set<Diagrama> diagramas = new HashSet<Diagrama>();
	protected Set<Componente> componentes = new HashSet<Componente>();
	protected Validacion validacion = new Validacion();

	public Diagrama() {
		super();
	}

	public Diagrama(String nombre) {
		super(nombre);
	}

	public Diagrama(String nombre, String id) {
		super(nombre, id);
	}

	public Diagrama(String nombre, String id, Componente padre) {
		super(nombre, id, padre);
	}

	public Set<Diagrama> getDiagramas() {
		return Collections.unmodifiableSet(diagramas);
	}

	public Set<Componente> getComponentes() {
		return Collections.unmodifiableSet(componentes);
	}

	/**
	 * Obtiene las entidades de este diagrama y sus ancestros.
	 * 
	 * @param incluirAncestros
	 *            Indica si se deben incluir las entidades de los ancestros.
	 * @return
	 */
	public Set<Entidad> getEntidades(boolean incluirAncestros) {
		Set<Entidad> entidades = Componente.filtrarComponentes(Entidad.class,
				this.componentes);

		if (this.getPadre() != null && incluirAncestros) {
			Diagrama diagrama = (Diagrama) this.getPadre();
			entidades.addAll(diagrama.getEntidades(true));
		}

		return entidades;
	}

	public Set<Entidad> getEntidades() {
		return this.getEntidades(false);
	}

	public Set<Relacion> getRelaciones() {
		return Componente.filtrarComponentes(Relacion.class, this.componentes);
	}

	public Set<Jerarquia> getJerarquias() {
		return Componente.filtrarComponentes(Jerarquia.class, this.componentes);
	}

	public Validacion getValidacion() {
		return this.validacion;
	}

	/**
	 * Agrega el componente a este diagrama. Si es un Diagrama lo agrega a
	 * conjunto de diagramas de lo contrario al conjunto de componentes.
	 * 
	 * @param componente
	 */
	public void agregar(Componente componente) {
		componente.setPadre(this);
		if (Diagrama.class.isInstance(componente))
			this.diagramas.add((Diagrama) componente);
		else
			this.componentes.add(componente);
	}

	@Override
	public boolean contiene(Componente componente) {
		boolean contiene = this.diagramas.contains(componente)
				|| this.componentes.contains(componente);
		if (contiene)
			return contiene;
		for (Componente hijo : this.componentes) {
			contiene = hijo.contiene(componente);
			if (contiene)
				return contiene;
		}
		return false;
	}

	/**
	 * Quita el componente hijo de este diagrama.
	 * 
	 * @param componente
	 */
	public void eliminar(Componente componente) {
		if (Diagrama.class.isInstance(componente))
			this.diagramas.remove((Diagrama) componente);
		else
			this.componentes.remove(componente);
	}

	public Entidad getEntidadByNombre(String nombre) {
		for(Entidad entidad : this.getEntidades())
			if(nombre.equals(entidad.getNombre()))
				return entidad;

		return null;
	}
}
