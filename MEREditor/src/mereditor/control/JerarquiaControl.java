package mereditor.control;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class JerarquiaControl extends Jerarquia implements Control<Jerarquia> {

	@Override
	public Figura<Jerarquia> getFigura(String id) {
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
}
