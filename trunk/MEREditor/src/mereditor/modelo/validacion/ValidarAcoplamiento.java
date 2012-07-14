package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.Relacion;
import mereditor.modelo.Relacion.EntidadRelacion;
import mereditor.modelo.base.Componente;

public class ValidarAcoplamiento implements Validacion {

	@Override
	public String validar(Componente componente) {
		List<String> observaciones = new ArrayList<>();

		Diagrama diagrama = (Diagrama) componente;
		Set<Entidad> entidades = diagrama.getEntidades(false);
		Set<Relacion> relaciones = diagrama.getRelaciones(false);

		/*
		 *  Verificar si alguna relacion tiene una entidad de otro diagrama.		
		 */
		for (Relacion relacion : relaciones) {
			for (EntidadRelacion participante : relacion.getParticipantes()) {
				if (!entidades.contains(participante.getEntidad())) {
					String msj = "La relacion %s tiene como participante a la entidad %s que no se encunetra incluida en este diagrama.";
					observaciones.add(String.format(msj, relacion.getNombre(), participante
							.getEntidad().getNombre()));
				}
			}
		}
		
		/*
		 *  Verificar si alguna entidad pertenece a una relacion de otro diagrama.	
		 */
		for (Entidad entidad : entidades) {
			for (Relacion relacion : entidad.getRelaciones()) {
				if (!relaciones.contains(relacion)) {
					String msj = "La entidad %s pertence a la relación %s que no se encuentra incluida en este diagrama.";
					observaciones.add(String.format(msj, entidad.getNombre(), relacion.getNombre()));
				}
			}
		}
		

		for (Jerarquia jerarquia : diagrama.getJerarquias(false)) {
			if (!entidades.contains(jerarquia.getGenerica())) {
				String msj = "La jerarquia %s tiene como genérica a la entidad %s que no se encuentra incluida en este diagrama.";
				observaciones.add(String.format(msj, jerarquia.toString(), jerarquia.getGenerica()
						.getNombre()));
			}

			for (Entidad entidad : jerarquia.getDerivadas()) {
				if (!entidades.contains(entidad)) {
					String msj = "La jerarquia %s tiene como derivada a la entidad %s que no se encunetra incluida en este diagrama.";
					observaciones
							.add(String.format(msj, jerarquia.toString(), entidad.getNombre()));
				}
			}
		}

		return StringUtils.join(observaciones, "\n");
	}
}
