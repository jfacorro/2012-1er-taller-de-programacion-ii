package mereditor.interfaz.swt;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
		fileNewItem.addSelectionListener(this.nuevo);

		MenuItem fileOpenItem = new MenuItem(fileMenu, SWT.PUSH);
		fileOpenItem.setText("&Abrir");
		fileOpenItem.addSelectionListener(this.abrir);

		MenuItem fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Guardar");
		fileSaveItem.addSelectionListener(this.guardar);

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Salir");
		fileExitItem.addSelectionListener(this.salir);
		
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
		nuevoDiagramaItem.addSelectionListener(this.nuevoDiagrama);

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

	private SelectionListener nuevo = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try {
				Principal.getInstance().nuevoProyecto();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private SelectionListener abrir = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Principal.getInstance().abrirProyecto();
		}
	};
	
	private SelectionListener guardar = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try {
				Principal.getInstance().guardar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private SelectionListener salir = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Principal.getInstance().salir();
		}
	};
	
	private SelectionListener nuevoDiagrama = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try {
				Principal.getInstance().nuevoDiagrama();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void update(Observable o, Object arg) {
		this.habilitarItems(this.principal.getProyecto() != null);		
	}
}
