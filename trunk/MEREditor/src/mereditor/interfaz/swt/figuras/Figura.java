package mereditor.interfaz.swt.figuras;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import mereditor.interfaz.swt.listeners.DragDropControlador;
import mereditor.interfaz.swt.listeners.MovimientoControlador;
import mereditor.modelo.base.Componente;
import mereditor.representacion.PList;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public abstract class Figura<T extends Componente> extends Figure {
	private static Color defaultBackColor = new Color(null, 255, 255, 206);
	private static Color defaultLineColor = new Color(null, 0, 0, 0);
	private static Dimension defaultSize = new Dimension(80, 50);
	private static Font defaultFont = new Font(null, "Helvetica", 10, SWT.NONE);

	protected Font font = defaultFont;
	protected Color lineColor = defaultLineColor;
	protected int lineStyle = Graphics.LINE_SOLID;
	protected int lineWidth = 1;
	protected Color backColor = defaultBackColor;
	protected T componente;
	protected Label lblName;
	/**
	 * Lista de figuras que se deben mover junto con esta
	 */
	private Set<Figure> figurasLoqueadas = new LinkedHashSet<>();

	/**
	 * Mapa de conexiones con otras figuras
	 */
	protected Map<String, Connection> conexiones = new HashMap<>();

	public Figura(T componente, Dimension dim) {
		this(componente);
		this.setSize(dim);
	}

	public Figura(T componente) {
		this.componente = componente;

		this.setFont(this.font);
		this.setLayoutManager(new BorderLayout());

		// Agregar controlador para arrastre
		new DragDropControlador(this);
		// Agregar controlador para el movivimento de las figuras loqueadas
		new MovimientoControlador(this);

		this.init();
	}

	/**
	 * Establece los estilos por default para la figura y agrega un label con el
	 * nombre del componente
	 */
	protected void init() {
		this.setBorder(this.getEstiloBorde());
		this.setBackgroundColor(this.backColor);

		this.setOpaque(true);
		this.setSize(Figura.defaultSize);

		this.lblName = new Label();
		this.lblName.setFont(this.font);
		this.add(lblName, BorderLayout.CENTER);
	}

	public T getComponente() {
		return this.componente;
	}

	public Connection getConexion(String id) {
		if (!this.conexiones.containsKey(id)) {
			String error = "La figura de id %s (%s) no tiene ninguna conexion con la figura del componente con id: %s";
			throw new RuntimeException(String.format(error, this.componente
					.getId(), this.componente.getClass().getName(), id));
		}

		return this.conexiones.get(id);
	}

	/**
	 * Actualizar la figura en base a los valores de su componente asociado.
	 */
	public abstract void actualizar();

	/**
	 * Establece la posicion y dimension de la figura en base al objeto de
	 * representacion
	 * 
	 * @param repr
	 */
	public void setRepresentacion(PList repr) {
		if (repr != null) {
			if (repr.<PList> get("Posicion") != null) {
				Rectangle rect = new Rectangle(repr.<PList> get("Posicion")
						.<Integer> get("x"), repr.<PList> get("Posicion")
						.<Integer> get("y"), repr.<PList> get("Dimension")
						.<Integer> get("ancho"), repr.<PList> get("Dimension")
						.<Integer> get("alto"));

				this.setBounds(rect);
			}

			if (repr.<PList> get("ColorFondo") != null) {
				this.backColor = new Color(null, repr.<PList> get("ColorFondo")
						.<Integer> get("r"), repr.<PList> get("ColorFondo")
						.<Integer> get("g"), repr.<PList> get("ColorFondo")
						.<Integer> get("b"));
			}

			if (repr.<PList> get("ColorLinea") != null) {
				this.lineColor = new Color(null, repr.<PList> get("ColorLinea")
						.<Integer> get("r"), repr.<PList> get("ColorLinea")
						.<Integer> get("g"), repr.<PList> get("ColorLinea")
						.<Integer> get("b"));
			}

			if (repr.<PList> get("AnchoLinea") != null) {
				this.lineWidth = repr.<Integer> get("AnchoLinea");
			}

			if (repr.<Integer> get("EstiloLinea") != null) {
				this.lineStyle = repr.<Integer> get("EstiloLinea");
			}
		}

		this.init();
	}

	public PList getRepresentacion() {
		PList repr = new PList();
		Rectangle rect = this.getBounds();
		repr.set("Posicion", new PList());
		repr.<PList> get("Posicion").set("x", rect.x);
		repr.<PList> get("Posicion").set("y", rect.y);
		repr.set("Dimension", new PList());
		repr.<PList> get("Dimension").set("ancho", rect.width);
		repr.<PList> get("Dimension").set("alto", rect.height);

		return repr;
	}

	/**
	 * Agrega la figura para que se mueva junto con esta.
	 * 
	 * @param figura
	 */
	public boolean agregarFiguraLoqueada(Figure figura) {
		return this.figurasLoqueadas.add(figura);
	}

	/**
	 * Obtener la lista de figuras loqueadas.
	 * 
	 * @return
	 */
	public Set<Figure> getFigurasLoqueadas() {
		return this.figurasLoqueadas;
	}

	@Override
	public void setParent(IFigure p) {
		super.setParent(p);
		this.onSetParent();
	}

	/**
	 * Ejecutado cuando se setea la figura padre
	 */
	protected void onSetParent() {
	}
	
	protected Border getEstiloBorde() {
		return new LineBorder(this.lineColor, this.lineWidth,
				this.lineStyle);
	}
	
	protected void aplicarEstiloBorde(Shape shape) {
		shape.setLineStyle(this.lineStyle);
		shape.setLineWidth(this.lineWidth);
		shape.setForegroundColor(this.lineColor);
	}

	/**
	 * Genera una conexion entre la figura de origen y destino
	 * 
	 * @param figOrigen
	 * @param figDestino
	 * @return Conexion entre las figuras
	 */
	public static Connection conectar(Figure figOrigen, Figure figDestino) {
		PolylineConnection conexion = new PolylineConnection();
		conexion.setAntialias(SWT.ON);

		ConnectionAnchor origen = new ChopboxAnchor(figOrigen);
		ConnectionAnchor destino = new ChopboxAnchor(figDestino);

		conexion.setSourceAnchor(origen);
		conexion.setTargetAnchor(destino);

		return conexion;
	}
}
