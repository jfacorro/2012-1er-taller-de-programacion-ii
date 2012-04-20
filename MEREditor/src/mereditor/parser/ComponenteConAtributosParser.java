package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Atributo;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class ComponenteConAtributosParser implements ElementParser {

	List<Atributo> atributosParseados;
	private static final String ATRIBUTOS_TAG = "Atributos";
	
	
	protected void parsearAtributos(Element item) {
		List<Atributo> atributosParseados= null;
		if (item.getNodeName() != ATRIBUTOS_TAG )
			return;
		atributosParseados= new ArrayList<Atributo>();
		NodeList atributos= item.getChildNodes();
		for (int i=0; i<atributos.getLength(); i++){
			if ( (atributos.item(i) instanceof Element)  ){
				AtributoParser attrParser = new AtributoParser();
				attrParser.parsear ( (Element) atributos.item(i) );
				atributosParseados.add(attrParser.atributoParseado);
			}
		}
		
	}
	
}
	
