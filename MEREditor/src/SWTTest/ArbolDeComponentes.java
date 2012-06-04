package SWTTest;

import mereditor.modelo.Atributo;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Entidad;
import mereditor.modelo.Atributo.TipoAtributo;
import mereditor.modelo.Entidad.TipoEntidad;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class ArbolDeComponentes {

	Tree arbol;
	TreeItem diagramaActivo;

	public ArbolDeComponentes(Tree t) {
		arbol = t;
		diagramaActivo = null;
		arbol.addListener(SWT.MouseDoubleClick, new TreeDoubleClickListener(arbol));
	}

	public void setDiagramaActivo(TreeItem activo) {
		diagramaActivo = activo;
	}

	public void cargar(String string) {
		Diagrama d = new Diagrama("DiagramaPpal", "id");
		ComponenteArbol c = new ComponenteArbol(d);
		Entidad e2 = new Entidad("casa", "id2", d, TipoEntidad.MAESTRA_COSA);
		ComponenteArbol c2 = new ComponenteArbol(e2);
		Atributo a = new Atributo("direccion", "id3", e2, "1", "1", TipoAtributo.CARACTERIZACION, null);
		e2.agregarAtributo(a);
		ComponenteArbol c3 = new ComponenteArbol(a);
		TreeItem t = c.agregarA(arbol);
		TreeItem t2 = c2.agregarA(t);
		c3.agregarA(t2);
		setDiagramaActivo(t);

	}

}
