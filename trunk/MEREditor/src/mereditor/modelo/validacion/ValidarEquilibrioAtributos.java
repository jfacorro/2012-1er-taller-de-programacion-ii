package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;

import mereditor.control.Proyecto;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteAtributos;

import org.apache.commons.lang.StringUtils;

public class ValidarEquilibrioAtributos implements Validacion {

	@Override
	public String validar(Componente componente) {
		List<String> observaciones = new ArrayList<>();

		Proyecto proyecto = (Proyecto) componente;

		// Inlcuir dentro de los componentes sólo a las entidades y las
		// relaciones.
		List<ComponenteAtributos> componentes = new ArrayList<>();
		componentes.addAll(proyecto.getEntidades());
		componentes.addAll(proyecto.getRelaciones());

		int total = 0;

		for (ComponenteAtributos componenteAtrs : componentes)
			total += componenteAtrs.getAtributos().size();

		int promedio = total / componentes.size();

		for (ComponenteAtributos componenteAtrs : componentes) {
			int delta = promedio - componenteAtrs.getAtributos().size();
			if (Math.abs(delta) > Validacion.MAX_DESVIACION_ATRIBUTOS) {
				String msj = "El componente %s tiene %d atributos mientras que el promedio es %d.";
				observaciones.add(String.format(msj, componenteAtrs.toString(), componenteAtrs
						.getAtributos().size(), promedio));
			}
		}

		return StringUtils.join(observaciones, "\n").trim();
	}

}
