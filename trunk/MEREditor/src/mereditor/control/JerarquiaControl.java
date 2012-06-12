package mereditor.control;

import java.util.HashMap;
import java.util.Map;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.interfaz.swt.figuras.JerarquiaFigura;
import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

public class JerarquiaControl extends Jerarquia implements Control<Jerarquia>, MouseListener {
	protected Map<String, JerarquiaFigura> figures = new HashMap<>();

	@Override
	public Figura<Jerarquia> getFigura(String idDiagrama) {
		if (!this.figures.containsKey(idDiagrama)) {
			JerarquiaFigura figura = new JerarquiaFigura(this);
			this.figures.put(idDiagrama, figura);
			// Agregar este controlador como listener para mouse clicks
			figura.addMouseListener(this);
		}

		return this.figures.get(idDiagrama);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		JerarquiaFigura figura = (JerarquiaFigura) this.getFigura(idDiagrama);
		contenedor.add(figura);

		Figure generica = ((Control<?>) this.generica).getFigura(idDiagrama);
		figura.conectarGenerica(figura, generica);

		for (Entidad derivada : this.derivadas) {
			Figure derivadaFigure = ((Control<?>) derivada).getFigura(idDiagrama);
			figura.conectarDerivada(figura, derivadaFigure);
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
