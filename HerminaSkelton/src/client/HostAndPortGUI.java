package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class HostAndPortGUI extends JFrame{
	/**
	 * Serialization 
	 */
	private static final long serialVersionUID = 212215363234709756L;
	private JTextField portTextField; 
	private JTextField hostnameTextField;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	private JPanel hostnamePanel;
	private JPanel portPanel;
	private JPanel mainPanel;
	private JLabel hostnameLabel;
	private JLabel portLabel;
	private JTextPane descriptionTextPane;
	private JTextPane warningTextPane;
	private JButton connectButton;
	private JButton cancelButton;
	private boolean haveHost = false;
	private boolean havePort = false;
	//lock and condition initialization
	private Lock hostAndPortLock;
	private Condition hostAndPortCondition;
	private Socket socket;
	
	public HostAndPortGUI(){
		super("Settings");
		setSize(600,600);
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initializeVariables();
		createGUI();
		addActionAdapters();
		setVisible(true);
	}
	
	private void initializeVariables(){
		portTextField = new JTextField();
		hostnameTextField = new JTextField();
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		hostnamePanel = new JPanel();
		portPanel = new JPanel();
		mainPanel = new JPanel();
		descriptionTextPane = new JTextPane();
		hostnameLabel = new JLabel("Host");
		portLabel = new JLabel("Port");
		warningTextPane = new JTextPane();
		connectButton = new JButton("Connect");
		cancelButton = new JButton("Cancel");
		
	}
	
	private void createGUI(){
		connectButton.setEnabled(false);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		descriptionTextPane.setText("Set Server Settings");
		descriptionTextPane.setEditable(false);
		descriptionTextPane.setPreferredSize(new Dimension(200, 50));
		descriptionTextPane.setSize(new Dimension(200, 50));
		topPanel.add(descriptionTextPane);
		createCenterPanel();
		bottomPanel.add(connectButton);
		bottomPanel.add(cancelButton);
		mainPanel.add(topPanel);//, BorderLayout.NORTH);
		mainPanel.add(centerPanel);//, BorderLayout.CENTER);
		mainPanel.add(bottomPanel);//, BorderLayout.SOUTH);
		add(mainPanel);
		
	}
	
	private void createCenterPanel(){
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		hostnameTextField.setPreferredSize(new Dimension(200,30));
		portTextField.setPreferredSize(new Dimension(200,30));		
		
		
		warningTextPane.setEditable(false);
		warningTextPane.setPreferredSize(new Dimension(100, 50));
		warningTextPane.setSize(new Dimension(100,50));
		
		hostnamePanel.add(hostnameLabel);
		hostnamePanel.add(Box.createHorizontalStrut(15));
		hostnamePanel.add(hostnameTextField);
		
		centerPanel.add(warningTextPane);
		centerPanel.add(Box.createVerticalStrut(20));
		centerPanel.add(hostnamePanel);
		centerPanel.add(Box.createVerticalStrut(20));
		
		portPanel.add(portLabel);
		portPanel.add(Box.createHorizontalStrut(10));
		portPanel.add(portTextField);
		
		centerPanel.add(portPanel);
		
	}

	private boolean haveHost(){
		haveHost = true;
		if (hostnameTextField.getText().trim().equals("")){
			haveHost = false;
		}
		return haveHost;
	}
	
	private boolean havePort(){
		havePort = true;
		if (portTextField.getText().trim().equals("")){
			havePort = false;
		}
		return havePort;
	}
	
	private void addActionAdapters(){
		hostnameTextField.getDocument().addDocumentListener(new MyDocumentListener());
		portTextField.getDocument().addDocumentListener(new MyDocumentListener());
		
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
			
		});
	
		connectButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
			
		});
	}
	
	private class MyDocumentListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			connectButton.setEnabled(haveHost() && havePort());
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			connectButton.setEnabled(haveHost() && havePort());
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			connectButton.setEnabled(haveHost() && havePort());
			
		}
		
	}
	
		/*public static void main(String[] args){
			new HostAndPortGUI();
			new GameGUI();
			
			
			
		}*/

}
