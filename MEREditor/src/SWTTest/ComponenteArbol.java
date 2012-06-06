package SWTTest;

import mereditor.modelo.base.ComponenteNombre;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class ComponenteArbol {

	ComponenteNombre componente;

	public ComponenteArbol(ComponenteNombre c) {
		componente = c;
	}

	public TreeItem agregarA(Tree arbol) {
		TreeItem item = new TreeItem(arbol, SWT.NONE);
		item.setText(componente.getNombre());
		item.setData(componente);
		return item;
	}

	public TreeItem agregarA(TreeItem padre) {
		TreeItem item = new TreeItem(padre, SWT.NONE);
		item.setText(componente.getNombre());
		item.setData(componente);
		return item;
	}

}
