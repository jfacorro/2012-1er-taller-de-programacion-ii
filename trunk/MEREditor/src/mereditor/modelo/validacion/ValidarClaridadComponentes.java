package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Diagrama;

public class ValidarClaridadComponentes implements Validacion {
	@Override
	public List<Observacion> validar(Validable componente) {
		List<Observacion> observaciones = new ArrayList<>();

		Diagrama diagrama = (Diagrama) componente;

		if (diagrama.getComponentes().size() > Validacion.MAX_COMPONENTES) {
			String msj = "El diagrama %s tiene más de %d componentes lo cual reduce su claridad.";
			msj = String.format(msj, diagrama.getNombre(), Validacion.MAX_COMPONENTES);
			observaciones.add(new Observacion(diagrama, msj));
		}

		return observaciones;
	}
}
