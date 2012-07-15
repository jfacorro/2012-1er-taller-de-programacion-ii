package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mereditor.modelo.Entidad;
import mereditor.modelo.Proyecto;
import mereditor.modelo.base.Componente;

import org.apache.commons.lang.StringUtils;

public class ValidarEquilibrioRelaciones implements Validacion {

	@Override
	public String validar(Componente componente) {
		List<String> observaciones = new ArrayList<>();

		Proyecto proyecto = (Proyecto) componente;

		// Inlcuir dentro de los componentes sólo a las entidades y las
		// relaciones.
		Set<Entidad> entidades = proyecto.getEntidades();

		int total = 0;

		for (Entidad entidad : entidades)
			total += entidad.getRelaciones().size();

		int promedio = total / entidades.size();

		for (Entidad entidad : entidades) {
			int delta = promedio - entidad.getRelaciones().size();
			if (Math.abs(delta) > Validacion.MAX_DESVIACION_RELACIONES) {
				String msj = "La entidad %s está pertenece a %d relaciones mientras que el promedio es %d.";
				observaciones.add(String.format(msj, entidad.toString(), entidad.getRelaciones()
						.size(), promedio));
			}
		}

		return StringUtils.join(observaciones, "\n").trim();
	}

}
