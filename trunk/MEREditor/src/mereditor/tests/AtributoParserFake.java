package mereditor.tests;

import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.parser.AtributoParser;
import mereditor.parser.Parser;

public class AtributoParserFake extends AtributoParser {
	
	public AtributoParserFake(Parser parser) {
		super(parser);
	}
	
	Atributo getAtributo() {
		return super.atributoParseado;
	}
	
	String getCardMin() {
		return cardMin;
	}
	
	String getCardMax() {
		return cardMax;
	}
	
	TipoAtributo getTipoAtributo(){
		return tipoAttr;
	}
	
	List<ComponenteNombre> getAtributosContenidos() {
		return atributosParseados;
	}
	
}
