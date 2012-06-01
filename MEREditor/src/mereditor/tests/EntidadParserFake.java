package mereditor.tests;

import java.util.List;


import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.parser.EntidadParser;
import mereditor.parser.Parser;

public class EntidadParserFake extends EntidadParser{

	public EntidadParserFake(Parser parser) {
		super(parser);
	}
	
	List<ComponenteNombre> getAtributos(){
		return super.atributosParseados;
	}
	
	TipoEntidad getTipo (){
		return tipoEntidad;
	}
	
}
