package mereditor.interfaz.swt;

import mereditor.modelo.Atributo;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class AtributoFigure extends Figura<Atributo> {

	public AtributoFigure(Atributo componente) {
		super(componente);

		this.resetDefaultSetting();

		Ellipse ellipse = new Ellipse();
		ellipse.setBounds(new Rectangle(this.getLocation(), new Dimension(10, 10)));
		this.setBounds(ellipse.getBounds());
		this.add(ellipse);
	}
	
	@Override
	protected void onSetParent() {
		super.onSetParent();
		// Agregar el label del atributo al contenedor del circulo
		this.lblName = new Label();
		this.lblName.setFont(this.getFont());
		this.lblName.setText(this.componente.getNombre());
		this.lblName.setBounds(this.lblName.getTextBounds().translate(this.getLocation()));
		// Agregar el label como figura loqueada
		this.agregarFiguraLoqueada(this.lblName);

		this.getParent().add(this.lblName, 0);
		
		new DragDropControlador(this.lblName);
	}
	
	public void conectarAtributo(Figura<Atributo> figura) {
		this.getParent().add(Figura.conectar(this, figura));
	}
}
