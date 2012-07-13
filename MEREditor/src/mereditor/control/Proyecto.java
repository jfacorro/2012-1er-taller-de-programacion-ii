package mereditor.control;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mereditor.modelo.Atributo;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.Relacion;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public class Proyecto implements ProyectoProxy {
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
	public Proyecto() {
		this.validacion = new Validacion();
	}

	/**
	 * Constructor para crear un proyecto con un diagrama con nombre.
	 * 
	 * @param nombre
	 * @throws Exception
	 */
	public Proyecto(String nombre) {
		this();
		this.raiz = new DiagramaControl();
		this.raiz.setNombre(nombre);
		this.agregar(this.raiz);
	}

	/**
	 * Obtener diagrama raiz del proyecto.
	 * 
	 * @return
	 */
	public Diagrama getDiagramaRaiz() {
		return raiz;
	}

	/**
	 * Establece el diagrama raíz del proyecto.
	 * 
	 * @return
	 */
	public void setDiagramaRaiz(Diagrama raiz) {
		if (this.raiz != null)
			throw new RuntimeException("El diagrama raiz ya esta establecido.");

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
	 * Devuelve el path donde se guardó el archivo
	 */
	public String getPath() {
		return this.path;
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

	public String getNombre() {
		return this.raiz.getNombre();
	}

	@Override
	public Set<Entidad> getEntidadesDisponibles() {
		// Obtener las entidades de los ancestros
		return this.diagramaActual.getEntidades(true);
	}

	@Override
	public Set<Entidad> getEntidadesDiagrama() {
		return this.diagramaActual.getEntidades(false);
	}

	@Override
	public Collection<Atributo> getAtributosDiagrama() {
		return this.diagramaActual.getAtributos(false);
	}

	@Override
	public Set<Relacion> getRelacionesDisponibles() {
		return this.diagramaActual.getRelaciones(true);
	}

	@Override
	public Set<Relacion> getRelacionesDiagrama() {
		return this.diagramaActual.getRelaciones(false);
	}

	@Override
	public Set<Jerarquia> getJerarquiasDisponibles() {
		return this.diagramaActual.getJerarquias(true);
	}

	@Override
	public Set<Jerarquia> getJerarquiasDiagrama() {
		return this.diagramaActual.getJerarquias(false);
	}	
}
