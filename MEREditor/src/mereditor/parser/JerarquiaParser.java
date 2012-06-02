package mereditor.parser;

import java.util.List;

import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class JerarquiaParser extends ComponenteParser implements Linkeable {

	public static final String tag = Constants.JERARQUIA_TAG;

	protected Jerarquia jerarquia;
	protected String idEntidadGenerica;
	protected ReferenciasParser entidadesDerivadasParser;

	public JerarquiaParser(Parser parser) {
		super(parser);
		entidadesDerivadasParser = new ReferenciasParser(parser, Constants.JERARQUIA_DERIVADAS_TAG,
				Constants.DERIVADA_REF_TAG);
	}

	public void procesar(Element elemento) {
		List<Element> nodos = Parser.getElementList(elemento);

		for (Element nodo : nodos) {
				parsearEntidadGenerica(nodo);
				entidadesDerivadasParser.parsear(nodo);
		}
	}

	private void parsearEntidadGenerica(Node nodoActual) {
		if (nodoActual.getNodeName() != Constants.JERARQUIA_GENERICA_TAG)
			return;
		idEntidadGenerica = nodoActual.getAttributes().item(0).getNodeValue();
	}

	public void linkear(Componente componente) {
		if (componente.getId().equals(idEntidadGenerica)) {
			jerarquia.setEntidadGenerica((Entidad) componente);
			return;
		}
		if (entidadesDerivadasParser.pertenece(componente))
			jerarquia.getEntidadesDerivadas().add(componente);
	}

	public Object getElementoParseado() {
		if (jerarquia == null)
			jerarquia = new Jerarquia(id, idPadre);
		return jerarquia;
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
		parser.agregarLinkeable(this);
	}

	@Override
	protected String getTag() {
		return Constants.JERARQUIA_TAG;
	}

}
