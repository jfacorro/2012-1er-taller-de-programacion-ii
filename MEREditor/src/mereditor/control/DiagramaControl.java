package mereditor.control;

import java.util.Collection;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Diagrama;

import org.eclipse.draw2d.Figure;

public class DiagramaControl extends Diagrama implements Control<Diagrama> {

	@Override
	public Figura<Diagrama> getFigura(String idDiagrama) {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		idDiagrama = idDiagrama != null ? idDiagrama : this.id;

		this.dibujar(contenedor, idDiagrama, this.getEntidades());
		this.dibujar(contenedor, idDiagrama, this.getRelaciones());
		this.dibujar(contenedor, idDiagrama, this.getJerarquias());
	}

	private void dibujar(Figure contenedor, String id, Collection<?> componentes) {
		for (Object componente : componentes)
			((Control<?>) componente).dibujar(contenedor, id);
	}

	public void dibujar(Figure contenedor) {
		this.dibujar(contenedor, this.id);
	}
}
