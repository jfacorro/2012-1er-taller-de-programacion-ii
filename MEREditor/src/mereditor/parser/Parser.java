package mereditor.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mereditor.modelo.base.Componente;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Validacion;


public class Parser {
	Diagrama diagramaPpal;
	List <Componente> componentes;
	Validacion validacionGlobal;
	
	Document source;
	
	public Parser (Document sourceXML) {
		diagramaPpal= null;
		source= sourceXML;
		componentes= new ArrayList <Componente>();
		
	}
	
	public void parsear() {
		ParserFactory parserFact= new ParserFactory();
		List <ElementParser> parsers= new ArrayList<ElementParser>();
		Element raiz= source.getDocumentElement();
		NodeList hijos= raiz.getChildNodes();
		Node nodo= null;
		for (int i=0; i<hijos.getLength(); i++) {
			nodo= hijos.item(i);
			if (nodo instanceof Element ){
				ElementParser compParser = parserFact.getComponenteParser(nodo.getNodeName());
				//try
				compParser.parsear((Element)nodo);
				//catch error parserFact
				parsers.add(compParser);	
			}
			
		}
		//hidratar a partir de los parsers.
	}

	public Diagrama getDiagramaConstruido() {
		return diagramaPpal;
	}


}
