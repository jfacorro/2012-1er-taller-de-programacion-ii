package mereditor.parser;

import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;

public class EntidadParser extends AtributosParser implements
		Linkeable {

	private static final String IDS_INTERNOS_TAG = "IdentificadoresInternos";
	private static final String IDS_EXTERNOS_TAG = "IdentificadoresExternos";
	private static final String ATRIBUTO_REF_TAG = "RefAtributo";
	private static final String ENTIDAD_REF_TAG = "RefEntidad";
	private static final String TIPO_TAG = "TipoEntidad";

	protected ReferenciasParser idsExtParser;
	protected ReferenciasParser idsIntParser;
	protected TipoEntidad tipoEntidad;

	private Entidad entidadParseada;

	public EntidadParser(Parser parser) {
		super(parser);
		tipoEntidad = null;
		entidadParseada = null;
		idsExtParser = new ReferenciasParser(parser, IDS_EXTERNOS_TAG,
				ENTIDAD_REF_TAG);
		idsIntParser = new ReferenciasParser(parser, IDS_INTERNOS_TAG,
				ATRIBUTO_REF_TAG);
	}

	public void setIdContenedor(String idDiagrama) {
		this.idPadre = idDiagrama;
	}

	private void parsearTipo(Element item) {
		if (!item.getNodeName().equals(TIPO_TAG))
			return;
		String tipo = item.getAttribute("tipo");
		tipoEntidad = Entidad.TipoEntidad.valueOf(tipo);
	}

	public void linkear(Componente componente) {
		if (idsExtParser.pertenece(componente)) {
			(entidadParseada)
					.agregarIdentificador((ComponenteNombre) componente);
		}
	}

	private void inicializarEntidad() {
		entidadParseada = new Entidad(nombre, id, idPadre,
				tipoEntidad);
		/* Inicializo los atributos */
		for (int i = 0; i < atributosParseados.size(); i++) {
			if (idsIntParser.pertenece(atributosParseados.get(i)))
				((Entidad) entidadParseada)
						.agregarAtributo((Atributo) atributosParseados.get(i));
		}
	}

	public Object getElementoParseado() {
		if (entidadParseada == null)
			inicializarEntidad();

		return entidadParseada;
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
		parser.agregarLinkeable(this);
	}

	@Override
	protected void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		for(Element nodo : nodos) {
			this.obtenerNombre(nodo);
			parsearTipo(nodo);
			parsearAtributos(nodo);
			idsExtParser.parsear(nodo);
			idsIntParser.parsear(nodo);
		}
	}

	@Override
	protected String getTag() {
		return Constants.ENTIDAD_TAG;
	}
}
