package SWTTest;

import mereditor.modelo.Atributo;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.Entidad.TipoEntidad;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

public class Snippet2 {
		
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		FormLayout layout= new FormLayout();
		shell.setLayout(layout);
		crearMenu(shell);
		ToolBar bar = crearToolBar(shell);
		Tree tree= crearArbol(shell);
		FormData fdata= new FormData();
		fdata.bottom= new FormAttachment (10,0);
		bar.setLayoutData(fdata);
		fdata= new FormData();
		fdata.top= new FormAttachment (bar,0);
		fdata.bottom= new FormAttachment (0,shell.getClientArea().height);
		tree.setLayoutData(fdata);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static ToolBar crearToolBar(Shell shell) {
		ToolBarConMenuDesplegable toolBar = new ToolBarConMenuDesplegable(shell,SWT.NONE);
		/*Creo el menu para el item Entidad*/
		Menu imenu= new Menu (shell,SWT.POP_UP);
		MenuItem menu_item = new MenuItem (imenu,SWT.PUSH);
		menu_item.setText("Agregar Entidad Global");
		menu_item= new MenuItem (imenu,SWT.PUSH);
		menu_item.setText("Agregar Nueva Entidad");
		/*Creo el item Entidad con el menu anterior*/
		toolBar.agregarItem("Entidad", imenu);
		
		imenu= new Menu (shell, SWT.POP_UP);
		menu_item= new MenuItem (imenu, SWT.PUSH);
		menu_item.setText("Agregar Nueva Relacion");
		toolBar.agregarItem("Relacion", imenu);
		
		imenu= new Menu (shell, SWT.POP_UP);
		menu_item= new MenuItem (imenu, SWT.PUSH);
		menu_item.setText("Agregar Nueva Jerarquia");
		toolBar.agregarItem("Jerarquia", imenu);
		
		imenu= new Menu (shell, SWT.POP_UP);
		menu_item= new MenuItem (imenu, SWT.PUSH);
		menu_item.setText("Agregar Nuevo Diagrama");
		toolBar.agregarItem("Diagrama", imenu);
		
		ToolItem t_item= new ToolItem (toolBar.getToolBar(),SWT.PUSH);
		t_item.setText("Validar");
		return toolBar.getToolBar();
	}

	
	static Menu crearMenu(Shell shell) {
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("&File");
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		fileItem.setMenu (submenu);
		MenuItem item = new MenuItem (submenu, SWT.PUSH);
		item.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				System.out.println ("Select All");
			}
		});
		item.setText ("Select &All\tCtrl+A");
		item.setAccelerator (SWT.MOD1 + 'A');
		Rectangle clientArea = shell.getClientArea ();
		bar.setLocation (clientArea.x, clientArea.y);
		return bar;
	}
	
	private static Tree crearArbol (Shell shell){
		ComponenteArbol c = new ComponenteArbol ( new Diagrama ("DiagramaPpal", "id", "id"));
		Entidad e2 = new Entidad ("casa", "id2", "id", TipoEntidad.MAESTRA_COSA);
		ComponenteArbol c2= new ComponenteArbol ( e2);
		Atributo a = new Atributo("direccion","id3","id2","1","1",TipoAtributo.CARACTERIZACION,null) ;
		e2.agregarAtributo(a);
		ComponenteArbol c3= new ComponenteArbol (a );
		final Tree tree = new Tree (shell, SWT.BORDER | SWT.NO_SCROLL);
		tree.addListener(SWT.MouseDoubleClick, new TreeEventListener(tree));
		TreeItem t= c.agregarA(tree);
		TreeItem t2= c2.agregarA(t);
		c3.agregarA(t2);	
		return tree;
	}
	

	
}
