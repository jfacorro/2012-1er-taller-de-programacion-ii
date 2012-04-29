package mereditor.parser;

import org.w3c.dom.Element;

interface ElementParser {
	
	public void parsear(Element nodo);
	
	public void agregarAParser (Parser parser);
	public Object getElementoParseado();
	
}
