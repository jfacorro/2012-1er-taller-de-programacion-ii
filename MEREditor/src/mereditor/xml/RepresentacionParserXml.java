package mereditor.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.control.Control;
import mereditor.modelo.base.Componente;
import mereditor.representacion.Representacion;

import org.eclipse.draw2d.geometry.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RepresentacionParserXml extends ParserXml {

	public RepresentacionParserXml(Document modeloXml) {
		super(modeloXml);
	}
	
	public RepresentacionParserXml(String path) throws Exception {
		super(path);
	}
	
	/**
	 * Recorre la coleccion de componentes parseados y busca sus
	 * representaciones para cada diagrama en el que estén presentes.
	 */
	public void cargarRepresentaciones(ModeloParserXml modeloParser) {
		for (Componente componente : modeloParser.componentes.values()) {
			Map<String, Representacion> representaciones = this.obtenerRepresentaciones(componente.getId());
			Control<?> control = (Control<?>) componente;
			for (String idDiagrama : representaciones.keySet())
				control.getFigura(idDiagrama).setRepresentacion(representaciones.get(idDiagrama));
		}
	}
	
	/**
	 * Devuelve un mapa con las representaciones del componente por cada
	 * diagrama en el que se encuentra.
	 * 
	 * @param id
	 *            Id del componente
	 * @return Mapa con los ids de los diagramas como clave y las
	 *         representaciones como valor.
	 */
	public Map<String, Representacion> obtenerRepresentaciones(String id) {
		HashMap<String, Representacion> representaciones = new HashMap<>();

		// Buscar todas las representaciones para el id
		String query = String.format(Constants.REPRESENTACION_ID_QUERY, id);
		List<Element> representacionesXml = XmlHelper.query(this.root, query);

		for (Element representacionXml : representacionesXml) {
			Element diagramaXml = XmlHelper.querySingle(representacionXml, Constants.DIAGRAMA_PADRE_QUERY);

			String idDiagrama = this.obtenerId(diagramaXml);
			representaciones.put(idDiagrama, this.obtenerRepresentacion(representacionXml));
		}

		return representaciones;
	}
	
	/**
	 * Parsea un elemento de representación básico con posición y dimensión.
	 * 
	 * @param elemento
	 * @return
	 */
	protected Representacion obtenerRepresentacion(Element elemento) {
		Element posicionXml = XmlHelper.querySingle(elemento, Constants.POSICION_QUERY);
		Element dimensionXml = XmlHelper.querySingle(elemento, Constants.DIMENSION_QUERY);

		int x = Integer.parseInt(posicionXml.getAttribute(Constants.X_ATTR));
		int y = Integer.parseInt(posicionXml.getAttribute(Constants.Y_ATTR));
		int ancho = Integer.parseInt(dimensionXml.getAttribute(Constants.ANCHO_ATTR));
		int alto = Integer.parseInt(dimensionXml.getAttribute(Constants.ALTO_ATTR));
		Rectangle rect = new Rectangle(x, y, ancho, alto);

		Representacion representacion = new Representacion();
		representacion.setProperty("rect", rect);
		return representacion;
	}
}
