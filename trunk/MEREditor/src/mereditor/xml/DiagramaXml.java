package mereditor.xml;

import mereditor.modelo.Diagrama;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;

public class DiagramaXml extends Diagrama implements Xmlizable {

	@Override
	public Element toXml() {
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.id = elemento.getAttribute(Constants.ID_ATTR);
		this.nombre = XmlHelper.querySingle(elemento, Constants.NOMBRE_TAG).getTextContent();

		parser.register(this);
		
		this.componentes.addAll(parser.obtenerComponentes(elemento));
		
		// Obtener componentes
		for(Componente componente : parser.obtenerDiagramas(elemento))
			this.diagramas.add((Diagrama) componente);		
		
		this.validacion = (Validacion)parser.obtenerValidacion(elemento);
	}
}
