package mereditor.interfaz.swt;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mereditor.interfaz.swt.listeners.AccionesProvider;

import org.eclipse.swt.SWT;
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
		item.addSelectionListener(AccionesProvider.nuevo);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Abrir Proyecto");
		item.setImage(this.getImagen("abrir.png"));
		item.addSelectionListener(AccionesProvider.abrir);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Guardar Proyecto");
		item.setImage(this.getImagen("guardar.png"));
		item.addSelectionListener(AccionesProvider.guardar);
		proyectoItems.add(item);
		
		item = new ToolItem(this.toolBar, SWT.SEPARATOR);
		
		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Imprimir");
		item.setImage(this.getImagen("imprimir.png"));
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Exportar");
		item.setImage(this.getImagen("exportar.png"));
		item.addSelectionListener(AccionesProvider.exportar);
		proyectoItems.add(item);
		
		item = new ToolItem(this.toolBar, SWT.SEPARATOR);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Nuevo Diagrama");
		item.setImage(this.getImagen("diagrama.png"));
		item.addSelectionListener(AccionesProvider.nuevoDiagrama);
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Agregar Entidad");
		item.setImage(this.getImagen("entidad.png"));
		item.addSelectionListener(AccionesProvider.agregarEntidad);
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Agregar Relacion");
		item.setImage(this.getImagen("relacion.png"));
		item.addSelectionListener(AccionesProvider.agregarRelacion);
		proyectoItems.add(item);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Agregar Jerarquia");
		item.setImage(this.getImagen("jerarquia.png"));
		item.addSelectionListener(AccionesProvider.agregarJerarquia);
		proyectoItems.add(item);
		
		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Validar");
		item.setImage(this.getImagen("validar.png"));
		item.addSelectionListener(AccionesProvider.validar);
		proyectoItems.add(item);
	
		item = new ToolItem(this.toolBar, SWT.SEPARATOR);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Zoom");
		item.setImage(this.getImagen("zoom.png"));
		item.addSelectionListener(AccionesProvider.zoomIn);
		proyectoItems.add(item);
		
		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Explorador del Proyecto");
		item.setImage(this.getImagen("tree_mode.png"));
		item.addSelectionListener(AccionesProvider.mostrarArbol);
		proyectoItems.add(item);
		
		item = new ToolItem(this.toolBar, SWT.SEPARATOR);

		item = new ToolItem(this.toolBar, SWT.PUSH);
		item.setToolTipText("Salir");
		item.setImage(this.getImagen("salir.png"));
		item.addSelectionListener(AccionesProvider.salir);

		this.habilitarItems(false);
	}

	private void habilitarItems(boolean habilitar) {
		for (ToolItem item : this.proyectoItems)
			item.setEnabled(habilitar);
	}

	private Image getImagen(String nombre) {
		return new Image(this.toolBar.getDisplay(), PATH_IMAGENES + nombre);
	}

	@Override
	public void update(Observable principal, Object arg) {
		this.habilitarItems(this.principal.getProyecto() != null);
	}
}