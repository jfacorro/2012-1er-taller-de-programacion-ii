package mereditor.modelo;

public class Validacion {
	protected String observaciones;
	protected String estado;

	public Validacion(String estadoValidacion, String obs) {
		observaciones = obs;
		estado = estadoValidacion;
	}
}
