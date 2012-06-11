package mereditor.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mereditor.interfaz.swt.figuras.Figura;
import mereditor.modelo.Diagrama;
import mereditor.modelo.Validacion;
import mereditor.modelo.base.Componente;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Proyecto implements Control<Diagrama> {
	/**
	 * Diagrama raiz del proyecto
	 */
	protected DiagramaControl raiz;
	protected Validacion validacion;
	protected String path;

	protected Map<String, Componente> componentes = new HashMap<>();
	
	public Proyecto() {
		this.raiz = new DiagramaControl();
		this.agregar(this.raiz);
		this.validacion = new Validacion();
	}

	public Proyecto(String nombre) {
		this();
		this.raiz.setNombre(nombre);
	}

	public Diagrama getRaiz() {
		return raiz;
	}

	public void setRaiz(Diagrama raiz) {
		this.agregar(raiz);
		this.raiz = (DiagramaControl)raiz;
	}
	
	public Validacion getValidacion() {
		return this.validacion;		
	}

	public void setValidacion(Validacion validacion) {
		this.validacion = validacion;		
	}

	public void agregar(Componente componente) {
		this.componentes.put(componente.getId(), componente);
	}

	public Componente getComponente(String id) {
		return this.componentes.get(id);
	}
	
	public List<Componente> getComponentes() {
		return new ArrayList<>(this.componentes.values());
	}

	public boolean contiene(String id) {
		return this.componentes.containsKey(id);
	}

	@Override
	public Figura<Diagrama> getFigura(String idDiagrama) {
		throw new NotImplementedException();
	}
	
	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		DiagramaControl diagrama = (DiagramaControl)this.componentes.get(idDiagrama);
		diagrama.dibujar(contenedor, idDiagrama);
	}
	
	public void dibujar(Figure contenedor) {
		contenedor.removeAll();
		this.raiz.dibujar(contenedor, null);		
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponentesPath() {
		return this.raiz.getNombre() + "-comp.xml";
	}

	public String getRepresentacionPath() {
		return this.raiz.getNombre() + "-rep.xml";
	}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
