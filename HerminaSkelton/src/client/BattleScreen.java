package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import AllCPs.CP;
import utilities.BackGroundPanel;

public class BattleScreen extends JPanel{
	private static final long serialVersionUID = 538889179893602549L;
	
	private GameGUI mainGUI;
	
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
	
	private JTextArea CPUpdates1;
	private JTextArea CPUpdates2;
	
	
	public BattleScreen(Player currPlayer, GameGUI mainGUI){
		this.mainGUI = mainGUI;
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
		cardHolder.add(chooseSwitch, "card 3");
		throwAssignment.setEnabled(true);
		redraw();
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
		healthLabel1 = new JLabel(activeCP.getHealth()+"/"+activeCP.getMaxHealth());
		healthLabel1.setFont(new Font("Courier", Font.BOLD, 35));
		healthLabel1.setHorizontalTextPosition(JLabel.CENTER);
		JPanel healthPanel1 = new JPanel();
		
		Image image = sprite1.getImage();
		Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite1 = new ImageIcon(newimg);
		imageLabel1 = new JLabel(sprite1);
		
		CPUpdates1 = new JTextArea("");
		JPanel holder1 = new BackGroundPanel(Constants.POKEBALL);
		holder1.setLayout(new BorderLayout());
		holder1.add(imageLabel1, BorderLayout.CENTER);
		holder1.add(healthLabel1, BorderLayout.SOUTH);
		
		CP1 = new JPanel();
		CP1.setLayout(new BorderLayout());
		CP1.setBackground(Constants.TYPE_COLOR[wildCP.getType()]);
		CP1.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR2, 5));
		CP1.add(holder1, BorderLayout.CENTER);
		
		healthLabel2 = new JLabel(wildCP.getHealth()+"/"+wildCP.getMaxHealth());
		healthLabel2.setFont(new Font("Courier", Font.BOLD, 35));
		healthLabel2.setHorizontalTextPosition(JLabel.CENTER);
		JPanel healthPanel2 = new JPanel();
		
		image = sprite2.getImage();
		newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite2 = new ImageIcon(newimg);
		imageLabel2 = new JLabel(sprite2);
		
		CPUpdates2 = new JTextArea("dbd");
		JPanel holder2 = new BackGroundPanel(Constants.POKEBALL);
		holder2.setLayout(new BorderLayout());
		holder2.add(imageLabel2, BorderLayout.CENTER);
		holder2.add(healthLabel2, BorderLayout.SOUTH);
		
		
		CP2 = new JPanel();
		CP2.setLayout(new BorderLayout());
		CP2.setBackground(Constants.TYPE_COLOR[wildCP.getType()]);
		CP2.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR2, 5));
		CP2.add(holder2, BorderLayout.CENTER);
		
		battlePanel = new JPanel();
		battlePanel.setLayout(new GridLayout(1, 2));
		battlePanel.add(CP1);
		battlePanel.add(CP2);
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
		
		switchOption = new JButton[playerCPs.size()];
		
		for(int i = 0; i < playerCPs.size(); i++){
			int a = i;
			JButton CP = new JButton(playerCPs.get(i).getName()+" lvl "+playerCPs.get(i).getLevel());
			if(playerCPs.get(i).equals(activeCP))CP.setEnabled(false);
			CP.setBackground(Constants.BACKGROUND_COLOR2);
			CP.setForeground(Constants.FONT_COLOR);
			CP.setFont(Constants.GAMEFONT);
			CP.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < switchOption.length; i++){
						if(playerCPs.get(i).getHealth()>0){
							switchOption[i].setEnabled(true);
						}
					}
					CP.setEnabled(false);
					executeSwitch(playerCPs.get(a));
					CardLayout layout = (CardLayout)cardHolder.getLayout();
					layout.show(cardHolder, "card 1");
				}
			});
			switchOption[i] = CP;
		}
		
		chooseSwitch = new JPanel();
		chooseSwitch.setLayout(new FlowLayout());
		chooseSwitch.setBackground(Constants.BACKGROUND_COLOR);
		chooseSwitch.add(backButton);
		for(int i = 0; i< switchOption.length; i++){
			chooseSwitch.add(switchOption[i]);
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
		Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(1)]].move(wildCP, activeCP, CPUpdates2);
		redraw();
	}
	
	private void executeMove(int move){
		Constants.attackMoves[activeCP.getAttackMoves()[move]].move(activeCP, wildCP, CPUpdates1);
		redraw();
		System.out.println(wildCP.getHealth());
		if(wildCP.getHealth()<=0)battleOver();
		Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(2)]].move(wildCP, activeCP, CPUpdates2);
		if(activeCP.getHealth()<=0)deadCP();
		redraw();
	}
	
	private void redraw(){
		int a[] = activeCP.getAttackMoves();
		attacks[0].setText(Constants.attackMoves[a[0]].getName());
		attacks[1].setText(Constants.attackMoves[a[1]].getName());
		
		healthLabel1.setText(activeCP.getHealth()+"/"+activeCP.getMaxHealth());
		healthLabel2.setText(wildCP.getHealth()+"/"+wildCP.getMaxHealth());
		System.out.println(wildCP.getHealth());
		
		sprite1 = activeCP.getSprite();
		Image image = sprite1.getImage();
		Image newImg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite1 = new ImageIcon(newImg);
		imageLabel1.setIcon(sprite1);
		
		CP1.setBackground(Constants.TYPE_COLOR[activeCP.getType()]);
		
		for(int i = 0; i<switchOption.length; i++){
			if(playerCPs.get(i).getHealth()<=0){
				switchOption[i].setEnabled(false);
				switchOption[i].setText(switchOption[i].getText()+" (fainted)");
			}
		}
	}
	
	private void deadCP(){
		  
		  JPanel CPHolder = new JPanel();
		  CPHolder.setLayout(new BoxLayout(CPHolder, BoxLayout.X_AXIS));
		  
		  for(int i = 0; i < playerCPs.size(); i++){
			  if(playerCPs.get(i).getHealth()<=0)continue;
			  JPanel CP = new JPanel();
			  CP.setLayout(new BorderLayout());
			  CP.add(new JButton(playerCPs.get(i).getSprite()), BorderLayout.CENTER);
			  CP.add(new JLabel(playerCPs.get(i).getName()+" lvl "+playerCPs.get(i).getLevel()));
			  CPHolder.add(CP);
		  }
		  
		  
		  JPanel dialogPanel = new JPanel();
		  dialogPanel.setLayout(new BorderLayout());
		  dialogPanel.setBackground(Constants.BACKGROUND_COLOR);
		  dialogPanel.add(CPHolder, BorderLayout.CENTER);
		  dialogPanel.add(new JLabel("Your CP fainted, choose another!"));
		  
		  JDialog dialog = new JDialog(mainGUI, Dialog.ModalityType.APPLICATION_MODAL);
		  dialog.add(dialogPanel);
		  dialog.pack();
		  dialog.setVisible(true);
	}
	
	private boolean executeCatch(){
		player.deductAssignment();
		int roll = Constants.rand.nextInt(100);
		if(wildCP.getHealth()==0){
			//TODO
			return true;
		}
		int threshold = 70-2*(wildCP.getHealth()/wildCP.getMaxHealth());
		if(roll>=threshold){
			throwAssignment.setEnabled(false);
			return true;
		}
		else{
			Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(2)-1]].move(wildCP, activeCP, CPUpdates2);
			return false;
		}	
	}

	private void battleOver(){
		if(wildCP.getHealth()==0){
			System.out.println("1");
			activeCP.addXP(1);
			if(activeCP.levelUp()){
				//MainGUI.addToChat(activeCP.getName() + " grew to level "+activeCP.getLevel());
				//MainGUI.addToChat("Current Stats: HP = " + activeCP.getMaxHealth() + 
				//					", Attack = " +activeCP.getAttack() + ", Speed = " +
				//					activeCP.getSpeed()";
				//MainGUI.switchToMap();
			}
		}else if(activeCP.getHealth()==0){
			System.out.println("2");
			//MainGUI.addToChat("All your CPs died!");
			int runner = Constants.rand.nextInt(playerCPs.size());
			//MainGUI.addToChat(playerCPs.get(runner).getName()+" decided he didn't trust you and ran away!");
			playerCPs.remove(runner);
			//Player.setx(Constants.HealthCenterX);
			//Player.sety(Constants.HealthCenterY);
			//Player.setZone(Constants.HealthCenterZone);
			//MainGUI.switchToMap();
		}else{
			System.out.println("3");
			//MainGUI.addToChat(player.getName() + " caught the "+wildCP.getName());
			wildCP.heal();
			player.addCP(wildCP);
			//MainGUI.switchToMap();
		}
		System.out.println("4");
		newBattle(player);
		System.out.println("5");
	}

}
