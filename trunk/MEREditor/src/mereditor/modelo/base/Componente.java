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
	
	@Override
	public boolean equals(Object obj) {
		return obj == null ? false : this.getId().equals(((Componente)obj).getId());
	}

	public boolean es(Class<?> tipoComponente) {
		return tipoComponente.isInstance(this);
	}
	
	public boolean contiene(Componente componente) {
		return false;
	}	
}
