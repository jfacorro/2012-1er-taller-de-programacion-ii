package mereditor.control;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.Relacion;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public class Proyecto {
	/**
	 * Diagrama raiz del proyecto.
	 */
	protected DiagramaControl raiz;
	/**
	 * Diagrama que se encuetra abierto.
	 */
	protected DiagramaControl diagramaActual;
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
	 * 
	 * @throws Exception
	 */
	public Proyecto() throws Exception {
		this.raiz = new DiagramaControl();
		this.agregar(this.raiz);
		this.validacion = new Validacion();
	}

	/**
	 * Constructor para crear un proyecto con un diagrama con nombre.
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public Proyecto(String nombre) throws Exception {
		this();
		this.raiz.setNombre(nombre);
	}

	/**
	 * Obtener diagrama raiz del proyecto.
	 * 
	 * @return
	 */
	public Diagrama getRaiz() {
		return raiz;
	}

	/**
	 * Establece el diagrama raíz del proyecto.
	 * 
	 * @return
	 */
	public void setRaiz(Diagrama raiz) throws Exception {
		this.raiz = (DiagramaControl) raiz;
		if (!this.componentes.containsKey(raiz.getId()))
			this.agregar(raiz);
	}

	/**
	 * Devuelve la validación de todo el proyecto.
	 * 
	 * @return
	 */
	public Validacion getValidacion() {
		return this.validacion;
	}

	/**
	 * Establece la validación de todo el proyecto.
	 * 
	 * @param validacion
	 */
	public void setValidacion(Validacion validacion) {
		this.validacion = validacion;
	}

	/**
	 * Establece el diagrama actual
	 * 
	 * @param id
	 */
	public void setDiagramaActual(String id) {
		if (this.componentes.containsKey(id)) {
			Componente diagrama = this.componentes.get(id);
			if (DiagramaControl.class.isInstance(diagrama))
				this.diagramaActual = (DiagramaControl) diagrama;
		}
	}

	/**
	 * Devuelve el diagrama actual.
	 * 
	 * @return
	 */
	public DiagramaControl getDiagramaActual() {
		return this.diagramaActual;
	}

	/**
	 * Agregar un componente nuevo al proyecto y al diagrama actual si es que
	 * hay uno establecido.
	 * 
	 * @param componente
	 * @throws Exception
	 */
	public void agregar(Componente componente) {
		if (!this.componentes.containsKey(componente.getId()))
			this.componentes.put(componente.getId(), componente);

		if (this.diagramaActual != null)
			this.diagramaActual.agregar(componente);
	}

	/**
	 * Devuelve el componente que tiene el id.
	 * 
	 * @param id
	 * @return
	 */
	public Componente getComponente(String id) {
		return this.componentes.get(id);
	}

	/**
	 * Devuelve la coleccion de componentes.
	 * 
	 * @return
	 */
	public Collection<Componente> getComponentes() {
		return Collections.unmodifiableCollection(this.componentes.values());
	}

	/**
	 * Indica si el proyecto tiene el componente con el id indicado.
	 * 
	 * @param id
	 * @return
	 */
	public boolean contiene(String id) {
		return this.componentes.containsKey(id);
	}

	/**
	 * Dibujar el diagrama del id especificado.
	 * 
	 * @param contenedor
	 * @param idDiagrama
	 */
	public void dibujar(Figure contenedor, String idDiagrama) {
		DiagramaControl diagrama = (DiagramaControl) this.componentes
				.get(idDiagrama);
		diagrama.dibujar(contenedor, idDiagrama);
	}

	/**
	 * Establecer el path donde se debe guardar el proyecto.
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Obtener el path del archivo de componentes.
	 * 
	 * @return
	 */
	public String getComponentesPath() {
		File file = new File(path);
		String nombre = file.getName().replaceFirst("[.][^.]+$", "");
		return nombre + "-comp.xml";
	}

	/**
	 * Obtener el path del archivo de representacion.
	 * 
	 * @return
	 */
	public String getRepresentacionPath() {
		File file = new File(path);
		String nombre = file.getName().replaceFirst("[.][^.]+$", "");
		return nombre + "-rep.xml";
	}

	/**
	 * Obtener el conjunto de entidades.
	 * 
	 * @return
	 */
	public Set<Entidad> getEntidades() {
		return Componente.filtrarComponentes(Entidad.class,
				this.componentes.values());
	}

	/**
	 * Obtener el conjunto de relaciones.
	 * 
	 * @return
	 */
	public Set<Relacion> getRelaciones() {
		return Componente.filtrarComponentes(Relacion.class,
				this.componentes.values());
	}

	/**
	 * Obtener el conjunto de jerarquias.
	 * 
	 * @return
	 */
	public Set<Jerarquia> getJerarquias() {
		return Componente.filtrarComponentes(Jerarquia.class,
				this.componentes.values());
	}
}
