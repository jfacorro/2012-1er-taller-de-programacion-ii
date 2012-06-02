package mereditor.parser;

import java.util.List;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;

public class DiagramaParser extends ComponenteNombreParser implements Linkeable {

	public static final String tag = Constants.DIAGRAMA_TAG;

	protected Diagrama diagrama;
	protected ReferenciasParser componentesParser;
	protected ReferenciasParser diagramasParser;

	public DiagramaParser(Parser parser) {
		super(parser);
		componentesParser = new ReferenciasParser(parser,
				Constants.DIAGRAMA_COMPONENTES_TAG,
				Constants.DIAGRAMA_COMPONENTES_REF_TAG);
		diagramasParser = new ReferenciasParser(parser, Constants.DIAGRAMAS_TAG,
				Constants.DIAGRAMA_REF_TAG);
		diagrama = null;
	}

	protected void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
			this.obtenerNombre(nodo);
			componentesParser.parsear(nodo);
			diagramasParser.parsear(nodo);
		}
	}

	public void linkear(Componente comp) {
		if (componentesParser.pertenece(comp)) {
			diagrama.getComponentes().add(comp);
			return;
		}
		if (diagramasParser.pertenece(comp)) {
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

	@Override
	protected String getTag() {
		return Constants.DIAGRAMA_TAG;
	}
}
