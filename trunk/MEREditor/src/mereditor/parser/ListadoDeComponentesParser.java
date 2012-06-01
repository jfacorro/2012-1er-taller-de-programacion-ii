package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ListadoDeComponentesParser extends ComponenteParser {

	private List<String> idsParseados;
	private final String listadoTag;
	private final String indTag;
	
	ListadoDeComponentesParser (Parser parser, String ListTag, String compRefTag ){
		super(parser);
		idsParseados= new ArrayList <String>();
		listadoTag= ListTag;
		indTag= compRefTag;
	}
	
	public void parsear(Element nodo) {
		if (nodo.getNodeName() != listadoTag)
			return;
		NodeList nodos = nodo.getChildNodes();
		Node nodoActual= null;
		for (int i=0; i<nodos.getLength();i++){
			nodoActual= nodos.item(i);
			if ( nodoActual instanceof Element && nodoActual.getNodeName().equals(indTag)){
				idsParseados.add( parsearId((Element)nodoActual) );
			}
		}

	}
	
	private String parsearId (Element item){
		return item.getAttributes().item(0).getNodeValue();
	}
	
	boolean pertenece(Componente componenteALinkear) {
		boolean encontro= idsParseados.indexOf(componenteALinkear.getIdComponente()) >= 0;
		return encontro;
	}

	public Object getElementoParseado() {
		return idsParseados;
	}


	public void agregar(Parser parser) { }

	
}
