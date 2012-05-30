package mereditor.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Diagrama base
 * 
 * @author hordia
 * 
 */
public class Diagrama {

	/**
	 * Lista de entidades en el diagrama
	 */
	private List<Entidad> nodes = new ArrayList<Entidad>();

	public Diagrama() {
		createNodes();
	}

	protected void createNodes() {
		Entidad node = new Entidad("Obra"); //$NON-NLS-1$
		this.nodes.add(node);

		node = new Entidad("Teatro"); //$NON-NLS-1$
		this.nodes.add(node);
	}

	public List<Entidad> getNodes() {
		return this.nodes;
	}
}
