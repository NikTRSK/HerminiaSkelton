package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameGUI extends JFrame{
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JPanel MapScreen;
	private JPanel BattleScreen;
	private JPanel miniMap;
	private JPanel scoreDisplay;
	private ChatPanel chat;
	private JTextField score;
	private GameClientListener clientListener;
	GridBagConstraints gbc;
	CardLayout cards;

	public GameGUI(){
		super("Game");
		setSize(600,600);
		initializeComponents();
		createGUI();
		//cards.show(createMapCard(), "map screen");
		setVisible(true);
	}
	
	private void initializeComponents(){
		centerPanel = new JPanel();
		rightPanel = new JPanel();		
		MapScreen = new JPanel();//new map.MapScreen(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())); 
		miniMap = new JPanel();
		scoreDisplay = new JPanel();
		chat = new ChatPanel(clientListener);
		score = new JTextField();
		gbc = new GridBagConstraints();
		cards = new CardLayout();
		//clientListener = new GameClientListener();
	}
	
	private void createGUI(){
		setLayout(new GridBagLayout());
		MapScreen.setBackground(Color.pink);
		BattleScreen.setBackground(Color.orange);
		
		createCenterPanel();
		createScorePanel();
		createMiniMap();
		createRightPanel();
	

	}
	
	private void createMiniMap(){
		
	}
	
	private void createScorePanel(){
		
	}
	
	private void createRightPanel(){
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(Color.green);
		gbc.fill = GridBagConstraints.BOTH;
		//
		gbc.gridx = 200;
		gbc.gridy = 0;
		
		rightPanel.add(miniMap);
		rightPanel.add(scoreDisplay);
		rightPanel.add(chat);
		
		add(rightPanel, gbc);
	}
	
	private void createCenterPanel(){
		centerPanel.setLayout(new CardLayout());
		centerPanel.setBackground(Color.blue);
		centerPanel.add(createMapCard(), "map screen");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		//centerPanel.add(createBattleCard(), "battle card");
		
		add(centerPanel, gbc);
	}
	
	private JPanel createMapCard(){
		map.MapScreen map = new map.MapScreen(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()),null);
		MapScreen.add(map);
		
		return MapScreen;
	}
	
	private JPanel createBattleCard(){
		BattleScreen battle = new BattleScreen(null,null);
		BattleScreen.add(battle);
		
		return BattleScreen;
	}
	
	public void updateGameGUI(String whichUpdate){
		if (whichUpdate == utilities.Commands.END_GAME){
			//dialog pane that checks if user really wants to end game
		}
		else if(whichUpdate == utilities.Commands.LOGOUT_USER){
			
		}
		else if(whichUpdate == utilities.Commands.MY_MOVE){
			//move player?
		}
		
	}
	
}
