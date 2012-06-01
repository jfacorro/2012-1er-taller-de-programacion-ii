package mereditor.parser;

import java.util.List;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;

public class DiagramaParser extends ComponenteNombreParser implements Linkeable {

	protected ReferenciasParser compParser;
	protected ReferenciasParser diagParser;
	protected Diagrama diagrama;

	public DiagramaParser(Parser parser) {
		super(parser);
		compParser = new ReferenciasParser(parser, Constants.DIAGRAMA_COMPONENTES_TAG,
				Constants.DIAGRAMA_COMPONENTES_REF_TAG);
		diagParser = new ReferenciasParser(parser, Constants.DIAGRAMAS_TAG,
				Constants.DIAGRAMA_REF_TAG);
		diagrama = null;
	}

	protected void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
			this.obtenerNombre(nodo);
			compParser.parsear(nodo);
			diagParser.parsear(nodo);
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

	@Override
	protected String getTag() {
		return Constants.DIAGRAMA_TAG;
	}
}
