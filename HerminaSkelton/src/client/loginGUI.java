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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utilities.Constants;
import utilities.User;
import utilities.Util;

public class loginGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private BackgroundMusic music;
	private boolean pause = false;
	private JLabel loginLable;
	private JLabel error;
	private JTextField username;
	private JPasswordField userpassword;
	private JTextField port;
	private JTextField host;
	private JLabel connectionIcon;
	private JButton login, createAccount, guest, connect;
	private GridBagConstraints mGridBagConst;
	private boolean checkUsername = false;
	private boolean checkPassword = false;
	private boolean haveHost = false;
	private boolean havePort = false;
	private ImageIcon connectionicon, disconnectionicon;
	private Lock hostAndPortLock;
	private Condition hostAndPortCondition;
	private Socket socket;
	private GameClientListener gameClient;
	private JButton mute;
	private boolean loginornot = false;

	public loginGUI(){
		
		initializeComponents();
		setIcon();
		createGUI();
		addEvents();
		music.gamestart();
		
		gameClient = null;
	}
	
	private void initializeComponents(){
		// Socket settings
		socket = null;
		hostAndPortLock = new ReentrantLock();
		hostAndPortCondition = hostAndPortLock.newCondition();

		// added for user
		//appearance settings
		mute = new JButton();
		mute.setOpaque(false);
		mute.setContentAreaFilled(false);
		mute.setBorderPainted(false);
		mute.setToolTipText("mute?");
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
		userpassword = new JPasswordField("password");
		userpassword.setFont(new Font("Serif", Font.BOLD, 15));
		userpassword.setForeground(Color.gray);
		userpassword.setEditable(false);
		port = new JTextField(Integer.toString(Constants.DEFAULT_PORT));
		port.setFont(new Font("Serif", Font.BOLD, 15));
		port.setForeground(Color.gray);
		host = new JTextField(Constants.DEFUALT_HOST);
		host.setFont(new Font("Serif", Font.BOLD, 15));
		host.setForeground(Color.gray);
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
	
	private void setIcon(){
		try{
			Image img = ImageIO.read(new File(Constants.resourceFolderbg + "unmute.png"));
			mute.setIcon(new ImageIcon(img));
		}
		catch (IOException e1) {
			JPanel error = new JPanel();
			JOptionPane.showMessageDialog(error,
					"Asset Corruption",
					"File Error",
					JOptionPane.ERROR_MESSAGE);
		}
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
		mGridBagConst.ipadx = 32;
		mGridBagConst.ipady = 32;
		mGridBagConst.insets = new Insets(0,600,0,0);
		add(mute,mGridBagConst);
		
		mGridBagConst.gridy = 2;
		mGridBagConst.ipadx = 0;
		mGridBagConst.ipady = 0;
		mGridBagConst.insets = new Insets(10,500,40,80);
		add(loginLable,mGridBagConst);
		
		mGridBagConst.gridy = 3;
		mGridBagConst.ipadx = 300;
		
		mGridBagConst.insets = new Insets(0,450,20,80);
		error.setVisible(true);
		add(error,mGridBagConst);
		
		mGridBagConst.gridy = 4;
		mGridBagConst.ipadx = 0;
		mGridBagConst.insets = new Insets(0,430,0,0);
		connectionIcon = new JLabel(disconnectionicon);
		connectionIcon.setPreferredSize(new Dimension(32,32));
		add(connectionIcon, mGridBagConst);
		
		mGridBagConst.gridy = 5;
		mGridBagConst.ipadx = 200;
		mGridBagConst.ipady = 20;
		mGridBagConst.insets = new Insets(0,500,10,80);
		add(username,mGridBagConst);
		
		mGridBagConst.gridy = 6;
		add(userpassword,mGridBagConst);
		
		mGridBagConst.gridy = 7;
		mGridBagConst.ipadx = 120;
		mGridBagConst.ipady = 10;
		add(login,mGridBagConst);
		
		mGridBagConst.gridy = 8;
		mGridBagConst.ipadx = 120;
		mGridBagConst.ipady = 20;
		add(createAccount,mGridBagConst);
		
		mGridBagConst.gridy = 9;
		add(guest,mGridBagConst);
		
		mGridBagConst.gridy = 10;
		add(connect,mGridBagConst);
		
		userpassword.setEchoChar((char) 0);
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
	
	private boolean haveName(){
		checkUsername = true;
		if (username.getText().trim().equals("")){
			checkUsername = false;
		}
		return checkUsername;
	}
	
	private boolean havePassword(){
		checkPassword = true;
		if (userpassword.getPassword().equals("")){
			checkPassword = false;
		}
		return checkPassword;
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
		public void changedUpdate(DocumentEvent e) {
			removeUpdate(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			removeUpdate(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			connect.setEnabled(haveHost() && havePort());
		}
			
	}
	private class MyDocumentListener2 implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
			removeUpdate(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			removeUpdate(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			createAccount.setEnabled(haveName() && havePassword());
			login.setEnabled(haveName() && havePassword());
		}
			
	}
	
	protected void close(){
		 System.exit(0);
	}
	
	private void addEvents(){
		host.getDocument().addDocumentListener(new MyDocumentListener());
		port.getDocument().addDocumentListener(new MyDocumentListener());
		username.getDocument().addDocumentListener(new MyDocumentListener2());
		userpassword.getDocument().addDocumentListener(new MyDocumentListener2());
		port.setFocusable(true);
		host.setFocusable(true);
		host.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(host.getText().trim().equals(Constants.DEFUALT_HOST)){
					error.setText("");
					host.setText("");
					host.setForeground(Color.black);
				}
				error.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(host.getText().trim().equals("")){
					error.setText("");
					host.setText(Constants.DEFUALT_HOST);
					host.setForeground(Color.gray);
				}
				
			}
			
		});
		port.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(port.getText().trim().equals(Integer.toString(Constants.DEFAULT_PORT))){
					port.setText("");
					error.setText("");
					port.setForeground(Color.black);
				}
				error.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(port.getText().trim().equals("")){
					error.setText("");
					port.setText(Integer.toString(Constants.DEFAULT_PORT));
					port.setForeground(Color.gray);
				}
				
			}
			
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		    	if(!loginornot){
		    		System.exit(0);
		    	}
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Logout?","Hermina Skelton"
		        		,JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		        	gameClient.logout();
		        	close();
		        }
		    }
		    public void windowOpened( WindowEvent e ){
		    	mute.requestFocus();
		    }
		});
		username.setFocusable(true);
		username.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(username.getText().equals("username")){
					username.setText("");
					username.setForeground(Color.black);
					error.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(username.getText().equals("")){
					username.setText("username");
					username.setForeground(Color.gray);
				}
			}
		});
		userpassword.setFocusable(true);
		userpassword.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(String.valueOf(userpassword.getPassword()).equals("password")){
					userpassword.setText("");
					userpassword.setForeground(Color.black);
					error.setText("");
				}
				userpassword.setEchoChar('*');
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(userpassword.getPassword().equals("")){
					userpassword.setText("password");
					userpassword.setForeground(Color.gray);
				}
			}
			
		});
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = username.getText().trim();
				String password = String.valueOf(userpassword.getPassword());
				User loginUser = new User(name, password);
				if (gameClient != null) {
					gameClient.login(loginUser);
				}
			}
		});
		createAccount.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = username.getText().trim();
				String password = String.valueOf(userpassword.getPassword());
				User createUser = new User(name, password);
				if (gameClient != null) {
					gameClient.create(createUser);
				}
			}
			
		});
		guest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				GameGUI game = new GameGUI(new GameClientListener(new Socket()));
				game.hideChat();
				music.endMusic();
				loginGUI.this.dispose();
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
		mute.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!pause){
					music.pauseMusic();
					pause = true;
					try{
						Image img = ImageIO.read(new File(Constants.resourceFolderbg + "mute.png"));
						mute.setIcon(new ImageIcon(img));
					}
					catch (IOException e1) {
						JPanel error = new JPanel();
						JOptionPane.showMessageDialog(error,
								"Asset Corruption",
								"File Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				else{
					music.unpauseMusic();
					pause = false;
					try{
						Image img = ImageIO.read(new File(Constants.resourceFolderbg + "unmute.png"));
						mute.setIcon(new ImageIcon(img));
					}
					catch (IOException e1) {
						JPanel error = new JPanel();
						JOptionPane.showMessageDialog(error,
								"Asset Corruption",
								"File Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	protected void processLogin(Boolean response) {
		if (!response) {
			error.setVisible(true);
			error.setText("Username or password incorrect");
		} else {
			music.endMusic();
			new waitGUI(gameClient,host.getText(),port.getText()).setVisible(true);
			dispose();
		}
	}
	
	protected void processCreateAccount(Boolean response) {
		if (!response) {
			error.setVisible(true);
			error.setText("Account already exists");
		} else {
			error.setText("Account Successfully created");
		}
	}
}
