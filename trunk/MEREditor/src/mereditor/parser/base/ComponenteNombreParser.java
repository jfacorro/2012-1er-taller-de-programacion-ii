package mereditor.parser.base;

import mereditor.parser.Constants;
import mereditor.parser.Parser;

import org.w3c.dom.Element;

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
	
	protected void obtenerNombre(Element elemento) {
		if (Parser.isTag(elemento, Constants.NOMBRE_TAG)) {
			nombre = elemento.getTextContent().trim();
		}
	}
}
