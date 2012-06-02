package mereditor.parser;

import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.base.Componente;
import mereditor.parser.base.AtributosParser;

import org.w3c.dom.Element;

public class AtributoParser extends AtributosParser {

	protected Atributo atributo;
	protected TipoAtributo tipo;
	protected String cardinalidadMininima;
	protected String cardinalidadMaxima;
	
	public AtributoParser(Parser parser) {
		super(parser);
	}
	
	public void procesar(Element elemento) {
		this.tipo = TipoAtributo.valueOf(elemento.getAttribute(Constants.TIPO_ATTR));

		this.obtenerNombre(Parser.obtenerHijo(elemento, Constants.NOMBRE_TAG));
		this.obtenerCardinalidad(Parser.obtenerHijo(elemento, Constants.CARDINALIDAD_TAG));
		this.parsearAtributos(Parser.obtenerHijo(elemento, Constants.ATRIBUTOS_TAG));

		atributo = new Atributo(nombre, id, idPadre, cardinalidadMininima,
				cardinalidadMaxima, tipo, this.atributos);
	}

	private void obtenerCardinalidad(Element item) {
		if (Parser.isTag(item, Constants.CARDINALIDAD_TAG)) {
			this.cardinalidadMininima = item.getAttribute(Constants.CARDINALIDAD_MIN_ATTR);
			this.cardinalidadMaxima = item.getAttribute(Constants.CARDINALIDAD_MAX_ATTR);
		}
	}

	public void agregar(Parser parser) {
		parser.agregarComponenteParser(this);
	}

	public Componente getComponente() {
		return atributo;
	}

	@Override
	protected String getTag() {
		return Constants.ATRIBUTO_TAG;
	}
}
