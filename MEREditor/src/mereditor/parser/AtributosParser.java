package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class AtributosParser extends ComponenteNombreParser {

	protected List<ComponenteNombre> atributosParseados;

	AtributosParser(Parser parser) {
		super(parser);
		atributosParseados = new ArrayList<ComponenteNombre>();
	}

	protected void parsearAtributos(Element item) {
		if (item.getNodeName() != Constants.ATRIBUTOS_TAG)
			return;
		List<Node> atributos = Parser.getNodeList(item);
		for (Node nodo : atributos) {
			if (nodo instanceof Element) {
				AtributoParser attrParser = new AtributoParser(parser);
				attrParser.parsear((Element) nodo);
				atributosParseados
						.add((ComponenteNombre) attrParser.atributoParseado);
			}
		}
	}
}
