package mereditor.tests;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import mereditor.representacion.Representacion;
import mereditor.xml.RepresentacionParserXml;

import org.w3c.dom.Document;

public class RepresentacionTest extends TestCase {
	
	private static final String PATH_REPRESENTACION_TEST = "xml/tests/representacion.xml";
	private RepresentacionParserXml parser;
	
	protected void setUp() throws Exception {
		super.setUp();
		File sourceRepresentacion = new File(PATH_REPRESENTACION_TEST);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document docRepresentacion = builder.parse(sourceRepresentacion);
		this.parser = new RepresentacionParserXml(docRepresentacion);
	}
	
	public void testEncontrarRepresentacionPorId() {
		Map<String, Representacion> rep = this.parser.obtenerRepresentaciones("_1");
		assertTrue(rep != null);
	}
	
	public void testEncontrarRepresentacionPorIdVerificarCantidad() {
		Map<String, Representacion> rep = this.parser.obtenerRepresentaciones("_1");
		assertEquals(rep.size(), 1);
	}
}
