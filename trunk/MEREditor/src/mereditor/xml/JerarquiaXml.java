package mereditor.xml;

import mereditor.control.JerarquiaControl;
import mereditor.modelo.Entidad;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;

public class JerarquiaXml extends JerarquiaControl implements Xmlizable {

	@Override
	public Element toXml(ModeloParserXml parser) throws Exception {
		Element elemento = parser.crearElemento(Constants.JERARQUIA_TAG);

		parser.agregarGenerica(elemento, this.generica.getId());
		
		Element derivadasElement = parser.agregarDerivadas(elemento);
		for(Componente componente : this.derivadas)
			parser.agregarDerivada(derivadasElement, componente.getId());
		
		return elemento;
	}

	@Override
	public void fromXml(Element elemento, ModeloParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.tipo = TipoJerarquia.valueOf(parser.obtenerTipo(elemento));

		parser.registrar(this);

		this.generica = (Entidad) parser.obtenerGenerica(elemento);

		for (Componente componente : parser.obtenerDerivadas(elemento)) {
			componente.setPadre(this);
			this.derivadas.add((Entidad) componente);
		}
	}
}
