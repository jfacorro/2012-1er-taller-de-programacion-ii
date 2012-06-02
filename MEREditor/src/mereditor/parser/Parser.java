package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathFactory;

import mereditor.modelo.Diagrama;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;
import mereditor.parser.base.ComponenteParser;
import mereditor.parser.base.Linkeable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parsea un XML de diagrama y genera el modelo de datos correspondiente
 * 
 */
public class Parser {
	protected static XPathFactory xpath = XPathFactory.newInstance();

	protected Document xml;

	protected Validacion validacionGlobal;
	protected ComponenteParser validacionParser;
	protected ComponenteParser diagramaPrincipalParser;

	protected List<Componente> componentes;
	protected List<Linkeable> parsersLinkeables;
	protected List<ComponenteParser> componenteParsers;

	public Parser(Document xml) {
		this.xml = xml;
		this.componentes = new ArrayList<Componente>();
		this.parsersLinkeables = new ArrayList<Linkeable>();
		this.componenteParsers = new ArrayList<ComponenteParser>();
	}

	/**
	 * Comienza a parsear un modelo.
	 */
	public void parsear() {
		Element raiz = xml.getDocumentElement();

		List<Element> hijos = Parser.getElementList(raiz);

		ComponenteParser componenteParser = null;

		for (Element elemento : hijos) {
			String nombre = elemento.getNodeName();
			componenteParser = Parser.mapComponenteParser(nombre, this);
			componenteParser.parsear(elemento);
			componenteParser.agregar(this);
		}

		validacionGlobal = (Validacion) validacionParser.getComponente();

		inicializarComponentes();

		/* Una vez parseados los componentes, los puedo linkear */
		linkearComponentes();
	}

	/* PRE: componentes inicializados */
	private void linkearComponentes() {
		for (int i = 0; i < parsersLinkeables.size(); i++) {
			for (int j = 0; j < componentes.size(); j++) {
				parsersLinkeables.get(i).linkear(componentes.get(j));
			}
		}
	}

	/**
	 * Carga la lista de componentes en base a los parseados
	 */
	private void inicializarComponentes() {
		for (ComponenteParser componenteParser : componenteParsers) {
			componentes.add((Componente) componenteParser.getComponente());
		}
	}

	/**
	 * Devuelve el diagrama principal
	 * 
	 * @return
	 */
	public Diagrama getDiagrama() {
		return (Diagrama) diagramaPrincipalParser.getComponente();
	}

	/**
	 * Devuelve la lista de componentes
	 * 
	 * @return
	 */
	public List<Componente> getComponentes() {
		return componentes;
	}

	/**
	 * Devuelve el objeto de validación global
	 * 
	 * @return
	 */
	public Validacion getValidacionGlobal() {
		return validacionGlobal;
	}

	/**
	 * Agrega un parser de componente a la lista local
	 * 
	 * @param componenteParser
	 */
	public void agregarComponenteParser(ComponenteParser componenteParser) {
		componenteParsers.add(componenteParser);
	}

	/**
	 * Agrega un elemento linkeable
	 * 
	 * @param linkeable
	 */
	public void agregarLinkeable(Linkeable linkeable) {
		parsersLinkeables.add(linkeable);
	}

	private static List<Element> convertir(NodeList list) {
		ArrayList<Element> nodes = new ArrayList<>();
		for (int i = 0; i < list.getLength(); i++) {
			Node nodo = list.item(i);
			if (nodo instanceof Element)
				nodes.add((Element) nodo);
		}
		return nodes;
	}

	/**
	 * Devuelve una lista de los nodos hijos del elemento
	 * @param element
	 * @return
	 */
	public static List<Element> getElementList(Element element) {
		return Parser.convertir(element.getChildNodes());
	}

	/**
	 * Mapea el parser correspondiente al componente
	 * 
	 * @param tipo
	 *            Nombre del tag del elemento que representa el componente
	 * @return Instancia del parser correspondiente
	 */
	public static ComponenteParser mapComponenteParser(String tipo,
			Parser parser) {
		switch (tipo) {
		case EntidadParser.tag:
			return new EntidadParser(parser);
		case RelacionParser.tag:
			return new RelacionParser(parser);
		case JerarquiaParser.tag:
			return new JerarquiaParser(parser);
		case DiagramaParser.tag:
			return new DiagramaParser(parser);
		default:
			return new ValidacionParser(parser);
		}
	}

	/**
	 * Verifica si el elemento que se le pasa tiene el tag especficado
	 * 
	 * @param elemento
	 * @param tag
	 * @return
	 */
	public static boolean isTag(Element elemento, String tag) {
		return elemento.getNodeName() != tag;
	}

	/**
	 * Devuelve todos los elementos hijos inmediatos que tienen el tag
	 * especificado
	 * 
	 * @param elemento
	 * @param tag
	 * @return
	 */
	public static List<Element> obtenerHijos(Element elemento, String tag) {
		return Parser.convertir(elemento.getElementsByTagName(tag));
	}

	public static Element obtenerHijo(Element elemento, String tag) {
		NodeList hijos = elemento.getElementsByTagName(tag);
		Element hijo = null;
		if (hijos.getLength() > 0)
			hijo = (Element) hijos.item(0);

		return hijo;
	}
}
