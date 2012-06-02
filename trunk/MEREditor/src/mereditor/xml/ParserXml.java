package mereditor.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.modelo.Atributo;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;
import mereditor.representacion.base.Representacion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParserXml {

	protected Element root;
	protected Map<String, Componente> componentes = new HashMap<String, Componente>();
	protected Map<String, Componente> representaciones = new HashMap<String, Componente>();

	public ParserXml(Document xml) {
		this.root = xml.getDocumentElement();
	}

	/**
	 * Devuelve el componente con el id asociado. Si no se encuentra registrado
	 * en la tabla, lo busca y lo parsea.
	 * 
	 * @param id
	 * @return Componente parseado
	 * @throws Exception
	 */
	public Componente resolver(String id) throws Exception {
		if (this.componentes.containsKey(id))
			return this.componentes.get(id);
		else
			return this.buscarParsear(id);
	}
	
	public Representacion<Componente> representacion(String id) {
		return null;
	}

	/**
	 * Registra el componente en la tabla de componentes utilizando el id como
	 * clave
	 * 
	 * @param componente
	 * @throws Exception
	 */
	void register(Componente componente) throws Exception {
		if (componente.getId() == null)
			throw new Exception("No se puede agregar un componente sin identificador.");

		if (this.componentes.containsKey(componente.getId()))
			throw new Exception("Identificador duplicado: " + componente.getId());

		this.componentes.put(componente.getId(), componente);
	}

	List<Componente> obtenerComponentes(Element elemento) throws Exception {
		List<Element> diagramasXml = XmlHelper.query(elemento, Constants.DIAGRAMA_COMPONENTES_QUERY);
		List<Componente> componentes = new ArrayList<>();

		for (Element diagramaXml : diagramasXml)
			componentes.add(this.obtenerReferencia(diagramaXml));

		return componentes;
	}

	List<Componente> obtenerDiagramas(Element elemento) throws Exception {
		List<Element> diagramasXml = XmlHelper.query(elemento, Constants.DIAGRAMA_DIAGRAMAS_QUERY);
		List<Componente> diagramas = new ArrayList<>();

		for (Element diagramaXml : diagramasXml)
			diagramas.add(this.obtenerReferencia(diagramaXml));

		return diagramas;
	}

	Validacion obtenerValidacion(Element elemento) throws Exception {
		Element validacionXml = XmlHelper.querySingle(elemento, Constants.VALIDACION_QUERY);
		ValidacionXml validacion = (ValidacionXml) this.mapElement(validacionXml);
		validacion.fromXml(validacionXml, this);
		return validacion;
	}

	String obtenerEstado(Element elemento) {
		String estado = elemento.getAttribute(Constants.ESTADO_ATTR);
		return estado;
	}

	String obtenerObservaciones(Element elemento) {
		Element observacionesXml = XmlHelper.querySingle(elemento, Constants.OBSERVACIONES_QUERY);
		return observacionesXml == null ? null : observacionesXml.getTextContent();
	}

	String obtenerId(Element elemento) {
		return elemento.getAttribute(Constants.ID_ATTR);
	}

	String obtenerNombre(Element elemento) {
		return XmlHelper.querySingle(elemento, Constants.NOMBRE_TAG).getTextContent();
	}

	String obtenerTipo(Element elemento) {
		return elemento.getAttribute(Constants.TIPO_ATTR);
	}

	List<Atributo> obtenerAtributos(Element elemento) throws Exception {
		List<Element> atributosXml = XmlHelper.query(elemento, Constants.ATRIBUTOS_QUERY);
		List<Atributo> atributos = new ArrayList<>();

		for (Element atributoXml : atributosXml) {
			Atributo atributo = (Atributo) this.resolver(atributoXml.getAttribute(Constants.ID_ATTR));
			atributos.add(atributo);
		}

		return atributos;
	}

	List<Componente> obtenerIdentificadoresInternos(Element elemento) throws Exception {
		List<Element> idsInternosXml = XmlHelper.query(elemento, Constants.IDENTIFICADORES_INTERNOS_QUERY);
		List<Componente> atributos = new ArrayList<>();

		for (Element idInternoXml : idsInternosXml) {
			atributos.add(this.obtenerReferencia(idInternoXml));
		}

		return atributos;
	}

	List<Componente> obtenerIdentificadoresExternos(Element elemento) throws Exception {
		List<Element> idsExternosXml = XmlHelper.query(elemento, Constants.IDENTIFICADORES_EXTERNOS_QUERY);
		List<Componente> identificadores = new ArrayList<>();

		for (Element idExternoXml : idsExternosXml)
			identificadores.add(this.obtenerReferencia(idExternoXml));

		return identificadores;
	}

	Componente obtenerReferencia(Element elemento) throws Exception {
		String id = elemento.getAttribute(Constants.IDREF_ATTR);
		return this.resolver(id);
	}

	String[] obtenerCardinalidad(Element elemento) {
		Element cardinalidad = XmlHelper.querySingle(elemento, Constants.CARDINALIDAD_QUERY);
		return new String[] { cardinalidad.getAttribute(Constants.CARDINALIDAD_MIN_ATTR),
				cardinalidad.getAttribute(Constants.CARDINALIDAD_MAX_ATTR) };
	}

	String obtenerFormulaAtributo(Element elemento) {
		Element element = XmlHelper.querySingle(elemento, Constants.FORMULA_QUERY);
		return element == null ? null : element.getTextContent();
	}

	Atributo obtenerOriginalAtributo(Element elemento) throws Exception {
		Element element = XmlHelper.querySingle(elemento, Constants.ORIGINAL_QUERY);

		if (element != null)
			return (Atributo) this.resolver(element.getAttribute(Constants.IDREF_ATTR));

		return null;
	}

	Componente obtenerGenerica(Element elemento) throws Exception {
		Element generica = XmlHelper.querySingle(elemento, Constants.GENERICA_QUERY);
		String id = generica.getAttribute(Constants.IDREF_ATTR);

		return this.resolver(id);
	}

	List<Componente> obtenerDerivadas(Element elemento) throws Exception {
		List<Element> derivadasXml = XmlHelper.query(elemento, Constants.DERIVADAS_QUERY);
		List<Componente> derivadas = new ArrayList<>();

		for (Element derivadaXml : derivadasXml) {
			String id = derivadaXml.getAttribute(Constants.IDREF_ATTR);
			derivadas.add(this.resolver(id));
		}

		return derivadas;
	}

	List<Element> obtenerParticipantes(Element elemento) {
		return XmlHelper.query(elemento, Constants.PARTICIPANTES_QUERY);
	}

	Componente obtenerEntidadParticipante(Element elemento) throws Exception {
		Element entidadRefXml = XmlHelper.querySingle(elemento, Constants.ENTIDAD_REF_QUERY);
		return this.obtenerReferencia(entidadRefXml);
	}

	String obtenerRol(Element elemento) {
		Element rolXml = XmlHelper.querySingle(elemento, Constants.ROL_QUERY);
		return rolXml == null ? null : rolXml.getTextContent();
	}

	private Componente buscarParsear(String id) throws Exception {
		String query = String.format(Constants.ID_QUERY, id);
		List<Element> list = XmlHelper.query(this.root, query);

		if (list.size() == 1)
			return this.parse(list.get(0));

		throw new Exception("Identificador inexistente o duplicado: " + id);
	}

	private Componente parse(Element element) throws Exception {
		Xmlizable xmlizable = this.mapElement(element);
		xmlizable.fromXml(element, this);
		return (Componente) xmlizable;
	}

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
}
