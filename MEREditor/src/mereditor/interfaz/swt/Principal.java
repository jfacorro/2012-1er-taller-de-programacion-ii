package mereditor.interfaz.swt;

import mereditor.modelo.Entidad;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Principal implements MouseListener {
	public final String APP_NOMBRE = "MER Editor";
	
	private Point startPoint;
	private Point endPoint;

	private Shell shell;
	private LightweightSystem system;
	private Figure contents;
	private XYLayout contentsLayout;

	public static void main(String args[]) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Principal principal = new Principal(shell);
		principal.mostrar();
		
		principal.agregarEntidad(new Entidad("Boleteria"));
		principal.agregarEntidad(new Entidad("Fila"));

		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
	}

	public Principal(Shell shell) {
		this.shell = shell;
		this.shell.setSize(400, 400);
		this.shell.setText(APP_NOMBRE);

		this.system = new LightweightSystem(this.shell);
		this.contents = new Figure();		
		this.contentsLayout = new XYLayout();
		this.contents.setLayoutManager(contentsLayout);
		
		this.system.setContents(this.contents);
	}

	public void mostrar() {
		this.shell.open();
	}
	
	public void agregarEntidad(Entidad entidad)
	{
		EntidadFigure entidadFigure = new EntidadFigure(entidad);
		entidadFigure.addMouseListener(this);
		
		contentsLayout.setConstraint(entidadFigure, new Rectangle(10, 10, 100, 100));
		this.contents.add(entidadFigure);		
	}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.startPoint = new Point(e.x, e.y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.endPoint = new Point(e.x, e.y);

		if(!startPoint.equals(endPoint)) {
			int dx = this.endPoint.x - this.startPoint.x;
			int dy = this.endPoint.y - this.startPoint.y;
			
			Figure figure = (Figure)e.getSource();
			Point location = figure.getLocation();
			figure.setLocation(new Point(location.x + dx, location.y + dy));
		}
	}
}
