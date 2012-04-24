package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;

public class Atributo extends ComponenteNombre {
	public enum TipoAtributo {
		CARACTERIZACION,
		DERIVADO_COPIA,
		DERIVADO_CALCULO
	}
	
	protected String cardinalidadMin;
	protected String cardinalidadMax;
	protected TipoAtributo tipo;
	protected List<Atributo> atributos;
	
	// Derivado copia
	protected Atributo original;
	
	// Derivado calculo
	protected String formula;
	
	public Atributo(String nombre) {
		super(nombre);
		
		this.atributos = new LinkedList<Atributo>();
	}

	public Atributo(String nombre, String idAtributo, String idContenedor, String cardMin, String cardMax,
			TipoAtributo tipo, List<Atributo> atributosContenidos ) {
		super (nombre,idAtributo,idContenedor);
		this.atributos = atributosContenidos;
		cardinalidadMin= cardMin;
		cardinalidadMax= cardMax;
	}
}
