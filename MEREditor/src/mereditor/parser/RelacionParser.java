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

public class RelacionParser extends ComponenteConAtributosParser {

	public static final String tipo = "Relacion";
	private static final String TIPO_RELACION_TAG = "Tipo";
	private static final String REF_ENTIDADES_TAG = "Participantes";
	private static final String REF_ENTIDAD_TAG = "Participante";
	private static final String ID_TAG = "id";
	private static final String CARD_MIN_TAG = "cardMin";
	private static final String CARD_MAX_TAG = "cardMax";
	private static final String ROL_TAG = "rol";
	
 
	
	Relacion relacionParseada;
	TipoRelacion tipoRelacion;
	//List<EntidadRelacion> participantes;
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
		//parsear id componente e id contenedor
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
		if ( item.getNodeName() != REF_ENTIDADES_TAG)
			return;
		
		NodeList entidadesNodos= item.getChildNodes();
		String idParticipante;
		String cardMinParticipante;
		String cardMaxParticipante;
		String rol= null;
		Node rolNode;
		Node nodoActual;
		for (int i=0; i<entidadesNodos.getLength();i++){
			nodoActual = entidadesNodos.item(i);
			if ( nodoActual instanceof Element && nodoActual.getNodeName().equals( REF_ENTIDAD_TAG ) ){
					idParticipante= nodoActual.getAttributes().getNamedItem(ID_TAG).getTextContent();
					cardMinParticipante=nodoActual.getAttributes().getNamedItem(CARD_MIN_TAG).getTextContent();
					cardMaxParticipante=nodoActual.getAttributes().getNamedItem(CARD_MAX_TAG).getTextContent();
					rolNode= nodoActual.getAttributes().getNamedItem(ROL_TAG);
					if ( rolNode != null )
						rol= rolNode.getTextContent().trim();
					participantesAux.add(new DatosParticipante (rol,cardMinParticipante, cardMaxParticipante ) );
					refsAEntidades.add(idParticipante);
			}	
		}
	
	}


	private void parsearTipo(Element item) {
		if ( item.getNodeName() == TIPO_RELACION_TAG ){
			tipoRelacion = TipoRelacion.valueOf(item.getTextContent().toUpperCase());
		}
	}

	public void linkearEntidades (List<Componente> entidadesALinkear) {
		
		String idAux;
		Componente e;
		DatosParticipante datos;
		
		int indexE=0;
		for (int i=0; i<entidadesALinkear.size(); i++ ){
			e= entidadesALinkear.get(i);
			idAux= e.getIdComponente();
			
			indexE= refsAEntidades.indexOf(idAux);
			
			if ( indexE >= 0){ //encontro
				datos= participantesAux.get(indexE);
				EntidadRelacion eR= relacionParseada.new EntidadRelacion ((Entidad)e,datos.rol,datos.cardMin,datos.cardMax);
				relacionParseada.agregarParticipante(eR);
			}
		}
	
	}
	
	public Relacion getRelacion() {
		return relacionParseada;
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
