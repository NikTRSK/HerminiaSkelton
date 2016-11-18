package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AllCPs.CP;
import utilities.BackGroundPanel;
import utilities.FinalBattleState;
import utilities.PlayerAction;

public class FinalBattleScreen extends JPanel{

	// System Stuff.
	private static final long serialVersionUID = -9154200804429634453L;
	private GameClientListener cl;
	private Player player;
	private Integer me;
	private CP[] CPs;
	private JPanel cardHolder;
	
	// Stuff for chooseActionPanel.
	private JPanel actionPanel;
	
	// Stuff for Choose Target.
	private JPanel targetPanel;
	private JLabel targetHealth1;
	private JLabel targetHealth2;
	private JLabel targetSprite1;
	private JLabel targetSprite2;
	private JButton chooseTarget1;
	private JButton chooseTarget2;
	
	// Stuff for Battle Panel.
	private JLabel[] sprites;
	private JLabel[] healthLabels;
	private BackGroundPanel battlePanel;
	
	// Stuff for Choose Switch.
	private JButton[] switchOptions;
	private JPanel switchPanel;
	
	// Stuff for Choose Attack.
	private JPanel attackPanel;
	private JButton attackA;
	private JButton attackB;

	public FinalBattleScreen(GameClientListener cl, FinalBattleState fbs, Integer me){
		this.cl = cl;
		this.me = me;
		
		this.player = fbs.player;
		this.CPs = new CP[4];
		this.CPs[0] = fbs.cp1;
		this.CPs[1] = fbs.cp2;
		this.CPs[2] = fbs.cp3;
		this.CPs[3] = fbs.cp4;
		
		initializeBattlePanel();
		initializeOptionPanel();
		initializeChooseAttack();
		initializeChooseTarget();
		initializeChooseSwitch();
		initializeCards();
		createGUI();
	}
	
	private void initializeBattlePanel(){
		JPanel[] holders = new JPanel[4];
		sprites = new JLabel[4];
		healthLabels = new JLabel[4];
		
		// Setting image labels.
		for(int i = 0; i<4; i++){
			ImageIcon sprite = CPs[i].getSprite();
			Image img = sprite.getImage();
			Image nwImg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
			sprite = new ImageIcon(nwImg);
			sprites[i] = new JLabel(sprite);
		}
		
		// Setting health labels.
		for(int i = 0; i < 4; i++){
			JLabel health = new JLabel(CPs[i].getHealth()+"/"+CPs[i].getMaxHealth());
			health.setFont(Constants.GAMEFONT);
			health.setForeground(Constants.FONT_COLOR);
			health.setHorizontalAlignment(JLabel.CENTER);
			healthLabels[i] = health;
		}
		
		// Setting holder panels.
		for(int i = 0; i < 4; i++){
			JPanel holder = new JPanel();
			holder.setLayout(new BorderLayout());
			holder.add(sprites[i], BorderLayout.CENTER);
			holder.add(healthLabels[i], BorderLayout.SOUTH);
			holder.setOpaque(false);
			holders[i] = holder;
		}
		
		battlePanel = new BackGroundPanel(Constants.FINAL_BATTLE_BACKGROUND);
		battlePanel.setLayout(new GridLayout(2, 2));
		battlePanel.add(holders[0]);
		battlePanel.add(holders[2]);
		battlePanel.add(holders[1]);
		battlePanel.add(holders[3]);
	}
	
