package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AllCPs.CP;
import utilities.FinalBattleState;
import utilities.PlayerAction;

public class FinalBattleScreen extends JPanel{

	private static final long serialVersionUID = -9154200804429634453L;
	//private ClientListener cl;
	
	private Player player;
	private Integer me;
	
	private CP playerCP1;
	private CP playerCP2;
	private CP millerCP1;
	private CP millerCP2;
	
	private JPanel battlePanel;
	private JPanel actionPanel;
	private JPanel attackPanel;
	private JPanel switchPanel;
	
	private JLabel img1;
	private JLabel img2;
	private JLabel img3;
	private JLabel img4;
	
	private JButton[] switchOptions;
	private JButton attackA;
	private JButton attackB;

	public FinalBattleScreen(/*GameClientListener cl, */FinalBattleState fbs, Integer me){
		//this.cl = cl;
		this.me = me;
		
		this.player = fbs.player;
		this.playerCP1 = fbs.cp1;
		this.playerCP2 = fbs.cp2;
		this.millerCP1 = fbs.cp3;
		this.millerCP2 = fbs.cp4;
		
		initializeBattlePanel();
		initializeOptionPanel();
		initializeChooseAttack();
		initializeChooseSwitch();
		initializeCards();
		createGUI();
	}
	
	private void initializeBattlePanel(){
		//TODO
	}
	
	private void initializeOptionPanel(){
		//TODO
	}
	
	private void initializeChooseAttack(){
		//TODO
	}
	
	private void initializeChooseSwitch(){
		//TODO
	}
	
	private void initializeCards(){
		//TODO
	}
	
	private void createGUI(){
		//TODO
	}
	
	public void recieveMessage(FinalBattleState fbs){
		this.player = fbs.player;
		this.playerCP1 = fbs.cp1;
		this.playerCP2 = fbs.cp2;
		this.millerCP1 = fbs.cp3;
		this.millerCP2 = fbs.cp4;
		
		update();
	}
	
	private void update(){
		//TODO
	}
	
	private void sendMessage(PlayerAction pa){
		//cl.sendAction(pa);
	}
	
	
}
