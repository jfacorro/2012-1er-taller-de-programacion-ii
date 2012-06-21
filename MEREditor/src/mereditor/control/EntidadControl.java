package mereditor.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.editores.EditorFactory;
import mereditor.interfaz.swt.figuras.EntidadFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

public class EntidadControl extends Entidad implements Control<Entidad>, MouseListener {
	protected Map<String, EntidadFigure> figures = new HashMap<>();

	@Override
	public Figura<Entidad> getFigura(String idDiagrama) {
		if (!this.figures.containsKey(idDiagrama)) {
			EntidadFigure figura = new EntidadFigure(this);
			this.figures.put(idDiagrama, figura);
			// Agregar este controlador como listener para mouse clicks
			figura.addMouseListener(this);
		}
		
		this.figures.get(idDiagrama).actualizar();

		return this.figures.get(idDiagrama);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		EntidadFigure figuraEntidad = (EntidadFigure) this.getFigura(idDiagrama);
		contenedor.add(figuraEntidad);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			figuraEntidad.conectarAtributo(atributoControl.getFigura(idDiagrama));
			atributoControl.dibujar(contenedor, idDiagrama);
			figuraEntidad.agregarFiguraLoqueada(atributoControl.getFigura(idDiagrama));
		}
		
		// Procesar identificadores internos de la entidad
		for (Identificador identificador : this.getIdentificadores()) {
			if (identificador.getEntidades().isEmpty()) {
				List<Connection> conexiones = new ArrayList<>();

				for (Atributo atributo : identificador.getAtributos())
					conexiones.add(figuraEntidad.getConexion(atributo.getId()));

				figuraEntidad.conectarIdentificador(conexiones);
			}
		}
	}

	public Map<String, EntidadFigure> getFiguras() {
		return this.figures;
	}

	@Override
	public void mouseDoubleClicked(MouseEvent event) {
		EditorFactory.getEditor(this).abrir();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
