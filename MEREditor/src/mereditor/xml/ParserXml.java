package mereditor.xml;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mereditor.control.Proyecto;
import mereditor.modelo.base.Componente;
import mereditor.representacion.PList;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ParserXml {
	
	protected Proyecto proyecto;

	protected DocumentBuilder docBuilder;

	protected Element root;
	private ModeloParserXml modeloParser;
	private RepresentacionParserXml representacionParser;
	
	public ParserXml() throws Exception {
		this.docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}
	
	public ParserXml(Proyecto proyecto) throws Exception {
		this();
		this.proyecto = proyecto;
		this.modeloParser = new ModeloParserXml(this.proyecto);
		this.representacionParser = new RepresentacionParserXml(this.proyecto);
	}
	
	public ParserXml(String path) throws Exception {
		this();
		File source = new File(path);
		this.root = docBuilder.parse(source).getDocumentElement();
		this.proyecto = new Proyecto();
		this.init(path);
	}
	
	private void init(String path) throws Exception {
		String dir = new File(path).getParent() + File.separator;
		String modeloPath = XmlHelper.querySingle(this.root, "./Modelo").getTextContent();
		String representacionPath = XmlHelper.querySingle(this.root, "./Representacion").getTextContent();;
		this.modeloParser = new ModeloParserXml(this.proyecto, dir + modeloPath);
		this.representacionParser = new RepresentacionParserXml(this.proyecto, dir + representacionPath);
	}

	/**
	 * Parsea los componentes y representaciones de un archivo
	 * de XML de un proyecto.
	 * @return
	 * @throws Exception
	 */
	public Proyecto parsear() throws Exception {
		this.modeloParser.parsearModelo();
		this.representacionParser.parsearRepresentacion();
		return this.proyecto;
	}
	
	/**
	 * Obtiene el valor del atributo id de un elemento.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerId(Element elemento) {
		return elemento.getAttribute(Constants.ID_ATTR);
	}
	
	/**
	 * Devuelve el componente con el id asociado. Si no se encuentra registrado
	 * en la tabla de componentes, lo busca en el XML del modelo y lo parsea.
	 * 
	 * @param id
	 * @return Componente parseado
	 * @throws Exception
	 */
	public Componente resolver(String id) throws Exception {
		return this.modeloParser.resolver(id);
	}

	/**
	 * Generar el documento XML de componentes
	 * @return
	 * @throws DOMException
	 * @throws Exception
	 */
	public Document generarXmlComponentes() throws DOMException, Exception {
		return this.modeloParser.generarXml();
	}

	/**
	 * Método que se hace disponible para fines de testing.
	 * Encuentra y devuelve las representaciones de un componente 
	 * para cada diagrama en el que este presente en el archivo.
	 * 
	 * @param string
	 * @return
	 */
	public Map<String, PList> obtenerRepresentaciones(String string) {
		return this.representacionParser.obtenerRepresentaciones(string);
	}
}
