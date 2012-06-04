package mereditor.interfaz.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import mereditor.representacion.DiagramaControl;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String [] args)
	{
		Principal principal = new Principal();
		principal.setVisible(true);
	}

	private PanelDiagrama diagrama;
	private ArbolDiagramas navegacion;
	private Menu menu;
	private DiagramaControl diagramaActual;
	
	public Principal() {
		super();
		this.inicializar();
	}
	
	public void inicializar() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("MER Editor");
		
		this.inicializarComponentes();
		
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
	
	public void inicializarComponentes() {
		this.diagrama = new PanelDiagrama(this);
		this.navegacion = new ArbolDiagramas();
		this.menu = new Menu(this);
		this.getContentPane().add(this.menu, BorderLayout.NORTH);
		this.getContentPane().add(this.navegacion, BorderLayout.WEST);
		this.getContentPane().add(this.diagrama, BorderLayout.CENTER);
	}
	
	public void salir() {
		this.dispose();
	}

	public DiagramaControl getDiagramaActual() {
		return this.diagramaActual;
	}
}
