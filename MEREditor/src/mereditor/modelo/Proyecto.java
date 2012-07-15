package mereditor.modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mereditor.control.DiagramaControl;
import mereditor.modelo.Validacion.EstadoValidacion;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.modelo.validacion.ValidarEquilibrioAtributos;
import mereditor.modelo.validacion.ValidarEquilibrioComponentes;
import mereditor.modelo.validacion.ValidarEquilibrioRelaciones;

import org.apache.commons.lang.StringUtils;

public class Proyecto extends ComponenteNombre implements ProyectoProxy {
	/**
	 * Diagrama raiz del proyecto.
	 */
	protected Diagrama raiz;
	/**
	 * Diagrama que se encuetra abierto.
	 */
	protected Diagrama diagramaActual;
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
	public Diagrama getDiagramaActual() {
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

	/**
	 * Obtener el conjunto de diagramas.
	 * 
	 * @return
	 */
	public Set<Diagrama> getDiagramas() {
		return Componente.filtrarComponentes(Diagrama.class,
				this.componentes.values());
	}

	/**
	 * Toma como nombre de este proyecto el del diagrama raíz.
	 */
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

	@Override
	public void addValidaciones() {
		this.validaciones.add(new ValidarEquilibrioComponentes());
		this.validaciones.add(new ValidarEquilibrioAtributos());
		this.validaciones.add(new ValidarEquilibrioRelaciones());
	}

	@Override
	public String validar() {
		List<String> observaciones = new ArrayList<>();
		List<String> output = new ArrayList<>();

		observaciones.add(super.validar());

		for (Diagrama diagrama : this.getDiagramas()) {
			String resultado = diagrama.validar();

			output.add("--------------------------------------");
			output.add(diagrama.getNombre());
			output.add("--------------------------------------");
			output.add(resultado);

			observaciones.add(resultado);
		}

		while (observaciones.remove(""))
			;
		while (observaciones.remove(null))
			;

		while (output.remove(""))
			;
		while (output.remove(null))
			;

		if (!observaciones.isEmpty()) {
			this.validacion.setObservaciones(StringUtils.join(output, "\n")
					.trim());
			this.validacion
					.setEstado(EstadoValidacion.VALIDADO_CON_OBSERVACIONES);
		} else {
			this.validacion.setEstado(EstadoValidacion.VALIDADO);
			observaciones.add(Validacion.SIN_OBSERVACIONES);
		}

		return StringUtils.join(output, "\n").trim();
	}

}
