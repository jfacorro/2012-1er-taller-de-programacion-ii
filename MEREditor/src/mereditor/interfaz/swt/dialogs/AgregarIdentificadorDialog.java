package mereditor.interfaz.swt.dialogs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mereditor.modelo.Atributo;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Entidad.Identificador;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class AgregarIdentificadorDialog extends Dialog {

	private Map<String, Entidad> entidades = new HashMap<>();
	private Map<String, Atributo> atributos = new HashMap<>();
	private List listEntidades;
	private List listAtributos;
	int[] atributosSeleccionados, entidadesSelecionadas;
	
	/**
	 * @wbp.parser.constructor
	 */
	protected AgregarIdentificadorDialog(Shell shell) {
		super(shell);
	}

	public AgregarIdentificadorDialog(Entidad entidad) {
		super();
		this.titulo = "Agregar Identificador";

		for (Atributo atributo : entidad.getAtributos()) {
			this.atributos.put(atributo.getNombre(), atributo);
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		Label lblEntidades = new Label(dialogArea, SWT.LEFT);
		lblEntidades.setText("Identificadores");

		Button btnNueva = new Button(dialogArea, SWT.PUSH);
		btnNueva.setText("Nuevo ID");

		btnNueva.addSelectionListener(this.nueva);
		
		CLabel lblpresionarCtrlPara = new CLabel(dialogArea, SWT.NONE);
		lblpresionarCtrlPara.setText("Presionar Ctrl para selecionar más de un Atributo o Entidad.");

		Composite header = new Composite(dialogArea, SWT.NONE);
		header.setLayout(new RowLayout(SWT.HORIZONTAL));
		header.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

		Group group_2 = new Group(header, SWT.NONE);
		group_2.setLayoutData(new RowData(414, SWT.DEFAULT));

		CLabel lblEntidades_1 = new CLabel(group_2, SWT.NONE);
		lblEntidades_1.setBounds(304, 10, 71, 23);
		lblEntidades_1.setText("Entidades");

		CLabel lblAtributos = new CLabel(group_2, SWT.NONE);
		lblAtributos.setBounds(59, 10, 71, 23);
		lblAtributos.setText("Atributos");

		Group group_1 = new Group(dialogArea, SWT.NONE);

		this.listAtributos = new List(group_1, SWT.BORDER | SWT.MULTI);
		listAtributos.setBounds(0, 0, 185, 98);

		this.listEntidades = new List(group_1, SWT.BORDER | SWT.MULTI);
		listEntidades.setBounds(245, 0, 174, 101);

		this.loadEntidades();
		this.loadAtributos();
		
		SelectionListener selectedAtributoListener = new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				atributosSeleccionados = listAtributos.getSelectionIndices();
				String outString = "";
				for (int loopIndex = 0; loopIndex < atributosSeleccionados.length; loopIndex++)
					outString += atributosSeleccionados[loopIndex] + " ";
				System.out.println("listAtributos Items: " + outString);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		};
		
		SelectionListener selectedEntidadListener = new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				entidadesSelecionadas = listEntidades.getSelectionIndices();
				String outString = "";
				for (int loopIndex = 0; loopIndex < entidadesSelecionadas.length; loopIndex++)
					outString += entidadesSelecionadas[loopIndex] + " ";
				System.out.println("listEntidades Items: " + outString);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		};
		
		this.listAtributos.addSelectionListener(selectedAtributoListener);
		this.listEntidades.addSelectionListener(selectedEntidadListener);

		return header;
	}

	/**
	 * Carga las entidades que pertenecen al diagrama actual y a sus padres en
	 * el combo.
	 */
	private void loadEntidades() {
		Diagrama diagrama = this.principal.getProyecto().getDiagramaActual();

		// Entidades sin entidades de diagramas ancestros
		Set<Entidad> entidades = diagrama.getEntidades(false);

		for (Entidad entidad : entidades) {
			this.entidades.put(entidad.getNombre(), entidad);
			this.listEntidades.add(entidad.getNombre());
		}

		String[] items = this.listEntidades.getItems();
		Arrays.sort(items);
		this.listEntidades.setItems(items);
	}

	/**
	 * Carga atributos de la entidad actual
	 */
	private void loadAtributos() {

		for (Atributo atributo : this.atributos.values()) {
			this.listAtributos.add(atributo.getNombre());
		}

		String[] items = this.listAtributos.getItems();
		Arrays.sort(items);
		this.listAtributos.setItems(items);
	}

	@Override
	protected void okPressed() {
//		if () {
//			this.principal.error("No hay selección válida para crear atributo");
//		} else {
			//TODO
			Entidad entidad = new Entidad(); // = FIXME
			Identificador identificador = entidad.new Identificador(entidad);
			identificador.addAtributo();
			identificador.addEntidad();
			super.okPressed();
//		}
	}

	protected SelectionAdapter nueva = new SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			// TODO
			close();
		};
	};

}
