package mereditor.tests;

import java.util.Map;

import junit.framework.TestCase;
import mereditor.representacion.PList;
import mereditor.xml.RepresentacionParserXml;

public class RepresentacionTest extends TestCase {
	
	private static final String PATH_REPRESENTACION_TEST = "xml/test/test-rep.xml";
	private RepresentacionParserXml parser;
	
	protected void setUp() throws Exception {
		super.setUp();
		this.parser = new RepresentacionParserXml(PATH_REPRESENTACION_TEST);
	}
	
	public void testEncontrarRepresentacionPorId() {
		Map<String, PList> rep = this.parser.obtenerRepresentaciones("_1");
		assertTrue(rep != null);
	}
	
	public void testEncontrarRepresentacionPorIdVerificarCantidad() {
		Map<String, PList> rep = this.parser.obtenerRepresentaciones("_1");
		assertEquals(rep.size(), 1);
	}
}
