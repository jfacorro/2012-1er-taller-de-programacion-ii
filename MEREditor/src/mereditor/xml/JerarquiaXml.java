package mereditor.xml;

import mereditor.modelo.Entidad;
import mereditor.modelo.Jerarquia;
import mereditor.modelo.base.Componente;

import org.w3c.dom.Element;

public class JerarquiaXml extends Jerarquia implements Xmlizable {

	@Override
	public Element toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromXml(Element elemento, ParserXml parser) throws Exception {
		this.id = parser.obtenerId(elemento);
		this.tipo = TipoJerarquia.valueOf(parser.obtenerTipo(elemento));

		parser.registrar(this);

		this.generica = (Entidad) parser.obtenerGenerica(elemento);
		for (Componente componente : parser.obtenerDerivadas(elemento)) {
			this.derivadas.add((Entidad) componente);
		}
	}

}
