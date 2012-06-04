package mereditor.modelo.base;

public abstract class Componente {
	/**
	 * Id del componente
	 */
	protected String id;

	/**
	 * Id del padre del componente
	 */
	protected String idPadre;

	public Componente() {}

	public Componente(String id, String idPadre) {
		this.id = id;
		this.idPadre = idPadre;
	}

	public String getId() {
		return id;
	}

	public String getIdPadre() {
		return idPadre;
	}
	
	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj == null ? false : this.getId().equals(((Componente)obj).getId());
	}
}
