package mereditor.modelo.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class Componente implements Comparable<Componente> {
	/**
	 * Id del componente
	 */
	protected String id;

	/**
	 * Id del padre del componente
	 */
	protected Map<String, Componente> padres = new HashMap<>();

	public Componente() {
		this(UUID.randomUUID().toString());
	}

	public Componente(String id) {
		this.id = id;
	}

	public Componente(String id, Componente padre) {
		this.id = id;
		this.setPadre(padre);
	}

	public String getId() {
		return id;
	}

	public Componente getPadre() {
		if (this.padres.size() > 1)
			throw new RuntimeException("Tiene más de un padre.");

		return this.padres.isEmpty() ? null : padres.values().iterator().next();
	}

	public Componente getPadre(String id) {
		return padres.get(id);
	}

	public void setPadre(Componente padre) {
		this.padres.put(padre.getId(), padre);
	}

	/**
	 * Valida el componente y devuelve las observaciones correspondientes en el
	 * caso que no sea válido.
	 * 
	 * @return Devuelve <code>null</code> si es válido o las observaciones
	 *         correspondientes si no lo es.
	 */
	public abstract String validar();

	/**
	 * Implementación de la evaluación de igualdad por comparación de ids de
	 * componentes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (Componente.class.isInstance(obj))
			return obj == null ? false : this.getId().equals(
					((Componente) obj).getId());
		else
			return false;
	}

	/**
	 * Indica si la instancia de este componente es de la clase que se pasa por
	 * parámetro.
	 * 
	 * @param tipoComponente
	 * @return
	 */
	public boolean es(Class<?> tipoComponente) {
		return tipoComponente.isInstance(this);
	}

	/**
	 * Indica si este componente tiene al especificado por parámetro como hijo.
	 * 
	 * @param componente
	 * @return
	 */
	public boolean contiene(Componente componente) {
		return false;
	}

	/**
	 * Devuelve la lista de objectos filtrados por el tipo que se le pasa.
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Set<T> filtrarComponentes(Class<T> clazz,
			Collection<Componente> coleccion) {
		Set<T> lista = new HashSet<>();

		for (Componente componente : coleccion)
			if (clazz.isInstance(componente))
				lista.add(clazz.cast(componente));

		return lista;
	}
	
	@Override
	public int compareTo(Componente componente) {
		return this.toString().compareTo(componente.toString());
	}
}
