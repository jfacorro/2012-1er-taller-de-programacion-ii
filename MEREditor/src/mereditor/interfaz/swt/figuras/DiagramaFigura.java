package mereditor.interfaz.swt.figuras;

import java.util.LinkedHashMap;
import java.util.Map;

import mereditor.control.DiagramaControl;
import mereditor.interfaz.swt.listeners.DragDropControlador;
import mereditor.interfaz.swt.listeners.SeleccionControlador;
import mereditor.modelo.Proyecto;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

/**
 * Figura sobre la que se dibuja el diagrama. Implementa lógica para realizar
 * zoom sobre las figuras que contiene.
 * 
 */
public class DiagramaFigura extends Figure {
	public final static Map<String, Float> zoomOptions = new LinkedHashMap<>();
	public final static String zoom100 = "100%";

	public static final float MIN_ZOOM = 0.25f;
	public static final float MAX_ZOOM = 2.0f;
	public static final float DELTA_ZOOM = 0.25f;

	private FigureCanvas canvas;
	private Proyecto proyecto;

	private float zoom = 1;

	/**
	 * Listener que deselecciona todas las figuras que están seleccionadas al
	 * hacer un click sobre el fondo.
	 */
	private MouseListener selection = new MouseListener.Stub() {
		public void mousePressed(MouseEvent me) {
			SeleccionControlador.deselectAll(null);
		};
	};

	static {
		initZoomOptions();
	}

	/**
	 * Inicializa el mapa que ofrece los diferentes valores de zoom disponibles.
	 */
	private static void initZoomOptions() {
		float zoom = 0;
		while (zoom < DiagramaFigura.MAX_ZOOM) {
			zoom += DiagramaFigura.DELTA_ZOOM;
			if (zoom >= DiagramaFigura.MIN_ZOOM) {
				String key = Integer.toString((int) (zoom * 100));
				zoomOptions.put(key + "%", zoom);
			}
		}
	}

	public DiagramaFigura(FigureCanvas canvas, Proyecto proyecto) {
		this.canvas = canvas;
		this.canvas.setContents(this);
		this.proyecto = proyecto;

		this.addMouseListener(this.selection);
		// Agregar el controlador de arrastre para que si el cursor se escapa de
		// la figura que se está arrastrando, no se deje de mover dado que toma
		// el control el listener de esta figura.
		this.addMouseMotionListener(new DragDropControlador(this));
	}

	/**
	 * Actualiza la vista con el diagrama actual.
	 */
	public void actualizar() {
		this.removeAll();
		DiagramaControl diagrama = (DiagramaControl) this.proyecto.getDiagramaActual();
		diagrama.dibujar(this);
	}

	/**
	 * Aplica una disminución al zoom.
	 */
	public void zoomOut() {
		if (this.zoom - DELTA_ZOOM >= MIN_ZOOM)
			this.setZoom(this.zoom - DELTA_ZOOM);
	}

	/**
	 * Aplica un aumento al zoom.
	 */
	public void zoomIn() {
		if (this.zoom + DELTA_ZOOM <= MAX_ZOOM)
			this.setZoom(this.zoom + DELTA_ZOOM);
	}

	public void zoom(String zoom) {
		if (zoomOptions.containsKey(zoom))
			this.setZoom(zoomOptions.get(zoom));
		else {
			float zoomFloat = Float.parseFloat(zoom.replace("%", ""));
			this.setZoom(zoomFloat);
		}
	}

	/**
	 * Establecer el nuevo zoom y volver a dibujar la figura.
	 * 
	 * @param zoom
	 */
	public void setZoom(float zoom) {
		this.zoom = zoom;
		revalidate();
		repaint();
	}

	/**
	 * Devuelve un thumbnail del diagrama actual.
	 * 
	 * @return
	 */
	public Image getImagen() {
		Rectangle rootFigureBounds = this.getBounds();
		GC figureCanvasGC = new GC(this.canvas);

		Image image = new Image(this.canvas.getDisplay(), rootFigureBounds.width,
				rootFigureBounds.height);
		GC imageGC = new GC(image);

		imageGC.setBackground(figureCanvasGC.getBackground());
		imageGC.setForeground(figureCanvasGC.getForeground());
		imageGC.setFont(figureCanvasGC.getFont());
		imageGC.setLineStyle(figureCanvasGC.getLineStyle());
		imageGC.setLineWidth(figureCanvasGC.getLineWidth());
		// imageGC.setXORMode(figureCanvasGC.getXORMode());

		Graphics imgGraphics = new SWTGraphics(imageGC);
		this.paint(imgGraphics);

		return image;
	}

	public String getZoom() {
		String zoom = Integer.toString((int) (this.zoom * 100));
		return zoom + "%";
	}

	/**
	 * @see org.eclipse.draw2d.Figure#translateToParent(Translatable)
	 */
	public void translateToParent(Translatable t) {
		t.performScale(zoom);
		super.translateToParent(t);
	}

	/**
	 * @see org.eclipse.draw2d.Figure#translateFromParent(Translatable)
	 */
	public void translateFromParent(Translatable t) {
		super.translateFromParent(t);
		t.performScale(1 / zoom);
	}

	/**
	 * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
	 */
	protected boolean useLocalCoordinates() {
		return true;
	}

	/**
	 * @see org.eclipse.draw2d.Figure#getClientArea()
	 */
	public Rectangle getClientArea(Rectangle rect) {
		super.getClientArea(rect);
		rect.width /= zoom;
		rect.height /= zoom;
		return rect;
	}

	/**
	 * @see org.eclipse.draw2d.Figure#paintClientArea(Graphics)
	 */
	protected void paintClientArea(Graphics graphics) {
		if (getChildren().isEmpty())
			return;

		boolean optimizeClip = getBorder() == null || getBorder().isOpaque();

		ScaledGraphics g = new ScaledGraphics(graphics);

		if (!optimizeClip)
			g.clipRect(getBounds().getShrinked(getInsets()));
		g.translate(getBounds().x + getInsets().left, getBounds().y + getInsets().top);
		g.scale(zoom);
		g.pushState();
		paintChildren(g);
		g.popState();
		g.dispose();
		graphics.restoreState();
	}
}
