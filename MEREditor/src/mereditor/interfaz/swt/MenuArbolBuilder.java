package mereditor.interfaz.swt;

import mereditor.interfaz.swt.listeners.MenuArbolControlador;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;


public class MenuArbolBuilder {
	private Menu menuArbol;
	private Tree tree;

	public static Menu build(Shell shell,Tree tree) {
		return new MenuArbolBuilder(shell, tree).menuArbol;
	}
	
	private MenuArbolBuilder (Shell shell,Tree tree) {
		menuArbol= new Menu(shell, SWT.POP_UP);
		this.tree= tree;
		this.init();
	}

	private void init() {
		MenuItem i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Abrir");
		i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Cortar");
		i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Eliminar");
		new MenuArbolControlador (tree, menuArbol);
	}
}
