package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import utilities.Constants;
import utilities.User;
import utilities.Util;

public class loginGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BackgroundMusic music;
	private JLabel loginLable;
	private JLabel error;
	private JTextField username;
	private JTextField userpassword;
	private JTextField port;
	private JTextField host;
	private JLabel connectionIcon;
	private JButton login, createAccount, guest, connect;
	private GridBagConstraints mGridBagConst;
	private boolean checkUsername, checkPassword;
	private boolean haveHost = false;
	private boolean havePort = false;
	private ImageIcon connectionicon, disconnectionicon;
	private Lock hostAndPortLock, userLock;
	private Condition hostAndPortCondition, userCondition;
	private Socket socket;
	private GameClientListener gameClient;

	public loginGUI(){
		
		initializeComponents();
		createGUI();
		addEvents();
//		music.gamestart();
		
		gameClient = null;
	}
	
	private void initializeComponents(){
		// Socket settings
		socket = null;
		hostAndPortLock = new ReentrantLock();
		hostAndPortCondition = hostAndPortLock.newCondition();
		
		// added for user
		userLock = new ReentrantLock();
		userCondition = userLock.newCondition();
		// added for user
		//appearance settings
		connectionicon = new ImageIcon(Constants.resourceFolderbg + Constants.connected);
		disconnectionicon = new ImageIcon(Constants.resourceFolderbg + Constants.disconnected);
		music = new BackgroundMusic();
		loginLable = new JLabel("Please Login");
		loginLable.setFont(new Font("Serif", Font.BOLD, 20));
		error = new JLabel("");
		error.setFont(new Font("Serif", Font.BOLD, 15));
		error.setForeground(Color.red);
		error.setVisible(false);
		username = new JTextField("username");
		username.setFont(new Font("Serif", Font.BOLD, 15));
		username.setForeground(Color.gray);
		username.setEditable(false);
		userpassword = new JTextField("password");
		userpassword.setFont(new Font("Serif", Font.BOLD, 15));
		userpassword.setForeground(Color.gray);
		userpassword.setEditable(false);
		port = new JTextField("Port");
		port.setFont(new Font("Serif", Font.BOLD, 15));
		port.setForeground(Color.black);
		host = new JTextField("Host");
		host.setFont(new Font("Serif", Font.BOLD, 15));
		host.setForeground(Color.black);
		login = new JButton("   login   ");
		login.setFont(new Font("Serif", Font.BOLD, 15));
		login.setBorderPainted(false);
		login.setContentAreaFilled(false);
		login.setOpaque(true);
		login.setEnabled(false);
		createAccount = new JButton("Create Account");
		createAccount.setFont(new Font("Serif", Font.BOLD, 15));
		createAccount.setBorderPainted(false);
		createAccount.setContentAreaFilled(false);
		createAccount.setOpaque(true);
		createAccount.setEnabled(false);
		guest = new JButton("Guest Login");
		guest.setFont(new Font("Serif", Font.BOLD, 15));
		guest.setBorderPainted(false);
		guest.setContentAreaFilled(false);
		guest.setOpaque(true);
		connect = new JButton("Connect");
		connect.setFont(new Font("Serif", Font.BOLD, 15));
		connect.setBorderPainted(false);
		connect.setContentAreaFilled(false);
		connect.setOpaque(true);
		connect.setEnabled(false);
		checkUsername = false;
		checkPassword = false;
	}
	
	private void createGUI(){
		setSize(800,620);
		setLocation(750,100);
		setBackground();
		setLayout(new GridBagLayout());
		setTitle("Welcome");
		mGridBagConst = new GridBagConstraints();
		mGridBagConst.weightx = 1.0;
		mGridBagConst.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		
		JPanel portandhost = new JPanel(new BorderLayout());
		portandhost.add(host, BorderLayout.CENTER);
		host.setPreferredSize(new Dimension(100,20));
		portandhost.add(port, BorderLayout.EAST);
		port.setPreferredSize(new Dimension(100,20));
		
		mGridBagConst.gridy = 0;
		mGridBagConst.insets = new Insets(0,0,0,250);
		mGridBagConst.ipadx = 200;
		mGridBagConst.ipady = 20;
		add(portandhost,mGridBagConst);
		
		mGridBagConst.gridy = 1;
		mGridBagConst.ipadx = 0;
		mGridBagConst.ipady = 0;
		mGridBagConst.insets = new Insets(20,500,100,80);
		add(loginLable,mGridBagConst);
		
		mGridBagConst.gridy = 2;
		mGridBagConst.ipadx = 300;
		
		mGridBagConst.insets = new Insets(0,450,30,80);
		error.setVisible(true);
		add(error,mGridBagConst);
		
		mGridBagConst.gridy = 3;
		mGridBagConst.ipadx = 0;
		mGridBagConst.insets = new Insets(0,430,0,0);
		connectionIcon = new JLabel(disconnectionicon);
		connectionIcon.setPreferredSize(new Dimension(32,32));
		add(connectionIcon, mGridBagConst);
		
		mGridBagConst.gridy = 4;
		mGridBagConst.ipadx = 200;
		mGridBagConst.ipady = 20;
		mGridBagConst.insets = new Insets(0,500,10,80);
		add(username,mGridBagConst);
		
		mGridBagConst.gridy = 5;
		add(userpassword,mGridBagConst);
		
		mGridBagConst.gridy = 6;
		mGridBagConst.ipadx = 120;
		mGridBagConst.ipady = 10;
		add(login,mGridBagConst);
		
		mGridBagConst.gridy = 7;
		mGridBagConst.ipadx = 120;
		mGridBagConst.ipady = 20;
		add(createAccount,mGridBagConst);
		
		mGridBagConst.gridy = 8;
		add(guest,mGridBagConst);
		
		mGridBagConst.gridy = 9;
		add(connect,mGridBagConst);
	}
	
	private void setBackground(){
		try {
		    final Image backgroundImage = javax.imageio.ImageIO.read(new File(Constants.resourceFolderbg + "background.jpg"));
		    setContentPane(new JPanel(new BorderLayout()) {
				private static final long serialVersionUID = -1315795587311609680L;

				@Override public void paintComponent(Graphics g) {
		            g.drawImage(backgroundImage, 0, 0, null);
		        }
		    });
		} catch (IOException e) {
			JPanel error = new JPanel();
			JOptionPane.showMessageDialog(error,
					"Asset Corruption",
					"File Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean haveHost(){
		haveHost = true;
		if (host.getText().trim().equals("")){
			haveHost = false;
		}
		return haveHost;
	}
	
	private boolean havePort(){
		havePort = true;
		if (port.getText().trim().equals("")){
			havePort = false;
		}
		return havePort;
	}
	
	public Socket getSocket() {
		while (socket == null) {
			hostAndPortLock.lock();
			try {
				hostAndPortCondition.await();
			} catch (InterruptedException ie) {
				Util.printExceptionToCommand(ie);
			}
			hostAndPortLock.unlock();
		}
		return socket;
	}
	
	private class MyDocumentListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			connect.setEnabled(haveHost() && havePort());
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			connect.setEnabled(haveHost() && havePort());
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			connect.setEnabled(haveHost() && havePort());
		}
			
	}
	
	private void addEvents(){
		host.getDocument().addDocumentListener(new MyDocumentListener());
		port.getDocument().addDocumentListener(new MyDocumentListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		username.setFocusable(true);
		username.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(username.getText().equals("username")){
					username.setText("");
					username.setForeground(Color.black);
					checkUsername = false;
					error.setText("");
				}
				else{
					error.setVisible(false);
					checkUsername = true;
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(username.getText().equals("")){
					username.setText("username");
					username.setForeground(Color.gray);
					checkUsername = false;
					login.setEnabled(false);
					createAccount.setEnabled(false);
				}
				else{

					if(checkPassword){
						login.setEnabled(true);
						createAccount.setEnabled(true);
					}
					checkUsername = true;
				}
			}
		});
		userpassword.setFocusable(true);
		userpassword.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(userpassword.getText().equals("password")){
					userpassword.setText("");
					userpassword.setForeground(Color.black);
					checkPassword = false;
					login.setEnabled(false);
					createAccount.setEnabled(false);
					error.setText("");
				}
				else{
					error.setVisible(false);
					checkPassword = true;
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(userpassword.getText().equals("")){
					userpassword.setText("password");
					userpassword.setForeground(Color.gray);
					checkPassword = false;
					login.setEnabled(false);
					createAccount.setEnabled(false);
				}
				else{
					if(checkUsername){
						login.setEnabled(true);
						createAccount.setEnabled(true);
					}
					checkPassword = true;
				}
			}
			
		});
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("LOGIN CLICKED");
				String name = username.getText().trim();
				String password = userpassword.getText().trim();
				User loginUser = new User(name, password);
				if (gameClient != null) {
					if (gameClient == null)
						System.out.println("GC null");
					else System.out.println("GC notnull");
					gameClient.login(loginUser);
//					System.out.println("RESPONSE: " + response);
//					// wait for login condition
//					
//					// handle true/false case
//					System.out.println("RESPONSE: " + response);
//					if (!response) {
//						error.setVisible(true);
//						error.setText("Username or password incorrect");
//					} else {
//						// handle create the main gui and start it
//					}
				}
