package mereditor.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mereditor.interfaz.swt.Principal;
import mereditor.interfaz.swt.editores.EditorFactory;
import mereditor.interfaz.swt.figuras.EntidadFigure;
import mereditor.interfaz.swt.figuras.Figura;
import mereditor.interfaz.swt.figuras.RelacionFigure;
import mereditor.modelo.Atributo;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.Identificador;
import mereditor.modelo.Relacion;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

public class RelacionControl extends Relacion implements Control<Relacion>,
		MouseListener {
	protected Map<String, RelacionFigure> figures = new HashMap<>();

	@Override
	public Figura<Relacion> getFigura(String idDiagrama) {
		if (!this.figures.containsKey(idDiagrama)) {
			RelacionFigure figura = new RelacionFigure(this);
			this.figures.put(idDiagrama, figura);
			// Agregar este controlador como listener para mouse clicks
			figura.addMouseListener(this);
			figura.addFigureListener(Principal.getInstance());
		}

		this.figures.get(idDiagrama).actualizar();

		return this.figures.get(idDiagrama);
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		RelacionFigure figuraRelacion = (RelacionFigure) this
				.getFigura(idDiagrama);
		contenedor.add(figuraRelacion);

		for (Atributo atributo : this.atributos) {
			AtributoControl atributoControl = (AtributoControl) atributo;

			figuraRelacion.conectarAtributo(atributoControl
					.getFigura(idDiagrama));
			atributoControl.dibujar(contenedor, idDiagrama);
			figuraRelacion.agregarFiguraLoqueada(atributoControl
					.getFigura(idDiagrama));
		}

		// Obtener el diagrama padre correspondiente
		Diagrama padre = (Diagrama) this.getPadre(idDiagrama);

		/*
		 * Conectar las entidades participantes.
		 */
		for (EntidadRelacion entidadRelacion : this.participantes) {
			EntidadControl entidadCtrl = (EntidadControl) entidadRelacion
					.getEntidad();
			// Verificar que la entidad pertenece al diagrama
			if (padre.contiene(entidadCtrl)) {
				Figura<Entidad> figEntidad = entidadCtrl.getFigura(idDiagrama);

				figuraRelacion.conectarEntidad(figEntidad,
						entidadRelacion.toString());
			}
		}

		this.dibujarIdentificadoresExternosMixtos(idDiagrama, figuraRelacion);
	}

	private void dibujarIdentificadoresExternosMixtos(String idDiagrama,
			Figura<Relacion> figuraRelacion) {
		/*
		 * Procesar identificadores externos y mixtos de cada entidad.
		 */
		for (EntidadRelacion entidadRelacion : this.participantes) {
			EntidadControl entidadCtrl = (EntidadControl) entidadRelacion
					.getEntidad();
			EntidadFigure figEntidad = (EntidadFigure) entidadCtrl
					.getFigura(idDiagrama);

			for (Identificador identificador : entidadCtrl.getIdentificadores()) {
				// Solo los identificadores que pertencen a la relación y son
				// externos
				if (this.pertenece(identificador)
						&& !identificador.getEntidades().isEmpty()) {
					List<Connection> conexiones = new ArrayList<>();

					for (Entidad entidad : identificador.getEntidades())
						conexiones.add(figuraRelacion.getConexion(entidad
								.getId()));

					for (Atributo atributo : identificador.getAtributos())
						conexiones
								.add(figEntidad.getConexion(atributo.getId()));

					/*
					if (identificador.getAtributos().isEmpty())
						conexiones.add(figEntidad);
					 */

					figEntidad.conectarIdentificador(conexiones);
				}
			}
		}

	}

	@Override
	public String getNombreIcono() {
		return "relacion.png";
	}

	/**
	 * Indica si todas la entidades del identificador pertencen a la relación
	 * 
	 * @param identificador
	 * @return
	 */
	private boolean pertenece(Identificador identificador) {
		Set<Entidad> entidadesParticipantes = this.getEntidadesParticipantes();
		return entidadesParticipantes.containsAll(identificador.getEntidades());
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
