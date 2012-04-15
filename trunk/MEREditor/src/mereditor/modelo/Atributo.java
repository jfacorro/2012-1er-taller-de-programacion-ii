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
}
