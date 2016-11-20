 package client;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utilities.Constants;

public class waitGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton singlePlayer;
	private JButton multiPlayer;
	private JButton startButton;
	private JButton mute;
	private GridBagConstraints mGridBagConst;
	private JLabel notice;
	private JLabel sel;
	private int select = -1;
	private boolean pause = false;
	private BackgroundMusic music;
	private JButton portInfo;
	private String host;
	private String port;
	private GameClientListener gameListener;
	
	public waitGUI(GameClientListener gameListener, String host, String port){
		this.gameListener = gameListener;
		this.host = host;
		this.port = port;
		gameListener.setWaitGUI(this);
		initializeComponents();
		setIcon();
		createGUI();
		addEvents();
		music.waitstart();
	}
	
	private void initializeComponents(){
		mGridBagConst = new GridBagConstraints();
		music = new BackgroundMusic();
		mute = new JButton();
		mute.setOpaque(false);
		mute.setContentAreaFilled(false);
		mute.setBorderPainted(false);
		mute.setToolTipText("mute?");
		portInfo = new JButton("");
		portInfo.setText("Host: " + host + "; Port: " + port);
		portInfo.setOpaque(false);
		portInfo.setContentAreaFilled(false);
		portInfo.setBorderPainted(false);
		portInfo.setEnabled(false);
		singlePlayer = new JButton("Single Player");
		singlePlayer.setFont(new Font("Serif", Font.BOLD, 15));
		multiPlayer = new JButton("Multi Player");
		multiPlayer.setFont(new Font("Serif", Font.BOLD, 15));
		startButton = new JButton("Start Game");
		startButton.setFont(new Font("Serif", Font.BOLD, 15));
		startButton.setEnabled(false);
		notice = new JLabel("Please make a selection:");
		notice.setFont(new Font("Serif", Font.BOLD, 20));
		sel = new JLabel("");
		sel.setFont(new Font("Serif", Font.BOLD, 20));
		sel.setForeground(Color.red);
	}
	
	private void setIcon(){
		try{
			Image img = ImageIO.read(new File(Constants.resourceFolderbg + "singleMario.png"));
			singlePlayer.setIcon(new ImageIcon(img));
			Image img2 = ImageIO.read(new File(Constants.resourceFolderbg + "multiMario.png"));
			multiPlayer.setIcon(new ImageIcon(img2));
			Image img3 = ImageIO.read(new File(Constants.resourceFolderbg + "unmute.png"));
			mute.setIcon(new ImageIcon(img3));
		}
		catch (IOException e) {
			JPanel error = new JPanel();
			JOptionPane.showMessageDialog(error,
					"Asset Corruption",
					"File Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void createGUI(){
		//set window 
		setSize(800,470);
		setLocation(750,100);
		setBackground();
		setTitle("waiting");
		setLayout(new GridBagLayout());
		mGridBagConst.weightx = 1.0;
		mGridBagConst.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		
		//add components
		mGridBagConst.gridy = 0;
		mGridBagConst.ipadx = 32;
		mGridBagConst.ipady = 32;
		mGridBagConst.insets = new Insets(0,600,10,0);
		add(mute,mGridBagConst);
		
		mGridBagConst.gridy = 1;
		mGridBagConst.ipadx = 0;
		mGridBagConst.ipady = 0;
		mGridBagConst.insets = new Insets(0,250,100,0);
		add(notice,mGridBagConst);
		
		mGridBagConst.insets = new Insets(0,250,0,0);
		mGridBagConst.gridy = 2;
		add(sel,mGridBagConst);
		
		mGridBagConst.ipadx = 100;
		mGridBagConst.ipady = 20;
		mGridBagConst.gridy = 3;
		add(singlePlayer,mGridBagConst);
		
		mGridBagConst.gridy = 4;
		add(multiPlayer,mGridBagConst);
		
		mGridBagConst.gridy = 5;
		add(startButton,mGridBagConst);
		
		mGridBagConst.gridy = 6;
		add(portInfo,mGridBagConst);
	}
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Logout?","Hermina Skelton"
		        		,JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		            //gameclientlistener...
		        }
		    }
		});
		singlePlayer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				select = 0;
				singlePlayer.setEnabled(false);
				multiPlayer.setEnabled(false);
				sel.setText("Single player mode selected");
				gameListener.sendGameMode(0);
				startButton.setEnabled(true);
			}
			
		});
		multiPlayer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				select = 1;
				singlePlayer.setEnabled(false);
				multiPlayer.setEnabled(false);
				gameListener.sendGameMode(1);
				sel.setText("Multi-player mode selected");
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
	
	private void setBackground(){
		try {
		    final Image backgroundImage = javax.imageio.ImageIO.read(new File(Constants.resourceFolderbg + "wait.jpg"));
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
	
	protected void startGame(){
		new GameGUI(gameListener).setVisible(true);
		dispose();
	}
}
