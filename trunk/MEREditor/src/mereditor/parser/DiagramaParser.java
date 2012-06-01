package mereditor.parser;

import java.util.List;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DiagramaParser extends ComponenteNombreParser implements Linkeable {

	public static final String tag = "Diagrama";

	protected ReferenciasParser compParser;
	protected ReferenciasParser diagParser;
	protected Diagrama diagrama;

	public DiagramaParser(Parser parser) {
		super(parser);
		compParser = new ReferenciasParser(parser, Constants.COMPONENTES_TAG,
				Constants.COMPONENTES_REF_TAG);
		diagParser = new ReferenciasParser(parser, Constants.DIAGRAMAS_TAG,
				Constants.DIAGRAMA_REF_TAG);
		diagrama = null;
	}

	public void parsear(Element elemento) {
		super.parsear(elemento);

		List<Node> nodos = Parser.getNodeList(elemento);

		for (Node nodo : nodos) {
			if (nodo instanceof Element) {
				this.obtenerNombre(nodo);
				compParser.parsear((Element) nodo);
				diagParser.parsear((Element) nodo);
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

	public Object getElementoParseado() {
		if (diagrama == null)
			diagrama = new Diagrama(nombre, id, idPadre);

		return diagrama;
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
		parser.agregarLinkeable(this);
		parser.diagramaPrincipalParser = this;
	}
}
