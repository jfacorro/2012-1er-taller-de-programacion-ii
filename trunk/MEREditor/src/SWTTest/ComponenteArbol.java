package SWTTest;

import mereditor.modelo.base.ComponenteNombre;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class ComponenteArbol {
	
	ComponenteNombre componente;
	
	public ComponenteArbol (ComponenteNombre c){
		componente= c;
	}
	
	public TreeItem agregarA(Tree t) { 
		TreeItem n= new TreeItem(t,SWT.NONE);
		n.setText( componente.getNombre() );
		n.setData(componente);
		return n;
	}
	
	public TreeItem agregarA(TreeItem t) {
		TreeItem n= new TreeItem(t,SWT.NONE);
		n.setText( componente.getNombre() );
		n.setData(componente);
		return n;
	}


}
