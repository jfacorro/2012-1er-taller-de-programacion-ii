package mereditor.modelo.base;

public abstract class ComponenteNombre extends Componente {
	
	protected String nombre;
	
	public ComponenteNombre() {}
	
	public ComponenteNombre(String nombre, String id, String idPadre) {
		super (id, idPadre);
		this.nombre= nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
