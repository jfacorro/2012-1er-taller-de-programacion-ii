package SWTTest;

/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

/* 
 * example snippet: Hello World
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class Snippet1 {
	
	
	static void crearMenu(Shell shell) {
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

	}
	
	private static void crearArbol (Shell shell){
		
		final Tree tree = new Tree (shell, SWT.BORDER);
		for (int i=0; i<4; i++) {
			TreeItem iItem = new TreeItem (tree, 0);
			iItem.setText ("TreeItem (0) -" + i);
			for (int j=0; j<4; j++) {
				TreeItem jItem = new TreeItem (iItem, 0);
				jItem.setText ("TreeItem (1) -" + j);
				for (int k=0; k<4; k++) {
					TreeItem kItem = new TreeItem (jItem, 0);
					kItem.setText ("TreeItem (2) -" + k);
					for (int l=0; l<4; l++) {
						TreeItem lItem = new TreeItem (kItem, 0);
						lItem.setText ("TreeItem (3) -" + l);
					}
				}
			}
		}
		
	}
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		FillLayout layout= new FillLayout();
		layout.type=SWT.HORIZONTAL;
		shell.setLayout(layout);
		crearMenu(shell);
		crearArbol(shell);
		crearAreaDeEdicion(shell,display);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static void crearAreaDeEdicion(Shell shell,Display display) {
		Rectangle clientArea = shell.getClientArea ();
		Image image = new Image (display, clientArea.height, clientArea.width);
		Color color = display.getSystemColor (SWT.COLOR_DARK_BLUE);
		GC gc = new GC (image);
		gc.setBackground (color);
		gc.fillRectangle (image.getBounds ());
		gc.dispose ();
		Label label = new Label (shell, SWT.BORDER);
		label.setLocation (clientArea.x, clientArea.y);
		label.setImage (image);
		label.pack ();
		
	}

	
}