package mereditor.tests;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.Relacion;
import mereditor.modelo.Relacion.EntidadRelacion;
import mereditor.modelo.base.Componente;
import mereditor.xml.ParserXml;

import org.w3c.dom.Document;

public class ParserXmlTest extends TestCase {
	
	private static final String PATH_ARCHIVO_PARSERTEST = "xml/tests/modelo.xml";
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
	}
	
	public void testEncontrarEntidadPorIdVerificarCantidadAtributos() throws Exception {
		Entidad entidad = (Entidad)this.parser.resolver("_1");
		assertEquals(entidad.getAtributos().size(), 4);
	}
	
	public void testEncontrarEntidadPorIdVerificarTipo() throws Exception {
		Entidad entidad = (Entidad)this.parser.resolver("_1");
		assertEquals(entidad.getTipo(), TipoEntidad.MAESTRA_COSA);
	}
	
	public void testEncontrarEntidadPorIdVerificarNombre() throws Exception {
		Entidad entidad = (Entidad)this.parser.resolver("_1");
		assertEquals(entidad.getNombre(), "Localidad");
	}
	
	public void testEncontrarAtributoPorId() throws Exception {
		Atributo atributo = (Atributo)this.parser.resolver("_2");
		assertTrue(atributo != null);
	}
	
	public void testEncontrarAtributoPorIdVerificarNombre() throws Exception {
		Atributo atributo = (Atributo)this.parser.resolver("_2");
		assertEquals(atributo.getNombre(), "fila");
	}
	
	public void testEncontrarAtributoPorIdVerificarTipo() throws Exception {
		Atributo atributo = (Atributo)this.parser.resolver("_2");
		assertEquals(atributo.getTipo(), TipoAtributo.CARACTERIZACION);
	}
	
	public void testEncontrarAtributoPorIdVerificarCardinalidad() throws Exception {
		Atributo atributo = (Atributo)this.parser.resolver("_14");
		assertEquals(atributo.getCardinalidadMinima(), "1");
		assertEquals(atributo.getCardinalidadMaxima(), "n");
	}
	
	public void testEncontrarAtributoCopiaPorIdVerificarOriginal() throws Exception {
		Atributo atributo = (Atributo)this.parser.resolver("_2a");
		Atributo original = (Atributo)this.parser.resolver("_3");
		assertEquals(atributo.getOriginal(), original);
	}

	public void testEncontrarAtributoCalculoPorIdVerificarFormula() throws Exception {
		Atributo atributo = (Atributo)this.parser.resolver("_2b");
		assertEquals(atributo.getFormula(), "1 + 1");
	}
	
	public void testEncontrarAtributoCompuestoPorIdVerificarHijos() throws Exception {
		Atributo atributo = (Atributo)this.parser.resolver("_32");
		assertEquals(atributo.getAtributos().size(), 5);
	}
	
	public void testEncontrarJerarquiaPorIdVerificarGenerica() throws Exception {
		Jerarquia jerarquia = (Jerarquia)this.parser.resolver("_50");
		Componente generica = this.parser.resolver("_1");
		assertEquals(jerarquia.getGenerica(), generica);
		assertEquals(jerarquia.getDerivadas().size(), 2);
	}
	
	public void testEncontrarRelacionPorIdVerificarNombre() throws Exception {
		Relacion relacion = (Relacion)this.parser.resolver("_24");
		assertEquals(relacion.getNombre(), "SL");
	}
	
	public void testEncontrarRelacionPorIdVerificarParticipantes() throws Exception {
		Relacion relacion = (Relacion)this.parser.resolver("_24");
		assertEquals(relacion.getParticipantes().size(), 2);
	}
	
	public void testEncontrarRelacionPorIdVerificarRolParticipante() throws Exception {
		Relacion relacion = (Relacion)this.parser.resolver("_24");
		assertEquals(relacion.getParticipantes().size(), 2);
		EntidadRelacion participante = relacion.getParticipantes().get(0);
		assertEquals(participante.getRol(), "Bosss");
	}

}
