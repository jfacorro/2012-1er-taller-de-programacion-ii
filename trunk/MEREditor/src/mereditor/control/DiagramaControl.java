package mereditor.control;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public class DiagramaControl extends Diagrama implements Control<Diagrama> {

	@Override
	public Figura<Diagrama> getFigura(String id) {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		for (Componente componente : this.componentes) {
			((Control<?>) componente).dibujar(contenedor, idDiagrama);
		}
	}
}
