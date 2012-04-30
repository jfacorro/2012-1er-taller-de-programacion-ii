package mereditor.representacion;

import java.util.LinkedList;
import java.util.List;

import mereditor.modelo.Diagrama;
import mereditor.representacion.base.ComponenteRepresentacion;

public class DiagramaRepresentacion extends ComponenteRepresentacion {
	protected Diagrama diagrama;
	
	protected List<DiagramaRepresentacion> diagramas;
	protected List<ComponenteRepresentacion> componentes;
	
	public DiagramaRepresentacion() {
		this.diagramas = new LinkedList<>();
		this.componentes = new LinkedList<>();
	}
	
	public List<ComponenteRepresentacion> getComponentes() {
		return this.componentes;
	}

	@Override
	public void dibujar(Object graphics) {
		// TODO Auto-generated method stub
		
	}
}
