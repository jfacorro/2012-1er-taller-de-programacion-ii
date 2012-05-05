package mereditor.interfaz.swt;

import mereditor.modelo.Entidad;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
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
		
		Figure fig1 = principal.agregarEntidad(new Entidad("Boleteria"), new Point(10, 10));
		Figure fig2 = principal.agregarEntidad(new Entidad("Fila"), new Point(200, 200));
		
		principal.agregarConexion(fig1, fig2);

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
	
	public Figure agregarEntidad(Entidad entidad, Point posicion)
	{
		EntidadFigure entidadFigure = new EntidadFigure(entidad);
		DragDropControlador dragDropCtrl = new DragDropControlador();
		entidadFigure.addMouseListener(dragDropCtrl);
		entidadFigure.addMouseMotionListener(dragDropCtrl);
		
		contentsLayout.setConstraint(entidadFigure, new Rectangle(posicion.x, posicion.y, 100, 100));
		this.contents.add(entidadFigure);
		
		return entidadFigure;
	}
	
	public void agregarConexion(Figure f1, Figure f2) {
		PolylineConnection c = new PolylineConnection();
		ChopboxAnchor sourceAnchor = new ChopboxAnchor(f1);
		ChopboxAnchor targetAnchor = new ChopboxAnchor(f2);
		c.setSourceAnchor(sourceAnchor);
		c.setTargetAnchor(targetAnchor);
		
		this.contents.add(c);
	}
}
