package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AtributoParser extends AtributosParser {

	public static final String ATRIBUTO_TAG = "Atributo";

	private static final String TIPO_TAG = "TipoAtributo";

	private static final String CARDINALIDAD_TAG = "Cardinalidad";
	private static final String CARD_MIN_TAG = "min";
	private static final String CARD_MAX_TAG = "max";

	protected Atributo atributoParseado;
	protected TipoAtributo tipoAttr;
	protected String cardMin;
	protected String cardMax;
	
	public AtributoParser(Parser parser) {
		super(parser);
	}

	public void parsear(Element item) {
		if (item.getNodeName() != ATRIBUTO_TAG)
			return;

		super.parsear(item);

		List<Node> nodos = Parser.getNodeList(item);

		for (Node nodo : nodos) {
			if (nodo instanceof Element) {
				obtenerNombre(nodo);
				obtenerTipoAttr(nodo);
				obtenerCardinalidad(nodo);
				parsearAtributos((Element) nodo);
			}
		}

		List<Atributo> atributos = new ArrayList<Atributo>();
		for (int i = 0; i < atributosParseados.size(); i++)
			atributos.add((Atributo) atributosParseados.get(i));

		atributoParseado = new Atributo(nombre, id, idPadre, cardMin,
				cardMax, tipoAttr, atributos);
	}

	private void obtenerTipoAttr(Node item) {
		String aux;
		if (item.getNodeName() == TIPO_TAG) {
			aux = item.getAttributes().item(0).getNodeValue();
			tipoAttr = TipoAtributo.valueOf(aux);
		}
	}

	private void obtenerCardinalidad(Node item) {
		if (item.getNodeName() == CARDINALIDAD_TAG) {
			cardMin = item.getAttributes().getNamedItem(CARD_MIN_TAG)
					.getNodeValue();
			cardMax = item.getAttributes().getNamedItem(CARD_MAX_TAG)
					.getNodeValue();
		}
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
	}

	public Object getElementoParseado() {
		return atributoParseado;
	}
}
