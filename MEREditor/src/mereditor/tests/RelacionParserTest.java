package mereditor.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.Relacion.TipoRelacion;
import mereditor.modelo.base.Componente;
import mereditor.parser.RelacionParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import junit.framework.TestCase;

public class RelacionParserTest extends TestCase {

	private static final String PATH_ARCHIVO_DE_RELACION_PARSER_TEST = "src/mereditor/tests/xml de prueba/relacion-comp.xml";

	private static final String NOMBRE_RELACION = "FO";

	private static final TipoRelacion TIPO_RELACION = TipoRelacion.ASOCIACION;

	private static final String ID_RELACION = "6";

	private static final String ID_CONT_RELACION = "0";

	private Element elementoAParsear;
	private RelacionParser parser;
	private Relacion parseada;
	
	public RelacionParserTest(){
		super();
		parseada= null;
		elementoAParsear=null;
	}
	
	protected void setUp() throws Exception {
		
		File source = new File (PATH_ARCHIVO_DE_RELACION_PARSER_TEST);		
		DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc= builder.parse(source);
		Element raiz= doc.getDocumentElement();
		elementoAParsear= (Element) raiz.getElementsByTagName(RelacionParser.tipo).item(0);
		
	}

	public void testParsear() throws Exception {
		parser= new RelacionParser();
		assertTrue (elementoAParsear != null);
		parser.parsear(elementoAParsear);
		parseada= (Relacion) parser.getElementoParseado();
		assertTrue (parseada != null);
		assertTrue (parseada.getIdComponente().equals(ID_RELACION));
	//	assertTrue (parseada.getIdContenedor().equals(ID_CONT_RELACION));
		assertTrue (parseada.getNombre().equals(NOMBRE_RELACION));
		assertTrue (parseada.getTipo().equals(TIPO_RELACION));
		List<Componente> entidadesALinkear= new ArrayList<Componente>();
		entidadesALinkear.add(new Entidad("e1","4",ID_CONT_RELACION,TipoEntidad.MAESTRA) );
		entidadesALinkear.add(new Entidad("e2","5",ID_CONT_RELACION,TipoEntidad.MAESTRA) );
		
	}
 
	

}
