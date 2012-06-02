package mereditor.xml;

import org.w3c.dom.Element;

import mereditor.modelo.Validacion;

public class ValidacionXml extends Validacion implements Xmlizable {

	@Override
	public Element toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.estado = EstadoValidacion.valueOf(parser.obtenerEstado(elemento)); 
		this.observaciones = parser.obtenerObservaciones(elemento);
	}
}