//				if(!gameclient.sendPacket(loginUser)){
//					error.setVisible(true);
//					error.setText("Username or password incorrect");
//				}
			}
		});
		createAccount.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = username.getText().trim();
				String password = userpassword.getText().trim();
				User createUser = new User(name, password);
				if (gameClient != null) {
					gameClient.create(createUser);
				}
//				if(!gameclient.sendPacket(loginUser)){
//					error.setVisible(true);
//					error.setText("User name already in the system");
//				}
			}
			
		});
		guest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//gameclient.setGuest(true);
			}
			
		});
		connect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				String portInput = port.getText();
				int portInt = -1;
				try {
					portInt = Integer.parseInt(portInput);
				} catch (Exception e) {
					error.setText(utilities.Constants.PORT_ERROR_MESSAGE);
					return;
				}
				if (portInt > utilities.Constants.MIN_PORT_VAL && portInt < utilities.Constants.MAX_PORT_VAL) {
					// try to connect
					String hostnameStr = host.getText();
					try {
						System.out.println("Trying to connect: " + hostnameStr + ", " + portInt);
						socket = new Socket(hostnameStr, portInt);
						hostAndPortLock.lock();
						hostAndPortCondition.signal();
						hostAndPortLock.unlock();
						username.setEditable(true);
						userpassword.setEditable(true);
						port.setEditable(false);
						host.setEditable(false);
						connectionIcon.setIcon(connectionicon);
						gameClient = new GameClientListener(socket); // initialize the game client
						gameClient.setLoginGUI(loginGUI.this);
					} catch (IOException ioe) {
						error.setText(utilities.Constants.PORT_ERROR_MESSAGE);
						Util.printExceptionToCommand(ioe);
						return;
					}
				}
				else { // port value out of range
					error.setText(utilities.Constants.PORT_IN_USE_MESSAGE);
					return;
				}
			}
		});
	}
	
	protected void processLogin(Boolean response) {
		if (!response) {
			error.setVisible(true);
			error.setText("Username or password incorrect");
		} else {
			// handle create the main gui and start it
		}
	}
	
	protected void processCreateAccount(Boolean response) {
		if (!response) {
			error.setVisible(true);
			error.setText("Account already exists");
		} else {
			// handle create the main gui and start it
		}
	}
/*	
	public static void main(String[] args){
		new loginGUI().setVisible(true);
	}*/
}

//
