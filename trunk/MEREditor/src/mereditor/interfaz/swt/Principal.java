package mereditor.interfaz.swt;

import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
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
	protected FigureCanvas figureCanvas;
	private Figure contents;

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

		this.figureCanvas = new FigureCanvas(shell);
		this.shell.setLayout(new GridLayout(2, false));
		this.generarContenido();
		this.figureCanvas.setContents(this.contents);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);
		this.figureCanvas.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	private void generarContenido() {
		this.contents = new Figure();

		Figure fig1 = this.agregarFigura(new EntidadFigure(new Entidad(
				"Boleteria")), new Point(10, 10));
		Figure fig2 = this.agregarFigura(
				new EntidadFigure(new Entidad("Fila")), new Point(200, 200));
		
		Figure rel = this.agregarFigura(
				new RelacionFigure(new Relacion("Rel")), new Point(0, 200));

		this.agregarConexion(fig1, rel);
		this.agregarConexion(fig2, rel);
	}

	public void mostrar() {
		this.shell.open();
	}

	public Figure agregarFigura(Figure figure, Point pos) {
		figure.setLocation(pos);

		this.contents.add(figure);

		return figure;
	}

	public void agregarConexion(Figure f1, Figure f2) {
		PolylineConnection c = new PolylineConnection();
		//c.setTargetDecoration(new PolygonDecoration());
		ChopboxAnchor sourceAnchor = new ChopboxAnchor(f1);
		ChopboxAnchor targetAnchor = new ChopboxAnchor(f2);
		c.setSourceAnchor(sourceAnchor);
		c.setTargetAnchor(targetAnchor);

		this.contents.add(c);
	}
}
