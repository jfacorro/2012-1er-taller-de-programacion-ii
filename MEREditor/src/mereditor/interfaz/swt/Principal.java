package mereditor.interfaz.swt;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mereditor.control.Proyecto;
import mereditor.xml.ParserXml;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.w3c.dom.Document;

/**
 * Formulario principal de la aplicación.
 * 
 */
public class Principal {
	public static final Color defaultBackgroundColor = new Color(null, 255, 255, 255);
	public static final String APP_NOMBRE = "MER Editor";

	private Shell shell;

	private SashForm sashForm;
	private ToolBar toolBar;
	private Tree tree;
	private FigureCanvas figureCanvas;

	private Figure panelDiagrama;
	private Proyecto proyecto;

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

		MenuBuilder.build(this);
		this.toolBar = ToolBarBuilder.build(this);
		this.sashForm = new SashForm(this.shell, SWT.HORIZONTAL);
		this.tree = TreeManager.build(this);
		this.initFigureCanvas();

		this.arregloLayout();
	}

	private void arregloLayout() {
		FormData formData = null;

		// Separacion vertical entre arbol y gráfico.
		formData = new FormData();
		formData.top = new FormAttachment(this.toolBar); // Attach to top
		formData.bottom = new FormAttachment(100, 0); // Attach to bottom
		formData.left = new FormAttachment(0, 0); // Attach halfway acros
		formData.right = new FormAttachment(100, 0); // Attach halfway acros
		this.sashForm.setLayoutData(formData);

		sashForm.setWeights(new int[] { 3, 16 });
	}

	private void initFigureCanvas() {
		this.figureCanvas = new FigureCanvas(this.sashForm, SWT.V_SCROLL | SWT.H_SCROLL);
		this.figureCanvas.setBackground(Principal.defaultBackgroundColor);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);
	}

	/**
	 * Genera el contenido.
	 */
	public void abrir() {
		FileDialog fileDialog = new FileDialog(this.shell);

		String path = fileDialog.open();

		if (path != null) {
			this.panelDiagrama = new Figure();
			this.panelDiagrama.setBackgroundColor(new Color(null, 255, 255, 255));

			try {
				ParserXml modelo = new ParserXml(path);

				this.proyecto = modelo.parsear();
				this.proyecto.dibujar(this.panelDiagrama);

				TreeManager.agregar(this.proyecto.getRaiz(), this.tree);

			} catch (Exception e) {
				e.printStackTrace();
			}

			this.figureCanvas.setContents(this.panelDiagrama);
		}
	}

	public void guardar() throws Exception {
		FileDialog fileDialog = new FileDialog(this.shell);
		String path = fileDialog.open();

		if (path != null) {
			String dir = new File(path).getParent() + File.separator;
			this.proyecto.setPath(path);
			ParserXml modelo = new ParserXml(this.proyecto);
			this.guardarXml(modelo.generarXmlProyecto(), path);
			this.guardarXml(modelo.generarXmlComponentes(), dir + this.proyecto.getComponentesPath());
			this.guardarXml(modelo.generarXmlRepresentacion(), dir + this.proyecto.getRepresentacionPath());
		}
	}

	private void guardarXml(Document doc, String path) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
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

	public SashForm getSashForm() {
		return this.sashForm;
	}
}
