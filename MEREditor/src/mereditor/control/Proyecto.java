package mereditor.control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.modelo.Diagrama;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public class Proyecto {
	/**
	 * Diagrama raiz del proyecto.
	 */
	protected DiagramaControl raiz;
	/**
	 * Validación del proyecto entero.
	 */
	protected Validacion validacion;
	/**
	 * Path al archivo donde se guardo por ultima vez el proyecto abierto.
	 */
	protected String path;

	/**
	 * Mapa de todos los componentes presentes en el proyecto.
	 */
	protected Map<String, Componente> componentes = new HashMap<>();
	
	/**
	 * Constructor para crear un nuevo proyecto.
	 * @throws Exception 
	 */
	public Proyecto() throws Exception {
		this.raiz = new DiagramaControl();
		this.agregar(this.raiz);
		this.validacion = new Validacion();
	}

	/**
	 * Constructor para crear un proyecto con un diagrama con nombre.
	 * @param nombre
	 * @throws Exception 
	 */
	public Proyecto(String nombre) throws Exception {
		this();
		this.raiz.setNombre(nombre);
	}

	public Diagrama getRaiz() {
		return raiz;
	}

	public void setRaiz(Diagrama raiz) throws Exception {
		this.raiz = (DiagramaControl)raiz;
		if(!this.componentes.containsKey(raiz.getId()))
			this.agregar(raiz);
	}
	
	public Validacion getValidacion() {
		return this.validacion;		
	}

	public void setValidacion(Validacion validacion) {
		this.validacion = validacion;		
	}

	/**
	 * Agregar un componente nuevo al proyecto.
	 * @param componente
	 * @throws Exception 
	 */
	public void agregar(Componente componente) throws Exception {
		if(this.componentes.containsKey(componente.getId()))
			throw new Exception("Un mismo componente no se puede agregar dos veces. Id: " + componente.getId());
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

	public void dibujar(Figure contenedor, String idDiagrama) {
		DiagramaControl diagrama = (DiagramaControl)this.componentes.get(idDiagrama);
		diagrama.dibujar(contenedor, idDiagrama);
	}
	
	public void dibujar(Figure contenedor) {
		contenedor.removeAll();
		this.raiz.dibujar(contenedor, null);		
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponentesPath() {
		File file = new File(path);
		String nombre = file.getName().replaceFirst("[.][^.]+$", "");
		return nombre + "-comp.xml";
	}
	
	public String getRepresentacionPath() {
		File file = new File(path);
		String nombre = file.getName().replaceFirst("[.][^.]+$", "");
		return nombre + "-rep.xml";
	}
}
