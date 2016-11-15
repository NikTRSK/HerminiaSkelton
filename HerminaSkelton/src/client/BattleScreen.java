package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AllCPs.CP;

public class BattleScreen extends JPanel{
	private static final long serialVersionUID = 538889179893602549L;
	
	private Player player;
	private Vector<CP> playerCPs;
	private CP activeCP;
	private CP wildCP;
	
	private JPanel chooseAction;
	private JPanel chooseAttack;
	private JPanel chooseSwitch;
	private JPanel cardHolder;
	private JPanel battlePanel;
	
	private JButton attack;
	private JButton switchCP;
	private JButton throwAssignment;
	private JButton[] attacks;
	private JButton[] switchOption;
	
	private ImageIcon sprite1;
	private ImageIcon sprite2;
	private JLabel healthLabel1;
	private JLabel healthLabel2;
	private JLabel imageLabel1;
	private JLabel imageLabel2;
	private JPanel CP1;
	private JPanel CP2;
	
	
	public BattleScreen(Player currPlayer){
		player = currPlayer;
		
		initializeVariables();
		initializeComponents();
		updateListeners();
		createGUI();
	}
	
	public void newBattle(Player p){
		player = p;
		initializeVariables();
		initializeChooseSwitch();
		throwAssignment.setEnabled(true);
	}
	
	private void initializeVariables(){
		playerCPs = player.getCP();
		activeCP = playerCPs.get(Constants.rand.nextInt(playerCPs.size()));
		wildCP = Constants.generateCP(Constants.rand.nextInt(Constants.numCPs));
		sprite1 = activeCP.getSprite();
		sprite2 = wildCP.getSprite();
	}
	
	private void initializeComponents(){
		initializeBattlePanel();
		initializeChooseAction();
		initializeChooseAttack();
		initializeChooseSwitch();
		initializeCardHolder();
	}
	
