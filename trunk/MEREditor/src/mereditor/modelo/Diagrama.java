package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Diagrama extends ComponenteNombre {

	protected List<Diagrama> diagramas = new LinkedList<Diagrama>();
	protected List<Componente> componentes = new LinkedList<Componente>();
	protected Validacion validacion = new Validacion();
	
	public Diagrama() {}

	public Diagrama(String nombre, String id, String idPadre) {
		super(nombre, id, idPadre);
	}

	public List<Diagrama> getDiagramas() {
		return diagramas;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public Validacion getValidacion() {
		return this.validacion;
	}

}
