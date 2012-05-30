package mereditor.interfaz.swt;

import mereditor.modelo.Entidad;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author jfacorro
 *
 */
public class Principal {
	public final String APP_NOMBRE = "MER Editor";
	
	private Shell shell;
	//private LightweightSystem system;
	protected FigureCanvas figureCanvas;
	private Figure contents;
	//private XYLayout contentsLayout;

	public static void main(String args[]) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);

		Principal principal = new Principal(shell);
		principal.mostrar();
		
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
	}

	public Principal(Shell shell) {
		this.shell = shell;
		this.shell.setSize(400, 400);
		this.shell.setText(APP_NOMBRE);

		//this.system = new LightweightSystem(this.shell);
		this.figureCanvas = new FigureCanvas(shell);
		//this.contentsLayout = new XYLayout();
		this.shell.setLayout(new GridLayout(2, false));
		//this.contents.setLayoutManager(contentsLayout);
		this.generarContenido();
		this.figureCanvas.setContents(this.contents);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);
		this.figureCanvas.setLayoutData(new GridData(GridData.FILL_BOTH));
	}
	
	private void generarContenido(){
		this.contents = new Figure();

		Figure fig1 = this.agregarEntidad(new Entidad("Boleteria"), new Point(10, 10));
		Figure fig2 = this.agregarEntidad(new Entidad("Fila"), new Point(200, 200));
		
		this.agregarConexion(fig1, fig2);
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
		
		entidadFigure.setBounds(new Rectangle(posicion.x, posicion.y, 100, 100))
;
		this.contents.add(entidadFigure);
		
		return entidadFigure;
	}
	
	public void agregarConexion(Figure f1, Figure f2) {
		CustomConnection c = new CustomConnection();
		//c.setTargetDecoration(new PolygonDecoration());
		ChopboxAnchor sourceAnchor = new ChopboxAnchor(f1);
		ChopboxAnchor targetAnchor = new ChopboxAnchor(f2);
		c.setSourceAnchor(sourceAnchor);
		c.setTargetAnchor(targetAnchor);
		
		
		this.contents.add(c);
	}
}
