package mereditor.modelo.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public abstract class Componente implements Comparable<Componente> {
	/**
	 * Id del componente
	 */
	protected String id;

	/**
	 * Id del padre del componente
	 */
	protected Map<String, Componente> padres = new HashMap<>();

	/**
	 * Lista de validaciones.
	 */
	protected List<mereditor.modelo.validacion.Validacion> validaciones = new ArrayList<>();

	public Componente(String id) {
		this.id = id;
		this.addValidaciones();
	}

	public Componente() {
		this(UUID.randomUUID().toString());
	}

	public Componente(String id, Componente padre) {
		this(id);
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

	public Collection<Componente> getAllPadres() {
		return Collections.unmodifiableCollection(this.padres.values());
	}

	public void setPadre(Componente padre) {
		this.padres.put(padre.getId(), padre);
	}

	/**
	 * Carga las validaciones para este componente.
	 */
	public void addValidaciones() {
	}

	/**
	 * Valida el componente y devuelve las observaciones correspondientes en el
	 * caso que no sea válido.
	 * 
	 * @return Devuelve <code>null</code> si es válido o las observaciones
	 *         correspondientes si no lo es.
	 */
	public String validar() {
		List<String> observaciones = new ArrayList<>();

		for (mereditor.modelo.validacion.Validacion validacion : this.validaciones)
			observaciones.add(validacion.validar(this).trim());

		// Eliminar todas las observaciones vacías.
		while (observaciones.remove(""));

		return StringUtils.join(observaciones, "\n").trim();
	}

	/**
	 * Implementación de la evaluación de igualdad por comparación de ids de
	 * componentes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (Componente.class.isInstance(obj))
			return obj == null ? false : this.getId().equals(((Componente) obj).getId());
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
	public static <T> Set<T> filtrarComponentes(Class<T> clazz, Collection<Componente> coleccion) {
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
