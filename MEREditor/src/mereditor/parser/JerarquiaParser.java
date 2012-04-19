package mereditor.parser;

import mereditor.modelo.base.Componente;
import mereditor.modelo.Jerarquia;

import org.w3c.dom.Element;

public class JerarquiaParser implements ElementParser {

	public static final String tipo = "Jerarquia";
	Componente jerarquia;
	
	public JerarquiaParser () {
		jerarquia= new Jerarquia();
	}
	@Override
	public void parsear(Element nodo) {
		// TODO Auto-generated method stub

	}
	
	
}
