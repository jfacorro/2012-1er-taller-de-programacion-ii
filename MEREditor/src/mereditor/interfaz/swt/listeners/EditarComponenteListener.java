package mereditor.interfaz.swt.listeners;

import mereditor.interfaz.swt.Principal;
import mereditor.interfaz.swt.TreeManager;
import mereditor.interfaz.swt.editores.EditorFactory;
import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

public class EditarComponenteListener implements Listener {
	private MenuArbolControlador menuArbolC;

	public EditarComponenteListener(MenuArbolControlador menuArbolC) {
		this.menuArbolC = menuArbolC;
	}

	@Override
	public void handleEvent(Event arg0) {
		TreeItem treeItemActivo = menuArbolC.getTreeItemActivo();
		Diagrama diagramaActivo = (Diagrama) TreeManager.getDiagramaActivo()
				.getData();
		Componente componenteAEditar = (Componente) treeItemActivo.getData();
		if (componenteAEditar instanceof Diagrama
				&& validarAbrirDiagrama(diagramaActivo, componenteAEditar)) {
			abrirDiagramaParaEdicion(componenteAEditar);
			return;
		}

		EditorFactory.getEditor(componenteAEditar).abrir();

	}

	private boolean validarAbrirDiagrama(Diagrama diagramaActivo,
			Componente componenteAEditar) {
		if (componenteAEditar.equals(diagramaActivo)) {
			MessageDialog.openInformation(Principal.getInstance().getShell(),
					"Diagrama actual", "El diagrama ya está en edición");
			return false;
		}
		return true;
	}

	private void abrirDiagramaParaEdicion(Componente componente) {
		TreeManager.setDiagramaActivo(menuArbolC.getTreeItemActivo());
		Principal.getInstance().abrirDiagrama(componente.getId());
	}

}
