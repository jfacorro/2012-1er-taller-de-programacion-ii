package mereditor.control;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public interface Control<T extends Componente> {

	public Figura<T> getFigura(String idDiagrama);
	
	public void dibujar(Figure contenedor, String idDiagrama);
}
