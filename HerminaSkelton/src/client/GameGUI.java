package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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

	public GameGUI(){
		super("Game");
		setSize(600,600);
		initializeComponents();
		createGUI();
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
		//clientListener = new GameClientListener();
	}
	
	private void createGUI(){
		setLayout(new BorderLayout());
		
		createCenterPanel();
		createScorePanel();
		createMiniMap();
		createRightPanel();
		
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

	}
	
	private void createMiniMap(){
		
	}
	
	private void createScorePanel(){
		
	}
	
	private void createRightPanel(){
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		rightPanel.add(miniMap);
		rightPanel.add(scoreDisplay);
		rightPanel.add(chat);
	}
	
	private void createCenterPanel(){
		centerPanel.setLayout(new CardLayout());
		centerPanel.add(createMapCard(), "map screen");
		//centerPanel.add(createBattleCard(), "battle card");
	}
	
	private JPanel createMapCard(){
		map.MapScreen map = new map.MapScreen(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		MapScreen.add(map);
		
		return MapScreen;
	}
	
	private JPanel createBattleCard(){
		BattleScreen battle = new BattleScreen(null);
		BattleScreen.add(battle);
		
		return BattleScreen;
	}
	
}
