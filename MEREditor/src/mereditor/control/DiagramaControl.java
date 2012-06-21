package mereditor.control;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;

public class DiagramaControl extends Diagrama implements Control<Diagrama> {

	@Override
	public Figura<Diagrama> getFigura(String idDiagrama) {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		idDiagrama = idDiagrama != null ? idDiagrama : this.id;

		for (Componente componente : this.componentes)
			((Control<?>) componente).dibujar(contenedor, idDiagrama);
	}

	public void dibujar(Figure contenedor) {
		this.dibujar(contenedor, this.id);
	}
}
