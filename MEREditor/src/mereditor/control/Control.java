package mereditor.control;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseListener;

public interface Control<T extends Componente> extends MouseListener {

	/**
	 * Figura asociada con este control correspondiente al diagrama del id
	 * especificado.
	 * 
	 * @param idDiagrama
	 * @return
	 */
	public Figura<T> getFigura(String idDiagrama);

	/**
	 * Agrega la figura de este control y las figuras de los hijos al
	 * contenedor.
	 * 
	 * @param contenedor
	 * @param idDiagrama
	 */
	public void dibujar(Figure contenedor, String idDiagrama);
}
