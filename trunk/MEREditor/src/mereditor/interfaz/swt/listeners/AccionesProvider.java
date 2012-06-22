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
	public static SelectionListener guardar = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				principal().guardar();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
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
