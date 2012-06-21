package mereditor.interfaz.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ToolBarBuilder {
	private static final String PATH_IMAGENES = "src/recursos/imagenes/";

	private Principal principal;
	private ToolBar toolBar;

	public static ToolBar build(Principal principal) {
		return new ToolBarBuilder(principal).toolBar;
	}

	private ToolBarBuilder(Principal principal) {
		this.toolBar = new ToolBar(principal.getShell(), SWT.HORIZONTAL
				| SWT.FLAT);
		this.principal = principal;
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

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Imprimir");
		item.setImage(this.getImagen("imprimir.png"));

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Exportar");
		item.setImage(this.getImagen("exportar.png"));

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nueva Entidad");
		item.setImage(this.getImagen("entidad.png"));

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nueva Relacion");
		item.setImage(this.getImagen("relacion.png"));

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nueva Jerarqu�a");
		item.setImage(this.getImagen("jerarquia.png"));

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nuevo Diagrama");
		item.setImage(this.getImagen("diagrama.png"));
		item.addSelectionListener(nuevoDiagrama);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Validar");
		item.setImage(this.getImagen("validar.png"));

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Zoom");
		item.setImage(this.getImagen("zoom.png"));

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Explorador del Proyecto");
		item.setImage(this.getImagen("tree_mode.png"));
		item.addSelectionListener(mostrarArbol);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Salir");
		item.setImage(this.getImagen("salir.png"));
		item.addSelectionListener(salir);
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
			principal.abrir();
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

}
