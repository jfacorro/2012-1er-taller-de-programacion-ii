package mereditor.interfaz.swt;

import mereditor.control.DiagramaControl;
import mereditor.xml.ModeloParserXml;
import mereditor.xml.RepresentacionParserXml;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;

/**
 * Formulario principal de la aplicación.
 * 
 */
public class Principal {
	public static final Color defaultBackgroundColor = new Color(null, 255, 255, 255);
	public static final String APP_NOMBRE = "MER Editor";
	private static final String PATH_ARCHIVO = "xml/tests/";
	private static final String NOMBRE_ARCHIVO = "modelo";

	private Shell shell;

	private Menu menuBar;
	private ToolBar toolBar;
	private Tree tree;
	private FigureCanvas figureCanvas;

	private Figure panelDiagrama;
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
		this.shell.setLayout(new FormLayout());

		this.menuBar = MenuBuilder.build(this);
		this.toolBar = ToolBarBuilder.build(this);
		this.tree = TreeManager.build(this);
		this.initFigureCanvas();
		
		this.arregloLayout();

	}

	private void arregloLayout() {
		// Canvas
		FormData formData = new FormData();
		formData.top = new FormAttachment(this.toolBar);
		formData.bottom = new FormAttachment(100,0);
		formData.left = new FormAttachment(this.tree);
		formData.right = new FormAttachment(100,0);
		this.figureCanvas.setLayoutData(formData);
		
		formData = new FormData();
		formData.top = new FormAttachment(this.toolBar);
		formData.bottom = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(0, 100);
		this.tree.setLayoutData(formData);
	}

	private void initFigureCanvas() {
		this.figureCanvas = new FigureCanvas(this.shell, SWT.V_SCROLL | SWT.H_SCROLL);
		this.figureCanvas.setBackground(Principal.defaultBackgroundColor);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);
	}

	/**
	 * Genera el contenido.
	 */
	public void abrirProyecto() {
		String path = PATH_ARCHIVO, nombre = NOMBRE_ARCHIVO;

		this.panelDiagrama = new Figure();
		this.panelDiagrama.setBackgroundColor(new Color(null, 255, 255, 255));

		try {
			ModeloParserXml modelo = new ModeloParserXml(path + nombre + "-comp.xml");
			RepresentacionParserXml representacion = new RepresentacionParserXml(path + nombre + "-rep.xml");

			this.diagrama = (DiagramaControl) modelo.diagramaPrincipal();
			representacion.cargarRepresentaciones(modelo);

			this.diagrama.dibujar(this.panelDiagrama, diagrama.getId());
			
			TreeManager.agregar(this.diagrama, this.tree);

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.figureCanvas.setContents(this.panelDiagrama);
	}

	/**
	 * Abrir la aplicación.
	 */
	public void mostrar() {
		this.shell.open();
	}

	public Shell getShell() {
		return this.shell;
	}
}
