package mereditor.tests;

import java.util.List;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.parser.EntidadParser;
import mereditor.parser.Parser;

public class EntidadParserFake extends EntidadParser{

	public EntidadParserFake(Parser parser) {
		super(parser);
	}
	
	List<Atributo> getAtributos(){
		return super.atributos;
	}
	
	TipoEntidad getTipo (){
		return tipo;
	}
	
}
