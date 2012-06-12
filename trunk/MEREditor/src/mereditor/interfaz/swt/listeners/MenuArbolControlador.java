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
	String treeItemCortadoData;
	TreeItem treeItemActivo;
	Menu menu;
	
	public MenuArbolControlador(Tree tree, Menu m){
		observado= tree;
		menu= m;
		observado.addListener(SWT.MouseDown, this);
		treeItemCortadoData= null;
		treeItemActivo= null;
		agregarMenuListeners ();
	}
	
	public void handleEvent(Event event) {
		Point point = new Point (event.x, event.y);
		TreeItem treeItem= observado.getItem(point);
		if (treeItem != null){
			treeItemActivo= treeItem;
			Rectangle area= treeItem.getBounds();
			Point p= new Point (area.x, area.y + area.height);
			p= observado.toDisplay(p);
			menu.setLocation(p.x, p.y);
			menu.setVisible(true);
		}
	}

	private void agregarMenuListeners() {
		MenuItem[] items = menu.getItems();
		for (int i=0; i<items.length;i++){
			new ItemMenuArbolControlador (items[i]);
		}	
	}
	
	
	private class ItemMenuArbolControlador implements Listener {

		private static final String ACCION_ELIMINAR = "Eliminar";
		private static final String ACCION_CORTAR = "Cortar";
		private static final String ACCION_PEGAR = "Pegar";
		
		
		
		public ItemMenuArbolControlador (MenuItem menuItem) {
			menuItem.addListener(SWT.Selection, this);
		}

		public void handleEvent(Event event) {
			MenuItem menuItem=(MenuItem) event.widget;
			if (menuItem == null)
				return;
			String accion=menuItem.getText();
			if (accion.equals(ACCION_ELIMINAR)) {
				treeItemActivo.dispose();
			}
			else
			if (accion.equals(ACCION_CORTAR)) {
				treeItemCortadoData= treeItemActivo.getText();
				menuItem.setText(ACCION_PEGAR);
				treeItemActivo.dispose();
				treeItemActivo= null;
			}
			else
			if (accion.equals(ACCION_PEGAR)) {
				TreeItem nuevoItem = new TreeItem (treeItemActivo,SWT.NULL);
				nuevoItem.setText(treeItemCortadoData);
				menuItem.setText(ACCION_CORTAR);
			}
		}

	}
	
}
