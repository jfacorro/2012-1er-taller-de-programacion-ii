package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Relacion.EntidadRelacion;
import mereditor.modelo.Relacion.TipoRelacion;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RelacionParser extends AtributosParser implements Linkeable {

	public static final String tag = Constants.RELACION_TAG;

	protected Relacion relacionParseada;
	protected TipoRelacion tipo;
	protected List<DatosParticipante> participantesAux;
	protected List<String> refsAEntidades;

	public RelacionParser(Parser parser) {
		super(parser);
		participantesAux = new ArrayList<DatosParticipante>();
		refsAEntidades = new ArrayList<String>();
	}

	public void parsear(Element nodo) {
		List<Node> hijos = Parser.getNodeList(nodo);
		this.id = nodo.getAttribute(Constants.ID_TAG);
		this.tipo = TipoRelacion.valueOf(nodo
				.getAttribute(Constants.RELACION_TIPO_ATTR));

		// parsear id contenedor
		for (Node item : hijos) {
			if (item instanceof Element) {
				obtenerNombre(item);
				parsearAtributos((Element) item);
				parsearRefEntidades((Element) item);
			}
		}

		relacionParseada = new Relacion(nombre, id, idPadre, tipo);

		for (int i = 0; i < atributosParseados.size(); i++)
			relacionParseada.agregarAtributo(atributosParseados.get(i));

	}

	private void parsearRefEntidades(Element item) {
		if (item.getNodeName() != Constants.RELACION_PARTICIPANTES_TAG)
			return;
		NodeList entidadesNodos = item.getChildNodes();
		String idParticipante = null;
		String cardMinParticipante = null;
		String cardMaxParticipante = null;
		String rol = null;
		Node nodoActual;
		for (int i = 0; i < entidadesNodos.getLength(); i++) {
			nodoActual = entidadesNodos.item(i);
			if (nodoActual instanceof Element
					&& nodoActual.getNodeName().equals(
							Constants.REF_PARTICIPANTE_TAG)) {
				parsearRefEntidad((Element) nodoActual, idParticipante);
				parsearCardinalidad((Element) nodoActual, cardMinParticipante,
						cardMaxParticipante);
				parsearRol((Element) nodoActual, rol);
				participantesAux.add(new DatosParticipante(rol,
						cardMinParticipante, cardMaxParticipante));
				refsAEntidades.add(idParticipante);
			}
		}
	}

	private void parsearRol(Element item, String rol) {
		Element e = (Element) item.getElementsByTagName(
				Constants.RELACION_ROL_TAG).item(0);
		if (e != null)
			rol = e.getTextContent();
	}

	private void parsearRefEntidad(Element item, String idParticipante) {
		Element ref = (Element) (item
				.getElementsByTagName(Constants.REF_ENTIDAD_TAG).item(0));
		idParticipante = ref.getAttribute(Constants.ID_TAG);
	}

	private void parsearCardinalidad(Element item, String cardMinParticipante,
			String cardMaxParticipante) {
		Element c = (Element) (item
				.getElementsByTagName(Constants.CARDINALIDAD_TAG).item(0));
		cardMinParticipante = c.getAttribute(Constants.CARDINALIDAD_MIN_TAG);
		cardMaxParticipante = c.getAttribute(Constants.CARDINALIDAD_MAX_TAG);

	}

	public void linkear(Componente e) {
		DatosParticipante datos = null;
		EntidadRelacion eR = null;
		int indexE = refsAEntidades.indexOf(e.getId());
		if (indexE >= 0) { // encontro
			datos = participantesAux.get(indexE);
			eR = relacionParseada.new EntidadRelacion((Entidad) e, datos.rol,
					datos.cardMin, datos.cardMax);
			relacionParseada.agregarParticipante(eR);
		}
	}

	public Object getElementoParseado() {
		return relacionParseada;
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
		parser.agregarLinkeable(this);
	}

	protected class DatosParticipante {
		String rol;
		String cardMin;
		String cardMax;

		DatosParticipante(String rolP, String cardMinP, String cardMaxP) {
			rol = rolP;
			cardMin = cardMinP;
			cardMax = cardMaxP;
		}
	}

}
