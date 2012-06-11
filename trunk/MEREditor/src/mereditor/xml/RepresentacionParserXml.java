package mereditor.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.control.Control;
import mereditor.control.Proyecto;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;
import mereditor.representacion.PList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class RepresentacionParserXml extends ParserXml {

	public RepresentacionParserXml(Proyecto proyecto, String path) throws Exception {
		super();
		File source = new File(path);
		this.root = docBuilder.parse(source).getDocumentElement();
		this.proyecto = proyecto;
	}

	public RepresentacionParserXml(Proyecto proyecto) throws Exception {
		this.proyecto = proyecto;
	}

	/**
	 * Recorre la coleccion de componentes parseados y busca sus
	 * representaciones para cada diagrama en el que estén presentes.
	 */
	public void parsearRepresentacion() {
		// Para cada componente del proyecto
		for (Componente componente : this.proyecto.getComponentes()) {
			// Buscar las representaciones en cada diagrama
			Map<String, PList> representaciones = this.obtenerRepresentaciones(componente.getId());
			// Asignarselas a las figura correspondiente de cada diagrama
			Control<?> control = (Control<?>) componente;
			for (String idDiagrama : representaciones.keySet())
				control.getFigura(idDiagrama).setRepresentacion(representaciones.get(idDiagrama));
		}
	}

	/**
	 * Devuelve un mapa con las representaciones del componente por cada
	 * diagrama en el que se encuentra.
	 * 
	 * @param id
	 *            Id del componente
	 * @return Mapa con los ids de los diagramas como clave y las
	 *         representaciones como valor.
	 */
	public Map<String, PList> obtenerRepresentaciones(String id) {
		HashMap<String, PList> representaciones = new HashMap<>();

		// Buscar todas las representaciones para el id
		String query = String.format(Constants.REPRESENTACION_ID_QUERY, id);
		List<Element> representacionesXml = XmlHelper.query(this.root, query);

		for (Element representacionXml : representacionesXml) {
			Element diagramaXml = XmlHelper.querySingle(representacionXml, Constants.DIAGRAMA_PADRE_QUERY);

			String idDiagrama = this.obtenerId(diagramaXml);
			representaciones.put(idDiagrama, this.obtenerRepresentacion(representacionXml));
		}

		return representaciones;
	}

	/**
	 * Parsea un elemento de representación básico con posición y dimensión.
	 * 
	 * @param elemento
	 * @return
	 */
	protected PList obtenerRepresentacion(Element elemento) {
		PList representacion = new PList();
		for (Element elementoHijo : XmlHelper.query(elemento, "./*")) {
			// Si es una lista de representaciones
			if (elementoHijo.getNodeName() == Constants.REPRESENTACIONES_TAG) {
				representacion.set(elementoHijo.getNodeName(), this.obtenerRepresentaciones(elementoHijo));
			} else {
				PList hijo = this.obtenerRepresentacion(elementoHijo);
				representacion.set(elementoHijo.getNodeName(), hijo);
			}
		}
		for (String nombre : XmlHelper.attributeNames(elemento)) {
			representacion.set(nombre, elemento.getAttribute(nombre));
		}
		return representacion;
	}

	/**
	 * Obtiene una lista de Representaciones parseades en PLists.
	 * 
	 * @param elementoHijo
	 * @return
	 */
	protected List<PList> obtenerRepresentaciones(Element elemento) {
		List<PList> representacion = new ArrayList<>();
		for (Element elementoHijo : XmlHelper.query(elemento, "./*")) {
			PList hijo = this.obtenerRepresentacion(elementoHijo);
			representacion.add(hijo);
		}

		return representacion;
	}

	public Document generarXml() {
		Document doc = this.docBuilder.newDocument();
		this.root = doc.createElement(Constants.PROYECTO_TAG);
		doc.appendChild(this.root);

		this.generarDiagramaXml(this.root, this.proyecto.getRaiz());

		return doc;
	}

	/**
	 * Genera el element de XML de representación de un diagrama y sus hijos.
	 * Agrega el elemento generado al nodo raíz.
	 * 
	 * @param elemento
	 * @param diagrama
	 */
	protected void generarDiagramaXml(Element elemento, Diagrama diagrama) {
		Element diagramaElem = this.agregarElemento(elemento, Constants.DIAGRAMA_TAG);
		this.agregarAtributo(diagramaElem, Constants.ID_ATTR, diagrama.getId());

		// Recorrer todos los componentes del proyecto
		for (Componente componente : this.proyecto.getComponentes()) {
			Control<?> control = (Control<?>) componente;
			Figura<?> figura = control.getFigura(diagrama.getId());
			if (figura != null) {
				PList plist = figura.getRepresentacion();
				if (plist != null) {
					Element reprElement = this.agregarElemento(diagramaElem, Constants.REPRESENTACION_TAG);
					this.agregarAtributo(reprElement, Constants.ID_ATTR, componente.getId());
					this.agregarRepresentacion(reprElement, plist);
				}
			}
		}

		// Recorrer todos los diagramas hijos del principal
		for (Diagrama diagramaHijo : diagrama.getDiagramas()) {
			this.generarDiagramaXml(elemento, diagramaHijo);
		}
	}

	/**
	 * Genera una element Representación XML desde una PList.
	 * 
	 * @param elemento
	 * @param repr
	 */
	@SuppressWarnings("unchecked")
	private void agregarRepresentacion(Element elemento, PList repr) {
		for (String nombre : repr.getNames()) {
			Object valor = repr.get(nombre);

			if (valor instanceof PList) {
				// Si el valor es una PList entonces armar un elemento
				Element elemHijo = this.agregarElemento(elemento, nombre);
				this.agregarRepresentacion(elemHijo, (PList) valor);
			} else if (valor instanceof List<?>) {
				// Si el valor es una List<> entonces armar una lista de
				// representaciones
				Element representaciones = this.agregarElemento(elemento, Constants.REPRESENTACIONES_TAG);
				for (PList plist : (List<PList>) valor)
					this.agregarRepresentacion(representaciones, plist);
			} else {
				// Si no agregar como par atributo/valor
				this.agregarAtributo(elemento, nombre, valor.toString());
			}
		}
	}
}
