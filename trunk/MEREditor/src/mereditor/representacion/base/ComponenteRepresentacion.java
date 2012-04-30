package mereditor.representacion.base;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxGraph;

public abstract class ComponenteRepresentacion {
	
	protected int x, y;
	protected int ancho, alto; 
	
	protected mxCell celda;
	
	protected String estilo;
	
	public void dibujar(Object graphics) {
		mxGraph graph = (mxGraph)graphics;
		
		mxGeometry geometry = new mxGeometry(this.getAncho(), this.getAlto(), this.getX(), this.getY());
		geometry.setRelative(true);
		
		this.celda = new mxCell(this.getNombre(), geometry, this.estilo);
		this.celda.setVertex(true);
		this.celda.setConnectable(false);
		
		graph.addCell(this.celda);
	}

	private Object getNombre() {
		return null;
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
