package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;

public abstract class AtributosParser extends ComponenteNombreParser {

	protected List<ComponenteNombre> atributosParseados;

	AtributosParser(Parser parser) {
		super(parser);
		atributosParseados = new ArrayList<ComponenteNombre>();
	}

	protected void parsearAtributos(Element item) {
		if (item.getNodeName() != Constants.ATRIBUTOS_TAG)
			return;

		List<Element> atributos = Parser.getElementList(item);

		for (Element nodo : atributos) {
			AtributoParser attrParser = new AtributoParser(parser);
			attrParser.parsear(nodo);
			atributosParseados.add(attrParser.atributo);
		}
	}
}
