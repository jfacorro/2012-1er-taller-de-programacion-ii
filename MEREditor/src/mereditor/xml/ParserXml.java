package mereditor.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.modelo.Atributo;
import mereditor.modelo.base.Componente;
import mereditor.parser.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParserXml {

	protected Element root;
	protected Map<String, Componente> componentes;

	public ParserXml(Document xml) {
		this.root = xml.getDocumentElement();
		this.componentes = new HashMap<String, Componente>();
	}

	public Componente resolver(String id) throws Exception {
		if (this.componentes.containsKey(id))
			return this.componentes.get(id);
		else
			return this.findParse(id);
	}

	public void register(Componente componente) throws Exception {
		if(componente.getId() == null)
			throw new Exception("No se puede agregar un componente sin identificador.");

		if (this.componentes.containsKey(componente.getId()))
			throw new Exception("Identificador duplicado: " + componente.getId());

		this.componentes.put(componente.getId(), componente);
	}

	public List<Element> query(String query) {
		return XmlHelper.query(this.root, query);
	}
	
	public String obtenerId(Element elemento) {
		return elemento.getAttribute(Constants.ID_ATTR);
	}
	
	public String obtenerNombre(Element elemento) {
		return 	XmlHelper.querySingle(elemento, Constants.NOMBRE_TAG).getTextContent();
	}
	
	public String obtenerTipo(Element elemento) {
		return elemento.getAttribute(Constants.TIPO_ATTR);		
	}
	
	public List<Atributo> obtenerAtributos(Element elemento) throws Exception {
		List<Element> atributosXml = XmlHelper.query(elemento, Constants.ATRIBUTOS_QUERY);
		List<Atributo> atributos = new ArrayList<>();

		for(Element atributoXml : atributosXml) {
			Atributo atributo = (Atributo)this.resolver(atributoXml.getAttribute(Constants.ID_ATTR));
			atributos.add(atributo);
		}

		return 	atributos;
	}

	private Componente findParse(String id) throws Exception {
		List<Element> list = this.query("//*[@id='" + id + "']");

		if (list.size() == 1) {
			return this.parse(list.get(0));
		}

		throw new Exception("Identificador inexistente o duplicado: " + id);
	}

	private Componente parse(Element element) throws Exception {
		Xmlizable xmlizable = this.mapElement(element);
		xmlizable.fromXml(element, this);
		return (Componente) xmlizable;
	}

	private Xmlizable mapElement(Element element) {
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
			default:
				return new ValidacionXml();
		}
	}
}
