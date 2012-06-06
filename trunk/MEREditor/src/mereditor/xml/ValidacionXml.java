package mereditor.xml;

import mereditor.modelo.Validacion;

import org.w3c.dom.Element;

public class ValidacionXml extends Validacion implements Xmlizable {

	@Override
	public Element toXml(ModeloParserXml parser) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromXml(Element elemento, ModeloParserXml parser) throws Exception {
		this.estado = EstadoValidacion.valueOf(parser.obtenerEstado(elemento)); 
		this.observaciones = parser.obtenerObservaciones(elemento);
	}
}
