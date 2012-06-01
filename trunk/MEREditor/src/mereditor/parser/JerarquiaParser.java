package mereditor.parser;

import java.util.List;

import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class JerarquiaParser extends ComponenteParser implements Linkeable {

	protected ReferenciasParser compParser;
	protected Componente jerarquia;
	protected String id;
	protected String idGenerica;
	protected String idContenedor;
	private Jerarquia jerarquiaParseada;

	public JerarquiaParser(Parser parser) {
		super(parser);
		jerarquia = null;
		compParser = new ReferenciasParser(parser, Constants.JERARQUIA_DERIVADAS_TAG,
				Constants.DERIVADA_REF_TAG);
	}

	public void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
				parsearEntidadGenerica(nodo);
				compParser.parsear(nodo);
		}
	}

	private void parsearEntidadGenerica(Node nodoActual) {
		if (nodoActual.getNodeName() != Constants.JERARQUIA_GENERICA_TAG)
			return;
		idGenerica = nodoActual.getAttributes().item(0).getNodeValue();
	}

	public void linkear(Componente componenteALinkear) {
		if (componenteALinkear.getId().equals(idGenerica)) {
			jerarquiaParseada.setEntidadGenerica((Entidad) componenteALinkear);
			return;
		}
		if (compParser.pertenece(componenteALinkear))
			jerarquiaParseada.getEntidadesDerivadas().add(componenteALinkear);
	}

	public Object getElementoParseado() {
		if (jerarquiaParseada == null)
			jerarquiaParseada = new Jerarquia(id, idContenedor);
		return jerarquiaParseada;
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
		parser.agregarLinkeable(this);
	}

	@Override
	protected String getTag() {
		return Constants.JERARQUIA_TAG;
	}

}
