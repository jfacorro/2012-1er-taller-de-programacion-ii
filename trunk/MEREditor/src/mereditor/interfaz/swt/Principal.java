package mereditor.interfaz.swt;

import java.io.File;
import java.util.Observable;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mereditor.control.DiagramaControl;
import mereditor.control.Proyecto;
import mereditor.interfaz.swt.DialogBuilder.PromptResult;
import mereditor.interfaz.swt.DialogBuilder.Resultado;
import mereditor.modelo.Diagrama;
import mereditor.xml.ParserXml;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.w3c.dom.Document;

/**
 * Formulario principal de la aplicaci�n.
 * 
 */
public class Principal extends Observable {
	public static final Color defaultBackgroundColor = new Color(null, 255,
			255, 255);
	public static final String APP_NOMBRE = "MER Editor";

	private Shell shell;

	private SashForm sashForm;
	private ToolBar toolBar;
	private Tree tree;
	private FigureCanvas figureCanvas;

	private PanelDisegno panelDisegno;
	private Proyecto proyecto;

	private static Principal instancia;

	public static void main(String args[]) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);

		Principal.instancia = new Principal(shell);
		instancia.mostrar();

		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
	}

	public static Principal getInstance() {
		return Principal.instancia;
	}

	private Principal(Shell shell) {
		this.shell = shell;
		this.shell.setMaximized(true);
		this.shell.setText(APP_NOMBRE);
		this.shell.setLayout(new FormLayout());

		MenuBuilder.build(this);
		this.toolBar = ToolBarBuilder.build(this);
		this.sashForm = new SashForm(this.shell, SWT.HORIZONTAL);
		this.tree = TreeManager.build(this.sashForm);
		MenuArbolBuilder.build(this.shell, this.tree);
		this.initFigureCanvas();

		this.arregloLayout();
	}

	private void arregloLayout() {
		FormData formData = null;

		// Separacion vertical entre arbol y grafico.
		formData = new FormData();
		formData.top = new FormAttachment(this.toolBar);
		formData.bottom = new FormAttachment(100, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		this.sashForm.setLayoutData(formData);

		sashForm.setWeights(new int[] { 3, 16 });
	}

	private void initFigureCanvas() {
		this.figureCanvas = new FigureCanvas(this.sashForm);
		GC gc = new GC(figureCanvas);
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		this.figureCanvas.setBackground(Principal.defaultBackgroundColor);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);

	}

	/**
	 * Crea un nuevo proyecto.
	 * 
	 * @throws Exception
	 */
	public void nuevoProyecto() throws Exception {
		PromptResult resultado = DialogBuilder.prompt(this.shell,
				"Ingresar nombre", "Nombre");

		if (resultado.result == Resultado.OK) {
			this.proyecto = new Proyecto(resultado.value);
			this.cargarProyecto();
		}
	}

	/**
	 * Abre un proyecto.
	 */
	public void abrirProyecto() {
		FileDialog fileDialog = new FileDialog(this.shell);

		String path = fileDialog.open();

		if (path != null) {
			try {
				ParserXml modelo = new ParserXml(path);

				this.proyecto = modelo.parsear();
				this.cargarProyecto();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Carga el proyecto actual.
	 */
	private void cargarProyecto() {
		this.proyecto.setDiagramaActual(this.proyecto.getRaiz().getId());
		this.panelDisegno = new PanelDisegno(this.figureCanvas, this.proyecto);
		this.panelDisegno.actualizar();
		// Carga inicial del arbol.
		TreeManager.cargar(this.proyecto, this.tree);
		// Notificar a la toolbar que hay un proyecto abierto.
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Guarda un proyecto en el archivo especificado.
	 * 
	 * @throws Exception
	 */
	public void guardar() throws Exception {
		FileDialog fileDialog = new FileDialog(this.shell);
		String path = fileDialog.open();

		if (path != null) {
			File file = new File(path);
			String dir = file.getParent() + File.separator;
			this.proyecto.setPath(path);
			ParserXml modelo = new ParserXml(this.proyecto);
			this.guardarXml(modelo.generarXmlProyecto(), path);
			this.guardarXml(modelo.generarXmlComponentes(),
					dir + this.proyecto.getComponentesPath());
			this.guardarXml(modelo.generarXmlRepresentacion(), dir
					+ this.proyecto.getRepresentacionPath());
		}
	}

	/**
	 * Guarda un objecto Document en un archivo fisico en el path especificado.
	 * 
	 * @param doc
	 * @param path
	 * @throws Exception
	 */
	private void guardarXml(Document doc, String path) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}

	/**
	 * Agrega un Diagrama al proyecto.
	 * 
	 * @throws Exception
	 */
	public void nuevoDiagrama() throws Exception {
		PromptResult resultado = DialogBuilder.prompt(this.shell,
				"Ingresar nombre", "Nombre");
		if (resultado.result == Resultado.OK) {
			DiagramaControl nuevoDiagrama = new DiagramaControl();
			nuevoDiagrama.setNombre(resultado.value);

			this.proyecto.agregar(nuevoDiagrama);
			this.panelDisegno.actualizar();

			TreeManager.agregarADiagramaActual(nuevoDiagrama);
		}
	}

	/**
	 * Abrir la aplicacion.
	 */
	public void mostrar() {
		this.shell.open();
	}

	/**
	 * Actualiza la vista.
	 */
	public void actualizar() {
		this.panelDisegno.actualizar();
	}

	/**
	 * Cierra el programa.
	 */
	public void salir() {
		System.exit(0);
	}

	/**
	 * Abre el diagrama para su visualizacion y/o edicion
	 * 
	 * @param diagrama
	 **/
	public void abrir(Diagrama diagrama) {
		this.proyecto.setDiagramaActual(diagrama.getId());
		this.actualizar();
	}

	public Shell getShell() {
		return this.shell;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void error(String mensaje) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setText("Error");
		messageBox.setMessage(mensaje);
		messageBox.open();
	}
}
