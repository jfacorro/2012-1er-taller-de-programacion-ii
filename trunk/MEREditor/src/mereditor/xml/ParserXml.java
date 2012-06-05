package mereditor.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mereditor.base.Representacion;
import mereditor.control.Control;
import mereditor.modelo.Atributo;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.geometry.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParserXml {

	protected Element root;
	protected Element rootRepresentacion;
	protected Map<String, Componente> componentes = new HashMap<String, Componente>();

	public ParserXml(Document modeloXml) {
		this.root = modeloXml.getDocumentElement();
	}

	public ParserXml(Document modeloXml, Document representacionXml) {
		this(modeloXml);
		this.rootRepresentacion = representacionXml.getDocumentElement();
	}

	public ParserXml(String modeloPath, String representacionPath) throws Exception {
		File sourceModelo = new File(modeloPath);
		File sourceRepresentacion = new File(representacionPath);
		DocumentBuilder builder;
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.root = builder.parse(sourceModelo).getDocumentElement();
		this.rootRepresentacion = builder.parse(sourceRepresentacion).getDocumentElement();
	}

	/**
	 * Encuentra, parsea y devuelve el diagrama principal. También carga las
	 * representaciones que haya presentes en el archivo de representaciones
	 * asociado.
	 * 
	 * @return Diagrama principal
	 * @throws Exception
	 */
	public Componente diagramaPrincipal() throws Exception {
		Element diagramaXml = XmlHelper.querySingle(this.root, Constants.DIAGRAMA_QUERY);
		Componente diagrama = this.resolver(this.obtenerId(diagramaXml));
		this.cargarRepresentaciones();
		return diagrama;
	}

	/**
	 * Devuelve el componente con el id asociado. Si no se encuentra registrado
	 * en la tabla de componentes, lo busca en el XML del modelo y lo parsea.
	 * 
	 * @param id
	 * @return Componente parseado
	 * @throws Exception
	 */
	public Componente resolver(String id) throws Exception {
		if (this.componentes.containsKey(id))
			return this.componentes.get(id);

		return this.buscarParsear(id);
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
	public Map<String, Representacion> obtenerRepresentaciones(String id) {
		HashMap<String, Representacion> representaciones = new HashMap<>();

		// Buscar todas las representaciones para el id
		String query = String.format(Constants.REPRESENTACION_ID_QUERY, id);
		List<Element> representacionesXml = XmlHelper.query(this.rootRepresentacion, query);

		for (Element representacionXml : representacionesXml) {
			Element diagramaXml = XmlHelper.querySingle(representacionXml, Constants.DIAGRAMA_PADRE_QUERY);

			String idDiagrama = this.obtenerId(diagramaXml);
			representaciones.put(idDiagrama, this.obtenerRepresentacion(representacionXml));
		}

		return representaciones;
	}

	/**
	 * Registra el componente en la tabla de componentes utilizando el id como
	 * clave.
	 * 
	 * @param componente
	 * @throws Exception
	 */
	void registrar(Componente componente) throws Exception {
		if (componente.getId() == null)
			throw new Exception("No se puede agregar un componente sin identificador.");

		if (this.componentes.containsKey(componente.getId()))
			throw new Exception("Identificador duplicado: " + componente.getId());

		this.componentes.put(componente.getId(), componente);
	}

	/**
	 * Obtiene todos los componentes hijos de un diagrama.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	List<Componente> obtenerComponentes(Element elemento) throws Exception {
		List<Element> diagramasXml = XmlHelper.query(elemento, Constants.DIAGRAMA_COMPONENTES_QUERY);
		List<Componente> componentes = new ArrayList<>();

		for (Element diagramaXml : diagramasXml)
			componentes.add(this.obtenerReferencia(diagramaXml));

		return componentes;
	}

	/**
	 * Obtiene todos los diagramas hijos de un diagrama.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	List<Componente> obtenerDiagramas(Element elemento) throws Exception {
		List<Element> diagramasXml = XmlHelper.query(elemento, Constants.DIAGRAMA_DIAGRAMAS_QUERY);
		List<Componente> diagramas = new ArrayList<>();

		for (Element diagramaXml : diagramasXml)
			diagramas.add(this.obtenerReferencia(diagramaXml));

		return diagramas;
	}

	/**
	 * Obtiene el objeto de validacion asociado con un diagrama.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	Validacion obtenerValidacion(Element elemento) throws Exception {
		Element validacionXml = XmlHelper.querySingle(elemento, Constants.VALIDACION_QUERY);
		ValidacionXml validacion = (ValidacionXml) this.mapElement(validacionXml);
		validacion.fromXml(validacionXml, this);
		return validacion;
	}

	/**
	 * Obtiene el valor del estado de un elemento de validacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerEstado(Element elemento) {
		String estado = elemento.getAttribute(Constants.ESTADO_ATTR);
		return estado;
	}

	/**
	 * Obtiene las observacion de un elemento de validacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerObservaciones(Element elemento) {
		Element observacionesXml = XmlHelper.querySingle(elemento, Constants.OBSERVACIONES_QUERY);
		return observacionesXml == null ? null : observacionesXml.getTextContent();
	}

	/**
	 * Obtiene el valor del atributo id de un elemento.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerId(Element elemento) {
		return elemento.getAttribute(Constants.ID_ATTR);
	}

	/**
	 * Obtiene el valor contenido en el tag hijo "Nombre" de un elemento.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerNombre(Element elemento) {
		return XmlHelper.querySingle(elemento, Constants.NOMBRE_TAG).getTextContent();
	}

	/**
	 * Obtiene el valor del atributo tipo de un elemento.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerTipo(Element elemento) {
		return elemento.getAttribute(Constants.TIPO_ATTR);
	}

	/**
	 * Obtiene una lista de atributos correspondientes a un componente.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	List<Atributo> obtenerAtributos(Element elemento) throws Exception {
		List<Element> atributosXml = XmlHelper.query(elemento, Constants.ATRIBUTOS_QUERY);
		List<Atributo> atributos = new ArrayList<>();

		for (Element atributoXml : atributosXml) {
			Atributo atributo = (Atributo) this.resolver(this.obtenerId(atributoXml));
			atributos.add(atributo);
		}

		return atributos;
	}

	/**
	 * Obtiene una lista de los identificadores internos de una entidad.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	List<Componente> obtenerIdentificadoresInternos(Element elemento) throws Exception {
		List<Element> idsInternosXml = XmlHelper.query(elemento, Constants.IDENTIFICADORES_INTERNOS_QUERY);
		List<Componente> atributos = new ArrayList<>();

		for (Element idInternoXml : idsInternosXml) {
			atributos.add(this.obtenerReferencia(idInternoXml));
		}

		return atributos;
	}

	/**
	 * Obtiene una lista de los identificadores externos de una entidad.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	List<Componente> obtenerIdentificadoresExternos(Element elemento) throws Exception {
		List<Element> idsExternosXml = XmlHelper.query(elemento, Constants.IDENTIFICADORES_EXTERNOS_QUERY);
		List<Componente> identificadores = new ArrayList<>();

		for (Element idExternoXml : idsExternosXml)
			identificadores.add(this.obtenerReferencia(idExternoXml));

		return identificadores;
	}

	/**
	 * Toma el id de referencia del elemento y lo trata de resolver.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	Componente obtenerReferencia(Element elemento) throws Exception {
		String id = elemento.getAttribute(Constants.IDREF_ATTR);
		return this.resolver(id);
	}

	/**
	 * Obtiene la cardinalidad minima y maxima de un atributo o relacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String[] obtenerCardinalidad(Element elemento) {
		Element cardinalidad = XmlHelper.querySingle(elemento, Constants.CARDINALIDAD_QUERY);
		return new String[] { cardinalidad.getAttribute(Constants.CARDINALIDAD_MIN_ATTR),
				cardinalidad.getAttribute(Constants.CARDINALIDAD_MAX_ATTR) };
	}

	/**
	 * Obtiene el valor de la formula de un atributo.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerFormulaAtributo(Element elemento) {
		Element element = XmlHelper.querySingle(elemento, Constants.FORMULA_QUERY);
		return element == null ? null : element.getTextContent();
	}

	/**
	 * Obtiene el atributo original de un atributo de tipo copia.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	Atributo obtenerOriginalAtributo(Element elemento) throws Exception {
		Element element = XmlHelper.querySingle(elemento, Constants.ORIGINAL_QUERY);

		if (element != null)
			return (Atributo) this.resolver(element.getAttribute(Constants.IDREF_ATTR));

		return null;
	}

	/**
	 * Obtiene la entidad generica de una jerarquia.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	Componente obtenerGenerica(Element elemento) throws Exception {
		Element generica = XmlHelper.querySingle(elemento, Constants.GENERICA_QUERY);
		String id = generica.getAttribute(Constants.IDREF_ATTR);

		return this.resolver(id);
	}

	/**
	 * Obtiene la lista de entidades derivadas de una jerarquia.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	List<Componente> obtenerDerivadas(Element elemento) throws Exception {
		List<Element> derivadasXml = XmlHelper.query(elemento, Constants.DERIVADAS_QUERY);
		List<Componente> derivadas = new ArrayList<>();

		for (Element derivadaXml : derivadasXml) {
			String id = derivadaXml.getAttribute(Constants.IDREF_ATTR);
			derivadas.add(this.resolver(id));
		}

		return derivadas;
	}

	/**
	 * Obtiene los elemento de participantes de un elemento relacion.
	 * 
	 * @param elemento
	 * @return
	 */
	List<Element> obtenerParticipantes(Element elemento) {
		return XmlHelper.query(elemento, Constants.PARTICIPANTES_QUERY);
	}

	/**
	 * Obtiene la entidad participante de la relacion.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	Componente obtenerEntidadParticipante(Element elemento) throws Exception {
		Element entidadRefXml = XmlHelper.querySingle(elemento, Constants.ENTIDAD_REF_QUERY);
		return this.obtenerReferencia(entidadRefXml);
	}

	/**
	 * Obtiene el rol de la entidad participante de la relacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerRol(Element elemento) {
		Element rolXml = XmlHelper.querySingle(elemento, Constants.ROL_QUERY);
		return rolXml == null ? null : rolXml.getTextContent();
	}

	/**
	 * Busca un elemento con el id especificado y trata de parsearlo según el
	 * tipo de elemento.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Componente buscarParsear(String id) throws Exception {
		String query = String.format(Constants.ID_QUERY, id);
		List<Element> list = XmlHelper.query(this.root, query);

		if (list.size() == 1)
			return this.parsear(list.get(0));

		throw new Exception("Identificador inexistente o duplicado: " + id);
	}

	/**
	 * Parsea un elemento según la implementacion de la instancia devuelta por
	 * mapElement.
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	private Componente parsear(Element element) throws Exception {
		Xmlizable xmlizable = this.mapElement(element);
		xmlizable.fromXml(element, this);
		return (Componente) xmlizable;
	}

	/**
	 * Devuelve una instancia de la clase correspondiente de parseo según el
	 * nombre del elemento a parsear.
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	private Xmlizable mapElement(Element element) throws Exception {
		switch (element.getNodeName()) {
		case Constants.ENTIDAD_TAG:
			return new EntidadXml();
		case Constants.RELACION_TAG:
			return new RelacionXml();
		case Constants.JERARQUIA_TAG:
			return new JerarquiaXml();
		case Constants.DIAGRAMA_TAG:
			return new DiagramaXml();
		case Constants.ATRIBUTO_TAG:
			return new AtributoXml();
		case Constants.VALIDACION_TAG:
			return new ValidacionXml();
		}

		throw new Exception("No existe un mapeo para: " + element.getNodeName());
	}

	/**
	 * Recorre la coleccion de componentes parseados y busca sus
	 * representaciones para cada diagrama en el que estén presentes.
	 */
	private void cargarRepresentaciones() {
		for (Componente componente : this.componentes.values()) {
			Map<String, Representacion> representaciones = this.obtenerRepresentaciones(componente.getId());
			Control<?> control = (Control<?>) componente;
			for (String idDiagrama : representaciones.keySet())
				control.getFigura(idDiagrama).setRepresentacion(representaciones.get(idDiagrama));
		}
	}

	/**
	 * Parsea un elemento de representación básico con posición y dimensión.
	 * 
	 * @param elemento
	 * @return
	 */
	private Representacion obtenerRepresentacion(Element elemento) {
		Element posicionXml = XmlHelper.querySingle(elemento, Constants.POSICION_QUERY);
		Element dimensionXml = XmlHelper.querySingle(elemento, Constants.DIMENSION_QUERY);

		int x = Integer.parseInt(posicionXml.getAttribute(Constants.X_ATTR));
		int y = Integer.parseInt(posicionXml.getAttribute(Constants.Y_ATTR));
		int ancho = Integer.parseInt(dimensionXml.getAttribute(Constants.ANCHO_ATTR));
		int alto = Integer.parseInt(dimensionXml.getAttribute(Constants.ALTO_ATTR));
		Rectangle rect = new Rectangle(x, y, ancho, alto);

		Representacion representacion = new Representacion();
		representacion.setProperty("rect", rect);
		return representacion;
	}
}
