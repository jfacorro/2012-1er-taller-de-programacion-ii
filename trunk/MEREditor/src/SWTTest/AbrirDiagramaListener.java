package SWTTest;

import mereditor.modelo.base.ComponenteNombre;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;

public class AbrirDiagramaListener implements Listener {

	ArbolDeComponentes arbol;
	Menu observado;
	CTabFolder folder;
	
	public AbrirDiagramaListener(ArbolDeComponentes a, Menu m, CTabFolder f){
		arbol= a;
		observado= m;
		folder=f;
	}
	
	public void handleEvent(Event event) {
		arbol.setDiagramaActivo((TreeItem) observado.getData());
		CTabItem item= new CTabItem (folder, SWT.BOTTOM | SWT.CLOSE);
		item.setText(((ComponenteNombre)arbol.diagramaActivo.getData() ).getNombre());
	}

}
