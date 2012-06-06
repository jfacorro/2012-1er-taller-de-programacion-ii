package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;
import mereditor.modelo.Atributo;

public class Entidad extends ComponenteNombre {

	public enum TipoEntidad {
		MAESTRA_COSA, MAESTRA_DOMINIO, TRANSACCIONAL_HISTORICA, TRANSACCIONAL_PROGRAMADA
	}

	protected List<Atributo> atributos = new LinkedList<Atributo>();
	/**
	 * Pueden ser tanto Atributos como Entidades
	 */
	protected List<Componente> identificadores = new LinkedList<Componente>();
	protected TipoEntidad tipo;

	public Entidad() {
		super();
	}
	
	public Entidad(String nombre) {
		super(nombre);
	}

	public Entidad(String nombre, String id, TipoEntidad tipo) {
		super(nombre, id);
		this.tipo = tipo;
	}

	public Entidad(String nombre, String id, Componente padre, TipoEntidad tipo) {
		super(nombre, id, padre);
		this.tipo = tipo;
	}

	public void agregarIdentificador(ComponenteNombre identificador) {
		identificadores.add(identificador);
	}

	public void agregarAtributo(Atributo atributo) {
		identificadores.add(atributo);
	}

	public List<Atributo> getAtributos() {
		return this.atributos;
	}

	public TipoEntidad getTipo() {
		return this.tipo;
	}
}
