package mereditor.editores;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class AtributoProvider implements IContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		//TODO: se llama cuando se modifica un parámetro
	}

	/**
	* Retorna los objetos atributos
	*/
//	public Object[] getElements(Object inputElement) {
//	    return ((List) inputElement).toArray();
//	}

}
