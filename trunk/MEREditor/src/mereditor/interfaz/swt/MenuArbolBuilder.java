package mereditor.interfaz.swt;

import mereditor.interfaz.swt.listeners.MenuArbolControlador;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;


public class MenuArbolBuilder {
	private Menu menuArbol;
	private Principal principal;

	public static Menu build(Principal principal) {
		return new MenuArbolBuilder(principal).menuArbol;
	}
	
	private MenuArbolBuilder (Principal principal) {
		menuArbol= new Menu(principal.getShell(), SWT.POP_UP);
		this.principal= principal;
		this.init();
	}

	private void init() {
		MenuItem i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Abrir");
		//i.addListener(SWT.Selection, new AbrirDiagramaListener(principal.getTree(), menuArbol, carpetaDeDibujo));
		i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Cortar");
		i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Eliminar");
		new MenuArbolControlador (principal.getTree(), menuArbol);
	}
}
