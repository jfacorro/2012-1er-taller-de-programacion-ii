package mereditor.modelo;

import mereditor.modelo.base.Componente;

public class Validacion extends Componente {
	public enum EstadoValidacion {
		SIN_VALIDAR,
		VALIDADO
	}

	protected String observaciones;
	protected EstadoValidacion estado;
	
	public Validacion() {}

	public Validacion(EstadoValidacion estado, String observaciones) {
		this.estado = estado;
		this.observaciones = observaciones;
	}

	public EstadoValidacion getEstado() {
		return this.estado;
	}

	public String getObservaciones() {
		return observaciones;
	}
}
