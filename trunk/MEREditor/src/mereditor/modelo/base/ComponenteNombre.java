package mereditor.modelo.base;

import java.util.UUID;

public abstract class ComponenteNombre extends Componente implements Comparable<ComponenteNombre> {
	
	protected String nombre = "";
	
	public ComponenteNombre() {
		super();
	}
	
	public ComponenteNombre(String nombre) {
		this(nombre, UUID.randomUUID().toString());
	}
	
	public ComponenteNombre(String nombre, String id) {
		super (id);
		this.nombre= nombre;
	}

	public ComponenteNombre(String nombre, String id, Componente padre) {
		super (id, padre);
		this.nombre = nombre;
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
	
	@Override
	public int compareTo(ComponenteNombre componente) {
		return this.nombre.compareTo(componente.getNombre());
	}
}
