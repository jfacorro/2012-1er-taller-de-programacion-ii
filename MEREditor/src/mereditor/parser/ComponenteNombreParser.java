package mereditor.parser;

import org.w3c.dom.Node;

/**
 * Aporta el método de parseo de nombre 
 * @author jfacorro
 *
 */
public abstract class ComponenteNombreParser extends ComponenteParser {
	
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
