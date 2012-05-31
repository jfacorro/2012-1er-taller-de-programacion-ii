package mereditor.modelo.base;

public abstract class Componente {
	String idComponente;
	String idContenedor;
	
	public Componente (String idComp, String idCont ) {
		idComponente= idComp;
		idContenedor= idCont;
	}
	public Componente () {
		
	}
	public String getIdComponente() {
		return idComponente;
	}
	public String getIdContenedor() {
		return idContenedor;
	}
}
