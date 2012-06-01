package mereditor.parser;

import org.w3c.dom.Element;

public abstract class ComponenteParser {
	protected Parser parser;

	protected String id;

	public ComponenteParser(Parser parser) {
		this.parser = parser;
	}

	public void parsear(Element elemento) {
		if (elemento.getNodeName() != this.getTag())
			return;

		this.id = elemento.getAttribute(Constants.ID_TAG);
		if(this.id != null) this.id = this.id.replace("_", "");
		
		this.procesar(elemento);
	}
	
	protected abstract void procesar(Element elemento);

	public abstract Object getElementoParseado();

	protected abstract void agregar(Parser parser);
	
	protected abstract String getTag();
}
