package mereditor.parser;

import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.parser.base.AtributosParser;
import mereditor.parser.base.Linkeable;
import mereditor.parser.base.ReferenciasParser;

import org.w3c.dom.Element;

public class EntidadParser extends AtributosParser implements Linkeable {

	public static final String tag = Constants.ENTIDAD_TAG;

	protected Entidad entidad;
	protected TipoEntidad tipo;
	protected ReferenciasParser idsExternosParser;
	protected ReferenciasParser idsInternosParser;

	public EntidadParser(Parser parser) {
		super(parser);
		tipo = null;
		entidad = null;
		idsExternosParser = new ReferenciasParser(parser,
				Constants.ENTIDAD_IDS_EXTERNOS_TAG, Constants.ENTIDAD_REF_TAG);
		idsInternosParser = new ReferenciasParser(parser,
				Constants.ENTIDAD_IDS_INTERNOS_TAG, Constants.ATRIBUTO_REF_TAG);
	}

	public void linkear(Componente componente) {
		if (idsExternosParser.pertenece(componente)) {
			entidad.agregarIdentificador((ComponenteNombre) componente);
		}
	}

	private void inicializarEntidad() {
		entidad = new Entidad(nombre, id, idPadre, tipo);
		/* Inicializo los atributos */
		for (int i = 0; i < atributos.size(); i++) {
			if (idsInternosParser.pertenece(atributos.get(i)))
				((Entidad) entidad)
						.agregarAtributo((Atributo) atributos.get(i));
		}
	}

	public Componente getComponente() {
		if (entidad == null)
			inicializarEntidad();

		return entidad;
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
		parser.agregarLinkeable(this);
	}

	@Override
	protected void procesar(Element elemento) {
		this.tipo = TipoEntidad.valueOf(elemento
				.getAttribute(Constants.TIPO_ATTR));
		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
			this.obtenerNombre(nodo);
			parsearAtributos(nodo);
			idsExternosParser.parsear(nodo);
			idsInternosParser.parsear(nodo);
		}
	}

	@Override
	protected String getTag() {
		return Constants.ENTIDAD_TAG;
	}
}
