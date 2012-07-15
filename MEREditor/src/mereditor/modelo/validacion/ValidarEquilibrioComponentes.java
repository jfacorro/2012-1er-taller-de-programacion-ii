package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;

import mereditor.control.Proyecto;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.apache.commons.lang.StringUtils;

public class ValidarEquilibrioComponentes implements Validacion {

	@Override
	public String validar(Componente componente) {
		List<String> observaciones = new ArrayList<>();

		Proyecto proyecto = (Proyecto) componente;

		int total = 0;
		for (Diagrama diagrama : proyecto.getDiagramas())
			total += diagrama.getComponentes().size();

		int promedio = total / proyecto.getDiagramas().size();

		for (Diagrama diagrama : proyecto.getDiagramas()) {
			int delta = promedio - diagrama.getComponentes().size();
			if (Math.abs(delta) >= Validacion.MAX_DESVIACION_COMPONENTES) {
				String msj = "El diagrama %s tiene %d componentes mientras que el promedio es %d.";
				observaciones.add(String.format(msj, diagrama.getNombre(), diagrama
						.getComponentes().size(), promedio));
			}
		}

		return StringUtils.join(observaciones, "\n").trim();
	}
}
