package mereditor.interfaz.swt.figuras;

import mereditor.interfaz.swt.listeners.DragDropControlador;
import mereditor.modelo.Atributo;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class AtributoFigure extends Figura<Atributo> {

	private Ellipse ellipse = new Ellipse();

	public AtributoFigure(Atributo componente) {
		super(componente);

		this.resetDefaultSetting();

		if (this.componente.getAtributos().size() == 0) {
			ellipse.setBounds(new Rectangle(this.getLocation(), new Dimension(
					10, 10)));
		} else {
			Label label = this.getLabel();
			ellipse.setLayoutManager(new BorderLayout());
			ellipse.setSize(label.getTextBounds().getSize().getExpanded(20, 5));
			ellipse.add(label, BorderLayout.CENTER);
		}
		
		this.setBounds(ellipse.getBounds());
		this.add(ellipse);
	}

	@Override
	protected void onSetParent() {
		super.onSetParent();
		if (this.componente.getAtributos().size() == 0) {
			Label label = this.getLabel();
			// Agregar el label como figura loqueada
			this.agregarFiguraLoqueada(label);

			this.getParent().add(label, 0);
			new DragDropControlador(label);
		}
	}

	public void conectarAtributo(Figura<Atributo> figura) {
		this.getParent().add(Figura.conectar(this, figura));
	}
	
	private Label getLabel() {
		this.lblName = new Label();
		this.lblName.setFont(this.getFont());
		this.lblName.setText(this.componente.getNombre());
		this.lblName.setBounds(this.lblName.getTextBounds().translate(
				this.getLocation()));
		
		return this.lblName;
	}
}
