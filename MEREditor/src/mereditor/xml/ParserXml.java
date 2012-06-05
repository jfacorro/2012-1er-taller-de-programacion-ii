package mereditor.xml;

import java.io.File;
import java.util.List;

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
	 * Obtiene el valor del estado de un elemento de validacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerEstado(Element elemento) {
		String estado = elemento.getAttribute(Constants.ESTADO_ATTR);
		return estado;
	}

	/**
	 * Obtiene las observacion de un elemento de validacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerObservaciones(Element elemento) {
		Element observacionesXml = XmlHelper.querySingle(elemento, Constants.OBSERVACIONES_QUERY);
		return observacionesXml == null ? null : observacionesXml.getTextContent();
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
	 * Obtiene el valor contenido en el tag hijo "Nombre" de un elemento.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerNombre(Element elemento) {
		return XmlHelper.querySingle(elemento, Constants.NOMBRE_TAG).getTextContent();
	}

	/**
	 * Obtiene el valor del atributo tipo de un elemento.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerTipo(Element elemento) {
		return elemento.getAttribute(Constants.TIPO_ATTR);
	}

	/**
	 * Obtiene la cardinalidad minima y maxima de un atributo o relacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String[] obtenerCardinalidad(Element elemento) {
		Element cardinalidad = XmlHelper.querySingle(elemento, Constants.CARDINALIDAD_QUERY);
		return new String[] { cardinalidad.getAttribute(Constants.CARDINALIDAD_MIN_ATTR),
				cardinalidad.getAttribute(Constants.CARDINALIDAD_MAX_ATTR) };
	}

	/**
	 * Obtiene el valor de la formula de un atributo.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerFormulaAtributo(Element elemento) {
		Element element = XmlHelper.querySingle(elemento, Constants.FORMULA_QUERY);
		return element == null ? null : element.getTextContent();
	}

		/**
	 * Obtiene los elemento de participantes de un elemento relacion.
	 * 
	 * @param elemento
	 * @return
	 */
	List<Element> obtenerParticipantes(Element elemento) {
		return XmlHelper.query(elemento, Constants.PARTICIPANTES_QUERY);
	}

	/**
	 * Obtiene el rol de la entidad participante de la relacion.
	 * 
	 * @param elemento
	 * @return
	 */
	String obtenerRol(Element elemento) {
		Element rolXml = XmlHelper.querySingle(elemento, Constants.ROL_QUERY);
		return rolXml == null ? null : rolXml.getTextContent();
	}
}
