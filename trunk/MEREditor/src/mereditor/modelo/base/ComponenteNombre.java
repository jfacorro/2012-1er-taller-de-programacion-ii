package mereditor.modelo.base;

public abstract class ComponenteNombre extends Componente {
	
	protected String nombre;
	
	public ComponenteNombre(String nombre, String idEntidad, String idContenedor) {
		super (idEntidad, idContenedor);
		this.nombre= nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
