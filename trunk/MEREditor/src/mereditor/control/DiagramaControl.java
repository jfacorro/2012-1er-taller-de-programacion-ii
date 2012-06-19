package mereditor.control;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

public class DiagramaControl extends Diagrama implements Control<Diagrama>, MouseListener {

	@Override
	public Figura<Diagrama> getFigura(String idDiagrama) {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		idDiagrama = idDiagrama != null? idDiagrama : this.id;

		for (Componente componente : this.componentes) {
			((Control<?>) componente).dibujar(contenedor, idDiagrama);
		}
	}
	
	public void dibujar(Figure contenedor) {
		this.dibujar(contenedor, this.id);
	}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
