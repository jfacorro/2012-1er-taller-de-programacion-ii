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
	
	protected Document source;
	protected Validacion validacionGlobal;
	ElementParser validacionParser;
	ElementParser diagramaPpalParser;
	List <Componente> componentes;
	List<Linkeable> parsersLinkeables;
	List<ElementParser> parsersDeComponentes;
	
	
	public Parser (Document sourceXML) {
		source= sourceXML;
		validacionGlobal= null;
		validacionParser= null;
		componentes= new ArrayList <Componente>();
		parsersLinkeables= new ArrayList <Linkeable>();
		parsersDeComponentes=new ArrayList <ElementParser>();
		
	}
	
	public void parsear() {
		ParserFactory parserFact= new ParserFactory();

		Element raiz= source.getDocumentElement();
		NodeList hijos= raiz.getChildNodes();
		Node nodo= null;
		ElementParser compParser= null;
		for (int i=0; i<hijos.getLength(); i++) {
			nodo= hijos.item(i);
			if (nodo instanceof Element ){
				compParser = parserFact.getComponenteParser(nodo.getNodeName());
				//try
				compParser.parsear((Element)nodo);
				compParser.agregarAParser(this);
				//catch exc parser
			}
		}
		validacionGlobal= (Validacion) validacionParser.getElementoParseado();
		inicializarComponentes();
		/*Una vez parseados los componentes, los puedo linkear*/
		linkearComponentes();
	}

	/*PRE: componentes inicializados*/
	private void linkearComponentes() {
		for (int i=0;i<parsersLinkeables.size();i++){
			for (int j=0;j<componentes.size(); j++){
				parsersLinkeables.get(i).linkear( componentes.get(j) );
			}
		}
		
	}

	private void inicializarComponentes() {
		for (int i=0; i<parsersDeComponentes.size(); i++ ){
			componentes.add( (Componente) parsersDeComponentes.get(i).getElementoParseado());
		}
	}

	public Diagrama getDiagramaPpal() {
		return (Diagrama) diagramaPpalParser.getElementoParseado();
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public Validacion getValidacionGlobal() {
		return validacionGlobal;
	}
	public void agregarParserDeComponente(ElementParser parserDeComponente) {
		parsersDeComponentes.add(parserDeComponente);
	}
	public void agregarParserLinkeable(Linkeable parserLinkeable) {
		parsersLinkeables.add(parserLinkeable);
	}

}
