package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
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

import AllCPs.BlankCP;
import AllCPs.CP;
import AllMoves.DummyMove;
import utilities.BackGroundPanel;
import utilities.DeadSwitch;
import utilities.FinalBattleState;
import utilities.HealthPanel;
import utilities.PlayerAction;

public class FinalBattleScreen extends JPanel{

	// System Stuff.
	private static final long serialVersionUID = -9154200804429634453L;
	private GameClientListener cl;
	private Player player;
	private Integer me;
	private CP[] CPs;
	private JPanel cardHolder;
	private GameGUI mainGUI;
	private Integer state;
	
	// Stuff for Battle Panel.
	private JLabel[] nameLabels;
	private JLabel[] sprites;
	private HealthPanel[] healthLabels;
	private BackGroundPanel battlePanel;
		
	// Stuff for chooseActionPanel.
	private JPanel actionPanel;

	// Stuff for Choose Attack.
	private JPanel attackPanel;
	private JButton attackA;
	private JButton attackB;
	private Integer myAttack;
	
	// Stuff for Choose Target.
	private JPanel targetPanel;
	private JLabel targetHealth1;
	private JLabel targetHealth2;
	private JLabel targetSprite1;
	private JLabel targetSprite2;
	private JButton chooseTarget1;
	private JButton chooseTarget2;
	
	// Stuff for Choose Switch.
	private JButton[] switchOptions;
	private JPanel switchPanel;
	
	// Stuff for waitPanel.
	private JPanel waitPanel;
	private JLabel waitMessage;
	
	// Stuff for deadPanel.
	private JPanel deadPanel;

	public FinalBattleScreen(GameGUI mainGUI, GameClientListener cl, FinalBattleState fbs, Integer me){
		this.cl = cl;
		if(me==0)me=1;
		else if(me==1)me=0;
		this.me = me;
		this.myAttack = null;
		this.state = 1;
		
		this.player = fbs.players[me];
		this.CPs = new CP[4];
		if(fbs.cp1==null)System.out.println("FinalBattleScreen Constructor, cp1 is null");
		this.CPs[0] = fbs.cp1;
		this.CPs[1] = fbs.cp2;
		this.CPs[2] = fbs.cp3;
		this.CPs[3] = fbs.cp4;
		
		initializeBattlePanel();
		initializeOptionPanel();
		initializeChooseAttack();
		initializeChooseTarget();
		initializeChooseSwitch();
		initializeWaitPanel();
		initializeDeadPanel();
		initializeCards();
		createGUI();
		
		this.cl.setFBS(this);
	}
	
	private void initializeBattlePanel(){
		JPanel[] holders = new JPanel[4];
		nameLabels = new JLabel[4];
		sprites = new JLabel[4];
		healthLabels = new HealthPanel[4];
		
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
			HealthPanel health = new HealthPanel(CPs[i]);
			health.setPreferredSize(new Dimension(0, 50));
			healthLabels[i] = health;
		}
		
		// Setting name labels.
		for(int i = 0; i < 4; i++){
			JLabel name = new JLabel(CPs[i].getName());
			name.setPreferredSize(new Dimension(0, 50));
			if(i>1)name.setText("Miller's"+name.getText());
			name.setFont(Constants.GAMEFONT);
			name.setForeground(Constants.FONT_COLOR);
			name.setHorizontalAlignment(JLabel.CENTER);
			nameLabels[i] = name;
		}
		
