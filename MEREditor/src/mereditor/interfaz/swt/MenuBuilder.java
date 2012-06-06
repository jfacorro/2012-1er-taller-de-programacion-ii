package mereditor.interfaz.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MenuBuilder {
	private Principal principal;
	private Menu menuBar;

	public static Menu build(Principal principal) {
		return new MenuBuilder(principal).menuBar;
	}

	private MenuBuilder(Principal principal) {
		this.menuBar = new Menu(principal.getShell(), SWT.BAR);
		this.principal = principal;
		this.init();
	}
	
	private void init() {
		MenuItem fileMenuHeader = new MenuItem(this.menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&Archivo");

		Menu fileMenu = new Menu(principal.getShell(), SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Guardar");

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Salir");

		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Ayuda");

		Menu helpMenu = new Menu(principal.getShell(), SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		MenuItem helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
		helpGetHelpItem.setText("&Sobre " + Principal.APP_NOMBRE + "...");

		principal.getShell().setMenuBar(menuBar);
	}
}
