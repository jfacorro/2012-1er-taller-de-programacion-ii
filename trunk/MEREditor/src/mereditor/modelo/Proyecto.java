package mereditor.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Figure;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import mereditor.control.Control;
import mereditor.control.DiagramaControl;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.base.Componente;

public class Proyecto implements Control<Diagrama> {
	/**
	 * Diagrama raiz del proyecto
	 */
	protected DiagramaControl raiz;

	protected Map<String, Componente> componentes = new HashMap<>();
	
	public Proyecto() {
		this.raiz = new DiagramaControl();		
	}

	public Proyecto(String nombre) {
		this();
		this.raiz.setNombre(nombre);
	}

	public Diagrama getRaiz() {
		return raiz;
	}

	public void setRaiz(Diagrama raiz) {
		this.raiz = (DiagramaControl)raiz;
	}

	public void agregar(Componente componente) {
		this.componentes.put(componente.getId(), componente);
	}

	public Componente getComponente(String id) {
		return this.componentes.get(id);
	}
	
	public List<Componente> getComponentes() {
		return new ArrayList<>(this.componentes.values());
	}

	public boolean contiene(String id) {
		return this.componentes.containsKey(id);
	}

	@Override
	public Figura<Diagrama> getFigura(String idDiagrama) {
		throw new NotImplementedException();
	}
	
	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		DiagramaControl diagrama = (DiagramaControl)this.componentes.get(idDiagrama);
		diagrama.dibujar(contenedor, idDiagrama);
	}
	
	public void dibujar(Figure contenedor) {
		this.raiz.dibujar(contenedor, null);		
	}
}
