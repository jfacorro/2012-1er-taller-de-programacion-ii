package mereditor.interfaz.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
		/*
		 * Archivo
		 */
		MenuItem fileMenuHeader = new MenuItem(this.menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&Archivo");

		Menu fileMenu = new Menu(principal.getShell(), SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		
		MenuItem fileNewItem = new MenuItem(fileMenu, SWT.PUSH);
		fileNewItem.setText("&Nuevo");
		fileNewItem.addSelectionListener(this.nuevoListener);

		MenuItem fileOpenItem = new MenuItem(fileMenu, SWT.PUSH);
		fileOpenItem.setText("&Abrir");
		fileOpenItem.addSelectionListener(this.abrirListener);

		MenuItem fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Guardar");
		fileSaveItem.addSelectionListener(this.guardarListener);

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Salir");
		fileExitItem.addSelectionListener(this.salirListener);
		
		/*
		 * Proyecto
		 */
		MenuItem proyectoMenuHeader = new MenuItem(this.menuBar, SWT.CASCADE);
		proyectoMenuHeader.setText("&Proyecto");
		
		Menu proyectoMenu = new Menu(principal.getShell(), SWT.DROP_DOWN);
		proyectoMenuHeader.setMenu(proyectoMenu);

		

		/*
		 * Ayuda
		 */
		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Ayuda");

		Menu helpMenu = new Menu(principal.getShell(), SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		MenuItem helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
		helpGetHelpItem.setText("&Sobre " + Principal.APP_NOMBRE + "...");

		principal.getShell().setMenuBar(menuBar);
	}

	private SelectionListener nuevoListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try {
				Principal.getInstance().nuevo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private SelectionListener abrirListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Principal.getInstance().abrir();
		}
	};
	
	private SelectionListener guardarListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try {
				Principal.getInstance().guardar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private SelectionListener salirListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Principal.getInstance().salir();
		}
	};
}
