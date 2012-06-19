package mereditor.interfaz.swt;

import mereditor.interfaz.swt.listeners.MenuArbolControlador;

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

	public static Menu build(Shell shell, Tree tree) {
		return new MenuArbolBuilder(shell, tree).menuArbol;
	}

	private MenuArbolBuilder(Shell shell, Tree tree) {
		menuArbol = new Menu(shell, SWT.POP_UP);
		controlador = new MenuArbolControlador(tree, menuArbol);
		this.init();
	}

	private void init() {

		MenuItem i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Editar");
		i.addListener(SWT.Selection, this.editar);
		i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Cortar");
		i.addListener(SWT.Selection, this.cortar);
		i = new MenuItem(menuArbol, SWT.DROP_DOWN);
		i.setText("Eliminar");
		i.addListener(SWT.Selection, this.eliminar);
		itemPegar = new MenuItem(menuArbol, SWT.DROP_DOWN);
		itemPegar.setText("Pegar");
		itemPegar.setEnabled(false);
		itemPegar.addListener(SWT.Selection, this.pegar);

	}

	private Listener eliminar = new Listener() {

		public void handleEvent(Event arg0) {
			controlador.eliminarItemActivo();
		}

	};

	private Listener editar = new Listener() {

		@Override
		public void handleEvent(Event arg0) {
			// TODO Auto-generated method stub

		}

	};

	private Listener cortar = new Listener() {

		public void handleEvent(Event arg0) {
			controlador.cortarItemActivo();
			itemPegar.setEnabled(true);
		}

	};

	private Listener pegar = new Listener() {

		public void handleEvent(Event arg0) {
			controlador.pegarItemCortado();
			itemPegar.setEnabled(false);
		}

	};
}
