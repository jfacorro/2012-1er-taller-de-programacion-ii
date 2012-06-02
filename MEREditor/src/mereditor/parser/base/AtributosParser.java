package mereditor.parser.base;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.parser.AtributoParser;
import mereditor.parser.Constants;
import mereditor.parser.Parser;

import org.w3c.dom.Element;

public abstract class AtributosParser extends ComponenteNombreParser {

	protected List<Atributo> atributos = new ArrayList<Atributo>();

	public AtributosParser(Parser parser) {
		super(parser);
	}

	protected void parsearAtributos(Element item) {
		if (item.getNodeName() != Constants.ATRIBUTOS_TAG)
			return;

		List<Element> nodos = Parser.getElementList(item);

		for (Element nodo : nodos) {
			AtributoParser atributoParser = new AtributoParser(parser);
			atributoParser.parsear(nodo);
			atributos.add((Atributo)atributoParser.getComponente());
		}
	}
}
