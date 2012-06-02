package mereditor.parser;

import mereditor.modelo.Validacion;
import org.w3c.dom.Element;

public class ValidacionParser extends ComponenteParser {

	private Validacion validacion;
	
	public ValidacionParser(Parser parser) {
		super(parser);
	}

	public void parsear(Element element) {
		String estado = element.getAttribute(Constants.ESTADO_ATTR);
		Element observacionesTag = (Element) element.getElementsByTagName(
				Constants.VALIDACION_OBSERVACIONES_TAG).item(0);
		String obs = observacionesTag.getTextContent().trim();
		this.validacion = new Validacion(estado, obs);
	}

	public void agregar(Parser parser) {
		parser.validacionParser = this;
	}

	public Object getElementoParseado() {
		return validacion;
	}

	@Override
	protected void procesar(Element elemento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getTag() {
		return null;
	}
}
