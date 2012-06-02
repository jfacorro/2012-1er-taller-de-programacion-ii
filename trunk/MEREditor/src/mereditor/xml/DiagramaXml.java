package mereditor.xml;

import java.util.List;

import mereditor.modelo.Diagrama;
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
		
		// Obtener componentes
		List<Element> componentesXml = XmlHelper.query(elemento, Constants.DIAGRAMA_COMPONENTES_QUERY);
		for(Element componenteXml : componentesXml) {
			Componente componente = parser.resolver(componenteXml.getAttribute(Constants.IDREF_ATTR));
			this.componentes.add(componente);
		}
		
		// Obtener componentes
		List<Element> diagramasXml = XmlHelper.query(elemento, Constants.DIAGRAMA_DIAGRAMAS_QUERY);
		for(Element diagramaXml : diagramasXml) {
			Diagrama diagrama = (Diagrama)parser.resolver(diagramaXml.getAttribute(Constants.IDREF_ATTR));
			this.diagramas.add(diagrama);
		}		
	}
}
