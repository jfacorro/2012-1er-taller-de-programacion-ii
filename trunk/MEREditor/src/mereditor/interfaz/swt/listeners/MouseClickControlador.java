package mereditor.interfaz.swt.listeners;

//import mereditor.editores.EntidadEditor;
import mereditor.interfaz.swt.figuras.Figura;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;

public class MouseClickControlador implements MouseListener {
	
	protected Figura<?> figura;
	private Point current;

	public MouseClickControlador(Figura<?> figura) {
		this.figura = figura;
		this.current = this.figura.getLocation();
		this.figura.addMouseListener(this);
	}
	
	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		System.out.println("doble click");
//		EntidadEditor editor = new EntidadEditor(this.figura.getParent(), 0);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
