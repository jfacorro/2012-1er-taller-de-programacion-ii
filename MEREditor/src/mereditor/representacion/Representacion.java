package mereditor.representacion;

import java.util.HashMap;
import java.util.Map;

public class Representacion {
	protected Representacion prototype;

	protected Map<String, Object> properties = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public <T> T getProperty(String name) {
		if (this.properties.containsKey(name))
			return (T) this.properties.get(name);
		
		return (T) this.prototype.getProperty(name);
	}

	public void setProperty(String name, Object value) {
		// Si es entero convertir automáticamente
		if(value != null && value.toString().matches("\\d*"))
			value = Integer.parseInt(value.toString());

		this.properties.put(name, value);
	}
}
