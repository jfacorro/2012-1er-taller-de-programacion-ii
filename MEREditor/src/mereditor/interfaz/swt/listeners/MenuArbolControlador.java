package mereditor.interfaz.swt.listeners;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/*Activa Menu desplegable en el item donde recibió el evento*/

public class MenuArbolControlador implements Listener{

	Tree observado;
	Menu menu;
	
	public MenuArbolControlador(Tree tree, Menu m){
		observado= tree;
		menu= m;
		observado.addListener(SWT.MouseDown, this);
	}
	
	public void handleEvent(Event event) {
		Point point = new Point (event.x, event.y);
		TreeItem treeItem= observado.getItem(point);
		if (treeItem != null){
			Rectangle area= treeItem.getBounds();
			Point p= new Point (area.x, area.y + area.height);
			p= observado.toDisplay(p);
			menu.setLocation(p.x, p.y);
			menu.setVisible(true);
			menu.setData(treeItem);
			agregarMenuListeners (treeItem);
		}
	}

	private void agregarMenuListeners(TreeItem treeItem) {
		MenuItem[] items = menu.getItems();
		for (int i=0; i<items.length;i++){
			new ItemMenuArbolControlador (items[i],treeItem);
		}
		
	}
	

}
