package mereditor.modelo.base;

public abstract class Componente {
	/**
	 * Id del componente
	 */
	protected String id;

	/**
	 * Id del padre del componente
	 */
	protected Componente padre;

	public Componente() {}
	
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
}
