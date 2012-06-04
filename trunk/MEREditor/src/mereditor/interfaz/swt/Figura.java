package mereditor.interfaz.swt;

import java.util.ArrayList;
import java.util.List;

import mereditor.modelo.base.Componente;
import mereditor.representacion.base.Representacion;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
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
		this.init();
	}

	protected void init() {
		this.setFont(Figura.defaultFont);
		LayoutManager layout = new BorderLayout();
		this.setLayoutManager(layout);
		this.setBorder(new LineBorder(this.getLineColor(), 1));
		this.setBackgroundColor(this.getBackColor());
		this.setOpaque(true);
		this.setSize(Figura.defaultSize);

		this.lblName = new Label();
		this.lblName.setFont(defaultFont);
		this.add(lblName);
		layout.setConstraint(this.lblName, BorderLayout.CENTER);

		// Agregar controlador para arrastre
		new DragDropControlador(this);
		new MovimientoControlador(this);
	}

	protected Color getBackColor() {
		return Figura.defaultBackColor;
	}

	protected Color getLineColor() {
		return Figura.defaultLineColor;
	}
	
	protected void resetDefaultSetting() {
		this.removeAll();
		this.setBackgroundColor(null);
		this.setBorder(null);
		this.setOpaque(false);
	}

	/**
	 * Establece la posicion y dimension de la figura en base al objeto de
	 * representacion
	 * 
	 * @param representacion
	 */
	public void setRepresentacion(Representacion representacion) {
		if (representacion != null) {
			Rectangle rectangle = new Rectangle();
			rectangle.setX(representacion.getX());
			rectangle.setY(representacion.getY());
			rectangle.setWidth(representacion.getAncho());
			rectangle.setHeight(representacion.getAlto());

			this.setBounds(rectangle);
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
