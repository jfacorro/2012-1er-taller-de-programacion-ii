package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.apache.commons.lang.StringUtils;

public class ValidarClaridadComponentes implements Validacion {
	@Override
	public String validar(Componente componente) {
		List<String> observaciones = new ArrayList<>();

		Diagrama diagrama = (Diagrama) componente;

		if (diagrama.getComponentes().size() > Validacion.MAX_COMPONENTES) {
			String msj = "El diagrama %s tiene más de %d componentes lo cual reduce su claridad.";
			observaciones.add(String.format(msj, diagrama.getNombre(), Validacion.MAX_COMPONENTES));
		}

		return StringUtils.join(observaciones, "\n");
	}
}
