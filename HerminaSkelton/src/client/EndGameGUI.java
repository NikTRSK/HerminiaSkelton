package client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import AllCPs.CP;
import AllCPs.EmmaLautz;
import utilities.Constants;

public class EndGameGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private String username1;
	private String username2;
	private String temp;
	private JLabel message;
	private JButton favCP;
	private boolean win;
	private int gamemode;
	private int score1;
	private int score2;
	private CP bestfriend;
	private JLabel name1, name2, score1_, score2_;
	private JTextArea professorwords;
	private GameClientListener mlistener;
	private Vector<Integer> topFiveScores;
	private GridBagConstraints mGridBagConst;
	private BackgroundMusic music;
	public EndGameGUI(int gamemode, String name1, String name2, CP bestfriend, int score1, int score2, boolean win, GameClientListener mlistener){
		//gamemode 0 as guest, gamemmode 1 as singleplayer, gameode 2 as multiplayer
		this.gamemode = gamemode;
		this.mlistener = mlistener;
		this.bestfriend = bestfriend;
		this.win = win;
		this.score1 = score1;
		this.score2 = score2;
		username1 = name1;
		username2 = name2;
		topFiveScores = new Vector<Integer>(5);
		for(int i = 0; i < 5; i++){
			topFiveScores.add(i*1000);
		}
		addNames();
		setBackground();
		initializeComponents();
		setIcon();
		createGUI();
		addEvents();
		music.endstart();
	}
	
	private void addNames(){
		temp = Constants.generateWord(win, username1, username2, gamemode);
	}
	
	private void setIcon(){
		favCP.setIcon(bestfriend.getSprite());
	}
	
	private void initializeComponents(){
		music = new BackgroundMusic();
		message = new JLabel("");
		name1 = new JLabel("");
		name1.setFont(new Font("Serif", Font.BOLD, 20));
		name2 = new JLabel("");
		name2.setFont(new Font("Serif", Font.BOLD, 20));
		name1.setText(username1);
		if(gamemode ==2){
			name2.setText(username2);
		}
		score1_ = new JLabel("");
		score2_ = new JLabel("");
		score1_.setText(Integer.toString(score1));
		if(gamemode ==2){
			score2_.setText(Integer.toString(score2));
		}
		professorwords = new JTextArea(temp);
		professorwords.setEditable(false);
		professorwords.setPreferredSize(new Dimension(5,5));
		professorwords.setOpaque(false);
		professorwords.setColumns(1);
		professorwords.setLineWrap(true);
		professorwords.setRows(1);
		professorwords.setWrapStyleWord(true);
		professorwords.setFont(new Font("Serif", Font.BOLD, 20));
		favCP = new JButton();
		favCP.setOpaque(false);
		favCP.setContentAreaFilled(false);
		favCP.setBorderPainted(false);
	}
	
	private void setBackground(){
		try {
		    final Image backgroundImage = javax.imageio.ImageIO.read(new File(Constants.resourceFolderbg + "EndGameGUI.jpg"));
		    setContentPane(new JPanel(new BorderLayout()) {
				private static final long serialVersionUID = -1315795587311609680L;

				@Override public void paintComponent(Graphics g) {
		            g.drawImage(backgroundImage, 0, 0, null);
		        }
		    });
		} catch (IOException e) {
			JPanel error = new JPanel();
			JOptionPane.showMessageDialog(error,
					"Asset Corruption",
					"File Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void createGUI(){
		setSize(1280,720);
		setTitle("Herminia Skelton");
		setLocation(750,100);
		setLayout(new GridBagLayout());
		mGridBagConst = new GridBagConstraints();
		mGridBagConst.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		mGridBagConst.fill = GridBagConstraints.BOTH;
		
		mGridBagConst.gridx = 0;
		mGridBagConst.gridy = 0;
		mGridBagConst.insets = new Insets(100,0,0,0);
		add(name1,mGridBagConst);
		mGridBagConst.gridx = 1;
		mGridBagConst.insets = new Insets(100,100,0,300);
		add(name2,mGridBagConst);
		
		mGridBagConst.insets = new Insets(0,0,0,0);
		mGridBagConst.gridx = 0;
		mGridBagConst.gridy = 1;
		add(score1_,mGridBagConst);
		mGridBagConst.gridx = 1;
		mGridBagConst.insets = new Insets(0,100,0,300);
		add(score2_,mGridBagConst);
		
		for(int i = 0 ; i < 5; i++){
			JLabel numLabel = new JLabel(Integer.toString(i+1));
			JLabel namLabel = new JLabel("");
			if(topFiveScores.get(i) != null){
				namLabel.setText(Integer.toString(topFiveScores.get(i)));
			}
			mGridBagConst.insets = new Insets(0,0,0,0);
			if(i == 4){
				mGridBagConst.insets = new Insets(0,0,0,0);
			}
			mGridBagConst.gridx = 0;
			mGridBagConst.gridy = i+2;
			add(numLabel,mGridBagConst);
			mGridBagConst.insets = new Insets(0,100,0,400);
			if(i == 4){
				mGridBagConst.insets = new Insets(0,100,0,100);
			}
			mGridBagConst.gridx = 1;
			add(namLabel,mGridBagConst);
		}
		mGridBagConst.gridy = 7;
		mGridBagConst.gridx = 3;
		mGridBagConst.ipadx = 150;
		mGridBagConst.ipady = 150;
		mGridBagConst.insets = new Insets(0,0,100,100);
		add(professorwords,mGridBagConst);
		
		mGridBagConst.gridy = 8;
		mGridBagConst.gridx = 3;
		mGridBagConst.ipadx = 100;
		mGridBagConst.ipady = 100;
		mGridBagConst.insets = new Insets(0,0,0,300);
		add(favCP,mGridBagConst);

	}
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No","Restart"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Logout or Restart?","Hermina Skelton"
		        		,JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[2]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		        	System.exit(0);
		        }
		        else if(PromptResult == JOptionPane.CANCEL_OPTION){
		        	closePanel();
		        	new waitGUI(mlistener,"old host", "old port").setVisible(true);
		        }
		    }
		});
	}
	protected void closePanel(){
		music.endMusic();
		this.dispose();
	}
	protected void close(){
		 System.exit(0);
	}
	
	public static void main(String[] args){
		new EndGameGUI(2,"Nick","Matt", new EmmaLautz(3), 2000,3000,true, null).setVisible(true);
	}
}
