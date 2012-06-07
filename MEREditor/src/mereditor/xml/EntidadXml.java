package mereditor.xml;

import mereditor.control.EntidadControl;
import mereditor.modelo.Atributo;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;

public class EntidadXml extends EntidadControl implements Xmlizable {

	@Override
	public Element toXml(ModeloParserXml parser) throws Exception {
		Element elemento = parser.crearElemento(Constants.ENTIDAD_TAG);
		parser.agregarId(elemento, this.id.toString());
		parser.agregarTipo(elemento, this.tipo.toString());
		parser.agregarNombre(elemento, nombre);

		if (this.atributos.size() > 0) {
			Element atributosElement = parser.agregarElementoAtributos(elemento);
			for (Atributo atributo : this.atributos)
				atributosElement.appendChild(((Xmlizable) atributo).toXml(parser));
		}

		if (this.identificadores.size() > 0) {
			Element idsInternos = parser.agregarIdentificadoresInternos(elemento);
			Element idsExternos = parser.agregarIdentificadoresExternos(elemento);

			for (Componente componente : this.identificadores) {
				if (componente instanceof AtributoXml)
					parser.agregarReferenciaAtributo(idsInternos, componente.getId());
				else if (componente instanceof EntidadXml)
					parser.agregarReferenciaEntidad(idsExternos, componente.getId());
				else
					throw new Exception("El identificador no es una entidad ni un atributo.");
			}
		}

		return elemento;
	}

	@Override
	public void fromXml(Element elemento, ModeloParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento);
		this.tipo = TipoEntidad.valueOf(parser.obtenerTipo(elemento));

		parser.registrar(this);

		for (Atributo atributo : parser.obtenerAtributos(elemento)) {
			atributo.setPadre(this);
			this.atributos.add(atributo);
		}

		// Obtener identificadores internos
		for (Componente componente : parser.obtenerIdentificadoresInternos(elemento)) {
			if (!this.atributos.contains(componente))
				throw new Exception("El identificador debe ser un hijo: " + id);

			this.identificadores.add(componente);
		}

		// Obtener identificadores externos
		this.identificadores.addAll(parser.obtenerIdentificadoresExternos(elemento));
	}
}
