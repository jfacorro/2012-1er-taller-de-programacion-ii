package mereditor.interfaz.swt;

import mereditor.control.DiagramaControl;
import mereditor.xml.ModeloParserXml;
import mereditor.xml.RepresentacionParserXml;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author jfacorro
 * 
 */
public class Principal {
	public static final Color defaultBackgroundColor = new Color(null, 255, 255, 255);
	public static final String APP_NOMBRE = "MER Editor";
	private static final String PATH_ARCHIVO = "xml/tests/";
	private static final String NOMBRE_ARCHIVO = "modelo";

	private Shell shell;
	private FigureCanvas figureCanvas;
	private Figure contents;
	private DiagramaControl diagrama;

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
		this.shell.setMaximized(true);
		this.shell.setText(APP_NOMBRE);
		this.shell.setLayout(new GridLayout(1, false));

		this.figureCanvas = new FigureCanvas(shell);
		this.figureCanvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.figureCanvas.setBackground(Principal.defaultBackgroundColor);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);
		
		this.abrirModelo(PATH_ARCHIVO, NOMBRE_ARCHIVO);
	}

	/**
	 * Genera el contenido.
	 */
	private void abrirModelo(String path, String nombre) {
		this.contents = new Figure();
		this.contents.setBackgroundColor(new Color(null, 255, 255, 255));

		try {
			ModeloParserXml modelo = new ModeloParserXml(path + nombre + "-comp.xml");
			RepresentacionParserXml representacion = new RepresentacionParserXml(path + nombre + "-rep.xml");

			this.diagrama = (DiagramaControl) modelo.diagramaPrincipal();
			representacion.cargarRepresentaciones(modelo);

			this.diagrama.dibujar(this.contents, diagrama.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.figureCanvas.setContents(this.contents);
	}

	/**
	 * Abrir la aplicación.
	 */
	public void mostrar() {
		this.shell.open();
	}
}
