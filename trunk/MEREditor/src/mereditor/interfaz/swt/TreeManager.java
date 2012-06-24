package mereditor.interfaz.swt;

import mereditor.control.Control;
import mereditor.control.Proyecto;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeManager {
	private static Tree tree;
	private static CTabItem tab;
	private static CTabFolder folder;
	private static TreeItem diagramaActivo;

	public static Tree build(Composite composite) {
		new TreeManager(composite);
		return TreeManager.tree;
	}

	private CTabFolder2Listener minimizar = new CTabFolder2Adapter() {
		public void minimize(CTabFolderEvent event) {
			folder.setVisible(false);
		};
	};

	private TreeManager(Composite composite) {
		folder = new CTabFolder(composite, SWT.CENTER);
		folder.setSimple(false);
		folder.setMinimizeVisible(true);
		folder.addCTabFolder2Listener(this.minimizar);
		tab = new CTabItem(folder, SWT.CLOSE | SWT.BOTTOM);
		tab.setShowClose(false);
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
	private static void agregar(Diagrama diagrama, Tree raiz) {
		raiz.removeAll();
		raiz.setData(diagrama);
		TreeItem item = new TreeItem(raiz, SWT.NULL);
		item.setText(diagrama.getNombre());
		item.setData(diagrama);
		item.setImage(Principal.getIcono("diagrama.png"));
		diagramaActivo = item;

		for (Diagrama diagramaHijo : diagrama.getDiagramas())
			agregar(diagramaHijo, item);

		for (Componente componente : diagrama.getEntidades())
			agregar(componente, item);

		for (Componente componente : diagrama.getRelaciones())
			agregar(componente, item);

		for (Componente componente : diagrama.getJerarquias())
			agregar(componente, item);

		item.setExpanded(true);
	}

	/**
	 * Agregado de diagrama no principal y sus hijos.
	 * 
	 * @param diagrama
	 * @param padre
	 */
	private static void agregar(Diagrama diagrama, TreeItem padre) {
		TreeItem item = new TreeItem(padre, SWT.NULL);
		item.setText(diagrama.getNombre());
		item.setData(diagrama);
		item.setImage(Principal.getIcono("diagrama.png"));

		for (Diagrama diagramaHijo : diagrama.getDiagramas())
			agregar(diagramaHijo, item);

		for (Componente componente : diagrama.getEntidades())
			agregar(componente, item);

		for (Componente componente : diagrama.getRelaciones())
			agregar(componente, item);

		for (Componente componente : diagrama.getJerarquias())
			agregar(componente, item);
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

		String icono = ((Control<?>) componente).getIcono();
		hijo.setImage(Principal.getIcono(icono));
	}

	public static void cargar(Proyecto proyecto, Tree tree) {
		TreeManager.agregar(proyecto.getRaiz(), tree);
		tab.setText(proyecto.getNombre());
		folder.setEnabled(true);
	}

	public static void mostrar() {
		folder.setVisible(true);
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
