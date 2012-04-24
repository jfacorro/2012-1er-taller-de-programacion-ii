package mereditor.parser;

import java.util.ArrayList;
import java.util.List;


import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class ComponenteConAtributosParser implements ElementParser {

	protected List<ComponenteNombre> atributosParseados;
	private static final String ATRIBUTOS_TAG = "Atributos";
	
	ComponenteConAtributosParser () {
		atributosParseados= new ArrayList<ComponenteNombre>();
	}
	
	protected void parsearAtributos(Element item) {
		
		if (item.getNodeName() != ATRIBUTOS_TAG )
			return;	
		NodeList atributos= item.getChildNodes();
		for (int i=0; i<atributos.getLength(); i++){
			if ( (atributos.item(i) instanceof Element)  ){
				AtributoParser attrParser = new AtributoParser();
				attrParser.parsear ( (Element) atributos.item(i) );		
				atributosParseados.add( (ComponenteNombre) attrParser.atributoParseado);
			}
		}
		
	}
	
	
	
}
	
