package mereditor.modelo;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteNombre;

public class Entidad extends ComponenteNombre {

	public enum TipoEntidad {
		MAESTRA_COSA, MAESTRA_DOMINIO, TRANSACCIONAL_HISTORICA, TRANSACCIONAL_PROGRAMADA
	}

	protected List<Atributo> atributos;
	protected List<Componente> identificadores; // pueden ser tanto Atributos
												// como Entidades
	protected TipoEntidad tipo;

	public Entidad(String nombre) {
		super(nombre);
		identificadores = new LinkedList<Componente>();
		this.atributos = new LinkedList<Atributo>();
	}

	public Entidad(String nombre, String idEntidad, String idDiagrama,
			TipoEntidad t) {
		super(nombre, idEntidad, idDiagrama);
		this.atributos = new LinkedList<Atributo>();
		this.identificadores = new LinkedList<Componente>();
		this.tipo = t;
	}

	public void agregarIdentificador(ComponenteNombre identificador) {
		identificadores.add(identificador);
	}

	public void agregarAtributo(Atributo atributo) {
		identificadores.add(atributo);
	}
}
