package mereditor.interfaz.swt.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;

public class ItemMenuArbolControlador implements Listener {

	private static final String ACCION_ELIMINAR = "Eliminar";
	MenuItem menuItem;
	TreeItem treeItem;
	
	public ItemMenuArbolControlador (MenuItem menuItem, TreeItem treeItem ) {
		this.menuItem= menuItem;
		this.treeItem= treeItem;
		menuItem.addListener(SWT.Selection, this);
	}
	
	public void handleEvent(Event event) {
		MenuItem item=(MenuItem) event.widget;
		if (item == null)
			return;
		String accion=item.getText();
		if (accion.equals(ACCION_ELIMINAR)) {
				treeItem.dispose();
		}
	}

}
