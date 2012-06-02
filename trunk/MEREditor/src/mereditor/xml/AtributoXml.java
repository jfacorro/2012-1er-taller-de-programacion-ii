package mereditor.xml;

import mereditor.modelo.Atributo;

import org.w3c.dom.Element;

public class AtributoXml extends Atributo implements Xmlizable {

	@Override
	public Element toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento); 
		this.tipo = TipoAtributo.valueOf(parser.obtenerTipo(elemento));

		parser.register(this);
		
		this.atributos.addAll(parser.obtenerAtributos(elemento));
	}
}
