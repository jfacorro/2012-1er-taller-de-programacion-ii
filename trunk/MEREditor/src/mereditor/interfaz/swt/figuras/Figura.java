package mereditor.interfaz.swt.figuras;

import java.util.ArrayList;
import java.util.List;

import mereditor.interfaz.swt.listeners.DragDropControlador;
import mereditor.interfaz.swt.listeners.MovimientoControlador;
import mereditor.modelo.base.Componente;
import mereditor.representacion.PList;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class Figura<T extends Componente> extends Figure {
	private static Color defaultBackColor = new Color(null, 255, 255, 206);
	private static Color defaultLineColor = new Color(null, 0, 0, 0);
	private static Dimension defaultSize = new Dimension(80, 50);
	private static Font defaultFont = new Font(null, "Helvetica", 10, SWT.NONE);

	protected T componente;
	protected Label lblName;
	/**
	 * Lista de figuras que se deben mover junto con esta
	 */
	private List<Figure> figurasLoqueadas = new ArrayList<>();

	public Figura(T componente, Dimension dim) {
		this(componente);
		this.setSize(dim);
	}

	public Figura(T componente) {
		this.componente = componente;

		this.setFont(defaultFont);
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
		this.setBorder(new LineBorder(this.getLineColor(), 1));
		this.setBackgroundColor(this.getBackColor());

		this.setOpaque(true);
		this.setSize(Figura.defaultSize);

		this.lblName = new Label();
		this.lblName.setFont(defaultFont);
		this.add(lblName, BorderLayout.CENTER);
	}

	protected Color getBackColor() {
		return Figura.defaultBackColor;
	}

	protected Color getLineColor() {
		return Figura.defaultLineColor;
	}

	/**
	 * Establece la posicion y dimension de la figura en base al objeto de
	 * representacion
	 * 
	 * @param repr
	 */
	public void setRepresentacion(PList repr) {
		this.init();

		if (repr != null) {
			Rectangle rect = new Rectangle(
				repr.<PList>get("Posicion").<Integer>get("x"),
				repr.<PList>get("Posicion").<Integer>get("y"),
				repr.<PList>get("Dimension").<Integer>get("ancho"),
				repr.<PList>get("Dimension").<Integer>get("alto"));

			if(repr.<PList>get("ColorFondo") != null) {
				this.setBackgroundColor(new Color(null, 
					repr.<PList>get("ColorFondo").<Integer>get("r"),
					repr.<PList>get("ColorFondo").<Integer>get("g"),
					repr.<PList>get("ColorFondo").<Integer>get("b"))
				);
			}
			
			if(repr.<PList>get("ColorLinea") != null) {
				Color lineColor = new Color(null, 
					repr.<PList>get("ColorLinea").<Integer>get("r"),
					repr.<PList>get("ColorLinea").<Integer>get("g"),
					repr.<PList>get("ColorLinea").<Integer>get("b"));

				this.setBorder(new LineBorder(lineColor));				
			}

			this.setBounds(rect);
		}
	}	

	/**
	 * Agrega la figura para que se mueva junto con esta.
	 * 
	 * @param figura
	 */
	public void agregarFiguraLoqueada(Figure figura) {
		this.figurasLoqueadas.add(figura);
	}

	/**
	 * Obtener la lista de figuras loqueadas.
	 * 
	 * @return
	 */
	public List<Figure> getFigurasLoqueadas() {
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

	/**
	 * Genera una conexión entre la figura de origen y destino
	 * 
	 * @param figOrigen
	 * @param figDestino
	 * @return Conexion entre las figuras
	 */
	public static Connection conectar(Figure figOrigen, Figure figDestino) {
		PolylineConnection conexion = new PolylineConnection();
		ChopboxAnchor origen = new ChopboxAnchor(figOrigen);
		ChopboxAnchor destino = new ChopboxAnchor(figDestino);
		conexion.setTargetAnchor(origen);
		conexion.setSourceAnchor(destino);
		return conexion;
	}
}
