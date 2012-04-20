package mereditor.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.parser.EntidadParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

public class EntidadParserTest extends TestCase {

	
	private static final String ID_DIAGRAMA = "i354x";
	private static final TipoEntidad TIPO_ENT = Entidad.TipoEntidad.MAESTRA;
	private static final String PATH_ARCHIVO_DE_ENTIDADPARSERTEST = "src/mereditor/tests/xml de prueba/boleteria-comp.xml";
	List<Element> elementosAParsear;
	List<Componente> entidadesParseadas;
	List<Componente> entidadesAComparar;
	
	
	public EntidadParserTest(String arg0) {
		super(arg0);
		elementosAParsear= new ArrayList<Element> ();
		entidadesParseadas= new ArrayList<Componente> ();
	}

	protected void setUp() throws Exception {
		super.setUp();
		File source = new File (PATH_ARCHIVO_DE_ENTIDADPARSERTEST);		
		DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc= builder.parse(source);
		Element raiz= doc.getDocumentElement();
		NodeList hijos= raiz.getChildNodes();
		Node nodo= null;
		for (int i=0;i<hijos.getLength();i++){
			nodo=hijos.item(i);
			if ( nodo instanceof Element && nodo.getNodeName()== ( EntidadParser.tipo ) ){
				elementosAParsear.add((Element) nodo);
			}
		}
		//Crear las entidades para comparar
		List<Atributo> atributos= new ArrayList<Atributo>();
		String idE1= "1";
		Atributo a1= new Atributo ("fila","1",idE1,"1","1",Atributo.TipoAtributo.CARACTERIZACION);
		Atributo a2= new Atributo ("butaca","2",idE1,"1","1",Atributo.TipoAtributo.CARACTERIZACION);
		atributos.add( a1 );
		atributos.add( a2 );
		List<ComponenteNombre> ids= new ArrayList<ComponenteNombre>();
		//falta agregar ids externos
		Entidad e1= new Entidad ("Localidad",idE1,ID_DIAGRAMA,atributos,ids,TIPO_ENT);
	}
	
	public void testParsearEntidades(){
		List<EntidadParser> parsers= new ArrayList<EntidadParser>();
		EntidadParser parser= null;
		Componente entidadParseada= null;
		for (int i=0; i<elementosAParsear.size();i++ ){
			parser= new EntidadParser();
			parser.setIdContenedor(ID_DIAGRAMA);
			parser.parsear( elementosAParsear.get(i) );
			entidadParseada = parser.getEntidadParseada();
			assertTrue (entidadParseada != null ); 
			entidadesParseadas.add(entidadParseada);
			parsers.add(parser);
		}
		
		for (int i=0; i<parsers.size(); i++){
			parsers.get(i).linkearIdentificadores(entidadesParseadas);
		}
		
		//Comprobar clase por clase el contenido
		
		
	}

}
