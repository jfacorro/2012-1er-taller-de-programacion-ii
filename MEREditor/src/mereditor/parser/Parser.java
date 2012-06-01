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

		List<Node> hijos = Parser.getNodeList(raiz);

		ComponenteParser componenteParser = null;

		for (Node nodo : hijos) {
			if (nodo instanceof Element) {
				String nombre = nodo.getNodeName();
				componenteParser = Parser.mapComponenteParser(nombre,
						this);
				componenteParser.parsear((Element) nodo);
				componenteParser.agregar(this);
			}
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

	private void inicializarComponentes() {
		for (int i = 0; i < componenteParsers.size(); i++) {
			componentes.add((Componente) componenteParsers.get(i)
					.getElementoParseado());
		}
	}

	public Diagrama getDiagramaPpal() {
		return (Diagrama) diagramaPrincipalParser.getElementoParseado();
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public Validacion getValidacionGlobal() {
		return validacionGlobal;
	}

	public void agregarParserDeComponente(ComponenteParser parserDeComponente) {
		componenteParsers.add(parserDeComponente);
	}

	public void agregarParserLinkeable(Linkeable parserLinkeable) {
		parsersLinkeables.add(parserLinkeable);
	}

	/**
	 * Devuelve una lista de los nodos hijos del elemento
	 * @param element
	 * @return
	 */
	public static List<Node> getNodeList(Element element) {
		NodeList list = element.getChildNodes();
		ArrayList<Node> nodes = new ArrayList<>();
		for (int i = 0; i < list.getLength(); i++) {
			nodes.add(list.item(i));
		}
		return nodes;
	}
	
	/**
	 * Mapea el parser correspondiente al componente
	 * @param tipo Nombre del tag del elemento que representa el componente
	 * @return Instancia del parser correspondiente
	 */
	public static ComponenteParser mapComponenteParser(String tipo, Parser parser) {
		switch (tipo) {
		case EntidadParser.tipo:
			return new EntidadParser(parser);
		case RelacionParser.tipo:
			return new RelacionParser(parser);
		case JerarquiaParser.tipo:
			return new JerarquiaParser(parser);
		case DiagramaParser.tipo:
			return new DiagramaParser(parser);
		default:
			return new ValidacionParser(parser);
		}
	}
}
