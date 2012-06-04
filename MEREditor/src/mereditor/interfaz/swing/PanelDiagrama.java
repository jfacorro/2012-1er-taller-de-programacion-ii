package mereditor.interfaz.swing;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import mereditor.representacion.DiagramaControl;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class PanelDiagrama extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Principal principal;

	private DiagramaControl diagrama;
	
	private mxGraph graph;
	
	public PanelDiagrama(Principal principal) {
		super();
		
		this.principal = principal;
		
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());

		this.diagrama = this.principal.getDiagramaActual();
		
		if(diagrama != null) {
			this.diagrama.dibujar(null);
		}
		
		this.graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try
		{
			mxGeometry geometry = new mxGeometry(300, 30, 100, 20);
			geometry.setRelative(false);
			mxCell vertex = new mxCell("lalala", geometry, null);
			vertex.setId("lala");
			vertex.setVertex(true);
			vertex.setConnectable(false);
			
			graph.addCell(vertex);
			
			mxCell v1 = (mxCell)graph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			mxCell v2 = (mxCell)graph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);

			graph.insertEdge(parent, null, "Edge", v1, v2);
			
			mxCell edge = (mxCell)graph.insertEdge(parent, null, "Edge", v1, vertex);
			edge.setConnectable(false);
			edge.setStyle("");
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		this.add(graphComponent, BorderLayout.CENTER);
	}
}
