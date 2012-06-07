package mereditor.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ParserXml {

	protected Element root;

	public ParserXml(Document modeloXml) {
		this.root = modeloXml.getDocumentElement();
	}

	public ParserXml(String modeloPath) throws Exception {
		File source = new File(modeloPath);
		DocumentBuilder builder;
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.root = builder.parse(source).getDocumentElement();
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
