package mereditor.xml;

import java.util.List;

import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;

import org.w3c.dom.Element;

public class RelacionXml extends Relacion implements Xmlizable {

	@Override
	public Element toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento);
		this.tipo = TipoRelacion.valueOf(parser.obtenerTipo(elemento));

		parser.registrar(this);

		List<Element> participantesXml = parser.obtenerParticipantes(elemento);
		
		for (Element participanteXml : participantesXml) {
			Entidad entidad = (Entidad)parser.obtenerEntidadParticipante(participanteXml);
			String [] cardinalidad = parser.obtenerCardinalidad(participanteXml);
			String rol = parser.obtenerRol(participanteXml);
			EntidadRelacion entidadRelacion = new EntidadRelacion(entidad, rol, cardinalidad[0], cardinalidad[1]);
			this.participantes.add(entidadRelacion);
		}
	}

}
