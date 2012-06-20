package mereditor.interfaz.swt.listeners;

import mereditor.interfaz.swt.Principal;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/*Activa Menu desplegable en el item donde recibió el evento*/

public class MenuArbolControlador implements Listener {

	Tree tree;
	TreeItem treeItemCortado;
	TreeItem treeItemActivo;
	Menu menu;

	public MenuArbolControlador(Tree tree, Menu m) {
		this.tree = tree;
		menu = m;
		tree.addListener(SWT.MouseDown, this);
		treeItemCortado = null;
		treeItemActivo = null;
	}

	public void handleEvent(Event event) {
		Point point = new Point(event.x, event.y);
		TreeItem treeItem = tree.getItem(point);
		if (treeItem != null) {
			treeItemActivo = treeItem;
			Rectangle area = treeItem.getBounds();
			Point p = new Point(area.x, area.y + area.height);
			p = tree.toDisplay(p);
			menu.setLocation(p.x, p.y);
			menu.setVisible(true);
		}
	}

	public void eliminarItemActivo() {
		if (treeItemActivo != null && !treeItemActivo.isDisposed()) {
			Componente componente = (Componente) treeItemActivo.getData();
			Diagrama diagrama = (Diagrama)treeItemActivo.getParent().getData();
			diagrama.eliminar(componente);

			Principal.getInstance().getPanelDisegno().actualizar();
			
			treeItemActivo.dispose();
		}			
	}

	public void cortarItemActivo() {
		treeItemCortado = treeItemActivo;
	}

	public void pegarItemCortado() {
		if (treeItemCortado != null && treeItemCortado != treeItemActivo
				&& !treeItemCortado.isDisposed() && validarPegar() ) {
			TreeItem nuevoItem = new TreeItem(treeItemActivo, SWT.NULL);
			nuevoItem.setText(treeItemCortado.getText());
			pegarHijos(nuevoItem, treeItemCortado);
			treeItemCortado.dispose();
			treeItemCortado = null;
		}
	}

	private void pegarHijos(TreeItem itemDestino, TreeItem itemOrigen) {
			TreeItem[] hijos = itemOrigen.getItems();
			TreeItem destinoActual = null;
			for (int i = 0; i < hijos.length; i++) {
				destinoActual = new TreeItem(itemDestino, SWT.NONE);
				destinoActual.setText(hijos[i].getText());
				pegarHijos(destinoActual, hijos[i]);
			}
	}

	private boolean validarPegar() {
		boolean valido = true;
		TreeItem itemActual = treeItemActivo;
		while (valido && (tree.indexOf(itemActual) < 0)) {
			valido = itemActual != treeItemCortado;
			itemActual = itemActual.getParentItem();
		}
		return valido;
	}

}
