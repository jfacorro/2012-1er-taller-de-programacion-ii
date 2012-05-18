package mereditor.parser;

import java.util.ArrayList;
import java.util.List;


import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Relacion.EntidadRelacion;
import mereditor.modelo.Relacion.TipoRelacion;
import mereditor.modelo.base.Componente;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RelacionParser extends ComponenteConAtributosParser implements Linkeable{

	public static final String tipo = "Relacion";
	private static final String TIPO_RELACION_TAG = "TipoRelacion";
	private static final String PARTICIPANTES_TAG = "Participantes";
	private static final String REF_PARTICIPANTE_TAG = "Participante";
	private static final String REF_ENTIDAD_TAG = "RefEntidad";
	private static final String ID_TAG = "id";
	private static final String CARD_MIN_TAG = "cardMin";
	private static final String CARD_MAX_TAG = "cardMax";
	private static final String ROL_TAG = "rol";
	private static final String TIPO_RELACION_ATTR = "tipo";
	private static final String CARDINALIDAD_TAG = "Cardinalidad";
	
 
	
	Relacion relacionParseada;
	TipoRelacion tipoRelacion;
	List<DatosParticipante> participantesAux;
	List<String> refsAEntidades;
	String idRelacion;
	String idContenedor;
	
	
	public RelacionParser (){
		super();
		relacionParseada= null;
		tipoRelacion=null;
		participantesAux= new ArrayList<DatosParticipante>();
		refsAEntidades= new ArrayList<String>();
		idRelacion=null;
		idContenedor=null;
	}
	
	public void parsear(Element nodo) {
		NodeList hijos = nodo.getChildNodes();
		idRelacion= nodo.getAttributes().item(0).getNodeValue();
		//parsear id contenedor
		for (int i=0; i<hijos.getLength(); i++){
			Node item = hijos.item(i);
			if ( item instanceof Element ){
				obtenerNombre(item);
				parsearTipo((Element) item);
				parsearAtributos((Element) item);
				parsearRefAEntidades( (Element) item);
			}
		}
		relacionParseada= new Relacion (nombre, idRelacion, idContenedor, tipoRelacion );
		
		for (int i=0; i<atributosParseados.size(); i++ )
			relacionParseada.agregarAtributo(atributosParseados.get(i));
		
	}


	private void parsearRefAEntidades(Element item) {
		if ( item.getNodeName() != PARTICIPANTES_TAG)
			return;
		NodeList entidadesNodos= item.getChildNodes();
		String idParticipante=null;
		String cardMinParticipante=null;
		String cardMaxParticipante= null;
		String rol= null;
		Node nodoActual;
		for (int i=0; i<entidadesNodos.getLength();i++){
			nodoActual = entidadesNodos.item(i);
			if ( nodoActual instanceof Element && nodoActual.getNodeName().equals( REF_PARTICIPANTE_TAG ) ){
					parsearRefEntidad((Element)nodoActual,idParticipante);
					parsearCardinalidad((Element) nodoActual,cardMinParticipante,cardMaxParticipante);
					parsearRol ((Element)nodoActual,rol);
					participantesAux.add(new DatosParticipante (rol,cardMinParticipante, cardMaxParticipante ) );
					refsAEntidades.add(idParticipante);
			}	
		}
	
	}


	private void parsearRol(Element item, String rol) {
		Element e= (Element) item.getElementsByTagName(ROL_TAG).item(0);
		if (e != null)
			rol=e.getTextContent();
	}

	private void parsearRefEntidad(Element item, String idParticipante) {
		Element ref=(Element) (item.getElementsByTagName(REF_ENTIDAD_TAG).item(0) );
		idParticipante= ref.getAttribute(ID_TAG);
	}

	private void parsearCardinalidad(Element item,
			String cardMinParticipante, String cardMaxParticipante) {
		Element c= (Element) (item.getElementsByTagName(CARDINALIDAD_TAG).item(0));
		cardMinParticipante= c.getAttribute(CARD_MIN_TAG);
		cardMaxParticipante= c.getAttribute(CARD_MAX_TAG);
		
	}

	private void parsearTipo(Element item) {
		if ( item.getNodeName() == TIPO_RELACION_TAG ){
			String t= item.getAttribute(TIPO_RELACION_ATTR);
			tipoRelacion = TipoRelacion.valueOf(t);
		}
	}

	public void linkear ( Componente e ) {
		DatosParticipante datos= null;
		EntidadRelacion eR= null;
		int indexE = refsAEntidades.indexOf( e.getIdComponente() );
		if ( indexE >= 0){ //encontro
			datos= participantesAux.get(indexE);
			eR= relacionParseada.new EntidadRelacion ((Entidad)e,datos.rol,datos.cardMin,datos.cardMax);
			relacionParseada.agregarParticipante(eR);
		}
	}
	
	
	public Object getElementoParseado() {
		return relacionParseada;
	}
	
	public void agregarAParser(Parser parser) {
		parser.agregarParserDeComponente(this);
		parser.agregarParserLinkeable(this);
	}

	
	protected class DatosParticipante{
		String rol;
		String cardMin;
		String cardMax;
		
		DatosParticipante (String rolP, String cardMinP, String cardMaxP ) {
			rol= rolP;
			cardMin= cardMinP;
			cardMax= cardMaxP;
		}
	}



	


	
}
