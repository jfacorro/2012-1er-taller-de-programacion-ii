package mereditor.xml;

import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.parser.Constants;

import org.w3c.dom.Element;

public class EntidadXml extends Entidad implements Xmlizable {

	@Override
	public Element toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento);
		this.tipo = TipoEntidad.valueOf(parser.obtenerTipo(elemento));

		parser.register(this);
		
		this.atributos.addAll(parser.obtenerAtributos(elemento));

		// Obtener identificadores internos
		List<Element> idsInternosXml = XmlHelper.query(elemento, Constants.IDENTIFICADORES_INTERNOS_QUERY);
		for(Element idInternoXml : idsInternosXml) {
			String id = idInternoXml.getAttribute(Constants.IDREF_ATTR);
			Atributo atributo = (Atributo)parser.resolver(id);

			if(!this.atributos.contains(atributo))
				throw new Exception("El identificador debe ser un hijo: " + id);

			this.identificadores.add(atributo);
		}
		
		// Obtener identificadores externos
		List<Element> idsExternosXml = XmlHelper.query(elemento, Constants.IDENTIFICADORES_EXTERNOS_QUERY);
		for(Element idExternoXml : idsExternosXml) {
			String id = idExternoXml.getAttribute(Constants.IDREF_ATTR);
			Entidad entidad = (Entidad)parser.resolver(id);

			this.identificadores.add(entidad);
		}
	}
}
