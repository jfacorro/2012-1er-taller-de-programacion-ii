package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class EntidadParser extends ComponenteConAtributosParser {

	public static final String tipo = "Entidad";

	private static final String IDS_INTERNOS_TAG = "IdentificadoresInternos";
	private static final String IDS_EXTERNOS_TAG = "IdentificadoresExternos";
	private static final String ATRIBUTO_REF_TAG = "Atributo";
	private static final String ENTIDAD_REF_TAG = "Entidad";
	private static final String TIPO_TAG = "Tipo";
	private static final String NOMBRE_TAG = "Nombre";



	

	
	protected List<String> identificadoresAux;
	protected List<ComponenteNombre> identificadores;
	protected String idEntidad;
	protected String idContenedor;
	protected String nombre;
	protected Entidad entidadParseada;
	protected TipoEntidad tipoEntidad;


	
	public EntidadParser( ) { 

		identificadoresAux= new ArrayList<String> ();
		identificadores= new ArrayList <ComponenteNombre>();
		entidadParseada=null;
	}
	
	public void setIdContenedor (String idDiagrama){
		idContenedor= idDiagrama;
	}

	/*
	 * PRE: element no nulo
	 * 
	 */
	public void parsear(Element element) {	
		idEntidad = element.getAttributes().item(0).getNodeValue();
		NodeList nodos = element.getChildNodes();
		for (int i=0; i< nodos.getLength(); i++){
			if (nodos.item(i) instanceof Element ){
				Element item =(Element) nodos.item(i);
				parsearNombre (item);
				parsearTipo (item );
				parsearAtributos (item);
				parsearListaDeIdentificadores(item,IDS_INTERNOS_TAG,ATRIBUTO_REF_TAG);
				parsearListaDeIdentificadores(item,IDS_EXTERNOS_TAG,ENTIDAD_REF_TAG);
			}
		}	
		linkearIdentificadores (atributosParseados);
	}

	
	

	

	private void parsearNombre(Element item) {
		if (nombre == null )
		nombre= parsearString (item, NOMBRE_TAG);
	}

	private void parsearTipo(Element item) {
		String t= null;
		if ( (t= parsearString (item, TIPO_TAG)) == null )
			return;
		tipoEntidad= Entidad.TipoEntidad.valueOf(t);	
	}


	private String parsearString(Element item, String tag) {
		if ( item.getNodeName() != tag )
			return null;
		return item.getChildNodes().item(0).getNodeValue();
	}

	private void parsearListaDeIdentificadores(Element element,String tag_lista, String tag_ind) {
		
		if (element.getNodeName() != tag_lista)  
			return;
		NodeList nodos = element.getChildNodes();
		String idAux= null;
		for (int i=0; i< nodos.getLength(); i++)
			if (nodos.item(i).getNodeName() == tag_ind ){				
				/*Tiene un solo atributo: id*/
				idAux= nodos.item(i).getAttributes().item(0).getNodeValue();
				identificadoresAux.add(idAux);
				}
	}

	
	public void linkearIdentificadores(List <ComponenteNombre> componentes){
		String idAux;
		for (int i=0; i<componentes.size(); i++) {
			idAux= componentes.get(i).getIdComponente();
			if ( identificadoresAux.indexOf(idAux) >= 0 ){
				identificadores.add( componentes.get(i) );
				
			}
		}
	}

	public Entidad getEntidadParseada() {
		if ( entidadParseada == null ){
			List <Atributo> atributos= new ArrayList<Atributo> ();
			for (int i= 0; i<atributosParseados.size(); i++ ){
				atributos.add((Atributo) atributosParseados.get(i));
			}
			entidadParseada= new Entidad (nombre,idEntidad,idContenedor,atributos,identificadores,tipoEntidad) ;
		}
		return entidadParseada;
	}
	
}
