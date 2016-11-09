package server;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PortGUI extends JFrame {
	public static final long serialVersionUID = 1;
	private JLabel srvSettingsLbl; // title
	private JLabel portLbl; // port label
	private JTextField portInput; // textbox for port input
	private JLabel errorLbl; // shows connection problems
	private JButton connectBtn; // tries to initiate server
	
	public PortGUI() {
		super("Herminia Skelton Server Connection Settings");
		initializeVariables();
		createGUI();
		addEvents();
	}
	
	private void initializeVariables() {
		srvSettingsLbl = new JLabel("HERMINIA SKELTON", SwingConstants.CENTER);
		srvSettingsLbl.setForeground(Constants.SERVER_FONT_COLOR);
		
		portLbl = new JLabel("Port: ");
		
		connectBtn = new JButton("Connect");
	}
	
	private void createGUI() {
		getContentPane().setBackground(Constants.SERVER_BACKGROUND_COLOR); // Panel backgroud color
		
		add(srvSettingsLbl, BorderLayout.NORTH);
	}
	
	private void addEvents() {
		
	}
}
