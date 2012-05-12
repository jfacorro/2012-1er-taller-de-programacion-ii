package SWTTest;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/* Este listener despliega el menu indicado para el item y la toolBar pasadas */
public class ToolBarListener implements Listener {

	ToolItem item;
	ToolBar toolBar;
	Menu menuDesplegable;
	
	public ToolBarListener (ToolItem itemBar, ToolBar toolbar, Menu menuDespl){
		item= itemBar;
		toolBar= toolbar;
		menuDesplegable= menuDespl;
	}
	
	public void handleEvent(Event event) {
		if (event.detail== SWT.ARROW){
			Rectangle area= item.getBounds();
			Point p= new Point (area.x, area.y + area.height);
			p= toolBar.toDisplay(p);
			menuDesplegable.setLocation(p.x, p.y);
			menuDesplegable.setVisible(true);
		}
	}

}
