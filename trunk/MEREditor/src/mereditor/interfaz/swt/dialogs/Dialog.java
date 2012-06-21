package mereditor.interfaz.swt.dialogs;

import mereditor.interfaz.swt.Principal;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class Dialog extends ApplicationWindow {
	protected Principal principal = Principal.getInstance();
	protected String titulo;

	public Dialog() {
		super(Principal.getInstance().getShell());
	}

	/**
	 * Abre el dialogo indicando si se quiere una modal o no.
	 */
	public void abrir(boolean modal) {
		this.setBlockOnOpen(modal);
		this.open();
	}

	/**
	 * Abre el dialogo como ventana modal.
	 */
	public void abrir() {
		this.abrir(true);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		this.getShell().setText(this.titulo);

		Composite compositeButtons = new Composite(parent, SWT.NONE);
		compositeButtons.setLayout(new FillLayout());

		Button btnOK = new Button(compositeButtons, SWT.PUSH);
		btnOK.setText("Aceptar");
		btnOK.addSelectionListener(this.aceptar);

		Button btnCancel = new Button(compositeButtons, SWT.PUSH);
		btnCancel.setText("Cancelar");
		btnCancel.addSelectionListener(this.cancelar);

		return super.createContents(parent);
	}
	
	/**
	 * Acciones a realizar al presionar el boton aceptar
	 */
	protected abstract void aceptar();
	
	/**
	 * Acciones a realizar al presionar el boton cancelar
	 */
	protected void cancelar() {
		this.close();
	}
	
	private SelectionAdapter aceptar = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			aceptar();
		};
	};

	private SelectionAdapter cancelar = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			cancelar();
		};
	};
}
