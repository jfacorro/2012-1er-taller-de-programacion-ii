package mereditor.interfaz.swt;

import mereditor.control.Proyecto;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeManager {
	private Tree tree;

	public static Tree build(Principal principal) {
		return new TreeManager(principal).tree;
	}

	private TreeManager(Principal principal) {
		this.tree = new Tree(principal.getSashForm(), SWT.CENTER);
		this.tree.setBackground(new Color(null, 240, 240, 240));
		this.init();
	}

	private void init() {
	}

	/**
	 * Agregado del diagrama principal y sus hijos.
	 * 
	 * @param diagrama
	 * @param raiz
	 */
	public static void agregar(Diagrama diagrama, Tree raiz) {
		raiz.removeAll();
		raiz.setData(diagrama);
		TreeItem item = new TreeItem(raiz, SWT.NULL);
		item.setText(diagrama.getNombre());
		item.setData(diagrama);

		for (Diagrama diagramaHijo : diagrama.getDiagramas())
			agregar(diagramaHijo, item);
		
		for (Componente componente : diagrama.getComponentes())
			agregar(componente, item);
	}

	/**
	 * Agregado de diagrama no principal y sus hijos.
	 * 
	 * @param diagrama
	 * @param item
	 */
	private static void agregar(Diagrama diagrama, TreeItem item) {
		TreeItem hijo = new TreeItem(item, SWT.NULL);
		hijo.setText(diagrama.getNombre());
		hijo.setData(diagrama);

		for (Diagrama diagramaHijo : diagrama.getDiagramas())
			agregar(diagramaHijo, hijo);

		for (Componente componente : diagrama.getComponentes())
			agregar(componente, hijo);
	}

	/**
	 * Agregado de componente.
	 * 
	 * @param diagrama
	 * @param item
	 */
	private static void agregar(Componente componente, TreeItem padre) {
		TreeItem hijo = new TreeItem(padre, SWT.NULL);
		hijo.setText(componente.toString());
		hijo.setData(componente);
	}

	public static void cargar(Proyecto proyecto, Tree tree) {
		TreeManager.agregar(proyecto.getRaiz(), tree);		
	}
}
