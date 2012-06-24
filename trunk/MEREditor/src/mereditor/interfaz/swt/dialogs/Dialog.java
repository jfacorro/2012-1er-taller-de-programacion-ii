package mereditor.interfaz.swt.dialogs;

import mereditor.interfaz.swt.Principal;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class Dialog extends Window {
	protected Principal principal = Principal.getInstance();
	protected String titulo = "";
	
	/**
	 * @wbp.parser.constructor
	 */
	protected Dialog(Shell shell) {
		super(shell);
		setShellStyle(SWT.CLOSE);
	}

	public Dialog() {
		super(Principal.getInstance().getShell());
	}

	@Override
	protected Control createContents(Composite parent) {
		this.getShell().setText(this.titulo);
		
		Composite compositeButtons = new Composite(parent, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 2;
		gridLayout.marginWidth = 2;
		gridLayout.marginHeight = 2;
		gridLayout.marginBottom = 2;
		gridLayout.horizontalSpacing = 2;
		compositeButtons.setLayout(gridLayout);

		Button btnOK = new Button(compositeButtons, SWT.PUSH);
		btnOK.setText("Aceptar");
		btnOK.addSelectionListener(this.aceptar);

		Button btnCancel = new Button(compositeButtons, SWT.PUSH);
		btnCancel.setText("Cancelar");
		btnCancel.addSelectionListener(this.cancelar);
		
		this.getShell().pack();

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
