package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class ComponenteConAtributosParser extends ComponenteParser {

	protected List<ComponenteNombre> atributosParseados;
	protected String nombre;

	private static final String ATRIBUTOS_TAG = "Atributos";
	private static final String NOMBRE_TAG = "Nombre";

	ComponenteConAtributosParser(Parser parser) {
		super(parser);
		atributosParseados = new ArrayList<ComponenteNombre>();
	}

	protected void parsearAtributos(Element item) {

		if (item.getNodeName() != ATRIBUTOS_TAG)
			return;
		NodeList atributos = item.getChildNodes();
		for (int i = 0; i < atributos.getLength(); i++) {
			if ((atributos.item(i) instanceof Element)) {
				AtributoParser attrParser = new AtributoParser(parser);
				attrParser.parsear((Element) atributos.item(i));
				atributosParseados
						.add((ComponenteNombre) attrParser.atributoParseado);
			}
		}
	}

	protected void obtenerNombre(Node item) {
		if (item.getNodeName().equals(NOMBRE_TAG)) {
			nombre = item.getTextContent().trim();
		}
	}
}
