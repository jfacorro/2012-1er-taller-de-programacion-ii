package mereditor.base;

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
		this.properties.put(name, value);
	}
}
