package mereditor.xml;

import java.util.List;

import mereditor.control.RelacionControl;
import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

import org.w3c.dom.Element;

public class RelacionXml extends RelacionControl implements Xmlizable {

	@Override
	public Element toXml(ModeloParserXml parser) throws Exception {
		Element elemento = parser.crearElemento(Constants.RELACION_TAG);
		parser.agregarId(elemento, this.id.toString());
		parser.agregarTipo(elemento, this.tipo.toString());
		parser.agregarNombre(elemento, nombre);

		Element participantesElem = parser.agregarParticipantes(elemento);
		for (EntidadRelacion entidadRel : this.participantes) {
			Element participante = parser.agregarParticipante(participantesElem);
			parser.agregarReferenciaEntidad(participante, entidadRel.getEntidad().getId());
			parser.agregarCardinalidad(participante, entidadRel.getCardinalidadMinima(), entidadRel.getCardinalidadMaxima());
		}

		return elemento;
	}

	@Override
	public void fromXml(Element elemento, ModeloParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento);
		this.tipo = TipoRelacion.valueOf(parser.obtenerTipo(elemento));

		parser.registrar(this);

		for (Atributo atributo : parser.obtenerAtributos(elemento)) {
			atributo.setPadre(this);
			this.atributos.add(atributo);
		}

		List<Element> participantesXml = parser.obtenerParticipantes(elemento);

		for (Element participanteXml : participantesXml) {
			Entidad entidad = (Entidad) parser.obtenerEntidadParticipante(participanteXml);
			String[] cardinalidad = parser.obtenerCardinalidad(participanteXml);
			String rol = parser.obtenerRol(participanteXml);
			EntidadRelacion entidadRelacion = new EntidadRelacion(entidad, rol, cardinalidad[0], cardinalidad[1]);
			this.participantes.add(entidadRelacion);
		}
	}
}
