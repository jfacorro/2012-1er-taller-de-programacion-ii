package SWTTest;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

public class AgregarDiagramaListener implements Listener {
	TreeItem diagramaPadre;

	public AgregarDiagramaListener(TreeItem d) {
		diagramaPadre= d;
	}

	
	public void handleEvent(Event arg0) {
		System.out.println("AgregarDiagrama");
		Diagrama d = new Diagrama("nuevo","idNuevo",( (Componente) diagramaPadre.getData()).getIdComponente());
		ComponenteArbol nuevoDiagrama= new ComponenteArbol (d);
		nuevoDiagrama.agregarA(diagramaPadre);
	}

}
