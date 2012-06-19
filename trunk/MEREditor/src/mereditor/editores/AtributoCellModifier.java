package mereditor.editores;


import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Item;

public class AtributoCellModifier implements ICellModifier {

	private Viewer viewer;

	public AtributoCellModifier(Viewer viewer) {
		this.viewer = viewer;
	}

	public boolean canModify(Object element, String property) {
		//queda forzado q se puedan editar todas las propiedades
		return true;
	}

	public Object getValue(Object element, String property) {
		Atributo atr = (Atributo) element;

		if (EntidadEditor.NOMBRE.equals(property))
			return atr.getNombre();
		else if (EntidadEditor.TIPO.equals(property))
			return atr.getTipo().ordinal();
		else
			return null;
	}

	public void modify(Object element, String property, Object value) {
		if (element instanceof Item)
			element = ((Item) element).getData();

		Atributo atr = (Atributo) element;
		if (EntidadEditor.NOMBRE.equals(property))
			atr.setNombre((String) value);
		else if (EntidadEditor.TIPO.equals(property))
			atr.setTipo( TipoAtributo.class.getEnumConstants()[(int) value] );

		viewer.refresh();
	}
}
