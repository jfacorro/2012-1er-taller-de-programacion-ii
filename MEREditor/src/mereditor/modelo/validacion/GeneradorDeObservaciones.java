package mereditor.modelo.validacion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class GeneradorDeObservaciones {
	private List<String> caracteristicas = new ArrayList<>();
	private List<String> items = new ArrayList<>();

	public void caracteristicaNoDefinida(String caracteristica) {
		this.observacionCaracteristica(caracteristica, "Sin definir");
	}

	public void observacionCaracteristica(String caracteristica,
			String observacion) {
		if (observacion != null && !observacion.trim().isEmpty()) {
			if(caracteristicas.isEmpty())
				caracteristicas.add("Validacion de caracteristicas");
			
			caracteristicas.add(caracteristica + " : " + observacion);
		}
	}

	public void observacionItem(String item, String observacion) {
		if (observacion != null && !observacion.trim().isEmpty()) {
			if(items.isEmpty())
				items.add("Validacion de componentes agregados");

			items.add(item + " : " + observacion);
		}
	}

	public String getObservaciones() {
		if (items.isEmpty() && caracteristicas.isEmpty())
			return null;
		
		String obs = StringUtils.join(caracteristicas, "\n");
		obs += StringUtils.join(items, "\n");
		
		return obs;
	}
}
