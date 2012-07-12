package mereditor.interfaz.swt.listeners;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mereditor.interfaz.swt.figuras.Figura;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.swt.SWT;

public class SeleccionControlador implements MouseListener {
	private final static Set<IFigure> selected = new HashSet<>();
	private final static Map<IFigure, Figure> selectedBorders = new HashMap<>();

	private Figura<?> figura = null;

	public SeleccionControlador(Figura<?> figura) {
		this.figura = figura;
		this.figura.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		if ((me.getState() & SWT.CTRL) == 0)
			deselectAll();

		if (!selected.contains(this.figura))
			select(this.figura);
		else
			deselect(this.figura);
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {

	}

	/**
	 * Selecciona la figura indicada
	 * 
	 * @param figura
	 */
	private static void select(Figura<?> figura) {
		Figure seleccion = new Figure();
		seleccion.setOpaque(false);
		seleccion.setEnabled(false);
		seleccion.setBounds(figura.getBounds());
		seleccion.setBorder(new LineBorder(Figura.defaultLineColor, 2, SWT.LINE_DOT));
		figura.getParent().add(seleccion);
		figura.addFiguraLoqueada(seleccion);

		selected.add(figura);
		selectedBorders.put(figura, seleccion);
	}

	/**
	 * Deselecciona la figura indicada.
	 * 
	 * @param figura
	 */
	private static void deselect(Figura<?> figura) {
		if (figura != null && figura.getParent() != null) {
			IFigure parent = figura.getParent();
			if(parent.getChildren().contains(selectedBorders.get(figura)))
				parent.remove(selectedBorders.get(figura));
			figura.removeFiguraLoqueada(selectedBorders.get(figura));
		}

		selected.remove(figura);
		selectedBorders.remove(figura);
	}

	/**
	 * Indica si una figura está seleccionada.
	 * 
	 * @param figura
	 * @return
	 */
	public static boolean isSelected(Figure figura) {
		return selected.contains(figura);
	}

	/**
	 * Deselecciona todas las figuras.
	 */
	public static void deselectAll() {
		Set<IFigure> figuras = new HashSet<>(selected);

		for (IFigure figura : figuras)
			deselect((Figura<?>) figura);
	}

	/**
	 * Devuelve todas las figuras seleccionadas.
	 * 
	 * @return
	 */
	public static Set<IFigure> getSelected() {
		return Collections.unmodifiableSet(selected);
	}
}
