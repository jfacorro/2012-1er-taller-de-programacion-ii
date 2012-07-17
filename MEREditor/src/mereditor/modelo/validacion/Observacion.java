package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Observacion {
	public static final String SIN_OBSERVACIONES = "No hay observaciones de validación.";
	public static final String NO_DEFINIDO = "No definida";

	private List<Observacion> observaciones = new ArrayList<>();
	private String mensaje = "";
	private Validable validable;

	public Observacion(Validable validable) {
		this.validable = validable;
	}

	public Observacion(Validable validable, String mensaje) {
		this(validable);
		this.mensaje = mensaje;
	}

	public Observacion noDefinida(Validable validable) {
		Observacion obs = new Observacion(validable);
		obs.mensaje = Observacion.NO_DEFINIDO;
		return obs;
	}

	public void addObservacion(Observacion observacion) {
		if (!observacion.isEmpty())
			this.observaciones.add(observacion);
	}

	public void addObservaciones(List<Observacion> observaciones) {
		if (observaciones != null)
			for (Observacion observacion : observaciones)
				if (!observacion.isEmpty())
					this.observaciones.add(observacion);
	}

	public boolean isEmpty() {
		return StringUtils.isEmpty(mensaje) && this.observaciones.isEmpty();
	}

	@Override
	public String toString() {
		return this.toString(1);
	}

	/**
	 * Genera una representacion de texto con el mensaje de esta observacion y
	 * sus hijos, colocando un cantidad de tabuladores directamente proporcional
	 * con el nivel indicado.
	 * 
	 * @param nivel
	 * @return
	 */
	public String toString(int nivel) {
		if (this.isEmpty())
			return null;

		List<String> observaciones = new ArrayList<>();

		observaciones.add(this.validable.toString() + ": " + this.mensaje);

		String tabs = StringUtils.repeat("\t", nivel);

		for (Observacion observacion : this.observaciones)
			observaciones.add(tabs + observacion.toString(nivel + 1));

		return generar(observaciones);
	}

	public static String generar(List<String> observaciones) {
		// Eliminar vacíos y nulos
		while (observaciones.remove(""))
			;
		while (observaciones.remove(null))
			;

		return StringUtils.join(observaciones, "\n");
	}
}
