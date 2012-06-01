package mereditor.parser;

import mereditor.modelo.Validacion;
import org.w3c.dom.Element;

public class ValidacionParser extends ComponenteParser {

	private static final String OBS_TAG = "Observaciones";
	private Validacion validacion;
	
	public ValidacionParser(Parser parser) {
		super(parser);
	}

	public void parsear(Element element) {
		String estado = element.getAttributes().item(0).getNodeValue();
		Element observacionesTag = (Element) element.getElementsByTagName(
				OBS_TAG).item(0);
		String obs = observacionesTag.getTextContent().trim();
		validacion = new Validacion(estado, obs);
	}

	public void agregar(Parser parser) {
		parser.validacionParser = this;
	}

	public Object getElementoParseado() {
		return validacion;
	}
}
