package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
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
import utilities.HealthPanel;

public class SinglePlayerFinalBattleScreen extends JPanel{
	private static final long serialVersionUID = 538889179893602549L;
	
	private GameGUI mainGUI;
	private Random rand;
	
	private Player player;
	private Vector<CP> playerCPs;
	private CP activeCP;
	private CP wildCP;
	
	private JPanel chooseAction;
	private JPanel chooseAttack;
	private JPanel chooseSwitch;
	private JPanel cardHolder;
	private JPanel battlePanel;
	
	private BackGroundPanel typeBackground1;
	private BackGroundPanel typeBackground2;
	
	private JButton attack;
	private JButton switchCP;
	private JButton[] attacks;
	private JButton[] switchOption;
	
	private ImageIcon sprite1;
	private ImageIcon sprite2;
	private HealthPanel healthPanel1;
	private HealthPanel healthPanel2;
	private JLabel imageLabel1;
	private JLabel imageLabel2;
	private JPanel CP1;
	private JPanel CP2;
	
	private JTextArea CPUpdates1;
	private JTextArea CPUpdates2;
	
	
	public SinglePlayerFinalBattleScreen(Player currPlayer, GameGUI mainGUI){
		this.mainGUI = mainGUI;
		this.rand = new Random(System.currentTimeMillis());
		player = currPlayer;
		
		initializeVariables();
		initializeComponents();
		createGUI();
	}
	
