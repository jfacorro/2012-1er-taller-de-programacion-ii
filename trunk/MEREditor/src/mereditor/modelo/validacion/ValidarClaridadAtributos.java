package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;
import mereditor.modelo.base.ComponenteAtributos;

import org.apache.commons.lang.StringUtils;

public class ValidarClaridadAtributos implements Validacion {
	@Override
	public String validar(Componente componente) {
		List<String> observaciones = new ArrayList<>();

		Diagrama diagrama = (Diagrama) componente;
		Set<ComponenteAtributos> componentes = Componente.filtrarComponentes(
				ComponenteAtributos.class, diagrama.getComponentes());

		for (ComponenteAtributos comp : componentes) {
			if (comp.getAtributos().size() > Validacion.MAX_ATRIBUTOS) {
				String msj = "El componente %s tiene más de %d atributos lo cual reduce su claridad.";
				observaciones.add(String.format(msj, comp.toString(), Validacion.MAX_ATRIBUTOS));
			}
		}

		return StringUtils.join(observaciones, "\n");
	}
}
