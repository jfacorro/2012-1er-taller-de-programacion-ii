package mereditor.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;


import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.parser.EntidadParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class EntidadParserTest extends TestCase {

	
	private static final String ID_DIAGRAMA = "i354x";
	private static final TipoEntidad TIPO_ENT = Entidad.TipoEntidad.MAESTRA;
	private static final String PATH_ARCHIVO_DE_ENTIDADPARSERTEST = "src/mereditor/tests/xml de prueba/entidad-comp.xml";
	private Element elementoAParsear;
	private Componente entidadParseada;
	private Entidad entidadAComparar;
	private List<Atributo> atributosAComparar;
	private List<ComponenteNombre> idsAComparar;
	private List<ComponenteNombre> refsAEntidades;
	
	
	public EntidadParserTest(String arg0) {
		super(arg0);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		File source = new File (PATH_ARCHIVO_DE_ENTIDADPARSERTEST);		
		DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc= builder.parse(source);
		Element raiz= doc.getDocumentElement();
		Element nodo= (Element) raiz.getElementsByTagName(EntidadParser.tipo).item(0);	
		elementoAParsear= (Element) nodo;
		atributosAComparar= new ArrayList<Atributo>();
		String idE1= "1";
		Atributo a1= new Atributo ("fila","1",idE1,"1","1",Atributo.TipoAtributo.CARACTERIZACION,null);
		Atributo a2= new Atributo ("butaca","2",idE1,"1","1",Atributo.TipoAtributo.CARACTERIZACION, null);
		atributosAComparar.add( a1 );
		atributosAComparar.add( a2 );
		idsAComparar= new ArrayList<ComponenteNombre>();
		refsAEntidades= new ArrayList<ComponenteNombre>();
		entidadAComparar= new Entidad ("Localidad",idE1,ID_DIAGRAMA,atributosAComparar,idsAComparar,TIPO_ENT);
		Entidad ref1= new Entidad ("EntidadReferenciada","3", ID_DIAGRAMA,null,null, TipoEntidad.MAESTRA);
		Entidad ref2= new Entidad ("EntidadReferenciada","4", ID_DIAGRAMA,null,null, TipoEntidad.MAESTRA);
		Entidad ref3= new Entidad ("EntidadReferenciada","8", ID_DIAGRAMA,null,null, TipoEntidad.MAESTRA);
		refsAEntidades.add( ref1 );
		refsAEntidades.add( ref2 );
		refsAEntidades.add( ref3 );
		idsAComparar.add(a1);
		idsAComparar.add(a2);
		idsAComparar.add( ref1 );
		idsAComparar.add( ref2 );
		idsAComparar.add( ref3 );
		
	}
	
	public void testParsearEntidades(){
		
		EntidadParserFake parser= new EntidadParserFake();		
		parser.setIdContenedor(ID_DIAGRAMA);
		assertTrue ( elementoAParsear != null );
		parser.parsear( elementoAParsear );
		entidadParseada = parser.getEntidadParseada();
		assertTrue ( entidadParseada != null ); 	
		assertTrue ( entidadParseada.getIdComponente().equals(entidadAComparar.getIdComponente()) ) ;
		assertTrue ( entidadParseada.getIdContenedor().equals(entidadAComparar.getIdContenedor()) ) ;
		assertTrue ( ((ComponenteNombre)entidadParseada ).getNombre().equals(entidadAComparar.getNombre()) ) ;
		assertTrue ( parser.getTipo().equals(TIPO_ENT) );
		assertTrue ( parser.getAtributos().size() == atributosAComparar.size()  );
		
		for (int i=0; i<atributosAComparar.size();i++ ){
			assertTrue ( parser.getAtributos().get(i).getIdComponente().equals(atributosAComparar.get(i).getIdComponente() ) );
		}
		
		parser.linkearIdentificadores( refsAEntidades );
		assertTrue ( parser.getIdentificadores().size() == idsAComparar.size()  );
		String idAux;
		for (int i=0; i<idsAComparar.size();i++ ){
			idAux = parser.getIdentificadores().get(i).getIdComponente();
			assertTrue ( idAux.equals( idsAComparar.get(i).getIdComponente() ) );
		}
		
	}

	

}
