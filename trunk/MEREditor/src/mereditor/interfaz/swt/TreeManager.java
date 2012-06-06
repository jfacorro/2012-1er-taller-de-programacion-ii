package mereditor.interfaz.swt;

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

	public static void agregar(Diagrama diagrama, Tree tree) {
		TreeItem item = new TreeItem(tree, SWT.NULL);
		item.setText(diagrama.getNombre());

		for (Componente componente : diagrama.getComponentes()) {
			agregar(componente, item);
		}
	}

	public static void agregar(Componente componente, TreeItem tree) {
		TreeItem item = new TreeItem(tree, SWT.NULL);
		item.setText(componente.toString());
	}

}
