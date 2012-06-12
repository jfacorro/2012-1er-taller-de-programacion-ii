package mereditor.modelo.base;

import java.util.UUID;

public abstract class Componente {
	/**
	 * Id del componente
	 */
	protected String id;

	/**
	 * Id del padre del componente
	 */
	protected Componente padre;

	public Componente() {
		this(UUID.randomUUID().toString());
	}

	public Componente(String id) {
		this.id = id;
	}

	public Componente(String id, Componente padre) {
		this.id = id;
		this.padre = padre;
	}

	public String getId() {
		return id;
	}

	public Componente getPadre() {
		return padre;
	}

	public void setPadre(Componente padre) {
		this.padre = padre;
	}

	/**
	 * Implementación de la evaluación de igualdad por
	 * comparación de ids de componentes.
	 */
	@Override
	public boolean equals(Object obj) {
		return obj == null ? false : this.getId().equals(((Componente) obj).getId());
	}

	/**
	 * Indica si la instancia de este componente es de la clase que se pasa por
	 * parámetro.
	 * 
	 * @param tipoComponente
	 * @return
	 */
	public boolean es(Class<?> tipoComponente) {
		return tipoComponente.isInstance(this);
	}

	/**
	 * Indica si este componente tiene al especificado por parámetro como hijo.
	 * 
	 * @param componente
	 * @return
	 */
	public boolean contiene(Componente componente) {
		return false;
	}
}
