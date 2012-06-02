package mereditor.parser;

import org.w3c.dom.Element;

public abstract class ComponenteParser {

	protected Parser parser;

	protected String id;
	protected String idPadre;

	public ComponenteParser(Parser parser) {
		this.parser = parser;
	}

	public void parsear(Element elemento) {
		if (elemento.getNodeName() != this.getTag())
			return;

		this.id = elemento.getAttribute(Constants.ID_TAG);

		if (this.id != null)
			this.id = this.id.replace("_", "");

		this.procesar(elemento);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public abstract Object getElementoParseado();	

	protected abstract void procesar(Element elemento);

	protected abstract void agregar(Parser parser);

	protected abstract String getTag();
}
