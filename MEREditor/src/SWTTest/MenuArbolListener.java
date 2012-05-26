package SWTTest;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/*Activa Menu desplegable en el item donde recibió el evento*/

public class MenuArbolListener implements Listener{

	Tree observado;
	Menu menu;
	
	public MenuArbolListener(Tree t, Menu m){
		observado= t;
		menu= m;
	}
	
	public void handleEvent(Event event) {
		Point point = new Point (event.x, event.y);
		TreeItem item= observado.getItem(point);
		if (item != null){
			Rectangle area= item.getBounds();
			Point p= new Point (area.x, area.y + area.height);
			p= observado.toDisplay(p);
			menu.setLocation(p.x, p.y);
			menu.setVisible(true);
			menu.setData(item);
		}
	}

}
