package mereditor.interfaz.swt;

import mereditor.interfaz.swt.listeners.EditarComponenteListener;
import mereditor.interfaz.swt.listeners.MenuArbolControlador;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

public class MenuArbolBuilder {

	private Menu menuArbol;
	private MenuArbolControlador controlador;
	private MenuItem itemPegar;
	private boolean cortarActivo;

	public static Menu build(Shell shell, Tree tree) {
		return new MenuArbolBuilder(shell, tree).menuArbol;
	}

	private MenuArbolBuilder(Shell shell, Tree tree) {
		menuArbol = new Menu(shell, SWT.POP_UP);
		controlador = new MenuArbolControlador(tree, this);
		cortarActivo = false;
		this.init();
	}

	private void init() {
		MenuItem item = new MenuItem(menuArbol, SWT.DROP_DOWN);
		item.setText("Editar");
		item.addListener(SWT.Selection, new EditarComponenteListener(controlador));
		
		item = new MenuItem(menuArbol, SWT.DROP_DOWN);
		item.setText("Cortar");
		item.addListener(SWT.Selection, this.cortar);
		
		item = new MenuItem(menuArbol, SWT.DROP_DOWN);
		item.setText("Eliminar");
		item.addListener(SWT.Selection, this.eliminar);
		
		this.itemPegar = new MenuItem(menuArbol, SWT.DROP_DOWN);
		this.itemPegar.setText("Pegar");
		this.itemPegar.setEnabled(false);
		this.itemPegar.addListener(SWT.Selection, this.pegar);
	}

	private Listener eliminar = new Listener() {
		public void handleEvent(Event arg0) {
			controlador.eliminarItemActivo();
		}
	};

	private Listener cortar = new Listener() {
		public void handleEvent(Event arg0) {
			controlador.cortarItemActivo();
			cortarActivo = true;
		}
	};

	private Listener pegar = new Listener() {
		public void handleEvent(Event arg0) {
			controlador.pegarItemCortado();
			itemPegar.setEnabled(false);
			cortarActivo = false;
		}
	};

	public void mostrarOpciones(Componente componente) {
		if (componente instanceof Diagrama && cortarActivo) {
			itemPegar.setEnabled(true);
		}
	}

	public Menu getMenu() {
		return menuArbol;
	}

}
