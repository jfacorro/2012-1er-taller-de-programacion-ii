package mereditor.interfaz.swt;

import mereditor.modelo.Entidad;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class EntidadFigure extends Figure {
	public static Color classColor = new Color(null, 255, 255, 206);
	
	private Entidad entidad;
	private Label lblName;
	
	public EntidadFigure(Entidad entidad) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setBackgroundColor(classColor);
		setOpaque(true);

		this.entidad = entidad;
		this.lblName = new Label(entidad.getNombre());
		this.add(lblName);
	}
}
