package mereditor.xml;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;
import mereditor.control.DiagramaControl;

import org.w3c.dom.Element;

public class DiagramaXml extends DiagramaControl implements Xmlizable {

	@Override
	public Element toXml() {
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.id = elemento.getAttribute(Constants.ID_ATTR);
		this.nombre = XmlHelper.querySingle(elemento, Constants.NOMBRE_TAG).getTextContent();

		parser.registrar(this);

		for (Componente componente : parser.obtenerComponentes(elemento)) {
			componente.setPadre(this);
			this.componentes.add(componente);
		}

		for (Componente componente : parser.obtenerDiagramas(elemento)) {
			componente.setPadre(this);
			this.diagramas.add((Diagrama) componente);
		}

		this.validacion = parser.obtenerValidacion(elemento);
	}
}
