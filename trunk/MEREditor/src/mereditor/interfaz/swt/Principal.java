package mereditor.interfaz.swt;

import mereditor.modelo.Entidad;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author jfacorro
 *
 */
public class Principal {
	public final String APP_NOMBRE = "MER Editor";
	
	private Shell shell;
	private LightweightSystem system;
	private Figure contents;
	private XYLayout contentsLayout;

	public static void main(String args[]) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Principal principal = new Principal(shell);
		principal.mostrar();
		
		principal.agregarEntidad(new Entidad("Boleteria"));
		principal.agregarEntidad(new Entidad("Fila"));

		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
	}

	public Principal(Shell shell) {
		this.shell = shell;
		this.shell.setSize(400, 400);
		this.shell.setText(APP_NOMBRE);

		this.system = new LightweightSystem(this.shell);
		this.contents = new Figure();		
		this.contentsLayout = new XYLayout();
		this.contents.setLayoutManager(contentsLayout);
		
		this.system.setContents(this.contents);
	}

	public void mostrar() {
		this.shell.open();
	}
	
	public void agregarEntidad(Entidad entidad)
	{
		EntidadFigure entidadFigure = new EntidadFigure(entidad);
		DragDropControlador dragDropCtrl = new DragDropControlador();
		entidadFigure.addMouseListener(dragDropCtrl);
		entidadFigure.addMouseMotionListener(dragDropCtrl);
		
		contentsLayout.setConstraint(entidadFigure, new Rectangle(10, 10, 100, 100));
		this.contents.add(entidadFigure);		
	}
}
