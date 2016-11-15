package client;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
	public EndGameGUI(int gamemode, String name1, String name2, int score1, int score2, boolean win){
		//gamemode 0 as guest, gamemmode 1 as singleplayer, gameode 2 as multiplayer
		this.gamemode = gamemode;
		this.win = win;
		this.score1 = score1;
		this.score2 = score2;
		username1 = name1;
		username2 = name2;
		addNames();
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
	}
	
	private void createGUI(){
		setSize(800,620);
		setTitle("Herminia Skelton");
		setLocation(750,100);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.black);
		add(createNorthPanel(),BorderLayout.NORTH);
	}
	
	private JPanel createNorthPanel(){
		JPanel north = new JPanel();
		return north;
	}
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		new EndGameGUI(2,"Nick","Matt",2000,3000,true).setVisible(true);
	}
}
