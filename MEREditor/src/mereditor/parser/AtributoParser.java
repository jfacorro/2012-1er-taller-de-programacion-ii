package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.w3c.dom.Element;

public class AtributoParser extends AtributosParser {

	protected Atributo atributo;
	protected TipoAtributo tipo;
	protected String cardinalidadMininima;
	protected String cardinalidadMaxima;
	
	public AtributoParser(Parser parser) {
		super(parser);
	}
	
	public void procesar(Element elemento) {
		this.tipo = TipoAtributo.valueOf(elemento.getAttribute(Constants.TIPO_ATTR));

		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
			this.obtenerNombre(nodo);
			obtenerCardinalidad(nodo);
			parsearAtributos(nodo);
		}

		List<Atributo> atributos = new ArrayList<Atributo>();
		for (int i = 0; i < atributosParseados.size(); i++)
			atributos.add((Atributo) atributosParseados.get(i));

		atributo = new Atributo(nombre, id, idPadre, cardinalidadMininima,
				cardinalidadMaxima, tipo, atributos);
	}

	private void obtenerCardinalidad(Element item) {
		if (Parser.isTag(item, Constants.CARDINALIDAD_TAG)) {
			this.cardinalidadMininima = item.getAttribute(Constants.CARDINALIDAD_MIN_ATTR);
			this.cardinalidadMaxima = item.getAttribute(Constants.CARDINALIDAD_MAX_ATTR);
		}
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
	}

	public Object getElementoParseado() {
		return atributo;
	}

	@Override
	protected String getTag() {
		return Constants.ATRIBUTO_TAG;
	}
}
