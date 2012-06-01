package mereditor.parser;

import org.w3c.dom.Node;

public abstract class ComponenteNombreParser extends ComponenteParser {
	
	protected String idPadre;
	protected String nombre;
	
	public ComponenteNombreParser(Parser parser) {
		super(parser);
	}
	
	protected void obtenerNombre(Node item) {
		if (item.getNodeName().equals(Constants.NOMBRE_TAG)) {
			nombre = item.getTextContent().trim();
		}
	}
}
