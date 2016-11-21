package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AllCPs.CP;

public class GameGUI extends JFrame{
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JPanel MapScreen;
	private JPanel BattleScreen;
	//private JPanel miniMap;
	private JPanel scoreDisplay;
	private ChatPanel chat;
	private JTextField score;
	private GameClientListener clientListener;
	private JMenuBar menuBar;
	private JMenu menu;
	GridBagConstraints gbc;
	CardLayout cards;
	Player beta;
	private JDialog finalBattle;
	private Vector<CP> cpInventory;
	
	private map.MapScreen map;//
	private BattleScreen battle;//

	public GameGUI(GameClientListener listener){
		super("Game");
		clientListener = listener;
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		initializeComponents();
		createGUI();
		setVisible(true);
	}
	
	private void initializeComponents(){
		
		rightPanel = new JPanel();		
		MapScreen = new JPanel();
		BattleScreen = new JPanel();
		//miniMap = new JPanel();
		scoreDisplay = new JPanel();
		chat = new ChatPanel(clientListener);
		score = new JTextField();
		gbc = new GridBagConstraints();
		cards = new CardLayout();
		centerPanel = new JPanel(cards);
		beta = new Player("Elgin");
		finalBattle = new JDialog();
		menuBar = new JMenuBar();
		menu = new JMenu("Inventory");
	}
	
	private void createGUI(){
		setLayout(new GridBagLayout());
		map = new map.MapScreen(this,new Dimension(Toolkit.getDefaultToolkit().getScreenSize()),beta);
		battle  = new BattleScreen(beta, this);
		
		MapScreen.add(map);
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		MapScreen.setPreferredSize(new Dimension((2*width)/3,height));
		BattleScreen.setPreferredSize(new Dimension((2*width)/3,height));
		BattleScreen.add(battle);
		centerPanel.add(MapScreen, "card 1");		
		centerPanel.add(BattleScreen, "card 2");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = .75;
		gbc.gridwidth = GridBagConstraints.RELATIVE;	
		gbc.gridheight = GridBagConstraints.REMAINDER;
		centerPanel.setLayout(new CardLayout());
		add(centerPanel, gbc);
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		//rightPanel.add(miniMap);
		//rightPanel.add(scoreDisplay);
		rightPanel.add(chat);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.gridy = 0;
		gbc.weightx = .25;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
				
		add(rightPanel, gbc);
		
		menuBar.add(menu);
		JMenuItem inventory = new JMenuItem("Show Inventory");
		inventory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame showInventory = new JFrame("Inventory");
				showInventory.setSize(600, 600);
				showInventory.setVisible(true);
				cpInventory = beta.getCP();			
			}
			
		});
		menu.add(inventory);
		setJMenuBar(menuBar);
	
		cards.show(centerPanel, "card 1");
	}
	
	public void switchToBattle(){
		cards.show(centerPanel, "card 2");
	}
	
	public void switchToMap(boolean dead){
		if (dead == true){
			map.setToCPCenter();
		}
		else{
			map.renderAndPaint();
			cards.show(centerPanel, "card 1");
		}
	}	
	
	public void timerout(){
		//finalBattle.add(new FinalBattleScreen(this, clientListener, ,));
		finalBattle.pack();
		finalBattle.setVisible(true);
	}
}
