package mereditor.modelo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Diagrama extends ComponenteNombre {

	protected List<Diagrama> diagramas = new LinkedList<Diagrama>();
	protected List<Componente> componentes = new LinkedList<Componente>();
	protected Validacion validacion = new Validacion();

	public Diagrama() {
		super();
	}

	public Diagrama(String nombre) {
		super(nombre);
	}

	public Diagrama(String nombre, String id) {
		super(nombre, id);
	}

	public Diagrama(String nombre, String id, Componente padre) {
		super(nombre, id, padre);
	}

	public List<Diagrama> getDiagramas() {
		return Collections.unmodifiableList(diagramas);
	}

	public List<Componente> getComponentes() {
		return Collections.unmodifiableList(componentes);
	}

	public Validacion getValidacion() {
		return this.validacion;
	}

	public void agregar(Componente componente) {
		if (Diagrama.class.isInstance(componente))
			this.diagramas.add((Diagrama) componente);
		else
			this.componentes.add(componente);
	}

	@Override
	public boolean contiene(Componente componente) {
		boolean contiene = this.diagramas.contains(componente)
				|| this.componentes.contains(componente);
		if (contiene)
			return contiene;
		for (Componente hijo : this.componentes) {
			contiene = hijo.contiene(componente);
			if (contiene)
				return contiene;
		}
		return false;
	}

	public void eliminar(Componente componente) {
		this.componentes.remove(componente);
	}
}
