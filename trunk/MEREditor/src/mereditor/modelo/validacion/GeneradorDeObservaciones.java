package mereditor.modelo.validacion;

public class GeneradorDeObservaciones {
	private String obs_caracteristicas;
	private String obs_coleccion;
	private String nombreComp;

	public GeneradorDeObservaciones(String nombreDelComponente) {
		nombreComp = nombreDelComponente;
		obs_caracteristicas = "";
		obs_coleccion = "";
	}

	public void agregarCaracteristicaNoDefinida (String nombreCaracteristica){
		this.observacionSobreUnaCaracteristica(nombreCaracteristica, "Sin definir");
	}
	
	public void observacionSobreUnaCaracteristica(String nombreAtributo,
			String observacion) {
		if (observacion != null) {
			obs_caracteristicas = obs_caracteristicas == "" ? "Validacion de caracteristicas\n"
					: obs_caracteristicas;
			obs_caracteristicas += nombreAtributo + " : " + observacion + "/n";
		}

	}

	public void observacionSobreItemDeColeccion(String nombreDelItem,
			String observacion) {
		if (observacion != null) {
			obs_coleccion = obs_coleccion == "" ? "Validacion de componentes agregados\n"
					: obs_coleccion;
			obs_coleccion += nombreDelItem + " : " + observacion + "/n";
		}

	}

	public String getObservaciones() {
		if (obs_coleccion.isEmpty() && obs_caracteristicas.isEmpty())
			return null;
		return "Observaciones Componente " + nombreComp + "/n"
				+ obs_caracteristicas + obs_coleccion;
	}
}