	private void initializeVariables(){
		playerCPs = player.getCP();
		System.out.println("CP SIZES " + player.getCP().size());
		activeCP = playerCPs.get(Constants.rand.nextInt(playerCPs.size()));
		while(activeCP.getHealth()<=0){
			activeCP = playerCPs.get(Constants.rand.nextInt(playerCPs.size()));
		}
		wildCP = Constants.generateCP(rand.nextInt(Constants.numCPs));
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
		//healthLabel1 = new JLabel(activeCP.getHealth()+"/"+activeCP.getMaxHealth());
		//healthLabel1.setFont(new Font("Courier", Font.BOLD, 35));
		//healthLabel1.setHorizontalTextPosition(JLabel.CENTER);
		//TODO: green-red health panels
		healthPanel1 = new HealthPanel(activeCP);
		healthPanel1.setPreferredSize(new Dimension(0, 50));
		
		Image image = sprite1.getImage();
		Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite1 = new ImageIcon(newimg);
		imageLabel1 = new JLabel(sprite1);
		
		CPUpdates1 = new JTextArea("");
		typeBackground1 = new BackGroundPanel(Constants.TYPE_BACKGROUNDS[activeCP.getType()-1]);
		typeBackground1.setLayout(new BorderLayout());
		typeBackground1.add(imageLabel1, BorderLayout.CENTER);
		typeBackground1.add(healthPanel1, BorderLayout.SOUTH);
		
		CP1 = new JPanel();
		CP1.setLayout(new BorderLayout());
		CP1.setBackground(Constants.TYPE_COLOR[wildCP.getType()]);
		CP1.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR2, 5));
		CP1.add(typeBackground1, BorderLayout.CENTER);
		
		healthPanel2 = new HealthPanel(wildCP);
		healthPanel2.setPreferredSize(new Dimension(0, 50));
		
		image = sprite2.getImage();
		newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite2 = new ImageIcon(newimg);
		imageLabel2 = new JLabel(sprite2);
		
		CPUpdates2 = new JTextArea("dbd");
		typeBackground2 = new BackGroundPanel(Constants.TYPE_BACKGROUNDS[wildCP.getType()-1]);
		typeBackground2.setLayout(new BorderLayout());
		typeBackground2.add(imageLabel2, BorderLayout.CENTER);
		typeBackground2.add(healthPanel2, BorderLayout.SOUTH);
		
		
		CP2 = new JPanel();
		CP2.setLayout(new BorderLayout());
		CP2.setBackground(Constants.TYPE_COLOR[wildCP.getType()]);
		CP2.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR2, 5));
		CP2.add(typeBackground2, BorderLayout.CENTER);
		
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
		
		chooseAction.setLayout(new GridLayout(1, 2));
		chooseAction.add(attack, BorderLayout.WEST);
		chooseAction.add(switchCP, BorderLayout.CENTER);
	}
	
	private void initializeChooseAttack(){
		chooseAttack = new JPanel();
		chooseAttack.setLayout(new GridLayout(1, 3));
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
					if(activeCP.getHealth()<=0){
						boolean allDead = true;
						for(int i = 0; i < playerCPs.size(); i++){
							if(playerCPs.get(i).getHealth()>0)allDead=false;
						}
						if(allDead)lostBattle();
						else deadCP();
					}	
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
			if(playerCPs.get(i).getHealth()<=0)CP.setText(CP.getText()+" (fainted)");
			CP.setBackground(Constants.BACKGROUND_COLOR2);
			CP.setForeground(Constants.FONT_COLOR);
			CP.setFont(Constants.GAMEFONT);
			CP.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < switchOption.length; i++){
						if(playerCPs.get(i).getHealth()>0){
							switchOption[i].setEnabled(true);
						}else{
							switchOption[i].setEnabled(false);
						}
					}
					CP.setEnabled(false);
					executeSwitch(playerCPs.get(a));
					CardLayout layout = (CardLayout)cardHolder.getLayout();
					layout.show(cardHolder, "card 1");
					if(activeCP.getHealth()<=0)deadCP();
				}
			});
			switchOption[i] = CP;
		}
		
		chooseSwitch = new JPanel();
		chooseSwitch.setLayout(new GridLayout(1, switchOption.length+1));
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
	
	private void executeSwitch(CP switchTo){
		activeCP = switchTo;
		Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(1)]].move(wildCP, activeCP, CPUpdates2);
		redraw();
	}
	
	private void executeMove(int move){
		if(wildCP.getSpeed()<activeCP.getSpeed()){
			Constants.attackMoves[activeCP.getAttackMoves()[move]].move(activeCP, wildCP, CPUpdates1);
			if(wildCP.getHealth()<=0){
				redraw();
				wonBattle();
				return;
			}
			Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(2)]].move(wildCP, activeCP, CPUpdates2);
			redraw();
		}else{
			Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(2)]].move(wildCP, activeCP, CPUpdates2);
			if(activeCP.getHealth()<=0){
				redraw();
				return;
			}
			Constants.attackMoves[activeCP.getAttackMoves()[move]].move(activeCP, wildCP, CPUpdates1);
			redraw();
			if(wildCP.getHealth()<=0)wonBattle();
		}
		
	}
	
	private void redraw(){
		int a[] = activeCP.getAttackMoves();
		attacks[0].setText(Constants.attackMoves[a[0]].getName());
		attacks[1].setText(Constants.attackMoves[a[1]].getName());
		
		healthPanel1.refresh(activeCP);
		healthPanel2.refresh(wildCP);
		
		sprite1 = activeCP.getSprite();
		Image image = sprite1.getImage();
		Image newImg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite1 = new ImageIcon(newImg);
		imageLabel1.setIcon(sprite1);
		
		typeBackground1.setImage(Constants.TYPE_BACKGROUNDS[activeCP.getType()-1]);
		
		sprite2 = wildCP.getSprite();
		image = sprite2.getImage();
		newImg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
		sprite2 = new ImageIcon(newImg);
		imageLabel2.setIcon(sprite2);
		
		typeBackground2.setImage(Constants.TYPE_BACKGROUNDS[wildCP.getType()-1]);
		
		for(int i = 0; i<switchOption.length; i++){
			if(playerCPs.get(i).getHealth()<=0){
				switchOption[i].setEnabled(false);
			}
		}
	}
	
	private void deadCP(){
		JDialog dialog = new JDialog(mainGUI, Dialog.ModalityType.APPLICATION_MODAL);  
		
		for(int i = 0; i<switchOption.length; i++){
			if(playerCPs.get(i)==activeCP){
				switchOption[i].setEnabled(false);
				switchOption[i].setText(switchOption[i].getText()+" (fainted)");
			}
		}
		
		  JPanel CPHolder = new JPanel();
		  CPHolder.setLayout(new BoxLayout(CPHolder, BoxLayout.X_AXIS));
		  
		  for(int i = 0; i < playerCPs.size(); i++){
			  if(playerCPs.get(i).getHealth()<=0)continue;
			  int a = i;
			  
			  JButton chooseCP = new JButton(playerCPs.get(i).getName()+" lvl "+playerCPs.get(i).getLevel());
			  chooseCP.setBackground(Constants.BACKGROUND_COLOR2);
			  chooseCP.setForeground(Constants.FONT_COLOR);
			  chooseCP.setFont(Constants.GAMEFONT);
			  chooseCP.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					activeCP = playerCPs.get(a);
					redraw();
					dialog.dispose();
				}
				  
			  });
			  
			  JPanel CP = new BackGroundPanel(Constants.TYPE_BACKGROUNDS[playerCPs.get(i).getType()-1]);
			  CP.setLayout(new BorderLayout());
			  CP.add(new JLabel(playerCPs.get(i).getSprite()), BorderLayout.CENTER);
			  CP.add(chooseCP, BorderLayout.SOUTH);
			  CPHolder.add(CP);
		  }
		  
		  
		  JPanel dialogPanel = new JPanel();
		  dialogPanel.setLayout(new BorderLayout());
		  dialogPanel.setBackground(Constants.BACKGROUND_COLOR);
		  dialogPanel.add(CPHolder, BorderLayout.CENTER);
		  dialogPanel.add(new JLabel("Your CP fainted, choose another!"), BorderLayout.NORTH);
		  
		  dialog.add(dialogPanel);
		  dialog.pack();
		  dialog.setVisible(true);
	}
	
	private void lostBattle(){
		JDialog dialog = new JDialog(mainGUI, Dialog.ModalityType.APPLICATION_MODAL);
		JLabel info;
		
		if(playerCPs.size()>1){
			int runner = Constants.rand.nextInt(playerCPs.size());
			info = new JLabel("All of your CPs have fainted! "+playerCPs.get(runner).getName()
									+" doesn't trust you anymore and ran away!");
			playerCPs.remove(runner);
		}
		else{
			info = new JLabel("You lost, and will be returned to the health center");
		}
		info.setForeground(Constants.FONT_COLOR);
		info.setBackground(Constants.BACKGROUND_COLOR2);
		info.setFont(Constants.GAMEFONT);
		
		JButton okay = new JButton("Go to Health Center");
		okay.setForeground(Constants.FONT_COLOR);
		okay.setBackground(Constants.BACKGROUND_COLOR2);
		okay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				player.healAll();
				battleOver(true);
				//TODO: teleport player to health center
				//Player.setx(Constants.HealthCenterX);
				//Player.sety(Constants.HealthCenterY);
				//Player.setZone(Constants.HealthCenterZone);
			}
			
		});
		
		
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.setBackground(Constants.BACKGROUND_COLOR);
		result.add(info, BorderLayout.NORTH);
		//result.add(new JLabel(playerCPs.get(runner).getSprite()), BorderLayout.CENTER);
		result.add(okay, BorderLayout.SOUTH);
		
		dialog.add(result);
		dialog.pack();
		dialog.setVisible(true);
	}
	
	private void wonBattle(){
		JDialog dialog = new JDialog(mainGUI, Dialog.ModalityType.APPLICATION_MODAL);
		
		JLabel info = new JLabel("You won the battle! "+activeCP.getName()
								+" gained xp.");
		info.setForeground(Constants.FONT_COLOR);
		info.setBackground(Constants.BACKGROUND_COLOR2);
		info.setFont(Constants.GAMEFONT);
		
		JButton okay = new JButton("Okay");
		okay.setForeground(Constants.FONT_COLOR);
		okay.setBackground(Constants.BACKGROUND_COLOR2);
		okay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				System.out.println("a"+activeCP.getXP());
				activeCP.addXP(wildCP.getLevel());
				System.out.println("b"+activeCP.getXP());
				battleOver(false);
			}
			
		});
		
		
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.setBackground(Constants.BACKGROUND_COLOR);
		result.add(info, BorderLayout.NORTH);
		result.add(new JLabel(activeCP.getSprite()), BorderLayout.CENTER);
		result.add(okay, BorderLayout.SOUTH);
		
		dialog.add(result);
		dialog.pack();
		dialog.setVisible(true);
	}
	
	private void battleOver(boolean lost){
		
	}

}
