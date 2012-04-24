package mereditor.tests;

import java.util.List;


import mereditor.modelo.Entidad.TipoEntidad;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.parser.EntidadParser;

public class EntidadParserFake extends EntidadParser{

	List<ComponenteNombre> getIdentificadores() {
		return super.identificadores;
	}
	
	List<ComponenteNombre> getAtributos(){
		return super.atributosParseados;
	}
	
	TipoEntidad getTipo (){
		return tipoEntidad;
	}
	
}
