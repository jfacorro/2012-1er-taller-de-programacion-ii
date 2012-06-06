package mereditor.modelo.base;

public abstract class ComponenteNombre extends Componente {
	
	protected String nombre;
	
	public ComponenteNombre() {}
	
	public ComponenteNombre(String nombre, String id) {
		super (id);
		this.nombre= nombre;
	}

	public ComponenteNombre(String nombre, String id, Componente padre) {
		super (id, padre);
		this.nombre= nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
}
