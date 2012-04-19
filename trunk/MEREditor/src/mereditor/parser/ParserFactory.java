package mereditor.parser;


public class ParserFactory {
	
	public ParserFactory () {
	}
	
	public ElementParser getComponenteParser(String tipo){
		if ( tipo == EntidadParser.tipo )
			return new EntidadParser( );
		if ( tipo == RelacionParser.tipo )
			return new RelacionParser( );
		if ( tipo == JerarquiaParser.tipo )
			return new JerarquiaParser( );
		if ( tipo == DiagramaParser.tipo )
			return new DiagramaParser( );
		return new ValidacionParser();
	}
}
