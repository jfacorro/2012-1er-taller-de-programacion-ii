package mereditor.tests;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.xml.ParserXml;

import org.w3c.dom.Document;

public class ParserXmlTest extends TestCase {
	
	private static final String PATH_ARCHIVO_PARSERTEST = "xml/ejemplos/boleteria-comp.xml";
	private ParserXml parser;

	protected void setUp() throws Exception {
		super.setUp();
		File source = new File(PATH_ARCHIVO_PARSERTEST);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(source);
		this.parser = new ParserXml(doc);
	}
	
	public void testEncontrarEntidadPorId() throws Exception {
		Entidad entidad = (Entidad)this.parser.resolver("_1");
		assertTrue(entidad != null);
		assertTrue(entidad instanceof Entidad);
	}
	
	public void testEncontrarEntidadPorIdVerificarCantidadAtributos() throws Exception {
		Entidad entidad = (Entidad)this.parser.resolver("_1");
		assertEquals(entidad.getAtributos().size(), 2);
	}
	
	public void testEncontrarEntidadPorIdVerificarTipo() throws Exception {
		Entidad entidad = (Entidad)this.parser.resolver("_1");
		assertEquals(entidad.getTipo(), TipoEntidad.MAESTRA_COSA);
	}
	
	public void testEncontrarEntidadPorIdVerificarNombre() throws Exception {
		Entidad entidad = (Entidad)this.parser.resolver("_1");
		assertEquals(entidad.getNombre(), "Localidad");
	}
	
	
}
