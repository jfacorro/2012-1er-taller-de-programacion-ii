package mereditor.interfaz.swt.editores;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.Identificador;

public class IdentificadorTabla extends Tabla<Identificador> {
	private Entidad entidad;

	public IdentificadorTabla(Composite parent, Entidad entidad) {
		super(parent);
		this.entidad = entidad;
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.ATRIBUTOS);
		this.columnas.add(Editor.ENTIDADES);
	}

	@Override
	protected void initEditorsCeldas(Table table) {

	}

	@Override
	protected String getTextoColumna(Identificador element, int columnIndex) {
		String nombreColumna = this.columnas.get(columnIndex);
		return (String) this.getValorCelda(element, nombreColumna);
	}

	@Override
	protected Object getValorCelda(Identificador element, String property) {
		switch (property) {
		case Editor.ATRIBUTOS:
			return StringUtils.join(element.getAtributos().iterator(), ",");
		case Editor.ENTIDADES:
			return StringUtils.join(element.getEntidades().iterator(), ",");
		}

		return null;
	}

	@Override
	protected void setValorCelda(Identificador element, String property, Object value) {
		
	}

	@Override
	protected Identificador nuevoElemento() {
		return entidad.new Identificador(entidad);
	}

}
