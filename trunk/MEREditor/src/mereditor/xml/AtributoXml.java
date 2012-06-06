package mereditor.xml;

import mereditor.control.AtributoControl;
import mereditor.modelo.Atributo;

import org.w3c.dom.Element;

public class AtributoXml extends AtributoControl implements Xmlizable {

	@Override
	public Element toXml(ModeloParserXml parser) throws Exception {
		// TODO Auto-generated method stub
		return null;
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

		// Cardinalidad
		String[] cardinalidad = parser.obtenerCardinalidad(elemento);
		if (cardinalidad != null) {
			this.cardinalidadMinima = cardinalidad[0];
			this.cardinalidadMaxima = cardinalidad[1];
		}

		// Formula u origianl según tipo
		switch (this.tipo) {
		case DERIVADO_CALCULO:
			this.formula = parser.obtenerFormulaAtributo(elemento);
			break;
		case DERIVADO_COPIA:
			this.original = parser.obtenerOriginalAtributo(elemento);
			break;
		}
	}
}
