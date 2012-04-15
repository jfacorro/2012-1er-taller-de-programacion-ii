package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;

public class Entidad extends ComponenteNombre {
	
	enum TipoEntidad {
		MAESTRA,
		TRANSACCIONAL
	}
	
	protected List<Atributo> atributos;
	protected List<ComponenteNombre> identificadores; // pueden ser tanto Atributos como Entidades
	protected TipoEntidad tipo;
	
	public Entidad(String nombre) {
		super(nombre);
		
		this.atributos = new LinkedList<Atributo>();
	}
}
