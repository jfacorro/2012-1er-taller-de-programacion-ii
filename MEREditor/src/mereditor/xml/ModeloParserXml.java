package mereditor.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.modelo.Atributo;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ModeloParserXml extends ParserXml {
	
	protected Map<String, Componente> componentes = new HashMap<String, Componente>();

	public ModeloParserXml(Document modeloXml) {
		super(modeloXml);
	}
	
	public ModeloParserXml(String path) throws Exception {
		super(path);
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
	 * Obtiene el objeto de validacion asociado con un diagrama.
	 * 
	 * @param elemento
	 * @return
	 * @throws Exception
	 */
	Validacion obtenerValidacion(Element elemento) throws Exception {
		Element validacionXml = XmlHelper.querySingle(elemento, Constants.VALIDACION_QUERY);
		ValidacionXml validacion = (ValidacionXml) this.mapeoXmlizable(validacionXml);
		validacion.fromXml(validacionXml, this);
		return validacion;
	}
	
	/**
	 * Busca un elemento con el id especificado y trata de parsearlo según el
	 * tipo de elemento.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected Componente buscarParsear(String id) throws Exception {
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
	protected Componente parsear(Element element) throws Exception {
		Xmlizable xmlizable = this.mapeoXmlizable(element);
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
	protected Xmlizable mapeoXmlizable(Element element) throws Exception {
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

	Element crearElemento(String nombre) {
		return XmlHelper.getNuevoElemento(this.root, nombre) ;
	}
	
	Element agregarElemento(Element elemento, String nombre) {
		return this.agregarElemento(elemento, nombre, null);
	}
	
	Element agregarElemento(Element elemento, String nombre, String valor) {
		Element hijo = XmlHelper.getNuevoElemento(elemento, nombre);
		hijo.setNodeValue(valor);
		elemento.appendChild(elemento);
		return hijo;
	}
	
	Attr agregarAtributo(Element elemento, String nombre, String valor) {
		Attr atributo = XmlHelper.getNuevoAtributo(elemento, nombre);
		atributo.setNodeValue(valor);
		elemento.appendChild(atributo);
		return atributo;
	}
	
	Element agregarNombre(Element elemento, String valor) {
		return this.agregarElemento(elemento, Constants.NOMBRE_TAG, valor);
	}

	Attr agregarId(Element elemento, String valor) {
		return this.agregarAtributo(elemento, Constants.ID_ATTR, valor);
	}
	
	Attr agregarTipo(Element elemento, String valor) {
		return this.agregarAtributo(elemento, Constants.TIPO_ATTR, valor);
	}

	Element agregarElementoAtributos(Element elemento) {
		return this.agregarElemento(elemento, Constants.ATRIBUTOS_TAG, "");
	}

	Element agregarIdentificadoresInternos(Element elemento) {
		return this.agregarElemento(elemento, Constants.IDENTIFICADORES_INTERNOS_TAG);
	}
	
	Element agregarIdentificadoresExternos(Element elemento) {
		return this.agregarElemento(elemento, Constants.IDENTIFICADORES_EXTERNOS_TAG);
	}

	public Element agregarComponentes(Element elemento) {
		return this.agregarElemento(elemento, Constants.COMPONENTES_TAG);
	}

	public Element agregarComponente(Element componentesElement, String id) {
		Element componenteElement = this.agregarElemento(componentesElement, Constants.COMPONENTE_TAG);
		this.agregarAtributo(componenteElement, Constants.IDREF_ATTR, id);
		return componenteElement;		
	}

	public Element agregarDiagramas(Element elemento) {
		return this.agregarElemento(elemento, Constants.DIAGRAMAS_TAG);
	}
}
