package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AtributoParser extends ComponenteConAtributosParser {

	public static final String ATRIBUTO_TAG = "Atributo";
	
	private static final String TIPO_TAG = "Tipo";

	private static final String CARDINALIDAD_TAG = "Cardinalidad";
	private static final String CARD_MIN_TAG = "min";
	private static final String CARD_MAX_TAG = "max";

	
	protected Atributo atributoParseado;
	protected String idContenedor;
	protected TipoAtributo tipoAttr;
	protected String cardMin;
	protected String cardMax;
	

	public void parsear(Element item) {
		if (item.getNodeName()!= ATRIBUTO_TAG)
			return;
		//obtengo el id del atributo
		String id = item.getAttributes().item(0).getNodeValue();
		//falta obtener el id del contenedor
		

		
		NodeList hijos = item.getChildNodes();
		
		for (int i=0; i<hijos.getLength(); i++ ){
			if (hijos.item(i) instanceof Element) {
				obtenerNombre( hijos.item(i) );
				obtenerTipoAttr( hijos.item(i) );
				obtenerCardinalidad (hijos.item(i) );
				parsearAtributos( (Element) hijos.item(i) );
			}
		}
		List<Atributo> atributos = new ArrayList<Atributo>();
		for (int i=0; i<atributosParseados.size(); i++ )
			atributos.add( (Atributo) atributosParseados.get(i));
		
		atributoParseado = new Atributo (nombre,id,idContenedor,cardMin, cardMax,tipoAttr, atributos );

	}
	
	private void obtenerTipoAttr(Node item ) {
		String aux;
		if (item.getNodeName()== TIPO_TAG){
			aux= item.getTextContent().trim();
			tipoAttr= TipoAtributo.valueOf(aux.toUpperCase());
		}
	}

	private void obtenerCardinalidad(Node item ) {
		if ( item.getNodeName()== CARDINALIDAD_TAG ){
			cardMin= item.getAttributes().getNamedItem(CARD_MIN_TAG).getNodeValue();
			cardMax= item.getAttributes().getNamedItem(CARD_MAX_TAG).getNodeValue();
		}
		
	}


}