	private void initializeBattlePanel(){
		battlePanel = new JPanel();
		//battlePanel.setLayout(new BoxLayout(battlePanel, BoxLayout.X_AXIS));
		battlePanel.setLayout(new GridLayout(1, 2));
		
		healthLabel1 = new JLabel(activeCP.getHealth()+"/"+activeCP.getMaxHealth());
		healthLabel1.setFont(new Font("Courier", Font.BOLD, 35));
		healthLabel1.setHorizontalTextPosition(JLabel.CENTER);
		//ImageIcon imageIcon = sprite1; // load the image to a imageIcon
		Image image = sprite1.getImage(); // transform it 
		Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		sprite1 = new ImageIcon(newimg);
		imageLabel1 = new JLabel(sprite1);
		
		CP1 = new JPanel();
		CP1.setLayout(new BoxLayout(CP1, BoxLayout.Y_AXIS));
		CP1.setBackground(Constants.TYPE_COLOR[activeCP.getType()]);
		CP1.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR2, 5));
		CP1.add(imageLabel1);
		CP1.add(healthLabel1);
		
		healthLabel2 = new JLabel(wildCP.getHealth()+"/"+wildCP.getMaxHealth());
		healthLabel2.setFont(new Font("Courier", Font.BOLD, 35));
		healthLabel2.setHorizontalTextPosition(JLabel.CENTER);
		//imageIcon = sprite2; // load the image to a imageIcon
		image = sprite2.getImage(); // transform it 
		newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		sprite2 = new ImageIcon(newimg);
		imageLabel2 = new JLabel(sprite2);
		
		
		CP2 = new JPanel();
		CP2.setLayout(new BoxLayout(CP2, BoxLayout.Y_AXIS));
		CP2.setBackground(Constants.TYPE_COLOR[wildCP.getType()]);
		CP2.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR2, 5));
		CP2.add(imageLabel2);
		CP2.add(healthLabel2);
		//JLabel CP1 = new JLabel(sprite1);
		//JLabel CP2 = new JLabel(sprite2);
		//battlePanel.add(Box.createGlue());
		battlePanel.add(CP1);
		//battlePanel.add(Box.createGlue());
		battlePanel.add(CP2);
		//battlePanel.add(Box.createGlue());
	}
	
	private void initializeChooseAction(){
		chooseAction = new JPanel();
		chooseAction.setBackground(Constants.BACKGROUND_COLOR);
		
		attack = new JButton("Attack");
		attack.setForeground(Constants.FONT_COLOR);
		attack.setFont(Constants.GAMEFONT);
		attack.setBackground(Constants.BACKGROUND_COLOR2);
		attack.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		attack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 2");
				
			}
			
		});
		
		switchCP = new JButton("Switch CP");
		switchCP.setForeground(Constants.FONT_COLOR);
		switchCP.setFont(Constants.GAMEFONT);
		switchCP.setBackground(Constants.BACKGROUND_COLOR2);
		switchCP.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		switchCP.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 3");
				
			}
			
		});
		
		throwAssignment = new JButton("Throw Assignment");
		throwAssignment.setForeground(Constants.FONT_COLOR);
		throwAssignment.setFont(Constants.GAMEFONT);
		throwAssignment.setBackground(Constants.BACKGROUND_COLOR2);
		throwAssignment.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));	
		throwAssignment.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(executeCatch()){
					battleOver();
				}				
			}
			
		});
		
		chooseAction.setLayout(new GridLayout(1, 3));
		chooseAction.add(attack, BorderLayout.WEST);
		chooseAction.add(switchCP, BorderLayout.CENTER);
		chooseAction.add(throwAssignment, BorderLayout.EAST);
		//TODO: Formatting
	}
	
	private void initializeChooseAttack(){
		chooseAttack = new JPanel();
		chooseAttack.setLayout(new FlowLayout());
		chooseAttack.setBackground(Constants.BACKGROUND_COLOR);
		JButton backButton = new JButton("<< Back");
		backButton.setBackground(Constants.BACKGROUND_COLOR2);
		backButton.setForeground(Constants.FONT_COLOR);
		backButton.setFont(Constants.GAMEFONT);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 1");
				
			}
			
		});
		chooseAttack.add(backButton);
		
		attacks = new JButton[2];
		for(int i = 0; i < 2; i++){
			int a[] = activeCP.getAttackMoves();
			int b = i;
			System.out.println(activeCP.getName());
			JButton attackButton = new JButton(Constants.attackMoves[a[i]].getName());
			attackButton.setBackground(Constants.BACKGROUND_COLOR2);
			attackButton.setForeground(Constants.FONT_COLOR);
			attackButton.setFont(Constants.GAMEFONT);
			attackButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout layout = (CardLayout)cardHolder.getLayout();
					layout.show(cardHolder, "card 1");
					executeMove(b);
				}
				
			});
			attacks[i] = attackButton;
			chooseAttack.add(attackButton);
		}
		
	}
	
	private void initializeChooseSwitch(){
		if(chooseSwitch!=null){
			chooseSwitch.removeAll();
		}
		
		chooseSwitch = new JPanel();
		chooseSwitch.setLayout(new FlowLayout());
		chooseSwitch.setBackground(Constants.BACKGROUND_COLOR);
		JButton backButton = new JButton("<< Back");
		backButton.setBackground(Constants.BACKGROUND_COLOR2);
		backButton.setForeground(Constants.FONT_COLOR);
		backButton.setFont(Constants.GAMEFONT);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 1");
				
			}
			
		});
		chooseSwitch.add(backButton);
		JButton CPs[] = new JButton[playerCPs.size()];
		for(int i = 0; i < playerCPs.size(); i++){
			int a = i;
			JButton CP = new JButton(playerCPs.get(i).getName()+" lvl "+playerCPs.get(i).getLevel());
			CP.setBackground(Constants.BACKGROUND_COLOR2);
			CP.setForeground(Constants.FONT_COLOR);
			CP.setFont(Constants.GAMEFONT);
			CP.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < CPs.length; i++){
						CPs[i].setEnabled(true);
					}
					CP.setEnabled(false);
					executeSwitch(playerCPs.get(a));
					CardLayout layout = (CardLayout)cardHolder.getLayout();
					layout.show(cardHolder, "card 1");
				}
			});
			CPs[i] = CP;
			chooseSwitch.add(CP);
		}
	}
	
	private void initializeCardHolder(){
		cardHolder = new JPanel(new CardLayout());	
		cardHolder.setPreferredSize(new Dimension(0, 300));
		cardHolder.add(chooseAction, "card 1");
		cardHolder.add(chooseAttack, "card 2");
		cardHolder.add(chooseSwitch, "card 3");
		add(cardHolder, BorderLayout.SOUTH);
	}
	
	private void createGUI(){
		this.setLayout(new BorderLayout());
		this.add(battlePanel, BorderLayout.CENTER);
		this.add(cardHolder, BorderLayout.SOUTH);
		
		CardLayout layout = (CardLayout)cardHolder.getLayout();
		layout.show(cardHolder, "card 1");
	}
	
	private void updateListeners(){
		//TODO
	}
	
	private void executeSwitch(CP switchTo){
		activeCP = switchTo;
		redraw();
		Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(1)]].move(wildCP, activeCP);
		redraw();
	}
	
	
	private void executeMove(int move){
		Constants.attackMoves[activeCP.getAttackMoves()[move]].move(activeCP, wildCP);
		redraw();
		if(wildCP.getHealth()<=0)battleOver();
		Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(1)]].move(wildCP, activeCP);
		redraw();
	}
	
	private void redraw(){
		int a[] = activeCP.getAttackMoves();
		attacks[0].setText(Constants.attackMoves[a[0]].getName());
		attacks[1].setText(Constants.attackMoves[a[1]].getName());
		
		healthLabel1.setText(activeCP.getHealth()+"/"+activeCP.getMaxHealth());
		healthLabel2.setText(wildCP.getHealth()+"/"+wildCP.getMaxHealth());
		
		sprite1 = activeCP.getSprite();
		Image image = sprite1.getImage();
		Image newImg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite1 = new ImageIcon(newImg);
		imageLabel1.setIcon(sprite1);
		
		CP1.setBackground(Constants.TYPE_COLOR[activeCP.getType()]);
	}
	
	
	private boolean executeCatch(){
		player.deductAssignment();
		int roll = Constants.rand.nextInt(100);
		int threshold = 70-2*(wildCP.getHealth()/wildCP.getMaxHealth());
		if(roll>=threshold){
			throwAssignment.setEnabled(false);
			return true;
		}
		else{
			Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(2)-1]].move(wildCP, activeCP);
			return false;
		}	
	}

	private void battleOver(){
		if(wildCP.getHealth()==0){
			activeCP.addXP(1);
			if(activeCP.levelUp()){
				//MainGUI.addToChat(activeCP.getName() + " grew to level "+activeCP.getLevel());
				//MainGUI.addToChat("Current Stats: HP = " + activeCP.getMaxHealth() + 
				//					", Attack = " +activeCP.getAttack() + ", Speed = " +
				//					activeCP.getSpeed()";
				//MainGUI.switchToMap();
			}
		}else if(activeCP.getHealth()==0){
			//MainGUI.addToChat("All your CPs died!");
			int runner = Constants.rand.nextInt(playerCPs.size());
			//MainGUI.addToChat(playerCPs.get(runner).getName()+" decided he didn't trust you and ran away!");
			playerCPs.remove(runner);
			//Player.setx(Constants.HealthCenterX);
			//Player.sety(Constants.HealthCenterY);
			//Player.setZone(Constants.HealthCenterZone);
			//MainGUI.switchToMap();
		}else{
			//MainGUI.addToChat(player.getName() + " caught the "+wildCP.getName());
			wildCP.heal();
			player.addCP(wildCP);
			//MainGUI.switchToMap();
		}
	}

}
