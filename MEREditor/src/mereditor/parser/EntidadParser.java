package mereditor.parser;


import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class EntidadParser extends ComponenteConAtributosParser implements Linkeable {

	public static final String tipo = "Entidad";

	private static final String IDS_INTERNOS_TAG = "IdentificadoresInternos";
	private static final String IDS_EXTERNOS_TAG = "IdentificadoresExternos";
	private static final String ATRIBUTO_REF_TAG = "Atributo";
	private static final String ENTIDAD_REF_TAG = "Entidad";
	private static final String TIPO_TAG = "TipoEntidad";
	
	protected ListadoDeComponentesParser idsExtParser;
	protected ListadoDeComponentesParser idsIntParser;
	protected String idEntidad;
	protected String idContenedor;
	protected TipoEntidad tipoEntidad;

	private Componente entidadParseada;

	
	public EntidadParser( ) { 
		idEntidad= idContenedor = null;
		tipoEntidad= null;
		entidadParseada= null;
		idsExtParser= new ListadoDeComponentesParser (IDS_EXTERNOS_TAG,ENTIDAD_REF_TAG);
		idsIntParser= new ListadoDeComponentesParser (IDS_INTERNOS_TAG,ATRIBUTO_REF_TAG);
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
				obtenerNombre (item);
				parsearTipo (item );
				parsearAtributos (item);
				idsExtParser.parsear(item);
				idsIntParser.parsear(item);
			}
		}	
	}

	private void parsearTipo(Element item) {
		if (! item.getNodeName().equals(TIPO_TAG) )
			return;
		String t= item.getAttribute("tipo");
		tipoEntidad= Entidad.TipoEntidad.valueOf(t);
	}

	
	public void linkear(Componente componente) {
		if ( idsExtParser.pertenece(componente) ){
			((Entidad) entidadParseada).agregarIdentificador((ComponenteNombre) componente);
		}
		
	}

	private void inicializarEntidad() {
		entidadParseada= new Entidad (nombre,idEntidad,idContenedor,tipoEntidad);
		/*Inicializo los atributos*/
		for (int i= 0 ; i<atributosParseados.size(); i++){
			if ( idsIntParser.pertenece(atributosParseados.get(i)) )
				((Entidad)entidadParseada).agregarAtributo((Atributo) atributosParseados.get(i));
		}
	}
	
	public Object getElementoParseado() {
		if (entidadParseada == null)
			inicializarEntidad();
		return entidadParseada;
	}


	public void agregarAParser(Parser parser) {
		parser.agregarParserDeComponente(this);
		parser.agregarParserLinkeable(this);
		
	}
	
}
