package mereditor.modelo;

import java.util.HashMap;
import java.util.Map;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Proyecto extends ComponenteNombre {
	/**
	 * Diagrama raiz del proyecto
	 */
	private Diagrama raiz;

	private Map<String, Componente> componentes = new HashMap<>();

	public Proyecto(String nombre) {
		super(nombre);
		this.raiz = new Diagrama();
		this.raiz.setNombre(nombre);
	}

	public Diagrama getRaiz() {
		return raiz;
	}

	public void setRaiz(Diagrama raiz) {
		this.raiz = raiz;
	}

	public void agregarComponente(Componente componente) {
		this.componentes.put(componente.getId(), componente);
	}

	@SuppressWarnings("unchecked")
	public <T extends Componente> T getComponentePorId(String id) {
		return (T) this.componentes.get(id);
	}
}
