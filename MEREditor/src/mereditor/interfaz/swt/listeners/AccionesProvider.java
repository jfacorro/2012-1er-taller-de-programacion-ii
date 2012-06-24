package mereditor.interfaz.swt.listeners;

import mereditor.interfaz.swt.Principal;
import mereditor.interfaz.swt.TreeManager;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class AccionesProvider {
	private static Principal principal() {
		return Principal.getInstance();
	}

	/**
	 * Nuevo proyecto.
	 */
	public static final SelectionListener nuevo = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				principal().nuevoProyecto();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Abrir Proyecto.
	 */
	public static final SelectionListener abrir = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			principal().abrirProyecto();
		}
	};

	/**
	 * Guardar proyecto.
	 */
	public static final SelectionListener guardar = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				principal().guardarProyecto();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};

	public static final SelectionListener imprimir = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			principal().imprimir();
		};
	};

	public static final SelectionListener exportar = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			principal().exportar();
		};
	};

	public static final SelectionListener zoomIn = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			principal().zoomIn();
		};
	};

	public static final SelectionListener zoomOut = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			principal().zoomOut();
		};
	};

	/**
	 * Agregar una Relacion al diagrama actual.
	 */
	public static final SelectionListener agregarRelacion = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			principal().agregarRelacion();
		};
	};

	/**
	 * Agregar una Jerarquia al diagrama actual.
	 */
	public static final SelectionListener agregarJerarquia = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			principal().agregarJerarquia();
		};
	};
	
	public static final SelectionListener validar = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			principal().validar();
		};
	};

	/**
	 * Mostrar arbol.
	 */
	public static final SelectionListener mostrarArbol = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				TreeManager.mostrar();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Agregar una entidad al diagrama actual.
	 */
	public static final SelectionListener agregarEntidad = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			principal().agregarEntidad();
		}
	};

	/**
	 * Agregar un nuevo diagrama al actual.
	 */
	public static final SelectionListener nuevoDiagrama = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				principal().nuevoDiagrama();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Salir de la aplicación.
	 */
	public static final SelectionListener salir = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			principal().salir();
		}
	};

}
