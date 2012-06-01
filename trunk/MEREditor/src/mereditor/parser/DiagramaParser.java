package mereditor.parser;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DiagramaParser extends ComponenteParser implements Linkeable {

	public static final String tipo = "Diagrama";
	private static final String NOMBRE_TAG = "Nombre";
	private static final String DIAGRAMAS_TAG = "Diagramas";
	private static final String DIAGRAMA_REF_TAG = "Diagrama";
	private static final String COMPONENTES_TAG = "Componentes";
	private static final String COMP_REF_TAG = "RefComponente";

	protected String idD;
	protected String idC;
	protected String nombre;
	protected ListadoDeComponentesParser compParser;
	protected ListadoDeComponentesParser diagParser;
	protected Diagrama diagrama;

	public DiagramaParser(Parser parser) {
		super(parser);
		idC = idD = nombre = null;
		compParser = new ListadoDeComponentesParser(parser, COMPONENTES_TAG,
				COMP_REF_TAG);
		diagParser = new ListadoDeComponentesParser(parser, DIAGRAMAS_TAG,
				DIAGRAMA_REF_TAG);
		diagrama = null;
	}

	public void parsear(Element nodo) {
		idD = nodo.getAttributes().item(0).getNodeValue();
		NodeList nodos = nodo.getChildNodes();
		Node nodoActual = null;
		for (int i = 0; i < nodos.getLength(); i++) {
			nodoActual = nodos.item(i);
			if (nodoActual instanceof Element) {
				obtenerNombre(nodoActual);
				compParser.parsear((Element) nodoActual);
				diagParser.parsear((Element) nodoActual);
			}
		}
	}

	public void linkear(Componente comp) {
		if (compParser.pertenece(comp)) {
			diagrama.getComponentes().add(comp);
			return;
		}
		if (diagParser.pertenece(comp)) {
			diagrama.getDiagramas().add((Diagrama) comp);
		}
	}

	protected void obtenerNombre(Node item) {
		if (item.getNodeName().equals(NOMBRE_TAG)) {
			nombre = item.getTextContent().trim();
		}
	}

	public Object getElementoParseado() {
		if (diagrama == null)
			diagrama = new Diagrama(nombre, idD, idC);
		return diagrama;
	}

	public void agregar(Parser parser) {
		parser.agregarParserDeComponente(this);
		parser.agregarParserLinkeable(this);
		parser.diagramaPrincipalParser = this;
	}
}
