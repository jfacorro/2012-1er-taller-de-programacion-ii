package mereditor.interfaz.swt;

import java.io.File;
import java.util.Observable;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mereditor.control.DiagramaControl;
import mereditor.control.Proyecto;
import mereditor.control.ProyectoProxy;
import mereditor.interfaz.swt.DialogBuilder.PromptResult;
import mereditor.interfaz.swt.DialogBuilder.Resultado;
import mereditor.interfaz.swt.dialogs.AgregarEntidadDialog;
import mereditor.interfaz.swt.dialogs.AgregarJerarquiaDialog;
import mereditor.interfaz.swt.dialogs.AgregarRelacionDialog;
import mereditor.interfaz.swt.figuras.DiagramaFigura;
import mereditor.modelo.Validacion.EstadoValidacion;
import mereditor.xml.ParserXml;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PrintFigureOperation;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.w3c.dom.Document;

/**
 * Formulario principal de la aplicacion.
 * 
 */
public class Principal extends Observable implements FigureListener {
	public static final Color defaultBackgroundColor = new Color(null, 255, 255, 255);
	public static final String APP_NOMBRE = "MER Editor";
	private static final String TITULO_GUARDAR_DIAGRAMA_ACTUAL = "Información";
	private static final String MENSAJE_GUARDAR_DIAGRAMA_ACTUAL = "¿Desea guardar los cambios hechos al diagrama actual?";
	public static final String[] extensionProyecto = new String[] { "*.xml" };
	public static final String[] extensionesImagen = new String[] { "*.jpg" };
	private static final String PATH_IMAGENES = "src/recursos/imagenes/";
	private static final String PATH_ICONOS = "src/recursos/iconos/";

	private static Principal instancia;

