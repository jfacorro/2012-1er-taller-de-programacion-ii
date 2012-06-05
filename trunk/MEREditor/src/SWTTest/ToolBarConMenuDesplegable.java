package SWTTest;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ToolBarConMenuDesplegable {
	ToolBar toolBar;

	public ToolBarConMenuDesplegable(Composite c, int modo) {
		toolBar = new ToolBar(c, modo);
	}

	public void agregarItem(String textoItem, Menu itemMenu) {
		ToolItem item = new ToolItem(toolBar, SWT.DROP_DOWN);
		item.setText(textoItem);
		item.addListener(SWT.Selection, new ToolBarListener(item, toolBar, itemMenu));
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

}
