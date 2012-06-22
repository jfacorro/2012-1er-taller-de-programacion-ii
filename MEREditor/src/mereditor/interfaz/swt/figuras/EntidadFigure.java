package mereditor.interfaz.swt.figuras;

import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

public class EntidadFigure extends Figura<Entidad> {

	public EntidadFigure(Entidad entidad) {
		super(entidad);
	}

	@Override
	protected void init() {
		super.init();

		this.update();
	}

	public void update() {
		this.lblName.setText(this.componente.getNombre());
	}

	public void conectarAtributo(Figura<Atributo> figura) {
		Connection conexion = Figura.conectar(this, figura);
		this.getParent().add(conexion);

		this.conexiones.put(figura.getComponente().getId(), conexion);
	}

	@Override
	public void actualizar() {
		this.lblName.setText(this.componente.getNombre());
	}

	/**
	 * Conecta los atributos y entidades que pertenecen a un mismo
	 * identificador.
	 * 
	 * @param conexiones
	 */
	public void conectarIdentificador(List<Connection> conexiones) {
		if (conexiones.size() > 1 && this.getParent() != null) {
			Connection conexionOrigen = conexiones.get(0);
			Ellipse circuloOrigen = this.circuloIdentificador();
			conexionOrigen.add(circuloOrigen, new MidpointLocator(conexionOrigen, 0));

			for (Connection conexion : conexiones) {
				if (conexion != conexionOrigen) {
					Ellipse circulo = this.circuloIdentificador();
					conexion.add(circulo, new MidpointLocator(conexion, 0));

					this.getParent().add(Figura.conectar(circuloOrigen, circulo));

					conexionOrigen = conexion;
					circuloOrigen = circulo;
				}
			}
		}
	}
	
	/**
	 * Devuelve una figura circulo que se utiliza para unir los identificadores. 
	 * @return
	 */
	private Ellipse circuloIdentificador() {
		Color negro = new Color(null, 0, 0, 0);
		Ellipse circulo = new Ellipse();
		circulo.setAntialias(SWT.ON);
		circulo.setSize(10, 10);
		circulo.setBackgroundColor(negro);
		return circulo;
	}
}