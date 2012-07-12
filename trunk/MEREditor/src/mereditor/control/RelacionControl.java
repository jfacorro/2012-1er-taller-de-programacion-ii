package mereditor.control;

import java.util.HashMap;
import java.util.Map;

import mereditor.interfaz.swt.editores.EditorFactory;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.interfaz.swt.figuras.RelacionFigure;
import mereditor.modelo.Atributo;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Relacion;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

public class RelacionControl extends Relacion implements Control<Relacion>, MouseListener {
	protected Map<String, RelacionFigure> figures = new HashMap<>();

	@Override
	public Figura<Relacion> getFigura(String idDiagrama) {
		if (!this.figures.containsKey(idDiagrama)) {
			RelacionFigure figura = new RelacionFigure(this);
			this.figures.put(idDiagrama, figura);
		}

		this.figures.get(idDiagrama).actualizar();

		return this.figures.get(idDiagrama);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		RelacionFigure figRelacion = (RelacionFigure) this.getFigura(idDiagrama);
		contenedor.add(figRelacion);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoCtrl = (AtributoControl) atributo;
			atributoCtrl.dibujar(contenedor, idDiagrama);

			figRelacion.conectarAtributo(atributoCtrl.getFigura(idDiagrama));
			figRelacion.addFiguraLoqueada(atributoCtrl.getFigura(idDiagrama));
		}

		// Obtener el diagrama padre correspondiente
		Diagrama diagrama = (Diagrama) this.getPadre(idDiagrama);

		// Conectar las entidades participantes.
		for (EntidadRelacion entidadRelacion : this.getParticipantes()) {
			EntidadControl entidadCtrl = (EntidadControl) entidadRelacion.getEntidad();

			// Verificar que la entidad pertenece al diagrama
			if (diagrama.contiene(entidadCtrl)) {
				Figura<Entidad> figEntidad = entidadCtrl.getFigura(idDiagrama);
				figRelacion.conectarEntidad(figEntidad, entidadRelacion.toString());
			}
		}
	}

	@Override
	public String getNombreIcono() {
		return "relacion.png";
	}

	/**
	 * Devuelve las figuras para todos los diagramas
	 * 
	 * @return
	 */
	public Map<String, RelacionFigure> getFiguras() {
		return this.figures;
	}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		EditorFactory.getEditor(this).open();
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
