package mereditor.xml;

import mereditor.control.AtributoControl;
import mereditor.modelo.Atributo;

import org.w3c.dom.Element;

public class AtributoXml extends AtributoControl implements Xmlizable {

	@Override
	public Element toXml(ModeloParserXml parser) throws Exception {
		Element elemento = parser.crearElemento(Constants.ATRIBUTO_TAG);
		parser.agregarId(elemento, this.id.toString());
		parser.agregarTipo(elemento, this.tipo.toString());
		parser.agregarNombre(elemento, nombre);
		
		// Cardinalidad
		parser.agregarCardinalidad(elemento, this.cardinalidadMinima, this.cardinalidadMaxima);
		
		// Formula u original según tipo
		switch (this.tipo) {
		case DERIVADO_CALCULO:
			parser.agregarFormula(elemento, this.formula);
			break;
		case DERIVADO_COPIA:
			parser.agregarOriginal(elemento, this.original.getId());
			break;
		}

		if (this.atributos.size() > 0) {
			Element atributosElement = parser.agregarElementoAtributos(elemento);
			for (Atributo atributo : this.atributos)
				atributosElement.appendChild(((Xmlizable) atributo).toXml(parser));
		}

		return elemento;
	}

	@Override
	public void fromXml(Element elemento, ModeloParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento);
		this.tipo = TipoAtributo.valueOf(parser.obtenerTipo(elemento));

		parser.registrar(this);

		// Atributos
		for (Atributo atributo : parser.obtenerAtributos(elemento)) {
			atributo.setPadre(this);
			this.atributos.add(atributo);
		}
		
		// Formula u original según tipo
		switch (this.tipo) {
		case DERIVADO_CALCULO:
			this.formula = parser.obtenerFormulaAtributo(elemento);
			break;
		case DERIVADO_COPIA:
			this.original = parser.obtenerOriginalAtributo(elemento);
			break;
		}

		// Cardinalidad
		String[] cardinalidad = parser.obtenerCardinalidad(elemento);
		if (cardinalidad != null) {
			this.cardinalidadMinima = cardinalidad[0];
			this.cardinalidadMaxima = cardinalidad[1];
		}
	}
}
