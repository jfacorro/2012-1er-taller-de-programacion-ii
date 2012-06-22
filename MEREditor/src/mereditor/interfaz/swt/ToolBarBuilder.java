package mereditor.interfaz.swt;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mereditor.interfaz.swt.dialogs.AgregarEntidadDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ToolBarBuilder implements Observer {
	private static final String PATH_IMAGENES = "src/recursos/imagenes/";

	private Principal principal;
	private ToolBar toolBar;
	private List<ToolItem> proyectoItems = new ArrayList<>();

	public static ToolBar build(Principal principal) {
		return new ToolBarBuilder(principal).toolBar;
	}

	private ToolBarBuilder(Principal principal) {
		this.toolBar = new ToolBar(principal.getShell(), SWT.HORIZONTAL
				| SWT.FLAT);
		this.principal = principal;
		this.principal.addObserver(this);
		this.init();
	}

	private void init() {
		ToolItem item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nuevo Proyecto");
		item.setImage(this.getImagen("nuevo.png"));
		item.addSelectionListener(this.nuevo);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Abrir Proyecto");
		item.setImage(this.getImagen("abrir.png"));
		item.addSelectionListener(this.abrir);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Guardar Proyecto");
		item.setImage(this.getImagen("guardar.png"));
		item.addSelectionListener(this.guardar);
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Imprimir");
		item.setImage(this.getImagen("imprimir.png"));
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Exportar");
		item.setImage(this.getImagen("exportar.png"));
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nueva Entidad");
		item.setImage(this.getImagen("entidad.png"));
		item.addSelectionListener(this.nuevaEntidad);
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nueva Relacion");
		item.setImage(this.getImagen("relacion.png"));
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nueva Jerarquia");
		item.setImage(this.getImagen("jerarquia.png"));
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nuevo Diagrama");
		item.setImage(this.getImagen("diagrama.png"));
		item.addSelectionListener(nuevoDiagrama);
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Validar");
		item.setImage(this.getImagen("validar.png"));
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Zoom");
		item.setImage(this.getImagen("zoom.png"));
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Explorador del Proyecto");
		item.setImage(this.getImagen("tree_mode.png"));
		item.addSelectionListener(mostrarArbol);
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Salir");
		item.setImage(this.getImagen("salir.png"));
		item.addSelectionListener(salir);

		this.habilitarItems(false);
	}

	private void habilitarItems(boolean habilitar) {
		for (ToolItem item : this.proyectoItems)
			item.setEnabled(habilitar);
	}

	private Image getImagen(String nombre) {
		return new Image(this.toolBar.getDisplay(), PATH_IMAGENES + nombre);
	}

	private SelectionListener nuevo = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				principal.nuevoProyecto();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	private SelectionListener abrir = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			principal.abrirProyecto();
		}
	};

	private SelectionListener guardar = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				principal.guardar();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};

	private SelectionListener mostrarArbol = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				TreeManager.mostrar();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};
	private SelectionListener nuevaEntidad = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			new AgregarEntidadDialog().abrir();
		}
	};

	private SelectionListener nuevoDiagrama = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				principal.nuevoDiagrama();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	private final SelectionListener salir = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			principal.salir();
		}
	};

	@Override
	public void update(Observable principal, Object arg) {
		this.habilitarItems(this.principal.getProyecto() != null);
	}
}