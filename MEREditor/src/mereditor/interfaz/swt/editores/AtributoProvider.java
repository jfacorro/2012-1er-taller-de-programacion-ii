package mereditor.interfaz.swt.editores;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class AtributoProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		//TODO: se llama cuando se modifica un parámetro
	}

	public Object[] getElements(Object inputElement) {
	    return ((List<?>) inputElement).toArray();
	}

}
