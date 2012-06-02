package mereditor.modelo;

import mereditor.modelo.base.Componente;

public class Validacion extends Componente {
	protected String observaciones;
	protected String estado;
	
	public Validacion() {}

	public Validacion(String estadoValidacion, String obs) {
		observaciones = obs;
		estado = estadoValidacion;
	}
}
