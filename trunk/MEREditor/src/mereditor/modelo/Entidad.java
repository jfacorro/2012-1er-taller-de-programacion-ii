package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.ComponenteNombre;
import mereditor.modelo.Atributo;

public class Entidad extends ComponenteNombre {
	
	public enum TipoEntidad {
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
	
	public Entidad (String nombre, String idEntidad, String idDiagrama, List<Atributo> atributos, List<ComponenteNombre> ids, TipoEntidad t){
		super (nombre,idEntidad,idDiagrama);
		this.atributos = atributos;
		this.identificadores= ids;
		this.tipo= t;
	}
	
	
	
}
