package mereditor.tests;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.parser.AtributoParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AtributoParserTest extends TestCase {

	private static final String ID_DIAGRAMA = "i354x";
	private static final String NOMBRE_ATTR = "fila";
	private static final String ID_ATTR = "1";
	private static final String CARD_MAX = "*";
	private static final String CARD_MIN = "1";
	private static final TipoAtributo TIPO_ATTR = TipoAtributo.CARACTERIZACION;
	private static final String PATH_1_ATRIBUTO_PARSER_TEST = "xml/tests/atributo-comp.xml";
	private static final String PATH_2_ATRIBUTO_PARSER_TEST = "xml/tests/atributoCompuesto-comp.xml";
	private static final int CANT_DE_ATRIBUTOS_CONTENIDOS = 5;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testParsearAtributoSimple() throws Exception {
		/* Inicializo archivo nodo a parsear */
		File source = new File(PATH_1_ATRIBUTO_PARSER_TEST);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = builder.parse(source);
		Element raiz = doc.getDocumentElement();
		Element atributoSimpleXML = (Element) raiz.getElementsByTagName(
				AtributoParser.ATRIBUTO_TAG).item(0);

		/* Inicializo el atributo con el que voy a comparar el parseado */
		Atributo atributoSimple = new Atributo(NOMBRE_ATTR, ID_ATTR,
				ID_DIAGRAMA, CARD_MIN, CARD_MAX, TIPO_ATTR, null);

		/* LLamado al metodo */
		AtributoParserFake parser = new AtributoParserFake(null);
		parser.parsear(atributoSimpleXML);

		/* Comparacion */
		assertFalse(parser.getAtributo().getNombre() == null);
		assertTrue(parser.getAtributo().getIdComponente()
				.equals(atributoSimple.getIdComponente()));
		assertTrue(parser.getAtributo().getNombre()
				.equals(atributoSimple.getNombre()));
		assertTrue(parser.getTipoAtributo().equals(TIPO_ATTR));
		assertTrue(parser.getCardMin().equals(CARD_MIN));
		assertTrue(parser.getCardMax().equals(CARD_MAX));
		assertTrue(parser.getAtributosContenidos().size() == 0);

		// assertTrue ( parser.getAtributo().getIdContenedor().equals(
		// atributoSimple.getIdContenedor() ) );
	}

	public void testParsearAtributoCompuesto() throws Exception {
		/* Inicializo el nodo a parsear */
		File source = new File(PATH_2_ATRIBUTO_PARSER_TEST);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = builder.parse(source);
		Element raiz = doc.getDocumentElement();
		Element atributoCompuestoXML = (Element) raiz.getElementsByTagName(
				AtributoParser.ATRIBUTO_TAG).item(0);

		/* Parseo */
		AtributoParserFake parser = new AtributoParserFake(null);
		parser.parsear(atributoCompuestoXML);

		/* Comparo */
		assertTrue(parser.getAtributosContenidos().size() == CANT_DE_ATRIBUTOS_CONTENIDOS);
		assertTrue(parser.getAtributosContenidos().get(0).getIdComponente()
				.equals("1"));
		assertTrue(parser.getAtributosContenidos().get(1).getIdComponente()
				.equals("2"));
		assertTrue(parser.getAtributosContenidos().get(4).getIdComponente()
				.equals("5"));

	}
}
