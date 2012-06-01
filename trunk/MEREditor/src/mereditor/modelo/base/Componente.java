package mereditor.modelo.base;

public abstract class Componente {
	/**
	 * Id del componente
	 */
	String id;

	/**
	 * Id del padre del componente
	 */
	String idPadre;

	public Componente(String id, String idPadre) {
		this.id = id;
		this.idPadre = idPadre;
	}

	public Componente() {

	}

	public String getId() {
		return id;
	}

	public String getIdPadre() {
		return idPadre;
	}
}
