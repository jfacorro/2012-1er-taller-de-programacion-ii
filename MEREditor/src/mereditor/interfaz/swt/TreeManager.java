package mereditor.interfaz.swt;

import mereditor.control.Proyecto;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeManager {
	private static Tree tree;
	private static CTabItem tab;
	private static CTabFolder folder;
	private static TreeItem diagramaActivo;

	public static Tree build(Principal principal) {
		new TreeManager(principal);
		return TreeManager.tree;
	}

	private TreeManager(Principal principal) {
		folder = new CTabFolder(principal.getSashForm(), SWT.CENTER );
		tab = new CTabItem(folder, SWT.CLOSE | SWT.BOTTOM);
		tree = new Tree(folder, SWT.NO_SCROLL);
		this.init();
	}

	private void init() {
		tree.setBackground(new Color(null, 240, 240, 240));
		folder.setEnabled(false);
		folder.setSimple(false);
		folder.setSelection(tab);
		tab.setControl(tree);
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
		diagramaActivo= item;

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
		tab.setText(proyecto.getRaiz().getNombre());
		folder.setEnabled(true);
	}

	public static void mostrar() {
		if (tab.isDisposed()) {
			tab = new CTabItem(folder, SWT.CLOSE | SWT.BOTTOM );
			tab.setText(((Diagrama) tree.getTopItem().getData()).getNombre());
			tab.setControl(tree);
			folder.setSelection(tab);
		}
	}

	public static void agregarADiagramaActual(Diagrama nuevoDiagrama) {
		agregar(nuevoDiagrama, tree.getTopItem());
	}
	
	public static TreeItem getDiagramaActivo() {
		return diagramaActivo;
	}

	public static void setDiagramaActivo(TreeItem diagramaActivo) {
		TreeManager.diagramaActivo = diagramaActivo;
	}
	
}
