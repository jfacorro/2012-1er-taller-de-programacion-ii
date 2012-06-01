package mereditor.parser;

import org.w3c.dom.Element;

public abstract class ComponenteParser {
	protected Parser parser;

	public ComponenteParser(Parser parser) {
		this.parser = parser;
	}

	public abstract void parsear(Element elemento);

	public abstract Object getElementoParseado();

	protected abstract void agregar(Parser parser);
}
