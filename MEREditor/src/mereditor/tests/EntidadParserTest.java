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
	private static final TipoEntidad TIPO_ENT = Entidad.TipoEntidad.MAESTRA_COSA;
	private static final String PATH_ARCHIVO_DE_ENTIDADPARSERTEST = "xml/tests/entidad-comp.xml";
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
		File source = new File(PATH_ARCHIVO_DE_ENTIDADPARSERTEST);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = builder.parse(source);
		Element raiz = doc.getDocumentElement();
		Element nodo = (Element) raiz.getElementsByTagName(EntidadParser.tag)
				.item(0);
		elementoAParsear = (Element) nodo;
		String idE1 = "1";
		entidadAComparar = new Entidad("Localidad", idE1, ID_DIAGRAMA, TIPO_ENT);
		atributosAComparar = new ArrayList<Atributo>();
		Atributo a1 = new Atributo("fila", "1", idE1, "1", "1",
				Atributo.TipoAtributo.CARACTERIZACION, null);
		Atributo a2 = new Atributo("butaca", "2", idE1, "1", "1",
				Atributo.TipoAtributo.CARACTERIZACION, null);
		atributosAComparar.add(a1);
		atributosAComparar.add(a2);
		entidadAComparar.agregarAtributo(a1);
		entidadAComparar.agregarAtributo(a2);

		idsAComparar = new ArrayList<ComponenteNombre>();
		refsAEntidades = new ArrayList<ComponenteNombre>();

		Entidad ref1 = new Entidad("EntidadReferenciada", "3", ID_DIAGRAMA,
				TipoEntidad.MAESTRA_COSA);
		Entidad ref2 = new Entidad("EntidadReferenciada", "4", ID_DIAGRAMA,
				TipoEntidad.MAESTRA_COSA);
		Entidad ref3 = new Entidad("EntidadReferenciada", "8", ID_DIAGRAMA,
				TipoEntidad.MAESTRA_COSA);
		refsAEntidades.add(ref1);
		refsAEntidades.add(ref2);
		refsAEntidades.add(ref3);
		idsAComparar.add(a1);
		idsAComparar.add(a2);
		idsAComparar.add(ref1);
		idsAComparar.add(ref2);
		idsAComparar.add(ref3);

	}

	public void testParsearEntidades() {
		EntidadParserFake parser = new EntidadParserFake(null);
		parser.setIdPadre(ID_DIAGRAMA);
		assertTrue(elementoAParsear != null);
		parser.parsear(elementoAParsear);
		entidadParseada = (Componente) parser.getElementoParseado();
		assertTrue(entidadParseada != null);
		assertTrue(entidadParseada.getId().equals(
				entidadAComparar.getId()));
		assertTrue(entidadParseada.getIdPadre().equals(
				entidadAComparar.getIdPadre()));
		assertTrue(((ComponenteNombre) entidadParseada).getNombre().equals(
				entidadAComparar.getNombre()));
		assertTrue(parser.getTipo().equals(TIPO_ENT));
		assertTrue(parser.getAtributos().size() == atributosAComparar.size());

		for (int i = 0; i < atributosAComparar.size(); i++) {
			assertTrue(parser.getAtributos().get(i).getId()
					.equals(atributosAComparar.get(i).getId()));
		}

	}

}
