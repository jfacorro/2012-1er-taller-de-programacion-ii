package mereditor.parser;

import java.util.List;

import mereditor.modelo.Atributo;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AtributoParser extends ComponenteConAtributosParser {

	Atributo atributoParseado;
	private static final String ATRIBUTO_TAG = "Atributo";
	

	public void parsear(Element item) {
		if (item.getNodeName()!= ATRIBUTO_TAG)
			return;
		String id = item.getAttributes().item(0).getNodeValue();
		String nombre = null;
		String tipoAttr= null;
		String cardMax= null;
		String cardMin= null;
		List<Atributo> atributosParseados= null;
		
		NodeList hijos = item.getChildNodes();
		
		for (int i=0; i<hijos.getLength(); i++ ){
			if (hijos.item(i) instanceof Element) {
				obtenerNombreAttr( hijos.item(i), nombre );
				obtenerTipoAttr( hijos.item(i), tipoAttr );
				obtenerCardinalidad (hijos.item(i), cardMin, cardMax );
				parsearAtributos( (Element) hijos.item(i) );
			}
		}
	//	Atributo atributoParseado = new Atributo (id,nombre,tipoAttr,cardMin, cardMax);
		//if ( atributosParseados != null )
		//	atributoParseado.setAtributos (atributosParseados);

	}
	
	private void obtenerTipoAttr(Node item, String tipoAttr) {
		if (item.getNodeName()== "Tipo"){
			tipoAttr= item.getTextContent().trim();
			System.out.println(tipoAttr);
		}
	}

	private void obtenerCardinalidad(Node item, String cardMin, String cardMax) {
		// TODO Auto-generated method stub
		
	}



	private void obtenerNombreAttr(Node item, String nombre) {
		if (item.getNodeName()== "Nombre"){
			nombre= item.getTextContent().trim();
			System.out.println(nombre);
		}
	}

}
