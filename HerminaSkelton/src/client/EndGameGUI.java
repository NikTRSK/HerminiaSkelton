package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utilities.Constants;

public class EndGameGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private String username1;
	private String username2;
	private String temp;
	private JLabel message;
	private boolean win;
	private int gamemode;
	private int score1;
	private int score2;
	private JLabel name1, name2, score1_, score2_;
	private GameClientListener mlistener;
	private Vector<Integer> topFiveScores;
	private GridBagConstraints mGridBagConst;
	public EndGameGUI(int gamemode, String name1, String name2, int score1, int score2, boolean win, GameClientListener mlistener){
		//gamemode 0 as guest, gamemmode 1 as singleplayer, gameode 2 as multiplayer
		this.gamemode = gamemode;
		this.mlistener = mlistener;
		this.win = win;
		this.score1 = score1;
		this.score2 = score2;
		username1 = name1;
		username2 = name2;
		topFiveScores = new Vector<Integer>(5);
		for(int i = 0; i < 5; i++){
			topFiveScores.add(null);
		}
		addNames();
		setBackground();
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void addNames(){
		if(win){
			if(gamemode == 2){
				temp = "Congratulate" +  username1 + " " + username2 + " you guys ";
			}
			else{
				temp = "Congratulate" +  username1 + " you ";
			}
			temp+=Constants.win;
			if(gamemode == 2){
				temp += "you guys ";
			}
			else{
				temp += "you ";
			}
		}
		else{
			if(gamemode == 2){
				temp = "WAHAHAHAHA" +  username1 + " " + username2 + " you guys ";
			}
			else{
				temp = "WAHAHAHAHA" +  username1 + " you ";
			}
			temp+=Constants.lose;
		}
	}
	
	private void initializeComponents(){
		message = new JLabel("");
		name1 = new JLabel("");
		name2 = new JLabel("");
		name1.setText(username1);
		if(gamemode ==2){
			name2.setText(username2);
		}
		score1_ = new JLabel("");
		score2_ = new JLabel("");
		score1_.setText(Integer.toString(score1));
		if(gamemode ==2){
			score2_.setText(Integer.toString(score2));
		}
	}
	
	private void setBackground(){
		try {
		    final Image backgroundImage = javax.imageio.ImageIO.read(new File(Constants.resourceFolderbg + "EndGameGUI.jpg"));
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
	
	private void createGUI(){
		setSize(1280,720);
		setTitle("Herminia Skelton");
		setLocation(750,100);
		setLayout(new GridBagLayout());
		mGridBagConst = new GridBagConstraints();
		mGridBagConst.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		
		mGridBagConst.gridx = 0;
		mGridBagConst.gridy = 0;
		mGridBagConst.ipadx = 20;
		mGridBagConst.ipady = 20;
		mGridBagConst.anchor = GridBagConstraints.NORTHWEST;
		mGridBagConst.insets = new Insets(0,0,0,0);
		add(name1,mGridBagConst);
		mGridBagConst.gridx = 1;
		add(name2,mGridBagConst);
		
		mGridBagConst.gridx = 0;
		mGridBagConst.gridy = 1;
		mGridBagConst.insets = new Insets(0,0,0,0);
		add(score1_,mGridBagConst);
		mGridBagConst.gridx = 1;
		add(score2_,mGridBagConst);
		
		for(int i = 0 ; i < 5; i++){
			JLabel numLabel = new JLabel(Integer.toString(i+1));
			JLabel namLabel = new JLabel("");
			if(topFiveScores.get(i) != null){
				namLabel.setText(Integer.toString(topFiveScores.get(i)));
			}
			mGridBagConst.gridx = 0;
			mGridBagConst.gridy = i+2;
			add(numLabel,mGridBagConst);
			mGridBagConst.gridx = 1;
			add(namLabel,mGridBagConst);
		}
	}
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		new EndGameGUI(2,"Nick","Matt",2000,3000,true, null).setVisible(true);
	}
}
