package mereditor.parser;

import mereditor.modelo.base.Componente;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JerarquiaParser extends ComponenteParser implements Linkeable {

	public static final String tipo = "Jerarquia";
	private static final String GENERICA_TAG = "Generica";
	private static final String DERIVADAS_TAG = "Derivadas";
	private static final String DERIVADA_REF_TAG = "Entidad";

	protected ListadoDeComponentesParser compParser;
	protected Componente jerarquia;
	protected String id;
	protected String idGenerica;
	protected String idContenedor;
	private Jerarquia jerarquiaParseada;

	public JerarquiaParser(Parser parser) {
		super(parser);
		jerarquia = null;
		compParser = new ListadoDeComponentesParser(parser, DERIVADAS_TAG,
				DERIVADA_REF_TAG);
	}

	public void parsear(Element nodo) {
		id = nodo.getAttributes().item(0).getNodeValue();
		NodeList hijos = nodo.getChildNodes();
		Node nodoActual = null;
		for (int i = 0; i < hijos.getLength(); i++) {
			nodoActual = hijos.item(i);
			if (nodoActual instanceof Element) {
				parsearEntidadGenerica(nodoActual);
				compParser.parsear((Element) nodoActual);
			}
		}

	}

	private void parsearEntidadGenerica(Node nodoActual) {
		if (nodoActual.getNodeName() != GENERICA_TAG)
			return;
		idGenerica = nodoActual.getAttributes().item(0).getNodeValue();
	}

	public void linkear(Componente componenteALinkear) {
		if (componenteALinkear.getIdComponente().equals(idGenerica)) {
			jerarquiaParseada.setEntidadGenerica((Entidad) componenteALinkear);
			return;
		}
		if (compParser.pertenece(componenteALinkear))
			jerarquiaParseada.getEntidadesDerivadas().add(componenteALinkear);
	}

	public Object getElementoParseado() {
		if (jerarquiaParseada == null)
			jerarquiaParseada = new Jerarquia(id, idContenedor);
		return jerarquiaParseada;
	}

	public void agregar(Parser parser) {
		parser.agregarParserDeComponente(this);
		parser.agregarParserLinkeable(this);
	}

}
