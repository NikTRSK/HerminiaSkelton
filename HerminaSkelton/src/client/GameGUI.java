package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import AllCPs.CP;
import utilities.BackGroundPanel;
import utilities.FinalBattleState;

public class GameGUI extends JFrame implements MouseListener {
	private static final long serialVersionUID = -8312855782342576917L;
	
	private JPanel centerPanel;
	private JPanel rightPanel;
	
	private ChatPanel chat;
	private GameClientListener clientListener;
	private JMenuBar menuBar;
	private JMenu menu;
	GridBagConstraints gbc;
	Player beta;
	
	private map.MapScreen map;
	private BattleScreen battle;

	private FinalBattleScreen finalBattle;
	
	private BackgroundMusic bgm;
	private JLabel time;
	
	private JButton mute;
	private Boolean muted;
	private int state;

	public GameGUI(GameClientListener listener){
		super("Game");
		clientListener = listener;
		clientListener.setGameGUI(this);
		
		// For the GUI.
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		initializeComponents();
		createGUI();
		
		// For Music.
		bgm = new BackgroundMusic();
		bgm.casualstart();
		muted = false;
		state = 1;
		
		// Initialize.
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);		
		setVisible(true);
		switchToMap(false, beta);
	}
	
	private void initializeComponents(){
		time = new JLabel("00:00");
		rightPanel = new JPanel();		
		
		beta = new Player("Elgin");
		
		chat = new ChatPanel(clientListener);
		gbc = new GridBagConstraints();

		centerPanel = new JPanel(new CardLayout());
		
		menuBar = new JMenuBar();
		menu = new JMenu("Inventory");
	}
	
	private void createGUI(){
		this.setLayout(new BorderLayout());
		
		// Map Stuff.
		map = new map.MapScreen(this,new Dimension(Toolkit.getDefaultToolkit().getScreenSize()),beta);
		map.addMouseListener(this);
		
		// Battle Stuff.
		battle  = new BattleScreen(beta, this);		
		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.add(map, "card 1");		
		centerPanel.add(battle, "card 2");
		this.add(centerPanel, BorderLayout.CENTER);
		
		// Mute Button.
		mute = new JButton("Mute");
		mute.setBackground(Constants.BACKGROUND_COLOR2);
		mute.setForeground(Constants.FONT_COLOR);
		mute.setFont(Constants.GAMEFONT);
		mute.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mute.getText().equals("Mute")){
					bgm.endMusic();
					mute.setText("Un-Mute");
					muted = true;
					map.setFocusable(true);
					map.requestFocusInWindow();
				}
				else{
					mute.setText("Mute");
					muted = false;
					if(state==1)bgm.casualstart();
					if(state==2)bgm.battlestart();
					if(state==3)bgm.finalstart();
					map.setFocusable(true);
					map.requestFocusInWindow();
				}
			}
			
		});
		
		
		chat.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		time.setPreferredSize(new Dimension(100,100));
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		rightPanel.setBackground(Constants.BACKGROUND_COLOR);
		rightPanel.add(time);
		rightPanel.add(chat);
		rightPanel.add(mute);
		
		chat.setFocusable(true);
		chat.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e){
				map.requestFocusInWindow();
			}
		});
		
		this.add(rightPanel, BorderLayout.EAST);
		
		menuBar.add(menu);
		JMenuItem inventory = new JMenuItem("Show Inventory");
		inventory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog(GameGUI.this, Dialog.ModalityType.APPLICATION_MODAL);  
				
				Vector<CP> CPs = beta.getCP();
				
				JPanel CPHolder = new JPanel();
				  CPHolder.setLayout(new BoxLayout(CPHolder, BoxLayout.X_AXIS));
				  
				  for(int i = 0; i < CPs.size(); i++){
					  
					  JLabel chooseCP = new JLabel(CPs.get(i).getName()+" lvl "+CPs.get(i).getLevel());
					  chooseCP.setBackground(Constants.BACKGROUND_COLOR2);
					  chooseCP.setForeground(Constants.FONT_COLOR);
					  chooseCP.setFont(Constants.GAMEFONT);
					  
					  JPanel CP = new BackGroundPanel(Constants.TYPE_BACKGROUNDS[CPs.get(i).getType()-1]);
					  CP.setLayout(new BorderLayout());
					  CP.add(new JLabel(CPs.get(i).getSprite()), BorderLayout.CENTER);
					  CP.add(chooseCP, BorderLayout.SOUTH);
					  CPHolder.add(CP);
				  }
				  
				  
				  JPanel dialogPanel = new JPanel();
				  dialogPanel.setLayout(new BorderLayout());
				  dialogPanel.setBackground(Constants.BACKGROUND_COLOR);
				  dialogPanel.add(CPHolder, BorderLayout.CENTER);
				  dialogPanel.add(new JLabel("Your CPs"), BorderLayout.NORTH);
				  
				  dialog.add(dialogPanel);
				  dialog.pack();
				  dialog.setVisible(true);		
			}
			
		});
		
		JMenu options = new JMenu("Options");
		
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientListener.logout();	
			}
			
		});
		
		menu.add(inventory);
		menuBar.add(options);
		options.add(quit);
		setJMenuBar(menuBar);
		this.pack();
	}
	
	public void switchToBattle(){
		battle.newBattle(beta);
		CardLayout cards = (CardLayout)centerPanel.getLayout();
		cards.show(centerPanel, "card 2");
		if(!muted){
			bgm.endMusic();
			bgm.battlestart();
		}
		state=2;
	}
	
	public void switchToMap(boolean dead, Player player){
		this.beta = player;
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
		
		if(!muted){
			bgm.endMusic();
			bgm.casualstart();
		}
		state = 2 ;
	}	
	
	public void playHealthCenter(){
		if(!muted){
			bgm.endMusic();
			bgm.healstart();
		}
	}
	
	public void playExplore(){
		if(!muted){
			bgm.endMusic();
			bgm.casualstart();
		}
	}
	
	public void updateTimer(Integer seconds){
		int sec = seconds % 60;
		int min = seconds / 60;
		
		int minutes = min % 60;
		time.setText("Timer "+minutes+":"+sec);
	}
	
	public void timerOut(){
		clientListener.prepareForFinalBattle(beta);		
	}
	
	public void StartMultiPlayerFinalBattle(Integer me, FinalBattleState fbs){
		if(!muted){
			bgm.endMusic();
			bgm.finalstart();
		}
		state=3;
		finalBattle = new FinalBattleScreen(this, clientListener, fbs, me);
		centerPanel.add(finalBattle, "card 3");
		
		CardLayout cards = (CardLayout)centerPanel.getLayout();
		cards.show(centerPanel, "card 3");
	}
	
	public void StartSinglePlayerFinalBattle(){
		centerPanel.add(new SinglePlayerFinalBattleScreen(beta, this), "card 3");
		
		CardLayout cards = (CardLayout)centerPanel.getLayout();
		cards.show(centerPanel, "card 3");
	}
	
	public void endOfGame(){
		int maxLevel = 0;
		int index = 0;
		for(int i = 0; i < beta.getCP().size(); i++){
			if(beta.getCP().get(i).getLevel()>maxLevel){
				maxLevel = beta.getCP().get(i).getLevel();
				index = i;
			}
		}
		CP friend  = beta.getCP().get(index);
		//new endGameGUI();
	}
	
	public void hideChat(){
		getContentPane().getLayout().removeLayoutComponent(rightPanel);
	}
	
	public void appendToChat(String user, String message) {
		chat.appendText(user, message);
	}
	
	public int getPlayerScore() {
		return beta.generateScore();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		map.setFocusable(true);
		map.requestFocusInWindow();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
