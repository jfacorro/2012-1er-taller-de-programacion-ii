package mereditor.representacion;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.Figure;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;
import mereditor.representacion.base.ComponenteRepresentacion;

public class DiagramaRepresentacion extends ComponenteRepresentacion<Diagrama> {
	protected Diagrama diagrama;
	
	protected List<DiagramaRepresentacion> diagramas;
	protected List<ComponenteRepresentacion<Componente>> componentes;
	//protected List<JerarquiaRepresentacion> jerarquias;
	
	public DiagramaRepresentacion() {
		this.diagramas = new LinkedList<DiagramaRepresentacion>();
		this.componentes = new LinkedList<ComponenteRepresentacion<Componente>>();
	}
	
	public List<ComponenteRepresentacion<Componente>> getComponentes() {
		return this.componentes;
	}

	@Override
	public void dibujar(Figure contenedor) {
		
		
	}
}
