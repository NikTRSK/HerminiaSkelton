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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

public class GameGUI extends JFrame{
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
<<<<<<< HEAD
	
	private BackgroundMusic music;
=======
	private FinalBattleScreen finalBattle;
	
	private BackgroundMusic bgm;
	private JLabel time;
>>>>>>> 70d835e276a737d08bcfe8017100bc13e0d9e83b

	public GameGUI(GameClientListener listener){
		super("Game");
		clientListener = listener;
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		bgm = new BackgroundMusic();
		bgm.casualstart();
		initializeComponents();
		createGUI();
		
		// fullscreen stuff
//		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//		setUndecorated(true);
//		gd.setFullScreenWindow(this);
		
		setVisible(true);
		switchToMap(false, beta);
		
		//GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //this.setUndecorated(true);
        //gd.setFullScreenWindow(this);
	}
	
	private void initializeComponents(){
		time = new JLabel("00:00");
		rightPanel = new JPanel();		
		
		beta = new Player("Elgin");
		
		chat = new ChatPanel(clientListener);
		gbc = new GridBagConstraints();
		//cards = new CardLayout();
		centerPanel = new JPanel(new CardLayout());
		
		menuBar = new JMenuBar();
		menu = new JMenu("Inventory");
	}
	
	private void createGUI(){
		this.setLayout(new BorderLayout());
		
		// Map Stuff.
		map = new map.MapScreen(this,new Dimension(Toolkit.getDefaultToolkit().getScreenSize()),beta);
		
		// Battle Stuff.
		battle  = new BattleScreen(beta, this);		
		centerPanel.setBackground(Constants.BACKGROUND_COLOR);
		centerPanel.add(map, "card 1");		
		centerPanel.add(battle, "card 2");
		
		this.add(centerPanel, BorderLayout.CENTER);
		chat.setBorder(BorderFactory.createLineBorder(Constants.BACKGROUND_COLOR, 5));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		rightPanel.setBackground(Constants.BACKGROUND_COLOR);
		time.setPreferredSize(new Dimension(100,100));
		rightPanel.add(time);
		rightPanel.add(chat);
		
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
		
		menu.add(inventory);
		setJMenuBar(menuBar);
		this.pack();
	}
	
	public void switchToBattle(){
		battle.newBattle(beta);
		CardLayout cards = (CardLayout)centerPanel.getLayout();
		cards.show(centerPanel, "card 2");
		bgm.endMusic();
		bgm.battlestart();
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
		
		bgm.endMusic();
		bgm.casualstart();
	}	
	
	public void playHealthCenter(){
		bgm.endMusic();
		bgm.healstart();
	}
	
	public void playExplore(){
		bgm.endMusic();
		bgm.casualstart();
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
		finalBattle = new FinalBattleScreen(this, clientListener, fbs, me);
		//TODO: display it
	}
	
	public void StartSinglePlayerFinalBattle(){
		//TODO
	}
}
