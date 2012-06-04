package mereditor.representacion;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.representacion.base.Control;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;

public class JerarquiaControl extends Jerarquia implements Control<Jerarquia> {

	@Override
	public Figura<Jerarquia> getFigura() {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor) {
		Figure generica = ((EntidadControl)this.generica).getFigura();
		
		for(Entidad derivada : this.derivadas)
		{
			Figure derivadaFigure = ((EntidadControl)derivada).getFigura();
			
			Connection connection = Figura.conectar(derivadaFigure, generica);

			contenedor.add(connection);
		}
	}
}
