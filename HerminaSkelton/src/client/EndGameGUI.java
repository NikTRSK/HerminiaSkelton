package client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import AllCPs.CP;
import AllCPs.EdgarLugo;
import AllCPs.EmmaLautz;
import AllCPs.JimmyChen;
import utilities.Constants;
import utilities.GameScore;

public class EndGameGUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private String temp;
    private JLabel message;
    private JButton favCP;
    private boolean win;
    private boolean pause = false;
    private int gamemode;
    private CP bestfriend;
    private JLabel name1, name2, score1_, score2_;
    private JTextArea professorwords;
    private GameClientListener mlistener;
    private ArrayList<GameScore> topFiveScores;
    private GridBagConstraints mGridBagConst;
    private BackgroundMusic music;
    private JButton mute;
    
    public EndGameGUI(int gamemode,CP bestfriend, ArrayList<GameScore> gs, boolean win, GameClientListener mlistener){
        //gamemode 0 as guest, gamemmode 1 as singleplayer, gameode 2 as multiplayer
        this.gamemode = gamemode;
        this.mlistener = mlistener;
        this.bestfriend = bestfriend;
        this.win = win;
        topFiveScores = gs;
        addNames();
        setBackground();
        initializeComponents();
        setIcon();
        createGUI();
        addEvents();
        music.endstart();
    }
    
    private void addNames(){
        temp = Constants.generateWord(win, gamemode);
    }
    
    private void setIcon(){
        try{
            Image img3 = ImageIO.read(new File(Constants.resourceFolderbg + "unmute.png"));
            Image img4 = bestfriend.getSprite().getImage();
            Image img5 = img4.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            ImageIcon img6 = new ImageIcon(img5);
            favCP.setIcon(img6);
            mute.setIcon(new ImageIcon(img3));
            
        }
        catch (IOException e) {
            JPanel error = new JPanel();
            JOptionPane.showMessageDialog(error,
                                          "Asset Corruption",
                                          "File Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeComponents(){
        music = new BackgroundMusic();
        message = new JLabel("");
        name1 = new JLabel("");
        name1.setFont(new Font("Serif", Font.BOLD, 20));
        name2 = new JLabel("");
        name2.setFont(new Font("Serif", Font.BOLD, 20));
        name1.setText("Top Five Scores:");
        if(gamemode ==2){
            name2.setText("");
        }
        score1_ = new JLabel("");
        score2_ = new JLabel("");
        score1_.setText("");
        if(gamemode ==2){
            score2_.setText("");
        }
        professorwords = new JTextArea(temp);
        professorwords.setEditable(false);
        professorwords.setPreferredSize(new Dimension(1,1));
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
        music = new BackgroundMusic();
        mute = new JButton();
        mute.setOpaque(false);
        mute.setContentAreaFilled(false);
        mute.setBorderPainted(false);
        mute.setToolTipText("mute?");
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
        
        int count = 0;
        for(int i = 0 ; i < topFiveScores.size(); i++){
            JLabel numLabel = new JLabel("");
            JLabel namLabel = new JLabel("");
            numLabel.setText(topFiveScores.get(i).getUsername());
            namLabel.setText(Integer.toString(topFiveScores.get(i).getScore()));
            mGridBagConst.gridx = 0;
            mGridBagConst.gridy = i+2;
            mGridBagConst.insets = new Insets(0,0,0,0);
            if(i == 4){
                mGridBagConst.insets = new Insets(0,0,0,0);
            }
            numLabel.setHorizontalAlignment(JLabel.CENTER);
            numLabel.setVerticalAlignment(JLabel.CENTER);
            mGridBagConst.gridx = 0;
            mGridBagConst.gridy = i+2;
            add(namLabel,mGridBagConst);
            mGridBagConst.gridx = 1;
            mGridBagConst.insets = new Insets(0,50,0,300);
            if(i == 4){
                mGridBagConst.insets = new Insets(0,50,0,300);
            }
            namLabel.setHorizontalAlignment(JLabel.CENTER);
            namLabel.setVerticalAlignment(JLabel.CENTER);
            add(numLabel,mGridBagConst);
            count ++;
        }
        while(count != 5){
            JLabel numLabel = new JLabel("    ");
            JLabel namLabel = new JLabel("    ");
            mGridBagConst.gridx = 0;
            mGridBagConst.gridy = count+2;
            mGridBagConst.insets = new Insets(0,0,0,0);
            add(numLabel,mGridBagConst);
            mGridBagConst.gridx = 1;
            mGridBagConst.insets = new Insets(0,50,0,400);
            if(count == 4){
                mGridBagConst.insets = new Insets(0,50,0,400);
            }
            numLabel.setHorizontalAlignment(JLabel.CENTER);
            numLabel.setVerticalAlignment(JLabel.CENTER);
            namLabel.setHorizontalAlignment(JLabel.CENTER);
            namLabel.setVerticalAlignment(JLabel.CENTER);
            add(namLabel,mGridBagConst);
            count ++;
        }
        mGridBagConst.gridy = 7;
        mGridBagConst.gridx = 3;
        mGridBagConst.ipadx = 150;
        mGridBagConst.ipady = 150;
        mGridBagConst.insets = new Insets(0,100,100,100);
        add(professorwords,mGridBagConst);
        
        mGridBagConst.gridy = 8;
        mGridBagConst.gridx = 3;
        mGridBagConst.ipadx = 0;
        mGridBagConst.ipady = 0;
        mGridBagConst.insets = new Insets(0,0,0,300);
        add(favCP,mGridBagConst);
        
        mGridBagConst.gridy = 8;
        mGridBagConst.gridx = 1;
        mGridBagConst.ipadx = 32;
        mGridBagConst.ipady = 32;
        mGridBagConst.insets = new Insets(0,0,0,0);
        add(mute,mGridBagConst);
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
                    mlistener.logout();
                    System.exit(0);
                }
                else if(PromptResult == JOptionPane.CANCEL_OPTION){
                    closePanel();
                    new waitGUI(mlistener,"old host", "old port").setVisible(true);
                }
            }
        });
        mute.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!pause){
                    music.pauseMusic();
                    pause = true;
                    try{
                        Image img = ImageIO.read(new File(Constants.resourceFolderbg + "mute.png"));
                        mute.setIcon(new ImageIcon(img));
                    }
                    catch (IOException e1) {
                        JPanel error = new JPanel();
                        JOptionPane.showMessageDialog(error,
                                                      "Asset Corruption",
                                                      "File Error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    music.unpauseMusic();
                    pause = false;
                    try{
                        Image img = ImageIO.read(new File(Constants.resourceFolderbg + "unmute.png"));
                        mute.setIcon(new ImageIcon(img));
                    }
                    catch (IOException e1) {
                        JPanel error = new JPanel();
                        JOptionPane.showMessageDialog(error,
                                                      "Asset Corruption",
                                                      "File Error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
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
    	ArrayList<GameScore> topFiveScores = new ArrayList<GameScore>();
    	new EndGameGUI(2,new JimmyChen(2),topFiveScores,true,null).setVisible(true);
    }
}
