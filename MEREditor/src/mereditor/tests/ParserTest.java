package mereditor.tests;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import mereditor.modelo.Diagrama;
import mereditor.parser.Parser;
import junit.framework.TestCase;

public class ParserTest extends TestCase {

	private static final String PATH_ARCHIVO_PARSERTEST = "xml/ejemplos/boleteria-comp.xml";
	private Parser p;

	protected void setUp() throws Exception {
		super.setUp();
		File source = new File(PATH_ARCHIVO_PARSERTEST);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = builder.parse(source);
		p = new Parser(doc);
	}

	public void testParsear() {
		p.parsear();
		Diagrama ppal = p.getDiagramaPpal();
		assertTrue(ppal != null);
		assertTrue(ppal.getIdComponente().equals("_99"));
		assertTrue(ppal.getNombre().equals("BoleteriaTeatro"));
		assertTrue(ppal.getDiagramas().isEmpty());
		assertTrue(ppal.getComponentes().size() == 7);
	}

}
