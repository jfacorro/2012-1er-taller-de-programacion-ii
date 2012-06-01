package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Relacion.EntidadRelacion;
import mereditor.modelo.Relacion.TipoRelacion;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;

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

	protected void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		this.id = elemento.getAttribute(Constants.ID_TAG);
		this.tipo = TipoRelacion.valueOf(elemento
				.getAttribute(Constants.TIPO_ATTR));

		// parsear id contenedor
		for (Element nodo : nodos) {
			obtenerNombre(nodo);
			parsearAtributos(nodo);
			parsearRefEntidades(nodo);
		}

		relacionParseada = new Relacion(nombre, id, idPadre, tipo);

		for (int i = 0; i < atributosParseados.size(); i++)
			relacionParseada.agregarAtributo(atributosParseados.get(i));

	}

	private void parsearRefEntidades(Element elemento) {
		if (elemento.getNodeName() != Constants.RELACION_PARTICIPANTES_TAG)
			return;
		List<Element> nodos = Parser.getElementList(elemento);

		String idParticipante = null;
		String cardMinParticipante = null;
		String cardMaxParticipante = null;
		String rol = null;

		for (Element nodo : nodos) {
			if (nodo.getNodeName().equals(Constants.REF_PARTICIPANTE_TAG)) {
				parsearRefEntidad(nodo, idParticipante);
				parsearCardinalidad(nodo, cardMinParticipante,
						cardMaxParticipante);
				parsearRol(nodo, rol);
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
					datos.cardinalidadMininma, datos.cardinalidadMaxima);
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
		protected String rol;
		protected String cardinalidadMininma;
		protected String cardinalidadMaxima;

		DatosParticipante(String rol, String cardinalidadMininma, String cardinalidadMaxima) {
			this.rol = rol;
			this.cardinalidadMininma = cardinalidadMininma;
			this.cardinalidadMaxima = cardinalidadMaxima;
		}
	}

	@Override
	protected String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

}
