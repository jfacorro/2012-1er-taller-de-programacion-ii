package mereditor.parser;

import java.util.List;
import mereditor.modelo.base.Componente;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EntidadParser extends ComponenteConAtributosParser {

	public static final String tipo = "Entidad";

	List<String> identificadoresExternos;
	List<String> identificadoresInternos;
	String id;

	
	
	public EntidadParser() { 
		
	}

	public void parsear(Element element) {	
		id = element.getAttributes().item(0).getNodeValue();
		NodeList nodos = element.getChildNodes();
		for (int i=0; i< nodos.getLength(); i++){
			if (nodos.item(i) instanceof Element ){
				Element item =(Element) nodos.item(i);
				parsearAtributos (item);
				parsearIdentificadoresInternos(item);
				parsearIdentificadoresExternos(item);
			}
		}	
	}

	
	private void parsearIdentificadoresInternos(Element element) {
		
		
	}



	private void parsearIdentificadoresExternos(Element element) {
		
	}


	public void linkearIdentificadoresExternos(List <Componente> componentes){
		
	}
	
}
