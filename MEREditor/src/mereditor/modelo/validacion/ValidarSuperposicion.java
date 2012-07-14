package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

public class ValidarSuperposicion implements Validacion {

	@Override
	public String validar(Componente componente) {
		List<String> observaciones = new ArrayList<>();

		Diagrama diagrama = (Diagrama) componente;

		for (Componente comp : diagrama.getComponentes()) {
			if (comp.getAllPadres().size() > 1) {
				String msj = "El componente %s pertenece a %d diagramas: %s";
				String diagramas = StringUtils.join(comp.getAllPadres(), ", ");
				observaciones.add(String.format(msj, comp.toString(), comp.getAllPadres().size(),
						diagramas));
			}
		}

		return StringUtils.join(observaciones, "\n");
	}
}
