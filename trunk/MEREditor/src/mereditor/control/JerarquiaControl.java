package mereditor.control;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class JerarquiaControl extends Jerarquia implements Control<Jerarquia>, MouseListener {

	@Override
	public Figura<Jerarquia> getFigura(String idDiagrama) {
		throw new NotImplementedException();
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		Figure generica = ((EntidadControl)this.generica).getFigura(idDiagrama);
		
		for(Entidad derivada : this.derivadas)
		{
			Figure derivadaFigure = ((EntidadControl)derivada).getFigura(idDiagrama);
			
			Connection connection = Figura.conectar(derivadaFigure, generica);

			contenedor.add(connection);
		}
	}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
