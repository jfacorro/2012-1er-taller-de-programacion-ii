package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Diagrama;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parsea un XML de diagrama y genera el modelo de datos correspondiente
 * 
 */
public class Parser {
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

		validacionGlobal = (Validacion) validacionParser.getElementoParseado();

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
		for (int i = 0; i < componenteParsers.size(); i++) {
			componentes.add((Componente) componenteParsers.get(i)
					.getElementoParseado());
		}
	}

	/**
	 * Devuelve el diagrama principal
	 * 
	 * @return
	 */
	public Diagrama getDiagrama() {
		return (Diagrama) diagramaPrincipalParser.getElementoParseado();
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

	/**
	 * Devuelve una lista de los nodos hijos del elemento
	 * 
	 * @param element
	 * @return
	 */
	public static List<Element> getElementList(Element element) {
		NodeList list = element.getChildNodes();
		ArrayList<Element> nodes = new ArrayList<>();
		for (int i = 0; i < list.getLength(); i++) {
			Node nodo = list.item(i);
			if (nodo instanceof Element)
				nodes.add((Element) nodo);
		}
		return nodes;
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
	
	public static boolean isTag(Element elemento, String tag) {
		return elemento.getNodeName() != tag;
	}
}
