package mereditor.editores;

import java.awt.Event;
import java.util.ArrayList;

import mereditor.interfaz.swt.Principal;
import mereditor.modelo.Atributo;
import mereditor.modelo.Entidad;

import org.eclipse.draw2d.GridData;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class EntidadEditor extends ApplicationWindow {

	private Shell shell;
	private Entidad entidad;
	private ArrayList atributos;
	
	//TODO: hace dinámico
	public static final String NOMBRE = "Nombre";
	public static final String TIPO = "Tipo";
	public static final String[] PROPS = { NOMBRE, TIPO};
	  
	public EntidadEditor(Principal principal, Entidad entidad) {
		super(principal.getShell());

		this.shell = principal.getShell();
		this.entidad = entidad;
		
		this.atributos = new ArrayList();
		
//		FillLayout layout = new FillLayout();
//		layout.marginHeight = layout.marginWidth = 4;
//		setLayout(layout);
//		
//	    white = new Color(null, 255, 255, 255);
//	    setBackground(white);	
				
	    //initTable();
	}

	
	@Override
	protected Control createContents(final Composite parent) {
		
		parent.setSize(400,250);
		
		GridLayout layout = new GridLayout(1, false);
		
		parent.setLayout(layout);
		
		Label label = new Label(parent, SWT.CENTER);
		label.setText( entidad.getNombre() );
		
		    
		Button btnAceptar = new Button(parent, SWT.PUSH);
		btnAceptar.setText("Aceptar");
		btnAceptar.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event)  {
					parent.dispose();  
			}
		});
			

				
		//Composite composite = new Composite(parent, SWT.NONE);
		//composite.setLayout(new GridLayout(1, false));

		Button btnNuevoAtributo = new Button(parent, SWT.PUSH);
		btnNuevoAtributo.setText("Crear un nuevo atributo");

		//TableViewer
		final TableViewer tv = new TableViewer(parent, SWT.FULL_SELECTION);
//		tv.setContentProvider(new AtributoProvider());
//		//tv.setLabelProvider(new AtributoLabelProvider());
//		tv.setInput(this.atributos);

//	    //Configuración de la tabla
//		Table table = tv.getTable();
//		table.setLayoutData(new GridData(GridData.FILL_BOTH));
//
//		new TableColumn(table, SWT.CENTER).setText(NOMBRE);
//		new TableColumn(table, SWT.CENTER).setText(TIPO);
//
//		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
//			table.getColumn(i).pack();
//		}
//
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);

		//TODO: agregar atributos existentes
		
//		//Agregar un nuevo atributo cuando se hace click sobre el botón
//		nuevoAtributoBtn.addSelectionListener(new SelectionAdapter() {
//		      public void widgetSelected(SelectionEvent event) {
//		        Atributo atr = new Atributo();
//		        atr.setNombre("nombre");
//		        //atr.setIdentificador(identificador)
//		        //TODO: completar el resto de los valores iniciales del atributo
//		        atributos.add(atr);
//		        tv.refresh();
//		      }
//		      //TODO: impactar sobre el modelo
//		    });

//		    // Create the cell editors
//		    CellEditor[] editors = new CellEditor[4];
//		    editors[0] = new TextCellEditor(table);
//		    editors[1] = new CheckboxCellEditor(table);
//		    editors[2] = new ComboBoxCellEditor(table, AgeRange.INSTANCES,
//		        SWT.READ_ONLY);
//		    editors[3] = new ColorCellEditor(table);
//
//		    // Set the editors, cell modifier, and column properties
//		    tv.setColumnProperties(PROPS);
//		    tv.setCellModifier(new PersonCellModifier(tv));
//		    tv.setCellEditors(editors);

		    //return composite;
		    return super.createContents(parent);
	}
	

}