		// Setting holder panels.
		for(int i = 0; i < 4; i++){
			JPanel holder = new JPanel();
			holder.setLayout(new BorderLayout());
			holder.add(nameLabels[i]);
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
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 4");
				
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
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 1");
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
				myAttack = new Integer(CPs[me].getAttackMoves()[0]);
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 3");
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
				myAttack = new Integer(CPs[me].getAttackMoves()[1]);
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 3");
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
				myAttack = null;
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 2");
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
				System.out.println("Player Action sent from player "+me);
				sendMessage(new PlayerAction(me, 1, Constants.attackMoves[myAttack], me, 2));
				myAttack = null;
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 5");
			}
			
		});
		
		JPanel holder1 = new JPanel();
		holder1.setLayout(new BorderLayout());
		holder1.setOpaque(true);
		holder1.add(targetHealth1, BorderLayout.NORTH);
		holder1.add(targetSprite1);
		holder1.add(chooseTarget1, BorderLayout.SOUTH);
		
		// For Miller's second CP.
		targetHealth2 = new JLabel(CPs[3].getHealth()+"/"+CPs[3].getMaxHealth());
		targetHealth2.setFont(Constants.GAMEFONT);
		targetHealth2.setBackground(Constants.BACKGROUND_COLOR2);
		targetHealth2.setForeground(Constants.FONT_COLOR);
		
		sprite = CPs[3].getSprite();
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
				System.out.println("Player Action sent from player "+me);
				sendMessage(new PlayerAction(me, 1, Constants.attackMoves[myAttack], me, 3));
				myAttack = null;
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 5");
				waitMessage.setText("Waiting for the other player to make their move...");
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
		targetPanel.setBackground(Constants.BACKGROUND_COLOR);
		targetPanel.add(backButton);
		targetPanel.add(holder1);
		targetPanel.add(holder2);
	}
	
	private void initializeChooseSwitch(){
		switchOptions = new JButton[player.getCP().size()];
		
		JButton backButton = new JButton("<< Back");
		backButton.setBackground(Constants.BACKGROUND_COLOR2);
		backButton.setForeground(Constants.FONT_COLOR);
		backButton.setFont(Constants.GAMEFONT);
		backButton.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 1");
			}
		});
		
		for(int i = 0; i < player.getCP().size(); i++){
			CP option = player.getCP().get(i);
			int a = i;
			JButton chooseOption = new JButton(option.getName()+" lvl "+option.getLevel());
			chooseOption.setBackground(Constants.BACKGROUND_COLOR2);
			chooseOption.setForeground(Constants.FONT_COLOR);
			chooseOption.setFont(Constants.GAMEFONT);
			chooseOption.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
			chooseOption.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					sendMessage(new PlayerAction(me, new Integer(2), null, me, a));
					CardLayout layout = (CardLayout)cardHolder.getLayout();
					layout.show(cardHolder, "card 5");
					waitMessage.setText("Waiting for other player to make their move...");
				}
				
			});
			if(option==CPs[me])chooseOption.setEnabled(false);
			switchOptions[i] = chooseOption;
		}
		
		switchPanel = new JPanel();
		switchPanel.setBackground(Constants.BACKGROUND_COLOR);
		switchPanel.setLayout(new GridLayout(1, switchOptions.length));
		
		switchPanel.add(backButton);
		for(int i = 0; i < switchOptions.length; i++){
			switchPanel.add(switchOptions[i]);
		}
		
	}
	
	private void initializeWaitPanel(){
		waitMessage = new JLabel("Waiting for other player...");
		waitMessage.setFont(Constants.GAMEFONT);
		waitMessage.setForeground(Constants.FONT_COLOR);
		
		waitPanel = new JPanel();
		waitPanel.setLayout(new BorderLayout());
		waitPanel.setBackground(Constants.BACKGROUND_COLOR);
		waitPanel.add(waitMessage);
	}
	
	private void initializeDeadPanel(){
		JLabel message = new JLabel("Your CPs have all fainted! Maybe your partner can still win...");
		message.setFont(Constants.GAMEFONT);
		message.setForeground(Constants.FONT_COLOR);
		
		deadPanel = new JPanel();
		deadPanel.setLayout(new BorderLayout());
		deadPanel.setBackground(Constants.BACKGROUND_COLOR);
		deadPanel.add(message);
	}
	
	private void initializeCards(){
		cardHolder = new JPanel(new CardLayout());	
		cardHolder.setPreferredSize(new Dimension(0, 300));
		cardHolder.add(actionPanel, "card 1");
		cardHolder.add(attackPanel, "card 2");
		cardHolder.add(targetPanel, "card 3");
		cardHolder.add(switchPanel, "card 4");
		cardHolder.add(waitPanel, "card 5");
		cardHolder.add(deadPanel, "card 6");
		add(cardHolder, BorderLayout.SOUTH);
	}
	
	private void createGUI(){
		this.setLayout(new BorderLayout());
		this.add(battlePanel, BorderLayout.CENTER);
		this.add(cardHolder, BorderLayout.SOUTH);
		
		CardLayout layout = (CardLayout)cardHolder.getLayout();
		layout.show(cardHolder, "card 1");
	}
	
	public void recieveMessage(FinalBattleState fbs){
		this.player = fbs.players[me];
		this.CPs[0] = fbs.cp1;
		this.CPs[1] = fbs.cp2;
		this.CPs[2] = fbs.cp3;
		this.CPs[3] = fbs.cp4;
		Integer temp = new Integer(fbs.gameStates[me]);
		this.state = temp;
		System.out.println("me: "+me);
		
		for(int i = 0; i< CPs.length; i++){
			System.out.println("CP"+i+"'s health: "+fbs.CPHealth[i]);
			CPs[i].changeHealth(fbs.CPHealth[i]-CPs[i].getHealth());
		}
		
		update();
		
		/*if(CPs[me].getHealth()<=0){
			boolean allDead=true;
			for(int i = 0; i < player.getCP().size(); i++){
				if(player.getCP().get(i).getHealth()>0)allDead=false;
			}
			if(allDead)return;
			else{
				replaceDead();
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 5");
				waitMessage.setText("Choose a new CP to switch in");
				return;
			}
		}
		
		int otherPlayer = 0;
		if(otherPlayer==me)otherPlayer = 1;
		Player other = fbs.players[otherPlayer];
		if(CPs[otherPlayer].getHealth()==0){
			boolean allDead=true;
			for(int i = 0; i < other.getCP().size(); i++){
				if(other.getCP().get(i).getHealth()>0)allDead=false;
			}
			if(allDead)return;
			CardLayout layout = (CardLayout)cardHolder.getLayout();
			layout.show(cardHolder, "card 5");
			waitMessage.setText("Waiting for other player to replace their CP");
		}*/
		
	}
	
	public void replaceDead(){
		JDialog dialog = new JDialog(mainGUI, Dialog.ModalityType.APPLICATION_MODAL);  
		
		  JPanel CPHolder = new JPanel();
		  CPHolder.setLayout(new BoxLayout(CPHolder, BoxLayout.X_AXIS));
		  
		  for(int i = 0; i < player.getCP().size(); i++){
			  if(player.getCP().get(i).getHealth()<=0)continue;
			  int a = i;
			  
			  JButton chooseCP = new JButton(player.getCP().get(i).getName()+" lvl "+player.getCP().get(i).getLevel());
			  chooseCP.setBackground(Constants.BACKGROUND_COLOR2);
			  chooseCP.setForeground(Constants.FONT_COLOR);
			  chooseCP.setFont(Constants.GAMEFONT);
			  chooseCP.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					cl.sendDeadSwitch(new DeadSwitch(me, a));
					dialog.dispose();
				}
				  
			  });
			  
			  JPanel CP = new JPanel();
			  CP.setLayout(new BorderLayout());
			  CP.add(new JLabel(player.getCP().get(i).getSprite()), BorderLayout.CENTER);
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
	
	private void update(){
		System.out.println(state);
		
		// Checking State.
		if(state==3){
			endGame(false);
			return;
		}
		if(state==4){
			endGame(true);
			return;
		}
		
		// Battle Panel.
		for(int i = 0; i < 4; i++){
			ImageIcon sprite = CPs[i].getSprite();
			Image img = sprite.getImage();
			Image nwImg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
			sprite = new ImageIcon(nwImg);
			nameLabels[i].setText(CPs[i].getName());
			if(i>1)nameLabels[i].setText("Miller's"+nameLabels[i].getText());
			sprites[i].setIcon(sprite);
			healthLabels[i].refresh(CPs[i]);
			//healthLabels[i].setText(CPs[i].getHealth()+"(change)/"+CPs[i].getMaxHealth());
		}
		
		if(state==2){
			sprites[me].setIcon(new ImageIcon(""));
			//healthLabels[me].setText("");
			CardLayout layout = (CardLayout)cardHolder.getLayout();
			layout.show(cardHolder, "card 6");
			sendMessage(new PlayerAction(me, 1, new DummyMove(), me, 2));
			return;
		}
		
		// Choose Attack Panel.
		ActionListener[] al = attackA.getActionListeners();
		for(int i = 0; i < al.length; i++){
			attackA.removeActionListener(al[i]);
		}
		al = attackB.getActionListeners();
		for(int i = 0; i < al.length; i++){
			attackB.removeActionListener(al[i]);
		}
		attackA.setText(Constants.attackMoves[CPs[me].getAttackMoves()[0]].getName());
		attackA.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				myAttack = new Integer(CPs[me].getAttackMoves()[0]);
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 3");
			}
		});
		attackB.setText(Constants.attackMoves[CPs[me].getAttackMoves()[1]].getName());
		attackB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				myAttack = new Integer(CPs[me].getAttackMoves()[1]);
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 3");
			}
		});
		
		// Choose Target Panel.
		chooseTarget1.setText(CPs[2].getName());
		chooseTarget2.setText(CPs[3].getName());
		targetHealth1.setText(CPs[2].getHealth()+"/"+CPs[2].getMaxHealth());
		targetHealth2.setText(CPs[3].getHealth()+"/"+CPs[3].getMaxHealth());
		targetSprite1.setIcon(CPs[2].getSprite());
		targetSprite2.setIcon(CPs[3].getSprite());		
		
		// Switch Panel.
		Vector<CP> CPVector = player.getCP();
		for(int i = 0; i < CPVector.size(); i++){
			switchOptions[i].setEnabled(true);
			if(CPVector.get(i)==CPs[me])switchOptions[i].setEnabled(false);
			if(CPVector.get(i).getHealth()<=0)switchOptions[i].setEnabled(false);
		}
		
		// Change to options.
		CardLayout layout = (CardLayout)cardHolder.getLayout();
		layout.show(cardHolder, "card 1");
	}
	
	private void sendMessage(PlayerAction pa){
		cl.sendAction(pa);
	}
	
	private void endGame(boolean win){
		//TODO
	}
}
