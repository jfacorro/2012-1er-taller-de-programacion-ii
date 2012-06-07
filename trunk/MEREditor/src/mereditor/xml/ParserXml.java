package mereditor.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;

public abstract class ParserXml {

	protected DocumentBuilder docBuilder;

	protected Element root;
	protected ModeloParserXml modeloParser;
	protected RepresentacionParserXml representacionParser;
	
	public ParserXml() throws Exception {
		this.docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}
	
	public ParserXml(String modeloPath) throws Exception {
		this();
		File source = new File(modeloPath);
		this.root = docBuilder.parse(source).getDocumentElement();
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

}
