package SWTTest;

import mereditor.interfaz.swt.MenuArbolBuilder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;

public class Snippet2 {
	static ArbolDeComponentes arbolHandler;
	static CTabFolder carpetaDeDibujo;
	static CTabFolder tree;
	static ToolBar bar;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);

		FormLayout layout = new FormLayout();
		shell.setLayout(layout);

		Snippet2.crearMenu(shell);

		tree = crearArbol(shell);
		bar = crearToolBar(shell);
		
		FormData fdata = new FormData();
		fdata.top = new FormAttachment(bar);
		fdata.bottom = new FormAttachment(shell.getClientArea().height);
		tree.setLayoutData(fdata);

		crearCarpeta(shell);


		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}

	private static void crearCarpeta(Shell shell) {
		carpetaDeDibujo = new CTabFolder(shell, SWT.BORDER);
		carpetaDeDibujo.setSimple(false);

		FormData fdata = new FormData();
		fdata.left = new FormAttachment(tree);
		fdata.top = new FormAttachment(bar);
		fdata.right = new FormAttachment(shell.getClientArea().width);
		fdata.bottom = new FormAttachment(shell.getClientArea().height);
		carpetaDeDibujo.setLayoutData(fdata);
	}

	private static ToolBar crearToolBar(Shell shell) {
		ToolBarConMenuDesplegable toolBar = new ToolBarConMenuDesplegable(shell, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		/* Creo el menu para el item Entidad */
		Menu imenu = new Menu(shell, SWT.POP_UP);
		MenuItem menu_item = new MenuItem(imenu, SWT.PUSH);
		menu_item.setText("Agregar Entidad Global");
		menu_item = new MenuItem(imenu, SWT.PUSH);
		menu_item.setText("Agregar Nueva Entidad");
		/* Creo el item Entidad con el menu anterior */
		toolBar.agregarItem("Entidad", imenu);

		imenu = new Menu(shell, SWT.POP_UP);
		menu_item = new MenuItem(imenu, SWT.PUSH);
		menu_item.setText("Agregar Nueva Relacion");
		toolBar.agregarItem("Relacion", imenu);

		imenu = new Menu(shell, SWT.POP_UP);
		menu_item = new MenuItem(imenu, SWT.PUSH);
		menu_item.setText("Agregar Nueva Jerarquia");
		toolBar.agregarItem("Jerarquia", imenu);

		imenu = new Menu(shell, SWT.POP_UP);
		menu_item = new MenuItem(imenu, SWT.PUSH);
		menu_item.setText("Agregar Nuevo Diagrama");
		menu_item.addListener(SWT.Selection, new AgregarDiagramaListener(arbolHandler.diagramaActivo));
		toolBar.agregarItem("Diagrama", imenu);

		ToolItem t_item = new ToolItem(toolBar.getToolBar(), SWT.PUSH);
		t_item.setText("Validar");

		//FormData fdata2 = new FormData();
		//toolBar.getToolBar().setLayoutData(fdata2);
		
		return toolBar.getToolBar();
	}

	static Menu crearMenu(Shell shell) {
		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);
		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("&File");
		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(submenu);
		MenuItem item = new MenuItem(submenu, SWT.PUSH);
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				System.out.println("Select All");
			}
		});
		item.setText("Select &All\tCtrl+A");
		item.setAccelerator(SWT.MOD1 + 'A');
		Rectangle clientArea = shell.getClientArea();
		bar.setLocation(clientArea.x, clientArea.y);
		return bar;
	}

	private static CTabFolder crearArbol(Shell shell) {

		CTabFolder f = new CTabFolder(shell, SWT.NONE);
		f.setSimple(false);
		CTabItem i = new CTabItem(f, SWT.CLOSE | SWT.BOTTOM);
		i.setToolTipText("arbol de elementos");
		Composite composite = new Composite(f, SWT.NONE);
		Tree tree = new Tree(composite, SWT.NO_SCROLL);
		arbolHandler = new ArbolDeComponentes(tree);
		arbolHandler.cargar("");

		/* Formato composite */
		composite.setLayout(new FormLayout());
		i.setControl(composite);
		i.setText("Arbol");
		CoolBar barHeaderTree = new CoolBar(composite, SWT.NONE);
		FormData treeForm = new FormData();
		treeForm.top = new FormAttachment(barHeaderTree);
		treeForm.bottom = new FormAttachment(shell.getClientArea().height);
		tree.setLayoutData(treeForm);
		
		/*Menu del arbol*/
		MenuArbolBuilder.build(shell, tree);
		return f;
	}

}
