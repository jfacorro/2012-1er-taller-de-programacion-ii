package mereditor.interfaz.swt;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mereditor.interfaz.swt.listeners.AccionesProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MenuBuilder implements Observer {
	private Principal principal;
	private Menu menuBar;
	private List<MenuItem> proyectoItems = new ArrayList<>();

	public static Menu build(Principal principal) {
		return new MenuBuilder(principal).menuBar;
	}

	private MenuBuilder(Principal principal) {
		this.menuBar = new Menu(principal.getShell(), SWT.BAR);
		this.principal = principal;
		this.principal.addObserver(this);
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
		fileNewItem.addSelectionListener(AccionesProvider.nuevo);

		MenuItem fileOpenItem = new MenuItem(fileMenu, SWT.PUSH);
		fileOpenItem.setText("&Abrir");
		fileOpenItem.addSelectionListener(AccionesProvider.abrir);

		MenuItem fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Guardar");
		fileSaveItem.addSelectionListener(AccionesProvider.guardar);

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Salir");
		fileExitItem.addSelectionListener(AccionesProvider.salir);
		
		/*
		 * Proyecto
		 */
		MenuItem proyectoMenuHeader = new MenuItem(this.menuBar, SWT.CASCADE);
		proyectoMenuHeader.setText("&Proyecto");
		proyectoItems.add(proyectoMenuHeader);
		
		Menu proyectoMenu = new Menu(principal.getShell(), SWT.DROP_DOWN);
		proyectoMenuHeader.setMenu(proyectoMenu);

		MenuItem nuevoDiagramaItem = new MenuItem(proyectoMenu, SWT.PUSH);
		nuevoDiagramaItem.setText("&Nuevo Diagrama");
		nuevoDiagramaItem.addSelectionListener(AccionesProvider.nuevoDiagrama);
		
		MenuItem agregarEntidadItem = new MenuItem(proyectoMenu, SWT.PUSH);
		agregarEntidadItem.setText("&Nuevo Diagrama");
		agregarEntidadItem.addSelectionListener(AccionesProvider.agregarEntidad);

		/*
		 * Ayuda
		 */
		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("Ay&uda");

		Menu helpMenu = new Menu(principal.getShell(), SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		MenuItem helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
		helpGetHelpItem.setText("&Sobre " + Principal.APP_NOMBRE + "...");

		principal.getShell().setMenuBar(menuBar);
		
		this.habilitarItems(false);
	}
	
	private void habilitarItems(boolean habilitar) {
		for (MenuItem item : this.proyectoItems)
			item.setEnabled(habilitar);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.habilitarItems(this.principal.getProyecto() != null);		
	}
}
