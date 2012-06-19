package mereditor.interfaz.swt;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mereditor.control.Proyecto;
import mereditor.interfaz.swt.DialogBuilder.PromptResult;
import mereditor.xml.ParserXml;

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
 * Formulario principal de la aplicaci�n.
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
		this.tree = TreeManager.build(this);
		MenuArbolBuilder.build(this.shell,this.tree);
		this.initFigureCanvas();

		this.arregloLayout();
	}

	private void arregloLayout() {
		FormData formData = null;

		// Separacion vertical entre arbol y gr�fico.
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
		this.panelDisegno = new PanelDisegno(this.figureCanvas);
	}
	
	/**
	 * Crea un nuevo proyecto
	 * @throws Exception 
	 */
	public void nuevo() throws Exception {
		PromptResult resultado = DialogBuilder.prompt(this.shell, "Ingresar nombre", "Nombre");
		
		this.proyecto = new Proyecto(resultado.value);
		this.panelDisegno.setDiagrama(this.proyecto.getRaiz());
		TreeManager.cargar(this.proyecto, this.tree);
	}

	/**
	 * Abre un proyecto
	 */
	public void abrir() {
		FileDialog fileDialog = new FileDialog(this.shell);

		String path = fileDialog.open();

		if (path != null) {
			try {
				ParserXml modelo = new ParserXml(path);

				this.proyecto = modelo.parsear();
				this.panelDisegno.setDiagrama(this.proyecto.getRaiz());

				TreeManager.cargar(this.proyecto, this.tree);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Guarda un proyecto en el archivo especificado 
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
			this.guardarXml(modelo.generarXmlComponentes(), dir + this.proyecto.getComponentesPath());
			this.guardarXml(modelo.generarXmlRepresentacion(), dir + this.proyecto.getRepresentacionPath());
		}
	}
	
	/**
	 * Guarda un objecto Document en un archivo f�sico
	 * en el path especificado
	 * @param doc
	 * @param path
	 * @throws Exception
	 */
	private void guardarXml(Document doc, String path) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}

	/**
	 * Abrir la aplicaci�n.
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

	public Tree getTree() {
		return tree;
	}

	public PanelDisegno getPanelDisegno() {
		return this.panelDisegno;
	}
	
}
