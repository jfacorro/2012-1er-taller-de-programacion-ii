package mereditor.control.base;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public interface Control<T extends Componente> {

	public Figura<T> getFigura();
	
	public void dibujar(Figure contenedor);
}
