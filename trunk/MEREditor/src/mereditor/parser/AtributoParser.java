package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AtributoParser extends AtributosParser {

	protected Atributo atributo;
	protected TipoAtributo tipo;
	protected String cardinalidadMininima;
	protected String cardinalidadMaxima;
	
	public AtributoParser(Parser parser) {
		super(parser);
	}
	
	public void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
			obtenerNombre(nodo);
			obtenerTipoAttr(nodo);
			obtenerCardinalidad(nodo);
			parsearAtributos(nodo);
		}

		List<Atributo> atributos = new ArrayList<Atributo>();
		for (int i = 0; i < atributosParseados.size(); i++)
			atributos.add((Atributo) atributosParseados.get(i));

		atributo = new Atributo(nombre, id, idPadre, cardinalidadMininima,
				cardinalidadMaxima, tipo, atributos);
	}

	private void obtenerTipoAttr(Node item) {
		String aux;
		if (item.getNodeName() == Constants.TIPO_ATTR) {
			aux = item.getAttributes().item(0).getNodeValue();
			tipo = TipoAtributo.valueOf(aux);
		}
	}

	private void obtenerCardinalidad(Element item) {
		if (item.getNodeName() == Constants.CARDINALIDAD_TAG) {
			cardinalidadMininima = item.getAttribute(Constants.CARDINALIDAD_MIN_TAG);
			cardinalidadMaxima = item.getAttribute(Constants.CARDINALIDAD_MAX_TAG);
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
