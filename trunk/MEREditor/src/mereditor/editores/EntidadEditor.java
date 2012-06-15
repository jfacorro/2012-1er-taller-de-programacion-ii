package mereditor.editores;

import mereditor.interfaz.swt.Principal;
import mereditor.modelo.Entidad;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class EntidadEditor extends ApplicationWindow {

	String text;
	Shell shell;
	Entidad entidad;
	 
	public EntidadEditor(Principal principal, Entidad entidad) {
		super(principal.getShell());

		this.shell = principal.getShell();
		this.entidad = entidad;
		
		this.text = entidad.getNombre();
		
//		FillLayout layout = new FillLayout();
//		layout.marginHeight = layout.marginWidth = 4;
//		setLayout(layout);
//		
//	    white = new Color(null, 255, 255, 255);
//	    setBackground(white);	
				
	    //initTable();
	}

	
	@Override
	protected Control createContents(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		
		parent.setLayout(layout);
		
		Button btnAceptar = new Button(parent, SWT.PUSH);
		btnAceptar.setText("Aceptar");
		
		return super.createContents(parent);		
	}
	

}

