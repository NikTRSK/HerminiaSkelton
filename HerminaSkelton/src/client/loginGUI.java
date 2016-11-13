import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utilities.User;

public class loginGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BackGroundMusic music;
	private JLabel loginLable;
	private JLabel error;
	private JTextField username;
	private JTextField userpassword;
	private JButton login, createAccount, guest;
	private GridBagConstraints mGridBagConst;
	private boolean checkUsername, checkPassword;

	public loginGUI(){
		
		initializeComponents();
		createGUI();
		addEvents();
		music.gamestart();
	}
	
	private void initializeComponents(){
		music = new BackGroundMusic();
		loginLable = new JLabel("Please Login");
		loginLable.setFont(new Font("Serif", Font.BOLD, 25));
		error = new JLabel("");
		error.setFont(new Font("Serif", Font.BOLD, 15));
		error.setVisible(false);
		error.setFont(new Font("Serif", Font.BOLD, 25));
		username = new JTextField("username");
		username.setFont(new Font("Serif", Font.BOLD, 15));
		username.setForeground(Color.gray);
		userpassword = new JTextField("password");
		userpassword.setFont(new Font("Serif", Font.BOLD, 15));
		userpassword.setForeground(Color.gray);
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
		mGridBagConst.gridy = 0;
		mGridBagConst.insets = new Insets(20,500,100,80);
		add(loginLable,mGridBagConst);
		
		mGridBagConst.gridy = 1;
		mGridBagConst.insets = new Insets(0,500,30,80);
		error.setVisible(true);
		add(error,mGridBagConst);
		
		mGridBagConst.gridy = 2;
		mGridBagConst.ipadx = 200;
		mGridBagConst.ipady = 20;
		mGridBagConst.insets = new Insets(0,500,10,80);
		add(username,mGridBagConst);
		
		mGridBagConst.gridy = 3;
		add(userpassword,mGridBagConst);
		
		mGridBagConst.gridy = 4;
		mGridBagConst.ipadx = 120;
		mGridBagConst.ipady = 10;
		add(login,mGridBagConst);
		
		mGridBagConst.gridy = 5;
		mGridBagConst.ipadx = 120;
		mGridBagConst.ipady = 20;
		add(createAccount,mGridBagConst);
		
		mGridBagConst.gridy = 6;
		add(guest,mGridBagConst);
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
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		username.setFocusable(true);
		username.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(username.getText().equals("username")){
					username.setText("");
					username.setForeground(Color.black);
					checkUsername = false;
				}
				else{
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
				}
				else{
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
				String name = username.getText().trim();
				String password = userpassword.getText().trim();
				User loginUser = new User(name, password);
			}
		});
		createAccount.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = username.getText().trim();
				String password = userpassword.getText().trim();
				User loginUser = new User(name, password);
			}
			
		});
		guest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
//	public static void main(String args[]){
//		new loginGUI().setVisible(true);
//	}
}

