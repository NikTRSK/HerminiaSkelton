package Client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import AllCPs.CP;

public class BattleScreen extends JPanel{
	private static final long serialVersionUID = 538889179893602549L;
	private Vector<CP> playerCPs;
	private CP activeCP;
	private CP wildCP;
	private Player player;
	private JPanel battlePanel;
	private JPanel chooseAction;
	private JPanel chooseAttack;
	private JPanel chooseSwitch;
	private JPanel cardHolder;
	private JButton attack;
	private JButton switchCP;
	private JButton throwAssignment;
	
	
	public BattleScreen(Player currPlayer){
		player = currPlayer;
		
		initializeVariables();
		initializeComponents();
		updateListeners();
		createGUI();
	}

	
	private void initializeVariables(){
		playerCPs = player.getCP();
		activeCP = playerCPs.get(Constants.rand.nextInt(playerCPs.size()));
		wildCP = Constants.generateCP(Constants.rand.nextInt(Constants.numCPs));
	}
	
	private void initializeComponents(){
		initializeBattlePanel();
		initializeChooseAction();
		initializeChooseAttack();
		initializeChooseSwitch();
		initializeCardHolder();
	}
	
	private void initializeBattlePanel(){
		//TODO
	}
	
	private void initializeChooseAction(){
		chooseAction = new JPanel();
		attack = new JButton("Attack");
		attack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 2");
				
			}
			
		});
		switchCP = new JButton("Switch CP");
		switchCP.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout)cardHolder.getLayout();
				layout.show(cardHolder, "card 3");
				
			}
			
		});
		throwAssignment = new JButton("Throw Assignment");
		throwAssignment.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(executeCatch()){
					battleOver();
				}				
			}
			
		});
		chooseAction.setLayout(new FlowLayout());
		chooseAction.add(attack);
		chooseAction.add(switchCP);
		chooseAction.add(throwAssignment);
		//TODO: Formatting
	}
	
	private void initializeChooseAttack(){
		//TODO
	}
	
	private void initializeChooseSwitch(){
		//TODO
	}
	
	private void initializeCardHolder(){
		cardHolder = new JPanel(new CardLayout());		
		cardHolder.add(chooseAction, "card 1");
		cardHolder.add(chooseAttack, "card 2");
		cardHolder.add(chooseSwitch, "card 3");
		add(cardHolder, BorderLayout.SOUTH);
	}
	
	private void createGUI(){
		//TODO
		CardLayout layout = (CardLayout)cardHolder.getLayout();
		layout.show(cardHolder, "card 1");
	}
	
	private void updateListeners(){
		//TODO
	}
	
	private void executeSwitch(CP switchTo){
		activeCP = switchTo;
		Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(2)-1]].move(wildCP, activeCP);
	}
	
	private void executeMove(int move){
		Constants.attackMoves[activeCP.getAttackMoves()[move]].move(activeCP, wildCP);
		if(wildCP.getHealth()<=0)battleOver();
		Constants.attackMoves[wildCP.getAttackMoves()[Constants.rand.nextInt(2)-1]].move(wildCP, activeCP);
	}
	
	private boolean executeCatch(){
		player.deductAssignment();
		int roll = Constants.rand.nextInt(100);
		int threshold = 70-2*(wildCP.getHealth()/wildCP.getMaxHealth());
		if(roll>=threshold){
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
			}
		}else if(activeCP.getHealth()==0){
			//MainGUI.addToChat("All your CPs died!");
			int runner = Constants.rand.nextInt(playerCPs.size());
			//MainGUI.addToChat(playerCPs.get(runner).getName()+" decided he didn't trust you and ran away!");
			playerCPs.remove(runner);
		}else{
			//MainGUI.addToChat(player.getName() + " caught the "+wildCP.getName());
			wildCP.heal();
			player.addCP(wildCP);
		}
	}
}
