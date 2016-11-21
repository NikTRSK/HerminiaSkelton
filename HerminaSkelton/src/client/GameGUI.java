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
	private static final long serialVersionUID = -8312855782342576917L;
	
	private JPanel centerPanel;
	private JPanel rightPanel;
	//private JPanel miniMap;
	
	private ChatPanel chat;
	private GameClientListener clientListener;
	private JMenuBar menuBar;
	private JMenu menu;
	GridBagConstraints gbc;
	//CardLayout cards;
	Player beta;
	private JDialog finalBattle;
	//private Vector<CP> cpInventory;
	
	private map.MapScreen map;//
	private BattleScreen battle;//

	public GameGUI(GameClientListener listener){
		super("Game");
		clientListener = listener;
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		initializeComponents();
		createGUI();
		setVisible(true);
		switchToMap(false);
	}
	
	private void initializeComponents(){
		
		rightPanel = new JPanel();		
		
		beta = new Player("Elgin");
		
		chat = new ChatPanel(clientListener);
		gbc = new GridBagConstraints();
		//cards = new CardLayout();
		centerPanel = new JPanel(new CardLayout());
		
		finalBattle = new JDialog();
		
		menuBar = new JMenuBar();
		menu = new JMenu("Inventory");
	}
	
	private void createGUI(){
		//this.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		
		// Map Stuff.
		map = new map.MapScreen(this,new Dimension(Toolkit.getDefaultToolkit().getScreenSize()),beta);
		//int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		//int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		//map.setPreferredSize(new Dimension((2*width)/3,height));
		
		// Battle Stuff.
		battle  = new BattleScreen(beta, this);		
		//battle.setPreferredSize(new Dimension((2*width)/3,height));
		centerPanel.add(map, "card 1");		
		centerPanel.add(battle, "card 2");
		
		/*gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = .75;
		gbc.gridwidth = GridBagConstraints.RELATIVE;	
		gbc.gridheight = GridBagConstraints.REMAINDER;
		add(centerPanel, gbc);*/
		this.add(centerPanel, BorderLayout.CENTER);
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(chat);
		
		/*gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.gridy = 0;
		gbc.weightx = .25;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
				
		add(rightPanel, gbc);*/
		this.add(rightPanel, BorderLayout.EAST);
		
		menuBar.add(menu);
		JMenuItem inventory = new JMenuItem("Show Inventory");
		/*inventory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame showInventory = new JFrame("Inventory");
				showInventory.setSize(600, 600);
				showInventory.setVisible(true);
				//cpInventory = beta.getCP();			
			}
			
		});*/
		
		menu.add(inventory);
		setJMenuBar(menuBar);
	}
	
	public void switchToBattle(){
		CardLayout cards = (CardLayout)centerPanel.getLayout();
		cards.show(centerPanel, "card 2");
	}
	
	public void switchToMap(boolean dead){
		if (dead == true){
			map.setToCPCenter();
			CardLayout cards = (CardLayout)centerPanel.getLayout();
			cards.show(centerPanel, "card 1");
		}
		else{
			map.renderAndPaint();
			CardLayout cards = (CardLayout)centerPanel.getLayout();
			cards.show(centerPanel, "card 1");
		}
		map.renderAndPaint();
		map.setFocusable(true);
		map.requestFocusInWindow();
	}	
	
	public void timerout(){
		//finalBattle.add(new FinalBattleScreen(this, clientListener, ,));
		finalBattle.pack();
		finalBattle.setVisible(true);
	}
}
