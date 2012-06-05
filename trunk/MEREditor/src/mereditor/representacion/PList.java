package mereditor.representacion;

import java.util.HashMap;
import java.util.Map;

public class PList {
	public final String NOT_PRESENT = "NOT_PRESENT";
	protected PList prototype;

	protected Map<String, Object> properties = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		if (this.properties.containsKey(name))
			return (T) this.properties.get(name);

		if(this.prototype != null)
			return (T) this.prototype.get(name);

		return null;
	}

	public void set(String name, Object value) {
		// Si es entero convertir automáticamente
		if (value != null && value.toString().matches("\\d*"))
			value = Integer.parseInt(value.toString());

		this.properties.put(name, value);
	}

	public boolean has(String name) {
		return this.get(name) != null && this.get(name) != NOT_PRESENT;
	}

	public void remove(String name) {
		if (this.has(name))
			this.set(name, NOT_PRESENT);
	}
}
