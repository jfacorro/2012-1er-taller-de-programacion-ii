package mereditor.representacion;

import mereditor.interfaz.swt.Figura;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.Figure;

public class DiagramaControl extends Diagrama implements Control<Diagrama> {

	@Override
	public Figura<Diagrama> getFigura() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dibujar(Figure contenedor) {
		for(Componente componente : this.componentes) {
			((Control<?>) componente).dibujar(contenedor); 
		}
	}
}
