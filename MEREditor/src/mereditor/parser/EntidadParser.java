package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EntidadParser extends ComponenteConAtributosParser {

	public static final String tipo = "Entidad";

	private static final String IDS_INTERNOS_TAG = "IdentificadoresInternos";
	private static final String IDS_EXTERNOS_TAG = "IdentificadoresExternos";
	private static final String ATRIBUTO_REF_TAG = "Atributo";

	private static final String NOMBRE_TAG = "Nombre";
	

	
	private List<String> identificadoresAux;
	private List<ComponenteNombre> identificadores;
	private String idEntidad;
	private String idContenedor;
	private String nombre;
	private Entidad entidadParseada;
	private TipoEntidad tipoEntidad;


	
	public EntidadParser( ) { 
		identificadoresAux= new ArrayList<String> ();
		identificadores= new ArrayList <ComponenteNombre>();
		entidadParseada=null;
	}
	
	public void setIdContenedor (String idDiagrama){
		idContenedor= idDiagrama;
	}

	public void parsear(Element element) {	
		idEntidad = element.getAttributes().item(0).getNodeValue();
		NodeList nodos = element.getChildNodes();
		for (int i=0; i< nodos.getLength(); i++){
			if (nodos.item(i) instanceof Element ){
				Element item =(Element) nodos.item(i);
				parsearNombre (item);
				parsearTipo (item);
				parsearAtributos (item);
				parsearIdentificadores(item);
			}
		}	
		linkearIdentificadoresInternos();
	}

	
	private void parsearTipo(Element item) {
		// TODO Auto-generated method stub
		
	}

	private void linkearIdentificadoresInternos() {
		
		
	}

	private void parsearNombre(Element item) {
		if ( item.getNodeName() != NOMBRE_TAG )
			return;
		nombre = item.getChildNodes().item(0).getNodeValue();
	}

	private void parsearIdentificadores(Element element) {
		if (element.getNodeName() != IDS_INTERNOS_TAG )
			return;
		NodeList nodos = element.getChildNodes();
		String idAux= null;
		for (int i=0; i< nodos.getLength(); i++)
			if (nodos.item(i).getNodeName() == ATRIBUTO_REF_TAG ){
				/*Tiene un solo atributo: id*/
				idAux= nodos.item(i).getAttributes().item(0).getNodeValue();
				System.out.print("id" +"idAux");
				identificadoresAux.add(idAux);		
			}
		
	}

	
	public void linkearIdentificadores(List <Componente> componentes){
		//linkear con los atributos
		//linkear con los componentes
	}

	public Entidad getEntidadParseada() {
		if ( entidadParseada == null )
			entidadParseada= new Entidad (nombre,idEntidad,idContenedor,atributosParseados,identificadores,tipoEntidad) ;
		return entidadParseada;
	}
	
}
