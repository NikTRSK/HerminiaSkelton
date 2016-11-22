package server;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utilities.Util;

public class PortGUI extends JFrame {
	public static final long serialVersionUID = 1;
	private JLabel descriptionLabel; // message to the user
	private JLabel portLbl; // port label
	private JTextField portInput; // textbox for port input
	private JLabel errorLbl; // shows connection problems
	private JButton connectBtn; // tries to initiate server
	
	// networking variables
	private Lock portLock;
	private Condition portCondition;
	private ServerSocket serverSocket;
	
	public PortGUI() {
		super("Herminia Skelton Server Connection Settings");
		initializeVariables();
		createGUI();
		addEvents();
		
		setVisible(true);
	}
	
	private void initializeVariables() {
		descriptionLabel = new JLabel(utilities.Constants.PORT_DESCRIPTION_STRING);
		portLbl = new JLabel(utilities.Constants.PORT_LABEL_STRING);
		errorLbl = new JLabel();
		portInput = new JTextField(20);
		portInput.setText("" + utilities.Constants.DEFAULT_PORT);
		connectBtn = new JButton("Connect");
		portLock = new ReentrantLock();
		portCondition = portLock.newCondition();
		serverSocket = null;
	}
	
	private void createGUI() {
		// JFRAME Settings
		setSize(Constants.PORT_GUI_WIDTH, Constants.PORT_GUI_HEIGHT);
		setLayout(new GridLayout(4, 1));
		
		JPanel portFieldPanel = new JPanel();
		portFieldPanel.setLayout(new FlowLayout());
		portFieldPanel.add(portLbl);
		portFieldPanel.add(portInput);
		add(descriptionLabel);
		add(errorLbl);
		add(portFieldPanel);
		add(connectBtn);
	}
	
	private void addEvents() {
		connectBtn.addActionListener(new PortListener());
		portInput.addActionListener(new PortListener());
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent we) {
//				System.exit(0);
//			}
//		});
	}
	
	public ServerSocket getServerSocket() {
		while (serverSocket == null) {
			portLock.lock();
			try {
				portCondition.await();
			} catch (InterruptedException ie) {
				Util.printExceptionToCommand(ie);
			}
			portLock.unlock();
		}
		return serverSocket;
	}
	
	//Custom PORT ACTION LISTENER
	class PortListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String portStr = portInput.getText();
			int portNumber = -1;
			try {
				portNumber = Integer.parseInt(portStr);
			} catch (Exception e) {
				errorLbl.setText(utilities.Constants.PORT_ERROR_MESSAGE);
				return;
			}
			if (portNumber > utilities.Constants.MIN_PORT_VAL && portNumber < utilities.Constants.MAX_PORT_VAL) {
				try {
					ServerSocket tempss = new ServerSocket(portNumber);
					portLock.lock();
					serverSocket = tempss;
					portCondition.signal();
					portLock.unlock();
					PortGUI.this.setVisible(false);
				} catch (IOException ioe) {
					// Thrown if there is an issue with port binding
					errorLbl.setText(utilities.Constants.PORT_IN_USE_MESSAGE);
				}
			}
			else {
				errorLbl.setText(utilities.Constants.PORT_ERROR_MESSAGE);
				return;
			}
		}
	}	
}
