package client;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameGUI extends JFrame{
	private JPanel mainPanel;
	private JPanel rightPanel;
	private JPanel MapScreen;
	private JPanel miniMap;
	private JPanel scoreDisplay;
	//private ChatPanel chat;
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
		mainPanel = new JPanel();
		rightPanel = new JPanel();
		MapScreen = new JPanel();
		miniMap = new JPanel();
		scoreDisplay = new JPanel();
		//chat = new ChatPanel();
		score = new JTextField();
		//clientListener = new GameClientListener();
	}
	
	private void createGUI(){
		mainPanel.setLayout(new BorderLayout());
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(miniMap);
		rightPanel.add(scoreDisplay);
		
		mainPanel.add(MapScreen, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		add(mainPanel);
	}
	
}
