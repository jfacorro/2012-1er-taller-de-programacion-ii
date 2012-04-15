package mereditor.interfaz.swing;

import java.awt.Color;

import javax.swing.JPanel;

import mereditor.modelo.Diagrama;
import mereditor.modelo.base.Componente;

public class PanelDiagrama extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Principal principal;

	private Diagrama diagrama;
	
	public PanelDiagrama(Principal principal) {
		super();
		
		this.principal = principal;
		
		this.init();
	}

	private void init() {
		this.setBackground(Color.gray);
		this.setLayout(null);
		
		this.diagrama = this.principal.getDiagramaActual();
		
		if(diagrama != null) {
			this.dibujarDiagrama();
		}
	}

	private void dibujarDiagrama() {
		
		
		for (Componente componente : this.diagrama.getComponentes()) {
			
		}		
	}
}
