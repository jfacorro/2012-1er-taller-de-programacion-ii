package mereditor.xml;

import mereditor.modelo.Entidad;
import mereditor.modelo.base.Componente;

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

		parser.registrar(this);
		
		this.atributos.addAll(parser.obtenerAtributos(elemento));

		// Obtener identificadores internos
		for(Componente componente : parser.obtenerIdentificadoresInternos(elemento))
		{
			if (!this.atributos.contains(componente))
				throw new Exception("El identificador debe ser un hijo: " + id);
			
			this.identificadores.add(componente);
		}
				
		// Obtener identificadores externos
		this.identificadores.addAll(parser.obtenerIdentificadoresExternos(elemento));
	}
}
