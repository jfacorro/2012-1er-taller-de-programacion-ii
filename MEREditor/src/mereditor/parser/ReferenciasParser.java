package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ReferenciasParser extends ComponenteParser {

	private List<String> ids;
	private final String tag;
	private final String refTag;

	public ReferenciasParser(Parser parser, String listaReferenciasTag, String compRefTag) {
		super(parser);
		ids = new ArrayList<String>();
		tag = listaReferenciasTag;
		refTag = compRefTag;
	}

	public void parsear(Element nodo) {
		if (nodo.getNodeName() != tag)
			return;

		List<Node> nodos = Parser.getNodeList(nodo);

		for (Node nodoActual : nodos) {
			if (nodoActual instanceof Element
					&& nodoActual.getNodeName().equals(refTag)) {
				ids.add(parsearId((Element) nodoActual));
			}
		}
	}

	private String parsearId(Element item) {
		return item.getAttributes().item(0).getNodeValue();
	}

	boolean pertenece(Componente componenteALinkear) {
		boolean encontro = ids.indexOf(componenteALinkear
				.getId()) >= 0;
		return encontro;
	}

	public Object getElementoParseado() {
		return ids;
	}

	@Override
	protected void agregar(Parser parser) {
	}
}
