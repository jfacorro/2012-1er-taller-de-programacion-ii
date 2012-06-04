package mereditor.xml;

import mereditor.modelo.Atributo;

import org.w3c.dom.Element;

public class AtributoXml extends Atributo implements Xmlizable {

	@Override
	public Element toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.nombre = parser.obtenerNombre(elemento); 
		this.tipo = TipoAtributo.valueOf(parser.obtenerTipo(elemento));

		parser.registrar(this);
		
		this.atributos.addAll(parser.obtenerAtributos(elemento));
		
		String [] cardinalidad = parser.obtenerCardinalidad(elemento);
		if(cardinalidad != null)
		{
			this.cardinalidadMinima = cardinalidad[0];
			this.cardinalidadMaxima = cardinalidad[1];	
		}
		
		switch(this.tipo) {
			case DERIVADO_CALCULO:
				this.formula = parser.obtenerFormulaAtributo(elemento);
			break;
			case DERIVADO_COPIA:
				this.original = parser.obtenerOriginalAtributo(elemento);
			break;
		}
	}
}
