package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Diagrama extends ComponenteNombre {

	protected List<Diagrama> diagramas;
	protected List<Componente> componentes;

	public Diagrama(String nombre, String id, String idPadre) {
		super(nombre, id, idPadre);
		this.diagramas = new LinkedList<Diagrama>();
		this.componentes = new LinkedList<Componente>();
	}

	public List<Diagrama> getDiagramas() {
		return diagramas;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

}