	private void initializeOptionPanel(){
		
		JButton attack = new JButton("Attack");
		attack.setForeground(Constants.FONT_COLOR);
		attack.setFont(Constants.GAMEFONT);
		attack.setBackground(Constants.BACKGROUND_COLOR2);
		attack.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		attack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 2");
			}
			
		});
		
		JButton switchCP = new JButton("Switch CP");
		switchCP.setForeground(Constants.FONT_COLOR);
		switchCP.setFont(Constants.GAMEFONT);
		switchCP.setBackground(Constants.BACKGROUND_COLOR2);
		switchCP.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		switchCP.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 3");
				
			}
			
		});
		
		actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(1, 2));
		actionPanel.add(attack);
		actionPanel.add(switchCP);
	}
	
	private void initializeChooseAttack(){
		JButton backButton = new JButton("<< Back");
		backButton.setBackground(Constants.BACKGROUND_COLOR2);
		backButton.setForeground(Constants.FONT_COLOR);
		backButton.setFont(Constants.GAMEFONT);
		backButton.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		attackA  = new JButton(Constants.attackMoves[CPs[me].getAttackMoves()[0]].getName());
		attackA.setBackground(Constants.BACKGROUND_COLOR2);
		attackA.setForeground(Constants.FONT_COLOR);
		attackA.setFont(Constants.GAMEFONT);
		attackA.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		attackA.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		attackB  = new JButton(Constants.attackMoves[CPs[me].getAttackMoves()[1]].getName());
		attackB.setBackground(Constants.BACKGROUND_COLOR2);
		attackB.setForeground(Constants.FONT_COLOR);
		attackB.setFont(Constants.GAMEFONT);
		attackB.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		attackB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		attackPanel = new JPanel();
		attackPanel.setLayout(new GridLayout(1, 3));
		attackPanel.add(backButton);
		attackPanel.add(attackA);
		attackPanel.add(attackB);
	}
	
	private void initializeChooseTarget(){
		JButton backButton = new JButton("<< Back");
		backButton.setBackground(Constants.BACKGROUND_COLOR2);
		backButton.setForeground(Constants.FONT_COLOR);
		backButton.setFont(Constants.GAMEFONT);
		backButton.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		// For Miller's first CP.
		targetHealth1 = new JLabel(CPs[2].getHealth()+"/"+CPs[2].getMaxHealth());
		targetHealth1.setFont(Constants.GAMEFONT);
		targetHealth1.setBackground(Constants.BACKGROUND_COLOR2);
		targetHealth1.setForeground(Constants.FONT_COLOR);
		
		ImageIcon sprite = CPs[2].getSprite();
		Image img = sprite.getImage();
		Image nwimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		sprite = new ImageIcon(nwimg);
		targetSprite1 = new JLabel(sprite);
		
		chooseTarget1 = new JButton("Attack "+CPs[2].getName());
		chooseTarget1.setFont(Constants.GAMEFONT);
		chooseTarget1.setBackground(Constants.BACKGROUND_COLOR2);
		chooseTarget1.setForeground(Constants.FONT_COLOR);
		chooseTarget1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 
				
			}
			
		});
		
		JPanel holder1 = new JPanel();
		holder1.setLayout(new BorderLayout());
		holder1.setOpaque(true);
		holder1.add(targetHealth1, BorderLayout.NORTH);
		holder1.add(targetSprite1);
		holder1.add(targetHealth1, BorderLayout.SOUTH);
		
		
		
		
		// For Miller's second CP.
		targetHealth2 = new JLabel(CPs[3].getHealth()+"/"+CPs[3].getMaxHealth());
		targetHealth2.setFont(Constants.GAMEFONT);
		targetHealth2.setBackground(Constants.BACKGROUND_COLOR2);
		targetHealth2.setForeground(Constants.FONT_COLOR);
		
		sprite = CPs[2].getSprite();
		img = sprite.getImage();
		nwimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		sprite = new ImageIcon(nwimg);
		targetSprite2 = new JLabel(sprite);
		
		chooseTarget2 = new JButton("Attack "+CPs[3].getName());
		chooseTarget2.setFont(Constants.GAMEFONT);
		chooseTarget2.setBackground(Constants.BACKGROUND_COLOR2);
		chooseTarget2.setForeground(Constants.FONT_COLOR);
		chooseTarget2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 
				
			}
			
		});
		
		JPanel holder2 = new JPanel();
		holder2.setLayout(new BorderLayout());
		holder2.setOpaque(true);
		holder2.add(targetHealth2, BorderLayout.NORTH);
		holder2.add(targetSprite2);
		holder2.add(chooseTarget2, BorderLayout.SOUTH);
		
		
				
		targetPanel = new JPanel();
		targetPanel.setLayout(new GridLayout(1, 3));
		targetPanel.add(backButton);
		targetPanel.add(holder1);
		targetPanel.add(holder2);
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
		this.CPs[0] = fbs.cp1;
		this.CPs[1] = fbs.cp2;
		this.CPs[2] = fbs.cp3;
		this.CPs[3] = fbs.cp4;
		
		update();
	}
	
	private void update(){
		//TODO
	}
	
	private void sendMessage(PlayerAction pa){
		//cl.sendAction(pa);
	}
	
	
}
