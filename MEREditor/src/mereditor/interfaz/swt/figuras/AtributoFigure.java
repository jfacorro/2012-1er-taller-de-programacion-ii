package mereditor.interfaz.swt.figuras;

import mereditor.interfaz.swt.listeners.DragDropControlador;
import mereditor.modelo.Atributo;
import mereditor.representacion.PList;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Color;

public class AtributoFigure extends Figura<Atributo> {

	private Ellipse ellipse;

	public AtributoFigure(Atributo componente) {
		super(componente);
	}

	@Override
	protected void init() {
		this.lblName = new Label();
		this.lblName.setFont(this.getFont());
		this.lblName.setText(this.componente.getNombre());
		this.lblName.setBounds(this.lblName.getTextBounds().translate(this.getLocation()));

		this.ellipse = new Ellipse();
		this.ellipse.setLocation(this.getLocation());

		// Si es un atributo simple representar con circulo
		if (this.componente.getAtributos().isEmpty()) {
			ellipse.setSize(new Dimension(10, 10));
			new DragDropControlador(this.lblName);
		} else {
			ellipse.setLayoutManager(new BorderLayout());
			ellipse.setSize(this.lblName.getTextBounds().getSize().getExpanded(20, 5));
			ellipse.add(this.lblName, BorderLayout.CENTER);
		}

		// Si es un identificador usar fondo negro
		if (this.componente.esIdentificador()) {
			ellipse.setBackgroundColor(new Color(null, 0, 0, 0));
			ellipse.setBorder(null);
		}

		this.setBounds(ellipse.getBounds());
		this.add(ellipse);
	}
	
	@Override
	protected void onSetParent() {
		super.onSetParent();
		if (this.getParent() != null && this.componente.getAtributos().isEmpty()) {
			this.getParent().add(this.lblName, 0);
			this.agregarFiguraLoqueada(this.lblName);
				
		}
	}

	public void conectarAtributo(Figura<Atributo> figura) {
		this.getParent().add(Figura.conectar(this, figura));
	}

	@Override
	public void setRepresentacion(PList repr) {
		super.setRepresentacion(repr);

		if (this.lblName != null && repr.get("Label") != null) {
			PList labelRepr = repr.<PList> get("Label");
			this.lblName.setLocation(new Point(labelRepr.<Integer> get("x"), labelRepr.<Integer> get("y")));
		}
	}

	@Override
	public PList getRepresentacion() {
		PList repr = super.getRepresentacion();
		if (this.lblName != null) {
			PList labelRepr = new PList();
			repr.set("Label", labelRepr);
			labelRepr.set("x", this.lblName.getLocation().x);
			labelRepr.set("y", this.lblName.getLocation().y);
		}

		return repr;
	}
	
	@Override
	public void actualizar() {
		this.lblName.setText(this.componente.getNombre());		
	}
}
