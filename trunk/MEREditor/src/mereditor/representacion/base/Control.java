package mereditor.representacion.base;

import mereditor.interfaz.swt.Figura;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public abstract class Control<T extends Componente> {
	
	protected Figura<T> figura;
	protected T componente;

	public T getComponente() {
		return this.componente;
	}
	
	public Figura<T> getFigura() {
		return this.figura;
	}
	
	public abstract void dibujar(Figure contenedor);
}
