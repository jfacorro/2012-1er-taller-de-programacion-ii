package mereditor.interfaz.swt.editores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mereditor.control.AtributoControl;
import mereditor.modelo.Atributo;
import mereditor.modelo.Atributo.TipoAtributo;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class AtributosTabla extends TableViewer {
	public static final String[] PROPS = { Editor.NOMBRE, Editor.TIPO };

	private List<Atributo> atributos = new ArrayList<>();
	private List<Atributo> atributosAEliminar = new ArrayList<>();
	
	private IStructuredSelection selection = null;
	
	public AtributosTabla(Composite parent) {
		super(parent, SWT.FULL_SELECTION);
		this.init();
	}

	private void init() {
		this.setContentProvider(this.contentProvider);
		this.setLabelProvider(this.labelProvider);
		this.setInput(this.atributos);

		// Configurar tabla
		Table tblAtributos = this.getTable();
		tblAtributos
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		new TableColumn(tblAtributos, SWT.CENTER).setText(Editor.NOMBRE);
		new TableColumn(tblAtributos, SWT.CENTER).setText(Editor.TIPO);

		tblAtributos.setHeaderVisible(true);
		tblAtributos.setLinesVisible(true);

		// Crear editores de celda
		CellEditor[] editors = new CellEditor[2];
		editors[0] = new TextCellEditor(tblAtributos);
		editors[1] = new ComboBoxCellEditor(tblAtributos, Editor.TiposAtributo,
				SWT.READ_ONLY);

		// Establecer el titulo de las columnas, los modificadores y los
		// editores.
		this.setColumnProperties(PROPS);
		this.setCellModifier(this.cellModifier);
		this.setCellEditors(editors);

		this.addSelectionChangedListener(seleccion);
	}

	// Agregar un nuevo atributo cuando se hace click sobre el boton "Nuevo"
	public final SelectionListener nuevo = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			AtributoControl atributo = new AtributoControl();
			atributo.setNombre("Nombre");
			atributo.setTipo(TipoAtributo.CARACTERIZACION);

			atributos.add(atributo);

			refresh();
		}
	};
	
	// Eliminar atributo
	public final SelectionListener eliminar = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (selection!=null) {
				AtributoControl atributo = (AtributoControl)selection.getFirstElement();
				
				atributos.remove(atributo);
				atributosAEliminar.add(atributo);
				
				refresh();
			}
		}
	};
	
	private final ISelectionChangedListener seleccion = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			selection = (IStructuredSelection) getSelection();
		}
	};

	private final IContentProvider contentProvider = new IStructuredContentProvider() {
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return ((List<?>) inputElement).toArray();
		}
	};

	private final IBaseLabelProvider labelProvider = new ITableLabelProvider() {

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			Atributo atr = (Atributo) element;
			switch (columnIndex) {
			case 0:
				return atr.getNombre();
			case 1:
				return atr.getTipo().name();
			}
			return null;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	};
	
	private ICellModifier cellModifier = new ICellModifier() {
		public boolean canModify(Object element, String property) {
			// queda forzado q se puedan editar todas las propiedades
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
				atr.setTipo(TipoAtributo.class.getEnumConstants()[(int) value]);

			refresh();
		}
	};

	public void setAtributos(Set<Atributo> atributos) {
		this.atributos.addAll(atributos);
		this.refresh();
		
		for (TableColumn column : this.getTable().getColumns())
			column.pack();
	}
	
	public List<Atributo> getAtributos() {
		return this.atributos;
	}
	
	public List<Atributo> getAtributosAEliminar() {
		return this.atributosAEliminar;
	}
	
}
