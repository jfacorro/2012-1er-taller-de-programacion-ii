package mereditor.representacion;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.Figure;

public class DiagramaRepresentacion extends Control<Diagrama> {
	protected Diagrama diagrama;
	
	protected List<DiagramaRepresentacion> diagramas;
	protected List<Control<Componente>> componentes;
	
	public DiagramaRepresentacion() {
		this.diagramas = new LinkedList<DiagramaRepresentacion>();
		this.componentes = new LinkedList<Control<Componente>>();
	}
	
	public List<Control<Componente>> getComponentes() {
		return this.componentes;
	}

	@Override
	public void dibujar(Figure contenedor) {
		
		
	}
}
