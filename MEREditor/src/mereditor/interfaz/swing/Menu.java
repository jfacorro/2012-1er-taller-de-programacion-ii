package mereditor.interfaz.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Menu extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	private Principal principal;

	public Menu(Principal principal) {
		super();
		
		this.principal = principal;
		
		this.setFloatable(false);
		this.CrearBotones();
	}

	private void CrearBotones() {
		this.add(this.crearBoton("Crear Poyecto", "nuevo.png"));
		this.add(this.crearBoton("Abrir Poyecto", "abrir.png"));
		this.add(this.crearBoton("Guardar", "guardar.png"));
		this.add(this.crearBoton("Imprimir", "imprimir.png"));
		this.add(this.crearBoton("Exportar", "exportar.png"));
		this.add(this.crearBoton("Entidad", "entidad.png"));
		this.add(this.crearBoton("Relacion", "relacion.png"));
		this.add(this.crearBoton("Jerarquia", "jerarquia.png"));
		this.add(this.crearBoton("Diagrama", "diagrama.png"));
		this.add(this.crearBoton("Validar", "validar.png"));
		this.add(this.crearBoton("Zoom", "zoom.png"));
		
		this.add(this.crearBoton("Salir", "salir.png", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				principal.salir();
			}
		}));
	}
	
	private JButton crearBoton(String texto, String icono) {
		return this.crearBoton(texto, icono, null);
	}
	
	private JButton crearBoton(String texto, String icono, ActionListener listener) {
		JButton boton = new JButton();
		boton.addActionListener(listener);
		boton.setToolTipText(texto);
		
		URL iconoURL = null;
		if(icono != null) {
			iconoURL = classLoader.getResource("recursos/imagenes/" + icono);
			if(iconoURL != null)
				boton.setIcon(new ImageIcon(iconoURL, texto));
		}
		if (iconoURL == null) {
			boton.setText(texto);
		}
		
		
		return boton;
	}
}
