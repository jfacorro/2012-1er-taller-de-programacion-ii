package mereditor.representacion.base;

import mereditor.interfaz.swt.Figura;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public abstract class Representacion<T extends Componente> {
	
	protected int x, y;
	protected int ancho, alto; 
	
	protected Figura<T> figura;
	protected T componente;
	
	public abstract void dibujar(Figure contenedor);

	public T getComponente() {
		return this.componente;
	}
	
	public Figura<T> getFigura() {
		return this.figura;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}		
}