	public static void main(String args[]) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);

		Principal.instancia = new Principal(shell);

		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
	}

	public static Principal getInstance() {
		return Principal.instancia;
	}

	public static Image getImagen(String nombre) {
		return new Image(Display.getDefault(), PATH_IMAGENES + nombre);
	}

	public static Image getIcono(String nombre) {
		return new Image(Display.getDefault(), PATH_ICONOS + nombre);
	}

	private Shell shell;

	private SashForm sashForm;
	private ToolBar toolBar;
	private FigureCanvas figureCanvas;
	private Label lblStatus;

	private DiagramaFigura panelDiagrama;
	private Proyecto proyecto;

	private Listener promptClose = new Listener() {
		@Override
		public void handleEvent(Event event) {
			int resultado = preguntarGuardar();
			event.doit = resultado != SWT.CANCEL;
		}
	};

	private Principal(Shell shell) {
		this.shell = shell;
		this.shell.setMaximized(true);
		this.shell.setText(APP_NOMBRE);
		this.shell.setLayout(new FormLayout());
		this.shell.addListener(SWT.Close, this.promptClose);

		MenuBuilder.build(this);
		this.toolBar = ToolBarBuilder.build(this);
		this.sashForm = new SashForm(this.shell, SWT.HORIZONTAL);
		TreeManager.build(this.sashForm);
		this.lblStatus = new Label(shell, SWT.BORDER);

		this.initFigureCanvas();

		this.arregloLayout();

		this.shell.open();
	}

	/**
	 * Establece el layout de los diferentes widgets en la ventana principal.
	 */
	private void arregloLayout() {
		FormData formData = null;

		// Separacion vertical entre arbol y grafico.
		formData = new FormData();
		formData.top = new FormAttachment(this.toolBar);
		formData.bottom = new FormAttachment(this.lblStatus);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		this.sashForm.setLayoutData(formData);
		this.mostrarArbol(false);

		formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
		this.lblStatus.setLayoutData(formData);
	}

	/**
	 * Inicializa el canvas donde se dibuja el diagrama.
	 */
	private void initFigureCanvas() {
		this.figureCanvas = new FigureCanvas(this.sashForm);
		this.figureCanvas.setBackground(Principal.defaultBackgroundColor);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);
	}

	/**
	 * Crea un nuevo proyecto.
	 * 
	 * @throws Exception
	 */
	public void nuevoProyecto() {
		PromptResult resultado = DialogBuilder.prompt(this.shell, "Ingresar nombre", "Nombre");

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
		fileDialog.setFilterExtensions(extensionProyecto);
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
		this.proyecto.setDiagramaActual(this.proyecto.getDiagramaRaiz().getId());
		this.panelDiagrama = new DiagramaFigura(this.figureCanvas, this.proyecto);
		this.panelDiagrama.actualizar();
		// Carga inicial del arbol.
		TreeManager.cargar(this.proyecto);
		this.mostrarArbol(true);
		// Notificar a la toolbar que hay un proyecto abierto.
		this.setChanged();
		this.notifyObservers();

		this.modificado(false);
	}

	private void actualizarEstado() {
		String status = "[Ningún proyecto abierto]";

		if (this.proyecto != null) {
			status = "Proyecto: %s [%s]- Diagrama: %s [%s]";
			status = String.format(status, this.proyecto.getNombre(), this.proyecto.getValidacion()
					.getEstado().toString(), this.proyecto.getDiagramaActual().getNombre(),
					this.proyecto.getDiagramaActual().getValidacion().getEstado().toString());
		}

		this.lblStatus.setText(status);
	}

	/**
	 * Actualiza el titulo según el estado del proyecto.
	 */
	private void actualizarTitulo() {
		String titulo = APP_NOMBRE;

		if (this.proyecto != null) {
			titulo += " - " + this.proyecto.getNombre();
			titulo += this.shell.getModified() ? " *" : "";
			titulo += " [" + this.proyecto.getPath() + "]";
		}

		this.shell.setText(titulo);
	}

	/**
	 * Guarda un proyecto en el path que ya tiene asignado o muestra el dialogo
	 * para elegir el archivo.
	 * 
	 * @throws Exception
	 */
	public void guardarProyecto() {
		this.guardarProyecto(false);
	}

	/**
	 * Guarda un proyecto en el path indicado.
	 * 
	 * @param showDialog
	 *            indica si se debe mostrar el dialogo de seleccion de archivo.
	 * @throws Exception
	 */
	public void guardarProyecto(boolean showDialog) {
		String path = this.proyecto.getPath();

		if (path == null || showDialog) {
			FileDialog fileDialog = new FileDialog(this.shell, SWT.SAVE);
			fileDialog.setFilterExtensions(extensionProyecto);
			path = fileDialog.open();
		}

		if (path != null) {
			File file = new File(path);
			String dir = file.getParent() + File.separator;
			this.proyecto.setPath(path);
			ParserXml modelo;
			try {
				modelo = new ParserXml(this.proyecto);
				this.guardarXml(modelo.generarXmlProyecto(), path);
				this.guardarXml(modelo.generarXmlComponentes(),
						dir + this.proyecto.getComponentesPath());
				this.guardarXml(modelo.generarXmlRepresentacion(),
						dir + this.proyecto.getRepresentacionPath());
			} catch (Exception e) {
				this.error("Ocurrió un error al guardar el proyecto.");
				e.printStackTrace();
			}

			this.modificado(false);
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
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}

	/**
	 * Agrega un Diagrama al proyecto.
	 * 
	 * @throws Exception
	 */
	public void nuevoDiagrama() {
		PromptResult resultado = DialogBuilder.prompt(this.shell, "Ingresar nombre", "Nombre");
		if (resultado.result == Resultado.OK) {
			DiagramaControl nuevoDiagrama = new DiagramaControl();
			nuevoDiagrama.setNombre(resultado.value);

			this.proyecto.agregar(nuevoDiagrama);
			this.actualizarVista();

			TreeManager.agregarADiagramaActual(nuevoDiagrama);

			this.modificado(true);
		}
	}

	/**
	 * Actualiza la vista.
	 */
	public void actualizarVista() {
		this.panelDiagrama.actualizar();
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
	public void abrirDiagrama(String id) {
		String idActual = this.proyecto.getDiagramaActual().getId();
		if (!id.equals(idActual)) {
			int resultado = this.preguntarGuardar();

			if (resultado != SWT.CANCEL) {
				this.proyecto.setDiagramaActual(id);
				this.actualizarVista();
				this.actualizarEstado();
			}
		}
	}

	/**
	 * Pregunta al usuario si se quiere guardar el diagrama. Si se ingresa un
	 * si, se realiza el guardado del diagrama y se devuelve el resultado del
	 * dialogo.
	 * 
	 * @return resultado: SWT.YES | SWT.NO | SWT.CANCEL
	 */
	private int preguntarGuardar() {
		int result = SWT.NO;

		if (shell.getModified()) {
			int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO | SWT.CANCEL;
			MessageBox messageBox = new MessageBox(shell, style);
			messageBox.setText(TITULO_GUARDAR_DIAGRAMA_ACTUAL);
			messageBox.setMessage(MENSAJE_GUARDAR_DIAGRAMA_ACTUAL);

			result = messageBox.open();

			if (result == SWT.YES)
				guardarProyecto();
		}

		return result;
	}

	/**
	 * Abre el diálogo para agregar una Entidad al diagrama que se encuentra
	 * abierto.
	 */
	public void agregarEntidad() {
		AgregarEntidadDialog dialog = new AgregarEntidadDialog();
		if (dialog.open() == Window.OK) {
			this.proyecto.agregar(dialog.getComponente());
			this.actualizarVista();
			TreeManager.agregarADiagramaActual(dialog.getComponente());
			this.modificado(true);
		}
	}

	/**
	 * Abre el diálogo para agregar una Relacion al diagrama que se encuentra
	 * abierto.
	 */
	public void agregarRelacion() {
		AgregarRelacionDialog dialog = new AgregarRelacionDialog();
		if (dialog.open() == Window.OK) {
			this.proyecto.agregar(dialog.getComponente());
			this.actualizarVista();
			TreeManager.agregarADiagramaActual(dialog.getComponente());
			this.modificado(true);
		}
	}

	/**
	 * Abre el diálogo para agregar una Jerarquia al diagrama que se encuentra
	 * abierto.
	 */
	public void agregarJerarquia() {
		AgregarJerarquiaDialog dialog = new AgregarJerarquiaDialog();
		if (dialog.open() == Window.OK) {
			this.proyecto.agregar(dialog.getComponente());
			this.actualizarVista();
			TreeManager.agregarADiagramaActual(dialog.getComponente());
			this.modificado(true);
		}
	}

	/**
	 * Disminucion del zoom.
	 */
	public void zoomOut(Combo cboZoom) {
		this.panelDiagrama.zoomOut();
		cboZoom.setText(this.panelDiagrama.getZoom());
	}

	/**
	 * Aumento del zoom.
	 */
	public void zoomIn(Combo cboZoom) {
		this.panelDiagrama.zoomIn();
		cboZoom.setText(this.panelDiagrama.getZoom());
	}

	public void zoom(String zoom) {
		this.panelDiagrama.zoom(zoom);
	}

	/**
	 * Exportar el diagrama a un archivo de imagen.
	 */
	public void exportar() {
		FileDialog fileDialog = new FileDialog(this.shell, SWT.SAVE);
		fileDialog.setFilterExtensions(extensionesImagen);
		fileDialog.setFileName(this.proyecto.getDiagramaActual().getNombre() + ".jpg");
		String path = fileDialog.open();

		if (path != null) {
			Image image = this.panelDiagrama.getImagen();

			ImageData[] data = new ImageData[1];
			data[0] = image.getImageData();

			ImageLoader imgLoader = new ImageLoader();
			imgLoader.data = data;
			imgLoader.save(path, SWT.IMAGE_JPEG);
		}
	}

	/**
	 * Muestra la pantalla de impresión para el digrama actual.
	 */
	public void imprimir() {
		PrintDialog printDialog = new PrintDialog(this.shell);
		PrinterData printerData = printDialog.open();

		if (printerData != null) {
			Printer printer = new Printer(printerData);

			PrintFigureOperation printerOperation = new PrintFigureOperation(printer,
					this.panelDiagrama);
			printerOperation.setPrintMode(PrintFigureOperation.FIT_PAGE);
			printerOperation.setPrintMargin(new Insets(0, 0, 0, 0));
			printerOperation.run(this.proyecto.getDiagramaActual().getNombre());

			printer.dispose();
		}
	}

	/**
	 * Validar diagrama actual
	 */
	public void validar() {
		this.advertencia(this.proyecto.getDiagramaActual().validar());
		this.actualizarEstado();
	}

	/**
	 * Validar diagrama actual
	 */
	public void validarProyecto() {
		this.advertencia(this.proyecto.validar());
		this.actualizarEstado();
	}

	@Override
	public void figureMoved(IFigure source) {
		this.modificado(true);
	}

	/**
	 * Devuelve el shel de la ventana principal.
	 * 
	 * @return
	 */
	public Shell getShell() {
		return this.shell;
	}

	/**
	 * Devuelve un proxy del proyecto que se encuentra abierto exponiendo solo
	 * los métodos seleccionados.
	 * 
	 * @return
	 */
	public ProyectoProxy getProyecto() {
		return this.proyecto;
	}

	/**
	 * Muestra una ventana de error con el mensaje especificado.
	 * 
	 * @param mensaje
	 */
	public void error(String mensaje) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setText("Error");
		messageBox.setMessage(mensaje);
		messageBox.open();
	}

	/**
	 * Muestra una ventana de advertencia con el mensaje especificado.
	 * 
	 * @param mensaje
	 */
	public void advertencia(String mensaje) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		messageBox.setText("Advertencia");
		messageBox.setMessage(mensaje);
		messageBox.open();
	}

	/**
	 * Define si el proyecto fue modificado y actualiza el titulo de la ventana
	 * principal.
	 * 
	 * @param modificado
	 *            Define el estado del diagrama actual. <code>true</code> si el
	 *            diagrama tiene alguna modificación pendiente de ser guardada.
	 *            <code>false</code> si no tiene ninguna.
	 */
	private void modificado(boolean modificado) {
		if (modificado != this.shell.getModified()) {
			this.shell.setModified(modificado);
			this.actualizarTitulo();
		}
		
		if (modificado && this.proyecto != null) {
			this.proyecto.getValidacion().setEstado(EstadoValidacion.SIN_VALIDAR);
			this.proyecto.getDiagramaActual().getValidacion()
					.setEstado(EstadoValidacion.SIN_VALIDAR);
			
			this.actualizarEstado();
		}
	}

	/**
	 * Muestra o esconde el arbol de jerarquías según el valor del parámetros
	 * 
	 * @param mostrar
	 *            indica si se debe mostrar el árbol.
	 */
	public void mostrarArbol(boolean mostrar) {
		int peso = mostrar ? 3 : 0;

		this.sashForm.setWeights(new int[] { peso, 16 });
	}
}
