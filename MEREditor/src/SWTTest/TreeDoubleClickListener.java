package SWTTest;

import mereditor.modelo.base.Componente;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeDoubleClickListener implements Listener {

	Tree observado;
	
	public TreeDoubleClickListener (Tree t) {
		observado= t;
	}
	
	public void handleEvent(Event event) {
		Point point = new Point (event.x, event.y);
		TreeItem item= observado.getItem(point);
		System.out.println(((Componente)item.getData()).getId()+" double clicked");
	}
	

}
