package mereditor.control;

import java.util.HashMap;
import java.util.Map;

import mereditor.editores.EntidadEditor;
import mereditor.interfaz.swt.Principal;
import mereditor.interfaz.swt.figuras.EntidadFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

public class EntidadControl extends Entidad implements Control<Entidad>, MouseListener {
	protected Map<String, EntidadFigure> figures = new HashMap<>();

	@Override
	public Figura<Entidad> getFigura(String idDiagrama) {
		if (!this.figures.containsKey(idDiagrama)) {
			EntidadFigure figura = new EntidadFigure(this);
			this.figures.put(idDiagrama, figura);
			// Agregar este controlador como listener para mouse clicks
			figura.addMouseListener(this);
		}
		
		this.figures.get(idDiagrama).actualizar();

		return this.figures.get(idDiagrama);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		EntidadFigure figure = (EntidadFigure) this.getFigura(idDiagrama);
		contenedor.add(figure);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			figure.conectarAtributo(atributoControl.getFigura(idDiagrama));
			atributoControl.dibujar(contenedor, idDiagrama);
			figure.agregarFiguraLoqueada(atributoControl.getFigura(idDiagrama));
		}
	}

	public Map<String, EntidadFigure> getFiguras() {
		return this.figures;
	}

	@Override
	public void mouseDoubleClicked(MouseEvent event) {
		EntidadEditor window = new EntidadEditor(Principal.getInstance(), this);
		window.setBlockOnOpen(true);
		window.open();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
