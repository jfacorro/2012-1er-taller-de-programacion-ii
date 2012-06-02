package mereditor.parser.base;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.parser.Constants;
import mereditor.parser.Parser;

import org.w3c.dom.Element;

public class ReferenciasParser extends ComponenteParser {

	private List<String> ids;
	private final String tag;
	private final String refTag;

	public ReferenciasParser(Parser parser, String listaReferenciasTag,
			String compRefTag) {
		super(parser);
		ids = new ArrayList<String>();
		tag = listaReferenciasTag;
		refTag = compRefTag;
	}

	protected void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
			if (Parser.isTag(nodo, refTag)) {
				ids.add(elemento.getAttribute(Constants.ID_ATTR));
			}
		}
	}

	public boolean pertenece(Componente componenteALinkear) {
		return ids.indexOf(componenteALinkear.getId()) >= 0;
	}
	
	public List<String> getIds() {
		return this.ids;
	}

	public Componente getComponente() {
		return null;
	}

	@Override
	public void agregar(Parser parser) {}

	@Override
	protected String getTag() {
		return this.tag;
	}
}
